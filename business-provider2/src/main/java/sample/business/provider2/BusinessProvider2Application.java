package sample.business.provider2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BusinessProvider2Application {

	public static void main(String[] args) {
		SpringApplication.run(BusinessProvider2Application.class, args);
	}

}
