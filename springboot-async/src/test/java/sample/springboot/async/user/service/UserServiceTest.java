package sample.springboot.async.user.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sample.springboot.async.util.AsyncUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

  @Autowired
  private UserService userService;

  @AfterClass
  public static void afterClass() {
    AsyncUtils.sleep(5000);
  }

  @Test
  public void testLogin() {
    boolean success = userService.login("admin", "123456");
    logger.info("验证结果");
    Assert.assertTrue("登录成功", success);
  }

  @Test
  public void testLoginAutoLog() {
    boolean success = userService.loginAutoLog("admin", "123456");
    logger.info("验证结果");
    Assert.assertTrue("登录成功", success);
  }

}
