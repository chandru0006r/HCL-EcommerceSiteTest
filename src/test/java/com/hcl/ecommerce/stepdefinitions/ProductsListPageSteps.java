package com.hcl.ecommerce.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.hcl.ecommerce.page.ProductsListPage;
import com.hcl.ecommerce.page.Homepage;

public class ProductsListPageSteps {
    ProductsListPage resultsPage = new ProductsListPage();
    Homepage homepage = new Homepage();

    @Then("relevant product results should be displayed")
    public void relevant_product_results_should_be_displayed() {
        resultsPage.verifySearchResults();
    }

    @Then("the system should display 'No Results Found' message")
    public void the_system_should_display_no_results_found_message() {
        resultsPage.verifyNoResultsMessage();
    }

    @Then("search keyword should be visible in results page")
    public void search_keyword_should_be_visible_in_results_page() {
        resultsPage.verifySearchKeywordVisible("study table");
    }

    @Given("the user is on a product listing page")
    public void the_user_is_on_a_product_listing_page() {
        homepage.navigateToHomePage();
        homepage.searchForProduct("sofa");
        resultsPage.verifySearchResults();
    }

    @When("the user selects sort option 'Price: Low to High'")
    public void the_user_selects_sort_option_price_low_to_high() {
        resultsPage.applySortLowToHigh();
    }

    @Then("products should be arranged in ascending price order")
    public void products_should_be_arranged_in_ascending_price_order() {
        resultsPage.verifyPricesAscending();
    }

    @When("the user selects sort option 'Newest'")
    public void the_user_selects_sort_option_newest() {
        resultsPage.applySortNewest();
    }

    @Then("products should be arranged in newest first order")
    public void products_should_be_arranged_in_newest_first_order() {
        resultsPage.verifyNewestFirst();
    }

    @Then("the displayed product count should match the results range")
    public void the_displayed_product_count_should_match_the_results_range() {
        resultsPage.verifyProductCountMatchesRange();
    }
}
