package com.juaracoding.mera;

import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.*;

import com.juaracoding.mera.pages.*;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class SwagLabsTest {

    private AndroidDriver driver;
    private WebDriverWait wait;

    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeClass
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Pixel 3");
        options.setUdid("emulator-5554");
        options.setPlatformName("Android");
        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");
        options.setNoReset(true);

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(driver, wait);
        productPage = new ProductPage(driver, wait);
        cartPage = new CartPage(driver, wait);
        checkoutPage = new CheckoutPage(driver, wait);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterMethod
    public void doLogout() {
    try {
        productPage.logout();
    } catch (Exception e) {
        System.out.println("Logout skipped (maybe not logged in)");
    }
}

    @Test (description = "Test valid login", priority = 1)
    public void testLoginSuccess() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productPage.isOnProductPage());
    }

    @Test (description = "Test invalid login", priority = 2)
    public void testLoginFailed() {
        loginPage.login("wrong_user", "wrong_pass");
        Assert.assertTrue(loginPage.isErrorDisplayed());
    }

    @Test (description = "Test sorting products and adding to cart", priority = 4)
    public void testSortAndAddToCart() {
        loginPage.login("standard_user", "secret_sauce");
        productPage.sortLowToHigh();
        productPage.addProduct();
        productPage.goToCart();

        Assert.assertTrue(cartPage.isCartDisplayed());
    }

    @Test (description = "Test checkout flow", priority = 3)
    public void testCheckoutFlow() {
        loginPage.login("standard_user", "secret_sauce");
        productPage.addProduct();
        productPage.goToCart();

        cartPage.clickCheckout();
        checkoutPage.fillData();
        checkoutPage.finishCheckout();

        Assert.assertTrue(checkoutPage.isCheckoutSuccess());
    }
}