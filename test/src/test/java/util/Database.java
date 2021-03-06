package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import com.mysql.jdbc.CommunicationsException;

/*
 *
 *PreparedStatement object
boolean execute() - Executes the SQL statement (which may be any kind of SQL statement) in this PreparedStatement object.
ResultSet executeQuery() -> Executes the SQL query in this PreparedStatement object and returns the ResultSet object generated by the query.
int executeUpdate()-> Executes the SQL statement(must be SQL INSERT, UPDATE or DELETE statement) in this PreparedStatement object;
 */

/*9/15 dbUnit 
 * IDataSet interface represents one or more blocks of tabular data. This data may be generated from:

flat XML files
tables in the database
database queries created using SQL
 Excel spreadsheets
 */
public class Database {

	private static Logger LOGGER=Logger.getLogger(Database.class);

	//test server 1
	private 	static final String test_iocontentdb="jdbc:mysql://10.88.0.52:3306/iocontentdb?autoReconnect=true";	
	//testdb server 2
	private 	static final String testdb_iocontentdb="jdbc:mysql://10.88.0.54:3306/iocontentdb?autoReconnect=true";
	private	static final String testdb_ioserverdb="jdbc:mysql://10.88.0.54:3306/ioserverdb?autoReconnect=true&zeroDateTimeBehavior=convertToNull";

	private static final  String dbUser= "your user";
	private static final String dbPswd= "your password";

	/**
	 * @return the testIocontentdb
	 */
	public static String getTestIocontentdb() {
		return test_iocontentdb;
	}

	/**
	 * @return the testdbIocontentdb
	 */
	public static String getTestdbIocontentdb() {
		return testdb_iocontentdb;
	}
	/**
	 * @return the testdbIoserverdb
	 */
	public static String getTestdbIoserverdb() {
		return testdb_ioserverdb;
	}
	
	/**
	 * @return the dbuser
	 */
	public static String getDbuser() {
		return dbUser;
	}
	/**
	 * @return the dbpswd
	 */
	public static String getDbpswd() {
		return dbPswd;
	}

	//dbUnit:  IDatabaseConnection object, which wraps a normal JDBC connection  
	public static IDatabaseConnection getConnection(String dbName,String dbUser, String dbPswd) throws Exception{
		Connection jdbcConnection =null;
		try 
		{
			Class driverClass = Class.forName("com.mysql.jdbc.Driver");
			jdbcConnection=  DriverManager.getConnection(dbName, dbUser, dbPswd);

		}

		catch (CommunicationsException e)
		{
			LOGGER.debug(e);
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			LOGGER.debug(e);
			e.printStackTrace();
		}

		return new DatabaseConnection(jdbcConnection);
	}

	//dbunit: method returns a data set loaded from an XML file on the classpath.
	protected IDataSet getDataSet() throws Exception
	{
		IDataSet loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("input.xml"));
		return loadedDataSet;
	}
	//reg java
	public static Connection connectToDB(String dbName, String dbUser, String dbPswd) 
	{
		Connection connection=null;
		try
		{   
		System.out.println("Trying connect to "+dbName);
		LOGGER.info("Trying connect to "+dbName);
		connection=DriverManager.getConnection(dbName, dbUser, dbPswd);
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		System.out.println("Successfully connected to "+dbName);
		LOGGER.info("Successfully connected to "+dbName);

		}

		catch (Exception e)
		{
			System.out.println("Exception while connecting to dB.");
			LOGGER.debug("Exception while connecting to dB.");
			LOGGER.debug(e);
			e.printStackTrace();
		}
		return connection;
	}


	//returns resultset: the caller of this method should close ResultSet
	public 	static ResultSet getTestDataAsResultSet(Connection connection, String sqlQuery) {

		PreparedStatement preStatement = null;		
		ResultSet resultset = null;
		try
		{			
			preStatement=connection.prepareStatement(sqlQuery);//creates preparedStatement object
			resultset= preStatement.executeQuery();
			ResultSetMetaData rsMetaData = resultset.getMetaData();

			int cols = rsMetaData.getColumnCount();
			System.out.println("Number of columns in ResultSet: "+cols);	

			 while (resultset.next()) {
			       for (int i = 1; i <= cols; i++) {
			           String columnValue = resultset.getString(i);
			           System.out.println(rsMetaData.getColumnName(i)+" "+ columnValue );
			       }
			   }
		}
			
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.debug(e);
		}

		finally {
			if (resultset != null) {
				try {
					resultset.close(); //
				} catch (SQLException e) {  }
			}
			if (preStatement != null) {
				try {
					preStatement.close();
				} catch (SQLException e) { }
			}
		}

		return resultset;
	}
	//returns ArrayList 
	public 	static ArrayList<String> getTestData(Connection connection, String sqlQuery)  {

		PreparedStatement preStatement = null;		
		ResultSet resultset = null;
		ArrayList<String> arrayList = new ArrayList<String>(); 

		try
		{			
			preStatement=connection.prepareStatement(sqlQuery);//creates preparedStatement object
			resultset= preStatement.executeQuery();
			ResultSetMetaData rsMetaData = resultset.getMetaData();

			int cols = rsMetaData.getColumnCount();
			System.out.println("Number of columns in ResultSet: "+cols);	

			 while (resultset.next()) {
			       for (int i = 1; i <= cols; i++) {
			           String columnValue = resultset.getString(i);
			           System.out.println(rsMetaData.getColumnName(i)+" "+ columnValue );
						arrayList.add(columnValue);
			       }
			   }
		}
			
		catch (Exception e)
		{
			e.printStackTrace();
			LOGGER.debug(e);
		}

		finally {
			if (resultset != null) {
				try {
					resultset.close(); //
				} catch (SQLException e) {  }
			}
			if (preStatement != null) {
				try {
					preStatement.close();
				} catch (SQLException e) { }
			}
		}

		return arrayList;
	}
	/*
	 * 
Iterate over the ResultSet
Create a new Object for each row, to store the fields you need
Add this new object to ArrayList/Hashmap or whatever you like
Close the ResultSet, Statement.
	 */
	public static List<HashMap<String,Object>> convertResultSetToList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

		while (rs.next()) {
			HashMap<String,Object> row = new HashMap<String, Object>(columns);//capacity=numOfCols
			for(int i=1; i<=columns; ++i) {

				//put columnName as key and column value as value into HashMap<String,Object>
				row.put(md.getColumnName(i),rs.getObject(i));
			}
			list.add(row);//add all of HashMap<String,Object> to the ArrayList. 
		}

		return list;
	}
