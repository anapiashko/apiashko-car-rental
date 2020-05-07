package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Car;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
class CarControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @BeforeEach
    public void setup() {
        mockMvc =  MockMvcBuilders.standaloneSetup(carController)
                .setViewResolvers(new ThymeleafConfig().viewResolver())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldReturnListOfFreeCars() throws Exception {
        LocalDate filter = LocalDate.of(2020,1,2);
//        String filter = "2020-01-10";
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars?filter="+filter))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("cars"));
    }

    @Test
    public void shouldOpenEditCarPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("car"))
                .andExpect(model().attribute("isNew", false))
                .andExpect(model().attribute("car", hasProperty("id", is(2))))
                .andExpect(model().attribute("car", hasProperty("brand", is("AUDI"))))
                .andExpect(model().attribute("car", hasProperty("registerNumber", is("0056 AB-1"))))
                .andExpect(model().attribute("car", hasProperty("price", is(new BigDecimal("140.00")))))
        ;
    }

    @Test
    public void shouldReturnToCarsPageIfCarNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cars"));
    }

    @Test
    public void shouldUpdateCarAfterEdit() throws Exception {

        Car car = new Car();
        car.setId(1);
        car.setBrand("BMW");
        car.setRegisterNumber("0056 AB-1");
        car.setPrice(BigDecimal.valueOf(240));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/cars/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("brand", "BMW")
                        .param("registerNumber", "0076 AB-1")
                        .param("price", "240")
                        .sessionAttr("car", car)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/cars"))
                .andExpect(redirectedUrl("/cars"));
    }

    @Test
    public void shouldOpenNewCarPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/car")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("car"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("car", isA(Car.class)));
    }

    @Test
    public void shouldAddNewCar() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/car")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("brand", "test")
                        .param("registerNumber", "1111 AB-1")
                        .param("price", "111")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/cars"))
                .andExpect(redirectedUrl("/cars"));
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars/1/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/cars"))
                .andExpect(redirectedUrl("/cars"));
    }

    @Test
    public void shouldReturnListCarsWithNumberOfOrders() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/car-statistics")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("dateFrom", "2020-01-01")
                        .param("dateTo", "2020-01-15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("statistics"))
                .andExpect(model().attribute("cars", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("brand", is("AUDI")),
                                hasProperty("registerNumber", is("0056 AB-1")),
                                hasProperty("numberOrders", is(2))
                        )
                )))
        ;
    }
}
