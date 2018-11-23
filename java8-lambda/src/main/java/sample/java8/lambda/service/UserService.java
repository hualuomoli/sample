package sample.java8.lambda.service;

import java.util.Collections;
import java.util.List;

import sample.java8.lambda.entity.User;

public class UserService {

  public void lambda(List<User> users) {
    Collections.sort(users, (u1, u2) -> u1.getAge() - u2.getAge());
  }

  // 指定参数类型
  public void defineParamType(List<User> users) {
    Collections.sort(users, (User u1, User u2) -> u1.getAge() - u2.getAge());
  }

}
