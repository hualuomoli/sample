package org.java8.interfaces.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Instance implements UserService, MonitorService {

  private static final Logger logger = LoggerFactory.getLogger(Instance.class);

  // 实现多个接口时,如果方法相同需要制定实现类使用哪个接口的方法(也可以用多个)
  @Override
  public boolean login(String username, String password) {
    return MonitorService.super.login(username, password);
  }

  @Override
  public void register(String username, String password, String nickname) {
    logger.info("register info username={}, password={}, nickname={}", username, password, nickname);
  }

  @Override
  public void monitor() {
    logger.info("monitor started.");
  }

}
