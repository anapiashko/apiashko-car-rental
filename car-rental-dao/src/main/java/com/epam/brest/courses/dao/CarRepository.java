package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Car;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {

    List<Car> findAll();

    Optional<Car> findByRegisterNumber(String registerNumber);

    @Query(value = "select  * from car where id not in (SELECT car_id from order_record where order_date = :date)", nativeQuery = true)
    List<Car> findAllByDate(@Param("date") LocalDate date);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE car SET brand = :#{#forUpdate.brand}, price = :#{#forUpdate.price}, register_number = :#{#forUpdate.registerNumber}"
           + " WHERE id =:#{#forUpdate.id}", nativeQuery = true)
    int update(@Param("forUpdate") Car car);
}