package com.epam.brest.courses.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.epam.brest.courses.*")
@ComponentScan("com.epam.brest.courses.*")
public class TestConfig {

}
