package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Order;

/**
 * Order service.
 */
public interface OrderService {
    /**
     * Persist new order record.
     *
     * @param order order record.
     * @return persisted order id.
     */
    Order create(Order order);
}