package com.epam.brest.courses;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class CarTest extends WebDriverSettings {

    @Test
    public void addingNewCarTest() {

        driver.get("http://localhost:8080");

        driver.findElement(By.id("filter-button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[href=\"/car\"]")));

        driver.findElement(By.cssSelector("[href=\"/car\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("carForm")));

        driver.findElement(By.id("field_carBrand")).sendKeys("Audi");

        Random random = new Random();
        int r = random.nextInt(10000) + 1000;
        System.out.println("r = " + r);

        driver.findElement(By.id("field_carRegisterNumber")).sendKeys(r + " RC-1");
        System.out.println(r + " RC-1");

        driver.findElement(By.id("field_carPrice")).sendKeys(r / 100 + "");
        System.out.println(r / 100);

        driver.findElement(By.xpath("//form[@id=\"carForm\"]/div[4]/a[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter-button")));

    }
}
