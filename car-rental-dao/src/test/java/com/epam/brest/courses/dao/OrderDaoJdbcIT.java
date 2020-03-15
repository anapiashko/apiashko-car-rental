package com.epam.brest.courses.dao;

import com.epam.brest.courses.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath:dao.xml"})
class OrderDaoJdbcIT {

    private final OrderDao orderDao;

    @Autowired
    OrderDaoJdbcIT(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Test
    public void shouldFindAllOrderRecords() {
        List<Order> orders = orderDao.findAll();
        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }
}