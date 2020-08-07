package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.CarDto;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CarDtoControllerRestStepdefs {

    private final String URL = "http://localhost:8088/car_dtos";
    private LocalDate localDateFrom;
    private LocalDate localDateTo;
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity responseEntity;

    @Given("^user enter dateFrom (.*) and dateTo (.*)$")
    public void userEnterDateFromAndDateTo(String dateFrom, String dateTo) {
        localDateFrom = LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        localDateTo = LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @When("^user send GET request to /car_dtos$")
    public void userSendGETRequestToCar_dtos() {
        responseEntity = restTemplate.getForEntity(URL + "?dateFrom=" + localDateFrom
                + "&dateTo=" + localDateTo, List.class);
    }

    @Then("^the requested data is returned in format list of car_dtos$")
    public void theRequestedDataIsReturnedInFormatListOfCar_dtos() {
        List<CarDto> carDtos = (List<CarDto>)responseEntity.getBody();
        assertNotNull(carDtos);
    }

    @And("^car_dto code is \"([^\"]*)\"$")
    public void car_dtoCodeIs(String code) {
        assertSame(responseEntity.getStatusCode(), HttpStatus.valueOf(code));
    }
}
