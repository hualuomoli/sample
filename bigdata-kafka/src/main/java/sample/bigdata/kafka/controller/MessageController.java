package sample.bigdata.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.bigdata.kafka.sender.MessageSender;

@RequestMapping(value = "/message")
@RestController(value = "sample.bigdata.kafka.controller.MessageController")
public class MessageController {

  @Autowired
  private MessageSender messageSender;

  // http://localhost:8080/message/send?content=测试内容发送
  @RequestMapping(value = "/send")
  public String send(@RequestParam(value = "content") String content) {
    messageSender.send(content);
    return "send " + content + " success.";
  }

}
