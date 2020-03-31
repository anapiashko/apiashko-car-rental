package com.epam.brest.courses.rest_app;


import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class CarRestControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestControllerIT.class);

    public static final String CARS_ENDPOINT = "/cars";


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
    public void shouldFindAllFreeCars() throws Exception {

        List<Car> cars = carService.findAll();
        assertNotNull(cars);
        assertTrue(cars.size() > 0);
    }

    @Test
    void findById() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);

        //when
        Optional<Car> optionalCar = carService.findById(id);

        //then
        assertTrue(optionalCar.isPresent());
        assertEquals(id,optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("5302 AB-1", optionalCar.get().getRegisterNumber());
        assertEquals(0 ,BigDecimal.valueOf(150).compareTo(optionalCar.get().getPrice()));
    }

    @Test
    void update() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);
        Optional<Car> optionalCar = carService.findById(id);

        assertTrue(optionalCar.isPresent());
        optionalCar.get().setBrand("HONDA");
        optionalCar.get().setRegisterNumber("7350 AB-1");
        optionalCar.get().setPrice(BigDecimal.valueOf(200));

        //when
        int result = carService.update(optionalCar.get());

        //then
        assertTrue(1 == result);
        Optional<Car> updatedOptionalCar = carService.findById(id);
        assertTrue(optionalCar.isPresent());
        assertEquals(id,updatedOptionalCar.get().getId());
        assertEquals("HONDA", updatedOptionalCar.get().getBrand());
        assertEquals("7350 AB-1", updatedOptionalCar.get().getRegisterNumber());
        assertEquals(0, BigDecimal.valueOf(200).compareTo(updatedOptionalCar.get().getPrice()));
    }

    @Test
    void create() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        //when
        Integer id = carService.create(car);

        //then
        assertNotNull(id);

        Optional<Car> optionalCar = carService.findById(id);
        assertTrue(optionalCar.isPresent());

        assertEquals(id,optionalCar.get().getId());
        assertEquals("Honda", optionalCar.get().getBrand());
        assertEquals("5302 AB-1", optionalCar.get().getRegisterNumber());
    }

    @Test
    void delete() throws Exception {
        //given
        Car car = new Car();
        car.setBrand("Honda");
        car.setRegisterNumber("5302 AB-1");
        car.setPrice(BigDecimal.valueOf(150));

        Integer id = carService.create(car);

        //when
        int result = carService.delete(id);

        //then
        assertTrue(1 == result);
    }

    class MockMvcCarService {

        public List<Car> findAll() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(CARS_ENDPOINT+"/filter/2020-03-04")
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Car>>() {});
        }

        public Optional<Car> findById(Integer carId) throws Exception {

            LOGGER.debug("findById({})", carId);
            MockHttpServletResponse response = mockMvc.perform(get(CARS_ENDPOINT + "/" + carId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Car.class));
        }

        public Integer create(Car car) throws Exception {

            LOGGER.debug("create({})", car);
            String json = objectMapper.writeValueAsString(car);
            MockHttpServletResponse response =
                    mockMvc.perform(post(CARS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Car car) throws Exception {

            LOGGER.debug("update({})", car);
            MockHttpServletResponse response =
                    mockMvc.perform(post(CARS_ENDPOINT+"/"+car.getId()+"/update")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(car))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer carId) throws Exception {

            LOGGER.debug("delete(id:{})", carId);
            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(new StringBuilder(CARS_ENDPOINT).append("/")
                            .append(carId).toString())
            ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}