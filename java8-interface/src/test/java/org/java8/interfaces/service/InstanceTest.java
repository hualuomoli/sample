package org.java8.interfaces.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceTest {

  private static final Logger logger = LoggerFactory.getLogger(InstanceTest.class);

  private Instance instance;

  @Before
  public void before() {
    instance = new Instance();
  }

  // 静态方法
  @Test
  @Ignore
  public void testEncrypt() {
    String chipher = UserService.encrypt("123456");
    logger.info("chipher={}", chipher);
  }

  // 默认实现
  @Test
  @Ignore
  public void testLogin() {
    boolean success = instance.login("admin", "123456");
    Assert.assertTrue(success);
  }

  // 未实现方法
  @Test
  @Ignore
  public void testRegister() {
    instance.register("admin", "123456", "管理员");
  }

}
