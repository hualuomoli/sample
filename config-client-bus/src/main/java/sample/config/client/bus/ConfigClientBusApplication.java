package sample.config.client.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ConfigClientBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientBusApplication.class, args);
	}

}
