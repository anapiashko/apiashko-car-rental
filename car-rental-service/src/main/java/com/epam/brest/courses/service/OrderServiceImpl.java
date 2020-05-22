package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of order service interface.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Integer create(Order order) {
        LOGGER.debug("create (order:{})", order);

        return orderDao.create(order);
    }
}
