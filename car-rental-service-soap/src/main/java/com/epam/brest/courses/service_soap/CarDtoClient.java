package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_soap.wsdl.CarDtoInfo;
import com.epam.brest.courses.service_soap.wsdl.GetAllCarDtosRequest;
import com.epam.brest.courses.service_soap.wsdl.GetAllCarDtosResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDtoClient extends WebServiceGatewaySupport implements CarDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoClient.class);

    @Override
    public List<CarDto> findAllWithNumberOfOrders(LocalDate dateFrom, LocalDate dateTo) {
        LOGGER.debug("find cars with numbers of orders (dateFrom = {}, dateTo = {})", dateFrom, dateTo);

        GetAllCarDtosRequest request = new GetAllCarDtosRequest();
        try {
            XMLGregorianCalendar xmlDateFrom =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            dateFrom.toString()
                    );
            XMLGregorianCalendar xmlDateTo =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            dateTo.toString()
                    );
            request.setDateFrom(xmlDateFrom);
            request.setDateTo(xmlDateTo);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        GetAllCarDtosResponse response = (GetAllCarDtosResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

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
