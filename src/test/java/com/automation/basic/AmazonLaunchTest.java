package com.automation.basic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmazonLaunchTest {

    public static void main(String[] args) throws InterruptedException {
        // Local variable: this driver exists only inside main().
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.amazon.in");



        // Search for product
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).click();
        // sendKeys() types text into the input box.
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("laptop");
        driver.findElement(By.id("nav-search-submit-button")).click();


        // Pause only for demo purposes. In real tests, explicit waits are better.
        Thread.sleep(5000);

        System.out.println("Title is: " + driver.getTitle());

        // Always close the browser at the end.
        driver.quit();

    }
}
