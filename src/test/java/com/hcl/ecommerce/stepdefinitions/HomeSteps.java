package com.hcl.ecommerce.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import com.hcl.ecommerce.page.Homepage;

public class HomeSteps {
    Homepage homepage = new Homepage();

    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        String userName = homepage.verifyloggedin();
        System.out.println("User is logged in as: " + userName);
    }

    @Given("the user is on the homepage")
    public void the_user_is_on_the_homepage() {
        homepage.navigateToHomePage();
    }

    @When("the user enters a valid product keyword in search bar")
    public void the_user_enters_a_valid_product_keyword_in_search_bar() {
        homepage.searchForProduct("Study Table");
    }

    @When("the user searches for a non-existing product")
    public void the_user_searches_for_a_non_existing_product() {
        homepage.searchForProduct("xyz123abc");
    }
}
