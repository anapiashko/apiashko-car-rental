package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_soap.config.WSConfigClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("com.epam.brest.courses.service_soap.*")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WSConfigClient.class)
public class CarClientIT {

    @Autowired
    private CarClient carClient;

    @Test
    public void shouldFindAllCars() {
        List<Car> response = carClient.findAll();

        assertNotNull(response);
    }
    @Test
    public void findAllByDate() {
        LocalDate date = LocalDate.of(2020,4,3);
        List<Car> cars = carClient.findAllByDate(date);

        assertNotNull(cars);
    }

    @Test
    public void findById() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5002 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Car savedCar = carClient.create(car);

        //when
        Optional<Car> optionalCar = carClient.findById(savedCar.getId());

        //then
        assertTrue(optionalCar.isPresent());
        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals(savedCar.getBrand(), optionalCar.get().getBrand());
        assertEquals(savedCar.getRegisterNumber(), optionalCar.get().getRegisterNumber());
        assertEquals(0 ,savedCar.getPrice().compareTo(optionalCar.get().getPrice()));
    }

    @Test
    public void create() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //when
        Car savedCar = carClient.create(car);

        //then
        assertNotNull(savedCar.getId());

        Optional<Car> optionalCar = carClient.findById(savedCar.getId());
        assertTrue(optionalCar.isPresent());

        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals(savedCar.getBrand(), optionalCar.get().getBrand());
        assertEquals(savedCar.getRegisterNumber(), optionalCar.get().getRegisterNumber());
    }

    @Test
    public void update() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("7302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Car savedCar = carClient.create(car);
        Optional<Car> optionalCar = carClient.findById(savedCar.getId());

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carClient.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carClient.findById(savedCar.getId());
        assertTrue(optionalCar.isPresent());
        assertEquals(savedCar.getId(),updatedOptionalCar.get().getId());
        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
    }

    @Test
    public void delete() {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5402 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Car savedCar = carClient.create(car);;

        //when
        carClient.delete(savedCar.getId());

        //then
        Optional<Car> optionalCar = carClient.findById(savedCar.getId());
        assertTrue(optionalCar.get().getId() == 0);
    }
}
