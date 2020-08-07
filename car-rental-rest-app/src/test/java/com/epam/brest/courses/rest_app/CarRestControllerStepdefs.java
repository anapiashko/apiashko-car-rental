package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarRestControllerStepdefs {

    private final String URL = "http://localhost:8088/cars";
    private LocalDate localDate;
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity responseEntity;
    private Car car = new Car();

    /**
     * Find  available cars by date.
     *
     * @param date date to make order
     */
    @Given("^user enter date (.*) on which the order will be$")
    public void userEnterDateOnWhichTheOrderWillBe(String date) {
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @When("^user send GET request$")
    public void usersSendGETRequest() {
        responseEntity = restTemplate.getForEntity(URL + "/filter/" + localDate, List.class);
    }

    @Then("^the requested data is returned$")
    public void theRequestedDataIsReturned() {
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseEntity.getBody());
    }

    /**
     * Create new car.
     *
     * @param brand           car brand
     * @param register_number car registration number
     * @param price           car price
     */
    @Given("^user send car data to be created with brand (.*), register_number (.*), price (.*)$")
    public void userSendCarDataToBeCreated(String brand, String register_number, String price) {
        car.setBrand(brand);
        car.setRegisterNumber(register_number);
        car.setPrice(new BigDecimal(price));
    }

    @When("^the car added to list$")
    public void theCarAddedToList() {
        responseEntity = restTemplate.postForEntity(URL, car, Car.class);
    }

    @Then("^the car_id not null$")
    public void theCar_idNotNull() {
        Car savedCar = (Car) responseEntity.getBody();
        assertNotNull(savedCar.getId());
        car.setId(savedCar.getId());
    }

    @And("^car code is \"([^\"]*)\"$")
    public void carCodeIs(String code) {
        assertSame(responseEntity.getStatusCode(), HttpStatus.valueOf(code));
    }
    /**
     * Find car by id.
     */
    @When("^user send GET request with car_id$")
    public void userSendGETRequestWithCar_id() {
        Car savedCar = (Car) responseEntity.getBody();
        responseEntity = restTemplate.getForEntity(URL + "/" + savedCar.getId(), Car.class);
    }

    /**
     * Update car.
     *
     * @param brand           car brand
     * @param register_number car registration number
     * @param price           car price
     */
    @When("^user send PUT request with new car to be updated, with brand \"([^\"]*)\", register_number \"([^\"]*)\", price (\\d+)$")
    public void userSendPUTRequestWithNewCarToBeUpdatedWith(String brand, String register_number, double price) {
        car.setBrand(brand);
        car.setRegisterNumber(register_number);
        car.setPrice(new BigDecimal(price));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(car, headers);
        responseEntity = restTemplate.exchange(URL + "/" + car.getId(), HttpMethod.PUT, entity, Integer.class);
    }

    @Then("^server should return 1 as a result$")
    public void serverShouldReturn1AsAResultWithCodeOK() {
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseEntity.getBody());
        assertTrue(1 == (Integer) responseEntity.getBody());
    }

    /**
     * Delete car.
     */
    @When("^user send DELETE request with car_id to be deleted$")
    public void userSendDELETERequestWithCar_idToBeDeleted() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Car> entity = new HttpEntity<>(headers);
        responseEntity = restTemplate.exchange(URL + "/" + car.getId(), HttpMethod.DELETE, entity, Integer.class);
    }

    @Then("^the car is removed$")
    public void theCarIsRemovedWithCodeOK() {
        assertSame(responseEntity.getStatusCode(), HttpStatus.OK);
    }
}
