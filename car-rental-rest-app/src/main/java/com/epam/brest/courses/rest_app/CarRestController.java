package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.rest_app.exception.ErrorResponse;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.service_api.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.courses.rest_app.exception.CustomExceptionHandler.CAR_NOT_FOUND;

@RestController
public class CarRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarRestController.class);

    private final CarService carService;

    private final ExcelService excelService;

    @Autowired
    public CarRestController(CarService carServices, ExcelService excelService) {
        this.carService = carServices;
        this.excelService = excelService;
    }

    /**
     * Find all free cars on date.
     *
     * @param filter filter(date)
     * @return list of cars
     */
    @GetMapping(value = "/cars/filter/{filter}")
    public ResponseEntity<List<Car>> freeCars(@PathVariable(name = "filter")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate filter) {
        LOGGER.debug("find all free cars on date  = {}", filter);

        return new ResponseEntity<>(carService.findAllByDate(filter), HttpStatus.OK);
    }

    @GetMapping(value = "cars/download/cars.xlsx")
    public ResponseEntity<InputStreamResource> excelCarsReport() throws IOException {
        LOGGER.debug("export car table to excel sheet ()");

        List<Car> cars = carService.findAll();

        ByteArrayInputStream in = excelService.carsToExcel(cars);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=cars.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping(value = "/cars/import_xlsx")
    public List<Car> uploadFromExcel(@RequestParam(value = "filename", required = false) String filename) {
        LOGGER.debug("import excel sheet to car table)");

        List<Car> cars = excelService.excelToCars(filename);

        return cars;
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
