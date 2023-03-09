package Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

public class Categories extends BaseClass {
    
	/*Locator Objects*/
	By availableProducts = By.xpath(
			".//input[@type='button' and @value='Add to cart']/ancestor::div[2]/preceding-sibling::h2[@class='product-title']");

	By btn_addTocart = By.xpath("//input[@type = 'button' and @value = 'Add to cart']");

	By productPrice = By.xpath("//div[@class='product-price']/span");

	By Totalquantity = By.xpath("//label[@class='qty-label']/following-sibling::input[1]");

	By addToCartProductSuccessMsg = By.xpath("//p[@class='content']");

	By currentItem = By.xpath("//li/strong[@class='current-item']");
	
    
	/*Method Usage : User selects desired desktop specifications form available list
	 * 
	 * @params Product name with specifications : Processor , RAM , HDD, Software & quantity of the product
	 */
	public void addDesiredDesktopToCart(String desiredProduct, String processor, String RAM, String HDD,
			String Software, String quantity) throws InterruptedException {

		ArrayList<String> currentProducts = new ArrayList<String>();

		List<WebElement> displayedProductsList = createWebElements(availableProducts);

		for (WebElement w : displayedProductsList) {
			currentProducts.add(w.getText());
		}
		Reporter.log("Available Desktops : " + currentProducts);
		Reporter.log("Please select desired one from available items");
        Reporter.log("User selects " +desiredProduct+ " from available items");
		if (currentProducts.contains(desiredProduct)) {
			clickElement(
					By.xpath("//div[@class='item-box']/div/div[2]/h2/a[contains(text(),'" + desiredProduct + "')]"));
			explicitWaitForElement(currentItem);
			Assert.assertTrue(getTextFromElement(currentItem).equalsIgnoreCase(desiredProduct));
			Reporter.log("Choose available features for wishlist Product and proceed to Payment Screen");

			chooseDesktopSpecifications("Processor", processor);
			chooseDesktopSpecifications("RAM", RAM);
			chooseDesktopSpecifications("HDD", HDD);
			chooseDesktopSpecifications("Software", Software);

			Reporter.log("Product Price is " + getTextFromElement(productPrice));

			Reporter.log("User increases quantity to " + quantity);
			createWebElement(Totalquantity).clear();
			typeText(Totalquantity, quantity);

			Reporter.log("Add the Product to cart");
			clickElement(
					By.xpath("//div[@class='add-to-cart-panel']//input[@type = 'button' and @value = 'Add to cart']"));

			explicitWaitForElement(addToCartProductSuccessMsg);
			Assert.assertTrue(getTextFromElement(addToCartProductSuccessMsg).contains("he product has been added"));

			Reporter.log("Product has been added to cart successfully without any issue ");

		} else {
			Reporter.log("Sorry ! Desired Product - " + desiredProduct
					+ " is not available for Purchase , Please choose from available items");
		}

	}
    
	/**Method usage : This method selects specification based on inputs provided by the user
	 * 
	 * @param feature : RAM
	 * @param value : 4 GB
	 */
	public boolean chooseDesktopSpecifications(String feature, String value) {
		Reporter.log(" User selects " + feature + " - " + value);
		WebElement ele = createWebElement(By.xpath("//label[contains(text(),'" + feature
				+ "')]/parent::dt/following-sibling::dd[1]/ul/li/label[contains(text(),'" + value
				+ "')]/preceding-sibling::input"));
		ele.click();
		return ele.isSelected();
	}

}