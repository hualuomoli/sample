package sample.flink.cep.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import sample.flink.cep.entity.AirQualityRecoder;

public class AirQualityRecoderUtils {

    private static final String[] citys = new String[] { "天津", "北京", "上海", "西安", "深圳", "广州" };
    private static final Random random = new Random();
    private static final AtomicInteger atomic = new AtomicInteger();

    private static final Integer MAX_QUALITY = 10;

    // 创建一条记录
    public static AirQualityRecoder createOne() {
        AirQualityRecoder record = new AirQualityRecoder();
        record.setId(atomic.getAndIncrement());
        record.setCity(citys[random.nextInt(citys.length)]);
        record.setQuality(random.nextInt(MAX_QUALITY));
        record.setEmmit(System.currentTimeMillis());
        return record;
    }

}
