package testcases;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Pages.BaseClass;
import Pages.Categories;
import Pages.CheckOut;
import Pages.Home;
import Pages.Shopcart;
import testData.TestDataProvider;

public class TestCase_validatePurchaseOrder extends BaseClass {
    
	/*Page class Object Initialization*/
	
	Shopcart shopcart = new Shopcart();
	Home home = new Home();
	Categories category = new Categories();
	CheckOut checkout = new CheckOut();
    /**
     * Description : This Test case validates Desktop placed by user with payment Type as COD and prints Order Number
     * @throws InterruptedException
     */
	@Test(dataProvider = "BillingAddress", dataProviderClass = TestDataProvider.class)
	public void validateOrderPurchased(String FirstName, String LastName, String email, String Company, String Country,
			String state, String City, String Address1, String Address2, String zipCode, String PhoneNumber)
			throws InterruptedException {
       
		//clear shop cart if any products added previously
		Reporter.log("User logins to Application and is on Home Screen");
		shopcart.clearShopCart();
		
		//User clicks on Computer from displayed Desktop options
		Reporter.log("Click on Desktops from Computers Panel");
		mouseHoverAnElement(home.ComputersLink);
		mouseHoverAnElement(home.DesktopLink);
		explicitWaitForElement(home.DesktopLink);
		clickElement(home.DesktopLink);
		
		//User chooses desired Desktop with specifications and adds selected product to cart
		category.addDesiredDesktopToCart("Build your own expensive computer", "Medium", "4GB", "320 GB", "Image Viewer",
				"3");
		
		clickElement(home.shoppingcart);
		
		//validate sub-Total amount
		Assert.assertFalse(getTextFromElement(shopcart.subTotal).isEmpty());
		
		//User agrees to Terms and conditions and checkout the product
		clickElement(checkout.checkBox_termsofservice);
		clickElement(checkout.btn_checkOut);
		explicitWaitForElement(shopcart.pageTitle);
		Assert.assertTrue(getTextFromElement(shopcart.pageTitle).equals("Checkout"));
		Reporter.log("User is on Checkout Page");
		
		//User enters New Billing Address and selects Shipping Address same as Billing Address
		if (createWebElement(checkout.txt_selectBillingAddress).isDisplayed()) {
			checkout.selectBillingAddress("New Address");
			CheckOut billingAddress = new CheckOut(FirstName, LastName, email, Company, Country, state, City, Address1,
					Address2, zipCode, PhoneNumber);
			billingAddress.EnterBillingAddressDetails();
			clickElement(checkout.btn_continue);
			Thread.sleep(2000);
			Select s = new Select(createWebElement(checkout.shippingAddressDropDown));
			String shippingAddress = FirstName + " " + LastName + "," + " " + Address1 + ","+ " " + City + "," + " " + state + " " + zipCode + "," + " " + Country;
			s.selectByVisibleText(shippingAddress);
			Thread.sleep(4000);
		} else {
			clickElement(checkout.btn_continue);
		}
		//User continues to Shipment Type
		clickElement(By.xpath("//div[@id='shipping-buttons-container']/input"));
		checkout.selectShipment("Next Day Air");
		clickElement(By.xpath("//div[@id='shipping-method-buttons-container']/input"));
		
		//User chooses Payment Type as COD
		checkout.selectPaymentMethod("Cash On Delivery");
		clickElement(By.xpath("//div[@id='payment-method-buttons-container']/input"));
		
		//User validates Payment Information Message and continue to order confirmation Screen
		explicitWaitForElement(checkout.txt_paymentInformationMessage);
		Assert.assertTrue(getTextFromElement(checkout.txt_paymentInformationMessage).equals("You will pay by COD"));
		Reporter.log("Payment Method type is displayed - " +getTextFromElement(checkout.txt_paymentInformationMessage));
		clickElement(By.xpath("//div[@id='payment-info-buttons-container']/input"));
		explicitWaitForElement(checkout.btn_orderConfirmation);
		
		//User reviews order details and confirms purchase
		clickElement(checkout.btn_orderConfirmation);
		explicitWaitForElement(checkout.txt_orderSuccessMsg);
		
		//User validates order placed status
		Assert.assertEquals(getTextFromElement(checkout.txt_orderSuccessMsg),
				"Your order has been successfully processed!");
		
		//Prints Order Number on Console
		Reporter.log("Order has been placed succesfully with " + getTextFromElement(checkout.orderNumber));
		clickElement(By.xpath("//input[@value='Continue']"));
		
		//User navigates back to Home Screen
		Assert.assertTrue(createWebElement(home.currentUserAccountID).isDisplayed());
		Reporter.log("User naviagted back to Home Screen !");

	}
}
