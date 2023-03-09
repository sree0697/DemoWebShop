package Pages;

import org.openqa.selenium.By;

public class Home extends BaseClass{
	
	/*Locator Objects*/
	public By currentUserAccountID = By.xpath("//div[@class='header-links']//a[@class='account']");
	public By shoppingcart = By.linkText("Shopping cart");
	By shopCartQty = By.xpath("//span[@class='cart-label' and contains(text(),'cart')]/following-sibling::span");
	public By ComputersLink = By.xpath("//div[@class='header-menu']//a[contains(text(),'Computers')]");
	public By DesktopLink = By.xpath("//a[contains(text(), 'Desktops')]");
	

}
