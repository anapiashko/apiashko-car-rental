package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class ExcelServiceRest implements ExcelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public ExcelServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ByteArrayInputStream carsToExcel(List<Car> cars) throws IOException {
        return null;
    }

    @Override
    public List<Car> excelToCars(String fileName){
        LOGGER.debug("import in db()");

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url+"?filename="+fileName, List.class);
        return (List<Car>) responseEntity.getBody();
    }
}
