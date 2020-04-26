package com.epam.brest.courses.dao.mapper;

import com.epam.brest.courses.model.dto.CarDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDtoRowMapper implements RowMapper<CarDto> {
    @Override
    public CarDto mapRow(ResultSet resultSet, int i) throws SQLException {
        CarDto carDto = new CarDto();
        carDto.setId(resultSet.getInt("car_id"));
        carDto.setBrand(resultSet.getString("car_brand"));
        carDto.setRegisterNumber(resultSet.getString("car_register_number"));
        carDto.setNumberOrders(resultSet.getInt("count"));
        return carDto;
    }
}
