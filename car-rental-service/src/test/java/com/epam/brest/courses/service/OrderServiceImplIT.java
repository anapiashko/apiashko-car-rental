package com.epam.brest.courses.service;
//
//import com.epam.brest.courses.Application;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service.config.TestConfig;
import com.epam.brest.courses.service_api.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:dao.properties")
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
class OrderServiceImplIT {

    private final OrderService orderService;

    @Autowired
    OrderServiceImplIT(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    void shouldCreateOrder(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,12,20));
        order.setCarId(2);

        //when
        Integer id = orderService.create(order);

        //then
        assertNotNull(id);
    }
}