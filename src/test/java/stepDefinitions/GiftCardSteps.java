package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.GiftCardPage;

public class GiftCardSteps {

    private GiftCardPage giftCardPage = new GiftCardPage();

    // ======= Background Steps =======

    @Given("user is on the BMS home page for gift card test")
    public void user_is_on_the_bms_home_page_for_gift_card_test() {
        giftCardPage.openHome();
    }

    @When("city selection modal is displayed for gift card test")
    public void city_selection_modal_is_displayed_for_gift_card_test() {
        Assert.assertTrue("City modal not displayed", giftCardPage.isCityModalVisible());
    }

    @Then("user selects a city from the modal for gift card")
    public void user_selects_a_city_from_the_modal_for_gift_card() {
        giftCardPage.selectCity("Delhi"); // default selection
    }

    // ======= Scenario Steps =======

    @Given("user navigates to the Gift Card section")
    public void user_navigates_to_the_gift_card_section() {
        giftCardPage.navigateToGiftCardSection();
    }

    @Then("user should see the {string} icon")
    public void user_should_see_the_icon(String iconText) {
        Assert.assertTrue(iconText + " icon not visible", giftCardPage.isCheckBalanceIconVisible());
    }

    @When("user clicks on the {string} icon")
    public void user_clicks_on_the_icon(String iconText) {
        giftCardPage.clickCheckBalanceIcon();
    }

    @When("user enters an invalid voucher code {string}")
    public void user_enters_an_invalid_voucher_code(String code) {
        giftCardPage.enterVoucherCode(code);
    }

    @When("user submits the voucher for balance check")
    public void user_submits_the_voucher_for_balance_check() {
        giftCardPage.submitVoucher();
    }

    @Then("user should see an error message {string}")
    public void user_should_see_an_error_message(String expectedMsg) {
        String actualMsg = giftCardPage.getErrorMessage();
        Assert.assertTrue("Expected error message to contain: " + expectedMsg 
                          + " but was: " + actualMsg,
                          actualMsg.contains(expectedMsg));
    }

    
}
