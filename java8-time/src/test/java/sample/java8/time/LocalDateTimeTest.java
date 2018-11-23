package sample.java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalDateTimeTest {

  private static final Logger logger = LoggerFactory.getLogger(LocalDateTimeTest.class);

  @Test
  @Ignore
  public void testGet() {
    LocalDateTime current = LocalDateTime.now();
    logger.info("current is {}", current);

    // 日期
    logger.info("date is {}", current.toLocalDate());
    // 时间
    logger.info("time is {}", current.toLocalTime());
    // 年
    logger.info("year is {}", current.getYear());
    // 月
    logger.info("month is {}", current.getMonthValue());
    // 日
    logger.info("day of year is {}", current.getDayOfYear());
    logger.info("day of month is {}", current.getDayOfMonth());
    logger.info("day of week is {}", current.getDayOfWeek().getValue());
    // 时
    logger.info("hour is {}", current.getHour());
    // 分
    logger.info("minute is {}", current.getMinute());
    // 秒
    logger.info("second is {}", current.getSecond());
  }

  // 创建时间
  @Test
  @Ignore
  public void testOf() {
    logger.info("日期+时间 {}", LocalDateTime.of(LocalDate.now(), LocalTime.now()));
    logger.info("年月日时分 {}", LocalDateTime.of(2018, 10, 1, 12, 34));
    logger.info("年月日时分 {}", LocalDateTime.of(2018, Month.OCTOBER, 1, 12, 34));
    logger.info("年月日时分秒 {}", LocalDateTime.of(2018, 10, 1, 12, 34, 56));
    logger.info("年月日时分秒 {}", LocalDateTime.of(2018, Month.OCTOBER, 1, 12, 34, 56));
    logger.info("年月日时分秒/纳秒 {}", LocalDateTime.of(2018, 10, 1, 12, 34, 56, 999_999_999));
    logger.info("年月日时分秒/纳秒 {}", LocalDateTime.of(2018, Month.OCTOBER, 1, 12, 34, 56, 999_999_999));
  }

  // with(不改变原信息)
  @Test
  @Ignore
  public void testWith() {
    LocalDateTime current = LocalDateTime.of(2008, 8, 8, 12, 34, 56);
    LocalDateTime updated = current.withYear(2018).withMonth(10).withDayOfMonth(13).withHour(21).withMinute(37).withSecond(57);
    Assert.assertEquals(LocalDateTime.of(2018, 10, 13, 21, 37, 57), updated);
    Assert.assertEquals("原信息未修改", LocalDateTime.of(2008, 8, 8, 12, 34, 56), current);
  }

  // plus(不改变原信息)
  @Test
  @Ignore
  public void testPlus() {
    LocalDateTime current = LocalDateTime.of(2008, 8, 8, 12, 34, 56);
    LocalDateTime updated = current.plusYears(10).plusMonths(2).plusDays(5).plusHours(9).plusMinutes(3).plusSeconds(1);
    Assert.assertEquals(LocalDateTime.of(2018, 10, 13, 21, 37, 57), updated);
    Assert.assertEquals("原信息未修改", LocalDateTime.of(2008, 8, 8, 12, 34, 56), current);
  }

  // minus(不改变原信息)
  @Test
  @Ignore
  public void testMinus() {
    LocalDateTime current = LocalDateTime.of(2008, 8, 8, 12, 34, 56);
    LocalDateTime updated = current.minusYears(10).minusMonths(2).minusDays(5).minusHours(9).minusMinutes(3).minusSeconds(1);
    Assert.assertEquals(LocalDateTime.of(1998, 6, 3, 3, 31, 55), updated);
    Assert.assertEquals("原信息未修改", LocalDateTime.of(2008, 8, 8, 12, 34, 56), current);
  }

  // before
  @Test
  @Ignore
  public void testBefore() {
    Assert.assertTrue(LocalDateTime.of(2008, 8, 8, 12, 34, 55).isBefore(LocalDateTime.of(2008, 8, 8, 12, 34, 56)));
  }

  // after
  @Test
  @Ignore
  public void testAfter() {
    Assert.assertTrue(LocalDateTime.of(2008, 8, 8, 12, 34, 57).isAfter(LocalDateTime.of(2008, 8, 8, 12, 34, 56)));
  }

  // equal
  @Test
  @Ignore
  public void testEqual() {
    Assert.assertTrue(LocalDateTime.of(2008, 8, 8, 12, 34, 56).isEqual(LocalDateTime.of(2008, 8, 8, 12, 34, 56)));
  }

  // parse
  @Test
  @Ignore
  public void testParse() {
    LocalDateTime dateTime = LocalDateTime.parse("2008-08-08 12:34:56",
        new DateTimeFormatterBuilder()//
            .append(DateTimeFormatter.ISO_DATE)//
            .appendLiteral(' ')//
            .append(DateTimeFormatter.ISO_TIME)//
            .toFormatter());
    Assert.assertEquals(LocalDateTime.of(2008, 8, 8, 12, 34, 56), dateTime);
  }

  // format
  @Test
  @Ignore
  public void testFormat() {
    String dateTime = LocalDateTime.of(2008, 8, 8, 12, 34, 56)
        .format(new DateTimeFormatterBuilder()//
            .append(DateTimeFormatter.ISO_DATE)//
            .appendLiteral(' ')//
            .append(DateTimeFormatter.ISO_TIME)//
            .toFormatter());
    Assert.assertEquals("2008-08-08 12:34:56", dateTime);
  }

  // to date
  @Test
  @Ignore
  public void testParse2Date() {
    LocalDateTime dateTime = LocalDateTime.of(2008, 8, 8, 12, 34, 56);
    ZoneId zoneId = ZoneId.systemDefault();
    Instant instant = dateTime.atZone(zoneId).toInstant();
    Date date = Date.from(instant);
    Assert.assertEquals(new GregorianCalendar(2008, 7, 8, 12, 34, 56).getTime(), date);
  }

  // from date
  @Test
  @Ignore
  public void tesetFromDate() {
    Date date = new GregorianCalendar(2008, 7, 8, 12, 34, 56).getTime();
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, zoneId);
    Assert.assertEquals(LocalDateTime.of(2008, 8, 8, 12, 34, 56), dateTime);
  }

}
