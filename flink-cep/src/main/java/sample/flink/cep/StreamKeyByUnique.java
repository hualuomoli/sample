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
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.google.common.collect.Lists;

import sample.flink.model.trade.TradeRecord;
import sample.flink.model.trade.TradeStatus;
import sample.flink.model.util.TradeUtils;

/**
 * 根据唯一属性分组
 */
public class StreamKeyByUnique {

    private static final List<TradeRecord> records = Lists.newArrayList(//
            TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 01) // 
            , TradeUtils.create("pitter", TradeStatus.SUCCESS, 500, 03) // 
            , TradeUtils.create("jack", TradeStatus.FAIL___, 500, 07) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 10) // 
            , TradeUtils.create("tom", TradeStatus.SUCCESS, 500, 15) // 
            , TradeUtils.create("tom", TradeStatus.SUCCESS, 500, 18) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 20) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 21) // 
            , TradeUtils.create("jack", TradeStatus.FAIL___, 500, 24) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 28) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 30) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 37) // 
            , TradeUtils.create("jack", TradeStatus.FAIL___, 500, 45) // 
            , TradeUtils.create("jack", TradeStatus.SUCCESS, 500, 60) // 
    );

    @SuppressWarnings("serial")
    private static PatternSelectFunction<TradeRecord, String> selectFunction = new PatternSelectFunction<TradeRecord, String>() {
        @Override
        public String select(Map<String, List<TradeRecord>> pattern) throws Exception {
            TradeRecord r1 = pattern.get("step1").get(0);
            TradeRecord r2 = pattern.get("step2").get(0);
            return r1.getUsername() + ":" + Lists.newArrayList(r1, r2).stream()//
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

    public static void execute() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<TradeRecord> stream = env.fromCollection(records)//
                .assignTimestampsAndWatermarks(periodicWatermarks)//
                .keyBy(TradeRecord::getUsername);

        Pattern<TradeRecord, ?> pattern = Pattern.<TradeRecord>begin("step1").where(condition)//
                .next("step2").where(condition) // 
                .within(Time.seconds(10));

        PatternStream<TradeRecord> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.select(selectFunction);

        select.print();

        env.execute("periodic timestamps event time.");
    }

}
