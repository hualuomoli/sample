package sample.springboot.kafka.receiver;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import sample.springboot.kafka.consts.MessageConstans;

@Component(value = "sample.springboot.kafka.receiver.MessageReceiver")
public class MessageReceiver {

  private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

  @KafkaListener(topics = { MessageConstans.TOPIC })
  public void listen(ConsumerRecord<?, String> record) throws Exception {
    logger.info("topic={}, offset={}, value={}", record.topic(), record.offset(), record.value());
  }

}
