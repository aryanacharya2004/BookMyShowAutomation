package utils;

import base.DriverSetup;
import base.ConfigLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static WebDriverWait getWait(){
        return new WebDriverWait(DriverSetup.getDriver(), Duration.ofSeconds(ConfigLoader.getInt("explicitWait")));
    }

    // Accept By locator instead of WebElement
    public static WebElement visible(By locator){
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(By locator){
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }
}
