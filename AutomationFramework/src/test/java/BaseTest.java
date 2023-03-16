package test.java;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import main.java.utils.CaptureRequests;
import main.java.utils.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;


public class BaseTest {

    public static WebDriver driver;
    public ExtentSparkReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest logger;

    public static void setupDriver(String browserName) {
        if(browserName.equalsIgnoreCase("chrome")){

            System.setProperty("webDriver.chrome.driver",System.getProperty("user.dir")+ File.separator+"drivers"+File.separator+"chromedriver");
            driver= new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")){

            System.setProperty("webDriver.gecko.driver",System.getProperty("user.dir")+ File.separator+"drivers"+File.separator+"geckodriver");
            driver= new FirefoxDriver();
        }

        else {
            System.setProperty("webDriver.chrome.driver",System.getProperty("user.dir")+ File.separator+"drivers"+File.separator+"chromedriver");
            driver= new ChromeDriver();

        }


    }

    @BeforeTest
    public void beforeTestMethod(){
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"AutomationReports");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation report");
        htmlReporter.config().setReportName("Automation test results");
        htmlReporter.config().setTheme(Theme.DARK);

        extent= new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo( "Automation tester", "Sanchita");// adding additional info

    }


    @BeforeMethod
    @Parameters(value = "browserName")
    public void beforeMethodMethod(String browserName, Method testMethod ){
        CaptureRequests captureRequests = new CaptureRequests();
        logger = extent.createTest(testMethod.getName());
        //The createTest method returns a ExtentTest object which can be used
        // to publish logs, create nodes, assign attributes (tags, devices, authors) or attach screenshots.
          setupDriver(browserName);
          captureRequests.captureHttpRequests(driver);
          driver.manage().deleteAllCookies();
          driver.manage().window().maximize();
          driver.get(Constant.url);
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @AfterMethod
    public void afterMethodMethod(ITestResult result) {
        if(result.getStatus() == ITestResult.SUCCESS) {

            String methodName = result.getMethod().getMethodName();
            String logText = "Test Case:" + methodName + "Passed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
            logger.log(Status.PASS, m);

        } else if (result.getStatus() == ITestResult.FAILURE) {

            String methodName = result.getMethod().getMethodName();
            String logText = "Test Case:" + methodName + "Failed";
            Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
            logger.log(Status.FAIL, m);
            
        }
        driver.quit();

    }


    @AfterTest
    public void afterTestMethod(){
        extent.flush();


    }


}
