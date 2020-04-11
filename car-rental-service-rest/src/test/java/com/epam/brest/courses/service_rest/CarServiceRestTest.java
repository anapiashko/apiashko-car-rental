package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Car;
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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class CarServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceRestTest.class);

    public static final String CARS_URL = "http://localhost:8088/cars";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private CarServiceRest carServiceRest;


    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        carServiceRest = new CarServiceRest(CARS_URL, restTemplate);
    }

    @Test
    void findAllByDate() throws JsonProcessingException, URISyntaxException {
        LOGGER.debug("shouldFindAllFreeCars()");
        // given
        LocalDate filter = LocalDate.of(202,2,2);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL+"/filter/"+filter)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Car> freeCars = carServiceRest.findAllByDate(filter);

        // then
        mockServer.verify();
        assertNotNull(freeCars);
        assertTrue(freeCars.size() > 0);
    }

    @Test
    void create() throws URISyntaxException, JsonProcessingException {
        LOGGER.debug("shouldCreateDepartment()");
        // given
        Car car = create(2);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = carServiceRest.create(car);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    void findById() throws URISyntaxException, JsonProcessingException {

        // given
        Integer id = 1;
        Car car = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car))
                );

        // when
        Optional<Car> optionalCar = carServiceRest.findById(id);

        // then
        mockServer.verify();
        assertTrue(optionalCar.isPresent());
        assertEquals(optionalCar.get().getId(), id);
        assertEquals(optionalCar.get().getRegisterNumber(), car.getRegisterNumber());
    }

    @Test
    void update() throws URISyntaxException, JsonProcessingException {
        // given
        Integer id = 1;
        Car car = create(id);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL+"/"+id)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(car))
                );

        // when
        int result = carServiceRest.update(car);
        Optional<Car> updatedCarOptional =  carServiceRest.findById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedCarOptional.isPresent());
        assertEquals(updatedCarOptional.get().getId(), id);
        assertEquals(updatedCarOptional.get().getRegisterNumber(), car.getRegisterNumber());
    }

    @Test
    void delete() throws URISyntaxException, JsonProcessingException {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(CARS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        int result = carServiceRest.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    private Car create(int index) {
        Car car = new Car();
        car.setId(index);
        car.setBrand("b" + index);
        car.setRegisterNumber("rn" + index);
        car.setPrice(BigDecimal.valueOf(100 + index));
        return car;
    }
}