package sample.springboot.async.log.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import sample.springboot.async.util.AsyncUtils;

@Service
public class LogService {

  private static final Logger logger = LoggerFactory.getLogger(LogService.class);

  private static final int millis = 3000;

  @Async
  public void info(String formate, Object... args) {
    this.info(logger, formate, args);
  }

  @Async
  public void info(Logger logger, String formate, Object... args) {
    AsyncUtils.sleep(millis);
    logger.info(formate, args);
  }

  @Async
  public void error(String msg, Throwable t) {
    this.error(logger, msg, t);
  }

  @Async
  public void error(Logger logger, String msg, Throwable t) {
    AsyncUtils.sleep(millis);
    logger.error(msg, t);
  }

  @Async
  public void error(String formate, Object... args) {
    this.error(logger, formate, args);
  }

  @Async
  public void error(Logger logger, String formate, Object... args) {
    AsyncUtils.sleep(millis);
    logger.error(formate, args);
  }

}
