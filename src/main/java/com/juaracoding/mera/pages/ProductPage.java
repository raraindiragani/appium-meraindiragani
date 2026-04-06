package com.juaracoding.mera.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ProductPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public ProductPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    By title = By.xpath("//*[@text='PRODUCTS']");
    By sortBtn = AppiumBy.accessibilityId("test-Modal Selector Button");
    By lowToHigh = By.xpath("//*[@text='Price (low to high)']");
    By addToCart = By.xpath("(//*[@content-desc='test-ADD TO CART'])[1]");
    By cart = AppiumBy.accessibilityId("test-Cart");

    public boolean isOnProductPage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
    }

    public void sortLowToHigh() {
        wait.until(ExpectedConditions.elementToBeClickable(sortBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(lowToHigh)).click();
    }

    public void addProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public void goToCart() {
        driver.findElement(cart).click();
    }

    // tambahkan di ProductPage.java

By menuBtn = AppiumBy.accessibilityId("test-Menu");
By logoutBtn = AppiumBy.accessibilityId("test-LOGOUT");

public void logout() {
    wait.until(ExpectedConditions.elementToBeClickable(menuBtn)).click();
    wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
}
}