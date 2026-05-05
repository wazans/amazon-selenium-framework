package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonResultsPage;
import utils.BaseTest;
import utils.ExcelUtils;

public class AmazonSearchExcelDataTest extends BaseTest {

    @DataProvider(name = "excelData")
    public Object[][] getData() {
        // Build the Excel file path from the project root so this works
        // regardless of where the project is opened on the machine.
        String path = System.getProperty("user.dir") + "/testdata/searchData.xlsx";

        // ExcelUtils reads Sheet1 and converts its rows into Object[][]
        // so TestNG can execute the same test multiple times.
        return ExcelUtils.getTestData(path, "Sheet1");
    }

    @Test(dataProvider = "excelData")
    public void searchProductWithExcelData(String keyword) {
        // Unlike the inline DataProvider test, this keyword comes from Excel.
        // That means test data can be updated without changing Java code.
        AmazonHomePage home = new AmazonHomePage(getDriver());
        home.enterSearchText(keyword);
        home.clickSearch();

        AmazonResultsPage results = new AmazonResultsPage(getDriver());

        // The assertion is evaluated once per Excel row.
        // This makes it easy to identify exactly which external test data row failed.
        Assert.assertTrue(results.isResultsDisplayed(),
                "Results not displayed for keyword: " + keyword);
    }
}
