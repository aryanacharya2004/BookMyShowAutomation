@login
Feature: Login Module

  Background:
    Given user is on the BMS home page for login
    When city selection modal is displayed for login
    Then user selects default city from the modal

  @ui
  Scenario: Ensure login UI elements are visible and functional
    When user clicks on "Sign In"
    Then "Continue with Mobile Number" option is visible
    And all input fields and buttons are enabled and visible

  @positive
  Scenario Outline: Valid login and back navigation with different numbers
    When user clicks on "Sign In"
    And user enters a valid random mobile number "<validNumber>"
    And user clicks on "Continue"
    Then OTP input screen is displayed
    When user clicks on the back button
    Then user is taken back to the login method selection screen

    Examples:
      | validNumber |
      | 8936820040  |
      | 9876543210  |
      | 9123456789  |

  @negative
  Scenario Outline: Handle invalid login attempt with wrong mobile number
    When user clicks on "Sign In"
    And user enters an invalid mobile number "<invalidNumber>"
    Then the system should show an inline error with message "Invalid mobile number"

    Examples:
      | invalidNumber |
      | 5000000000    |
      | 1111111111    |
      | 2222222222    |
