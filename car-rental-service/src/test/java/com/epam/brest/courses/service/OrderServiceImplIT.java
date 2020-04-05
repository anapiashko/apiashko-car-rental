package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class OrderServiceImplIT {

    private final OrderService orderService;

    @Autowired
    OrderServiceImplIT(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void shouldCreateOrder(){

        //given
        Order order = new Order();
        order.setDate("2020-12-20");
        order.setCarId(2);

        //when
        Integer id = orderService.create(order);

        //then
        assertNotNull(id);
    }
}