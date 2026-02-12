package com.hcl.ecommerce.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.time.Duration;

public class Base {

    public static WebDriver driver;
    private static final String COOKIE_FILENAME = "src/test/resources/Cookies.data";

    @Before
    public void setup() {
        WebDriverFactory.initDriver();
        driver = WebDriverFactory.getDriver();

        getDriver().get("https://www.pepperfry.com/");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("pf-authentication")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='close-modal']"))).click();

        // File cookieFile = new File(COOKIE_FILENAME);

        // if (cookieFile.exists()) {
        // System.out.println("Saved Session found. Restoring...");

        // CookieUtil.loadCookiesFromFile(driver);

        // LocalStorageUtils.loadStorageFromFile(driver);

        // driver.navigate().refresh();

        // verifyLogin();

        // } else {
        // System.out.println("!!! NO SESSION FOUND !!!");
        // System.out.println("Please log in MANUALLY in the browser within the next 60
        // seconds...");

        // try {
        // Thread.sleep(60000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        // System.out.println("Time is up! Saving your session...");

        // CookieUtil.saveCookiesToFile(driver);

        // LocalStorageUtils.saveStorageToFile(driver);
        // }
    }

    private void verifyLogin() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
            WebElement userElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".loggedIn-users-name")));

            System.out.println("SUCCESS: User is logged in as: " + userElement.getText());
        } catch (Exception e) {
            System.out
                    .println("FAILURE: User is NOT logged in. Storage injection might have failed or session expired.");
        }
    }

    @After
    public void tearDown() {
        WebDriverFactory.quitDriver();
        driver = null;
    }

    public static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }
}