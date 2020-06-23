package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.service_soap.wsdl.GetAllCarsRequest;
import com.epam.brest.courses.service_soap.wsdl.GetAllCarsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class CarClient extends WebServiceGatewaySupport {

    private final String URL = "http://localhost:8088/ws";

    public GetAllCarsResponse getAllCars() {
        GetAllCarsRequest request = new GetAllCarsRequest();
        GetAllCarsResponse response = (GetAllCarsResponse) getWebServiceTemplate().marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/getAllCarsRequest"));
        return response;
    }
}
