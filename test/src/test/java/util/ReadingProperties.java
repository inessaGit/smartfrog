package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/*this is a class for reading from properties files; 
*/
public class ReadingProperties {

	private static final Logger LOGGER = Logger.getLogger(ReadingProperties.class);
	private static ReadingProperties readProperties=null;
	private static Properties propertyCONFIG = null;
	private String pathToConfig="/src/test/java/config/config.properties";

	private String value;
	private String key;
	
	// **************************************************************

	public static ReadingProperties getInstance(){

		if (readProperties==null){
			LOGGER.info(ReadingProperties.class.getName()+ " object does not exist. Creating object");
			readProperties=new ReadingProperties();

		}
		else {
			LOGGER.info(ReadingProperties.class.getName()+ " ReadingProperties object already exist");

		}
		return readProperties;
		
	}
	
	//private constructor 
	private ReadingProperties()
	{
		LOGGER.info(ReadingProperties.class.getName()+ " in constructor.");
		loadConfigProperties();
	}
	
	private final boolean loadConfigProperties()
	{
		propertyCONFIG = new Properties(); 
		boolean success=false;
		FileInputStream fis = null ;
		try
		{			
			fis= new FileInputStream(System.getProperty("user.dir") +pathToConfig );
			propertyCONFIG.load(fis);
			success=true;			
			LOGGER.info(ReadingProperties.class.getName()+ " in method loadConfigProperties");

		}
		
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e);
	}
		finally
		{
			if (fis!=null)
			{
				try {
					fis.close();
				} catch (IOException e) {
					LOGGER.debug(e);
					e.printStackTrace();
				}
			}
		}
	return success;
}


// *****************************************************************************
public String readConfigProperties(String key) {
	
	
	if (propertyCONFIG.getProperty(key) != null)// check if key exists in config.properties file
	{
		value = propertyCONFIG.getProperty(key);
		LOGGER.info("Successfully read from config.properties file <"+ key + "> "+ value);

	}
	else {
		value = propertyCONFIG.getProperty(key);
		LOGGER.debug("Could not read specified key from the config.properties file <"+ key + "> "+value);
	}
	return value;
}

// *****************************************************************************

}

class TestReadingProperties {
	 
public static void main(String args[]) {
	ReadingProperties rp = ReadingProperties.getInstance();
	ReadingProperties rp2 = ReadingProperties.getInstance();

	ReadingProperties rp3 = ReadingProperties.getInstance();

	String keyDoesNotExistValue = rp.readConfigProperties("key does not exist");
	System.out.println("case key NULL "  + keyDoesNotExistValue);

	String keyExistValue = rp.readConfigProperties("config.properties.path");
	System.out.println("case key NOT NULL "+ keyExistValue);


}
}