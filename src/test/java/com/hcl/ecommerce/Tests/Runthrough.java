package com.hcl.ecommerce.Tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Runthrough {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.out.println("Initializing Fresh WebDriver for Runthrough...");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.pepperfry.com/");


    }

    @org.testng.annotations.Test
    public void testHomePageTitle() {

        Assert.assertTrue(driver.getTitle().contains("Pepperfry"));
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pf-authentication")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='close-modal']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search"))).sendKeys("chair",
                org.openqa.selenium.Keys.ENTER);


        String countText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='color-secondary listing-count font-medium text-sm ng-star-inserted']"))).getText();
        System.out.println("Listing Count: " + countText);

        String[] parts = countText.trim().split("\\s+");
        String[] range = parts[1].split("-");
        int expectedCount = Integer.parseInt(range[1]) - Integer.parseInt(range[0]) + 1;

        java.util.List<org.openqa.selenium.WebElement> products = driver.findElements(By.className("product-name"));
        products.forEach(element -> System.out.println(element.getText()));
        Assert.assertEquals(products.size(), expectedCount);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

