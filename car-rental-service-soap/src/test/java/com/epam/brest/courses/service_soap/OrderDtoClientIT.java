package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_soap.config.WSConfigClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("com.epam.brest.courses.service_soap.*")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WSConfigClient.class})
class OrderDtoClientIT {

    @Autowired
    private OrderDtoClient orderDtoClient;

    @Test
    void findAllOrdersWithCar() {
        //given

        //when
        List<OrderDto> orderDtoList = orderDtoClient.findAllOrdersWithCar();

        //then
        assertNotNull(orderDtoList);
    }
}