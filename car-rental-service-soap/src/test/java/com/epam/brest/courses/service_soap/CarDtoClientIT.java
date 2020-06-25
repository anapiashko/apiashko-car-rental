package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_soap.config.WSConfigClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ComponentScan("com.epam.brest.courses.service_soap.*")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WSConfigClient.class})
class CarDtoClientIT {

    @Autowired
    private CarDtoClient carDtoClient;

    @Test
    void findAllWithNumberOfOrders() {
        //given
        LocalDate dateFrom = LocalDate.of(2020,1,1);
        LocalDate dateTo = LocalDate.of(2020,1,15);

        //when
        List<CarDto> list = carDtoClient.findAllWithNumberOfOrders(dateFrom, dateTo);

        //then
        assertNotNull(list);
    }
}