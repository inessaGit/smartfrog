package util;

import runner.BaseTestSuite;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({util.DefaultTestListener.class })
public class TakeScreenshot {

	private static final Logger LOGGER = Logger.getLogger(TakeScreenshot.class);
	private static TakeScreenshot TAKE_SCREENSHOT;
	
	private Constants CONSTANTS;
	private  final String userGenScreenshotDir;
	private  final String failTestScreenshotDir ;
	private  final String passTestScreenshotDir ;
	private String userDirPath;
	private  final String tempScreenshotFolderPath; 
	private  final String testngTestOutputFolderPath;
	
	private TakeScreenshot(){
		CONSTANTS =Constants.getInstance();
		userDirPath = System.getProperty("user.dir");
		userGenScreenshotDir =  userDirPath+CONSTANTS.getUsergenPath();
		failTestScreenshotDir = userDirPath+CONSTANTS.getFailPath();
		passTestScreenshotDir = userDirPath+CONSTANTS.getPassPath();
		
		tempScreenshotFolderPath =userDirPath+"/report/screenshot";
		testngTestOutputFolderPath =userDirPath+"/test-output/screenshot/";
		new File(userGenScreenshotDir).mkdirs();
		new File(failTestScreenshotDir).mkdirs();
		new File(passTestScreenshotDir).mkdirs();
	}
	
	public static TakeScreenshot getInstance(){
		
		if (TAKE_SCREENSHOT==null){
			TAKE_SCREENSHOT = new TakeScreenshot();
		}
		return TAKE_SCREENSHOT;
	}
	
	//1 - user generated path 
	//2 - fail generated path 
	//3 - pass generated path 
	public  void takeScreenshot(WebDriver driver, int dirNumber)

	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destFile =TimeUtil.getCurrentDate()+"_"+TimeUtil.getCurrentTime() + ".png";
		
		Reporter.setEscapeHtml(false);
		try {
			
		 switch (dirNumber)	{
		 case 1:
			FileUtils.moveFile(scrFile, new File(userGenScreenshotDir + destFile));
			LOGGER.info("Generated screenshot. Screenshot saved in "   + userGenScreenshotDir);
			Reporter.log("Saved <a href=" +userGenScreenshotDir+ destFile + ">User Generated Screenshot</a>");
			break;
		
		 case 2: FileUtils.moveFile(scrFile, new File(failTestScreenshotDir + destFile));
			LOGGER.info("Generated screenshot. Screenshot saved in "   + failTestScreenshotDir);
			Reporter.log("Saved <a href=" +failTestScreenshotDir + destFile + ">Test Fail Screenshot</a>");
			break;
			
		 case 3: FileUtils.moveFile(scrFile, new File(passTestScreenshotDir + destFile));
			LOGGER.info("Generated screenshot. Screenshot saved in "   + passTestScreenshotDir);
			Reporter.log("Saved <a href=" +passTestScreenshotDir + destFile + ">Test Pass Screenshot</a>");
			break;
		 }

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug("Could not take screenshot.");
		}
	
	}
	
	//should be called once in @AfrerSuite
	public void copyScreenshotsToTestOutput(){
		File srcDir = new File(tempScreenshotFolderPath);
		File testngTestOutputFolder = new File (testngTestOutputFolderPath);
		
		try {
			FileUtils.copyDirectory(srcDir, testngTestOutputFolder);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug("Could not copyScreenshotsToTestOutput.");

		}
	}
	
	public  void deleteScreenshotsInTestOutput(){
		File testngTestOutputFolder = new File (testngTestOutputFolderPath);
		FileUtils.deleteQuietly(testngTestOutputFolder);

	}
	
	public  void deleteScreenshotsInReportScreenshot(){
		File srcDir = new File(tempScreenshotFolderPath);
		FileUtils.deleteQuietly(srcDir);

	}
}

class TestTakeScreenshot{
	
	@Test
	public void testTakingScreenshot(){
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		
		firefoxDriver.get("http://stackoverflow.com/questions/31744778/opening-ie-web-driver-in-selenium");
		TakeScreenshot.getInstance().takeScreenshot(firefoxDriver,1);
		BaseTestSuite.destroyWebDrivers();
	}
	
	@Test
	public void testScreenshotOnFail(){
		
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		
		boolean actual =false;
		boolean expected = true; 
		Assert.assertEquals(actual, expected);
		//when test fails @AfterSuite does not run
	}
	
	@Test
	public void testScreenshotonPass(){
		
		WebDriver firefoxDriver =  BaseTestSuite.getFirefoxDriver();
		firefoxDriver.get("https://dev.mysql.com/doc/refman/5.7/en/date-and-time-functions.html#function_date-add");
		boolean actual =true;
		boolean expected = true; 
		Assert.assertEquals(actual, expected);
		BaseTestSuite.destroyWebDrivers();

	}
	 public static void main (String args[]){
		
	 }
}
