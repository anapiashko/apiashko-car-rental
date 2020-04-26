package com.epam.brest.courses.spring_boot_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.brest")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);//запуск приложения

    }
}