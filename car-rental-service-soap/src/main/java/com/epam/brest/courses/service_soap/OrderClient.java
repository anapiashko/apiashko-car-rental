package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class OrderClient extends WebServiceGatewaySupport implements OrderService {
    @Override
    public Order create(Order order) {
        return null;
    }
}
