package org.omer_cagri_alin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class QAListPage extends BasePage {

    // Optimized selectors (CSS/XPath)
    private By seeAllQAButton = By.xpath("//a[contains(normalize-space(),'See all QA jobs') or contains(.,'See all QA jobs')]");

    // Select2 dropdown opener locators
    private By locationDropdown = By.id("select2-filter-by-location-container");
    private By departmentDropdown = By.id("select2-filter-by-department-container");

    // Job listing elements
    private By jobsListItems = By.cssSelector("div.position-list-item, li.position-list-item, .position-list .position-item");
    private By jobPosition = By.cssSelector(".position-title, h3, h2");
    private By jobDepartment = By.cssSelector(".position-department, .department");
    private By jobLocation = By.cssSelector(".position-location, .location");

    // View Role button
    private By viewRoleButton = By.xpath("//a[contains(.,'View Role') or contains(.,'Apply')]");

    public QAListPage(WebDriver driver) {
        super(driver);
    }

    // Clicks "See all QA jobs" button
    public void clickSeeAllQA() {
        click(seeAllQAButton);
        wait.waitForSeconds(5);
    }

    // Filters job list by location using Select2 dropdown
    public void filterByLocation() {
        selectFromLongDropdown(locationDropdown, "Istanbul, Turkiye");
        wait.waitForSeconds(2);
    }

    // Filters job list by department using Select2 dropdown
    public void filterByDepartment() {
        selectFromLongDropdown(departmentDropdown, "Quality Assurance");
        wait.waitForSeconds(2);
    }

    // Gets all visible job elements
    public List<WebElement> getJobElements() {
        return driver.findElements(jobsListItems).stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());
    }

    // Checks if any jobs are listed
    public boolean hasJobsListed() {
        return !getJobElements().isEmpty();
    }

    // Extracts position text from a job card
    public String getPositionFromJob(WebElement jobElement) {
        try {
            return jobElement.findElement(jobPosition).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // Extracts department text from a job card
    public String getDepartmentFromJob(WebElement jobElement) {
        try {
            return jobElement.findElement(jobDepartment).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // Extracts location text from a job card
    public String getLocationFromJob(WebElement jobElement) {
        try {
            return jobElement.findElement(jobLocation).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // Clicks first "View Role" button
    public void clickFirstViewRole() {
        List<WebElement> jobs = getJobElements();
        if (!jobs.isEmpty()) {
            WebElement first = jobs.get(0);
            try {
                WebElement btn = first.findElement(viewRoleButton);
                btn.click();
            } catch (Exception e) {
                click(viewRoleButton);
            }
        }
        wait.waitForSeconds(2);
    }
}
