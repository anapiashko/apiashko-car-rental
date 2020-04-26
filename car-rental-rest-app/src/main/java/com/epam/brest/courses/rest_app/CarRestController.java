package com.epam.brest.courses.rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarRestController {

    private final static String VERSION = "1.0.1";

    @GetMapping(value = "/version")
    public String version(){
        return VERSION;
    }
}
