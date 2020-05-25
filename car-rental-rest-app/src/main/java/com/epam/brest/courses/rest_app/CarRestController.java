package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.epam.brest.courses.service_api.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.CAR_NOT_FOUND;

@RestController
public class CarRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestController.class);


    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 15;
    private static final int[] PAGE_SIZES = { 5, 10};

    private final CarService carService;

    @Autowired
    public CarRestController(CarService carServices) {
        this.carService = carServices;
    }

    /**
     * Find all free cars on date.
     *
     * @param filter filter(date)
     * @return list of cars
     */
    @GetMapping(value = "/cars/filter/{filter}")
    public ResponseEntity<List<Car>> freeCars(@PathVariable(name = "filter")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate filter
                                              ,@RequestParam("page") Optional<Integer> pageNumber
                                              ,@RequestParam("pageSize") Optional<Integer> pageSize) {
        LOGGER.debug("find all free cars on date  = {}", filter);

        int size = pageSize.orElse(INITIAL_PAGE_SIZE);
        int page = (pageNumber.orElse(0) < 1) ? INITIAL_PAGE : pageNumber.get() - 1;

        return new ResponseEntity<>(carService.findAllByDate(filter, PageRequest.of(page, size)), HttpStatus.OK);
    }

    /**
     * Find car bi id.
     *
     * @param id carId
     * @return car
     */
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

    /**
     * Update car.
     *
     * @param car car
     * @return number changed records
     */
    @PutMapping(value = "/cars/{id}", consumes = "application/json", produces = "application/json")
    public final ResponseEntity<Integer> update(@RequestBody Car car) {
        LOGGER.debug("update car({})", car);

        return new ResponseEntity<>(carService.update(car), HttpStatus.OK);
    }

    /**
     * Create car.
     *
     * @param car car
     * @return id created car
     */
    @PostMapping(value = "/cars", consumes = "application/json", produces = "application/json")
    public final ResponseEntity<Car> create(@RequestBody Car car) {
        LOGGER.debug("create car({})", car);
        return new ResponseEntity<>(carService.create(car), HttpStatus.CREATED);
    }

    /**
     * Delete car.
     *
     * @param id carId
     * @return number deleted records
     */
    @DeleteMapping(value = "/cars/{id}", produces = {"application/json"})
    public final ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
        LOGGER.debug("delete car({})", id);

        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
