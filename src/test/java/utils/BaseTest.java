package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ConfigReader config;

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
            //WebDriverManager.chromedriver().setup();
            wd = new ChromeDriver(createChromeOptions());
        } else if (browser.equalsIgnoreCase("edge")) {
            //WebDriverManager.edgedriver().setup();
            wd = new EdgeDriver(createEdgeOptions());
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            wd = new FirefoxDriver();
        } else {
            throw new IllegalStateException("Unsupported browser in config.properties: " + browser);
        }

        driver.set(wd);
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

    private ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return options;
    }

    private EdgeOptions createEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return options;
    }
}
