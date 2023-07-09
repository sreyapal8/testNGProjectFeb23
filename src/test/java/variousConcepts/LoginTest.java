
package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest {
	
	String browser;
	String url;
	WebDriver driver;
	
	
	@BeforeSuite
	public void readCongfig() {
		//FileReader  //BufferReader   //InputStream   //Scanner
		
		try {
			InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used:" + browser );
			url = prop.getProperty("url");
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
	}
	
	By USER_NAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGN_IN_BUTTON_FIELD = By.xpath("//button[@name='login']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//h2[text()=' Dashboard ']");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(), 'Customers')]");
	By ADD_CUSTOMER_MENU_FIELD = By.xpath("//a[contains(text(), 'Add Customer')]");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
	
	//test data or mock data
	String user_Name = "demo@techfios.com";
	String password = "abc123";
	String Dashboard_Header_Text = "Dashboard";
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge"))
		{
			System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		else 
		{
			System.out.println("select proper browser");
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	@Test
	public void login() {
		driver.findElement(USER_NAME_FIELD).sendKeys(user_Name);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGN_IN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), Dashboard_Header_Text, "Dasboard Page Not Found");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
