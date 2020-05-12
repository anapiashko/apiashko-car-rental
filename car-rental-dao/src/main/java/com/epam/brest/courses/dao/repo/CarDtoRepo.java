package com.epam.brest.courses.dao.repo;

import com.epam.brest.courses.dao.CarDtoDao;
import com.epam.brest.courses.model.dto.CarDto;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@Profile(value = "jpa")
public interface CarDtoRepo extends CrudRepository<CarDto, Integer>, CarDtoDao {

    @Query(value = "select car.car_id, car.car_brand, car.car_register_number, t.count from(" +
            "  select car_id, count(car_id) as count from (" +
            "  select * from order_record where order_date between :dateFrom and :dateTo) group by car_id) as t" +
            "   left join car on t.car_id = car.car_id;", nativeQuery = true)
    List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo);
}
