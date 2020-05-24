package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.dto.CarDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarDtoRepository extends CrudRepository<CarDto, Integer> {

    @Query(value = "select car.id, car.brand, car.register_number, t.number_orders from(" +
            "  select car_id, count(car_id) as number_orders from (" +
            "  select * from order_record where order_date between :dateFrom and :dateTo) group by car_id) as t" +
            "   left join car on t.car_id = car.id", nativeQuery = true)
    List<CarDto> findAllWithNumberOfOrders(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}