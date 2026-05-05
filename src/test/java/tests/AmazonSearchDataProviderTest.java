package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTest;

public class AmazonSearchDataProviderTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] data() {
        // This is an in-code DataProvider.
        // TestNG calls this method first, collects all rows, and then reruns
        // the same test method once for each row below.
        return new Object[][]{
                {"pen"},
                {"car"},
                {"stand"}
        };
    }

    @Test(dataProvider = "searchData")
    public void searchProductWithInlineDataProvider(String keyword) {
        // TestNG injects one value into "keyword" for each execution.
        // With the current data above, this method runs 3 times:
        // 1) keyword = pen
        // 2) keyword = car
        // 3) keyword = stand
        AmazonHomePage home = new AmazonHomePage(getDriver());
        home.enterSearchText(keyword);
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(getDriver());

        // This assertion validates only the current data iteration.
        // If "car" fails and the others pass, TestNG shows that exact iteration as failed.
        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed for keyword: " + keyword);
    }
}
