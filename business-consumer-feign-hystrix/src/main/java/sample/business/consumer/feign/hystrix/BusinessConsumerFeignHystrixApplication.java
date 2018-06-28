package sample.business.consumer.feign.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class BusinessConsumerFeignHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessConsumerFeignHystrixApplication.class, args);
	}

}
