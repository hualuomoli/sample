package sample.flink.cep;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternFlatSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sample.flink.model.login.LoginRecord;
import sample.flink.model.login.LoginStatus;
import sample.flink.model.trade.TradeRecord;
import sample.flink.model.util.LoginUtils;
import sample.flink.model.util.TradeUtils;

public class MultipleStream {

    private static final Logger logger = LoggerFactory.getLogger(MultipleStream.class);

    @SuppressWarnings("serial")
    public void run() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        List<Object> datas = Lists.newArrayList();

        List<String> users = Lists.newArrayList("jack", "tom");
        List<LoginRecord> loginRecords = LoginUtils.create(10, users, 10);
        List<TradeRecord> tradeRecords = TradeUtils.create(50, users, 200, 6);
        loginRecords.forEach(data -> datas.add(data));
        tradeRecords.forEach(data -> datas.add(data));

        DataStream<Object> stream = env//
                .addSource(new SourceFunction<Object>() {

                    private boolean cancel = false;

                    @Override
                    public void run(SourceContext<Object> ctx) throws Exception {
                        for (int i = 0; !cancel && i < datas.size(); i++) {
                            ctx.collect(datas.get(i));
                        }
                    }

                    @Override
                    public void cancel() {
                        this.cancel = true;
                    }

                })
                // record timestamps
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Object>(Time.seconds(1)) {

                    @Override
                    public long extractTimestamp(Object element) {
                        if (element instanceof LoginRecord) {
                            return ((LoginRecord) element).getLoginTime();
                        }

                        if (element instanceof TradeRecord) {
                            return ((TradeRecord) element).getTradeTime();
                        }

                        logger.warn("no check object type {}", element.getClass().getName());
                        return 0;
                    }
                })
                // key
                .keyBy(new KeySelector<Object, String>() {

                    @Override
                    public String getKey(Object value) throws Exception {
                        if (value instanceof LoginRecord) {
                            return ((LoginRecord) value).getUsername();
                        }

                        if (value instanceof TradeRecord) {
                            return ((TradeRecord) value).getUsername();
                        }

                        return StringUtils.EMPTY;
                    }

                });

        // pattern
        Pattern<Object, TradeRecord> pattern = Pattern.begin("login")//
                .subtype(LoginRecord.class)//
                .where(new IterativeCondition<LoginRecord>() {

                    @Override
                    public boolean filter(LoginRecord value, Context<LoginRecord> ctx) throws Exception {
                        return value.getLoginStatus() == LoginStatus.FAIL__;
                    }
                })
                //
                .followedBy("trade")//
                .subtype(TradeRecord.class)//
                .where(new IterativeCondition<TradeRecord>() {

                    @Override
                    public boolean filter(TradeRecord value, Context<TradeRecord> ctx) throws Exception {
                        return value.getTradeMoney() >= 10;
                    }

                })//
                .timesOrMore(3)
                //
                .within(Time.seconds(30));

        PatternStream<Object> cep = CEP.pattern(stream, pattern);
        SingleOutputStreamOperator<String> select = cep.flatSelect(new PatternFlatSelectFunction<Object, String>() {

            @Override
            public void flatSelect(Map<String, List<Object>> pattern, Collector<String> out) throws Exception {
                LoginRecord login = (LoginRecord) pattern.get("login").get(0);
                List<Object> trades = pattern.get("trade");

                StringBuilder buffer = new StringBuilder(); // 1546272000
                buffer.append(login.getUsername()).append(":")//
                        .append(getSeconds(login.getLoginTime()));

                long total = 0;
                StringBuilder tradeBuffer = new StringBuilder();
                for (Object obj : trades) {
                    if (!(obj instanceof TradeRecord)) {
                        logger.error("invalid data {}", obj);
                        continue;
                    }
                    TradeRecord trade = (TradeRecord) obj;
                    tradeBuffer.append("\t") //
                            .append(trade.getUsername()).append(":") //
                            .append(",").append(trade.getTradeMoney()) // 
                            .append(",").append(getSeconds(trade.getTradeTime()))//
                            .append("\n"); //
                    total = total + trade.getTradeMoney();
                }

                buffer.append(",").append(total).append("\n").append(tradeBuffer);
                // 统计符合添加到流,否则不添加
                if (total >= 500) {
                    out.collect(buffer.toString());
                } else {
                    logger.info("============== \n{}", buffer.toString());
                }
                // end
            }
        });

        select.print();

        env.execute("multiple stream started.");
    }

    private static int getSeconds(long time) {
        return Integer.parseInt(new SimpleDateFormat("ss").format(new Date(time)));
    }

}
