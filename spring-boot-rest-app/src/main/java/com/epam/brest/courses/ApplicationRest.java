package com.epam.brest.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApplicationRest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRest.class);//запуск приложения

    }
}