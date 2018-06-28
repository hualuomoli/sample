package sample.business.consumer.feign.hystrix.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DemoErrorService implements DemoService {

	@Override
	public String say(String world) {
		return "say " + world + " error.";
	}

}
