package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.CarDao;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        return carDao.findById(carId);
    }

    @Override
    public Integer create(Car car) {
        return carDao.create(car);
    }

    @Override
    public int update(Car car) {
        return carDao.update(car);
    }

    @Override
    public int delete(Integer carId) {
        return carDao.delete(carId);
    }
}
