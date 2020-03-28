package com.epam.brest.courses.dao;


import com.epam.brest.courses.dao.mapper.CarDtoRowMapper;
import com.epam.brest.courses.model.dto.CarDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class CarDtoDaoJdbc implements CarDtoDao{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CarDtoRowMapper carDtoRowMapper = new CarDtoRowMapper();

    @Value("${carDto.selectAllInPeriod}")
    private String SELECT_ALL_IN_PERIOD;

    public CarDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<CarDto> findAllWithNumberOfOrders(String dateFrom, String dateTo) {
        MapSqlParameterSource mapSqlParameterSource =  new MapSqlParameterSource();
        mapSqlParameterSource.addValue("order_date_from", dateFrom)
                .addValue("order_date_to", dateTo);
        return namedParameterJdbcTemplate.query(SELECT_ALL_IN_PERIOD,mapSqlParameterSource, carDtoRowMapper);
    }
}
