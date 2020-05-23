package com.epam.brest.courses.service.config;

import com.epam.brest.courses.dao.CarDao;
import com.epam.brest.courses.dao.CarDtoDao;
import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.service.CarDtoServiceImpl;
import com.epam.brest.courses.service.CarServiceImpl;
import com.epam.brest.courses.service.OrderServiceImpl;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.epam.brest.courses.*")
@ComponentScan("com.epam.brest.courses.*")
public class TestConfig {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CarDao carDao;

    @Autowired
    private CarDtoDao carDtoDao;

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(orderDao);
    }

    @Bean
    public CarDtoService carDtoService(){
        return new CarDtoServiceImpl(carDtoDao);
    }

    @Bean
    public CarService carService(){
        return new CarServiceImpl(carDao);
    }
}
