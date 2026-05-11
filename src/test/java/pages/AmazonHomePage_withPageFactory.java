package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AmazonHomePage_withPageFactory {
    private static final Logger log = LogManager.getLogger(AmazonHomePage_withPageFactory.class);
    private final WebDriver driver;

    public AmazonHomePage_withPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    public void enterSearchText(String text) {
        log.info("Entering search text: {}", text);
        searchBox.clear();
        searchBox.sendKeys(text);
    }

    public void clickSearch() {
        log.info("Clicking search");
        searchButton.click();
    }
}
