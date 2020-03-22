package com.epam.brest.courses.web_app;

import org.springframework.stereotype.Controller;
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
}