package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.config.TestConfig;
import com.epam.brest.courses.model.dto.OrderDto;
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
class OrderDtoRepositoryIT {

    private final OrderDtoRepository orderDtoRepository;

    @Autowired
    OrderDtoRepositoryIT(OrderDtoRepository orderDtoRepository) {
        this.orderDtoRepository = orderDtoRepository;
    }

    @Test
    void findAllOrdersWithCar() {
        //when
        List<OrderDto> orderDtoList = orderDtoRepository.findAllOrdersWithCar();

        //then
        assertNotNull(orderDtoList);
    }
}