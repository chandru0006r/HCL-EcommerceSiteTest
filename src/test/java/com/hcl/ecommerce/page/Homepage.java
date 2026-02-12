package com.hcl.ecommerce.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.hcl.ecommerce.utils.Base;

public class Homepage {

    WebDriver driver = Base.getDriver();

    public void navigateToHomePage() {
        driver.get("https://www.pepperfry.com/");
    }

    public void searchForProduct(String keyword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBox.clear();
            searchBox.sendKeys(keyword, Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Search box interaction failed: " + e.getMessage());
        }
    }

    public String verifyloggedin() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement userElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//div[@class='loggedIn-users-name cursor-pointer']/div[@class='cursor-pointer userName']")));
            return userElement.getText();
        } catch (Exception e) {
            return null;
        }
    }
}
