package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * CarDto REST service.
 */
public class CarDtoServiceRest implements CarDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public CarDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Find all cars with number of orders.
     *
     * @param dateFrom first date
     * @param dateTo last date
     * @return carDto list
     */
    @Override
    public List<CarDto> findAllWithNumberOfOrders(String dateFrom, String dateTo){
        LOGGER.debug("find all cars with numbers of orders()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url+"?dateFrom="+dateFrom
                +"&dateTo="+dateTo, List.class);
        return (List<CarDto>) responseEntity.getBody();
    }
}
