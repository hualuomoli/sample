package sample.kafka.logback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaLogbackTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaLogbackTest.class);

    @AfterClass
    public static void afterClass() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Test
    @Ignore
    public void test() {
        logger.info("send info to kafka");
    }

    @Test
    public void testPressure() throws InterruptedException {
        final int THREAD_SIZE = 3000;
        final int BATCH = 50;

        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch statistics = new CountDownLatch(THREAD_SIZE);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
        for (int i = 0; i < THREAD_SIZE; i++) {
            executorService.execute(new LogRunnable(i, BATCH, latch, statistics));
        }

        long start = System.currentTimeMillis();
        latch.countDown();
        statistics.await();
        long end = System.currentTimeMillis();
        logger.info("execute success with {} millis", (end - start));
    }

    private class LogRunnable implements Runnable {

        @SuppressWarnings("unused")
        private int index;
        private int batch;
        private CountDownLatch latch;
        private CountDownLatch statistics;

        private LogRunnable(int index, int batch, CountDownLatch latch, CountDownLatch statistics) {
            this.index = index;
            this.batch = batch;
            this.latch = latch;
            this.statistics = statistics;
        }

        @Override
        public void run() {

            try {
                latch.await();
            } catch (InterruptedException e) {
            }

            for (int i = 0; i < batch; i++) {
                this.execute(i);
                //                this.sleep();
            }
            // end for each

            statistics.countDown();
        }

        @SuppressWarnings("unused")
        private void sleep() {
            long waitMillis = (long) (Math.random() * 100 + 20);
            try {
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
            }
        }

        private void execute(int batchIndex) {
            int random = (int) (Math.random() * 100);

            // trace
            if (random <= 5) {
                logger.trace("trace message with {}", random);
                return;
            }

            // debug
            if (random <= 10) {
                logger.debug("debug message with {}", random);
                return;
            }

            // info
            if (random <= 95) {
                logger.info("info message with {}", random);
                return;
            }

            // warn
            if (random <= 98) {
                logger.warn("warn message with {}", random);
                return;
            }

            logger.error("error mesage with {}", random);
        }

    }

}
