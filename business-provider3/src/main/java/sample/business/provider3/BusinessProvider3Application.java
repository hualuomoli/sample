package sample.business.provider3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BusinessProvider3Application {

	public static void main(String[] args) {
		SpringApplication.run(BusinessProvider3Application.class, args);
	}

}
