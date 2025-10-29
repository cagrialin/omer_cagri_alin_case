package org.omer_cagri_alin.pages;

import org.omer_cagri_alin.utils.WaitUtils;
import org.openqa.selenium.*;

public abstract class BasePage {
    protected WebDriver driver;
    protected WaitUtils wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 15);
    }

    protected WebElement element(By locator) {
        return wait.waitForVisible(locator);
    }

    protected void click(By locator) {
        wait.waitForClickable(locator);
        element(locator).click();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return element(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollUntilElementVisible(By locator, int maxScrollAttempts) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < maxScrollAttempts; i++) {
            try {
                WebElement el = driver.findElement(locator);
                if (el.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
                    return; // element found and visible
                }
            } catch (NoSuchElementException ignored) {
            }

            // Scroll down incrementally
            js.executeScript("window.scrollBy(0, 400);");
            wait.waitForSeconds(1);
        }

        throw new RuntimeException("Element not found after " + maxScrollAttempts + " scroll attempts: " + locator);
    }

    // Opens a long Select2 dropdown, scrolls inside it if needed, and selects the target option.
    // Works best for UI tests where dropdown behavior must be simulated.
    // Created with the help of ChatGPT AI.

    protected void selectFromLongDropdown(By openerLocator, String optionText) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Click dropdown opener
            WebElement opener = driver.findElement(openerLocator);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", opener);
            opener.click();
            wait.waitForSeconds(1);

            // Container locator (Select2 dropdown list)
            By containerLocator = By.xpath("//span[contains(@class,'select2-results')]/ul");
            WebElement container = element(containerLocator);

            // Try to find and click target
            boolean clicked = false;
            for (int i = 0; i < 20; i++) { // up to 20 scrolls
                try {
                    WebElement target = container.findElement(
                            By.xpath(".//li[contains(@class,'select2-results__option') and normalize-space(text())='" + optionText + "']")
                    );

                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", target);
                    wait.waitForSeconds(1);
                    target.click();
                    clicked = true;
                    break;
                } catch (NoSuchElementException e) {
                    js.executeScript("arguments[0].scrollTop += arguments[0].clientHeight * 0.8;", container);
                    wait.waitForSeconds(1);
                }
            }

            if (!clicked) {
                throw new RuntimeException("Option '" + optionText + "' not found in dropdown.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to select option from dropdown: " + e.getMessage(), e);
        }
    }

}

