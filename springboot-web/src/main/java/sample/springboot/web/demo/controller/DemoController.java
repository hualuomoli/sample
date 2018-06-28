package sample.springboot.web.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.springboot.web.demo.service.DemoService;

@RequestMapping(path = "/demo")
@RestController
public class DemoController {

  private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

  @Autowired
  private DemoService demoService;

  @RequestMapping(path = "/say")
  public String say(@RequestParam(value = "world") String world) {
    logger.info("method demo.say called.");
    return demoService.say(world);
  }

  @RequestMapping(path = "/deal")
  public void deal(@RequestParam(value = "content", required = false) String content) {
    logger.info("method demo.deal called.");
    demoService.deal(content);
  }

}
