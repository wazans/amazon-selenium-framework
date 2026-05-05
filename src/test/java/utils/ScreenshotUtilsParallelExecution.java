package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtilsParallelExecution {

    public static String capture(WebDriver driver, String testName) {
        try {
            // Selenium first creates a temporary screenshot file.
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Use a dedicated parallel-execution folder so these screenshots do not mix
            // with screenshots from the existing framework.
            String dir = System.getProperty("user.dir") + "/screenshots/parallel-execution/";

            // Thread id is appended to the file name so two parallel failures with the same
            // test method name do not overwrite each other.
            String path = dir + testName + "-" + Thread.currentThread().getId() + ".png";

            File dest = new File(path);
            // Create folder structure if it does not exist yet.
            dest.getParentFile().mkdirs();

            // Copy temporary Selenium file into our framework location.
            Files.copy(src.toPath(), dest.toPath());

            return path;
        } catch (Exception e) {
            // Returning null keeps the listener alive even if screenshot capture fails.
            e.printStackTrace();
            return null;
        }
    }
}
