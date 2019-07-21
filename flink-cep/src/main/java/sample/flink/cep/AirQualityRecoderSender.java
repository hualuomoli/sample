package sample.flink.cep;

import java.util.Map;
import java.util.Random;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;

import com.google.common.collect.Maps;

import sample.flink.cep.codec.AirQualityRecoderEncoder;
import sample.flink.cep.entity.AirQualityRecoder;
import sample.flink.cep.util.AirQualityRecoderUtils;

public class AirQualityRecoderSender {

    private static final String topicId = "flink-cep";

    public static void main(String[] args) throws Exception {
        Map<String, String> properties = Maps.newHashMap();
        properties.put("bootstrap.servers", "localhost:9092");
        ParameterTool parameterTool = ParameterTool.fromMap(properties);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<AirQualityRecoder> stream = env.addSource(new AirQualityRecoderGenerator());

        @SuppressWarnings("unused")
        DataStreamSink<AirQualityRecoder> sink = stream.addSink(
                new FlinkKafkaProducer010<>(topicId, new AirQualityRecoderEncoder(), parameterTool.getProperties()));
        stream.print();
        env.execute("write to kafka !!!");
    }

    // 数据流
    @SuppressWarnings("serial")
    public static class AirQualityRecoderGenerator implements SourceFunction<AirQualityRecoder> {
        boolean running = true;

        @Override
        public void run(SourceContext<AirQualityRecoder> ctx) throws Exception {
            while (running) {
                Thread.sleep(new Random().nextInt(300));
                ctx.collect(AirQualityRecoderUtils.createOne());
            }
        }

        @Override
        public void cancel() {
            running = false;
        }

    }// end generator

}
