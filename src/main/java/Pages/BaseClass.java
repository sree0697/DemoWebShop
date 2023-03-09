package Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	
	static WebDriver driver;
	static String URL = "https://demowebshop.tricentis.com/";

	@BeforeTest
	@Parameters({ "browser", "userEmail", "password" })
	/**Setup Method opens browser ,logins to Application with credentials provided through XML 
	 * 
	 * @param browser : Chrome/FireFox/Edge (Global parameters)
	 * @param userEmail : emailAddress provided by the user
	 * @param password : password to login to Application
	 * **/
	public void setup(String browser, String userEmail, String password) {
		if (browser.equalsIgnoreCase("Chrome")) {
    
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		} else if (browser.equals("Firefox")) {
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browser.equals("Edge")) {
			
			 WebDriverManager.edgedriver().setup();
			 driver = new EdgeDriver();
			
		}
		Reporter.log("User hits URL on " +browser);
		driver.get(URL);
		Reporter.log("Maximize the screen");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Login login = new Login();
		login.userLogin(userEmail, password);
	}
   
	/*Method Usage : Returns WebElement of given locator*/
	public static WebElement createWebElement(By locator) {

		WebElement element = driver.findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='2px solid red'", element);
		return element;
	}
     
	/*Method Usage : createWebElements returns List of webelement of same locators
	 * 
	 * @param : Locator
	 **/
	public static List<WebElement> createWebElements(By locator) {

		List<WebElement> element = driver.findElements(locator);
		return element;
	}
    
	/*Method Usage : Method clicks on locator passed 
	 * 
	 * @param : Locator
	 **/
	public static void clickElement(By locator) {
		createWebElement(locator).click();
	}
    
	/*Method usage : types text on WebElement with provided input
	 * 
	 * @param : Locator , inputString
	 **/
	public void typeText(By locator, String value) {
		createWebElement(locator).sendKeys(value);

	}
    
	/*Method Usage : gets text from element*/
	public String getTextFromElement(By locator) {
		return createWebElement(locator).getText();

	}
    
	/*Method Usage : Hovers on the element
	 * 
	 * @param : Locator
	 **/
	public void mouseHoverAnElement(By locator) {
		Actions a = new Actions(driver);
		a.moveToElement(createWebElement(locator)).build().perform();

	}
    
	/*Method Usage : This method explicity waits for element till it is visible
	 * 
	 * Time out : 6 seconds
	 **/
	public void explicitWaitForElement(By locator) {
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver, 6);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
    
	/*Logout the user session and quits browser*/
	@AfterTest
	public void tearDown() {
		Login login = new Login();
		clickElement(login.btn_logout);
		driver.quit();
	}

}
