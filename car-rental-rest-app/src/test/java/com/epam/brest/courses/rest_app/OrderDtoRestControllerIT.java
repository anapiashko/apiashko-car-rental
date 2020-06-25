package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.rest_app.config.TestConfig;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@EntityScan("com.epam.brest.courses.*")
@ContextConfiguration(classes = {TestConfig.class})
class OrderDtoRestControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestControllerIT.class);

    private static final String ORDER_DTOS_ENDPOINT = "/order_dtos";

    @Autowired
    private OrderDtoRestController orderDtoRestController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private MockMvc mockMvc;

    private MockMvcOrderDtoService orderDtoService = new MockMvcOrderDtoService();

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderDtoRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void getAllOrderDtos() throws Exception {
        //when
        List<OrderDto> orderDtoList = orderDtoService.findAllOrdersWithCar();

        //then
        assertNotNull(orderDtoList);
    }

    private class MockMvcOrderDtoService {

        List<OrderDto> findAllOrdersWithCar() throws Exception {
            LOGGER.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(ORDER_DTOS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Car>>() {});
        }
    }
}