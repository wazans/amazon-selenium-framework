package tests;

import org.testng.annotations.*;
import pages.AmazonHomePage;
import utils.BaseTest;

public class AmazonSearchTest extends BaseTest {


        // This instance variable can be used by different test methods in this class.
        AmazonHomePage homePage;


        @Test
        public void searchProductTest() throws InterruptedException {
            // We pass the shared driver from BaseTest into the page object constructor.
            homePage=new AmazonHomePage(getDriver());
            homePage.enterSearchText("laptop");
            homePage.clickSearch();

            System.out.println("Result Page Title: " + homePage.getPageTitle());
        }

}
