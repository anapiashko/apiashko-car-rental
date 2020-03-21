package com.epam.brest.courses.service;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-service.xml", "classpath:dao.xml"})
class OrderServiceImplIT {

    private final OrderService orderService;

    @Autowired
    OrderServiceImplIT(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    void shouldFindAllOrderRecords() {
        List<Order> orders =  orderService.findAll();

        assertNotNull(orders);
        assertTrue(orders.size() > 0);
    }

    @Test
    public void shouldFindOrderById(){

        //given
        Order order = new Order();
        order.setDate("2020-03-04");
        order.setCarId(3);

        Integer id = orderService.create(order);

        //when
        Optional<Order> optionalOrder = orderService.findById(id);

        //then
        Assert.assertTrue(optionalOrder.isPresent());
        assertEquals(id, optionalOrder.get().getId());
        assertEquals(order.getDate(),optionalOrder.get().getDate());
        assertEquals(order.getCarId(), optionalOrder.get().getCarId());
    }

    @Test
    public void shouldCreateOrder(){

        //given
        Order order = new Order();
        order.setDate("2020-12-20");
        order.setCarId(2);

        //when
        Integer id = orderService.create(order);

        //then
        assertNotNull(id);
    }

    @Test
    public void shouldUpdateOrder(){

        //given
        Order order = new Order();
        order.setDate("2020-11-10");
        order.setCarId(2);

        Integer id = orderService.create(order);
        assertNotNull(id);

        Optional<Order> orderOptional = orderService.findById(id);
        assertTrue(orderOptional.isPresent());

        orderOptional.get().setDate("2020-11-11");
        orderOptional.get().setCarId(3);

        //when
        int result = orderService.update(orderOptional.get());

        //then
        assertTrue(1 == result);

        Optional<Order> updatedOptionalOrder = orderService.findById(id);
        assertTrue(updatedOptionalOrder.isPresent());
        assertEquals("2020-11-11", updatedOptionalOrder.get().getDate());
        assertTrue(3 == updatedOptionalOrder.get().getCarId());
    }

    @Test
    public void shouldDeleteOrder(){

        //given
        Order order = new Order();
        order.setDate("2020-08-10");
        order.setCarId(2);

        Integer id = orderService.create(order);
        assertNotNull(id);

        Optional<Order> optionalOrder = orderService.findById(id);
        assertTrue(optionalOrder.isPresent());
        assertEquals("2020-08-10", optionalOrder.get().getDate());
        assertTrue(2 == optionalOrder.get().getCarId());

        //when
        int result = orderService.delete(id);

        //then
        assertTrue(1 == result);

        Optional<Order> deletedOrder = orderService.findById(id);
        assertFalse(deletedOrder.isPresent());
    }
}