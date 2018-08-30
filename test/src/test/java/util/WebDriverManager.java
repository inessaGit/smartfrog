package util;


import runner.BaseTestSuite;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;

/*
 * WebDriver 3.4 requires JDK 8;
 * supports geckodriver 017
 * chromedriver 2.31
 * 
 */
public class WebDriverManager {
	private static  Logger LOGGER = Logger.getLogger(WebDriverManager.class);
	private static  WebDriverManager DRIVER_MANAGER= null;
	private   WebDriver firefoxDriver =null;
	private   WebDriver chromeDriver =null;
	private   WebDriver ieDriver =null;
	private   WebDriver safariDriver =null;
	private WebDriver iosMobileDriver = null;

	/*
	 * To set  preference in browser X, how to do the same in browser Y?This is usually done via setting/configuring:
FirefoxProfile or FirefoxOptions for Firefox 
ChromeOptions for Chrome
DesiredCapabilities or InternetExplorerOptions for Internet Explorer
Ultimately FirefoxProfile and ChromeOptions are just wrappers over DesiredCapabilities 
in the end, everything is converted down to a dictionary of DesiredCapabilities
	 */
	private DesiredCapabilities capabilityFirefox=null;
	private DesiredCapabilities capabilityChrome=null;
	private DesiredCapabilities capabilityIE=null;
	private DesiredCapabilities capabilitySafari=null;
	private DesiredCapabilities capabilityiosMobile=null;
	Constants CONSTANTS  =  Constants.getInstance();		

	//singleton private constructor doing initialization 
	private  WebDriverManager(){
		
		LOGGER.info (WebDriverManager.class.getName()+ " running constructor. Setting all drivers.");
		setDriver("firefox");
		setDriver("chrome");
		setDriver("ie");
		setDriver("safari"); 
		setDriver("iosMobileDriver"); 
	}

	public static WebDriverManager getInstance(){
		if(DRIVER_MANAGER == null) {
			LOGGER.info(WebDriverManager.class.getName()+ " WebDriverManager object does not exist. Creating object");
			DRIVER_MANAGER = new WebDriverManager();

		}
		else {
			LOGGER.info(WebDriverManager.class.getName()+ " WebDriverManager object already exist");
		}
		return DRIVER_MANAGER;
	}
	//getter method for all private fields 
	public   WebDriver getDriver(String browser){

		WebDriver driver = null;
		if (browser instanceof String){
			browser.toLowerCase();

			switch(browser){			
			case "firefox":
				firefoxDriver=new FirefoxDriver(capabilityFirefox);
				driver = firefoxDriver;
				LOGGER.info (WebDriverManager.class.getName()+ " getting driver instance  "+browserInfo(firefoxDriver));
				break;

			case "chrome":
				//start Chrome instance 
				chromeDriver =new ChromeDriver(capabilityChrome);
				WebDriverManager.defaultWindowSize(chromeDriver);
				driver = chromeDriver;
				LOGGER.info (WebDriverManager.class.getName()+ " getting driver instance  "+browserInfo(chromeDriver));
				break;

			case "iosMobileDriver":
				//start iosMobile instance
				driver = iosMobileDriver;
				try {
					iosMobileDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilityiosMobile);
					LOGGER.info (WebDriverManager.class.getName()+ " getting driver instance  "+browserInfo(iosMobileDriver));

				} catch (Exception e) {
					LOGGER.debug(WebDriverManager.class.getName()+ " exception getting iosMobileDriver instance "+e);
					e.printStackTrace();
				}
				break;

			case "safari": 
				driver =safariDriver;
				try {
					//start Safari instance 
					safariDriver = new SafariDriver(capabilitySafari);
					LOGGER.info (WebDriverManager.class.getName()+ " getting driver instance  "+browserInfo(safariDriver));
				}
				catch (WebDriverException e){
					LOGGER.debug(WebDriverManager.class.getName()+ " exception getting SafariDriver instance "+e);
					e.printStackTrace();
				}
				break;
				
			case "ie":
				driver = ieDriver; //start IE instance 
				ieDriver=new InternetExplorerDriver(capabilityIE);
				LOGGER.info (WebDriverManager.class.getName()+ " getting driver instance  "+browserInfo(ieDriver));
				break;

			default:
				System.out.println("Enter browser name as string. Browser type unsupported");
				LOGGER.debug("Enter browser name as string. Browser type unsupported");
				throw new IllegalArgumentException("Browser type unsupported");//super for IllegalArgumentExc
			}
		}

