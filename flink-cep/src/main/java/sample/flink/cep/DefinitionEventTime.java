package sample.flink.cep;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.google.common.collect.Lists;

import sample.flink.cep.entity.Record;
import sample.flink.cep.entity.RecordStatus;

/**
 * 自定义事件时间
 */
public class DefinitionEventTime {

    private static final AtomicInteger atomic = new AtomicInteger();

    private static final List<Record> records = Lists.newArrayList(//
            new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 01)// 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 03) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.ERROR__, 07) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 10) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 15) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 18) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 20) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 21) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.FAIL___, 24) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 28) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 30) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 37) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.ERROR__, 45) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 60) // 
            , new Record(atomic.incrementAndGet(), "jack", RecordStatus.SUCCESS, 04) // 非按照数据的顺序添加的
    );

    @SuppressWarnings("serial")
    private static PatternSelectFunction<Record, String> selectFunction = new PatternSelectFunction<Record, String>() {
        @Override
        public String select(Map<String, List<Record>> pattern) throws Exception {
            Record r1 = pattern.get("step1").get(0);
            Record r2 = pattern.get("step2").get(0);
            Record r3 = pattern.get("step3").get(0);
            return Lists.newArrayList(r1, r2, r3).stream()//
                    .map(Record::getId)//
                    .map(String::valueOf)//
                    .reduce((id1, id2) -> id1 + "," + id2)//
                    .get();
        }
    };

    @SuppressWarnings("serial")
    private static IterativeCondition<Record> condition = new IterativeCondition<Record>() {
        @Override
        public boolean filter(Record value, Context<Record> ctx) throws Exception {
            return value.getStatus() == RecordStatus.SUCCESS;
        }
    };

    @SuppressWarnings("serial")
    private static AssignerWithPeriodicWatermarks<Record> periodicWatermarks = new BoundedOutOfOrdernessTimestampExtractor<Record>(
            Time.seconds(1)) {

        @Override
        public long extractTimestamp(Record element) {
            return element.getEmit();
        }
    };

    @SuppressWarnings("serial")
    private static AssignerWithPunctuatedWatermarks<Record> punctuatedWatermarks = new AssignerWithPunctuatedWatermarks<Record>() {

        @Override
        public long extractTimestamp(Record element, long previousElementTimestamp) {
            return element.getEmit();
        }

        @Override
        public Watermark checkAndGetNextWatermark(Record lastElement, long extractedTimestamp) {
            return new Watermark(lastElement.getEmit());
        }
    };

    // // 最后一条看的到
    public void periodic() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Record> stream = env.fromCollection(records).assignTimestampsAndWatermarks(periodicWatermarks);

        Pattern<Record, Record> pattern = Pattern.<Record>begin("step1").where(condition)//
                .next("step2").where(condition) // 
                .next("step3").where(condition) //
                .within(Time.seconds(10));

        PatternStream<Record> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.select(selectFunction);

        select.print();

        env.execute("periodic timestamps event time.");
    }

    // 最后一条看不到
    public void punctuated() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<Record> stream = env.fromCollection(records).assignTimestampsAndWatermarks(punctuatedWatermarks);

        Pattern<Record, Record> pattern = Pattern.<Record>begin("step1").where(condition)//
                .next("step2").where(condition) // 
                .next("step3").where(condition) //
                .within(Time.seconds(10));

        PatternStream<Record> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.select(selectFunction);

        select.print();

        env.execute("punctuated timestamps event time.");
    }

}
