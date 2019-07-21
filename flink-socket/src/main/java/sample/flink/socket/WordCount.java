package sample.flink.socket;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCount {

    public static void run(String hostname, int port, String delimiter) throws Exception {

        //获取运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //连接socket获取输入的数据
        DataStreamSource<String> text = env.socketTextStream(hostname, port, delimiter);

        //计算数据
        @SuppressWarnings("serial")
        DataStream<Entity> stream = text.flatMap(new FlatMapFunction<String, Entity>() {
            public void flatMap(String value, Collector<Entity> out) throws Exception {
                String[] splits = value.split("\\s");
                for (String word : splits) {
                    out.collect(new Entity(word, 1L));
                }
            }
        })//打平操作，把每行的单词转为<word,count>类型的数据
                .keyBy("word")//针对相同的word数据进行分组
                .timeWindow(Time.seconds(20), Time.seconds(3))//指定计算数据的窗口大小和滑动窗口大小
                .sum("count");

        //把数据打印到控制台
        stream.print().setParallelism(4);//使用一个并行度
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        env.execute("streaming word count");

    }

    // entity
    public static class Entity {

        public String word;
        public long count;

        public Entity() {
        }

        public Entity(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Entity [word=" + word + ", count=" + count + "]";
        }
    } // end entity

}
