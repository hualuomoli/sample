package sample.springboot.filter.api.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/demo")
@RestController
public class DemoController {

  // http://localhost:8080/api/demo/say?world=filter
  // http://localhost:8080/api/demo/say?world=filter&token=83f2d4f2e89d4c578dddfa5a1664ede6
  @RequestMapping(path = "/say")
  public String say(@RequestParam(value = "world") String world) {
    return "Hello " + world;
  }

}
