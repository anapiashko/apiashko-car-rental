package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findAll();

    @Modifying
    @Query(value = "UPDATE order_record SET order_date = :#{#order.date}, car_id = :#{#order.carId} WHERE id = :#{#order.id}", nativeQuery = true)
    int update(@Param("order") Order order);
}