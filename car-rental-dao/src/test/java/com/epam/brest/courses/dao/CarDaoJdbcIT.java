package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.config.TestConfig;
import com.epam.brest.courses.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:dao.properties")
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
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

    @Test
    void findAllByDate() {
        LocalDate date = LocalDate.of(2020,4,3);
        List<Car> cars = carDao.findAllByDate(date);

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
        Car savedCar = carDao.save(car);

        //when
        Optional<Car> optionalCar = carDao.findById(savedCar.getId());

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
        Car savedCar = carDao.save(car);

        //then
        assertNotNull(savedCar.getId());

        Optional<Car> optionalCar = carDao.findById(savedCar.getId());
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
        Car savedCar = carDao.save(car);
        Optional<Car> optionalCar = carDao.findById(savedCar.getId());

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carDao.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carDao.findById(savedCar.getId());
        assertTrue(optionalCar.isPresent());
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
        Car savedCar = carDao.save(car);;

        //when
        carDao.deleteById(savedCar.getId());

        //then
        //assertTrue(1 == result);
        Optional<Car> optionalCar = carDao.findById(savedCar.getId());
        assertFalse(optionalCar.isPresent());
    }
}