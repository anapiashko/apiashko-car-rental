package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CarServiceRest implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public CarServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public List<Car> findAll() {
        LOGGER.debug("findAll()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Car>) responseEntity.getBody();
    }

    public List<Car> findAllByDate(String date) {
        LOGGER.debug("findAllByDate(date = {})", date);

        ResponseEntity responseEntity = restTemplate.getForEntity(url+"/filter/"+date, List.class);
        return (List<Car>) responseEntity.getBody();
    }

    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("findById({})", carId);

        ResponseEntity<Car> responseEntity = restTemplate.getForEntity(url+"/"+carId, Car.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    public Integer create(Car car) {
        LOGGER.debug("create({})", car);

        ResponseEntity responseEntity = restTemplate.postForEntity(url, car, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    public int update(Car car) {
        LOGGER.debug("update({})", car);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url+"/"+car.getId(), HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    public int delete(Integer carId) {
        LOGGER.debug("delete({})", carId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url+"/"+carId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }
}
