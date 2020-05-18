package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.dao.CarDao;
import com.epam.brest.courses.dao.CarDtoDao;
import com.epam.brest.courses.dao.OrderDao;
import com.epam.brest.courses.dao.jdbc.CarDaoJdbc;
import com.epam.brest.courses.dao.jdbc.CarDtoDaoJdbc;
import com.epam.brest.courses.dao.jdbc.OrderDaoJdbc;
import com.epam.brest.courses.service.CarDtoServiceImpl;
import com.epam.brest.courses.service.CarServiceImpl;
import com.epam.brest.courses.service.OrderServiceImpl;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.HomeController;
import com.epam.brest.courses.web_app.OrderController;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public HomeController homeController(){
        return new HomeController();
    }

    @Bean
    public OrderController orderController(){
        return new OrderController(orderService());
    }

    @Bean
    public CarController carController(){
        return new CarController(carService(), carDtoService());
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
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
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

//    @Bean
//    public ServiceProperties serviceProperties(){
//        return  ServiceProperties.builder()
//                .baseUrl("http://localhost:8088")
//                .carUrl("cars")
//                .carDtoUrl("car_dtos")
//                .orderUrl("orders")
//                .build();
//    }

}
