package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTest;

public class AmazonResultsTest extends BaseTest {

    @Test(groups = "smoke")
    public void verifySearchResultsDisplayed() throws InterruptedException {

        AmazonHomePage home = new AmazonHomePage(driver);
        home.enterSearchText("headphones");
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(driver);

        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed");

        System.out.println("Results page validation passed");
    }
}
