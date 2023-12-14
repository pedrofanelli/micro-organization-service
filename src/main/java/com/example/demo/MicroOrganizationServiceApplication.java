package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class MicroOrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroOrganizationServiceApplication.class, args);
	}

}
