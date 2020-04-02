package com.epam.brest.courses.service;


import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of order service interface.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(final OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public final List<Order> findAll() {
        LOGGER.debug("find all orders()");

        return orderDao.findAll();
    }

    @Override
    public final Optional<Order> findById(final Integer orderId) {
        LOGGER.debug("find order by id : {}", orderId);

        return orderDao.findById(orderId);
    }

    @Override
    public final Integer create(final Order order) {
        LOGGER.debug("create (order:{})", order);

        return orderDao.create(order);
    }

    @Override
    public final int update(final Order order) {
        LOGGER.debug("update (order:{})", order);

        return orderDao.update(order);
    }

    @Override
    public final int delete(final Integer orderId) {
        LOGGER.debug("delete order by id(orderIdId:{})", orderId);

        return orderDao.delete(orderId);
    }
}
