package com.epam.brest.courses.dao;

import com.epam.brest.courses.dao.config.TestConfig;
import com.epam.brest.courses.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EntityScan("com.epam.brest.courses.*")
@ContextConfiguration(classes = {TestConfig.class})
class OrderRepositoryIT {

    private final OrderRepository orderRepository;

    @Autowired
    OrderRepositoryIT(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Test
    public void shouldFindAllOrderRecords() {
        List<Order> orders = orderRepository.findAll();
        assertNotNull(orders);
        //assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldFindOrderById(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,3,4));
        order.setCarId(3);

        //Integer id = orderDao.save(order);
        Order savedOrder = orderRepository.save(order);

        //when
        Optional<Order> optionalOrder = orderRepository.findById(savedOrder.getId());

        //then
        assertTrue(optionalOrder.isPresent());
        assertEquals(savedOrder.getId(), optionalOrder.get().getId());
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
        //Integer id = orderDao.save(order);
        Order savedOrder = orderRepository.save(order);

        //then
        assertNotNull(savedOrder.getId());
    }

    @Test
    public void shouldUpdateOrder(){

        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,11,10));
        order.setCarId(2);

        //Integer id = orderDao.save(order);
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId());

        Optional<Order> orderOptional = orderRepository.findById(savedOrder.getId());
        assertTrue(orderOptional.isPresent());

        orderOptional.get().setDate(LocalDate.of(2020,11,11));
        orderOptional.get().setCarId(3);

        //when
        int result = orderRepository.update(orderOptional.get());

        //then
        assertTrue(1 == result);

        Optional<Order> updatedOptionalOrder = orderRepository.findById(savedOrder.getId());
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

        //Integer id = orderDao.save(order);
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder.getId());

        Optional<Order> optionalOrder = orderRepository.findById(savedOrder.getId());
        assertTrue(optionalOrder.isPresent());
        assertEquals(LocalDate.of(2020,8,10), optionalOrder.get().getDate());
        assertTrue(2 == optionalOrder.get().getCarId());

        //when
        orderRepository.deleteById(savedOrder.getId());

        //then
        //assertTrue(1 == result);

        Optional<Order> deletedOrder = orderRepository.findById(savedOrder.getId());
        assertFalse(deletedOrder.isPresent());
    }
}