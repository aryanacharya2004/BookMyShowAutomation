package pages;

import base.ConfigLoader;
import base.DriverSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class CityPage {
    private WebDriver driver;

    // Constructor
    public CityPage() {
        this.driver = DriverSetup.getDriver();
    }

    // ======= Locators =======
    private By modal = By.id("bottomSheet-model-close");
    private By searchInput = By.cssSelector("input[placeholder='Search for your city']");
    private By cityOption(String city) { return By.xpath("//p[contains(text(),'" + city + "')]"); }
    
    
    
    private By allcityOption(String city) {
        return By.xpath("//*[self::p or self::li][normalize-space(text())='" + city + "']");
    
    }

    
    private By selectedCityLabel = By.cssSelector("#common-header-region span.sc-1or3vea-16");

    private By allCitiesLink = By.xpath("//p[normalize-space()='View All Cities']");
    private By cityListContainer = By.xpath("//ul[contains(@class,'sc-p6ayv6-1')]");
    private By noResultsMessage = By.xpath("//div[@class='sc-fv93km-1 fZhJNQ']");
    private By popularCitiesHeader = By.xpath("//p[normalize-space()='Popular Cities']");

    // ======= Page Actions =======

    // Open home page
    public void openHome() {
        driver.get(ConfigLoader.get("baseUrl"));
    }

    // Check modal visibility
    public boolean isCityModalVisible() {
        try {
            WebElement modalEl = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(modal));
            return modalEl.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Select city from modal
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

    
    
    // Change city via dropdown (opens modal again)
    public void changeCity(String city) {
        openCityDropdown();
        selectCity(city);
    }

    // Open city dropdown
    public void openCityDropdown() {
        try {
            // Close modal if already open
            try {
                WebElement modalEl = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.visibilityOfElementLocated(modal));
                modalEl.click();
            } catch (TimeoutException e) {
                // Modal not visible, ignore
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Click the city label to open modal
            WebElement cityLabel = wait.until(ExpectedConditions.elementToBeClickable(selectedCityLabel));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cityLabel);

            // Wait for the search box inside the modal to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));

        } catch (TimeoutException e) {
            System.out.println("City dropdown is not clickable after waiting.");
        }
    }

    // Get displayed city beside Sign In
    public String getDisplayedCity() {
        WebElement label = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(selectedCityLabel));
        
        // Get the city name and remove anything after the first hyphen
        String cityName = label.getText().trim();
        
        // If there's a hyphen (indicating a region like '-NCR'), take only the part before it
        if (cityName.contains("-")) {
            cityName = cityName.split("-")[0].trim(); // Split by hyphen and get the city part
        }
        
        return cityName;
    }

    // Search for city
    public void searchCity(String cityName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        searchBox.clear();
        searchBox.sendKeys(cityName);
    }

    // Validate search results exist
    public boolean isSearchResultValid() {
        try {
            List<WebElement> results = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//p")));
            return !results.isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Select first searched city
    public void selectSearchedCity() {
        List<WebElement> results = driver.findElements(By.xpath("//p[contains(@class, 'sc-p6ayv6-0 iwwDFF')]"));
        if (results.size() > 0) {
            WebElement element = results.get(0);

            // Wait until the element is both visible and clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Ensure it's not covered by another element
            if (element.isDisplayed() && element.isEnabled()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        }
    }

    // Error message for invalid city
    public boolean isErrorMessageVisible(String expectedMsg) {
        try {
            WebElement errorEl = new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.visibilityOfElementLocated(noResultsMessage));
            
            // Make sure it's fully loaded and visible
            String errorText = errorEl.getText().trim();
            return errorText.equals(expectedMsg);
        } catch (TimeoutException e) {
            return false;
        }
    }

    // View all cities
    public void viewAllCities() {
        WebElement viewAll = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(allCitiesLink));
        viewAll.click();
    }

    // Validate city list displayed
    public boolean isCityListDisplayed() {
        try {
            WebElement container = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(cityListContainer));
            return container.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

	// Popular cities header on top
	public boolean arePopularCitiesOnTop() {
		try {
			WebElement header = new WebDriverWait(driver, Duration.ofSeconds(5))
					.until(ExpectedConditions.visibilityOfElementLocated(popularCitiesHeader));
			return header.isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

    // Validate specific city visible
    public boolean isCityVisible(String city) {
        try {
            WebElement cityEl = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(allcityOption(city)));
            return cityEl.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
