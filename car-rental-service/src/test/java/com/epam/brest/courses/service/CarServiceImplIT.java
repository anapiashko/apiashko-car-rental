package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service.config.TestConfig;
import com.epam.brest.courses.service_api.CarService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
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

    @Test
    void findAllByDate() {
        LocalDate date = LocalDate.of(2020,4,3);
        List<Car> cars = carService.findAllByDate(date);

        assertNotNull(cars);
    }

    @Test
    void findById() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("7702 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);

        //when
        Optional<Car> optionalCar = carService.findById(id);

        //then
        Assert.assertTrue(optionalCar.isPresent());
        assertEquals(id,optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("7702 AB-1", optionalCar.get().getRegisterNumber());
        assertEquals(0 ,BigDecimal.valueOf(150).compareTo(optionalCar.get().getPrice()));
    }

    @Test
    void create() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5309 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //when
        Integer id = carService.create(car);

        //then
        assertNotNull(id);

        Optional<Car> optionalCar = carService.findById(id);
        assertTrue(optionalCar.isPresent());

        assertEquals(id,optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("5309 AB-1", optionalCar.get().getRegisterNumber());
    }

    @Test
    void update() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);
        Optional<Car> optionalCar = carService.findById(id);

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carService.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carService.findById(id);
        assertTrue(optionalCar.isPresent());
        assertEquals(id,updatedOptionalCar.get().getId());
        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
    }

    @Test
    void delete() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5102 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);

        //when
        int result = carService.delete(id);

        //then
        assertTrue(1 == result);
        Optional<Car> optionalCar = carService.findById(id);
        assertFalse(optionalCar.isPresent());
    }
}
