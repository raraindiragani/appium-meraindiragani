package com.juaracoding.mera.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CartPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public CartPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    By title = By.xpath("//*[@text='YOUR CART']");
    By checkout = AppiumBy.accessibilityId("test-CHECKOUT");

    public boolean isCartDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkout)).click();
    }
}