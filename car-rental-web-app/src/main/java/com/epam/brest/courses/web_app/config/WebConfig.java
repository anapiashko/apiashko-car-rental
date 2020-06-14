package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.service_rest.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan(basePackages = {"com.epam.brest.courses.*"})
@RequiredArgsConstructor
public class WebConfig {

    private final ServiceProperties props;

//    @Bean
//    public OrderController orderController() {
//        return new OrderController(orderServiceRest(), orderDtoServiceRest(), xmlOrderServiceRest());
//    }

//    @Bean
//    public CarController carController() {
//        return new CarController(carServiceRest(), carDtoServiceRest(), xmlCarServiceRest());
//    }

    @Bean
    public OrderServiceRest orderServiceRest() {
        return new OrderServiceRest(props.getBaseUrl() + "/" + props.getOrderUrl(), restTemplate());
    }

    @Bean
    public XmlCarServiceRest xmlCarServiceRest() {
        return new XmlCarServiceRest(props.getBaseUrl() + "/" + props.getCarUrl() + "/" + props.getXmlUrl(), restTemplate());
    }

    @Bean
    public XmlOrderServiceRest xmlOrderServiceRest() {
        return new XmlOrderServiceRest(props.getBaseUrl() + "/" + props.getOrderUrl() + "/" + props.getXmlUrl(), restTemplate());
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
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        formHttpMessageConverter.addSupportedMediaTypes(MediaType.MULTIPART_FORM_DATA);
        restTemplate.getMessageConverters().add(formHttpMessageConverter);
        return restTemplate;
    }
}
