package sample.elk.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class KafkaElkTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaElkTest.class);

    @Test
    public void test() {
        logger.info("send info to kafka");
    }

}
