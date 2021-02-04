package com.epam.brest.courses;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSettings {

    protected WebDriver driver;

    private String chromeDriverPath = "src\\test\\java\\com\\epam\\brest\\courses\\web_drivers\\chromedriver.exe";

    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @After
    public void close(){
        driver.quit();
    }
}
