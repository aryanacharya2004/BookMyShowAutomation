package pages;

import base.ConfigLoader;
import base.DriverSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class MoviePage {
    private WebDriver driver;

    // Store selected movie title
    private String selectedMovieTitle;

    // Constructor
    public MoviePage() {
        this.driver = DriverSetup.getDriver();
    }

 // ====== Navbar Links ======
    private By navbarLinks = By.xpath("//div[contains(@class,'sc-1or3vea-19')]//a");
    
    // ======= Locators =======
    private By modal = By.id("bottomSheet-model-close");
    private By cityOption(String city) {
        return By.xpath("//p[contains(text(),'" + city + "')]");
    }

    // Recommended Movies section
    private By firstMovieCard = By.xpath("(//div[@class='sc-lnhrs7-4 hQMAVG']//a)[1]");
    private By firstMovieCardTitle = By.xpath("(//div[@class='sc-lnhrs7-4 hQMAVG']//a)[1]//div[@class='sc-7o7nez-0 lkwOqB']");

    // Movie Detail Page
    private By movieDetailTitle = By.xpath("//h1[contains(@class,'sc-qswwm9-6')]");

    // Navbar Movies link
    private By moviesNavLink = By.xpath("//a[contains(text(),'Movies')]");

    // Explore Upcoming Movies
    private By exploreUpcomingMovies = By.xpath("//img[@alt='Coming Soon']");

    // In Cinemas Near You (Now Showing)
    private By inCinemasSection = By.xpath("//img[@alt='Now Showing']");
    
    // ====== New Locators for Filter Section ======
    private By filtersHeader = By.xpath("//div[contains(text(),'Filters')]");
    private By genreFilter = By.xpath("//div[contains(text(),'Genres')]");
 
    // ======= Background Actions =======
    public void openHome() {
        driver.get(ConfigLoader.get("baseUrl"));
    }

    public boolean isCityModalVisible() {
        try {
            WebElement modalEl = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(modal));
            return modalEl.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void selectCity(String city) {
        try {
            WebElement cityEl = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(cityOption(city)));
            if (cityEl.isDisplayed() && cityEl.isEnabled()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cityEl);
            }
        } catch (TimeoutException e) {
            System.out.println("City element not clickable: " + city);
        }
    }

    // ======= Movie Actions =======
    public void noteFirstRecommendedMovieTitle() {
        WebElement titleEl = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(firstMovieCardTitle));
        selectedMovieTitle = titleEl.getText().trim();
    }

    public void clickFirstRecommendedMovieCard() {
        WebElement card = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(firstMovieCard));
        card.click();
    }

    public String getMovieDetailTitle() {
        WebElement titleEl = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(movieDetailTitle));
        return titleEl.getText().trim();
    }

    public String getSelectedMovieTitle() {
        return selectedMovieTitle;
    }

    public void navigateToMoviesNav() {
        WebElement link = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(moviesNavLink));
        link.click();
    }

    public boolean isExploreUpcomingMoviesVisible() {
        try {
            WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(exploreUpcomingMovies));
            return el.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ✅ Click Explore Upcoming Movies (Coming Soon)
    public void clickExploreUpcomingMovies() {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(exploreUpcomingMovies));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
        el.click();

        // wait for page change (url or new section to appear)
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(inCinemasSection));
    }

    public boolean isInCinemasSectionVisible() {
        try {
            WebElement el = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(inCinemasSection));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
            return el.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    // ====== New Methods for Filters ======
 // ====== Navbar Methods ======
    public boolean areNavbarLinksVisibleAndWorking() {
        try {
            List<WebElement> links = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navbarLinks));

            boolean allWorking = true;

            for (WebElement link : links) {
                if (!link.isDisplayed() || !link.isEnabled()) {
                    allWorking = false;
                    System.out.println("Navbar link not visible or not enabled: " + link.getText());
                } else {
                    System.out.println("Navbar link visible: " + link.getText());
                }
            }
            return allWorking;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    
    public boolean isFiltersHeaderVisible() {
        try {
            WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(filtersHeader));
            return header.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isGenreFilterVisible() {
        try {
            WebElement genre = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(genreFilter));
            return genre.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    
}
