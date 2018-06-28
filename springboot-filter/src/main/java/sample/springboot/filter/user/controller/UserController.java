package sample.springboot.filter.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/user")
@RestController
public class UserController {

  // http://localhost:8080/user/login?username=admin&password=123456
  @RequestMapping(path = "/login")
  public String login(@RequestParam(value = "username") String username//
      , @RequestParam(value = "password") String password) {
    return "success";
  }

}
