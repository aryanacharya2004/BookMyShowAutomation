package hooks;

import base.ConfigLoader;
import base.DriverSetup;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ScreenshotUtil;

public class Hooks {

    @Before
    public void setUp(){
        ConfigLoader.load();
        DriverSetup.initDriver(
                ConfigLoader.get("browser"),
                ConfigLoader.getBoolean("headless")
        );
    }

    @AfterStep
    public void attachOnFailure(Scenario scenario){
        if(scenario.isFailed()){
            byte[] ss = ((TakesScreenshot) DriverSetup.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(ss, "image/png", "failed-step");
        }
    }

    @After
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            ScreenshotUtil.screenshot(scenario.getName().replaceAll("\\s+","_"));
        }
        DriverSetup.quitDriver();
    }
}

