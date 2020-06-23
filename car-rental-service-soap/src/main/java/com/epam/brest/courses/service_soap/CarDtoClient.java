package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_soap.wsdl.CarDtoInfo;
import com.epam.brest.courses.service_soap.wsdl.GetAllCarDtosRequest;
import com.epam.brest.courses.service_soap.wsdl.GetAllCarDtosResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDtoClient extends WebServiceGatewaySupport implements CarDtoService {

    private final String URL = "http://localhost:8088/ws";

    @Override
    public List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo) {
        GetAllCarDtosRequest request = new GetAllCarDtosRequest();
        try {
            XMLGregorianCalendar xmlDateFrom =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            dateFrom.toString()
                    );
            XMLGregorianCalendar xmlDateTo =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            dateFrom.toString()
                    );
            request.setDateFrom(xmlDateFrom);
            request.setDateTo(xmlDateTo);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        GetAllCarDtosResponse response = (GetAllCarDtosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                request, new SoapActionCallback(URL + "/getAllCarDtosRequest"));

        List<CarDto> carDtoList = new ArrayList<>();
        List<CarDtoInfo> carDtoInfoList = response.getCarDtoInfo();

        for (int i = 0; i < carDtoInfoList.size(); i++) {
            CarDto carDto = new CarDto();
            BeanUtils.copyProperties(carDtoInfoList.get(i), carDto);
            carDtoList.add(carDto);
        }
        CarDto carDto = new CarDto();
        carDto.setId(7291);
        carDto.setBrand("Brand Num Dto");
        carDto.setRegisterNumber("0091 KC-1");
        carDto.setNumberOrders(76);
        carDtoList.add(carDto);
        return carDtoList;
    }
}
