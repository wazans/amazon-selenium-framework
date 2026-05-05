package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTestParallelExecution;

public class AmazonSearchParallelExecutionTest extends BaseTestParallelExecution {

    @Test
    public void searchLaptopParallelExecution() {
        // Page object gets the browser that belongs only to this current thread.
        AmazonHomePage home = new AmazonHomePage(getDriver());

        // This class is part of the isolated parallel suite. The logic is simple on purpose:
        // enter data, click search, then verify results.
        home.enterSearchText("laptop");
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(getDriver());

        // Assertion keeps the test meaningful; without it, the run would only click and exit.
        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed for laptop search");
    }
}
