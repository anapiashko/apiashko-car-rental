package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class OrderDtoServiceRest implements OrderDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public OrderDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<OrderDto> findAllOrdersWithCar() {
        LOGGER.debug("find all orders with their car ()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<OrderDto>) responseEntity.getBody();
    }
}
