package org.omer_cagri_alin.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    private static String reportPath;

    // Responsible for creating, configuring, and managing Reports, after all tests finish, the report is automatically opened in the default system browser.
    public static ExtentReports createInstance() {
        if (extent == null) {
            // Create timestamp for unique report name
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Define report directory and file
            String reportDir = System.getProperty("user.dir") + "/test-output/reports/";
            new File(reportDir).mkdirs();
            reportPath = reportDir + "ExtentReport_" + timestamp + ".html";

            // Initialize Spark HTML Reporter
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("Insider QA Automation Report");
            spark.config().setReportName("Insider QA Jobs UI Test Execution");
            spark.config().setTheme(Theme.DARK);
            spark.config().setEncoding("UTF-8");

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add environment/system information
            extent.setSystemInfo("Project", "Insider Careers Automation");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Tester", "Ömer Çağrı Alın");
        }
        return extent;
    }

    public static void flushReportAndOpen() {
        if (extent != null) {
            extent.flush();
            try {
                File reportFile = new File(reportPath);
                if (reportFile.exists()) {
                    Desktop.getDesktop().browse(reportFile.toURI());
                    System.out.println("✅ Test report opened automatically: " + reportPath);
                } else {
                    System.out.println("⚠️ Report file not found: " + reportPath);
                }
            } catch (IOException e) {
                System.out.println("⚠️ Failed to open report automatically: " + e.getMessage());
            }
        }
    }
}
