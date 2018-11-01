package sample.springboot.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component(value = "sample.springboot.kafka.sender.MessageSender")
public class MessageSender {

  private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

  @Autowired
  private KafkaTemplate<String, String> template;

  /**
   * 发送消息
   * @param topic 主题
   * @param content 消息内容
   */
  public void send(String topic, String content) {
    ListenableFuture<SendResult<String, String>> future = template.send(topic, content);
    future.addCallback((o) -> logger.info("send message success. {}", o), (e) -> logger.error("发送失败{}", e));
  }

}
