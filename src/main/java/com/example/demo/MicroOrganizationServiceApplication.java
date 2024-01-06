package com.example.demo;


import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RefreshScope
public class MicroOrganizationServiceApplication {

	@Autowired
	StreamBridge streamBridge;
	
	public static void main(String[] args) {
		SpringApplication.run(MicroOrganizationServiceApplication.class, args);
	}
	
	/**
	 * Debería agregarse de forma automática
	 * 
	 * NO! REVISAR STREAM BRIDGE!!
	 * 
	 * @return
	 */
	/*
	@Bean
    public Supplier<String> output(){
        return () -> {
            // Add logic to generate the message if needed
            String message = "Your generated message";
            System.out.println("Sending message to Kafka topic: " + message);
            return message;
        };
        
    }
    */

	/*
	//Test supplier
	@Bean
	public Supplier<String> supplier() {
		return () -> "random UUID: " + UUID.randomUUID();
	}

	@Bean
	public Consumer<String> receive() {
		return uuid -> {
			//This binding is targeted for Kafka cluster 1.
			streamBridge.send("foo-out-0", uuid);
			//This binding is targeted for Kafka cluster 2.
			//streamBridge.send("bar-out-0", uuid);
		};
	}
	*/
	
	@Bean
	public Function<String,String> processorBinding() {
		
		return s -> s + " :: " + System.currentTimeMillis();
		
	}
	

}
