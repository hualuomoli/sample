package sample.springboot.async.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.springboot.async.log.service.LogService;
import sample.springboot.async.util.AsyncUtils;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private LogService logService;

  // 手动添加日志
  public boolean login(String username, String password) {

    // add parameter
    logService.info(logger, "[login] username={}, password={}", username, password);

    // check
    boolean success = false;
    AsyncUtils.sleep(500);
    success = true;
    logger.info("登录成功");

    // add result
    logService.info(logger, "[login] success={}", success);

    return success;
  }

  // AOP自动切面
  public boolean loginAutoLog(String username, String password) {
    // check
    boolean success = false;
    AsyncUtils.sleep(500);
    success = true;
    logger.info("登录成功");

    return success;
  }

}
