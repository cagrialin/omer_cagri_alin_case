package org.omer_cagri_alin.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver, long seconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForPageToLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}

