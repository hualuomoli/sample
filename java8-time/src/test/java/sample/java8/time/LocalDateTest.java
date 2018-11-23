package sample.java8.time;

import java.time.LocalDate;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateTest {

  private static final Logger logger = LoggerFactory.getLogger(LocalDateTest.class);

  @Test
  public void test() {
    LocalDate current = LocalDate.now();

    logger.info("current is {}", current);
    // 年
    logger.info("year is {}", current.getYear());
    // 月
    logger.info("month is {}", current.getMonthValue());
    // 日
    logger.info("day of year is {}", current.getDayOfYear());
    logger.info("day of month is {}", current.getDayOfMonth());
    logger.info("day of week is {}", current.getDayOfWeek().getValue());
  }

}
