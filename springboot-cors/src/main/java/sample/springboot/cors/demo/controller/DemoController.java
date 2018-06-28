package sample.springboot.cors.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/demo")
@RestController
public class DemoController {

  @RequestMapping(path = "/say")
  public String say(@RequestParam(value = "world") String world) {
    return "I can say " + world;
  }

}
