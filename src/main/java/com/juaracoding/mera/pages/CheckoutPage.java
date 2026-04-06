package com.juaracoding.mera.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CheckoutPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    By firstName = AppiumBy.accessibilityId("test-First Name");
    By lastName = AppiumBy.accessibilityId("test-Last Name");
    By zip = AppiumBy.accessibilityId("test-Zip/Postal Code");
    By continueBtn = AppiumBy.accessibilityId("test-CONTINUE");
    By finishBtn = AppiumBy.accessibilityId("test-FINISH");
    By successMsg = By.xpath("//*[contains(@text,'THANK YOU')]");

    public void fillData() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).sendKeys("Mera");
        driver.findElement(lastName).sendKeys("QA");
        driver.findElement(zip).sendKeys("12345");
    }

   public void finishCheckout() {
    driver.findElement(continueBtn).click();
    scrollToFinishButton();
    wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
}

    public boolean isCheckoutSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).isDisplayed();
    }

    public void scrollToFinishButton() {
    try {
        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().description(\"test-FINISH\"))"
        ));
    } catch (Exception e) {
        driver.findElement(new AppiumBy.ByAndroidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().text(\"FINISH\"))"
        ));
    }
}
}