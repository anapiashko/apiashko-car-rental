package com.epam.brest.courses.rest_app.config;

import com.epam.brest.courses.dao.*;
import com.epam.brest.courses.rest_app.CarRestController;
import com.epam.brest.courses.rest_app.OrderRestController;
import com.epam.brest.courses.service.CarDtoServiceImpl;
import com.epam.brest.courses.service.CarServiceImpl;
import com.epam.brest.courses.service.OrderServiceImpl;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.OrderService;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
@ComponentScan(basePackages="com.epam.brest.courses.*")
public class TestConfig {

    @Bean
    public OrderRestController orderRestController(){
        return new OrderRestController(orderService());
    }

    @Bean
    public CarRestController carRestController(){
        return new CarRestController(carService());
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(orderDao());
    }

    @Bean
    public CarDtoService carDtoService(){
        return new CarDtoServiceImpl(carDtoDao());
    }

    @Bean
    public CarService carService(){
        return new CarServiceImpl(carDao());
    }

    @Bean
    public CarDtoDao carDtoDao(){
        return new CarDtoDaoJdbc(jdbcTemplate());
    }

    @Bean
    public CarDao carDao(){
        return new CarDaoJdbc(jdbcTemplate());
    }

    @Bean
    public OrderDao orderDao(){
        return new OrderDaoJdbc(jdbcTemplate());
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(){
        return new NamedParameterJdbcTemplate(getDataSource());
    }

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]
                {new ClassPathResource("dao.properties")};
        pspc.setLocations(resources);
        return pspc;
    }
}
