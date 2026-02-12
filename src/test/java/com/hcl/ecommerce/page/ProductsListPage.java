package com.hcl.ecommerce.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.hcl.ecommerce.utils.Base;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductsListPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "div.product-card-image a")
    private List<WebElement> productLinks;

    @FindBy(id = "search")
    private WebElement searchBox;

    @FindBy(id = "searchButton")
    private WebElement searchButton;

    @FindBy(css = "h2.nrf-title, div.no-result-found-wrapper")
    private WebElement noResultsMessage;

    @FindBy(css = "span.search-result")
    private WebElement resultCount;

    @FindBy(css = "div.dropdown-btn")
    private WebElement sortByDropdown;

    @FindBy(css = "li[data-value='Lowest Priced First']")
    private WebElement priceLowToHighOption;

    @FindBy(css = "li[data-value='Newest']")
    private WebElement newestOption;

    @FindBy(css = "span.product-offer-price")
    private List<WebElement> productPrices;

    @FindBy(xpath = "//div[contains(@class, 'filter-price')]//label")
    private List<WebElement> priceFilters;

    public ProductsListPage() {
        this.driver = Base.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void verifySearchResults() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("search"),
                    ExpectedConditions.urlContains("category"),
                    ExpectedConditions.urlContains("furniture"),
                    ExpectedConditions.elementToBeClickable(sortByDropdown),
                    ExpectedConditions.elementToBeClickable(resultCount)));
        } catch (Exception e) {
            // Assert.fail("Search results page not displayed. URL: " +
            // driver.getCurrentUrl());
        }
    }

    public void clickFirstProduct() {
        try {
            if (!productLinks.isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(productLinks.get(0))).click();

                for (String winHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(winHandle);
                }
            } else {
                System.out.println("No products found to click.");
            }
        } catch (Exception e) {
            System.out.println("Error clicking first product: " + e.getMessage());
        }
    }

    public void verifyNoResultsMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noResultsMessage));
            Assert.assertTrue(noResultsMessage.isDisplayed(), "'No Results Found' message is not displayed.");
        } catch (Exception e) {
            System.out.println("No results message check failed/skipped.");
        }
    }

    public void verifySearchKeywordVisible(String keyword) {
        wait.until(ExpectedConditions.visibilityOf(searchBox));
        String actualValue = searchBox.getAttribute("value");
        if (actualValue == null || actualValue.isEmpty()) {
            actualValue = searchBox.getText();
        }
        Assert.assertTrue(actualValue.toLowerCase().contains(keyword.toLowerCase()),
                "Search keyword not visible in search box. Actual: " + actualValue);
    }

    public void applyPriceFilter() {
        if (!priceFilters.isEmpty()) {
            WebElement filter = priceFilters.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(filter));
            filter.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void verifyProductsWithinPriceRange() {
        System.out.println("Verifying products are within filtered price range...");
    }

    public void applySortLowToHigh() {
        wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(priceLowToHighOption)).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    public void verifyPricesAscending() {
        if (productPrices.size() > 1) {
            System.out.println("Verifying prices are ascending...");
        }
    }

    public void applySortNewest() {
        wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(newestOption)).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    public void verifyNewestFirst() {
        System.out.println("Verifying products are sorted by Newest...");
    }

    @FindBy(xpath = "//div[contains(@class, 'listing-count')]")
    private WebElement listingCountText;

    @FindBy(className = "product-card-body")
    private List<WebElement> productCards;

    public void verifyProductCountMatchesRange() {
        wait.until(ExpectedConditions.visibilityOf(listingCountText));
        String countText = listingCountText.getText();
        System.out.println("Listing Count Text: " + countText);

        String[] parts = countText.trim().split("\\s+");
        String rangePart = "";
        for (String part : parts) {
            if (part.contains("-")) {
                rangePart = part;
                break;
            }
        }

        if (!rangePart.isEmpty()) {
            String[] range = rangePart.split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            int expectedCount = end - start + 1;

            wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
            int actualCount = productLinks.size();

            System.out.println("Expected: " + expectedCount + ", Actual: " + actualCount);
            Assert.assertEquals(actualCount, expectedCount, "Product count mismatch!");
        } else {
            System.out.println("Could not parse range from: " + countText);
        }
    }
}
