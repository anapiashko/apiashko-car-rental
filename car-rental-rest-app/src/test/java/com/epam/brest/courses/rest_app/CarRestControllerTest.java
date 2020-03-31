package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class CarRestControllerTest {

    @InjectMocks
    private CarRestController carRestController;

    @Mock
    private CarService carService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carRestController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(carService);
    }

    @Test
    void freeCars() throws Exception {
        String date = "2020-04-03";
        Mockito.when(carService.findAllByDate(date)).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cars/filter/"+date)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", Matchers.is("b0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].registerNumber", Matchers.is("rn0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand", Matchers.is("b1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].registerNumber", Matchers.is("rn1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(101)))
        ;

        Mockito.verify(carService).findAllByDate(date);
    }

    private Car create(int index) {
        Car car = new Car();
        car.setId(index);
        car.setBrand("b" + index);
        car.setRegisterNumber("rn" + index);
        car.setPrice(BigDecimal.valueOf(100 + index));
        return car;
    }
}
