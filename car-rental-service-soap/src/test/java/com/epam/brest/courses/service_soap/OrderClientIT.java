package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_soap.config.WSConfigClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("com.epam.brest.courses.service_soap.*")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WSConfigClient.class})
class OrderClientIT {

    @Autowired
    private OrderClient orderClient;

    @Test
    void create() {

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,12,20));
        order.setCarId(2);

        //when
        Order savedOrder = orderClient.create(order);

        //then
        assertNotNull(savedOrder);
    }
}