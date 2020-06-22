package com.epam.brest.courses.soap_app;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Endpoint
public class CarDtoEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoEndpoint.class);

    private static final String NAMESPACE_URI = "http://epam.com/brest/courses/soap_app";

    private final CarDtoService carDtoService;

    @Autowired
    public CarDtoEndpoint(CarDtoService carDtoService) {
        this.carDtoService = carDtoService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCarDtosRequest")
    @ResponsePayload
    public GetAllCarDtosResponse getAll(@RequestPayload GetAllCarDtosRequest request) {
        LOGGER.debug("find all car_dtos ()");

        GetAllCarDtosResponse response = new GetAllCarDtosResponse();
        List<CarDtoInfo> carDtoInfoList = new ArrayList<>();

        LocalDate dateFrom = LocalDate.of(
                request.getDateFrom().getYear(),
                request.getDateFrom().getMonth(),
                request.getDateFrom().getDay());
        LocalDate dateTo = LocalDate.of(
                request.getDateTo().getYear(),
                request.getDateTo().getMonth(),
                request.getDateTo().getDay());

        List<CarDto> carDtoList = carDtoService.findAllWithNumberOfOrders(dateFrom, dateTo);
        for (int i = 0; i < carDtoList.size(); i++) {
            CarDtoInfo carDtoInfo = new CarDtoInfo();
            BeanUtils.copyProperties(carDtoList.get(i), carDtoInfo);
            carDtoInfoList.add(carDtoInfo);
        }
        response.getCarDtoInfo().addAll(carDtoInfoList);

        return response;
    }
}
