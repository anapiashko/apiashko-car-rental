package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.config.TestConfig;
import com.epam.brest.courses.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {TestConfig.class})
class CarDaoRepoIT {

    private final CarDao carDao;

    @Autowired
    CarDaoRepoIT(CarDao carDao) {
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

//    @Test
//    void findById() {
//        //given
//        Car car = new Car();
//        car.setBrand("Honda");
//        car.setRegisterNumber("5002 AB-1");
//        car.setPrice(BigDecimal.valueOf(150));
//
//        Integer id = carDao.create(car);
//
//        //when
//        Optional<Car> optionalCar = carDao.findById(id);
//
//        //then
//        assertTrue(optionalCar.isPresent());
//        assertEquals(id,optionalCar.get().getId());
//        assertEquals("Honda", optionalCar.get().getBrand());
//        assertEquals("5002 AB-1", optionalCar.get().getRegisterNumber());
//        assertEquals(0 ,BigDecimal.valueOf(150).compareTo(optionalCar.get().getPrice()));
//    }
//
//    @Test
//    void create() {
//        //given
//        Car car = new Car();
//        car.setBrand("Honda");
//        car.setRegisterNumber("5302 AB-1");
//        car.setPrice(BigDecimal.valueOf(150));
//
//        //when
//        Integer id = carDao.create(car);
//
//        //then
//        assertNotNull(id);
//
//        Optional<Car> optionalCar = carDao.findById(id);
//        assertTrue(optionalCar.isPresent());
//
//        assertEquals(id,optionalCar.get().getId());
//        assertEquals("Honda", optionalCar.get().getBrand());
//        assertEquals("5302 AB-1", optionalCar.get().getRegisterNumber());
//    }
//
//    @Test
//    void update() {
//        //given
//        Car car = new Car();
//        car.setBrand("Honda");
//        car.setRegisterNumber("7302 AB-1");
//        car.setPrice(BigDecimal.valueOf(150));
//
//        Integer id = carDao.create(car);
//        Optional<Car> optionalCar = carDao.findById(id);
//
//        assertTrue(optionalCar.isPresent());
//        optionalCar.get().setBrand("HONDA");
//        optionalCar.get().setRegisterNumber("7350 AB-1");
//        optionalCar.get().setPrice(BigDecimal.valueOf(200));
//
//        //when
//        int result = carDao.update(optionalCar.get());
//
//        //then
//        assertTrue(1 == result);
//        Optional<Car> updatedOptionalCar = carDao.findById(id);
//        assertTrue(optionalCar.isPresent());
//        assertEquals(id,updatedOptionalCar.get().getId());
//        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
//        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
//        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
//    }
//
//    @Test
//    void delete() {
//        //given
//        Car car = new Car();
//        car.setBrand("Honda");
//        car.setRegisterNumber("5402 AB-1");
//        car.setPrice(BigDecimal.valueOf(150));
//
//        Integer id = carDao.create(car);
//
//        //when
//        int result = carDao.delete(id);
//
//        //then
//        assertTrue(1 == result);
//        Optional<Car> optionalCar = carDao.findById(id);
//        assertFalse(optionalCar.isPresent());
//    }
}