package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.OrderDto;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class OrderDtoRestControllerStepdefs {

    private final String URL = "http://localhost:8088/order_dtos";
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity responseEntity;

    @When("^user send GET request to /order_dtos$")
    public void userSendGETRequestToOrder_dtos() {
        responseEntity = restTemplate.getForEntity(URL, List.class);
    }

    @Then("^the requested data is returned in format list of order_dtos$")
    public void theRequestedDataIsReturnedInFormatListOfOrder_dtos() {
        List<OrderDto> orderDtos = (List<OrderDto>)responseEntity.getBody();
        assertNotNull(orderDtos);
    }

    @And("^order_dto code is \"([^\"]*)\"$")
    public void order_dtoCodeIs(String code) {
        assertSame(responseEntity.getStatusCode(), HttpStatus.valueOf(code));
    }
}
