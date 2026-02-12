package com.hcl.ecommerce.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.hcl.ecommerce.page.ProductPage;
import com.hcl.ecommerce.page.ProductsListPage;
import com.hcl.ecommerce.page.Homepage;

public class ProductSteps {

    ProductPage productPage = new ProductPage();
    ProductsListPage listPage = new ProductsListPage();
    Homepage homepage = new Homepage();

    @Given("the user clicks on a product from listing page")
    public void the_user_clicks_on_a_product_from_listing_page() {
        homepage.navigateToHomePage();
        homepage.searchForProduct("chair");
        listPage.clickFirstProduct();
    }

    @Given("the user is on product detail page")
    public void the_user_is_on_product_detail_page() {
        homepage.navigateToHomePage();
        homepage.searchForProduct("chair");
        listPage.clickFirstProduct();
        productPage.verifyProductDetails();
    }

    @Then("product title, price, description and images should be displayed")
    public void product_title_price_description_and_images_should_be_displayed() {
        productPage.verifyProductDetails();
    }

    @Then("product specifications should be clearly visible")
    public void product_specifications_should_be_clearly_visible() {
        productPage.verifySpecifications();
    }

    @Given("the user is on product detail page of an out-of-stock item")
    public void the_user_is_on_product_detail_page_of_an_out_of_stock_item() {
        homepage.navigateToHomePage();
        homepage.searchForProduct("out of stock item"); // Placeholder
        listPage.clickFirstProduct();
    }

    @When("the user hovers over product image")
    public void the_user_hovers_over_product_image() {
        productPage.hoverOverImage();
    }

    @Then("zoomed image preview should be displayed")
    public void zoomed_image_preview_should_be_displayed() {
        productPage.verifyZoomedImage();
    }

    @Then("'Out of Stock' message should be displayed")
    public void out_of_stock_message_should_be_displayed() {
        productPage.verifyOutOfStock();
    }

    @Then("Add to Cart button should be disabled")
    public void add_to_cart_button_should_be_disabled() {
        productPage.verifyAddToCartDisabled();
    }
}
