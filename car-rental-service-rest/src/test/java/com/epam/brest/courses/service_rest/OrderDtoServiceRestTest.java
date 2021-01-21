package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.dto.OrderDto;
import com.epam.brest.courses.service_rest.config.TestConfig;
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
@ContextConfiguration(classes = TestConfig.class)
class OrderDtoServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDtoServiceRestTest.class);

    private static final String ORDER_DTOS_URL = "http://localhost:8088/order_dtos";

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    private OrderDtoServiceRest orderDtoServiceRest;

    @BeforeEach
    void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        orderDtoServiceRest = new OrderDtoServiceRest(ORDER_DTOS_URL, restTemplate);
    }

    @Test
    void findAllOrdersWithCar() throws URISyntaxException, JsonProcessingException {
        LOGGER.debug("findAllOrdersWithCar ()");

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(ORDER_DTOS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<OrderDto> orderDtos = orderDtoServiceRest.findAllOrdersWithCar();

        // then
        mockServer.verify();
        assertNotNull(orderDtos);
        assertTrue(orderDtos.size() > 0);
    }

    private OrderDto create(int index) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(index);
        orderDto.setBrand("b" + index);
        orderDto.setRegisterNumber("rn" + index);
        orderDto.setDate(LocalDate.of(2020, 11, 1 + index));
        return orderDto;
    }
}