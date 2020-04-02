package com.epam.brest.courses.dao.mapper;

import com.epam.brest.courses.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for order entity.
 */
public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public final Order mapRow(final ResultSet resultSet, final int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("order_record_id"));
        order.setDate(resultSet.getString("order_date"));
        order.setCarId(resultSet.getInt("car_id"));
        return order;
    }
}
