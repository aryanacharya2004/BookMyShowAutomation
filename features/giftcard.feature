@giftcard
Feature: Gift Card Selection Module

  Background:
    Given user is on the BMS home page for gift card test
    When city selection modal is displayed for gift card test
    Then user selects a city from the modal for gift card

  @negative
  Scenario Outline: Validate error message for invalid gift card voucher
    Given user navigates to the Gift Card section
    Then user should see the "Check Gift card balance" icon
    When user clicks on the "Check Gift card balance" icon
    And user enters an invalid voucher code "<voucherCode>"
    And user submits the voucher for balance check
    Then user should see an error message "Invalid Gift voucher Code. (#-4426)"

    Examples:
      | voucherCode   |
      | INVALID123    |
   
