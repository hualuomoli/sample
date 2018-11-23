package sample.java8.time;

import java.time.LocalTime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalTimeTest {

  private static final Logger logger = LoggerFactory.getLogger(LocalTimeTest.class);

  @Test
  public void test() {
    LocalTime current = LocalTime.now();
    logger.info("current is {}", current);

    // 时
    logger.info("hour is {}", current.getHour());
    // 分
    logger.info("minute is {}", current.getMinute());
    // 秒
    logger.info("second is {}", current.getSecond());

  }

}
