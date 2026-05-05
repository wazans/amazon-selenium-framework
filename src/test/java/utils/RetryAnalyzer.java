package utils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private final int maxTry = 2; // total tries = 1 (original) + 2 retries

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxTry) {
            count++;
            System.out.println("Retrying test: " + result.getName() + " (attempt " + (count + 1) + ")");
            return true;
        }
        return false;
    }
}
