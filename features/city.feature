@city
Feature: City Selection Module

  Background:
    Given user is on the BMS home page
    When city selection modal is displayed
    Then user selects a city from the modal

  @ui
  Scenario: Ensure selected city is displayed beside Sign In
    Given user has selected a city from the modal
    Then the selected city should be displayed beside the "Sign In" option
    When user changes the city from the dropdown
    Then the newly selected city should be displayed beside "Sign In"

  @positive
  Scenario Outline: Search for a city by valid name
    When user clicks on the city dropdown
    And user enters a valid city name "<city>" in the search field
    Then the search results should display relevant cities
    And user should be able to select the city from search results

    Examples:
      | city        |
      | Mumbai      |
      | Delhi       |
      | Bangalore   |
      | Hyderabad   |

  @negative
  Scenario Outline: Search for a city by invalid/non-existing name
    When user clicks on the city dropdown
    And user enters an invalid city name "<invalidCity>" in the search field
    Then the system should display a "No results found." message

    Examples:
      | invalidCity   |
      | Atlantis      |
      | Gotham        |
      | Wakanda       |

  @ui
  Scenario: View all cities and validate few city names
    When user clicks on city name dropdown
    And user clicks on city option "View All Cities"
    Then the list of all cities should be displayed
    And popular cities should be shown at the top
    And validate that cities like "<city1>", "<city2>", "<city3>" are visible

    Examples:
      | city1       | city2        | city3     |
      | Chandigarh  | Bhubaneswar  | Kochi     |
      | Pune        | Lucknow      | Jaipur    |
