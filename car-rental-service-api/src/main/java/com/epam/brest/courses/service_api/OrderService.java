package com.epam.brest.courses.service_api;


import com.epam.brest.courses.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * Find all order reports.
     *
     * @return orders list.
     */
    List<Order> findAll();

    /**
     * Find order by Id.
     *
     * @param orderId order Id.
     * @return order
     */
    Optional<Order> findById(Integer orderId);

    /**
     * Persist new order record.
     *
     * @param order order record.
     * @return persisted order id.
     */
    Integer create(Order order);

    /**
     * Update order record.
     *
     * @param order order.
     * @return number of updated records in the database.
     */
    int update(Order order);

    /**
     * Delete order record.
     *
     * @param orderId order id.
     * @return number of updated records in the database.
     */
    int delete(Integer orderId);
}
