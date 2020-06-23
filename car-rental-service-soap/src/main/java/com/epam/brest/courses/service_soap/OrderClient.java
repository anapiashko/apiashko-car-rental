package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_soap.wsdl.AddOrderRequest;
import com.epam.brest.courses.service_soap.wsdl.AddOrderResponse;
import com.epam.brest.courses.service_soap.wsdl.OrderInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class OrderClient extends WebServiceGatewaySupport implements OrderService {
    @Override
    public Order create(Order order) {
        AddOrderRequest request = new AddOrderRequest();

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(order, orderInfo);
        request.setOrderInfo(orderInfo);

        AddOrderResponse response = (AddOrderResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        request, new SoapActionCallback("/ws/addOrderRequest"));

        BeanUtils.copyProperties(response.getOrderInfo(), order);
        return order;
    }
}
