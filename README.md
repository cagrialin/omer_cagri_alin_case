# omer_cagri_alin_case
# 🧪 Website UI Automation Project

## 📋 Project Overview
This project is an **end-to-end UI automation suite** built with **Java**, **Selenium WebDriver**, and **TestNG** to validate the **Careers → Quality Assurance** job listing flow on [Insider’s website](https://useinsider.com/).

It automatically navigates through key sections of the Careers page, applies location and department filters on the job listings, and verifies that job details are correctly displayed.  
The project also integrates **ExtentReports** for generating detailed HTML test reports and **ScreenshotUtils** for capturing screenshots on test failures.

---

## 🧱 Project Structure (Page Object Model)

The project follows the **Page Object Model (POM)** design pattern — every page of the website has a dedicated class containing locators and actions related to that page.

## 🚀 Features & Scenarios

| Test Case | Description                                                                                                                |
|------------|----------------------------------------------------------------------------------------------------------------------------|
| **1️⃣ openHomePage_checkOpened** | Opens BASE_URL’s homepage and verifies the correct URL.                                                                    |
| **2️⃣ navigateToCareers_checkSections** | Navigates to the page and verifies visibility of “VALUE1”, “VALUE2”, and “VALUE3” sections.                                |
| **3️⃣ openQAList_filterAndCheckJobs** | Navigates to the jobs page, filters jobs by *OPTION1* and *OPTİON2*, and verifies all listed jobs in Location. |
| **4️⃣ clickViewRole_redirectsToLever** | Clicks on “BUTTON” for the first job and verifies the redirect to portal.                                                  |

---

## 🧰 Technologies Used

| Category | Tool / Library | Purpose |
|-----------|----------------|----------|
| **Programming Language** | Java 11 | Core automation language |
| **Test Framework** | TestNG | Test structure, assertions, and execution control |
| **Automation Engine** | Selenium 4.28.1 | Browser automation |
| **Build Tool** | Maven 3.9.5 | Dependency management and build lifecycle |
| **Reporting** | ExtentReports | HTML-based test report generation |
| **Utilities** | ScreenshotUtils | Takes screenshots automatically on failure |
| **Design Pattern** | Page Object Model (POM) | Increases maintainability and readability |

---

## 🧩 Key Utility Classes

### 🕐 `WaitUtils.java`
Handles all synchronization between Selenium actions and the browser.  
Includes:
- `waitForVisible()`
- `waitForClickable()`
- `waitForPageToLoad(int seconds)`
- `waitForSeconds(int seconds)`

---

### 📜 `BasePage.java`
The parent class for all page objects.  
Provides reusable methods such as:
- `click()`, `element()`, `isDisplayed()`
- `selectFromLongDropdown()` → handles Select2-style dropdowns with scrolling
- `scrollUntilElementVisible()` → scrolls the page until a target element is visible

---

### 📄 `QAJobsTest.java`
Main TestNG test class that:
- Performs navigation and filtering
- Executes assertions for job details
- Generates ExtentReports logs
- Captures screenshots on failure (via `ITestListener` integration)

---

## 📊 Reporting

After running tests, a detailed **HTML report** is generated under:

Each test step is logged with:
- ✅ **PASS**: when the step is successful
- ❌ **FAIL**: when an assertion fails (with screenshot)
- ⚙️ **INFO**: when general information is logged

---

## 📸 Screenshot on Failure

When a test fails, the project automatically:
1. Captures a screenshot of the current browser state.
2. Saves it under `/screenshots/` directory.
3. Attaches it to the HTML report.

This feature helps in debugging failed steps visually.

---

## 🧠 Design Principles

This project was developed following:
- **POM (Page Object Model)** → clean separation of logic and elements
- **Reusable Utilities** → for waits, scrolling, and screenshots
- **Readable Assertions** → each test method reads like a natural test scenario
- **Scalable Structure** → easily extendable for new pages or features

---

## ▶️ How to Run

### **1. Clone the project**
```bash
git clone https://github.com/yourusername/omer_cagri_alin_case.git
cd omer_cagri_alin_case

mvn clean install

mvn test

/test-output/ExtentReports/ExtentReport.html


