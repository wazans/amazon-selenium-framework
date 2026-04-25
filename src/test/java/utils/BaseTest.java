package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    // "protected" lets child classes such as AmazonSearchTest use this variable directly.
    protected WebDriver driver;
    ConfigReader config;


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        config = new ConfigReader();
        // These are local variables. They are created inside setUp()
        // and used only during this method call.
        String browser = config.getProperty("browser");
        String url = config.getProperty("url");

        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalStateException("Browser value is missing in config.properties");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalStateException("URL value is missing in config.properties");
        }

        browser = browser.trim();
        url = url.trim();

        // We decide which browser object should be created based on config file values.
        if (browser.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalStateException("Unsupported browser in config.properties: " + browser);
        }

        driver.manage().window().maximize();
        driver.get(url);
    }






    @AfterMethod
    public void tearDown() {
        // Close the browser after every test method so tests stay independent.
        if (driver != null) {
            driver.quit();
        }
    }
}
