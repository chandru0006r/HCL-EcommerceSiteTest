Feature: Product Details Page

  Scenario: Verify product details display
    Given the user clicks on a product from listing page
    Then product title, price, description and images should be displayed
    And product specifications should be clearly visible

  Scenario: Verify product image zoom functionality
    Given the user is on product detail page
    When the user hovers over product image
    Then zoomed image preview should be displayed

  Scenario: Verify out of stock product behavior
    Given the user is on product detail page of an out-of-stock item
    Then 'Out of Stock' message should be displayed
    And Add to Cart button should be disabled
