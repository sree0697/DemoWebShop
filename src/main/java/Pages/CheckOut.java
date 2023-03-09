package Pages;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class CheckOut extends BaseClass {
	String FirstName;
	String LastName;
	String email;
	String Company;
	String Country;
	String state;
	String City;
	String Address1;
	String Address2;
	String zipCode;
	String PhoneNumber;
	ArrayList<String> BillingAddressFields = new ArrayList<String>(Arrays.asList("First name", "Last name", "Email",
			"Company", "Country", "State", "City", "Address 1", "Address 2", "Zip", "Phone number"));
	ArrayList<String> BillingAddressValues;
    
	/*Parameterized Constructor*/
	public CheckOut(String firstName, String lastName, String email, String company, String Country, String state,
			String city, String address1, String address2, String zipCode, String phoneNumber) {
		this.FirstName = firstName;
		this.LastName = lastName;
		this.email = email;
		this.Company = company;
		this.Country = Country;
		this.state = state;
		this.City = city;
		this.Address1 = address1;
		this.Address2 = address2;
		this.zipCode = zipCode;
		this.PhoneNumber = phoneNumber;
		BillingAddressValues = new ArrayList<String>(Arrays.asList(firstName, lastName, email, company, Country, state,
				city, address1, address2, zipCode, phoneNumber));
	}
   
	/*No Arg Constructor*/
	public CheckOut() {

	}
    
	/*Locator Objects*/
	public By txt_selectBillingAddress = By.xpath("//div[@class='section select-billing-address']/label");
	public By checkBox_termsofservice = By.id("termsofservice");
	public By btn_checkOut = By.id("checkout");
	public By shippingAddressDropDown = By.id("shipping-address-select");
	public By billingAddressDropDown = By.id("billing-address-select");
	public By btn_continue = By.xpath("//input[@title='Continue']");
	public By txt_pickUpItemsMessage = By.xpath("//p[@class='description']/input");
	public By txt_paymentInformationMessage = By.xpath("//div[@class='section payment-info']/div/table/tbody/tr[1]/td[1]/p");
	public By btn_orderConfirmation = By.xpath("//input[@value='Confirm']");
	public By txt_orderSuccessMsg = By.xpath("//div[@class='section order-completed']/div/strong");
	public By orderNumber = By.xpath("//div[@class='section order-completed']/ul/li[1]");
	
	/**Method Usage : Selects Billing Address provided by the user from Checkout Page
	 *  
	 * @param addresss
	 */
	public void selectBillingAddress(String billingAddress) {
		Reporter.log("User selects Billing Address " +billingAddress);
		Select address = new Select(createWebElement(By.id("billing-address-select")));
		address.selectByVisibleText(billingAddress);
	}
	
	/**Method Usage : Selects Shipping Address provided by the user on Checkout page
	 * 
	 * @param address
	 **/
	public void selectShippingAddress(String shippingAddress) {
		Reporter.log("User selects Billing Address " +shippingAddress);
		Select address = new Select(createWebElement(shippingAddressDropDown));
		address.selectByVisibleText(shippingAddress);
	}
    
	/**Method Usage : Displays Selected Billing Address
	 * 
	 * @return address
	 */
	public String getSelectedBillingAddress() {
		Select s = new Select(createWebElement(billingAddressDropDown));
		Reporter.log("Selected Billing Address is " +s.getFirstSelectedOption().getText());
		return s.getFirstSelectedOption().getText();
	}
    
	/**Method Usage : Displays Selected Shipping Address
	 * 
	 * @return address selected
	 */
	public String getSelectedShippingAddress() {
		Select s = new Select(createWebElement(shippingAddressDropDown));
		Reporter.log("Selected Shipping Address is " +s.getFirstSelectedOption().getText());
		return s.getFirstSelectedOption().getText();
	}
    
	/**Method Usage : If user chooses new Billing Address, this method will enter all required fields like FirstName, LastName, zipcode etc..;
	 * Note : all details are passed in Parameterized constructor and stored in ArrayList
	 * 
	 * @return address selected
	 */
	public void EnterBillingAddressDetails() throws InterruptedException {
		for (int i = 0; i < BillingAddressFields.size(); i++) {
			if (BillingAddressFields.get(i).equals("Country") || BillingAddressFields.get(i).equals("State")) {
				By dropDown = By.xpath(
						"//label[contains(text(),'" + BillingAddressFields.get(i) + "')]/following-sibling::select");
				Select value = new Select(createWebElement(dropDown));
				value.selectByVisibleText(BillingAddressValues.get(i));
				Reporter.log("Field " +BillingAddressFields.get(i) + " entered as " +BillingAddressValues.get(i));
				Thread.sleep(3000);
			} else {
				By currentField = By.xpath("//div[@class='inputs']/label[contains(text(),'"
						+ BillingAddressFields.get(i) + "')]/following-sibling::input");
				Reporter.log("Field " +BillingAddressFields.get(i) + " entered as " +BillingAddressValues.get(i));
				createWebElement(currentField).clear();
				typeText(currentField, BillingAddressValues.get(i));
				Thread.sleep(1000);
			}
		}
	}
    
	/**Method Usage : This method selects shipment Type provided by the user
	 * 
	 * @param shipmentType like Ground , Next Day Air
	 * 
	 **/
	public void selectShipment(String shipmentType) {
		
		Reporter.log("Select Shipment Type as - " +shipmentType);
		clickElement(By.xpath("//label[contains(text(),'"+shipmentType+"')]/preceding-sibling::input"));
		
	}
	
	/**Method Usage : This method selects Payment Type provided by the user
	 * 
	 * @param paymentType like Cash on Delivery, Credit card
	 */
	public void selectPaymentMethod(String paymentType) {
		
		Reporter.log("Select Shipment Type as - " +paymentType);
		clickElement(By.xpath("//label[contains(text(),'"+paymentType+"')]/preceding-sibling::input"));
	}
}
