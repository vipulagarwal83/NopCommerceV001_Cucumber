package stepDefinations;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.Logger;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class BaseClass {

	public WebDriver driver;
	public LoginPage lp;
	public AddCustomerPage addCust;
	public SearchCustomerPage searchCust;
	public static Logger logger;
	public Properties configProp;
	
	public static String randomestring()
	{
		// Created for generating string random for unique email id 
		String generateString1 = RandomStringUtils.randomAlphabetic(5);
		return (generateString1);
	}
}
