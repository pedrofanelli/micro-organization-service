package com.example.demo;

import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RefreshScope
public class MicroOrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroOrganizationServiceApplication.class, args);
	}
	
	/**
	 * Debería agregarse de forma automática
	 * 
	 * @return
	 */
	@Bean
    public Supplier<String> output(){
        return () -> {
            // Add logic to generate the message if needed
            String message = "Your generated message";
            System.out.println("Sending message to Kafka topic: " + message);
            return message;
        };
        
    }

}
