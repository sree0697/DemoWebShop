package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class Login extends BaseClass{
	
	/*Page class Object Initialization*/
	Home home = new Home();
	
	/*Locator Objects*/
	By loginLink = By.linkText("Log in");
	By userEmailAddress	= By.id("Email");
	By userPassword = By.id("Password");
	By btn_login = By.xpath("//input[@type = 'submit' and @value='Log in']");
	public By btn_logout = By.linkText("Log out");
    
	/*Method Usage : This method logins to application with provided credentials*/
	public void userLogin(String userEmail, String password) {
		clickElement(loginLink);
		Reporter.log("User clicks on Login Link from Home Screen");
		Reporter.log("Enter email Addres : " +userEmail);
		typeText(userEmailAddress , userEmail);
		Reporter.log("Enters Password : " +password);
		typeText(userPassword , password);
		Reporter.log("Click on Login Button");
		clickElement(btn_login);
		Assert.assertTrue(getTextFromElement(home.currentUserAccountID).equals(userEmail));
		Reporter.log("User has succesfully logged in with email Address " +userEmail);
	}
}
