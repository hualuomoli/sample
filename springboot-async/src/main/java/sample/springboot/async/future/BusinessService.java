package sample.springboot.async.future;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

  private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);

  @Autowired
  private BusinessOne one;
  @Autowired
  private BusinessTwo two;
  @Autowired
  private BusinessThree three;

  public void deal() throws Exception {

    long start = System.currentTimeMillis();

    Future<String> f1 = one.deal();
    Future<String> f2 = two.deal();
    Future<String> f3 = three.deal();

    while (true) {
      if (f1.isDone() && f2.isDone() && f3.isDone()) {
        long end = System.currentTimeMillis();
        logger.info("业务处理完成,耗时{}", (end - start));
        break;
      }
    }

    // end
    System.out.println("结束");

  }

}
