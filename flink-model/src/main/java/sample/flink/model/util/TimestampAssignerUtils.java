package sample.flink.model.util;

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * 自定义事件时间
 */
public class TimestampAssignerUtils {

    // {@linkplain  org.apache.flink.streaming.api.functions.TimestampAssigner }
    public interface TimestampAssigner<T> {

        long extractTimestamp(T element);
    }

    /** periodic */
    public static <T> AssignerWithPeriodicWatermarks<T> periodic(TimestampAssigner<T> assigner) {
        return periodic(Time.seconds(1), assigner);
    }

    /** periodic */
    @SuppressWarnings("serial")
    public static <T> AssignerWithPeriodicWatermarks<T> periodic(Time time, TimestampAssigner<T> assigner) {

        return new BoundedOutOfOrdernessTimestampExtractor<T>(Time.seconds(1)) {

            @Override
            public long extractTimestamp(T element) {
                return assigner.extractTimestamp(element);
            }

        };
    }

    /** punctuated */
    @SuppressWarnings("serial")
    public static <T> AssignerWithPunctuatedWatermarks<T> punctuated(TimestampAssigner<T> assigner) {
        return new AssignerWithPunctuatedWatermarks<T>() {

            @Override
            public long extractTimestamp(T element, long previousElementTimestamp) {
                return assigner.extractTimestamp(element);
            }

            @Override
            public Watermark checkAndGetNextWatermark(T lastElement, long extractedTimestamp) {
                return new Watermark(assigner.extractTimestamp(lastElement));
            }
        };
    }

}
