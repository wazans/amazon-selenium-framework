package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class AmazonResultsPage {

    WebDriver driver;
    WaitUtils wait;

    public AmazonResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By resultsContainer = By.cssSelector("div.s-main-slot");

    public boolean isResultsDisplayed() {
        return wait.waitForVisibility(resultsContainer).isDisplayed();
    }
}
