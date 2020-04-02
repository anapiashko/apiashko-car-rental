package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/orders")
    public ResponseEntity<Integer> create(@RequestBody Order order){
        LOGGER.debug("create order({})",order);

        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }
}
