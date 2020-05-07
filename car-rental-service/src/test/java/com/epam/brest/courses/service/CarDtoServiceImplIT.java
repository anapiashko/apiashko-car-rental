package com.epam.brest.courses.service;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service.config.TestConfig;
import com.epam.brest.courses.service_api.CarDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
class CarDtoServiceImplIT {

    private final CarDtoService carDtoService;

    @Autowired
    CarDtoServiceImplIT(CarDtoService carDtoService) {
        this.carDtoService = carDtoService;
    }

    @Test
    void findAllWithNumberOfOrders() {
        //given
        LocalDate dateFrom = LocalDate.of(2020,1,1);
        LocalDate dateTo = LocalDate.of(2020,1,15);

        //when
        List<CarDto> list = carDtoService.findAllWithNumberOfOrders(dateFrom, dateTo);

        //then
        assertNotNull(list);
    }
}