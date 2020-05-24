package com.epam.brest.courses.rest_app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.epam.brest.courses.*")
@ComponentScan("com.epam.brest.courses.*")
public class TestConfig {
}
