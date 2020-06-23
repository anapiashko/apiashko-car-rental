package com.epam.brest.courses.service_soap.config;

import com.epam.brest.courses.service_soap.CarClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WSConfigClient {
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.epam.brest.courses.service_soap.wsdl");
		return marshaller;
	}
	@Bean
	public CarClient carClient(Jaxb2Marshaller marshaller) {
		CarClient client = new CarClient();
		client.setDefaultUri("http://localhost:8088/ws/car-rental.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
} 