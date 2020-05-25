package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Car;
import org.springframework.data.domain.Pageable;

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
     * Find part of cars by date in database.
     *
     * @param date date
     * @return cars list
     */
    List<Car> findAllByDate(LocalDate date, Pageable pageable);

    /**
     * Find car by Id.
     *
     * @param carId car Id.
     * @return car
     */
    Optional<Car> findById(Integer carId);

    /**
     * Persist new car.
     *
     * @param car car for persist.
     * @return persisted car.
     */
    Car create(Car car);

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
    void delete(Integer carId);
}
