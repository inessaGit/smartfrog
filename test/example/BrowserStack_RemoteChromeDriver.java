package test.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class BrowserStack_RemoteChromeDriver {

  public static final String USERNAME = "qasmartfrog1";
  public static final String AUTOMATE_KEY = "wCy13SARjpRPZyqBoxBu";
  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

  public static void main(String[] args) throws Exception {

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
}