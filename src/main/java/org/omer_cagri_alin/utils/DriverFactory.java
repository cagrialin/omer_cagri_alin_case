package org.omer_cagri_alin.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {

        // Start Driver
        if (tlDriver.get() == null) {
            ChromeOptions options = new ChromeOptions();

            // Browser setups
            options.addArguments("--start-maximized");       // Launch browser in full screen
            // options.addArguments("--headless=new");       // Run in the background (invisible mode)
            options.addArguments("--disable-notifications"); // Run in the background (invisible mode)
            options.addArguments("--disable-popup-blocking");// Disable pop-up blocking
            options.addArguments("--disable-infobars");      // Hide the “Chrome is being controlled by automated test software” warning
            options.addArguments("--disable-extensions");    // Disable extensions
            options.addArguments("--no-sandbox");            // Disable security sandbox on Linux
            options.addArguments("--disable-dev-shm-usage"); // Reduces memory usage errors

            WebDriverManager.chromedriver().setup();
            tlDriver.set(new ChromeDriver(options));
        }
        return tlDriver.get();
    }

    // Close Driver
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
