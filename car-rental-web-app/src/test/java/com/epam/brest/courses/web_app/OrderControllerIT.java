package com.epam.brest.courses.web_app;

import com.epam.brest.courses.web_app.config.TestConfig;
import com.epam.brest.courses.web_app.config.ThymeleafConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:data-jpa-init-test.sql"})
class OrderControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;

    @BeforeEach
    void setup() {
        mockMvc =  MockMvcBuilders.standaloneSetup(orderController)
                .setViewResolvers(new ThymeleafConfig().viewResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldReturnAllOrderDtos() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("orders"))
        ;
    }

    @Test
    void shouldCreateOrder() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("carId", "3")
                        .param("date", "2020-01-01")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/cars"))
                .andExpect(redirectedUrl("/cars"));
    }

    @Test
    void shouldDeleteOrder() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/2/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/orders"))
                .andExpect(redirectedUrl("/orders"));
    }
}