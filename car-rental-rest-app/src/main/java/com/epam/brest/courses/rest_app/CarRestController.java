package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.CAR_NOT_FOUND;

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
    public ResponseEntity<List<Car>> freeCars(@PathVariable(name = "filter") String filter, Model model) {
        LOGGER.debug("find all free cars on date  = {}", filter);

        model.addAttribute("filter", filter);
        return new ResponseEntity<>(carService.findAllByDate(filter), HttpStatus.OK);
    }

    @GetMapping(value = "/cars/{id}")
    public ResponseEntity<Car> findById(@PathVariable(name = "id") Integer id) {
        LOGGER.debug("find car by id({})", id);

      //  return carService.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        Optional<Car> optional = carService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity(
                new ErrorResponse(CAR_NOT_FOUND,
                        Arrays.asList("Car not found for id:" + id)),
                HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/cars/{id}", consumes = "application/json", produces = "application/json")
    public final ResponseEntity<Integer> update(@RequestBody Car car) {
        LOGGER.debug("update car({})", car);

        return new ResponseEntity<>(carService.update(car), HttpStatus.OK);
    }

    @PostMapping(value = "/cars", consumes = "application/json", produces = "application/json")
    public final ResponseEntity<Integer> create(@RequestBody Car car) {
        LOGGER.debug("create car({})", car);
        return new ResponseEntity<>(carService.create(car), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/cars/{id}", produces = {"application/json"})
    public final ResponseEntity<Integer> delete(@PathVariable(name = "id") Integer id) {
        LOGGER.debug("delete car({})", id);

        int result = carService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
