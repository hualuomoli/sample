package sample.config.client.bus2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConfigClientBus2Application {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientBus2Application.class, args);
	}

}
