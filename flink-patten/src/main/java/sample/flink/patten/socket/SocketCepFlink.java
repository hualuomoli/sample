package sample.flink.patten.socket;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class SocketCepFlink {

    // nc -l 9000
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
        String hostname = "localhost";
        Integer port = 9000;
        String delimiter = "\n";
        return env.socketTextStream(hostname, port, delimiter);
    }

}
