package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.mapper.CarRowMapper;
import com.epam.brest.courses.model.Car;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

public class CarDaoJdbc implements CarDao {

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
        return namedParameterJdbcTemplate.query(SELECT_ALL, carRowMapper);
    }

    @Override
    public Optional<Car> findById(Integer carId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", carId);

        List<Car> cars =  namedParameterJdbcTemplate.query(SELECT_BY_ID, mapSqlParameterSource, carRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(cars));
    }

    @Override
    public Integer create(Car car) {

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

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", car.getId())
                .addValue("carBrand", car.getBrand())
                .addValue("carRegisterNumber", car.getRegisterNumber())
                .addValue("carPrice", car.getPrice());

        return namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
    }

    @Override
    public int delete(Integer carId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("carId", carId);

        return namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
    }
}
