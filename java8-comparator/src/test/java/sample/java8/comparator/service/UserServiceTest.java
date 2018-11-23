package sample.java8.comparator.service;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import sample.java8.comparator.entity.User;

public class UserServiceTest {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

  private UserService userService;
  private List<User> users;

  @Before
  public void before() {
    userService = new UserService();

    users = Lists.newArrayList();
    users.add(new User("jack ma", "马云", 50));
    users.add(new User("laowang", "王健林", 56));
    users.add(new User("liuqiangdong", "刘强东", 55));
    users.add(new User("mahuateng", "马化腾", 40));
  }

  @Test
  @Ignore
  public void testStaticMethod() {
    userService.staticMethod(users);
    users.stream().forEach(user -> logger.info("{}", user));
  }

  @Test
  @Ignore
  public void testInstanceMethod() {
    userService.instanceMethod(users);
    users.stream().forEach(user -> logger.info("{}", user));
  }

  @Test
  @Ignore
  public void testComparator() {
    userService.comparator(users);
    users.stream().forEach(user -> logger.info("{}", user));
  }

  @Test
  @Ignore
  public void testReversed() {
    userService.reversed(users);
    users.stream().forEach(user -> logger.info("{}", user));
  }

}
