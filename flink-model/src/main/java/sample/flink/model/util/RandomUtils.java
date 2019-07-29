package sample.flink.model.util;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    /** {@linkplain Random#nextInt(int)}} */
    public static int nextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

}
