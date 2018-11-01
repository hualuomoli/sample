package sample.springboot.kafka.sender;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sample.springboot.kafka.consts.MessageConstans;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class MessageSenderTest {

  @Autowired
  private MessageSender messageSender;

  @AfterClass
  public static void afterClass() throws Exception {
    Thread.sleep(1000 * 5);
  }

  @Test
  public void testSend() {
    messageSender.send(MessageConstans.TOPIC, "test data from java.");
  }

}
