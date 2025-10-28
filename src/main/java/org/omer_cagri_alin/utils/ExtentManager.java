package org.omer_cagri_alin.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    // After the scenario runs, it will report the results as an HTML page.
    public static ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
            htmlReporter.config().setDocumentTitle("Insider QA Jobs Automation Report");
            htmlReporter.config().setReportName("Insider Selenium Test Execution");
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Tester", "Ömer Çağrı Alın");
            extent.setSystemInfo("Environment", "UAT");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}
