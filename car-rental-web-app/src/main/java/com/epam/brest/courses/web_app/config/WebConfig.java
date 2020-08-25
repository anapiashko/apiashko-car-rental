package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.service_rest.CarDtoServiceRest;
import com.epam.brest.courses.service_rest.CarServiceRest;
import com.epam.brest.courses.service_rest.OrderDtoServiceRest;
import com.epam.brest.courses.service_rest.OrderServiceRest;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.OrderController;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
@RequiredArgsConstructor
public class WebConfig {

    private final ServiceProperties props;

    @Bean
    public OrderController orderController(){
        return new OrderController(orderServiceRest(), orderDtoServiceRest());
    }

    @Bean
    public CarController carController(){
        return new CarController(carServiceRest(), carDtoServiceRest());
    }

    @Bean
    @RefreshScope
    public OrderServiceRest orderServiceRest() {
        return new OrderServiceRest(props.getBaseUrl() + "/" + props.getOrderUrl(), restTemplate());
    }

    @Bean
    @RefreshScope
    public CarServiceRest carServiceRest() {
        return new CarServiceRest(props.getBaseUrl() + "/" + props.getCarUrl(), restTemplate());
    }

    @Bean
    @RefreshScope
    public CarDtoServiceRest carDtoServiceRest() {
        return new CarDtoServiceRest(props.getBaseUrl() + "/" + props.getCarDtoUrl(), restTemplate());
    }

    @Bean
    @RefreshScope
    public OrderDtoServiceRest orderDtoServiceRest() {
        return new OrderDtoServiceRest(props.getBaseUrl() + "/" + props.getOrderDtoUrl(), restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }
}
