package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
class CarDaoJdbcIT {

    private final CarDao carDao;

    @Autowired
    CarDaoJdbcIT(CarDao carDao) {
        this.carDao = carDao;
    }

    @Test
    void findAll() {
        List<Car> cars = carDao.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }
}