package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("rest")
@RestController
public class OrderDtoRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoRestController.class);

    private final OrderDtoService orderDtoService;

    @Autowired
    public OrderDtoRestController(OrderDtoService orderDtoService) {
        this.orderDtoService = orderDtoService;
    }

    @GetMapping(value = "/order_dtos")
    public List<OrderDto> getAllOrderDtos(){
        LOGGER.debug("find all orders with their car ()");

        return orderDtoService.findAllOrdersWithCar();
    }
}
