package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTestParallelExecution {

    protected static final Logger log = LogManager.getLogger(BaseTestParallelExecution.class);

    // ThreadLocal gives each parallel thread its own browser instance.
    // Without this, one test thread could use or close another thread's driver.
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ConfigReader config;

    @BeforeMethod(alwaysRun = true)
    public void setUpParallelExecution() {
        // Load browser and URL from the same config file used by the main framework.
        config = new ConfigReader();
        String browser = config.getProperty("browser");
        String url = config.getProperty("url");

        // Fail fast if config values are missing. This is easier to debug than a null driver later.
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalStateException("Browser value is missing in config.properties");
        }
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalStateException("URL value is missing in config.properties");
        }

        // Local variable first, then put it into ThreadLocal once the browser is decided.
        WebDriver webDriver;
        if (browser.equalsIgnoreCase("chrome")) {
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--headless=new");
            webDriver = new EdgeDriver(edgeOptions);
        } else if (browser.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        } else {
            throw new IllegalStateException("Unsupported browser in config.properties: " + browser);
        }

        // Bind this browser only to the current running test thread.
        driver.set(webDriver);

        // Thread id in the logs makes it obvious which browser belongs to which test when
        // two classes are running at the same time.
        log.info("Launching browser for thread {}", Thread.currentThread().getId());
        getDriver().manage().window().maximize();
        getDriver().get(url.trim());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownParallelExecution() {
        if (getDriver() != null) {
            log.info("Closing browser for thread {}", Thread.currentThread().getId());
            getDriver().quit();

            // Always remove the ThreadLocal value after quit().
            // This prevents stale driver references from leaking into later test runs.
            driver.remove();
        }
    }

    public WebDriver getDriver() {
        // This returns the driver mapped only to the current thread.
        return driver.get();
    }
}
