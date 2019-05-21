package com.microservices.apiaccountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAccountManagementApplication.class, args);
	}

}
