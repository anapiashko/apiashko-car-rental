package com.epam.brest.courses.dao;


import com.epam.brest.courses.dao.mapper.OrderRowMapper;
import com.epam.brest.courses.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;

public class OrderDaoJdbc implements OrderDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final OrderRowMapper orderRowMapper = new OrderRowMapper();

    @Value("${order.selectAll}")
    private String SELECT_ALL;

    @Value("${order.selectById}")
    private String SELECT_BY_ID;

    @Value("${order.insert}")
    private String INSERT;

    @Value("${order.update}")
    private String UPDATE;

    @Value("${order.delete}")
    private String DELETE;

    @Autowired
    public OrderDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Order> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL, orderRowMapper);
    }

    @Override
    public Optional<Order> findById(Integer orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", orderId);

        List<Order> orders = namedParameterJdbcTemplate.query(SELECT_BY_ID, mapSqlParameterSource, orderRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(orders));
    }

    @Override
    public Integer create(Order order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderDate", order.getDate())
                .addValue("carId", order.getCarId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
        Integer id = keyHolder.getKey().intValue();
        return keyHolder.getKey().intValue();
    }

    @Override
    public int update(Order order) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", order.getId())
                .addValue("orderDate", order.getDate())
                .addValue("carId", order.getCarId());

        return namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
    }

    @Override
    public int delete(Integer orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", orderId);
        return namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
    }
}
