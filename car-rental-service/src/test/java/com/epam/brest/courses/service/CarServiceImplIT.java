package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class CarServiceImplIT {

    private final CarService carService;

    @Autowired
    CarServiceImplIT(CarService carService) {
        this.carService = carService;
    }

    @Test
    void findAll() {
        List<Car> cars = carService.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

}