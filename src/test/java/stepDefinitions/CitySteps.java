package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.CityPage;

public class CitySteps {

    private CityPage cityPage = new CityPage();
    private String expectedCity; // store last selected city

    // ======= Background Steps =======

    @Given("user is on the BMS home page")
    public void user_is_on_the_bms_home_page() {
        cityPage.openHome();
    }

    @When("city selection modal is displayed")
    public void city_selection_modal_is_displayed() {
        Assert.assertTrue("City modal not displayed", cityPage.isCityModalVisible());
    }

    // Add an alias to cover "Given user has selected a city from the modal"
    @Then("user selects a city from the modal")
    @Given("user has selected a city from the modal")
    public void user_selects_a_city_from_the_modal() {
        expectedCity = "Delhi"; // Default selection
        cityPage.selectCity(expectedCity);
    }

    // ======= UI Verification Steps =======

    @Then("the selected city should be displayed beside the {string} option")
    public void the_selected_city_should_be_displayed_beside_the_option(String option) {
        String displayedCity = cityPage.getDisplayedCity();
        Assert.assertNotNull("City not displayed beside " + option, displayedCity);
        Assert.assertEquals(expectedCity, displayedCity);
    }

    @When("user changes the city from the dropdown")
    public void user_changes_the_city_from_the_dropdown() {
        expectedCity = "Mumbai"; // Update expected city
        cityPage.changeCity(expectedCity);
    }

    @Then("the newly selected city should be displayed beside {string}")
    public void the_newly_selected_city_should_be_displayed_beside(String option) {
        Assert.assertEquals(expectedCity, cityPage.getDisplayedCity());
    }

    // ======= Positive Search Scenario Steps =======

    @When("user clicks on city name dropdown")
    @When("user clicks on the city dropdown")
    public void user_clicks_on_the_city_dropdown() {
        cityPage.openCityDropdown();
    }

    @When("user enters a valid city name {string} in the search field")
    public void user_enters_a_valid_city_name_in_the_search_field(String city) {
        cityPage.searchCity(city);
    }

    @Then("the search results should display relevant cities")
    public void the_search_results_should_display_relevant_cities() {
        Assert.assertTrue("No valid cities found in search", cityPage.isSearchResultValid());
    }

    @Then("user should be able to select the city from search results")
    public void user_should_be_able_to_select_the_city_from_search_results() {
        cityPage.selectSearchedCity();
    }

    // ======= Negative Search Scenario Steps =======

    @When("user enters an invalid city name {string} in the search field")
    public void user_enters_an_invalid_city_name_in_the_search_field(String invalidCity) {
        cityPage.searchCity(invalidCity);
    }

    @Then("the system should display a {string} message")
    public void the_system_should_display_a_message(String errorMsg) {
        Assert.assertTrue("Error message not displayed", cityPage.isErrorMessageVisible(errorMsg));
    }

    // ======= View All Cities Validation =======

    @When("user clicks on city option {string}")
    public void user_clicks_on_city_option(String option) {
        if (option.equalsIgnoreCase("View All Cities")) {
            cityPage.viewAllCities();
        } else {
            throw new IllegalArgumentException("Unsupported city click option: " + option);
        }
    }

    @Then("the list of all cities should be displayed")
    public void the_list_of_all_cities_should_be_displayed() {
        Assert.assertTrue("City list not displayed", cityPage.isCityListDisplayed());
    }

    @Then("popular cities should be shown at the top")
    public void popular_cities_should_be_shown_at_the_top() {
        Assert.assertTrue("Popular cities not shown at top", cityPage.arePopularCitiesOnTop());
    }

    @Then("validate that cities like {string}, {string}, {string} are visible")
    public void validate_that_cities_like_are_visible(String c1, String c2, String c3) {
        Assert.assertTrue("City " + c1 + " not visible", cityPage.isCityVisible(c1));
        Assert.assertTrue("City " + c2 + " not visible", cityPage.isCityVisible(c2));
        Assert.assertTrue("City " + c3 + " not visible", cityPage.isCityVisible(c3));
    }
}
