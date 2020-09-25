package com.epam.brest.courses.web_app;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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

    /**
     * Logout.
     *
     * @param request httpServletRequest
     * @return redirect to home page
     * @throws ServletException exception
     */
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/";
    }
}