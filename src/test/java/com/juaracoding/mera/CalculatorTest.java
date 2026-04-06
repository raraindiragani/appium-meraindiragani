package com.juaracoding.mera;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class CalculatorTest {

    //Setup Android Google Calculator App

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.out.println("Setting up before tests...");
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 3");
        options.setUdid("emulator-5554");
        options.setPlatformName("Android");
        options.setAppPackage("com.google.android.calculator");
        options.setAppActivity("com.android.calculator2.Calculator");
        options.setNoReset(true);

         try {
            driver = new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Appium server URL is invalid", e);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Appium server URI is invalid", e);
        }
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Tearing down after tests...");
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Test 2 + 3 = 5")
    public void testAddition() {
        // Test implementation would go here
        System.out.println("Test 2 + 3 = 5");
        driver.findElement(By.id("com.google.android.calculator:id/digit_2")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_add")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_3")).click();
        driver.findElement(By.id("com.google.android.calculator:id/eq")).click();

        //Validate the result
        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();
        Assert.assertEquals(result, "5", "Expected result of 2 + 3 should be 5");
    }

    @Test(description = "Test Case: 10 - 4 = 6")
        public void testSubtraction() {
        System.out.println("Test Case: 10 - 4 = 6");
        driver.findElement(By.id("com.google.android.calculator:id/digit_1")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_0")).click();
        driver.findElement(By.id("com.google.android.calculator:id/op_sub")).click();
        driver.findElement(By.id("com.google.android.calculator:id/digit_4")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("com.google.android.calculator:id/eq"))).click();
        // Validate result
        String result = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();
        Assert.assertEquals(result, "6", "Expected result is 6");
    }
}
