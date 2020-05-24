package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.dto.CarDto;
import com.epam.brest.courses.service_api.CarDtoService;
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

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class CarDtoDaoRestControllerIT {

    @InjectMocks
    private CarDtoRestController carDtoRestController;

    @Mock
    private CarDtoService carDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(carDtoRestController)
                .build();
    }

    @AfterEach
    void end() {
        Mockito.verifyNoMoreInteractions(carDtoService);
    }

    @Test
    void carStatistics() throws Exception {
        LocalDate dateFrom = LocalDate.of(2020,1,1);
        LocalDate dateTo = LocalDate.of(2020,1,31);
        Mockito.when(carDtoService.findAllWithNumberOfOrders(dateFrom, dateTo)).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/car_dtos?dateFrom="+dateFrom+"&dateTo="+dateTo)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand", Matchers.is("b0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].registerNumber", Matchers.is("rn0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numberOrders", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand", Matchers.is("b1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].registerNumber", Matchers.is("rn1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].numberOrders", Matchers.is(6)))
        ;

        Mockito.verify(carDtoService).findAllWithNumberOfOrders(dateFrom, dateTo);
    }

    private CarDto create(int index) {
        CarDto carDto = new CarDto();
        carDto.setId(index);
        carDto.setBrand("b" + index);
        carDto.setRegisterNumber("rn" + index);
        carDto.setNumberOrders(5+index);
        return carDto;
    }
}