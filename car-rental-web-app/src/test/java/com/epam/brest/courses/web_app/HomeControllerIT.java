package com.epam.brest.courses.web_app;

import com.epam.brest.courses.web_app.config.TestConfig;
import com.epam.brest.courses.web_app.config.ThymeleafConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class HomeControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private HomeController homeController;

    @BeforeEach
    void setup() {
        mockMvc =  MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(new ThymeleafConfig().viewResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void selectData() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("findCar"))
        ;
    }

    @Test
    void putPeriod() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/period"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("period"))
                .andExpect(model().attribute("incorrectPeriod", false))
        ;
    }
}