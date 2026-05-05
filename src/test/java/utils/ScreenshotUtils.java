package utils;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtils {

    public static String capture(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dir = System.getProperty("user.dir") + "/screenshots/";
            String path = dir + testName + ".png";

            File dest = new File(path);
            dest.getParentFile().mkdirs(); // ensure folder exists
            Files.copy(src.toPath(), dest.toPath());

            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

