package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    ConfigReader config;

    @BeforeMethod
    public void setUp() {
        config = new ConfigReader();
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

        WebDriver wd;
        if (browser.equalsIgnoreCase("chrome")) {
            wd = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--headless=new");
            wd = new EdgeDriver(edgeOptions);
        } else if (browser.equalsIgnoreCase("firefox")) {
            wd = new FirefoxDriver();
        } else {
            throw new IllegalStateException("Unsupported browser in config.properties: " + browser);
        }

        driver.set(wd);
        driver.get().manage().window().maximize();
        driver.get().get(url);
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }
}
