package org.omer_cagri_alin.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.omer_cagri_alin.pages.*;
import org.omer_cagri_alin.utils.DriverFactory;
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
        extent = ExtentManager.getInstance();
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
        test = extent.createTest("Verify Insider Homepage Loads Successfully");

        // Step 1: Navigate to the Insider homepage
        test.log(Status.INFO, "Navigating to Insider homepage...");
        homePage.goToHomePage();

        // Step 2: Verify homepage URL is correct
        String url = driver.getCurrentUrl();
        test.log(Status.INFO, "Current URL: " + url);

        try {
            Assert.assertTrue(url.contains("useinsider.com"), "Homepage did not open correctly.");
            test.log(Status.PASS, "Homepage loaded successfully and URL is correct.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Homepage validation failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2)
    public void navigateToCareers_checkSections() {
        // Create ExtentTest for this scenario
        test = extent.createTest("Verify Careers Page Sections Are Visible");

        // Step 1: Navigate to the Careers page from the Company menu
        test.log(Status.INFO, "Opening Careers page from the Company menu...");
        homePage.openCareersFromCompanyMenu();

        // Step 2: Validate that main content sections are displayed
        try {
            Assert.assertTrue(careersPage.isTeamsVisible(), "Teams section is not visible.");
            test.log(Status.PASS, "Teams section is visible.");

            Assert.assertTrue(careersPage.isLocationsVisible(), "Locations section is not visible.");
            test.log(Status.PASS, "Locations section is visible.");

            Assert.assertTrue(careersPage.isLifeAtInsiderVisible(), "Life at Insider section is not visible.");
            test.log(Status.PASS, "Life at Insider section is visible.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "One or more Careers page sections are missing: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3)
    public void openQAList_filterAndCheckJobs() {
        // Create ExtentTest for QA job filtering scenario
        test = extent.createTest("Filter QA Jobs by Location and Department and Validate Listings");

        // Step 1: Navigate to QA Careers page
        test.log(Status.INFO, "Navigating to QA Careers page...");
        careersPage.openQACareers();

        // Step 2: Click the “See all QA jobs” button
        test.log(Status.INFO, "Clicking 'See all QA jobs' button...");
        qaListPage.clickSeeAllQA();

        // Step 3: Select specific location and department filters
        test.log(Status.INFO, "Filtering jobs by location (Istanbul, Turkiye) and department (Quality Assurance)...");
        qaListPage.filterByLocation();
        qaListPage.filterByDepartment();

        // Step 4: Verify that job listings appear after applying filters
        try {
            Assert.assertTrue(qaListPage.hasJobsListed(), "No job listings found after filtering.");
            test.log(Status.PASS, "Job listings are visible after applying filters.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "No jobs were displayed after filtering: " + e.getMessage());
            throw e;
        }

        // Step 5: Validate that each job in the filtered list matches expected criteria
        List<WebElement> jobs = qaListPage.getJobElements();
        test.log(Status.INFO, "Total jobs found after filtering: " + jobs.size());

        for (WebElement job : jobs) {
            String pos = qaListPage.getPositionFromJob(job);
            String dept = qaListPage.getDepartmentFromJob(job);
            String loc = qaListPage.getLocationFromJob(job);

            test.log(Status.INFO, String.format("Validating job: [Position: %s] [Department: %s] [Location: %s]", pos, dept, loc));

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

                test.log(Status.PASS, "Job validated successfully: " + pos);
            } catch (AssertionError e) {
                test.log(Status.FAIL, "Job validation failed: " + e.getMessage());
                throw e;
            }
        }
    }

    @Test(priority = 4)
    public void clickViewRole_redirectsToLever() {
        // Create ExtentTest for “View Role” button redirection validation
        test = extent.createTest("Verify 'View Role' Button Redirects to Lever Application Page");

        // Step 1: Click on the first “View Role” button
        test.log(Status.INFO, "Clicking the first 'View Role' button...");
        qaListPage.clickFirstViewRole();

        // Step 2: Switch to the newly opened browser tab
        test.log(Status.INFO, "Switching to new browser tab (Lever page)...");
        String original = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(original)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Step 3: Verify redirection to Lever application system
        String currentUrl = driver.getCurrentUrl();
        test.log(Status.INFO, "Redirected URL: " + currentUrl);

        try {
            Assert.assertTrue(jobDetailPage.isOnLeverApplication(),
                    "View Role button did not redirect to Lever application page.");
            test.log(Status.PASS, "Successfully redirected to Lever application page.");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Redirection validation failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void afterEachTest(ITestResult result) {
        // Automatically log test result status and capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
            try {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Passed Successfully.");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Skipped: " + result.getThrowable());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Close the browser and clean up resources after all test executions
        test.log(Status.INFO, "Closing browser and cleaning up resources...");
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        // Generate and flush the final Extent HTML report after all test cases are done
        extent.flush();
    }
}
