package org.omer_cagri_alin.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (tlDriver.get() == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            // options.addArguments("--headless=new");
            WebDriverManager.chromedriver().setup();
            tlDriver.set(new ChromeDriver(options));
        }
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
