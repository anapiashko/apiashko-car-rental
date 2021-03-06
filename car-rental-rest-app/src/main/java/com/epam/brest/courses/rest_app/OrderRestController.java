package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestController.class);

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create order.
     *
     * @param order order
     * @return id created record
     */
    @PostMapping(value = "/orders")
    public ResponseEntity<Order> create(@RequestBody Order order){
        LOGGER.debug("create order({})",order);

        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }

    /**
     * Delete order.
     *
     * @param id order id
     */
    @DeleteMapping(value = "/orders/{id}", produces = {"application/json"})
    public final ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        LOGGER.debug("delete order({})", id);

        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
