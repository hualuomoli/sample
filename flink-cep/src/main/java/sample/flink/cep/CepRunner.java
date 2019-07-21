package sample.flink.cep;

import java.util.List;
import java.util.Map;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import com.google.common.collect.Maps;

import sample.flink.cep.codec.AirQualityRecoderDecoder;
import sample.flink.cep.entity.AirQualityRecoder;

public class CepRunner {

    private static final String topicId = "flink-cep";

    public static void main(String[] args) throws Exception {
        Map<String, String> properties = Maps.newHashMap();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        ParameterTool parameterTool = ParameterTool.fromMap(properties);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        FlinkKafkaConsumer010<AirQualityRecoder> consumer = new FlinkKafkaConsumer010<AirQualityRecoder>(topicId,
                new AirQualityRecoderDecoder(), parameterTool.getProperties());
        DataStream<AirQualityRecoder> stream = env.addSource(consumer);

        // ------------------------- warning -------------------------
        // 1、pattern
        @SuppressWarnings("serial")
        Pattern<AirQualityRecoder, ?> warningPattern = Pattern.<AirQualityRecoder>begin("first")
                .subtype(AirQualityRecoder.class)//
                // quality >= 6 or quality <= 3
                .where(new IterativeCondition<AirQualityRecoder>() {
                    @Override
                    public boolean filter(AirQualityRecoder value, Context<AirQualityRecoder> ctx) throws Exception {
                        return value.getQuality() >= 6;
                    }
                }).or(new IterativeCondition<AirQualityRecoder>() {
                    @Override
                    public boolean filter(AirQualityRecoder value, Context<AirQualityRecoder> ctx) throws Exception {
                        return value.getQuality() <= 3;
                    }
                })
                // next
                .next("second")
                // quality >= 6 or quality <= 2
                .where(new IterativeCondition<AirQualityRecoder>() {
                    @Override
                    public boolean filter(AirQualityRecoder value, Context<AirQualityRecoder> ctx) throws Exception {
                        return value.getQuality() >= 7;
                    }
                }).or(new IterativeCondition<AirQualityRecoder>() {
                    @Override
                    public boolean filter(AirQualityRecoder value, Context<AirQualityRecoder> ctx) throws Exception {
                        return value.getQuality() <= 2;
                    }
                })
                // 
                .within(Time.seconds(60));
        // 2、filter
        // key: city
        PatternStream<AirQualityRecoder> warningStream = CEP.pattern(stream.keyBy(AirQualityRecoder::getCity),
                warningPattern);
        DataStream<AirQualityWarningRecoder> warnings = warningStream
                .select((Map<String, List<AirQualityRecoder>> pattern) -> {
                    AirQualityRecoder first = (AirQualityRecoder) pattern.get("first").get(0);
                    AirQualityRecoder second = (AirQualityRecoder) pattern.get("second").get(0);
                    return new AirQualityWarningRecoder(first.getCity(), first, second);
                });

        // ------------------------- type -------------------------
        Pattern<AirQualityWarningRecoder, ?> typePattern = Pattern.<AirQualityWarningRecoder>begin("pass")
                .subtype(AirQualityWarningRecoder.class);
        PatternStream<AirQualityWarningRecoder> typeStream = CEP
                .pattern(warnings.keyBy(AirQualityWarningRecoder::getCity), typePattern);
        DataStream<AirQualityWarningTypeRecoder> warningTypes = typeStream
                .select((Map<String, List<AirQualityWarningRecoder>> pattern) -> {
                    AirQualityWarningRecoder warningRecord = (AirQualityWarningRecoder) pattern.get("pass").get(0);
                    AirQualityWarningTypeRecoder record = new AirQualityWarningTypeRecoder(warningRecord.getCity(),
                            warningRecord.getFirst(), warningRecord.getSecond());
                    int differ = Math.abs(record.getFirstQuality() - record.getSecondQuality());
                    if (differ <= 2) {
                        record.setType("质量超标");
                    } else {
                        record.setType("波动较大");
                    }

                    return record;
                });
        warnings.print();
        warningTypes.print();
        env.execute("cep run!!!");
    }

    public static class AirQualityWarningRecoder {

        private String city;
        private AirQualityRecoder first;
        private AirQualityRecoder second;

        public AirQualityWarningRecoder() {
        }

        public AirQualityWarningRecoder(String city, AirQualityRecoder first, AirQualityRecoder second) {
            this.city = city;
            this.first = first;
            this.second = second;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public AirQualityRecoder getFirst() {
            return first;
        }

        public void setFirst(AirQualityRecoder first) {
            this.first = first;
        }

        public AirQualityRecoder getSecond() {
            return second;
        }

        public void setSecond(AirQualityRecoder second) {
            this.second = second;
        }

        @Override
        public String toString() {
            return "AirQualityWarningRecoder [city=" + city + ", first=" + first + ", second=" + second + "]";
        }

    } // end warning entity

    public static class AirQualityWarningTypeRecoder {

        private String city;
        private String type;
        private Integer firstId;
        private Integer firstQuality;
        private Integer secondId;
        private Integer secondQuality;

        public AirQualityWarningTypeRecoder() {
            super();
        }

        public AirQualityWarningTypeRecoder(String city, AirQualityRecoder first, AirQualityRecoder second) {
            super();
            this.city = city;
            this.firstId = first.getId();
            this.firstQuality = first.getQuality();
            this.secondId = second.getId();
            this.secondQuality = second.getQuality();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getFirstId() {
            return firstId;
        }

        public void setFirstId(Integer firstId) {
            this.firstId = firstId;
        }

        public Integer getFirstQuality() {
            return firstQuality;
        }

        public void setFirstQuality(Integer firstQuality) {
            this.firstQuality = firstQuality;
        }

        public Integer getSecondId() {
            return secondId;
        }

        public void setSecondId(Integer secondId) {
            this.secondId = secondId;
        }

        public Integer getSecondQuality() {
            return secondQuality;
        }

        public void setSecondQuality(Integer secondQuality) {
            this.secondQuality = secondQuality;
        }

        @Override
        public String toString() {
            return "AirQualityWarningTypeRecoder [city=" + city + ", type=" + type + ", firstId=" + firstId
                    + ", firstQuality=" + firstQuality + ", secondId=" + secondId + ", secondQuality=" + secondQuality
                    + "]";
        }

    } // 

}
