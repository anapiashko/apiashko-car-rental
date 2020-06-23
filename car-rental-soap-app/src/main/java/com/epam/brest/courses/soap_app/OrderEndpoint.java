package com.epam.brest.courses.soap_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDate;

@Endpoint
public class OrderEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEndpoint.class);

    private static final String NAMESPACE_URI = "http://epam.com/brest/courses/soap_app";

    private final OrderService orderService;

    @Autowired
    public OrderEndpoint(OrderService orderService) {
        this.orderService = orderService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addOrderRequest")
    @ResponsePayload
    public AddOrderResponse create(@RequestPayload AddOrderRequest request) {
        LOGGER.debug("create order({})", request.getOrderInfo());

        AddOrderResponse response = new AddOrderResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        OrderInfo orderInfo = request.getOrderInfo();

        Order order = new Order();
        order.setDate(LocalDate.of(
                orderInfo.getDate().getYear(),
                orderInfo.getDate().getMonth(),
                orderInfo.getDate().getDay()));
        order.setCarId(orderInfo.getCarId());
        Order savedOrder = orderService.create(order);

        if (savedOrder == null) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("Conflict during saving");
            response.setServiceStatus(serviceStatus);
        } else {
            BeanUtils.copyProperties(order, orderInfo);
            response.setOrderInfo(orderInfo);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }
}
