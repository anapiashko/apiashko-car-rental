package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> findAll() {
        LOGGER.debug("find all cars()");

        return carRepository.findAll();
    }

    @Override
    public List<Car> findAllByDate(LocalDate date) {
        LOGGER.debug("find all cars by date:{}", date);

        return carRepository.findAllByDate(date);
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("find car by id : {}", carId);

        return carRepository.findById(carId);
    }

    @Override
    public Car create(Car car) {
        LOGGER.debug("create (car:{})", car);

        if (carRepository.findByRegisterNumber(car.getRegisterNumber()).isPresent()) {
            throw new IllegalArgumentException("Car with the same registration number already exists in DB.");
        }

        return carRepository.save(car);
    }

    @Override
    public List<Car> saveAll(List<Car> cars){
        LOGGER.debug("save all cars from list ()");

        for(Car car: cars){
            if (carRepository.findByRegisterNumber(car.getRegisterNumber()).isPresent()) {
                throw new IllegalArgumentException("Car with the same registration number already exists : " + car.getRegisterNumber());
            }
        }

        return (List<Car>) carRepository.saveAll(cars);
    }

    @Override
    public int update(Car car) {
        LOGGER.debug("update (car:{})", car);

        Optional<Car> optionalCar = carRepository.findByRegisterNumber(car.getRegisterNumber());

        if (optionalCar.isPresent()) {
            if (!optionalCar.get().getId().equals(car.getId())) {
                throw new IllegalArgumentException("Car with the same registration number already exists in DB.");
            }
        }

        return carRepository.update(car);
    }

    @Override
    public void delete(Integer carId) {
        LOGGER.debug("delete car by id(carId:{})", carId);

        carRepository.deleteById(carId);
    }
}
