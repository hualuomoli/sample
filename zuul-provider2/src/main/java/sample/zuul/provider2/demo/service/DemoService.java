package sample.zuul.provider2.demo.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

	public String say(String world) {
		return "I can say " + world + " in zuul provider2 demo.";
	}

}
