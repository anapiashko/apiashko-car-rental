package com.epam.brest.courses.rest_app;

import com.epam.brest.courses.service_api.FakeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateController {

    private final FakeService fakeService;

    public GenerateController(FakeService fakeService) {
        this.fakeService = fakeService;
    }

    @GetMapping("/gen")
    public String generate(@RequestParam(value = "number", defaultValue = "1") Integer number){
        fakeService.createSampleData(number);
        return "generate\t" + number;
    }
}
