package sample.bigdata.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import sample.bigdata.kafka.consts.MessageConstans;

@Component(value = "sample.bigdata.kafka.sender.MessageSender")
public class MessageSender {

  private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

  @Autowired
  private KafkaTemplate<String, String> template;

  /**
   * 发送消息
   * @param content 消息内容
   */
  public void send(String content) {
    ListenableFuture<SendResult<String, String>> future = template.send(MessageConstans.TOPIC, content);
    future.addCallback((o) -> logger.info("send message success. {}", o), (e) -> logger.error("发送失败{}", e));
  }

}
