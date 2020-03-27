package com.epam.brest.courses.web_app;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.service_api.CarService;
import com.epam.brest.courses.web_app.validators.CarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {

    private CarValidator carValidator = new CarValidator();

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Goto free cars list.
     *
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/cars")
    public final String freeCars( @RequestParam(name = "filter", required = false) String filter, Model model) {

        List<Car> list = carService.findAll();
        model.addAttribute("cars",list);
        return "cars";
    }

    /**
     * Goto edit car page.
     *
     * @param id    car
     * @param model model
     * @return view name
     */
    @GetMapping(value = "/car/{id}")
    public final String gotoEditCarPage(@PathVariable Integer id, Model model) {

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
     * @return redirect to view name
     */
    @PostMapping(value = "/car/{id}")
    public final String updateCar(@Valid Car car, BindingResult result) {
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
        model.addAttribute("isNew", true);
        model.addAttribute("car", new Car());
        return "car";
    }

    /**
     * Create new car.
     *
     * @param car    new car with filled data
     * @param result binding result
     * @return redirect to view name
     */
    @PostMapping(value = "car")
    public final String createCar(@Valid Car car, BindingResult result) {
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
     * @return view name
     */
    @GetMapping(value = "car/{id}/delete")
    public final String deleteCar(@PathVariable Integer id) {
        carService.delete(id);
        return "redirect:/cars";
    }

}

