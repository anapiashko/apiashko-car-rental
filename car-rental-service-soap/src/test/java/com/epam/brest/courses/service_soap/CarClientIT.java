package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_soap.config.WSConfigClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ComponentScan("com.epam.brest.courses.service_soap.*")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WSConfigClient.class)
public class CarClientIT {

    @Autowired
    private CarClient carClient;

    @Test
    public void shouldFindAllCars() {
        List<Car> response = carClient.findAll();

        assertNotNull(response);
    }
//
//    @Test
//    public void givenCountryService_whenCountryPoland_thenCapitalIsWarsaw() {
//        GetCountryResponse response = client.getCountry("Poland");
//        assertEquals("Warsaw", response.getCountry().getCapital());
//    }
//
//    @Test
//    public void givenCountryService_whenCountrySpain_thenCurrencyEUR() {
//        GetCountryResponse response = client.getCountry("Spain");
//        assertEquals(Currency.EUR, response.getCountry().getCurrency());
//    }
}
