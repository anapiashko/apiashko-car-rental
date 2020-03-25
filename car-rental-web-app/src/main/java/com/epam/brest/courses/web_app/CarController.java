package com.epam.brest.courses.web_app;

import com.epam.brest.courses.dao.CarDao;
import com.epam.brest.courses.model.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CarController {

    private final CarDao carDao;

    public CarController(CarDao carDao) {
        this.carDao = carDao;
    }

    /**
     *Goto free cars list.
     *
     * @param model
     * @return view name
     */
    @GetMapping(value = "/cars")
    public final String freeCars(Model model) {
        model.addAttribute("cars", carDao.findAll());
        return "cars";
    }

    /**
     * Goto edit car page.
     *
     * @param id car
     * @param model
     * @return view name
     */
    @GetMapping(value = "/car/{id}")
    public final String gotoEditCarPage(@PathVariable Integer id, Model model) {

        Optional<Car> car = carDao.findById(id);

        if(car.isPresent()){
            model.addAttribute("isNew", false);
            model.addAttribute("car", car.get());
        }
        return "car";
    }

    /**
     * Update car.
     *
     * @param car to be updated
     * @param result  binding result
     * @return redirect to view name
     */
    @PostMapping(value = "/car/{id}")
    public final String updateCar(@Valid Car car, BindingResult result){
        carDao.update(car);
        return "redirect:/cars";
    }

    /**
     * Goto create car page.
     *
     * @param model
     * @return view name
     */
    @GetMapping(value = "/car")
    public final String gotoCreateCarPage(Model model){
        model.addAttribute("isNew", true);
        model.addAttribute("car", new Car());
        return "car";
    }

    /**
     * Create new car.
     *
     * @param car new car with filled data
     * @param result  binding result
     * @return redirect to view name
     */
    @PostMapping(value = "car")
    public final String createCar(@Valid Car car, BindingResult result){
        carDao.create(car);
        return "redirect:/cars";
    }

    /**
     * Delete car.
     *
     * @param id car
     * @return view name
     */
    @DeleteMapping(value = "car/{id}")
    public final String deleteCar(@PathVariable Integer id){
        carDao.delete(id);
        return "redirect:/cars";
    }
}

