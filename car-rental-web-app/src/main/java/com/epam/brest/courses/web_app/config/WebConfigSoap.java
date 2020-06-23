package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.service_soap.CarClient;
import com.epam.brest.courses.service_soap.CarDtoClient;
import com.epam.brest.courses.service_soap.OrderClient;
import com.epam.brest.courses.service_soap.OrderDtoClient;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.OrderController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("soap")
@Configuration
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
@RequiredArgsConstructor
public class WebConfigSoap {
    @Bean
    public OrderController orderController(){
        return new OrderController(orderClient(), orderDtoClient());
    }

    @Bean
    public CarController carController(){
        return new CarController(carClient(), carDtoClient());
    }

    @Bean
    public OrderClient orderClient() {
        return new OrderClient();
    }

    @Bean
    public CarClient carClient() {
        return new CarClient();
    }

    @Bean
    public CarDtoClient carDtoClient() {
        return new CarDtoClient();
    }

    @Bean
    public OrderDtoClient orderDtoClient() {
        return new OrderDtoClient();
    }

}
