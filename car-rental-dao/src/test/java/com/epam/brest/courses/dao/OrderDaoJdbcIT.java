package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.config.TestConfig;
import com.epam.brest.courses.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:dao.properties")
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
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

    @Test
    public void shouldFindOrderById(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,3,4));
        order.setCarId(3);

        Integer id = orderDao.create(order);

        //when
        Optional<Order> optionalOrder = orderDao.findById(id);

        //then
        assertTrue(optionalOrder.isPresent());
        assertEquals(id, optionalOrder.get().getId());
        assertEquals(order.getDate(),optionalOrder.get().getDate());
        assertEquals(order.getCarId(), optionalOrder.get().getCarId());
    }

    @Test
    public void shouldCreateOrder(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,12,20));
        order.setCarId(2);

        //when
        Integer id = orderDao.create(order);

        //then
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateOrder(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,11,10));
        order.setCarId(2);

        Integer id = orderDao.create(order);
        assertNotNull(id);

        Optional<Order> orderOptional = orderDao.findById(id);
        assertTrue(orderOptional.isPresent());

        orderOptional.get().setDate(LocalDate.of(2020,11,11));
        orderOptional.get().setCarId(3);

        //when
        int result = orderDao.update(orderOptional.get());

        //then
        assertTrue(1 == result);

        Optional<Order> updatedOptionalOrder = orderDao.findById(id);
        assertTrue(updatedOptionalOrder.isPresent());
        assertEquals(LocalDate.of(2020,11,11), updatedOptionalOrder.get().getDate());
        assertTrue(3 == updatedOptionalOrder.get().getCarId());
    }

    @Test
    public void shouldDeleteOrder(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,8,10));
        order.setCarId(2);

        Integer id = orderDao.create(order);
        assertNotNull(id);

        Optional<Order> optionalOrder = orderDao.findById(id);
        assertTrue(optionalOrder.isPresent());
        assertEquals(LocalDate.of(2020,8,10), optionalOrder.get().getDate());
        assertTrue(2 == optionalOrder.get().getCarId());

        //when
        int result = orderDao.delete(id);

        //then
        assertTrue(1 == result);

        Optional<Order> deletedOrder = orderDao.findById(id);
        assertFalse(deletedOrder.isPresent());
    }
}