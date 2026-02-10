package com.hcl.ecommerce.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class SessionManager {
    public static void ensureValidSession(WebDriver driver) {
        // Navigate to domain first to allow setting cookies
        try {
            driver.get("https://www.pepperfry.com");
        } catch (Exception e) {
            System.out.println("Error navigating to homepage: " + e.getMessage());
            return;
        }

        try {
            System.out.println("Attempting to load saved session...");
            boolean cookiesLoaded = CookieUtil.loadCookies(driver);

            if (!cookiesLoaded) {
                System.out.println("Cookies not found or failed to load. Forcing manual login...");
                performManualLogin(driver);
                return;
            }

            driver.navigate().refresh();
            Thread.sleep(5000);

            if (!isUserLoggedIn(driver)) {
                System.out.println("Session expired or invalid. Performing fresh login...");
                performManualLogin(driver);
            } else {
                System.out.println("Session valid. Continuing tests.");
            }
        } catch (Exception e) {
            System.out.println("Error verifying session: " + e.getMessage());
            // Do NOT call performManualLogin again here blindly to avoid double loops.
            // If the error was in performManualLogin, we don't want to retry immediately.
        }
    }

    private static boolean isUserLoggedIn(WebDriver driver) {
        try {
            List<WebElement> loginButtons = driver.findElements(By.xpath(
                    "//*[contains(text(),'Login') or contains(text(),'Register') or contains(text(),'Sign In')]"));
            boolean isLoginVisible = false;
            for (WebElement element : loginButtons) {
                if (element.isDisplayed() && (element.getTagName().equals("a") || element.getTagName().equals("button")
                        || element.getTagName().equals("span"))) {
                    if (element.getLocation().getY() < 200) {
                        System.out.println("Found login prompt: " + element.getText());
                        isLoginVisible = true;
                        break;
                    }
                }
            }
            return !isLoginVisible;
        } catch (Exception e) {
            return false;
        }
    }

    private static void performManualLogin(WebDriver driver) {
        System.out.println("Please login manually within 60 seconds...");
        try {
            Thread.sleep(60000);
            CookieUtil.saveCookies(driver);
            System.out.println("Manual login wait over. Cookies saved.");
        } catch (InterruptedException e) {
            System.out.println("Manual login interrupted.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error saving cookies after manual login: " + e.getMessage());
        }
    }
}
