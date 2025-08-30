package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.MoviePage;

public class MovieSteps {

    private MoviePage moviePage = new MoviePage();

    // ======= Background Steps =======
    @Given("user is on the BMS home page for movie test")
    public void user_is_on_the_bms_home_page_for_movie_test() {
        moviePage.openHome();
    }

    @When("city selection modal is displayed for movie test")
    public void city_selection_modal_is_displayed_for_movie_test() {
        Assert.assertTrue("City modal not displayed", moviePage.isCityModalVisible());
    }

    @Then("user selects a city from the modal for movie")
    public void user_selects_a_city_from_the_modal_for_movie() {
        moviePage.selectCity("Delhi"); // default
    }

    // ======= Scenario 1 =======
    @Given("user notes the first recommended movie title")
    public void user_notes_the_first_recommended_movie_title() {
        moviePage.noteFirstRecommendedMovieTitle();
    }

    @When("user clicks on the first recommended movie card")
    public void user_clicks_on_the_first_recommended_movie_card() {
        moviePage.clickFirstRecommendedMovieCard();
    }

    @Then("user should see the same movie title on the movie detail page")
    public void user_should_see_the_same_movie_title_on_the_movie_detail_page() {
        String expected = moviePage.getSelectedMovieTitle();
        String actual = moviePage.getMovieDetailTitle();
        Assert.assertEquals("Movie title mismatch!", expected, actual);
    }

    // ======= Scenario 2 =======
    @Given("user navigates to the Movies section from navbar")
    public void user_navigates_to_the_movies_section_from_navbar() {
        moviePage.navigateToMoviesNav();
    }

    @When("user clicks on the {string} section")
    public void user_clicks_on_the_section(String sectionName) {
        if (sectionName.equalsIgnoreCase("Explore Upcoming Movies")) {
            Assert.assertTrue("Explore Upcoming Movies not visible", moviePage.isExploreUpcomingMoviesVisible());
            moviePage.clickExploreUpcomingMovies();
        }
    }

    @Then("user should see the {string} section on the page")
    public void user_should_see_the_section_on_the_page(String expectedSection) {
        if (expectedSection.equalsIgnoreCase("In Cinemas Near You")) {
            Assert.assertTrue("In Cinemas Near You section not visible", moviePage.isInCinemasSectionVisible());
        }
    }
    
    // ======= Scenario 3 =======
    
    @When("Verify navbar links are visible and working")
    public void verify_navbar_links_are_visible_and_working() {
        Assert.assertTrue("Navbar links are not visible or not working", 
                          moviePage.areNavbarLinksVisibleAndWorking());
    }

    
    
    @When("I click on the Movies tab")
    public void i_click_on_the_movies_tab() {
        moviePage.navigateToMoviesNav();
    }

    @When("Verify {string} header is visible")
    public void verify_header_is_visible(String header) {
        if (header.equalsIgnoreCase("Filters")) {
            Assert.assertTrue("Filters header not visible", moviePage.isFiltersHeaderVisible());
        }
    }

    @When("Verify Genre filter is visible")
    public void verify_genre_filter_is_visible() {
        Assert.assertTrue("Genre filter not visible", moviePage.isGenreFilterVisible());
    }


}
