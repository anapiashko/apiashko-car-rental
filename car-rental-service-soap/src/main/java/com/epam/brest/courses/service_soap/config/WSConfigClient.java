package com.epam.brest.courses.service_soap.config;

import com.epam.brest.courses.service_soap.CarClient;
import com.epam.brest.courses.service_soap.CarDtoClient;
import com.epam.brest.courses.service_soap.OrderClient;
import com.epam.brest.courses.service_soap.OrderDtoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WSConfigClient {

	private String defaultUri = "http://localhost:8088/ws/car-rental.wsdl";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.epam.brest.courses.service_soap.wsdl");
		return marshaller;
	}
	@Bean
	public CarClient carClient(Jaxb2Marshaller marshaller) {
		CarClient client = new CarClient();
		client.setDefaultUri(defaultUri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	@Bean
	public CarDtoClient carDtoClient(Jaxb2Marshaller marshaller){
		CarDtoClient client = new CarDtoClient();
		client.setDefaultUri(defaultUri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	@Bean
	public OrderClient orderClient(Jaxb2Marshaller marshaller){
		OrderClient client = new OrderClient();
		client.setDefaultUri(defaultUri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	@Bean
	public OrderDtoClient orderDtoClient(Jaxb2Marshaller marshaller){
		OrderDtoClient client = new OrderDtoClient();
		client.setDefaultUri(defaultUri);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
} 