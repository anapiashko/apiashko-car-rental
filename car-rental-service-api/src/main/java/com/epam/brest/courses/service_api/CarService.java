package com.epam.brest.courses.service_api;


import com.epam.brest.courses.model.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarService {

    /**
     * Find all cars in database.
     *
     * @return cars list.
     */
    List<Car> findAll();


    /**
     * Find all cars by date in database.
     *
     * @param date date
     * @return cars list
     */
    List<Car> findAllByDate(LocalDate date);

    /**
     * Find car by Id.
     *
     * @param carId order Id.
     * @return order
     */
    Optional<Car> findById(Integer carId);

    /**
     * Persist new order record.
     *
     * @param car order record.
     * @return persisted order id.
     */
    Integer create(Car car);

    /**
     * Update car by id.
     *
     * @param car car.
     * @return number of updated cars in the database.
     */
    int update(Car car);

    /**
     * Delete car by id.
     *
     * @param carId car id.
     * @return number of updated records in the database.
     */
    int delete(Integer carId);
}
