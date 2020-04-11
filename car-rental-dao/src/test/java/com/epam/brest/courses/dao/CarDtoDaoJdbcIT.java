package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
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