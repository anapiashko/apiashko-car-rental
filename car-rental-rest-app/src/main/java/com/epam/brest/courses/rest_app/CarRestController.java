package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.exception.CarNotFoundException;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@RestController
public class CarRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestController.class);

    private final CarService carService;

    @Autowired
    public CarRestController(CarService carServices) {
        this.carService = carServices;
    }

    @GetMapping(value = "/cars/filter/{filter}")
    public List<Car> freeCars(@PathVariable(name = "filter") String filter, Model model) {
        LOGGER.debug("find all free cars on date  = {}", filter);

        model.addAttribute("filter", filter);
        return carService.findAllByDate(filter);
    }

    @GetMapping(value = "/cars/{id}")
    public Car findById(@PathVariable(name = "id") Integer id){
        LOGGER.debug("find car by id({})", id);

        return carService.findById(id).orElseThrow(()->new CarNotFoundException(id));
    }

    @PostMapping(value = "/cars/{id}/update", consumes = "application/json", produces = "application/json")
    public final Integer update(@RequestBody Car car) {
        LOGGER.debug("update car({})", car );

        return carService.update(car);
    }

    @PostMapping(value = "/cars", consumes = "application/json", produces = "application/json")
    public final Integer create(@RequestBody Car car) {
        LOGGER.debug("create car({})", car);
        return carService.create(car);
    }

    @DeleteMapping(value = "/cars/{id}", produces = {"application/json"})
    public final Integer delete(@PathVariable(name = "id") Integer id){
        LOGGER.debug("delete car({})", id);

        int result =  carService.delete(id);;
        return result;
    }
}
