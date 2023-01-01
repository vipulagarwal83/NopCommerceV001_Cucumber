package stepDefinations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException
	{
		
		logger=Logger.getLogger("nopCommerce"); // added logger
		PropertyConfigurator.configure("log4j.properties"); // added logger
		
		// Reading properties
		configProp=new Properties();
		FileInputStream configPropfile = new FileInputStream("config.properties");
		configProp.load(configPropfile);
		
		String br=configProp.getProperty("browser");
		
		if(br.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath"));
			driver=new ChromeDriver();	
		}
		else if(br.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath"));
			driver=new FirefoxDriver();	
		}
		else if(br.equals("ie"))
		{
			System.setProperty("webdriver.ie.driver",configProp.getProperty("iepath"));
			driver=new InternetExplorerDriver();	
		}
		
		logger.info("**************Launching browser**************");
		
	}
		
	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {
		
		lp=new LoginPage(driver);
	    
	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		logger.info("****************Opening URL***************");
		driver.get(url);	
		driver.manage().window().maximize();
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String password) {
		logger.info("****************Providing Login Details***************");
		lp.setUserName(email);
		lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_login() throws InterruptedException {
		logger.info("***********************Started Login Process*******************");
		lp.clickLogin();
		Thread.sleep(3000);
	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String title) {
		
		if(driver.getPageSource().contains("Login was unsuccessful.")) {
			driver.close();
			logger.info("*****************Login Passed*******************");
			Assert.assertTrue(false);
			
		}else {
			logger.info("*****************Login Failed*******************");
			Assert.assertEquals(title, driver.getTitle());
		}
	    
	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() throws InterruptedException {
		logger.info("*****************Click on logout link*******************");
		lp.clickLogout();
		Thread.sleep(3000);
	}

	@Then("close browser")
	public void close_browser() {
		logger.info("*****************Closing Browser*******************");
	   driver.quit();
	}

	
	// Customers feature step definitions...
	
	@Then("User can view Dashboad")
	public void user_can_view_dashboad() {
		
	    addCust=new AddCustomerPage(driver);
	    Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_menu() throws InterruptedException {
	    Thread.sleep(3000);
	    addCust.clickOnCustomersMenu();
	}
	
	@When("click on customers Menu Item")
	public void click_on_customers_Menu_Item() throws InterruptedException {
	    Thread.sleep(2000);
	    addCust.clickOnCustomersMenuItem();
	}

	@When("click on Add new button")
	public void click_on_add_new_button() throws InterruptedException {
	    addCust.clickOnAddnew();
	    Thread.sleep(2000);
	}

	@Then("User can view Add new customer page")
	public void user_can_view_add_new_customer_page() {
	    Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
		logger.info("*****************Adding new customer*******************");
		logger.info("*****************Providing customer details*******************");
	    String email=randomestring()+"@gmail.com";
	    addCust.setEmail(email);
	    addCust.setPassword("test123");
	    
	    // Registered - default
	    // The Customer cannot be in both "Guests" and "Registered" customers role
	    // Add the customer to Guests or Registered customer role
	    
	    addCust.setCustomerRoles("Guests");
	    Thread.sleep(3000);
	    
	    addCust.setManagerOfVendor("Vendor 2");
	    addCust.setGender("Male");
	    addCust.setFirstName("Vipul");
	    addCust.setLastName("Garg");
	    addCust.setDob("7/05/1991");
	    addCust.setCompanyName("busyQA");
	    addCust.setAdminContent("This is a testing.....");
	}

	@When("click on Save button")
	public void click_on_save_button() throws InterruptedException {
		logger.info("*****************Saving customer data*******************");
	    addCust.clickOnSave();
	    Thread.sleep(2000);
	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String msg) {
	    Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
	    		.contains("The new customer has been added successfully"));
	}

	
	//steps from searching a customer using Email ID.......................
	
	@When("Enter customer Email")
	public void enter_customer_Email()  {
		logger.info("*****************Searching customer by email id*******************");
		searchCust=new SearchCustomerPage(driver);
		searchCust.SetEmail("victoria_victoria@nopCommerce.com");
	    
	}
	
	@When("Click on search button")
	public void Click_on_search_button() throws InterruptedException {
	    
		searchCust.clickSearch();
	    Thread.sleep(3000);
	}
	@Then("User should found Email in the Search table")
	public void User_should_found_Email_in_the_Search_table() {
	    
	   boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
	   Assert.assertEquals(true, status);
	}

	//steps from searching a customer by using First Name & Last Name.......................
	
	@When("Enter customer FirstName")
	public void enter_customer_first_name() {
		logger.info("*****************Searching customer by Name*******************");
		searchCust=new SearchCustomerPage(driver);
		searchCust.setFirstName("Victoria");
	   
	}

	@When("Enter customer LastName")
	public void enter_customer_last_name() {
	    
		searchCust.setLastName("Terces");
	}
	
	@Then("User should found Name in the Search table")
	public void user_should_found_name_in_the_search_table() {
	    boolean status=searchCust.searchCustomerByName("Victoria Terces");
	    Assert.assertEquals(true, status);
	}

}
