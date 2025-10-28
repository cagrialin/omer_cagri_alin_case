package org.omer_cagri_alin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    private final String AQ_CAREERS_URL = "https://useinsider.com/careers/quality-assurance/";

    // Optimized selectors (CSS/XPath)
    private By teamsBlock = By.xpath("//h3[contains(normalize-space(.), 'Find your calling')]");
    private By locationsBlock = By.xpath("//h3[contains(normalize-space(.), 'Our Locations')]");
    private By lifeAtInsiderBlock = By.xpath("//h2[contains(normalize-space(.), 'Life at Insider')]");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    // Location block visible
    public boolean isLocationsVisible() {
        return isDisplayed(locationsBlock);
    }
    // Teams block visible
    public boolean isTeamsVisible() {
        return isDisplayed(teamsBlock);
    }
    // Life at Insider block visible
    public boolean isLifeAtInsiderVisible() {
        return isDisplayed(lifeAtInsiderBlock);
    }

    // QA careers direct link
    public void openQACareers() {
        driver.get(AQ_CAREERS_URL);
        wait.waitForSeconds(2);
    }
}
