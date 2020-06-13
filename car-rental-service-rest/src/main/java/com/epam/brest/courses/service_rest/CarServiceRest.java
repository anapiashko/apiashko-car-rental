package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Car REST service.
 */
public class CarServiceRest implements CarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public CarServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Find all cars.
     *
     * @return list of cars
     */
    public List<Car> findAll() {
        LOGGER.debug("findAll()");

        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Car>) responseEntity.getBody();
    }

    /**
     * Find all free cars on date.
     *
     * @param date date
     * @return list of cars
     */
    public List<Car> findAllByDate(LocalDate date) {
        LOGGER.debug("findAllByDate(date = {})", date);

        ResponseEntity responseEntity = restTemplate.getForEntity(url+"/filter/" + date, List.class);
        return (List<Car>) responseEntity.getBody();
    }

    /**
     * Find car by id.
     *
     * @param carId order Id.
     * @return optional car
     */
    public Optional<Car> findById(Integer carId) {
        LOGGER.debug("findById({})", carId);

        ResponseEntity<Car> responseEntity = restTemplate.getForEntity(url+"/"+carId, Car.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Create car.
     *
     * @param car order record.
     * @return id created car
     */
    public Car create(Car car) {
        LOGGER.debug("create({})", car);

        ResponseEntity responseEntity = restTemplate.postForEntity(url, car, Car.class);
        return (Car) responseEntity.getBody();
    }

    /**
     * Update car.
     *
     * @param car car.
     * @return number changed records
     */
    public int update(Car car) {
        LOGGER.debug("update({})", car);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url+"/"+car.getId(), HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    /**
     * Delete car from db.
     *
     * @param carId car id.
     * @return number deleted cars.
     */
    public void delete(Integer carId) {
        LOGGER.debug("delete({})", carId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url+"/"+carId, HttpMethod.DELETE, entity, Integer.class);
        //return result.getBody();
    }

    @Override
    public void deleteAll() {

    }
}
