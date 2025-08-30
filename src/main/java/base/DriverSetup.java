package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Initialize driver
    public static void initDriver(String browser, boolean headless) {
        if (getDriver() != null) return; // Prevent duplicate initialization

        switch (browser.toLowerCase()) {
            case "edge":
                EdgeOptions eo = new EdgeOptions();
                if (headless) eo.addArguments("--headless=new");
                eo.addArguments("--start-maximized");
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver(eo));
                break;

            case "firefox":
                FirefoxOptions fo = new FirefoxOptions();
                if (headless) fo.addArguments("--headless");
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver(fo));
                break;

            case "chrome":
            default:
                ChromeOptions co = new ChromeOptions();
                if (headless) co.addArguments("--headless=new");
                co.addArguments("--start-maximized");
                co.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(co));
                break;
        }
    }

    // Get current driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit driver completely
    public static void quitDriver() {
        if (getDriver() != null) {
            try {
                getDriver().quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            }
            driver.remove();
        }
    }
}
