package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarDtoService;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.web_app.validators.CarValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Car web controller.
 */
@Controller
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private CarValidator carValidator = new CarValidator();

    private final CarService carService;

    private final CarDtoService carDtoService;

    private static LocalDate date  = LocalDate.now();

    @Autowired
    public CarController(CarService carService, CarDtoService carDtoService) {
        this.carService = carService;
        this.carDtoService = carDtoService;
    }

    /**
     * Goto free cars list.
     *
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/cars")
    public final String freeCars(@RequestParam(name = "filter", required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate, Model model) {
        LOGGER.debug("free cars on date: {}", localDate);

        if(localDate != null) {
            date = localDate;
        }

        List<Car> cars = carService.findAllByDate(date);
        model.addAttribute("filter", date);
        model.addAttribute("cars",cars);
        return "cars";
    }

    /**
     * Goto edit car page.
     *
     * @param id    car
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/cars/{id}")
    public final String gotoEditCarPage(@PathVariable Integer id, Model model) {
        LOGGER.debug("gotoEditCarPage({},{})", id, model);

        Optional<Car> car = carService.findById(id);

        if (car.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("car", car.get());
            return "car";
        } else {
            return "redirect:/cars";
        }
    }

    /**
     * Update car.
     *
     * @param car    to be updated
     * @param result binding result
     * @return view name
     */
    @PostMapping(value = "/cars/{id}")
    public final String updateCar(@Valid Car car, BindingResult result) {
        LOGGER.debug("updateCar({}, {})", car, result);

        carValidator.validate(car, result);
        if (result.hasErrors()) {
            return "car";
        } else {
            carService.update(car);
            return "redirect:/cars";
        }
    }

    /**
     * Goto create car page.
     *
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/car")
    public final String gotoCreateCarPage(Model model) {
        LOGGER.debug("gotoCreateCarPage({})", model);

        model.addAttribute("isNew", true);
        model.addAttribute("car", new Car());
        return "car";
    }

    /**
     * Create new car.
     *
     * @param car    new car with filled data
     * @param result binding result
     * @return view name
     */
    @PostMapping(value = "car")
    public final String createCar(@Valid Car car, BindingResult result) {
        LOGGER.debug("createCar({}, {})", car, result);

        carValidator.validate(car, result);
        if (result.hasErrors()) {
            return "car";
        } else {
            carService.create(car);
            return "redirect:/cars";
        }
    }

    /**
     * Delete car.
     *
     * @param id car
     * @return redirect to view name
     */
    @GetMapping(value = "cars/{id}/delete")
    public final String deleteCar(@PathVariable Integer id, Model model) {
        LOGGER.debug("deleteCar({})", id);
        try {
            carService.delete(id);
            return "redirect:/cars";
        } catch (HttpServerErrorException e) {
            model.addAttribute("error", "There is order for this car");
            return freeCars(date, model);
        }
    }

    /**
     * Show cars with number of orders.
     *
     * @param dateFrom date from
     * @param dateTo date to
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/car-statistics")
    public String carStatistics(@RequestParam(value="dateFrom",required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
                                @RequestParam(value = "dateTo",required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo,
                                Model model){
        LOGGER.debug("carStatistics between (dateFrom = {}, dateTo = {}), model = {}", dateFrom, dateTo, model);

        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);

        if (dateFrom == null || dateTo == null || dateTo.isBefore(dateFrom)) {
            model.addAttribute("incorrectPeriod", true);
            return "period";
        }

        model.addAttribute("cars", carDtoService.findAllWithNumberOfOrders(dateFrom, dateTo));
        return "statistics";
    }

}

