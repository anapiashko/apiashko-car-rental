package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.rest_app.config.TestConfig;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
class OrderRestControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestControllerIT.class);

    private static final String ORDERS_ENDPOINT = "/orders";

    private final OrderRestController orderRestController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private MockMvc mockMvc;

    private MockMvcOrderService orderService = new MockMvcOrderService();

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    OrderRestControllerIT(OrderRestController orderRestController) {
        this.orderRestController = orderRestController;
    }

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void create() throws Exception {
        //given
        Order order = new Order();
        order.setDate(LocalDate.of(2020,12,20));
        order.setCarId(2);

        //when
        Order savedOrder = orderService.create(order);

        //then
        assertNotNull(savedOrder);
    }

    class MockMvcOrderService {

        public Order create(Order order) throws Exception {

            LOGGER.debug("create({})", order);
            String json = objectMapper.writeValueAsString(order);
            MockHttpServletResponse response =
                    mockMvc.perform(post(ORDERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Order.class);
        }
    }
}