package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTestParallelExecution;

public class AmazonMobileParallelExecutionTest extends BaseTestParallelExecution {

    @Test
    public void searchMobileParallelExecution() {
        // This second class exists so TestNG can run two classes in parallel
        // when the suite uses parallel=\"classes\".
        AmazonHomePage home = new AmazonHomePage(getDriver());
        home.enterSearchText("mobile");
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(getDriver());
        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed for mobile search");
    }
}
