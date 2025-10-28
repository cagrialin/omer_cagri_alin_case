package org.omer_cagri_alin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class JobDetailPage extends BasePage {
    // Lever usually redirects to a domain such as “apply.lever.co”
    private By leverApplyFormMarker = By.xpath("//iframe[contains(@src,'lever')]|//h1[contains(.,'Apply')]|//form[contains(@action,'lever.co')]|//div[contains(@class,'lever')]");

    public JobDetailPage(WebDriver driver) {
        super(driver);
    }

    // Simple check: Does the URL contain “lever” or is there a Lever form marker on the page?
    public boolean isOnLeverApplication() {
        String url = driver.getCurrentUrl();
        if (url != null && url.toLowerCase().contains("lever")) {
            return true;
        }
        return isDisplayed(leverApplyFormMarker);
    }
}
