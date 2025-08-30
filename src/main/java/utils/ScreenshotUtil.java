package utils;

import base.DriverSetup;
import constants.FrameworkConstants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String screenshot(String name){
        try{
            File src = ((TakesScreenshot) DriverSetup.getDriver()).getScreenshotAs(OutputType.FILE);
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String path = FrameworkConstants.SCREENSHOT_PATH + "/" + name + "_" + ts + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        }catch (Exception e){
            return null;
        }
    }
}
