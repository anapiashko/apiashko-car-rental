package com.epam.brest.courses.dao.jdbc;

import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.dao.mapper.OrderRowMapper;
import com.epam.brest.courses.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile(value = "jdbc")
@PropertySource("classpath:dao.properties")
public class OrderDaoJdbc implements OrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoJdbc.class);

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
    public OrderDaoJdbc(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public final List<Order> findAll() {
        LOGGER.trace("find all orders()");

        return namedParameterJdbcTemplate.query(SELECT_ALL, orderRowMapper);
    }

    @Override
    public final Optional<Order> findById(final Integer orderId) {
        LOGGER.trace("find by orderId:{}", orderId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", orderId);

        List<Order> orders = namedParameterJdbcTemplate.query(SELECT_BY_ID, mapSqlParameterSource, orderRowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(orders));
    }

    @Override
    public final Order save(final Order order) {
        LOGGER.trace("create(order:{})", order);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderDate", order.getDate())
                .addValue("carId", order.getCarId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
        Integer id = keyHolder.getKey().intValue();
        return findById(id).isPresent() ? findById(id).get() : null;
    }

    @Override
    public final int update(final Order order) {
        LOGGER.trace("update(order:{})", order);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", order.getId())
                .addValue("orderDate", order.getDate())
                .addValue("carId", order.getCarId());

        return namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
    }

    @Override
    public final void deleteById(final Integer orderId) {
        LOGGER.trace("delete order by id:{})", orderId);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderRecordId", orderId);
        namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
    }
}
