package com.example.demo.events.source;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleSourceBean {

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
