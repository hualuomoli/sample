package sample.business.consumer.url.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoService {

	private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

	@Value(value = "${provider.url}")
	private String providerUrl;

	@Autowired
	private RestTemplate template;

	public String say(String world) {
		String url = providerUrl + "/demo/say?world=" + world;
		logger.info("url is {}", url);
		return template.getForObject(url, String.class);
	}

}
