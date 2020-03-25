package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class CarControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnListOfFreeCars() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("cars"))
                .andExpect(model().attribute("cars",hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("brand", is("BMW")),
                                hasProperty("registerNumber", is("3456 AB-1")),
                                hasProperty("price", is(new BigDecimal("240.00")))
                        )
                )))
                .andExpect(model().attribute("cars",hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("brand", is("AUDI")),
                                hasProperty("registerNumber", is("0056 AB-1")),
                                hasProperty("price", is(new BigDecimal("140.00")))
                        )
                )))
                .andExpect(model().attribute("cars",hasItem(
                        allOf(
                                hasProperty("id", is(3)),
                                hasProperty("brand", is("TYOYTA")),
                                hasProperty("registerNumber", is("3836 AB-1")),
                                hasProperty("price", is(new BigDecimal("200.00")))
                        )
                )));
    }



}
