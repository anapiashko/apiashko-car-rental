package com.epam.brest.courses.rest_app;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features =
        {"src/test/resources/features/CarRestAPI.feature",
                "src/test/resources/features/OrderRestAPI.feature",
                "src/test/resources/features/CarDtoRestAPI.feature",
                "src/test/resources/features/OrderDtoRestAPI.feature"},
        glue = "com.epam.brest.courses.rest_app")
public class CucumberTest {
}