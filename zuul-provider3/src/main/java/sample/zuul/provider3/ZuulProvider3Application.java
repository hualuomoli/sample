package sample.zuul.provider3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ZuulProvider3Application {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProvider3Application.class, args);
	}

}
