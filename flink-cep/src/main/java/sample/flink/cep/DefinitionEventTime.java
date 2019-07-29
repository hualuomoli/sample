package sample.flink.cep;

import java.util.List;
import java.util.Map;

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

import sample.flink.model.trade.TradeRecord;
import sample.flink.model.trade.TradeStatus;
import sample.flink.model.util.TradeUtils;

/**
 * 自定义事件时间
 */
public class DefinitionEventTime {

    private static final List<TradeRecord> records = Lists.newArrayList(//
            TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 01) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 03) // 
            , TradeUtils.create("jack", TradeStatus.CANCEL_, 100, 07) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 10) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 15) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 18) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 20) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 21) // 
            , TradeUtils.create("jack", TradeStatus.FAIL___, 100, 24) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 28) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 30) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 37) // 
            , TradeUtils.create("jack", TradeStatus.CREATED, 100, 45) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 60) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 100, 04) // 非按照数据的顺序添加的
    );

    @SuppressWarnings("serial")
    private static PatternSelectFunction<TradeRecord, String> selectFunction = new PatternSelectFunction<TradeRecord, String>() {
        @Override
        public String select(Map<String, List<TradeRecord>> pattern) throws Exception {
            TradeRecord r1 = pattern.get("step1").get(0);
            TradeRecord r2 = pattern.get("step2").get(0);
            TradeRecord r3 = pattern.get("step3").get(0);
            return Lists.newArrayList(r1, r2, r3).stream()//
                    .map(TradeRecord::getId)//
                    .map(String::valueOf)//
                    .reduce((id1, id2) -> id1 + "," + id2)//
                    .get();
        }
    };

    @SuppressWarnings("serial")
    private static IterativeCondition<TradeRecord> condition = new IterativeCondition<TradeRecord>() {
        @Override
        public boolean filter(TradeRecord value, Context<TradeRecord> ctx) throws Exception {
            return value.getTradeStatus() == TradeStatus.SUCCESS;
        }
    };

    @SuppressWarnings("serial")
    private static AssignerWithPeriodicWatermarks<TradeRecord> periodicWatermarks = new BoundedOutOfOrdernessTimestampExtractor<TradeRecord>(
            Time.seconds(1)) {

        @Override
        public long extractTimestamp(TradeRecord element) {
            return element.getTradeTime();
        }
    };

    @SuppressWarnings("serial")
    private static AssignerWithPunctuatedWatermarks<TradeRecord> punctuatedWatermarks = new AssignerWithPunctuatedWatermarks<TradeRecord>() {

        @Override
        public long extractTimestamp(TradeRecord element, long previousElementTimestamp) {
            return element.getTradeTime();
        }

        @Override
        public Watermark checkAndGetNextWatermark(TradeRecord lastElement, long extractedTimestamp) {
            return new Watermark(lastElement.getTradeTime());
        }
    };

    // // 最后一条看的到
    public void periodic() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<TradeRecord> stream = env.fromCollection(records).assignTimestampsAndWatermarks(periodicWatermarks);

        Pattern<TradeRecord, ?> pattern = Pattern.<TradeRecord>begin("step1").where(condition)//
                .next("step2").where(condition) // 
                .next("step3").where(condition) //
                .within(Time.seconds(10));

        PatternStream<TradeRecord> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.select(selectFunction);

        select.print();

        env.execute("periodic timestamps event time.");
    }

    // 最后一条看不到
    public void punctuated() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<TradeRecord> stream = env.fromCollection(records)
                .assignTimestampsAndWatermarks(punctuatedWatermarks);

        Pattern<TradeRecord, ?> pattern = Pattern.<TradeRecord>begin("step1").where(condition)//
                .next("step2").where(condition) // 
                .next("step3").where(condition) //
                .within(Time.seconds(10));

        PatternStream<TradeRecord> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.select(selectFunction);

        select.print();

        env.execute("punctuated timestamps event time.");
    }

}
