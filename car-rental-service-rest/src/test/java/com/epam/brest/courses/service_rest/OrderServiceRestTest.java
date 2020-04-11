package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class OrderServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRestTest.class);

    private static final String ORDERS_URL = "http://localhost:8088/orders";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    private OrderServiceRest orderServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        orderServiceRest = new OrderServiceRest(ORDERS_URL, restTemplate);
    }

    @Test
    void create() throws JsonProcessingException, URISyntaxException {
        LOGGER.debug("shouldCreateDepartment()");
        // given
       Order order = create(0);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(ORDERS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Integer id = orderServiceRest.create(order);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    private Order create(int index) {
       Order order = new Order();
       order.setId(index);
       order.setDate(LocalDate.of(2020+index,3,4));
       order.setCarId(100+index);
       return order;
    }
}