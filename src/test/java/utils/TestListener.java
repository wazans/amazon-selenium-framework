package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent = ReportManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        System.out.println("STARTED: " + result.getName());
        log.info("STARTED: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED: " + result.getName());
        log.info("PASSED: {}", result.getName());
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((BaseTest) result.getInstance()).getDriver();
        String path = ScreenshotUtils.capture(driver, result.getName());

        test.get().fail(result.getThrowable());
        if (path != null) {
            test.get().addScreenCaptureFromPath(path);
        }

        log.error("FAILED: {}", result.getName());
        System.out.println("FAILED: " + result.getName());
        if (path != null) {
            System.out.println("Screenshot saved at: " + path);
            log.error("Screenshot saved at: {}", path);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("SKIPPED: " + result.getName());
        log.warn("SKIPPED: {}", result.getName());
        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
            test.set(extentTest);
        }
        test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
