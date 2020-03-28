package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.dto.CarDto;

import java.util.List;

public interface CarDtoService {

    /**
     * Get all cars with number of orders in period.
     *
     * @param dateFrom first date
     * @param dateTo last date
     * @return carDto list
     */
    List<CarDto> findAllWithNumberOfOrders(String dateFrom, String dateTo);
}
