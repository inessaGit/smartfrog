package webapp;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import util.CommonMethods;
import util.TakeScreenshot;
import util.WebDriverManager;

public class HomePageIFA {

	
	private final String firefoxPath=System.getProperty("user.dir")+ "/src/test/java/config/geckodriver";
	private final String iePath64=System.getProperty("user.dir")+ "/src/test/java/config/IEDriverServer.exe";
	private final String iePath32=System.getProperty("user.dir")+ "/src/test/java/config/IEDriverServer_Win32_3.4.exe";

    private final String url = "https://www.smartfrog.com/";
	
	@Test(invocationCount=1)
	public void testGetChromeIFA(){	

		WebDriverManager DRIVER_MANAGER =WebDriverManager.getInstance();		
		//Chrome v68 need chrome driver bindign for v2.41
		WebDriver chromeDriver = DRIVER_MANAGER.getDriver("chrome");
		chromeDriver.manage().window().maximize();
		
		/*
		String home_en_gb = "https://www.sf-test1.com/en-gb/" ;
		String home_de_de="https://www.sf-test1.com/de-de/" ;
		String home_en_ie="https://www.sf-test1.com/en-ie/"  ; 
		String home_de_at="https://www.sf-test1.com/de-at/" ; 
		String home_first_utility="https://www.sf-test1.com/en-gb/first-utility/" ;
	*/	
		TakeScreenshot.getInstance().deleteScreenshotsInReportScreenshot();
		TakeScreenshot.getInstance().deleteScreenshotsInTestOutput();;
		
		String home_en_gb = "https://www.smartfrog.com//en-gb/" ;
		String home_de_de="https://www.smartfrog.com/de-de/" ;
		String home_en_ie="https://www.smartfrog.com/en-ie/"  ; 
		String home_de_at="https://www.smartfrog.com/de-at/" ; 
		String home_first_utility="https://www.smartfrog.com/en-gb/first-utility/" ;
		
		
		chromeDriver.get(home_en_gb);
		JavascriptExecutor jse = (JavascriptExecutor)chromeDriver;
		jse.executeScript("window.scrollBy(0,250)", "");
		
			TakeScreenshot.getInstance().takeScreenshot(chromeDriver,1);
			CommonMethods.pause(1500);

			chromeDriver.get(home_de_de);
			jse.executeScript("window.scrollBy(0,250)", "");		
			TakeScreenshot.getInstance().takeScreenshot(chromeDriver,1);
			CommonMethods.pause(1500);

			chromeDriver.get(home_en_ie);
			jse.executeScript("window.scrollBy(0,250)", "");

			TakeScreenshot.getInstance().takeScreenshot(chromeDriver,1);
			CommonMethods.pause(1500);

			chromeDriver.get(home_de_at);
			jse.executeScript("window.scrollBy(0,250)", "");
			TakeScreenshot.getInstance().takeScreenshot(chromeDriver,1);
			CommonMethods.pause(1500);

			chromeDriver.get(home_first_utility);
			jse.executeScript("window.scrollBy(0,250)", "");
			TakeScreenshot.getInstance().takeScreenshot(chromeDriver,1);
			CommonMethods.pause(1500);

			
		TakeScreenshot.getInstance().copyScreenshotsToTestOutput();

		//driver.close();//closing window quit only if it is last window open
		chromeDriver.quit();//close all firefox windows and quits driver 

	}
	
	@Test(invocationCount=1)
	public void testGetFirefoxIFA(){	

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
	 	
		/*
		String home_en_gb = "https://www.sf-test1.com/en-gb/" ;
		String home_de_de="https://www.sf-test1.com/de-de/" ;
		String home_en_ie="https://www.sf-test1.com/en-ie/"  ; 
		String home_de_at="https://www.sf-test1.com/de-at/" ; 
		String home_first_utility="https://www.sf-test1.com/en-gb/first-utility/" ;
	*/	
		TakeScreenshot.getInstance().deleteScreenshotsInReportScreenshot();
		TakeScreenshot.getInstance().deleteScreenshotsInTestOutput();;
		
		String home_en_gb = "https://www.smartfrog.com//en-gb/" ;
		String home_de_de="https://www.smartfrog.com/de-de/" ;
		String home_en_ie="https://www.smartfrog.com/en-ie/"  ; 
		String home_de_at="https://www.smartfrog.com/de-at/" ; 
		String home_first_utility="https://www.smartfrog.com/en-gb/first-utility/" ;
		
		TakeScreenshot.getInstance().deleteScreenshotsInReportScreenshot();
		TakeScreenshot.getInstance().deleteScreenshotsInTestOutput();;
		
			driver.get(home_en_gb);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);

			driver.get(home_de_de);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);

			driver.get(home_en_ie);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
			driver.get(home_de_at);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
			driver.get(home_first_utility);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
		TakeScreenshot.getInstance().copyScreenshotsToTestOutput();

		//driver.close();//closing window quit only if it is last window open
		driver.quit();//close all firefox windows and quits driver 

	}
	
	@Test(invocationCount=1)
	public void testGetFirefox404ErrorPage(){	

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
		TakeScreenshot.getInstance().deleteScreenshotsInReportScreenshot();
		TakeScreenshot.getInstance().deleteScreenshotsInTestOutput();;
		
		String dev_en_gb = "https://www.sf-dev1.com/en-gb/test" ;
		String dev_de_de="https://www.sf-dev1.com/de-de/test" ;
		String stage_en_gb="https://www.sf-test1.com/en-gb/test"  ; 
		String stage_de_de="https://www.sf-test1.com/de-de/test" ; 
	
		String live_en_gb = "https://www.smartfrog.com//en-gb/test" ;
		String live_de_de="https://www.smartfrog.com/de-de/test" ;
		
			driver.get(live_en_gb);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);

			driver.get(live_de_de);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);

			/*
			driver.get(dev_en_gb);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
			driver.get(dev_de_de);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
			driver.get(stage_en_gb);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			
			driver.get(stage_de_de);
			TakeScreenshot.getInstance().takeScreenshot(driver,1);
			*/
			
		TakeScreenshot.getInstance().copyScreenshotsToTestOutput();

		//driver.close();//closing window quit only if it is last window open
		driver.quit();//close all firefox windows and quits driver 

	}

}
