package com.epam.brest.courses.web_app;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root controller.
 */
@Controller
public class HomeController {
    /**
     * Goto car list page.
     *
     * @return view name
     */
    @GetMapping(value = "/")
    public final String selectData() {
        return "findCar";
    }

    /**
     * Goto statistics car list page.
     *
     * @return view name
     */
    @GetMapping(value = "/period")
    public String putPeriod(Model model){
        model.addAttribute("incorrectPeriod", false);
        return "period";
    }
}