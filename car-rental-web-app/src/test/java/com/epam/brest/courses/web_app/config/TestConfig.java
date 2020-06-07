package com.epam.brest.courses.web_app.config;

import com.epam.brest.courses.dao.CarDtoRepository;
import com.epam.brest.courses.dao.CarRepository;
import com.epam.brest.courses.dao.OrderDtoRepository;
import com.epam.brest.courses.dao.OrderRepository;
import com.epam.brest.courses.service.*;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.ExcelService;
import com.epam.brest.courses.service_api.OrderService;
import com.epam.brest.courses.web_app.CarController;
import com.epam.brest.courses.web_app.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@TestConfiguration
@EnableJpaRepositories("com.epam.brest.courses.dao")
@ComponentScan("com.epam.brest.courses.*")
public class TestConfig {

    @Autowired
    private CarDtoRepository carDtoRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDtoRepository orderDtoRepository;

    @Bean
    public OrderController orderController(){
        return new OrderController(orderService(), orderDtoService());
    }

    @Bean
    public CarController carController() {
        return new CarController(carService(), carDtoService(), excelService());
    }

    @Bean
    public ExcelService excelService() {
        return new ExcelServiceImpl(carRepository, carService());
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(orderRepository);
    }

    @Bean
    public CarDtoService carDtoService() {
        return new CarDtoServiceImpl(carDtoRepository);
    }

    @Bean
    public OrderDtoServiceImpl orderDtoService() {
        return new OrderDtoServiceImpl(orderDtoRepository);
    }

    @Bean
    public CarService carService() {
        return new CarServiceImpl(carRepository);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
               entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.epam.brest.*");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
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

}
