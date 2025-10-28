package org.omer_cagri_alin.tests;

import org.omer_cagri_alin.pages.*;
import org.omer_cagri_alin.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class QAJobsTest {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private QAListPage qaListPage;
    private JobDetailPage jobDetailPage;

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and page objects before running tests
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        qaListPage = new QAListPage(driver);
        jobDetailPage = new JobDetailPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage_checkOpened() {
        // Navigate to Insider homepage
        homePage.goToHomePage();

        // Verify that homepage is opened successfully
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("useinsider.com"),
                "Home page did not open - Unexpected URL: " + url);
    }

    @Test(priority = 2)
    public void navigateToCareers_checkSections() {
        // Navigate to Careers page from Company menu
        homePage.openCareersFromCompanyMenu();

        // Validate visibility of main sections on the Careers page
        Assert.assertTrue(careersPage.isTeamsVisible(), "Teams section is not visible");
        Assert.assertTrue(careersPage.isLocationsVisible(), "Locations section is not visible");
        Assert.assertTrue(careersPage.isLifeAtInsiderVisible(), "Life at Insider section is not visible");
    }

    @Test(priority = 3)
    public void openQAList_filterAndCheckJobs() {
        // Step 1: Navigate to the QA Careers page
        careersPage.openQACareers();

        // Step 2: Click the “See all QA jobs” button -> Opens Insider open positions page
        qaListPage.clickSeeAllQA();

        // Step 3: Select filters for Location and Department dropdowns
        qaListPage.filterByLocation();
        qaListPage.filterByDepartment();

        // Step 4: Verify that job listings are visible after applying filters
        Assert.assertTrue(qaListPage.hasJobsListed(), "No job listings found after filtering");

        // Step 5: Validate each job card’s details (position, department, and location)
        List<WebElement> jobs = qaListPage.getJobElements();
        for (WebElement job : jobs) {
            String pos = qaListPage.getPositionFromJob(job);
            String dept = qaListPage.getDepartmentFromJob(job);
            String loc = qaListPage.getLocationFromJob(job);

            // Verify that the job position includes "Quality Assurance" or "QA"
            Assert.assertTrue(pos.toLowerCase().contains("quality assurance") || pos.toLowerCase().contains("qa"),
                    "Position does not contain 'Quality Assurance': " + pos);

            // Verify that the department field includes "Quality Assurance" or "QA"
            Assert.assertTrue(dept.toLowerCase().contains("quality assurance") || dept.toLowerCase().contains("qa"),
                    "Department does not contain 'Quality Assurance': " + dept);

            // Verify that the location field includes "Istanbul"
            Assert.assertTrue(loc.toLowerCase().contains("istanbul"),
                    "Location does not contain 'Istanbul, Turkiye': " + loc);
        }
    }

    @Test(priority = 4)
    public void clickViewRole_redirectsToLever() {
        // Step 1: Click on the first “View Role” button from the job list
        qaListPage.clickFirstViewRole();

        // Step 2: Switch to the newly opened browser tab (Lever application page)
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Step 3: Verify that the redirected page belongs to Lever (application system)
        Assert.assertTrue(jobDetailPage.isOnLeverApplication(),
                "View Role button did not redirect to Lever application page. Current URL: " + driver.getCurrentUrl());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Close the browser and clean up after tests
        DriverFactory.quitDriver();
    }
}
