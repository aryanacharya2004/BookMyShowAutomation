package pages;

import base.ConfigLoader;
import base.DriverSetup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class GiftCardPage {
    private WebDriver driver;

    // Constructor
    public GiftCardPage() {
        this.driver = DriverSetup.getDriver();
    }

    // ======= Locators =======
    private By modal = By.id("bottomSheet-model-close");
    private By cityOption(String city) {
        return By.xpath("//p[contains(text(),'" + city + "')]");
    }

    private By giftCardMenu = By.xpath("//a[contains(text(),'Gift Cards') or @href='/giftcards']");
    
    private By checkBalanceIcon = By.xpath("//div[contains(text(),'Check Gift Card Balance')]");
    private By voucherInput = By.id("gift-voucher");

    // ✅ Corrected: button is actually a <div> with text 'Check Balance'
    private By submitButton = By.xpath("//div[contains(text(),'Check Balance')]");

    // ✅ Corrected: error message is a <p> with text about invalid voucher
//    private By errorMessage = By.xpath("//p[contains(text(),'Invalid Gift voucher Code')]");
    private By errorMessage = By.xpath("//p[contains(@class,'sc-12r1n02-9')]");

    

    // ======= Background Actions =======

    // Open BMS home page
    public void openHome() {
        driver.get(ConfigLoader.get("baseUrl"));
    }

    // Check city modal visibility
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

    // ======= Gift Card Actions =======

    // Navigate to Gift Card section
    public void navigateToGiftCardSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until overlay disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.sc-1j4wkcy-0")));

        // Wait for Gift Card menu to be clickable
        WebElement giftCard = wait.until(ExpectedConditions.elementToBeClickable(giftCardMenu));
        giftCard.click();
    }

    // Verify Check Balance icon visible
    public boolean isCheckBalanceIconVisible() {
        try {
            WebElement icon = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(checkBalanceIcon));
            return icon.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Click Check Balance link
    public void clickCheckBalanceIcon() {
        WebElement icon = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(checkBalanceIcon));
        icon.click();
    }

    // Enter voucher code
    public void enterVoucherCode(String code) {
        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(voucherInput));
        input.clear();
        input.sendKeys(code);
    }

    // Submit voucher


    public void submitVoucher() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        button.click();
        // Small safety wait to allow error message render
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }
    
    
    // Validate error message
    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                errorMessage, "Invalid Gift voucher Code"));
        return driver.findElement(errorMessage).getText().trim();
    }

    
    
}
