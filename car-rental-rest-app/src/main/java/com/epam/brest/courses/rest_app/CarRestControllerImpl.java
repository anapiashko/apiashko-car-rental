package com.epam.brest.courses.rest_app;

import com.epam.brest.course.rest_app_api.CarRestController;
import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarRestControllerImpl implements CarRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestControllerImpl.class);

    private final CarService carService;

    private final static String VERSION = "1.0.1";

    @Autowired
    public CarRestControllerImpl(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(value = "/cars")
    public List<Car> version(){
        return carService.findAll();
    }
}
