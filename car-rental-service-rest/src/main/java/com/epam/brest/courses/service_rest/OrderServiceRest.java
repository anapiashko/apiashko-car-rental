package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Order REST service.
 */
public class OrderServiceRest implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public OrderServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Create order.
     *
     * @param order order record.
     * @return id created record.
     */
    public Order create(Order order){
        LOGGER.debug("create({})", order);

        ResponseEntity responseEntity = restTemplate.postForEntity(url,order,Order.class);
        return (Order) responseEntity.getBody();
    }
}
