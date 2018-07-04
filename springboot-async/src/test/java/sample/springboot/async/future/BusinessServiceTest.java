package sample.springboot.async.future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class BusinessServiceTest {

  @Autowired
  private BusinessService service;

  @Test
  public void testDeal() throws Exception {
    service.deal();
  }

}
