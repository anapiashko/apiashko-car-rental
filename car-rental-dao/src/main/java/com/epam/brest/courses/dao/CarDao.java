package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarDao {
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
     * @param carId car Id.
     * @return car
     */
    Optional<Car> findById(Integer carId);

    /**
     * Persist new car record.
     *
     * @param car car record.
     * @return persisted car id.
     */
    Car save(Car car);

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
     */
    void deleteById(Integer carId);

}
