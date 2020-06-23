package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.List;

public class OrderDtoClient extends WebServiceGatewaySupport implements OrderDtoService {
    @Override
    public List<OrderDto> findAllOrdersWithCar() {
        return null;
    }
}
