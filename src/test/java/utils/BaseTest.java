package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

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

        // 🔥 CHROME (BEST FOR JENKINS)
        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new"); // CI mandatory
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");

            wd = new ChromeDriver(options);
        }

        // ⚠️ EDGE (less stable in CI)
        else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            wd = new EdgeDriver(options);
        }

        // ⚠️ FIREFOX
        else if (browser.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            wd = new FirefoxDriver();
        }

        else {
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