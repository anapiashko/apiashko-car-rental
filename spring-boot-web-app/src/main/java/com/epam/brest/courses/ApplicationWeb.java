package com.epam.brest.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApplicationWeb {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class);//запуск приложения

    }
}