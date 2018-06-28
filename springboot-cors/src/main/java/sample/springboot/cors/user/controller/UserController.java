package sample.springboot.cors.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/user")
@RestController
public class UserController {

  @RequestMapping(path = "/login")
  public String login(@RequestParam(value = "username") String username //
      , @RequestParam(value = "password") String password) {
    return "success";
  }

}
