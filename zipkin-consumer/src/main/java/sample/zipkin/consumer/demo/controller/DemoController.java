package sample.zipkin.consumer.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.zipkin.consumer.demo.service.DemoService;

@RequestMapping(path = "/demo")
@RestController
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping(path = "/say")
	public String say(@RequestParam(value = "world", defaultValue = "zipkin consumer") String world) {
		return demoService.say(world);
	}

}
