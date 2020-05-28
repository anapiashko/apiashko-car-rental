package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.dao.OrderRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.FakeService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FakeServiceImpl implements FakeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeServiceImpl.class);

    private final CarRepository carRepository;
    private final OrderRepository orderRepository;
    private Faker faker = new Faker();

    public FakeServiceImpl(CarRepository carRepository, OrderRepository orderRepository) {
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void createCarDataSample(Integer number) {
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

    @Override
    public void createOrderDataSample(Integer number) {
        LOGGER.debug("generate {} order(s)", number);

        for (int i = 0; i < number; i++) {
            LocalDate date = LocalDate.of(faker.number().numberBetween(2018,2021), faker.number().numberBetween(1,12), faker.number().numberBetween(1,30));
            List<Car> cars = carRepository.findAll();
            Integer carId = faker.number().numberBetween(cars.get(0).getId(),cars.get(cars.size()-1).getId());

            if(carRepository.findById(carId).isPresent()) {
                if (!orderRepository.findByDateAndCarId(date, carId).isPresent()) {
                    Order order = new Order();
                    order.setDate(date);
                    order.setCarId(carId);
                    orderRepository.save(order);
                } else {
                    number++;
                }
            }
        }
    }
}
