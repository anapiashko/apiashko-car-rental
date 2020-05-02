package com.epam.brest.courses.dao.config;

import com.epam.brest.courses.dao.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

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

}
