@FilterTest
Feature: Product Filter Functionality

  Scenario: User applies product filters
    Given I log in to SauceDemo with username "standard_user" and password "secret_sauce"
    When I apply the filter "Price (low to high)"
    Then I should see products sorted according to "Price (low to high)"
