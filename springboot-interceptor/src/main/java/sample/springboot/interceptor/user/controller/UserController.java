package sample.springboot.interceptor.user.controller;

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

  // http://localhost:8080/user/find?username=admin
  // http://localhost:8080/user/find?username=admin&token=7b4920a878e444caa15fee93c9214c86
  @RequestMapping(path = "/find")
  public String find(@RequestParam(value = "username") String username) {
    return "finded.";
  }

}
