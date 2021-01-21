package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import java.util.List;

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

    @Override
    public List<Order> findAll() {
        return null;
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

    /**
     * Delete order from db.
     *
     * @param orderId order id.
     */
    @Override
    public void delete(Integer orderId) {
        LOGGER.debug("delete({})", orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url+"/"+orderId, HttpMethod.DELETE, entity, Integer.class);
    }

    @Override
    public void deleteAll() {

    }
}
