package com.epam.brest.courses.service;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service.config.TestConfig;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@EntityScan("com.epam.brest.courses.*")
@ContextConfiguration(classes = {TestConfig.class})
class OrderDtoServiceImplIT {

    @Autowired
    private OrderDtoService orderDtoService;

    @Test
    void findAllOrdersWithCar() {
        //when
        List<OrderDto> orderDtoList = orderDtoService.findAllOrdersWithCar();

        //then
        assertNotNull(orderDtoList);
    }
}