		return driver;
	}

	private  void setDriver(String browser)
	{
		if(browser.equalsIgnoreCase("firefox")) 
		{
			if (firefoxDriver ==null){
				//path to gecko driver -> needed for Webdriver 3.0.1 or higher
				String firefoxPath=System.getProperty("user.dir")+CONSTANTS.getGeckoPathWin();//reading from config.properties file 
				System.setProperty("webdriver.gecko.driver", firefoxPath);	
				capabilityFirefox=DesiredCapabilities.firefox();
			    FirefoxOptions firefoxOptions = new FirefoxOptions();

			    FirefoxProfile firefoxProfile = new FirefoxProfile();
				//Increasing the length of the network.http.phishy-userpass-length preference will cause Firefox to not prompt
				//when navigating to a website with a username or password in the URL
				firefoxProfile.setPreference("network.http.phishy-userpass-length", 255);
				firefoxProfile.setAcceptUntrustedCertificates(true);
			    
                /*throws org.openqa.selenium.SessionNotCreatedException: Unable to find a matching set of capabilities
				capabilityFirefox.setBrowserName("Firefox");
				*/
				capabilityFirefox.setPlatform(org.openqa.selenium.Platform.ANY);
				capabilityFirefox.setJavascriptEnabled(true);

			firefoxOptions.setProfile(firefoxProfile);
			capabilityFirefox.setCapability(FirefoxDriver.PROFILE, firefoxProfile);	
			capabilityFirefox.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);	
			}
		}

		else if(browser.equalsIgnoreCase("ie")) {
			if (ieDriver ==null){
				String iePath=System.getProperty("user.dir")+CONSTANTS.getIePathWin();//reading from config.properties file 
				System.setProperty("webdriver.ie.driver", iePath);	

				capabilityIE = DesiredCapabilities.internetExplorer();
				capabilityIE.setBrowserName("internet explorer");
				capabilityIE.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				capabilityIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			}
		}
		
		else  if(browser.equalsIgnoreCase("chrome")) {

			if (chromeDriver == null){
				
				String chromePath = System.getProperty("user.dir")+CONSTANTS.getChromePathMac();
				if (System.getProperty("os.name").contains("Windows")) {
					chromePath =System.getProperty("user.dir")+CONSTANTS.getChromePathWin();
				}
				System.setProperty("webdriver.chrome.driver", chromePath);
				
				capabilityChrome = DesiredCapabilities.chrome();
				ChromeOptions chromeOptions = new ChromeOptions(); 

				capabilityChrome.setBrowserName("CHROME");
				capabilityChrome.setPlatform(org.openqa.selenium.Platform.ANY);
				chromeOptions.addArguments("test-type");
				capabilityChrome.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

			}    
		}
		else if(browser.equalsIgnoreCase("safari")) {
			if (safariDriver ==null){
				if(WebDriverManager.isSupportedPlatform()==true) {
					capabilitySafari = DesiredCapabilities.safari();
					SafariOptions safariOptions = new SafariOptions();
					capabilitySafari = DesiredCapabilities.safari();
					capabilitySafari.setBrowserName("Safari");
					capabilitySafari.setPlatform(org.openqa.selenium.Platform.ANY);
					capabilityChrome.setCapability(SafariOptions.CAPABILITY, safariOptions);
				}				
			}
		}

		else  if(browser.equalsIgnoreCase("iosMobileDriver")) {
			if (iosMobileDriver == null){
				capabilityiosMobile =new DesiredCapabilities();
				capabilityiosMobile = DesiredCapabilities.iphone();
				//TODO replace app key 
				capabilityiosMobile.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
				capabilityiosMobile.setCapability("platformName", "Windows");
				capabilityiosMobile.setCapability("deviceName", "WindowsPC");

			}    
		}

		else {
			LOGGER.debug("Browser type unsupported");
			System.out.println("Browser type unsupported");
			throw new IllegalArgumentException("Browser type unsupported");//super for IllegalArgumentException
		}
		LOGGER.info (WebDriverManager.class.getName()+ " Setting up driver instance  "+browser);
	}

	public void destroyWebDriverInstances(String browser)

	{
		LOGGER.info(WebDriverManager.class.getName()+ " Attempting to  destroy WebDriver instances.");
		browser.toLowerCase();
		switch(browser){

		case "firefox":
			if (firefoxDriver!=null){
				firefoxDriver.quit();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully destroyed Firefox instances.");
				System.out.println(WebDriverManager.class.getName()+ " Successfully destroyed Firefox instances.");


			}
			break;

		case "chrome":
			if (chromeDriver!=null){
				chromeDriver.quit();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully destroyed Chrome instances.");
				System.out.println(WebDriverManager.class.getName()+ " Successfully destroyed Chrome instances.");

			}
			break;

		case "ie": 
			if (ieDriver!=null){
				ieDriver.quit();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully destroyed IE instances.");
				System.out.println(WebDriverManager.class.getName()+ " Successfully destroyed IE instances.");


			}
			break;

		case "safari":
			if (safariDriver!=null){
				safariDriver.quit();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully destroyed Safari instances.");
				System.out.println(WebDriverManager.class.getName()+ " Successfully destroyed Safari instances.");


			}
			break;
		case "iosMobileDriver":
			if (iosMobileDriver!=null){
				iosMobileDriver.quit();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully destroyed iosMobileDriver instances.");
				System.out.println(WebDriverManager.class.getName()+ " Successfully destroyed iosMobileDriver instances.");


			}
			break;

		}

	}

	public void closeDriverWindows(String browser){
		browser.toLowerCase();
		switch (browser){

		case "firefox":
			if (firefoxDriver!=null){
				firefoxDriver.close();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully closed  Firefox.");
			}
			break;

		case "chrome":
			if (chromeDriver!=null){
				chromeDriver.close();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully closed  Chrome.");

			}
			break;

		case "ie":
			if (ieDriver!=null){
				ieDriver.close();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully closed  IE.");

			}
			break;

		case "safari":
			if (safariDriver!=null){
				safariDriver.close();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully closed  Safari.");
			}
			break;
			
		case "iosMobileDriver":
			if (iosMobileDriver!=null){
				iosMobileDriver.close();
				LOGGER.info(WebDriverManager.class.getName()+ " Successfully closed  iosMobileDriver instances.");

			}
			break;
		}

	}

	public static void capabilitiesInfo(WebDriver driver){
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName().toUpperCase();
		String browserVersion = caps.getVersion();
		Reporter.log("Browser name: "+browserName+" browser version: "+browserVersion);
		System.out.println("Browser name: "+browserName+" browser version:  "+browserVersion);
	}
	
	// this method is called inside of getDriver() 
	public  static String  browserInfo(WebDriver driver)
	{
		String browserInfo = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		System.out.println("Browser info: "+browserInfo);
		//TestNG logger 
		Reporter.log("Browser info: "+browserInfo);
		//log4j logger 
		LOGGER.info(WebDriverManager.class.getName()+" browser info: "+browserInfo);


		return browserInfo;
	}
	
	private static boolean isSupportedPlatform() {
		Platform current = Platform.getCurrent();
		boolean isSafariSupported =false ; 

		if (Platform.MAC.is(current) ){
			isSafariSupported=true;
		}
		else {
			isSafariSupported =false; 
		}
		LOGGER.info("Safari browser: is supported platform "+ isSafariSupported );
		return isSafariSupported;

	}

	//wrapper
	public static void quitDriver(WebDriver driver)
	{
		driver.quit();
	}

	public static  void defaultWindowSize(WebDriver driver)
	{
		driver.manage().window().maximize(); //this would work only for Firefox and IE

	}

}

class TestWebDriverManager{

	public static void main (String args[]){
		WebDriverManager DRIVER_MANAGER =  WebDriverManager.getInstance(); //initializing all 4 drivers

		WebDriver firefoxDriver = DRIVER_MANAGER.getDriver("firefox");
		firefoxDriver.get("https://www.ifonly.com/");
		DRIVER_MANAGER.destroyWebDriverInstances("firefox");

		/*
		WebDriver chromeDriver = driverManager.getDriver("chrome");
		chromeDriver.get("https://www.ifonly.com/");

		WebDriver ieDriver = driverManager.getDriver("ie");//
		ieDriver.get("https://www.ifonly.com/");


		 */

	}

}
