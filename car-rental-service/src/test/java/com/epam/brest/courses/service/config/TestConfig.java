package com.epam.brest.courses.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.epam.brest.courses.*")
@ComponentScan("com.epam.brest.courses.*")
public class TestConfig {

//    @Autowired
//    private OrderDao orderDao;
//
//    @Autowired
//    private CarDao carDao;
//
//    @Autowired
//    private CarDtoDao carDtoDao;
//
//    @Bean
//    public OrderService orderService(){
//        return new OrderServiceImpl(orderDao);
//    }
//
//    @Bean
//    public CarDtoService carDtoService(){
//        return new CarDtoServiceImpl(carDtoDao);
//    }
//
//    @Bean
//    public CarService carService(){
//        return new CarServiceImpl(carDao);
//    }
}
