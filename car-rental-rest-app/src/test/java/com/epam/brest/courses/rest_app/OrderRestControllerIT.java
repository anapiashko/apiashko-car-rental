package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Order;
import com.epam.brest.courses.rest_app.exception.CustomExceptionHandler;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class OrderRestControllerIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRestControllerIT.class);

    public static final String ORDERS_ENDPOINT = "/orders";

    @Autowired
    private OrderRestController orderRestController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private MockMvc mockMvc;

    private MockMvcOrderService orderService = new MockMvcOrderService();

    private ObjectMapper objectMapper = new ObjectMapper();

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
        order.setDate("2020-12-20");
        order.setCarId(2);

        //when
        Integer id = orderService.create(order);

        //then
        assertNotNull(id);
    }

    class MockMvcOrderService {

        public Integer create(Order order) throws Exception {

            LOGGER.debug("create({})", order);
            String json = objectMapper.writeValueAsString(order);
            MockHttpServletResponse response =
                    mockMvc.perform(post(ORDERS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }
}