package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import com.epam.brest.courses.service_soap.wsdl.GetAllOrderDtosRequest;
import com.epam.brest.courses.service_soap.wsdl.GetAllOrderDtosResponse;
import com.epam.brest.courses.service_soap.wsdl.OrderDtoInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.ArrayList;
import java.util.List;

public class OrderDtoClient extends WebServiceGatewaySupport implements OrderDtoService {

    private final String URL = "http://localhost:8088/ws";

    @Override
    public List<OrderDto> findAllOrdersWithCar() {
        GetAllOrderDtosRequest request = new GetAllOrderDtosRequest();

        GetAllOrderDtosResponse response = (GetAllOrderDtosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/getAllOrderDtosRequest"));

        List<OrderDto> orderDtoList = new ArrayList<>();
        List<OrderDtoInfo> orderDtoInfoList = response.getOrderDtoInfo();

        for (int i = 0; i < orderDtoInfoList.size(); i++) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderDtoInfoList.get(i), orderDto);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
