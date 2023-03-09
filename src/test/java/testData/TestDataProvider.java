package testData;

import org.testng.annotations.DataProvider;

import Pages.BaseClass;
import Utils.ExcelUtils;

public class TestDataProvider extends BaseClass{
	
	static String filePath = "C:\\Users\\chait\\OneDrive\\Desktop\\WebShop\\src\\test\\java\\testData\\TestData.xlsx";
	ExcelUtils utils = new ExcelUtils();
	
	@DataProvider(name = "BillingAddress")
	public String[][] BillingAddressDetails() throws Exception {

		String[][] addressDetails = utils.DataArray(filePath, "BillingAddress");
		return addressDetails;
	}

}
