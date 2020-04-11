package com.epam.brest.courses.dao;


import com.epam.brest.courses.dao.mapper.CarDtoRowMapper;
import com.epam.brest.courses.model.dto.CarDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.List;

public class CarDtoDaoJdbc implements CarDtoDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoDaoJdbc.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private CarDtoRowMapper carDtoRowMapper = new CarDtoRowMapper();

    @Value("${carDto.selectAllInPeriod}")
    private String SELECT_ALL_IN_PERIOD;

    public CarDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo) {
        LOGGER.debug("find all with number of orders (dateFrom = {}, dateTo = {})", dateFrom, dateTo);

        MapSqlParameterSource mapSqlParameterSource =  new MapSqlParameterSource();
        mapSqlParameterSource.addValue("order_date_from", dateFrom)
                .addValue("order_date_to", dateTo);
        return namedParameterJdbcTemplate.query(SELECT_ALL_IN_PERIOD,mapSqlParameterSource, carDtoRowMapper);
    }
}
