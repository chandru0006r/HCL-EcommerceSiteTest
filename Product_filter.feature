Feature: Product Filtering

    Background:
        Given the user is on a product listing page
        
  Scenario: Sort products by Newest
    When the user selects sort option 'Newest'
    Then products should be arranged in newest first order

  Scenario: Sort products by price low to high
    When the user selects sort option 'Price: Low to High'
    Then products should be arranged in ascending price order
