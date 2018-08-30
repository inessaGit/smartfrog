package util;

import runner.BaseTestSuite;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.apache.log4j.*;


/*

/*THIS CLASS DEFINES RULES for DEFAULT BEHAVIOUR of  org.testng.ITestResult.Failure or Success
 * http://testng.org/javadocs/constant-values.html#org.testng.ITestResult.FAILURE

 * TO USE THIS CLASS:
 *put this annotation before you class where you define your test methods
 @Listeners({ util.DefaultTestListener.class })
 */
public class DefaultTestListener implements ITestListener, ISuiteListener {

	WebDriver driver;
	private static final Logger LOGGER = Logger.getLogger(DefaultTestListener.class);
	private static final Constants CONSTANTS =Constants.getInstance();
	String failTestScreenshotDir = System.getProperty("user.dir")+CONSTANTS.getFailPath();
	String userGenScreenshotDir = System.getProperty("user.dir")+CONSTANTS.getUsergenPath();
	String passTestScreenshotDir = System.getProperty("user.dir")+CONSTANTS.getPassPath();


	/**
	 * This method is invoked after the SuiteRunner (parent suite) has finished to run all the child suites.
	 */
	public void onFinish(ISuite suite) {
		Reporter.log("Finish executing Suite " + suite.getName(), true);
		Reporter.log(suite.getName()+" "+suite.getResults());
	}

	@Override
	public void onStart(ISuite suite) {
		Reporter.log("Begin executing Suite " + suite.getName(), true);
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		Object currentClass = tr.getInstance();
		driver   = ((BaseTestSuite) currentClass).getFirefoxDriver();
		if(tr.getStatus() == ITestResult.FAILURE)
		{
			System.out.println(tr.getTestClass()+" "+tr.getMethod()+" test run successful: "+tr.isSuccess());
			LOGGER.debug(tr.getMethod()+" test run successful: "+tr.isSuccess());
			TakeScreenshot.getInstance().takeScreenshot(driver,2);
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr) {

		if(tr.getStatus() == ITestResult.SKIP ){
			System.out.println("Skiped test "+tr.getTestClass()+" "+ tr.getMethod());
			LOGGER.info("Skiped test "+ tr.getMethod());
			Reporter.log("Skiped test "+ tr.getMethod());
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		Object currentClass = tr.getInstance();
		driver   = ((BaseTestSuite) currentClass).getFirefoxDriver();
		if(tr.getStatus() == ITestResult.SUCCESS)
		{
			System.out.println(tr.getTestClass()+" "+tr.getMethod()+" test run successful: "+tr.isSuccess());
			LOGGER.info(tr.getMethod()+" test run successful: "+tr.isSuccess());
			TakeScreenshot.getInstance().takeScreenshot(driver,3);
		}
	}


	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}


}