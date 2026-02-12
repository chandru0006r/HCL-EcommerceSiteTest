package com.hcl.ecommerce.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class WebDriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        if (driver.get() == null) {
            System.out.println("Initializing Thread-Safe WebDriver...");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            WebDriver dr = new ChromeDriver(options);
            dr.manage().window().maximize();
            dr.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(dr);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
