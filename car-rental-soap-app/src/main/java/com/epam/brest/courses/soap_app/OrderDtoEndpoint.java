package com.epam.brest.courses.soap_app;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class OrderDtoEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoEndpoint.class);

    private static final String NAMESPACE_URI = "http://epam.com/brest/courses/soap_app";

    private final OrderDtoService orderDtoService;

    @Autowired
    public OrderDtoEndpoint(OrderDtoService orderDtoService) {
        this.orderDtoService = orderDtoService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllOrderDtosRequest")
    @ResponsePayload
    public GetAllOrderDtosResponse getAll() throws DatatypeConfigurationException {
        LOGGER.debug("find all order_dtos ()");

        GetAllOrderDtosResponse response = new GetAllOrderDtosResponse();
        List<OrderDtoInfo> carInfoList = new ArrayList<>();
        List<OrderDto> orderDtoList = orderDtoService.findAllOrdersWithCar();
        for (int i = 0; i < orderDtoList.size(); i++) {
            OrderDtoInfo orderDtoInfo = new OrderDtoInfo();
            BeanUtils.copyProperties(orderDtoList.get(i), orderDtoInfo);
            XMLGregorianCalendar xmlGregorianCalendar =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            orderDtoList.get(i).getDate().toString()
                    );
            orderDtoInfo.setDate(xmlGregorianCalendar);
            carInfoList.add(orderDtoInfo);
        }
        response.getOrderDtoInfo().addAll(carInfoList);

        return response;
    }
}
