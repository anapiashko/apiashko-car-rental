package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.OrderRepository;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of order service interface.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        LOGGER.debug("find all orders()");

        return orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        LOGGER.debug("create (order:{})", order);

        return orderRepository.save(order);
    }

    @Override
    public void delete(Integer orderId) {
        LOGGER.debug("delete order by id(orderId:{})", orderId);

        orderRepository.deleteById(orderId);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("delete all orders ()");

        orderRepository.deleteAll();
    }
}
