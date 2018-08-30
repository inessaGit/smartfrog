package test.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import runner.BaseTestSuite;
import util.CommonMethods;
import util.TakeScreenshot;
import util.WebDriverManager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;


/*
 * WebDriver 3.4 requires JDK 8;
 * supports geckodriver 017
 * chromedriver 2.31
 * 
 */
public class CrossBrowserSimpleTest {

// For win:	private final String firefoxPath=System.getProperty("user.dir")+ "/src/test/java/config/geckodriver018.exe";
	
	private final String firefoxPath=System.getProperty("user.dir")+ "/src/test/java/config/geckodriver";

	private final String iePath64=System.getProperty("user.dir")+ "/src/test/java/config/IEDriverServer.exe";
	private final String iePath32=System.getProperty("user.dir")+ "/src/test/java/config/IEDriverServer_Win32_3.4.exe";

    private final String url = "https://www.ifonly.com/";
	 
	public static void getBrowserInfo(WebDriver driver){

		String info = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		System.out.println("Browser info: "+info);
	}

	@Test(invocationCount=1)
	public void testGetFirefoxBrowserInfo(){
		System.setProperty("webdriver.gecko.driver", firefoxPath);	
		WebDriver driver =new FirefoxDriver();
		CrossBrowserSimpleTest.getBrowserInfo(driver);
		driver.get(url);
		driver.quit();//close all firefox windows and quits driver ; geckodriver.exe process gets killed

	}
	
	@Test(invocationCount=1)
	public void testGetFirefoxViaNativeApiForGmail(){	

		String url="https://mail.google.com"  ;
				
		System.setProperty("webdriver.gecko.driver", firefoxPath);	
		Capabilities capabilities  = DesiredCapabilities.firefox();

		System.out.println(capabilities.getBrowserName());
		System.out.println(capabilities.getPlatform());
		System.out.println(capabilities.getVersion());
		System.out.println (capabilities.asMap());

		//Manage firefox specific settings in a way that geckodriver can understand
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addPreference("browser.startup.page", 1);

		WebDriver driver =new FirefoxDriver(firefoxOptions);
		driver.get(url);

	//	driver.close();//closing window quit only if it is last window open
	 	driver.quit();//close all firefox windows and quits driver 

	}
	
	@Test(invocationCount=1)
	public void testGetFirefoxViaNativeApi(){	

		System.setProperty("webdriver.gecko.driver", firefoxPath);	
		Capabilities capabilities  = DesiredCapabilities.firefox();

		System.out.println(capabilities.getBrowserName());
		System.out.println(capabilities.getVersion());
		System.out.println (capabilities.asMap());

		/*
		 * The Profile class may be used to configure the browser profile used with WebDriver, 
		 * with functions to install additional extensions, configure browser preferences;
		 * Ultimately FirefoxProfile and ChromeOptions are just wrappers over DesiredCapabilities 
           in the end, everything is converted down to a dictionary of DesiredCapabilities
		 */
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		
		//Increasing the length of the network.http.phishy-userpass-length preference will cause Firefox to not prompt
		//when navigating to a website with a username or password in the URL
		firefoxProfile.setPreference("network.http.phishy-userpass-length", 255);
		firefoxProfile.setAcceptUntrustedCertificates(true);

		//Manage firefox specific settings in a way that geckodriver can understand
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addPreference("browser.startup.page", 1);

		WebDriver driver =new FirefoxDriver(firefoxOptions);
		driver.get(url);
		
		WebElement logInTopNav = driver.findElement(By.id("logInBtn"));
		logInTopNav.click();
		
		Assert.assertTrue(url.contains("ifonly"));

		//driver.close();//closing window quit only if it is last window open
		driver.quit();//close all firefox windows and quits driver 

	}

	@Test(invocationCount=1)
	public void testGetInternetExplorerViaNativeApi() {
		
		System.setProperty("webdriver.ie.driver", iePath64);	
		WebDriver ieDriver = new InternetExplorerDriver();
		ieDriver.manage().window().maximize();
		ieDriver.get(url);
		Assert.assertTrue(url.contains("ifonly"));
		ieDriver.quit();
	}
	
	@Test
	public void testWebDriverManagerGetInternetExplorer(){

		WebDriverManager DRIVER_MANAGER =WebDriverManager.getInstance();
		WebDriver ieDriver = DRIVER_MANAGER.getDriver("ie");
		
		//ieDriver.get(url);
		//ieDriver.quit();//using native WebDriver API kills iedriver.exe process 
	    //DRIVER_MANAGER.closeDriverWindows("ie"); //using wrapper method leaves iedriver.exe process running on windows
		//DRIVER_MANAGER.destroyWebDriverInstances("ie"); //using wrapper method leaves kills iedriver.exe process 
	}
	@Test
	public void testWebDriverManagerGetFirefox(){

		WebDriverManager DRIVER_MANAGER =WebDriverManager.getInstance();
		WebDriver firefoxDriver = DRIVER_MANAGER.getDriver("firefox");
		firefoxDriver.get(url);
		DRIVER_MANAGER.destroyWebDriverInstances("firefox"); //using wrapper method
	    //DRIVER_MANAGER.closeDriverWindows("firefox"); //using wrapper method leaves firefox.exe process running on windows
	}	
	
	@Test
	//need to have Chrome v56 or higher 
	public void testWebDriverManagerGetChrome(){

		WebDriverManager DRIVER_MANAGER =WebDriverManager.getInstance();
		WebDriver chromeDriver = DRIVER_MANAGER.getDriver("chrome");
		chromeDriver.get(url);
		CommonMethods.pause(1500);
		//chromeDriver.quit();//using native WebDriver API kills chromedriver.exe process 
	    //DRIVER_MANAGER.closeDriverWindows("chrome"); //using wrapper method leaves chromedriver.exe process running on windows
		DRIVER_MANAGER.destroyWebDriverInstances("chrome"); //using wrapper method leaves kills chromedriver.exe process 

	}


	@Test
	public void testBaseTestSuiteGetWebDriverManager(){
		WebDriverManager webdriverManager = BaseTestSuite.getWebDriverManager();
		webdriverManager.getDriver("firefox").get(url);
		BaseTestSuite.destroyWebDrivers();
	}

	
	@Test
	public void testWebDriverManagerGetIosMobileDriver(){

		WebDriverManager DRIVER_MANAGER =WebDriverManager.getInstance();
		WebDriver iosMobileDriver = DRIVER_MANAGER.getDriver("iosMobileDriver");
		iosMobileDriver.get(url);
		DRIVER_MANAGER.destroyWebDriverInstances("iosMobileDriver"); //using wrapper method
	}

	@Test
	public void testGetGeckoDriverService(){
		
		GeckoDriverService.Builder gecko = new GeckoDriverService.Builder();
		String LOG_PATH =System.getProperty("user.dir")+"/logs/gecko.log";
		gecko.withLogFile(new File(LOG_PATH));
		
	}

}