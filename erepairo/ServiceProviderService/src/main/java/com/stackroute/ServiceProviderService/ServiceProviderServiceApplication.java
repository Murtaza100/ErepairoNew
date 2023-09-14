package com.stackroute.ServiceProviderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderServiceApplication.class, args);
	}


}
