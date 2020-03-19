package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.mapper.CarRowMapper;
import com.epam.brest.courses.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

public class CarDaoJdbc implements CarDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoJdbc.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CarRowMapper carRowMapper =  new CarRowMapper();

    @Value("${car.selectAll}")
    private String SELECT_ALL;

    @Value("${car.selectById}")
    private String SELECT_BY_ID;

    @Value("${car.insert}")
    private String INSERT;

    @Value("${car.update}")
    private String UPDATE;

    @Value("${car.delete}")
    private String DELETE;

    public CarDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Car> findAll() {
        LOGGER.trace("find all cars()");

        return namedParameterJdbcTemplate.query(SELECT_ALL, carRowMapper);
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        LOGGER.trace("find by car id:{}", carId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", carId);

        List<Car> cars =  namedParameterJdbcTemplate.query(SELECT_BY_ID, mapSqlParameterSource, carRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(cars));
    }

    @Override
    public Integer create(Car car) {
        LOGGER.trace("create(car:{})", car);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carBrand", car.getBrand())
                .addValue("carRegisterNumber", car.getRegisterNumber())
                .addValue("carPrice", car.getPrice());

        KeyHolder key = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, key);
        return key.getKey().intValue();
    }

    @Override
    public int update(Car car) {
        LOGGER.trace("update(car:{})", car);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", car.getId())
                .addValue("carBrand", car.getBrand())
                .addValue("carRegisterNumber", car.getRegisterNumber())
                .addValue("carPrice", car.getPrice());

        return namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
    }

    @Override
    public int delete(Integer carId) {
        LOGGER.trace("delete car by id:{})", carId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", carId);

        return namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
    }
}