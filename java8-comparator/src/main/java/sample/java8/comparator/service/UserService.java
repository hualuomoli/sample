package sample.java8.comparator.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sample.java8.comparator.entity.User;

public class UserService {

  // 静态方法
  public void staticMethod(List<User> users) {
    Collections.sort(users, UserComparator::sort);
  }

  // 实例方法
  public void instanceMethod(List<User> users) {
    Collections.sort(users, User::sort);
  }

  // 比较器
  public void comparator(List<User> users) {
    Comparator<User> comparator = User::sort;
    Collections.sort(users, comparator);
  }

  // 反转
  public void reversed(List<User> users) {
    Comparator<User> comparator = User::sort;
    Collections.sort(users, comparator.reversed());
  }

  private static class UserComparator {

    static int sort(User user1, User user2) {
      return user1.getAge() - user2.getAge();
    }

  }

}
