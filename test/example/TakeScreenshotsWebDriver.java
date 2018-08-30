package test.example;
import runner.BaseTestSuite;
import util.TakeScreenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;


public class TakeScreenshotsWebDriver {

	@Test
	public void testTakeScreenshot(){
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		
		firefoxDriver.get("http://stackoverflow.com/questions/31744778/opening-ie-web-driver-in-selenium");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		BaseTestSuite.destroyWebDrivers();
	}
	
	@Test
	public void copyScreenshotsToTestOutput(){
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		TakeScreenshot.getInstance().copyScreenshotsToTestOutput();
		BaseTestSuite.destroyWebDrivers();
	}
	
	@Test
	public void deleteScreenshotsInTestNGFolder(){
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		
		TakeScreenshot.getInstance().deleteScreenshotsInTestOutput();
		BaseTestSuite.destroyWebDrivers();
	}
	
	@Test
	public void deleteScreenshotsInReportFolder(){
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		TakeScreenshot.getInstance().deleteScreenshotsInReportScreenshot();
		BaseTestSuite.destroyWebDrivers();
	}
}
