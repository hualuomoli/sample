package org.java8.interfaces.service;

import org.apache.commons.codec.digest.DigestUtils;

public interface UserService {

  /**
   * 加密(接口静态方法)
   * @param password 密码
   * @return 密文
   */
  static String encrypt(String password) {
    return DigestUtils.md5Hex(password);
  }

  /**
   * 登录(接口默认实现)
   * @param username 用户名
   * @param password 密码
   * @return 是否登录成功
   */
  default boolean login(String username, String password) {
    return "admin".equals(username) && "123456".equals(password);
  }

  /**
   * 注册
   * @param username 用户名
   * @param password 密码
   * @param nickname 昵称
   */
  void register(String username, String password, String nickname);

}
