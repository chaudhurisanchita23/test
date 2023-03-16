package main.java.utils;



import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import test.java.BaseTest;

import java.io.File;
import java.io.IOException;



public class SuiteListener implements ITestListener {

public void onTestStart(ITestResult iTestResult) { /* compiled code */ }

public void onTestSuccess(ITestResult iTestResult) { /* compiled code */ }

public void onTestFailure(ITestResult iTestResult, ITestAnnotation iTestAnnotation) {

        String fileName = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + iTestResult.getMethod().getMethodName();
        File f = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);

        try {
                FileUtils.copyFile(f,new File(fileName+".png"));
        } catch (IOException e) {
                throw new RuntimeException(e);

        }

        iTestAnnotation.setRetryAnalyzer(RetryAnalyzer.class);


}

public void onTestSkipped(ITestResult iTestResult) { /* compiled code */ }

public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) { /* compiled code */ }

public void onTestFailedWithTimeout(ITestResult iTestResult) { /* compiled code */ }

public void onStart(ITestContext iTestContext) { /* compiled code */ }

public void onFinish(ITestContext iTestContext) { /* compiled code */ }


}





