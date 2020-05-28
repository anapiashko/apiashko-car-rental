package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.FakeCarService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FakeCarServiceImpl implements FakeCarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeCarServiceImpl.class);
    private final CarRepository carRepository;
    private Faker faker = new Faker();

    public FakeCarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void createSampleData(Integer number) {
        LOGGER.debug("generate {} car(s)", number);

        for (int i = 0; i < number; i++) {
            String brand = faker.space().nasaSpaceCraft();
            String registerNumber = faker.regexify("[0-9]{4} AB-[1-7]{1}");
            BigDecimal price = new BigDecimal(faker.commerce().price(50.0, 1000.0));

            if (!carRepository.findByRegisterNumber(registerNumber).isPresent()) {
                Car car = new Car();
                car.setBrand(brand);
                car.setRegisterNumber(registerNumber);
                car.setPrice(price);
                carRepository.save(car);
            } else {
                number++;
            }
        }
    }
}
