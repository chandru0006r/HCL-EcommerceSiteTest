Feature: Product Search

  Background:
    Given the user is on the homepage

  Scenario: Search product using valid keyword
    When the user enters a valid product keyword in search bar
    Then relevant product results should be displayed
    And search keyword should be visible in results page

  Scenario: Search with no matching results
    When the user searches for a non-existing product
    Then the system should display 'No Results Found' message

  Scenario: Verify displayed product count matches the result range
    When the user enters a valid product keyword in search bar
    Then the displayed product count should match the results range
