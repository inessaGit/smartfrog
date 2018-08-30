package pages;

import pages.BasePage;
import util.Constants;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


/*
 * this class represents a LoadableComponent that loads the BasePage page
 */
public class BasePage extends LoadableComponent<BasePage> {

	private static final Logger LOGGER = Logger.getLogger(BasePage.class);
	public  final  Constants CONSTANTS ;
	public  final String  base_url_test; 
	public  final String  base_url_dev; 

	private final WebDriver driver;

	/*
	 * 1) inherited by all PageObject classes that extend BasePage class
	 * 
	 */
	

	public BasePage(WebDriver driver){
        this.driver = driver;
        
        CONSTANTS = Constants.getInstance();
        base_url_test= CONSTANTS.getTest_env();
        base_url_dev= CONSTANTS.getDev_env();
        
		PageFactory.initElements(this.driver, this);
		LOGGER.info(this.getClass().getName()+" constructor");;
	}	
	
	@Override
	public void load(){
	    driver.get(base_url_test);
	}
	
	@Override
	public void isLoaded(){
		Assert.assertTrue(this.driver.getCurrentUrl().equalsIgnoreCase(base_url_test));
	}
	

}
