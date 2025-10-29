package org.omer_cagri_alin.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentLogger {

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void info(String message) {
        getTest().log(Status.INFO, message);
    }

    public static void pass(String message) {
        getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        getTest().log(Status.FAIL, message);
    }

    public static void skip(String message) {
        getTest().log(Status.SKIP, message);
    }

    public static void warning(String message) {
        getTest().log(Status.WARNING, message);
    }

    public static void unload() {
        extentTest.remove();
    }
}
