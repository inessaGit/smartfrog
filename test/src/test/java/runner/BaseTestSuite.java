package runner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pages.BasePage;
import util.Constants;
import util.Return2D;
import util.TakeScreenshot;
import util.WebDriverManager;

/**
 * @author Inessa 
 * 1)Every PageTest class should extend this class
 * 2) This class contains common logic for all test classes; driven by TestNG
 *
 */

@Listeners({util.DefaultTestListener.class})
public abstract class BaseTestSuite {

	private static WebDriverManager DRIVER_MANAGER = null;
	private static   WebDriver FIREFOX_DRIVER = null;
	private static  WebDriver CHROME_DRIVER = null;
	private static WebDriver IE_DRIVER = null;
	private static  WebDriver SAFARI_DRIVER = null;
	private static WebDriver IOS_MOBILE_DRIVER = null;


    private static final Logger LOGGER = Logger.getLogger(BaseTestSuite.class);
	public static  Constants CONSTANTS=null;

	//spreadsheet for with test data
	public static   Sheet SHEET_REGISTRATION;
	public static final Return2D RETURN2D;

	//static block should be executed only once when the class loaded; a good place to put initialization of static variables.
	static
	{
		DRIVER_MANAGER = WebDriverManager.getInstance();//all drivers are set in constructor 
		CONSTANTS = Constants.getInstance();
		RETURN2D = new Return2D();
		
		String filePath =System.getProperty("user.dir")+CONSTANTS.getTest_excelPath();
		try {
			LOGGER.info(BaseTestSuite.class.getName()+" attempting to load test data  spreadsheets");	 
			SHEET_REGISTRATION=RETURN2D.getSheet(filePath, "TEST_Registration");
			LOGGER.info(BaseTestSuite.class.getName()+" Test data  spreadsheets successfully loaded");	 

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug(e);
		}
	}
	
	public static WebDriverManager getWebDriverManager(){
		return DRIVER_MANAGER;
	}
	// DRIVER getters 
	public static  WebDriver getFirefoxDriver() {
		return FIREFOX_DRIVER;
	}

	public static  WebDriver getIOSMobileDriver() {
		return IOS_MOBILE_DRIVER;
	}

	
	public static WebDriver getChromeDriver() {
		return CHROME_DRIVER;
	}

	public static WebDriver getIeDriver() {
		return IE_DRIVER;
	}

	public static WebDriver getSafariDriver() {
		return SAFARI_DRIVER;
	}

	
	@BeforeMethod() // @BeforeSuite 
	public static void startDrivers(){
		LOGGER.info(BaseTestSuite.class.getName()+" running @BeforeTest - startDrivers()");	 
		FIREFOX_DRIVER = DRIVER_MANAGER.getDriver("firefox");
		//CHROME_DRIVER = DRIVER_MANAGER.getDriver("chrome");
		//IE_DRIVER = DRIVER_MANAGER.getDriver("ie");
		//SAFARI_DRIVER = DRIVER_MANAGER.getDriver("safari");
		//IOS_MOBILE_DRIVER = DRIVER_MANAGER.getDriver("iosMobileDriver");
		LOGGER.info(BaseTestSuite.class.getName()+" running @BeforeTest - all drivers started");	 
	}

	
	//@BeforeTest - For suite test, run before any test method belonging to the classes inside the <test> tag is run. 
	//@AfterTest - For suite test, run after all the test methods belonging to the classes inside the <test> tag have run. 
	
	@AfterMethod(alwaysRun=true)
	public static void closeDrivers(){

		LOGGER.info(BaseTestSuite.class.getName()+ " Attempting to  close  WebDriver browser windows.");
		DRIVER_MANAGER.closeDriverWindows("firefox");
		DRIVER_MANAGER.closeDriverWindows("chrome");
		DRIVER_MANAGER.closeDriverWindows("ie");
	    DRIVER_MANAGER.closeDriverWindows("safari");
		DRIVER_MANAGER.closeDriverWindows("iosMobileDriver");
		LOGGER.info(BaseTestSuite.class.getName()+ " Successfully closed  WebDriver browser windows.");

	}

   @AfterSuite(alwaysRun=true)
	public static void destroyWebDrivers()
	{
		LOGGER.info(BaseTestSuite.class.getName()+ " Attempting to  destroy WebDriver instances.");
		DRIVER_MANAGER.destroyWebDriverInstances("firefox");
		DRIVER_MANAGER.destroyWebDriverInstances("chrome");
		DRIVER_MANAGER.destroyWebDriverInstances("safari");
		DRIVER_MANAGER.destroyWebDriverInstances("ie");
		DRIVER_MANAGER.destroyWebDriverInstances("iosMobileDriver");
		
		LOGGER.info(BaseTestSuite.class.getName()+ " Successfully destroyed  webdriver instances.");
		}

 
	}