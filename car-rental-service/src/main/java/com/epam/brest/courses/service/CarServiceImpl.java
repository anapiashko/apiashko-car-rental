package com.epam.brest.courses.service;

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

    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> findAll() {
        LOGGER.debug("find all cars()");

        return carDao.findAll();
    }

    @Override
    public List<Car> findAllByDate(LocalDate date){
        LOGGER.debug("find all cars by date:{}", date);

        return carDao.findAllByDate(date);
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("find car by id : {}", carId);

        return carDao.findById(carId);
    }

    @Override
    public Integer create(Car car) {
        LOGGER.debug("create (car:{})", car);

        return carDao.create(car);
    }

    @Override
    public int update(Car car) {
        LOGGER.debug("update (car:{})", car);

        return carDao.update(car);
    }

    @Override
    public int delete(Integer carId) {
        LOGGER.debug("delete car by id(carId:{})", carId);

        return carDao.delete(carId);
    }
}
