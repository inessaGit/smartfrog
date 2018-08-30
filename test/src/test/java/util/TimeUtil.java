package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;


public class TimeUtil 
{
	private static SimpleDateFormat dateFormatter;
	private static SimpleDateFormat timeFormatter;
	private static final Logger LOGGER = Logger.getLogger(TimeUtil.class);

	
	static
	{
		Calendar calendar = Calendar.getInstance();
		LOGGER.info(TimeUtil.class.getName());
		dateFormatter=	new SimpleDateFormat("yyyy-MM-dd");
		timeFormatter=new SimpleDateFormat("HH_mm_ss_SSS");

	}
	//2015-Jan-07 SimpleDateFormat("yyyy-MMM-dd");
	public static String getCurrentDate() {

		Date date =new Date();//should not be static initialized!
		//String dateNow = dateFormatter.format(calendar.getTime());
		String dateNow = dateFormatter.format(date);
		return dateNow;
	}

	public static String getCurrentTime() {

		Date date = new Date();
		String timeNow = timeFormatter.format(date);
		return timeNow;
	}


	public static String  getTimeStamp( )
	{
		Date date = new Date();
		Timestamp timestamp= new Timestamp(date.getTime());
		System.out.println("Getting timestamp @" +timestamp);
		return timestamp.toString();
	}
}
class TestTimeUtil
{
	public static void main (String []args)
	{
		System.out.println("TimeUtil.getTimeStamp()="+TimeUtil.getTimeStamp());
		System.out.println("TimeUtil.CurrentDate()"+TimeUtil.getCurrentDate());
		System.out.println("TimeUtil.CurrentTime()"+TimeUtil.getCurrentTime());
		System.out.println(TimeUtil.getCurrentDate()+"_"+TimeUtil.getCurrentTime());

	}
}