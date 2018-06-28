package sample.springboot.doc.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.springboot.doc.user.entity.User;
import sample.springboot.doc.user.service.UserService;

@RequestMapping(path = "/user")
@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping(path = "/login")
  public Boolean login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
    return userService.login(username, password);
  }

  @RequestMapping(path = "/findUser")
  public User findUser(@RequestParam(value = "username") String username) {
    return userService.findUser(username);
  }

}
