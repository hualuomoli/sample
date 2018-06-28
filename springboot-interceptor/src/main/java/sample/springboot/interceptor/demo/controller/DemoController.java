package sample.springboot.interceptor.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/demo")
@RestController
public class DemoController {

  private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

  // http://localhost:8080/demo/say?world=hello
  @RequestMapping(path = "/say")
  public String say(@RequestParam(value = "world", defaultValue = "interceptor") String world) {
    logger.info("业务层被调用");
    return "I can say " + world + " in demo controller.";
  }

  // http://localhost:8080/demo/deal
  @RequestMapping(path = "/deal")
  public String deal() {
    return "success";
  }

}
