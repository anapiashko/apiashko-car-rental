package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.OrderDtoRepository;
import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderDtoServiceImpl implements OrderDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoServiceImpl.class);

    private final OrderDtoRepository orderDtoRepository;

    @Autowired
    public OrderDtoServiceImpl(OrderDtoRepository orderDtoRepository) {
        this.orderDtoRepository = orderDtoRepository;
    }

    @Override
    public List<OrderDto> findAllOrdersWithCar() {
        LOGGER.debug("find all orders with their car ()");

        return orderDtoRepository.findAllOrdersWithCar();
    }
}
