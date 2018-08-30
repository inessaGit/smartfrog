package util;

import util.CommonMethods;
import util.ReadingProperties;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;



public class CommonMethods {

	private static Logger LOGGER=Logger.getLogger(CommonMethods.class);
	ReadingProperties rp =ReadingProperties.getInstance();

	public static void loadUrl(WebDriver driver, String url){
		driver.get(url);
		System.out.println("Loading url: "+url);
		Reporter.log("Loading url: "+url);

	}

	   public static WebDriverWait initWait(WebDriver driver, int sec){
		   WebDriverWait wait = new WebDriverWait(driver ,sec);
		return wait;
		   
	    }
	   public static void changeElementDisplayStyle(WebDriver driver, WebElement el)
		 {
			 JavascriptExecutor js = (JavascriptExecutor)driver;
			 String script = "arguments[0].style.display='block';";
			 js.executeScript(script, el);
		 }
		 
		 public static void highlightEl (WebDriver driver,WebElement element)
		 {
			 JavascriptExecutor js = (JavascriptExecutor)driver;
			 String script = "arguments[0].style.backgroundColor='#FFFF00';";
			 js.executeScript(script, element);
		 }
	   public static void  deleteCookie (WebDriver driver, String cookieName)
		{
			try
			{
				Cookie cookie= driver.manage().getCookieNamed(cookieName);  
				String cookieVal=cookie.getValue();
				System.out.println("Cookie value "+cookieVal);
				Reporter.log("Cookie value "+cookieVal);

				driver.manage().deleteCookieNamed(cookieName);
				driver.navigate().refresh();
				if(CommonMethods.isAlertPresent(driver)==true)
				
				System.out.println("Cookie named: "+cookieName +" deleted.");
				LOGGER.info("Cookie named: "+cookieName +" deleted.");
				Reporter.log("Cookie named: "+cookieName +" deleted.");



			}
			catch(Exception e)
			{
				e.printStackTrace();
				LOGGER.debug(e);
			}

		}
	   
	   public static boolean isElementExist(WebDriver driver, WebElement el){
		   
		   boolean isElementExist=false;
		   try{
			  
			   isElementExist= el.isDisplayed();
			   
		   }
		   catch(NoSuchElementException e){
			   System.out.println("Element does not exist.");
			   LOGGER.debug("Element does not exist.");
		   }
		return isElementExist;
		   
	   }
	public static  boolean isAlertPresent(WebDriver driver)  
	{ String alertMsg=null;

	try 
	{ 
		Alert alert = driver.switchTo().alert();
		alertMsg =alert.getText(); 

		alert.accept();
		driver.switchTo().defaultContent();

		//  alert.dismiss();
		//((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){return true;};");
		System.out.println("Alert present: alert message "+ alertMsg);
		Reporter.log("Alert is present");
		LOGGER.info("Alert present" +alertMsg);

		return true; 
	}   
	catch (NoAlertPresentException Ex) 
	{ 

		System.out.println("No alert!");
		return false;
	}

	catch (UnhandledAlertException Ex) 
	{ 
		System.out.println("Modal dialog present: "+ alertMsg);
		return true; 
	}   
	catch (Exception Ex) 
	{ 
		System.out.println(Ex);
		return true; 
	}   
	}
	
	public static boolean onMouseOver(WebDriver driver, By locator)
	{
		boolean result = false;
		try
		{
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript(mouseOverScript, driver.findElement(locator));
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public static void slowType(WebDriver driver,WebElement el, String str)
	{
		
		char chars[]=str.toCharArray();
		for (char s: chars)
		{
			String letter = s + "";
			el.sendKeys(letter);

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static boolean waitToLoadElement(WebDriver driver, By by, int seconds) {
		boolean found = true;

		long bailOutPeriod = 1000 * seconds;
		long lStartTime = new Date().getTime();

		while (!isElementPresent(driver, by)) {
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime;

			if (difference < bailOutPeriod) {
				pause(1);
			}
			else {
				found = false;
				break;
			}
		}
		return found;
	}

}
