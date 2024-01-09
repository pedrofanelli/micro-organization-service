package com.example.demo;


import java.util.function.Consumer;

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
	
	/*
	
	@Bean
	public Function<String,String> processorBinding() {
		
		return s -> s + " :: " + System.currentTimeMillis();
		
	}
	
	*/
	
	/*
	@Bean
	public Consumer<String> consumerBinding() {
		return s -> System.out.println("Data Consumed :: " + s.toUpperCase());
	}
	*/
	/*
	
	@Bean
	public Supplier<String> producerBinding() {
		return () -> {
			try {
				Thread.sleep(1500);
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			return "new data";
		};
	}
	
	*/

}
