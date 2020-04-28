package com.epam.brest.courses.dao;

import com.epam.brest.courses.Application;
import com.epam.brest.courses.model.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class CarDtoDaoJdbcIT {

    private final CarDtoDao carDtoDao;

    @Autowired
    CarDtoDaoJdbcIT(CarDtoDao carDtoDao) {
        this.carDtoDao = carDtoDao;
    }

    @Test
    void findAllWithNumberOfOrders() {
        //given
        LocalDate dateFrom = LocalDate.of(2020,1,1);
        LocalDate dateTo = LocalDate.of(2020,1,15);

        //when
        List<CarDto> carDtoList = carDtoDao.findAllWithNumberOfOrders(dateFrom, dateTo);

        //then
        assertNotNull(carDtoList);
    }
}
