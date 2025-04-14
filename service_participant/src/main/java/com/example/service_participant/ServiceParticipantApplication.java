package com.example.service_participant;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class ServiceParticipantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceParticipantApplication.class, args);
	}

}
