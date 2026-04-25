package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    // This class keeps reusable Selenium wait logic in one place.
        WebDriver driver;
        WebDriverWait wait;

        // Constructor receives the same driver used by the test/page object.
        public WaitUtils(WebDriver driver) {
            // "this.driver" is the instance variable, "driver" is the parameter.
            this.driver = driver;
            // Create an explicit wait that will poll for up to 10 seconds.
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        public WebElement waitForVisibility(By locator) {
            // Wait until the element is visible before returning it.
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        }

        public WebElement waitForClickable(By locator) {
            // Wait until the element can be clicked before returning it.
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        }



    }


