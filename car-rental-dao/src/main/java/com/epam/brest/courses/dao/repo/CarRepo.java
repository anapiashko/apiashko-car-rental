package com.epam.brest.courses.dao.repo;

import com.epam.brest.courses.dao.CarDao;
import com.epam.brest.courses.model.Car;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile(value = "jpa")
public interface CarRepo extends CrudRepository<Car, Integer>, CarDao {

    @Query(value = "select  * from car where car_id not in (SELECT car_id from order_record where order_date = :date", nativeQuery = true)
    List<Car> findAllByDate(LocalDate date);

    @Query(value = "UPDATE car SET car_brand = :carBrand, car_register_number = :carRegisterNumber, car_price = :carPrice WHERE car_id =:carId", nativeQuery = true)
    int update(Car car);
}
