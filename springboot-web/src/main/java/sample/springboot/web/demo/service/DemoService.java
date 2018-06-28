package sample.springboot.web.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	public String say(String world) {
		return "I can say " + world;
	}

	public void deal(String content) {
		logger.info("[deal] content={}", content);
	}

}
