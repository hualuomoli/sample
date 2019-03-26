package sample.kafka.logback;

import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaLogbackTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLogbackTest.class);

    @AfterClass
    public static void afterClass() throws InterruptedException {
        System.out.println("wait stop");
        Thread.sleep(1000);
    }

    @Test
    public void test() {
        logger.info("info message {}", Math.random());
    }

}
