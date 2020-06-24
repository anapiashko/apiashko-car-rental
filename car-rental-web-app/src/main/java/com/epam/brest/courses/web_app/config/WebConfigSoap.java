package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.service_soap.CarClient;
import com.epam.brest.courses.service_soap.CarDtoClient;
import com.epam.brest.courses.service_soap.OrderClient;
import com.epam.brest.courses.service_soap.OrderDtoClient;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.OrderController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("soap")
@Configuration
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
@RequiredArgsConstructor
public class WebConfigSoap {

    private CarClient carClient;
    private CarDtoClient carDtoClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private OrderDtoClient orderDtoClient;

    @Autowired
    public WebConfigSoap(CarClient carClient, CarDtoClient carDtoClient) {
        this.carClient = carClient;
        this.carDtoClient = carDtoClient;
    }

    @Bean
    public OrderController orderController(){
        return new OrderController(orderClient, orderDtoClient);
    }

    @Bean
    public CarController carController(){
        return new CarController(carClient, carDtoClient);
    }

}
