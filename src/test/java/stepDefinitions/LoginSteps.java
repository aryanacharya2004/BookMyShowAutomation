package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LoginPage;

public class LoginSteps {

    private LoginPage loginPage = new LoginPage();

    // ======= Background Steps =======

    @Given("user is on the BMS home page for login")
    public void user_is_on_the_bms_home_page() {
        loginPage.openHome();
    }

    @When("city selection modal is displayed for login")
    public void city_selection_modal_is_displayed() {
        // Modal detection handled in selectCity()
    }

    @Then("user selects default city from the modal")
    public void user_selects_default_city_from_the_modal() {
        loginPage.selectCity("Delhi");
    }

    // ======= UI Verification Steps =======

    @When("user clicks on {string}")
    public void user_clicks_on(String option) {
        if (option.equalsIgnoreCase("Sign In")) {
            loginPage.openLogin();
        } else if (option.equalsIgnoreCase("Continue")) {
            loginPage.clickContinue();
        } else {
            throw new IllegalArgumentException("Unsupported click option: " + option);
        }
    }

    @Then("{string} option is visible")
    public void option_is_visible(String text) {
        Assert.assertTrue("Expected option not visible: " + text,
                loginPage.isContinueWithMobileVisible());
    }

    @Then("all input fields and buttons are enabled and visible")
    public void all_input_fields_and_buttons_are_enabled_and_visible() {
        Assert.assertTrue("Login UI not visible or enabled",
                loginPage.isLoginUiVisible());
    }

    // ======= Positive Login Scenario Steps =======

    @When("user enters a valid random mobile number {string}")
    public void user_enters_a_valid_random_mobile_number(String mobile) {
        loginPage.enterMobile(mobile);
    }

    @Then("OTP input screen is displayed")
    public void otp_input_screen_is_displayed() {
        Assert.assertTrue("OTP screen not displayed",
                loginPage.isOtpScreenDisplayed());
    }

    @When("user clicks on the back button")
    public void user_clicks_on_the_back_button() {
        loginPage.clickBack();
    }

    @Then("user is taken back to the login method selection screen")
    public void user_is_taken_back_to_the_login_method_selection_screen() {
        Assert.assertTrue("Back navigation failed",
                loginPage.isBackOnLoginOptions());
    }

    // ======= Negative Login Scenario Steps =======

    @When("user enters an invalid mobile number {string}")
    public void user_enters_an_invalid_mobile_number(String mobile) {
        loginPage.enterMobile(mobile);
    }

    @Then("the system should show an inline error with message {string}")
    public void the_system_should_show_an_inline_error_with_message(String msg) {
        Assert.assertTrue("Inline error message not matching: " + msg,
                loginPage.isInlineErrorVisible(msg));
    }
}
