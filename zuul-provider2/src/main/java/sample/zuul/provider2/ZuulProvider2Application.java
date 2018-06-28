package sample.zuul.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ZuulProvider2Application {

	public static void main(String[] args) {
		SpringApplication.run(ZuulProvider2Application.class, args);
	}

}
