package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.service_rest.*;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.OrderController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ServiceProperties props;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public OrderController orderController() {
        return new OrderController(orderServiceRest(), orderDtoServiceRest());
    }

    @Bean
    public CarController carController() {
        return new CarController(carServiceRest(), carDtoServiceRest(), excelServiceRest());
    }

    @Bean
    public OrderServiceRest orderServiceRest() {
        return new OrderServiceRest(props.getBaseUrl() + "/" + props.getOrderUrl(), restTemplate());
    }

    @Bean
    public ExcelServiceRest excelServiceRest() {
        return new ExcelServiceRest(props.getBaseUrl() + "/" + props.getCarUrl() + "/" + props.getImportExcelUrl(), restTemplate());
    }

    @Bean
    public CarServiceRest carServiceRest() {
        return new CarServiceRest(props.getBaseUrl() + "/" + props.getCarUrl(), restTemplate());
    }

    @Bean
    public CarDtoServiceRest carDtoServiceRest() {
        return new CarDtoServiceRest(props.getBaseUrl() + "/" + props.getCarDtoUrl(), restTemplate());
    }

    @Bean
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
