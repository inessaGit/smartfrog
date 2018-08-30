package test.example;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BaseTestNg {
	
	public BaseTestNg(){
		System.out.println("Inside of constructor for BaseTestNg");
	}
	
	@BeforeSuite()
	public static void beforeSuite(){
		System.out.println("Inside of @BeforeSuite in  BaseTestNg");
	}

	//@BeforeClass: The annotated method will be run before the first test method in the current class is invoked. 
	@BeforeClass()
	public static void beforeClass(){
		System.out.println("Inside of @BeforeClass in  BaseTestNg");
	}

	/*
	 * @BeforeTest: The annotated method will be run only when running via suite.xml
	 * Should not be placed directly inside of Test classes. 
	 * Functionality: runs before any @Test test method belonging to the classes inside the <test> tag in suite.xml is run. 
	 *  
	 *  <suite>
          <test name="Basic TestNG Suite example">
		     <classes>
			     <class name= "ifonly_automation.example.BaseTestNg"/>
			 </classes>
		   </test>
		   
		   <test name="Basic TestNG Suite example">
		     <classes>
			     <class name= "ifonly_automation.example.TestNg1"/>
			 </classes>
		   </test>
		</suite>

	 */
	@BeforeTest
	public static void beforeTest(){
		System.out.println("Inside of @BeforeTest in BaseTestNg");
	}
	
	
	@AfterTest
	public static void insideOfAfterTest(){
		System.out.println("Inside of @AfterTest in BaseTestNg");

	}

	@AfterClass()
	public static void afterClass(){
		System.out.println("Inside of @AfterClass in  BaseTestNg");
	}

	@AfterSuite()
	public static void afterSuite(){
		System.out.println("Inside of @AfterSuite in  BaseTestNg");
	}
	
	@AfterSuite()
	public static void afterSuite2(){
		System.out.println("Inside of @AfterSuite  2nd time in  BaseTestNg");
	}
	
}
