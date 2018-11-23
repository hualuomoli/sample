package org.java8.interfaces.service;

public interface MonitorService {

  default boolean login(String username, String password) {
    return true;
  }

  void monitor();

}
