Feature: Google Search Functionality

  As a user
  I want to search on Google
  So that I can find information on the internet

  Scenario: Perform a basic search
    Given I am on the Google homepage
    When I enter "Playwright testing" into the search box
    And I click the search button
    Then I should see search results

  Scenario: Use the "I'm Feeling Lucky" button
    Given I am on the Google homepage
    When I enter "Playwright documentation" into the search box
    And I click the "I'm Feeling Lucky" button
    Then I should be taken directly to a relevant page

  Scenario: Verify Google Search page elements
    Given I am on the Google homepage
    Then I should see the Google logo
    And I should see the search box
    And I should see the "Google Search" button
    And I should see the "I'm Feeling Lucky" button