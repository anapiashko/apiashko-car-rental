package com.epam.brest.courses.rest_app.steps;

import com.epam.brest.courses.model.Order;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class OrderRestControllerStepdefs {

    private final String URL = "http://localhost:8088/orders";
    private LocalDate localDate;
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity responseEntity;
    private Order order = new Order();

    @Given("^user send order data to be created with date (.*), car_id (.*)$")
    public void userSendOrderDataToBeCreatedWithDateCar_id(String date, String car_id) {
        localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        order.setDate(localDate);
        order.setCarId(Integer.parseInt(car_id));
    }

    @When("^the order added to list$")
    public void theOrderAddedToList() {
        responseEntity = restTemplate.postForEntity(URL, order, Order.class);
    }

    @Then("^the order_id not null$")
    public void theOrder_idNotNull() {
        Order savedOrder = (Order) responseEntity.getBody();
        assertNotNull(savedOrder.getId());
        order.setId(savedOrder.getId());
    }

    @And("^order code is \"([^\"]*)\"$")
    public void codeIs(String code) {
        assertSame(responseEntity.getStatusCode(), HttpStatus.valueOf(code));
    }
}
