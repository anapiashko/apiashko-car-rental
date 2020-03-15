package com.epam.brest.courses.dao.mapper;

import com.epam.brest.courses.model.Car;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet resultSet, int i) throws SQLException {
        Car car =  new Car();
        car.setId(resultSet.getInt("car_id"));
        car.setBrand(resultSet.getString("car_brand"));
        car.setRegisterNumber(resultSet.getString("car_register_number"));
        car.setPrice(resultSet.getBigDecimal("car_price"));
        return car;
    }
}
