package sample.business.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BusinessProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessProviderApplication.class, args);
	}

}