/*EXAMPLE
 * 
 * 
String sql = "update people set firstname=? , lastname=? where id=?";
PreparedStatement preparedStatement =  connection.prepareStatement(sql);
preparedStatement.setString(1, "Gary");
int rowsAffected = preparedStatement.executeUpdate();
handles INSERT, UPDATE, DELETE
	 */
	public static int  updateTestData(Connection connection, String sqlQuery, String parameter1) throws Exception {

		PreparedStatement preStatement =null;
		int rowsAffected=0;

		try{
			preStatement=connection.prepareStatement(sqlQuery);
			preStatement.setString(1,parameter1);
			rowsAffected=preStatement.executeUpdate(sqlQuery);
			System.out.println(connection+" :done updating data. Affected rows: "+rowsAffected);
		}

		catch (SQLException e) {
			e.printStackTrace();
			LOGGER.debug(e);
		}

		finally {

			if (preStatement != null) {
				try {
					preStatement.close();
				} catch (SQLException e) { /*ignored*/ }
			}
		}

		return rowsAffected;
	}

	//handles INSERT, UPDATE, DELETE
	public static int  updateTestData(Connection connection, String sqlQuery) {

		PreparedStatement preStatement =null;
		int rowsAffected=0;
		try{
			preStatement=connection.prepareStatement(sqlQuery);
			rowsAffected=preStatement.executeUpdate(sqlQuery);
			System.out.println(Database.class.getName()+" updateTestData(). Done updating data. Affected rows:"+ rowsAffected);
			LOGGER.info(Database.class.getName()+" updateTestData(). Done updating data. Affected rows:"+ rowsAffected);
		}

		catch (SQLException e) {
			e.printStackTrace();
			LOGGER.debug(e);
		}

		finally {

			if (preStatement != null) {
				try {
					preStatement.close();
				} catch (SQLException e) { /*ignored*/ }
			}
		}

		return rowsAffected;
	}

	public static void closeConnection(Connection connection)
	{

		if (connection!= null)
		{
			try {
				connection.close();
			} 
			catch (SQLException e) { /*ignored*/ }
		}
	}

	public static void closeResultSet(ResultSet resultset)
	{	
		if (resultset != null) {
			try {
				resultset.close(); //
			} catch (SQLException e) {  }
		}
	}
	

}

class TestDatabase
{
	public static void main (String [] args) throws Exception
	{
		//Step 1: get db credentials 
		String testdb_ioserverdb=Database.getTestdbIoserverdb();
		String dbUser = Database.getDbuser();
		String dbPswd=Database.getDbpswd();

		//Step 2: connect to db and get Connection
		Connection connection = Database.connectToDB(testdb_ioserverdb,dbUser,dbPswd);
		//ArrayList<String> data = Database.getTestData(connection, "SELECT * from ioserverdb.orders WHERE orderId=1431");

		//Step 3: create SQL statement and save it in String 
		String updateEmail =
				"UPDATE ioserverdb.emailaddresses"
						+ " SET emailAddress='inez2209@gmail.comnotvalid'"
						+ " WHERE emailAddress='testinessa2017@gmail.com'\");\r\n";
		//Database.updateTestData(connection, updateEmail);


		//emailAddressId column
		String selectEmails =
				"SELECT* FROM ioserverdb.emailaddresses \r\n" + 
						"WHERE emailAddress LIKE 'testinessa2017@gmail.com%'\r\n" + 
						"OR emailAddress LIKE 'testinessa2017_2@gmail.com%';\r\n";

		String deleteEmail=
				"DELETE FROM ioserverdb.emailaddresses \r\n" + 
						"WHERE emailAddress LIKE 'inessag08@gmail.com%'\r\n" + 
						"OR emailAddress LIKE 'testinessa2017@gmail.com%';";

		//Step 4: SELECT data and get it as ArrayList OR do Step 4A
		List<String> usersTableArr = Database.getTestData(connection, 
				selectEmails);
		System.out.println("=============================");
		System.out.println("Database Step 4: "+usersTableArr.get(0));
		System.out.println("=============================");

		//Step 4A: SELECT data and get it as ResultSet
		ResultSet usersTable = Database.getTestDataAsResultSet(connection, 
				selectEmails);
		//Step 4B: if ResultSet has multiple rows?
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list=Database.convertResultSetToList(usersTable);

		//Step 5: UPDATE data -> run updateTestData which returns num of affected rows
		String email="test126101@yopmail.com";
		String insertEmail="INSERT INTO ioserverdb.emailAddresses (emailAddress, emailOptedIn, siteId)" + 
				"VALUES ('"+email+"', 1, 1)";


		Database.updateTestData(connection, insertEmail);

	}


}

