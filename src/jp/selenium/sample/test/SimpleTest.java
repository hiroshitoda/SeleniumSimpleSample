package jp.selenium.sample.test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class SimpleTest
{

    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setBrowserName("firefox");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        driver = new RemoteWebDriver(capabilities);
        baseUrl = "http://example.selenium.jp";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws Exception
    {
        driver.get(baseUrl + "/reserveApp_Renewal/");
        new Select(driver.findElement(By.id("reserve_term"))).selectByVisibleText("7");
        new Select(driver.findElement(By.id("headcount"))).selectByVisibleText("5");
        driver.findElement(By.id("breakfast_off")).click();
        driver.findElement(By.id("agree_and_goto_next")).click();
        driver.findElement(By.id("returnto_index")).click();
        Files.write(
                FileSystems.getDefault().getPath("screenshot01.png"),
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
                );
    }

    @After
    public void tearDown() throws Exception
    {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString))
        {
            fail(verificationErrorString);
        }
    }
}
