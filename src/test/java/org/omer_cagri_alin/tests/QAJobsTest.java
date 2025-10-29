package org.omer_cagri_alin.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.omer_cagri_alin.pages.*;
import org.omer_cagri_alin.utils.DriverFactory;
import org.omer_cagri_alin.utils.ExtentLogger;
import org.omer_cagri_alin.utils.ExtentManager;
import org.omer_cagri_alin.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.List;

public class QAJobsTest {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private QAListPage qaListPage;
    private JobDetailPage jobDetailPage;

    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void beforeSuite() {
        // Initialize the ExtentReports instance once before all tests in the suite
        extent = ExtentManager.createInstance();
    }

    @BeforeClass
    public void setUp() {
        // Set up WebDriver and initialize all page objects before executing test cases
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        qaListPage = new QAListPage(driver);
        jobDetailPage = new JobDetailPage(driver);
    }

    @Test(priority = 1)
    public void openHomePage_checkOpened() {
        // Create ExtentTest for detailed reporting of this test
        ExtentTest test = extent.createTest("Verify Insider Homepage Loads Successfully");
        ExtentLogger.setTest(test);

        // Step 1: Navigate to the Insider homepage
        ExtentLogger.info("Navigating to Insider homepage...");
        homePage.goToHomePage();

        // Step 2: Verify homepage URL is correct
        String url = driver.getCurrentUrl();
        ExtentLogger.info("Current URL: " + url);

        try {
            Assert.assertTrue(url.contains("useinsider.com"), "Homepage did not open correctly.");
            ExtentLogger.pass("Homepage loaded successfully and URL is correct.");
        } catch (AssertionError e) {
            ExtentLogger.fail("Homepage validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void navigateToCareers_checkSections() {
        // Create ExtentTest for this scenario
        ExtentTest test = extent.createTest("Verify Careers Page Sections Are Visible");
        ExtentLogger.setTest(test);

        // Step 1: Navigate to the Careers page from the Company menu
        ExtentLogger.info("Opening Careers page from Company menu...");
        homePage.openCareersFromCompanyMenu();

        // Step 2: Validate that main content sections are displayed
        try {
            Assert.assertTrue(careersPage.isTeamsVisible(), "Teams section is not visible.");
            ExtentLogger.pass("Teams section is visible.");

            Assert.assertTrue(careersPage.isLocationsVisible(), "Locations section is not visible.");
            ExtentLogger.pass("Locations section is visible.");

            Assert.assertTrue(careersPage.isLifeAtInsiderVisible(), "Life at Insider section is not visible.");
            ExtentLogger.pass("Life at Insider section is visible.");
        } catch (AssertionError e) {
            ExtentLogger.fail("One or more Careers page sections are missing: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void openQAList_filterAndCheckJobs() {
        // Create ExtentTest for QA job filtering scenario
        ExtentTest test = extent.createTest("Filter QA Jobs by Location and Department and Validate Listings");
        ExtentLogger.setTest(test);

        // Step 1: Navigate to QA Careers page
        ExtentLogger.info("Navigating to QA Careers page...");
        careersPage.openQACareers();

        // Step 2: Click the “See all QA jobs” button
        ExtentLogger.info("Clicking 'See all QA jobs' button...");
        qaListPage.clickSeeAllQA();

        // Step 3: Select specific location and department filters
        ExtentLogger.info("Filtering jobs by location (Istanbul, Turkiye) and department (Quality Assurance)...");
        qaListPage.filterByLocation();
        qaListPage.filterByDepartment();

        // Step 4: Verify that job listings appear after applying filters
        try {
            Assert.assertTrue(qaListPage.hasJobsListed(), "No job listings found after filtering.");
            ExtentLogger.pass("Job listings are visible after applying filters.");
        } catch (AssertionError e) {
            ExtentLogger.fail("No jobs were displayed after filtering: " + e.getMessage());
            throw e;
        }

        // Step 5: Validate that each job in the filtered list matches expected criteria
        List<WebElement> jobs = qaListPage.getJobElements();
        ExtentLogger.info("Total jobs found after filtering: " + jobs.size());

        for (WebElement job : jobs) {
            String pos = qaListPage.getPositionFromJob(job);
            String dept = qaListPage.getDepartmentFromJob(job);
            String loc = qaListPage.getLocationFromJob(job);

            ExtentLogger.info(String.format("Validating job: [Position: %s] [Department: %s] [Location: %s]", pos, dept, loc));

            try {
                // Verify the position text
                Assert.assertTrue(pos.toLowerCase().contains("quality assurance") || pos.toLowerCase().contains("qa"),
                        "Position does not contain 'Quality Assurance': " + pos);

                // Verify the department text
                Assert.assertTrue(dept.toLowerCase().contains("quality assurance") || dept.toLowerCase().contains("qa"),
                        "Department does not contain 'Quality Assurance': " + dept);

                // Verify the location text
                Assert.assertTrue(loc.toLowerCase().contains("istanbul"),
                        "Location does not contain 'Istanbul, Turkiye': " + loc);

                ExtentLogger.pass("Job validated successfully: " + pos);
            } catch (AssertionError e) {
                ExtentLogger.fail("Job validation failed: " + e.getMessage());
                throw e;
            }
        }
    }

    @Test(priority = 4)
    public void clickViewRole_redirectsToLever() {
        // Create ExtentTest for “View Role” button redirection validation
        ExtentTest test = extent.createTest("Verify 'View Role' Button Redirects to Lever Application Page");
        ExtentLogger.setTest(test);

        // Step 1: Click on the first “View Role” button
        ExtentLogger.info("Clicking the first 'View Role' button...");
        qaListPage.clickFirstViewRole();

        // Step 2: Switch to the newly opened browser tab
        ExtentLogger.info("Switching to new browser tab (Lever page)...");
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Step 3: Verify redirection to Lever application system
        String currentUrl = driver.getCurrentUrl();
        ExtentLogger.info("Redirected URL: " + currentUrl);

        try {
            Assert.assertTrue(jobDetailPage.isOnLeverApplication(),
                    "View Role button did not redirect to Lever application page.");
            ExtentLogger.pass("Successfully redirected to Lever application page.");
        } catch (AssertionError e) {
            ExtentLogger.fail("Redirection validation failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        // Automatically log test result and attach inline screenshots on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentLogger.fail("Test Failed: " + result.getThrowable());
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());

                // Inline image (thumbnail preview in the report)
                ExtentLogger.fail("<b>Screenshot at failure:</b><br>" +
                        "<a href='" + screenshotPath + "' target='_blank'>" +
                        "<img src='" + screenshotPath + "' width='400' height='250'/></a>");
            } catch (IOException e) {
                ExtentLogger.warning("Failed to attach screenshot: " + e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentLogger.pass("Test Passed Successfully.");
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentLogger.skip("Test Skipped: " + result.getThrowable());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Close the browser and clean up resources after all test executions
        ExtentLogger.info( "Closing browser and cleaning up resources...");
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        // Generate and flush the final Extent HTML report after all test cases are done
        ExtentManager.flushReportAndOpen();
    }
}
