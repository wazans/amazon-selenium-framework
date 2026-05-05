package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class AmazonResultsPage {
    private static final Logger log = LogManager.getLogger(AmazonResultsPage.class);

    WebDriver driver;
    WaitUtils wait;

    public AmazonResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    private By resultsContainer = By.cssSelector("div.s-main-slot");

    public boolean isResultsDisplayed() {
        log.info("verify  if the landing page is displayed ");
        return wait.waitForVisibility(resultsContainer).isDisplayed();
    }
}

