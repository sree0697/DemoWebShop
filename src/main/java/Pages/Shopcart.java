package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

public class Shopcart extends BaseClass {
	
	/*Page class Objects Initialization*/
	Home home = new Home();
    
	/*Locator Objects*/
	public By pageTitle = By.xpath("//div[@class='page-title']");

	By selectProductsInCart = By.xpath("//td/input[@type='checkbox']");

	By btn_UpdateShoppingcart = By.xpath("//input[@value='Update shopping cart']");

	By orderSummaryMessage = By.className("order-summary-content");
	
	public By subTotal = By.xpath("//span[@class='product-price order-total']/strong");
	
	/*Method Usage : This method checks if there are any items in shop cart and clears it*/
	public void clearShopCart() {

		String message = "Your Shopping Cart is empty!";
		if (getTextFromElement(home.shopCartQty).contains("0")) {
			Reporter.log("Shop cart is Empty. Can continue Shopping !!");
		} else {
			clickElement(home.shoppingcart);
			Assert.assertTrue(getTextFromElement(pageTitle).contains("Shopping cart"));
			Reporter.log("User is on shopcart page");
			List<WebElement> ProductsIncart = createWebElements(selectProductsInCart);
			Reporter.log("Select all products that has been added to cart");
			for (WebElement item : ProductsIncart) {
				item.click();
			}
			clickElement(btn_UpdateShoppingcart);
			Reporter.log("Click on Update Shopping Cart to remove items");
			Assert.assertEquals(getTextFromElement(orderSummaryMessage), message);
			Reporter.log("Shop cart is cleared, you can continue shopping");
		}
	}
	

	
	

}
 