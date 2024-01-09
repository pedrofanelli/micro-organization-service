package com.example.demo.events.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.example.demo.events.model.OrganizationChangeModel;
import com.example.demo.utils.UserContext;

/**
 * Bean encargado de todo el manejo del envio por Kafka de update a otro microservicio (Licensing Service)
 */
@Component
public class SimpleSourceBean {

	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
	
	@Autowired
	private StreamBridge streamBridge;
	
	public void publishOrganizationChange(String action, String organizationId){
       logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
	    OrganizationChangeModel change =  new OrganizationChangeModel(
	            OrganizationChangeModel.class.getTypeName(),
	            action,
	            organizationId,
	            UserContext.getCorrelationId()); //seteado en filtro antes de entrar en el endpoint del microservicio
	
	    streamBridge.send("consumer-topic-license", change); //emviamos a topic que estará escuchando licensing
    }
	
}
