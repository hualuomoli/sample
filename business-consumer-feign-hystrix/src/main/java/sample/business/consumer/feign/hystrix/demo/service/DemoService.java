package sample.business.consumer.feign.hystrix.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "business-provider", fallback = DemoErrorService.class)
public interface DemoService {

	@RequestMapping(path = "/demo/say")
	String say(@RequestParam(value = "world") String world);

}
