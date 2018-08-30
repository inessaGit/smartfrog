package util;

import org.apache.log4j.Logger;

/**
 * @author Inessa
 * 2/12
 * Class with getters for constant values. Values are read from properties file. 
 */
public  class Constants {
	private static Logger LOGGER =Logger.getLogger(Constants.class);
	private static  Constants CONSTANTS = null;

	private final ReadingProperties rp= null;
	
	//screenshot path 
	private final  String failPath; 
	private final  String passPath;
	private final  String usergenPath;	
	private final String logsPackagePath; 
	

	private final  String test_excelPath;

	////environments path 
	private  final  String test_env;
	private  final  String dev_env;
	private  final   String content_env;
	private  final  String production_env; 
	
	//browsers path 
	private final String iePathWin;
	private final String chromePathWin;
	private final String chromePathMac;
	private final String geckoPathWin ;
	
	private  final String submittedOrdersPath;
	private  final String createdAccountsPath;
	
	private final String testUser;
	private  final String testUserPassword;
	
	
	public  static Constants getInstance() {
	      if(CONSTANTS == null) {
	 		LOGGER.info(Constants.class.getName()+ " Constants object does not exist. Creating object");
	        CONSTANTS = new Constants();

	      }
	      else {
	  		LOGGER.info(Constants.class.getName()+ " Constants object already exist");
	      }
	      return CONSTANTS;
	   }
	
	//private constructor +init constants  can assign a value to a final variable only one time.
	private Constants()
	
	{
		LOGGER.info(Constants.class.getName()+ " in constructor");
		ReadingProperties rp =ReadingProperties.getInstance();
		
		failPath=rp.readConfigProperties("fail.screenshot.path");
		passPath=rp.readConfigProperties("pass.screenshot.path");
		usergenPath=rp.readConfigProperties("usergen.screenshot.path");
		logsPackagePath=rp.readConfigProperties("log.package.path");

		test_excelPath = rp.readConfigProperties("test.excel.path");
		
		
		test_env = rp.readConfigProperties("test.env");
		dev_env = rp.readConfigProperties("dev.env");
		content_env = rp.readConfigProperties("content.env");
		production_env = rp.readConfigProperties("production.env"); 
		
		testUser=rp.readConfigProperties("testuser.username");
	    testUserPassword = rp.readConfigProperties("testuser.password");
	    
		submittedOrdersPath=rp.readConfigProperties("submittedorders.path");
		createdAccountsPath = rp.readConfigProperties("createdaccounts.path");
		
		iePathWin = rp.readConfigProperties("ie.path.win");
		chromePathWin = rp.readConfigProperties("chrome.path.win");
		chromePathMac = rp.readConfigProperties("chrome.path.mac");
		geckoPathWin = rp.readConfigProperties("gecko.path.win");
	}

	public String getLogsPackagePath() {
		return logsPackagePath;
	}
	
	public  String getTest_excelPath() {
		return test_excelPath;
	}

	public String getIePathWin() {
		return iePathWin;
	}

	public String getGeckoPathWin() {
		return geckoPathWin;
	}
	
	public String getChromePathWin() {
		return chromePathWin;
	}
	public String getChromePathMac() {
		return chromePathMac;
	}
	
	public ReadingProperties getRp() {
		return rp;
	}

	public String getFailPath() {
		return failPath;
	}

	public String getPassPath() {
		return passPath;
	}

	public String getUsergenPath() {
		//TODOCommonMethods
		return usergenPath;
	}

	public String getTestUser() {
		return testUser;
	}

	public String getTestUserPassword() {
		return testUserPassword;
	}

	public String getTest_env() {
		return test_env;
	}

	public String getDev_env() {
		return dev_env;
	}

	public String getContent_env() {
		return content_env;
	}

	public String getProduction_env() {
		return production_env;
	}

	public String getSubmittedOrdersPath() {
		return submittedOrdersPath;
	}

	public String getCreatedAccountsPath() {
		return createdAccountsPath;
	}
	
}

class TestConstants

{
	
	public static void main (String args[]){
		
			Constants constants = Constants.getInstance();
			constants = null;
			constants = Constants.getInstance();
			Constants.getInstance();
			Constants.getInstance();
		
	}
}
