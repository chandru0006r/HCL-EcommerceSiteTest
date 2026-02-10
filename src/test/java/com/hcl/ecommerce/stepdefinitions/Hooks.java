package com.hcl.ecommerce.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.hcl.ecommerce.utils.SessionManager;

import java.time.Duration;

public class Hooks {

    public static WebDriver driver;

    @Before
    public void setup() throws InterruptedException {
        // Singleton pattern: Only initialize if null
        if (driver == null) {
            System.out.println("Initializing Shared WebDriver...");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Perform login/session validation once at the start
            SessionManager.ensureValidSession(driver);
        } else {
            System.out.println("Reusing existing WebDriver session.");
            // Optional: If you strictly need to check session validity before every test:
            // SessionManager.ensureValidSession(driver);
            // But for speed, usually we assume it stays valid or handled by specific steps.
        }
    }

    // Removed @After driver.quit() to prevent closing after every scenario

    @After
    public static void tearDownAll() {
        if (driver != null) {
            System.out.println("All tests finished. Quitting Shared WebDriver...");
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
