package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.CarDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class CarDtoServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDtoServiceRestTest.class);


    public static final String CARS_URL = "http://localhost:8088/car_dtos";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private CarDtoServiceRest carDtoServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        carDtoServiceRest = new CarDtoServiceRest(CARS_URL, restTemplate);
    }


    @Test
    void findAllWithNumberOfOrders() throws URISyntaxException, JsonProcessingException {

        LOGGER.debug("shouldFindAllWithNumberOfOrders()");
        // given
        LocalDate dateFrom = LocalDate.of(2020,2,2);
        LocalDate dateTo = LocalDate.of(2020,3,3);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL+"?dateFrom="+dateFrom
                +"&dateTo="+dateTo)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<CarDto> cars = carDtoServiceRest.findAllWithNumberOfOrders(dateFrom, dateTo);

        // then
        mockServer.verify();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    private CarDto create(int index) {
        CarDto carDto = new CarDto();
        carDto.setId(index);
        carDto.setBrand("b" + index);
        carDto.setRegisterNumber("rn" + index);
        carDto.setNumberOrders(index + 2);
        return carDto;
    }
}