package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.service_soap.wsdl.AddOrderRequest;
import com.epam.brest.courses.service_soap.wsdl.AddOrderResponse;
import com.epam.brest.courses.service_soap.wsdl.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class OrderClient extends WebServiceGatewaySupport implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderClient.class);

    private final String URL = "http://localhost:8088/ws";

    @Override
    public Order create(Order order) {
        LOGGER.debug("create order ({})", order);

        AddOrderRequest request = new AddOrderRequest();

        OrderInfo orderInfo = new OrderInfo();
        try {
            XMLGregorianCalendar xmlDate =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            order.getDate().toString()
                    );
            orderInfo.setDate(xmlDate);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        orderInfo.setCarId(order.getCarId());
        request.setOrderInfo(orderInfo);

        AddOrderResponse response = (AddOrderResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        request, new SoapActionCallback(URL + "/addOrderRequest"));

        BeanUtils.copyProperties(response.getOrderInfo(), order);
        return order;
    }
}
