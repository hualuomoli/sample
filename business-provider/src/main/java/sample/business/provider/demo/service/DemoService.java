package sample.business.provider.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

	public String say(String world) {
		return "I can say " + world + " in business provider.";
	}

}
