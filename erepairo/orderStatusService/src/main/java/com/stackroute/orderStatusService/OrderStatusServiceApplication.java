package com.stackroute.orderStatusService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The Class OrderStatusServiceApplication.
 *
 * @author sushanth
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class OrderStatusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderStatusServiceApplication.class, args);
	}

}
