package com.hcl.ecommerce;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class PepperFryTest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testPepperFryTitle() {
        driver.get("https://www.pepperfry.com/");
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        // Note: Title might vary, so a flexible assertion is better initially
        Assert.assertTrue(title.toLowerCase().contains("pepperfry"), "Title does not contain 'Pepperfry'");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
