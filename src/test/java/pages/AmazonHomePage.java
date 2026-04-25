package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.Objects;

public class AmazonHomePage {

        // Instance variables belong to the object, so every AmazonHomePage object
        // can keep its own driver and wait utility.
        WebDriver driver;
        WaitUtils wait;

        // Constructor runs automatically when we create the object with:
        // new AmazonHomePage(driver)
        public AmazonHomePage(WebDriver driver) {
            // "this.driver" means the instance variable of the current object.
            // The plain "driver" on the right side is the constructor parameter.
            this.driver = Objects.requireNonNull(driver, "WebDriver is null. Check BaseTest.setUp() and your TestNG run configuration.");
            // Reuse the same browser driver inside WaitUtils.
            this.wait = new WaitUtils(this.driver);
        }

        // Locator variables store how Selenium should find elements.
        private By searchBox = By.id("twotabsearchtextbox");
        private By searchButton = By.id("nav-search-submit-button");

        // Methods below describe actions that can be performed on this page.
        public void enterSearchText(String text) {
            // "text" is a local parameter. It exists only while this method runs.
            // Wait once, then reuse the same visible element for clear() and sendKeys().
            WebElement searchInput = wait.waitForVisibility(searchBox);
            searchInput.clear();
            searchInput.sendKeys(text);
        }

        public void clickSearch() {
            wait.waitForClickable(searchButton).click();
        }

        public String getPageTitle() {
            return driver.getTitle();
        }
    }
