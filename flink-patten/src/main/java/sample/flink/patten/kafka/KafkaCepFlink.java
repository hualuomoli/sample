package sample.flink.patten.kafka;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;

import com.google.common.collect.Maps;

public class KafkaCepFlink {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> stream = loadStream(env);

        // pattern
        Pattern<String, ?> pattern = Pattern.<String>begin("step1").where(new Condition()) // 
                .next("step2").where(new Condition()) // 
                .next("step3").where(new Condition()) // 
                .within(Time.seconds(10)); // 10s 三次
        // cep
        PatternStream<String> patternStream = CEP.pattern(stream, pattern);
        // check
        @SuppressWarnings("serial")
        DataStream<String> warnings = patternStream.select(new PatternSelectFunction<String, String>() {

            @Override
            public String select(Map<String, List<String>> pattern) throws Exception {
                List<String> datas = pattern.values().stream().flatMap(list -> list.stream())
                        .collect(Collectors.toList());
                StringBuilder buffer = new StringBuilder();
                for (String data : datas) {
                    buffer.append(", ").append(data);
                }
                return buffer.substring(1).toString();
            }

        });
        warnings.print();
        env.execute("socket cep started.");

    }

    @SuppressWarnings("serial")
    private static class Condition extends IterativeCondition<String> {

        @Override
        public boolean filter(String value, Context<String> ctx) throws Exception {
            return Optional.ofNullable(value) //
                    .filter(v -> v.matches("\\d+")) //
                    .map(Integer::parseInt) //
                    .filter(v -> v >= 10) // 
                    .isPresent();
        }

    }

    private static DataStream<String> loadStream(StreamExecutionEnvironment env) {
        String topicId = "kafka-cep-flink";
        Map<String, String> properties = Maps.newHashMap();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "test");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        ParameterTool parameterTool = ParameterTool.fromMap(properties);

        FlinkKafkaConsumer010<String> consumer = new FlinkKafkaConsumer010<String>(topicId, new SimpleStringSchema(),
                parameterTool.getProperties());
        return env.addSource(consumer);
    }

}
