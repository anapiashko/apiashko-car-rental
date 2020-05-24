package com.epam.brest.courses.rest_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController {

    @GetMapping("/gen")
    public String generate(@RequestParam(value = "number", defaultValue = "1") Integer number){
        return "generate\t" + number;
    }
}
