package org.omer_cagri_alin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final String BASE_URL = "https://useinsider.com/";

    // Optimized selectors (CSS/XPath)
    private By companyMenu = By.xpath("//a[@id='navbarDropdownMenuLink' and contains(normalize-space(.), 'Company')]");
    private By careersLink = By.xpath("//a[@class='dropdown-sub' and contains(normalize-space(.), 'Careers')]");
    private By cookies = By.id("wt-cli-accept-all-btn");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToHomePage() {

        // Go to base url
        driver.get(BASE_URL);
        wait.waitForSeconds(2);
    }

    public void openCareersFromCompanyMenu() {

        //If you wish to accept cookies
        click(cookies);

        // Click Company and then Career.
        click(companyMenu);
        click(careersLink);
        wait.waitForSeconds(2);
    }
}