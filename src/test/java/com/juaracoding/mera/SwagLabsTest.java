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

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class SwagLabsTest {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.out.println("Setting up Swag Labs App...");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 3");
        options.setUdid("emulator-5554");
        options.setPlatformName("Android");

        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");
        options.setNoReset(true);

        try {
            driver = new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), options);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalStateException("Appium server error", e);
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        System.out.println("Closing App...");
        if (driver != null) {
            driver.quit();
        }
    }

    // helper login
    public void login(String username, String password) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("test-Username"))).sendKeys(username);

        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys(password);

        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
    }

    // Login success
    @Test(description = "Login Success", priority = 1)
    public void testLoginSuccess() {
        System.out.println("Login Success Test");

        login("standard_user", "secret_sauce");

        boolean isProductPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@text='PRODUCTS']")
                )
        ).isDisplayed();

        Assert.assertTrue(isProductPage, "Login gagal");

        // Logout
        driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("test-LOGOUT"))).click();
    }

    // Login failed
    @Test(description = "Login Failed", priority = 2)
    public void testLoginFailed() {
        System.out.println("Login Negative Test");

        login("wrong_user", "wrong_pass");

        boolean isErrorDisplayed = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@text,'Username and password')]")
                )
        ).isDisplayed();

        Assert.assertTrue(isErrorDisplayed, "Error tidak muncul");
    }

    // Sort + Cart
    @Test(description = "Sort Product & Add to Cart", priority = 3)
    public void testSortAndAddToCart() {
        System.out.println("Sort & Add To Cart");

        login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("test-Modal Selector Button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@text='Price (low to high)']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@content-desc='test-ADD TO CART'])[1]"))).click();

        driver.findElement(AppiumBy.accessibilityId("test-Cart")).click();

        boolean isCartPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@text='YOUR CART']")
                )
        ).isDisplayed();

        Assert.assertTrue(isCartPage, "Gagal masuk cart");
    }

    // Checkout
    @Test(description = "Checkout Flow", priority = 4)
    public void testCheckoutFlow() {
        System.out.println("Checkout Test");

        login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//*[@content-desc='test-ADD TO CART'])[1]"))).click();

        driver.findElement(AppiumBy.accessibilityId("test-Cart")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("test-CHECKOUT"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.accessibilityId("test-First Name"))).sendKeys("Mera");

        driver.findElement(AppiumBy.accessibilityId("test-Last Name")).sendKeys("QA");
        driver.findElement(AppiumBy.accessibilityId("test-Zip/Postal Code")).sendKeys("12345");

        driver.findElement(AppiumBy.accessibilityId("test-CONTINUE")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\"test-FINISH\"))"));

        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("test-FINISH"))).click();

        boolean isSuccess = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@text,'THANK YOU')]")
                )
        ).isDisplayed();

        Assert.assertTrue(isSuccess, "Checkout gagal");
    }
}