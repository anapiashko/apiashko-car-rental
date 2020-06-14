package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Order;

import java.util.List;

/**
 * Order service.
 */
public interface OrderService {

    /**
     * Find all orders in database.
     *
     * @return order list.
     */
    List<Order> findAll();

    /**
     * Persist new order record.
     *
     * @param order order record.
     * @return persisted order id.
     */
    Order create(Order order);
}