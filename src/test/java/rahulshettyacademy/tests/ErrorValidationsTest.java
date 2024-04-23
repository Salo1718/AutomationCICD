package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;
public class ErrorValidationsTest extends BaseTest{

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException 
	{
		landingpage.loginApplication("srk1@gmail.com", "Loni@817");
		Assert.assertEquals("Incorrect email or password.",landingpage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException 
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue productcatalogue = landingpage.loginApplication("sita12@gmail.com", "S@0001am");
		List<WebElement> products = productcatalogue.getProductList();
		productcatalogue.addProductToCart(productName);
		CartPage cartPage = productcatalogue.goToCartPage();  //here we have call this method from productcatalogue class despite this method is in abstractcomponent class we are able to do this because productcalss is child of abstract class.
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	
	}
	
	
}
