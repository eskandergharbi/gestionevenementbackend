package com.example.service_evenement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
public class ServiceEvenementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEvenementApplication.class, args);
	}

}
