package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.config.TestConfig;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.CAR_NOT_FOUND;
import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@EntityScan("com.epam.brest.courses.*")
@ContextConfiguration(classes = {TestConfig.class})
public class CarRestControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestControllerIT.class);

    private static final String CARS_ENDPOINT = "/cars";

    private final CarRestController carRestController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private MockMvcCarService carService = new MockMvcCarService();

    @Autowired
    CarRestControllerIT(CarRestController carRestController) {
        this.carRestController = carRestController;
    }

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(carRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void findAll() throws Exception {

        List<Car> cars = carService.findAll();
        assertNotNull(cars);
       // assertTrue(cars.size() > 0);
    }

    @Test
    void findById() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5380 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Car savedCar = carService.create(car);

        //when
        Optional<Car> optionalCar = carService.findById(savedCar.getId());

        //then
        assertTrue(optionalCar.isPresent());
        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("5380 AB-1", optionalCar.get().getRegisterNumber());
        assertEquals(0 ,BigDecimal.valueOf(150).compareTo(optionalCar.get().getPrice()));
    }


    @Test
    void update() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("7302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //Integer id = carDao.save(car);
        Car savedCar = carService.create(car);
        Optional<Car> optionalCar = carService.findById(savedCar.getId());

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carService.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carService.findById(optionalCar.get().getId());
        assertTrue(optionalCar.isPresent());
        assertEquals(savedCar.getId(),updatedOptionalCar.get().getId());
//        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
//        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
//        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
    }

    @Test
    void create() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //when
        Car savedCar = carService.create(car);

        //then
        assertNotNull(savedCar.getId());

        Optional<Car> optionalCar = carService.findById(savedCar.getId());
        assertTrue(optionalCar.isPresent());

        assertEquals(savedCar.getId(),optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("5302 AB-1", optionalCar.get().getRegisterNumber());
    }

    @Test
    void delete() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("9302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Car savedCar = carService.create(car);

        //when
        carService.delete(savedCar.getId());

        //then
        // assertTrue(1 == result);
    }

    @Test
    public void shouldReturnDepartmentNotFoundError() throws Exception {

        LOGGER.debug("shouldReturnDepartmentNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(CARS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), CAR_NOT_FOUND);
    }

    @Test
    public void shouldFailOnCreateCarWithDuplicateRegisterNumber() throws Exception {
        Car car1 = new Car();
        car1.setBrand("BMW");
        car1.setRegisterNumber("4343 AB-0");
        car1.setPrice(new BigDecimal("150"));
        Car id = carService.create(car1);
        assertNotNull(id);

        Car car2 = new Car();
        car2.setBrand("HONDA");
        car2.setRegisterNumber(car1.getRegisterNumber());
        car2.setPrice(new BigDecimal("200"));

        String json = objectMapper.writeValueAsString(car2);
        MockHttpServletResponse response =
                mockMvc.perform(post(CARS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), VALIDATION_ERROR);
    }

    private class MockMvcCarService {

        List<Car> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(CARS_ENDPOINT+"/filter/2020-03-04")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Car>>() {});
        }

        Optional<Car> findById(Integer carId) throws Exception {

            LOGGER.debug("findById({})", carId);
            MockHttpServletResponse response = mockMvc.perform(get(CARS_ENDPOINT + "/" + carId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Car.class));
        }

        Car create(Car car) throws Exception {

            LOGGER.debug("create({})", car);
            String json = objectMapper.writeValueAsString(car);
            MockHttpServletResponse response =
                    mockMvc.perform(post(CARS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Car.class);
        }

        int update(Car car) throws Exception {

            LOGGER.debug("update({})", car);
            String json = objectMapper.writeValueAsString(car);
            MockHttpServletResponse response =
                    mockMvc.perform(put(CARS_ENDPOINT+"/"+car.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        void delete(Integer carId) throws Exception {

            LOGGER.debug("delete(id:{})", carId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(CARS_ENDPOINT).append("/")
                            .append(carId).toString())
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            //return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}
