@movie
Feature: Movie Selection Module

  Background:
    Given user is on the BMS home page for movie test
    When city selection modal is displayed for movie test
    Then user selects a city from the modal for movie

  Scenario: Validate recommended movie selection on home page
    Given user notes the first recommended movie title
    When user clicks on the first recommended movie card
    Then user should see the same movie title on the movie detail page

  Scenario: Validate explore upcoming movies and in-cinemas section
    Given user navigates to the Movies section from navbar
    When user clicks on the "Explore Upcoming Movies" section
    Then user should see the "In Cinemas Near You" section on the page

    
Scenario: Validate UI elements on movie screen
    When Verify navbar links are visible and working
    And I click on the Movies tab
    And Verify "Filters" header is visible
    And Verify Genre filter is visible