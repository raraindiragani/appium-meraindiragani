package com.juaracoding.mera.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public LoginPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    By username = AppiumBy.accessibilityId("test-Username");
    By password = AppiumBy.accessibilityId("test-Password");
    By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
    By errorMsg = By.xpath("//*[contains(@text,'Username and password')]");

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }

    public boolean isErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg)).isDisplayed();
    }
}