package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.dto.OrderDto;

import java.util.List;

public interface OrderDtoService {

    /**
     * Find all orders with brand and registration number of the car.
     *
     * @return OrderDto list
     */
    List<OrderDto> findAllOrdersWithCar();
}
