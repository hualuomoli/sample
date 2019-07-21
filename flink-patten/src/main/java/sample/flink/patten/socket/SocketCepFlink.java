package sample.flink.patten.socket;

import java.util.List;
import java.util.Map;
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

    private static final String hostname = "localhost";
    private static final Integer port = 9000;

    // nc -l 9000
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Integer> stream = env.socketTextStream(hostname, port, "\n").map(Integer::parseInt);

        // pattern
        Pattern<Integer, ?> pattern = Pattern.<Integer>begin("step1").where(new IntegerIterativeCondition()) // 
                .next("step2").where(new IntegerIterativeCondition()) // 
                .next("step3").where(new IntegerIterativeCondition()) // 
                .within(Time.seconds(10)); // 10s 三次
        // cep
        PatternStream<Integer> patternStream = CEP.pattern(stream, pattern);
        // check
        @SuppressWarnings("serial")
        DataStream<String> warnings = patternStream.select(new PatternSelectFunction<Integer, String>() {

            @Override
            public String select(Map<String, List<Integer>> pattern) throws Exception {
                List<Integer> datas = pattern.values().stream().flatMap(list -> list.stream())
                        .collect(Collectors.toList());
                StringBuilder buffer = new StringBuilder();
                for (Integer data : datas) {
                    buffer.append(", ").append(data);
                }
                return buffer.substring(1).toString();
            }

        });
        warnings.print();
        env.execute("socket cep started.");

    }

    @SuppressWarnings("serial")
    private static class IntegerIterativeCondition extends IterativeCondition<Integer> {

        @Override
        public boolean filter(Integer value, Context<Integer> ctx) throws Exception {
            return value > 10;
        }

    }

}
