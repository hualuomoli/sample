package sample.springboot.web.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(DemoServiceTest.class);

	@Autowired
	private DemoService demoService;

	@Test
	public void testSay() {
		String result = demoService.say("hello service test.");
		logger.debug("[say] result={}", result);
		Assert.assertNotNull("result is null.", result);
		Assert.assertEquals("执行失败", "I can say hello service test.", result);
	}

	@Test
	public void testDeal() {
		demoService.deal("some data to deal.");
	}

}
