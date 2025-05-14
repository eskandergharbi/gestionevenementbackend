package com.example.service_resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.ws.config.annotation.EnableWs;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWs
public class ServiceResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceResourceApplication.class, args);
	}

}
