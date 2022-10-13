package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.Select;

public class BMICalculatorTest {

    private WebDriver driver;

    @BeforeClass
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void emptyFieldsTest() {
        driver.get("https://healthunify.com/bmicalculator/");
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Enter the value for weight");
    }

    @Test
    public void weightIsNotEnoughTest() {
        driver.get("https://healthunify.com/bmicalculator/");
        driver.findElement(By.xpath("//input[@name='wg']")).sendKeys("7");
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        Assert.assertEquals(alertText, "Weight should be greater than 10kgs");
    }

    @Test
    public void checkCalculatedSIValueTest() throws InterruptedException {
        driver.get("https://healthunify.com/bmicalculator/");
        driver.findElement(By.xpath("//input[@name='wg']")).sendKeys("11");
        driver.findElement(By.xpath("//select[@name='opt2']")).click();
        Select element = new Select(driver.findElement(By.xpath("//select[@name='opt2']")));
        element.selectByVisibleText("2′");
        driver.findElement(By.xpath("//input[@value='Calculate']")).click();
        Thread.sleep(3);
        String actualText = driver.findElement(By.xpath("//input[@name='si']"))
                .getAttribute("value");
        Assert.assertEquals(actualText, "29.56");
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

}
