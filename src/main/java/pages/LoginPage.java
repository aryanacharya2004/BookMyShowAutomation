package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.DriverSetup;
import utils.WaitUtils;
import base.ConfigLoader;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class LoginPage {
    private WebDriver driver;
    private String mainWindowHandle;

    // Constructor
    public LoginPage() {
        this.driver = DriverSetup.getDriver();
    }

    // Locators
    private By cityLocator(String city) { 
        return By.xpath("//p[contains(text(),'" + city + "')]"); 
    }

    private By modalOverlay = By.cssSelector(".sc-bmyXtO"); 
    private By profileIcon = By.xpath("//div[text()='Sign in']");
    private By continueWithMobile = By.xpath("//input[@placeholder='Continue with mobile number']");
    private By mobileInput = By.xpath("//input[@type='tel']");
    private By continueBtn = By.xpath("//div[contains(text(),'Continue')]");
    
    private By otpInputs = By.cssSelector("input[type='tel'][maxlength='6']");
    private By backButton = By.xpath("//div[@class='sc-1ydq0aj-0 bIaakI']");
    private By loginMethodHeader = By.xpath("//h4[contains(text(),'Login')]");
    private By errorToast = By.cssSelector("div.sc-2vmyj1-12.sc-2vmyj1-13, div.sc-2vmyj1-12.sc-2vmyj1-13.fXPqxZ");
    
    private By googlePopupClose = By.cssSelector("button[aria-label='Close']");
    private By googlePrompt = By.cssSelector("div[role='dialog']");
    private By bottomSheetModal = By.id("bottomSheet-model-close"); 
    private By bottomSheetContinueBtn = By.cssSelector("div.sc-zgl7vj-8.hpVUcY"); 

    // 🔹 New locators for inline invalid mobile case
    private By mobileErrorInline = By.cssSelector(".sc-z1ldnh-12.Qsrzn");

    // Open home page
    public void openHome() {
        driver.get(ConfigLoader.get("baseUrl"));
    }

    // Select city
    public void selectCity(String city) {
        WaitUtils.clickable(cityLocator(city)).click();
        closeModalIfPresent();
    }

    private void closeModalIfPresent() {
        List<WebElement> overlays = driver.findElements(modalOverlay);
        if (!overlays.isEmpty()) {
            WebElement modal = overlays.get(0);
            if (modal.isDisplayed()) {
                try { modal.click(); } catch (Exception ignored) {}
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.invisibilityOf(modal));
            }
        }
    }

    // Open login
    public void openLogin() {
        closeModalIfPresent();
        mainWindowHandle = driver.getWindowHandle();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signInIcon = wait.until(ExpectedConditions
                .elementToBeClickable(profileIcon));

        try {
            signInIcon.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signInIcon);
        }

        handleOauthPopupIfPresent();
        handleGooglePromptIfPresent();
    }

    // Detect and close OAuth popup window
    private void handleOauthPopupIfPresent() {
        try {
            Set<String> windowHandles = driver.getWindowHandles();
            if (windowHandles.size() > 1) {
                for (String handle : windowHandles) {
                    if (!handle.equals(mainWindowHandle)) {
                        driver.switchTo().window(handle);
                        List<WebElement> closeBtns = driver.findElements(googlePopupClose);
                        if (!closeBtns.isEmpty()) {
                            closeBtns.get(0).click();
                        } else {
                            driver.close();
                        }
                        driver.switchTo().window(mainWindowHandle);
                    }
                }
            }
        } catch (NoSuchWindowException ignored) {}
    }

    // Detect and close inline Google prompt
    private void handleGooglePromptIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement prompt = wait.until(ExpectedConditions.presenceOfElementLocated(googlePrompt));
            if (prompt != null && prompt.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", prompt);
            }
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }

    // Handle BMS bottom sheet modal
    private void handleBottomSheetIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(bottomSheetModal));
            if (modal.isDisplayed()) {
                WebElement continueBtn = modal.findElement(bottomSheetContinueBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.invisibilityOf(modal));
            }
        } catch (TimeoutException | NoSuchElementException ignored) {}
    }

    // Visible checks
    public boolean isContinueWithMobileVisible() {
        return WaitUtils.visible(continueWithMobile).isDisplayed();
    }

    public boolean isLoginUiVisible() {
        WebElement mobile = WaitUtils.visible(mobileInput);
        WebElement continueButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(continueBtn));
        return mobile.isDisplayed() && continueButton.isDisplayed()
                && mobile.isEnabled() && continueButton.isEnabled();
    }

    // Enter mobile
    public void enterMobile(String mobile) {
        WebElement input = WaitUtils.visible(mobileInput);
        input.clear();
        input.sendKeys(mobile);
    }

    // Click continue
    public void clickContinue() {
        handleOauthPopupIfPresent();
        handleGooglePromptIfPresent();
        WebElement continueBtnEl = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(continueBtn));
        try {
            continueBtnEl.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtnEl);
        }

        // Handle bottom sheet modal after clicking Continue
        handleBottomSheetIfPresent();
    }

    // OTP
    public boolean isOtpScreenDisplayed() {
        int retries = 3;
        int waitSeconds = 20;

        for (int i = 0; i < retries; i++) {
            try {
                handleOauthPopupIfPresent();
                handleGooglePromptIfPresent();
                handleBottomSheetIfPresent();

                List<WebElement> inputs = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(otpInputs));

                if (inputs != null && !inputs.isEmpty()) {
                    System.out.println("OTP screen detected with " + inputs.size() + " input boxes.");
                    return true;
                }
            } catch (TimeoutException e) {
                System.out.println("Retry " + (i+1) + ": OTP inputs not yet visible. Retrying...");
            }
        }
        System.out.println("OTP screen not displayed after retries.");
        return false;
    }

    public void enterOtp(String otp) {
        List<WebElement> inputs = driver.findElements(otpInputs);
        for (int i = 0; i < Math.min(otp.length(), inputs.size()); i++) {
            WebElement inputBox = inputs.get(i);
            inputBox.clear();
            inputBox.sendKeys(String.valueOf(otp.charAt(i)));
        }
    }

    // Back
    public void clickBack() {
        int explicitWaitTime = ConfigLoader.getInt("explicitWait");
        try {
            WebElement back = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime))
                    .until(ExpectedConditions.elementToBeClickable(backButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", back);
        } catch (TimeoutException e) {
            System.out.println("Back button not found or not clickable within timeout.");
        }
    }

    public boolean isBackOnLoginOptions() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(loginMethodHeader),
                            ExpectedConditions.visibilityOfElementLocated(continueWithMobile)
                    ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Inline error for invalid mobile
    public boolean isInlineErrorVisible(String expectedMessage) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorEl = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileErrorInline));
            String actualText = errorEl.getText().trim();
            System.out.println("Inline error message: " + actualText);
            return actualText.contains(expectedMessage);
        } catch (TimeoutException e) {
            return false;
        }
    }

	// Keep the existing error toast method
	public boolean isErrorVisibleWithMessage(String expectedMessage) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(errorToast));

			String actualText = toast.getText().trim();
			System.out.println("Actual error message: " + actualText);

			return actualText.contains(expectedMessage);
		} catch (TimeoutException ex) {
			System.out.println("Error message not visible within timeout.");
			return false;
		}
	}
}
