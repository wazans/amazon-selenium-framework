package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTest;

public class AmazonResultsTest extends BaseTest {

    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void verifySearchResultsDisplayed() throws InterruptedException {

        AmazonHomePage home = new AmazonHomePage(getDriver());
        home.enterSearchText("headphones");
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(getDriver());
        //Assert.assertTrue(false, "Force failure to test retry & screenshot");

        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed");

        System.out.println("Results page validation passed");
        System.out.println("Thread ID: " + Thread.currentThread().getId());
    }
}
