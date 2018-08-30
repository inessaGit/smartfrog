package test.example;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class LocalCrossBrowserTest {

	@Test
	public void testFF() {
		
		final String firefoxPath=System.getProperty("user.dir")+ "/src/test/java/config/geckodriver";

		System.setProperty("webdriver.gecko.driver", firefoxPath);	

		String urlDev ="https://www.sf-dev1.com/de-de/";
		String urlLive = "https://www.smartfrog.com/en-gb/";
		
		
		  WebDriver driver = new FirefoxDriver();
		  
		  
		    driver.get(urlLive);
		    WebElement email = driver.findElement(By.name("user"));

		    email.sendKeys("inessa.de@yopmail.com");
		    
		    WebElement password = driver.findElement(By.name("pass"));
		    password.sendKeys("nautica1");

		    WebElement loginBtn = driver.findElement(By.cssSelector("input.submit"));
		    loginBtn.click();
		     
		    WebDriverWait wait = new WebDriverWait(driver, 10);
		    //WebElement logout = driver.findElement(By.xpath("//span[contains(text(),'Logout')]"));
		    
		    wait.until(ExpectedConditions.urlContains("https://app.smartfrog.com/en-de/"));
		    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Logout')]"))));
		    
		    System.out.println(driver.getTitle());
		    driver.quit();
	}
	

	@Test
	public void testChrome() {
		
		final String chromePath=System.getProperty("user.dir")+ "/src/test/java/config/chromedriver2.38";

		System.setProperty("webdriver.chrome.driver", chromePath);	

		String urlDev ="https://www.sf-dev1.com/de-de/";
		String urlLive = "https://www.smartfrog.com/en-gb/";
		
		  
	     WebDriver driver = new ChromeDriver(); 
		  
		    driver.get(urlLive);
		    WebElement email = driver.findElement(By.name("user"));

		    email.sendKeys("inessa.de@yopmail.com");
		    
		    WebElement password = driver.findElement(By.name("pass"));
		    password.sendKeys("nautica1");

		    WebElement loginBtn = driver.findElement(By.cssSelector("input.submit"));
		    loginBtn.click();
		     
		    WebDriverWait wait = new WebDriverWait(driver, 10);
		    //WebElement logout = driver.findElement(By.xpath("//span[contains(text(),'Logout')]"));
		    
		    wait.until(ExpectedConditions.urlContains("https://app.smartfrog.com/en-de/"));
		    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Logout')]"))));
		    
		    System.out.println(driver.getTitle());
		    driver.quit();
	}
	
	@Test
	public void testBrowserStackChrome() throws MalformedURLException {
		
		  final String USERNAME = "qasmartfrog1";
		  final String AUTOMATE_KEY = "wCy13SARjpRPZyqBoxBu";
		  final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("browser", "Chrome");
		    caps.setCapability("browser_version", "62.0");
		    caps.setCapability("os", "Windows");
		    caps.setCapability("os_version", "10");
		    caps.setCapability("resolution", "1024x768");

		    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
		    
		    String urlDev ="https://www.sf-dev1.com/de-de/";
			String urlLive = "https://www.smartfrog.com/en-gb/";
			
		    driver.get(urlLive);
		    WebElement email = driver.findElement(By.name("user"));
		    email.sendKeys("inessa.de@yopmail.com");
		    
		    WebElement password = driver.findElement(By.name("pass"));
		    password.sendKeys("nautica1");

		    WebElement loginBtn = driver.findElement(By.cssSelector("input.submit"));
		    loginBtn.click();
		     
		    WebDriverWait wait = new WebDriverWait(driver, 10);
		    //WebElement logout = driver.findElement(By.xpath("//span[contains(text(),'Logout')]"));
		    
		    wait.until(ExpectedConditions.urlContains("https://app.smartfrog.com/en-de/"));
		    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Logout')]"))));
		    
		    System.out.println(driver.getTitle());
		    driver.quit();

	}
	@Test
	public void testBrowserStackIE11() throws MalformedURLException {
		
		  final String USERNAME = "qasmartfrog1";
		  final String AUTOMATE_KEY = "wCy13SARjpRPZyqBoxBu";
		  final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("browser", "IE");
		    caps.setCapability("browser_version", "11.0");
		    caps.setCapability("os", "Windows");

		    caps.setCapability("os_version", "10");
		    caps.setCapability("resolution", "1024x768");

		    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
		    
		    String urlDev ="https://www.sf-dev1.com/de-de/";
			String urlLive = "https://www.smartfrog.com/en-gb/";
			
		    driver.get(urlLive);
		    WebElement email = driver.findElement(By.name("user"));
		    email.sendKeys("inessa.de@yopmail.com");
		    
		    WebElement password = driver.findElement(By.name("pass"));
		    password.sendKeys("nautica1");

		    WebElement loginBtn = driver.findElement(By.cssSelector("input.submit"));
		    loginBtn.click();
		     
		    WebDriverWait wait = new WebDriverWait(driver, 10);
		    //WebElement logout = driver.findElement(By.xpath("//span[contains(text(),'Logout')]"));
		    
		    wait.until(ExpectedConditions.urlContains("https://app.smartfrog.com/en-de/"));
		    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Logout')]"))));
		    
		    System.out.println(driver.getTitle());
		    driver.quit();

	}
	/*
	 * RemoteWebDriver: This driver class comes directly from the upstream Selenium project. 
	 * This is a pretty generic driver where initializing the driver means making network requests to a Selenium hub to start a driver session. 
	 * Since Appium operates on the client-server model, Appium uses this to initialize a driver session. 
	 * However, directly using the RemoteWebDriver is not recommended since there are other drivers available that offer additional features or convenience functions.

AppiumDriver: This driver class inherits from the RemoteWebDriver class, but it adds in additional functions that are useful in the context of a mobile automation test through the Appium server.
AndroidDriver: This driver class inherits from AppiumDriver, but it adds in additional functions that are useful in the context of a mobile automation test on Android devices through Appium. Only use this driver class if you want to start a test on an Android device or Android emulator.
IOSDriver: This driver class inherits from AppiumDriver, but it adds in additional functions that are useful in the context of a mobile automation test on iOS devices through Appium. Only use this driver class if you want to start a test on an iOS device or iOS emulator.
	 */
	@Test
	public void testBrowserStackIEEdge() throws MalformedURLException {
		
		  final String USERNAME = "qasmartfrog1";
		  final String AUTOMATE_KEY = "wCy13SARjpRPZyqBoxBu";
		  final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		    DesiredCapabilities caps = new DesiredCapabilities();
		    caps.setCapability("browser", "Edge");
		    caps.setCapability("browser_version", "16.0");
		    caps.setCapability("os", "Windows");
		    caps.setCapability("os_version", "10");
		    
		    caps.setCapability("resolution", "1024x768");

		    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
		    
		    String urlDev ="https://www.sf-dev1.com/de-de/";
			String urlLive = "https://www.smartfrog.com/en-gb/";
			
		    driver.get(urlLive);
		    WebElement email = driver.findElement(By.name("user"));
		    email.sendKeys("inessa.de@yopmail.com");
		    
		    WebElement password = driver.findElement(By.name("pass"));
		    password.sendKeys("nautica1");

		    WebElement loginBtn = driver.findElement(By.cssSelector("input.submit"));
		    loginBtn.click();
		     
		    WebDriverWait wait = new WebDriverWait(driver, 10);
		    //WebElement logout = driver.findElement(By.xpath("//span[contains(text(),'Logout')]"));
		    
		    wait.until(ExpectedConditions.urlContains("https://app.smartfrog.com/en-de/"));
		    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'Logout')]"))));
		    
		    System.out.println(driver.getTitle());
		    driver.quit();

	}
}
