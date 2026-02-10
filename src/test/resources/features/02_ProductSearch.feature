Feature: Product Search & Filtering

  Scenario: Search product using valid keyword
    Given the user is on the homepage
    When the user enters a valid product keyword in search bar
    And clicks search
    Then relevant product results should be displayed
    And search keyword should be visible in results page

  Scenario: Search with no matching results
    Given the user is on the homepage
    When the user searches for a non-existing product
    Then the system should display 'No Results Found' message
    And suggest alternative products or categories

  Scenario: Filter products by price range
    Given the user is viewing product listing page
    When the user selects a specific price range filter
    Then the system should display only products within selected price range
    And update result count dynamically

  Scenario: Sort products by price low to high
    Given the user is on a product listing page
    When the user selects sort option 'Price: Low to High'
    Then products should be arranged in ascending price order
