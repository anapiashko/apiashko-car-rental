package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.time.LocalDate;
import java.util.List;

public class CarDtoClient extends WebServiceGatewaySupport implements CarDtoService {

    private final String URL = "http://localhost:8088/ws";

    @Override
    public List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
