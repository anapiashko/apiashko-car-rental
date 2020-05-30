package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.OrderDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDtoRepository extends CrudRepository<OrderDto, Integer> {

    @Query(value = "select  c.id, c.brand, c.register_number, order_date from order_record inner join car c on order_record.car_id = c.id", nativeQuery = true)
    List<OrderDto> findAllOrdersWithCar();
}
