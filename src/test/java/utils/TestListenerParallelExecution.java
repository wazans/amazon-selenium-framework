package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListenerParallelExecution implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListenerParallelExecution.class);

    // All parallel tests write into one HTML report object.
    private static final ExtentReports extent = ReportManagerParallelExecution.getInstance();

    // Each running test thread must have its own ExtentTest node.
    // This prevents one thread from writing logs into another thread's report entry.
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        // Include thread id in the report test name so parallel runs are easy to read.
        String testName = result.getMethod().getMethodName()
                + " [thread-" + Thread.currentThread().getId() + "]";

        // Create one report node for the current test method and current thread.
        test.set(extent.createTest(testName));
        test.get().info("Parallel test started");
        log.info("STARTED: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // test.get() resolves the ExtentTest for only the current running thread.
        test.get().pass("Test passed");
        log.info("PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // result.getInstance() returns the current test class object.
        // We cast it only if it is our parallel base class, because that class owns the thread-local driver.
        Object instance = result.getInstance();
        if (instance instanceof BaseTestParallelExecution) {
            WebDriver driver = ((BaseTestParallelExecution) instance).getDriver();

            // Screenshot file name includes the thread id to avoid collisions in parallel runs.
            String path = ScreenshotUtilsParallelExecution.capture(driver, result.getMethod().getMethodName());
            test.get().fail(result.getThrowable());
            if (path != null) {
                // Attach the screenshot to the Extent report entry for this same test thread.
                test.get().addScreenCaptureFromPath(path);
            }
        } else {
            test.get().fail(result.getThrowable());
        }
        log.error("FAILED: {}", result.getMethod().getMethodName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (test.get() == null) {
            // In rare skip cases, onTestStart may not have created the report node yet.
            // This safety block avoids null access in the report.
            String testName = result.getMethod().getMethodName()
                    + " [thread-" + Thread.currentThread().getId() + "]";
            test.set(extent.createTest(testName));
        }
        test.get().skip("Test skipped");
        log.warn("SKIPPED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // flush() writes everything collected in memory into the final HTML report file.
        extent.flush();
    }
}
