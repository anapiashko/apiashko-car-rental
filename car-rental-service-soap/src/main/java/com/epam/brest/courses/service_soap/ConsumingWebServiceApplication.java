package com.epam.brest.courses.service_soap;

import com.epam.brest.courses.service_soap.wsdl.GetAllCarsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumingWebServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumingWebServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(CarClient carClient) {
        return args -> {
            System.out.println("--- Get all Cars ---");
            GetAllCarsResponse allArticlesResponse = carClient.getAllCars();
            allArticlesResponse.getCarInfo().stream()
                    .forEach(e -> System.out.println(e.getId() + ", "
                            + e.getBrand() + ", " + e.getRegisterNumber()
                            + ", " + e.getPrice()));
        };
    }
}
