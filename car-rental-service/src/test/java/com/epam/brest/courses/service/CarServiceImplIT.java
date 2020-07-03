package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service.config.TestConfig;
import com.epam.brest.courses.service_api.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan("com.epam.brest.courses.*")
@ContextConfiguration(classes = {TestConfig.class})
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
       // assertTrue(cars.size() > 0);
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
        car.setRegisterNumber("5002 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //Integer id = carDao.save(car);
        Car savedCar = carService.create(car);

        //when
        Optional<Car> optionalCar = carService.findById(savedCar.getId());

        //then
        assertTrue(optionalCar.isPresent());
        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals(savedCar.getBrand(), optionalCar.get().getBrand());
        assertEquals(savedCar.getRegisterNumber(), optionalCar.get().getRegisterNumber());
        assertEquals(0 ,savedCar.getPrice().compareTo(optionalCar.get().getPrice()));
    }

    @Test
    void create() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //when
        //Integer id = carDao.save(car);
        Car savedCar = carService.create(car);

        //then
        assertNotNull(savedCar.getId());

        Optional<Car> optionalCar = carService.findById(savedCar.getId());
        assertTrue(optionalCar.isPresent());

        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals(savedCar.getBrand(), optionalCar.get().getBrand());
        assertEquals(savedCar.getRegisterNumber(), optionalCar.get().getRegisterNumber());
    }

    @Test
    void update() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("7302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //Integer id = carDao.save(car);
        Car savedCar = carService.create(car);
        Optional<Car> optionalCar = carService.findById(savedCar.getId());

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carService.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carService.findById(savedCar.getId());
        assertTrue(updatedOptionalCar.isPresent());
        assertEquals(savedCar.getId(),updatedOptionalCar.get().getId());
        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
    }

    @Test
    void delete() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5402 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //Integer id = carDao.save(car);
        Car savedCar = carService.create(car);;

        //when
        carService.delete(savedCar.getId());

        //then
        //assertTrue(1 == result);
        Optional<Car> optionalCar = carService.findById(savedCar.getId());
        assertFalse(optionalCar.isPresent());
    }
}
