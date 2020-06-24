package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_api.OrderDtoService;
import com.epam.brest.courses.service_soap.wsdl.GetAllOrderDtosRequest;
import com.epam.brest.courses.service_soap.wsdl.GetAllOrderDtosResponse;
import com.epam.brest.courses.service_soap.wsdl.OrderDtoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDtoClient extends WebServiceGatewaySupport implements OrderDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoClient.class);

    @Override
    public List<OrderDto> findAllOrdersWithCar() {
        LOGGER.debug("find orders with their cars ()");

        GetAllOrderDtosRequest request = new GetAllOrderDtosRequest();

        GetAllOrderDtosResponse response = (GetAllOrderDtosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        List<OrderDto> orderDtoList = new ArrayList<>();
        List<OrderDtoInfo> orderDtoInfoList = response.getOrderDtoInfo();

        for (int i = 0; i < orderDtoInfoList.size(); i++) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderDtoInfoList.get(i), orderDto);
            orderDto.setDate(LocalDate.of(
                    orderDtoInfoList.get(i).getDate().getYear(),
                    orderDtoInfoList.get(i).getDate().getMonth(),
                    orderDtoInfoList.get(i).getDate().getDay()));
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
