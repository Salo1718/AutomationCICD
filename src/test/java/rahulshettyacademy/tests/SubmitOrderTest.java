package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.LandingPage;
import rahulshettyacademy.pageObjects.OrderPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;
public class SubmitOrderTest extends BaseTest{

	String productName = "ZARA COAT 3"; // i have moved this to classlevel/global variable sothat orderhistory can also access this.
	
	@Test(dataProvider="getData", groups={"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException 
	{
		
		ProductCatalogue productcatalogue = landingpage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products = productcatalogue.getProductList();
		productcatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productcatalogue.goToCartPage();  //here we have call this method from productcatalogue class despite this method is in abstractcomponent class we are able to do this because productcalss is child of abstract class.
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutpage = cartPage.goToCheckOut();
		checkoutpage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutpage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	
	}
	

	// To verify ZARA COAT 3 is displaying in orders page.
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productcatalogue = landingpage.loginApplication("srk17@gmail.com", "Loni@1817");// this line for logining in.
		OrderPage ordersPage = productcatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	//Extend Reports--

	@DataProvider
	public Object[][] getData() throws IOException
	{
	
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};  // we are creating here two dimensional syntax
										// in the two curly braces we will send two different data set values.
										// Object is a generic data type which can handle int, string etc any type of data.
	}
	
}



// 1st way to use data provider 

//you want to run my test with two different sets this is our requirement.
//	@DataProvider
//	public Object[][] getData()
//	{
//		return new Object[][] {{"srk17@gmail.com","Loni@1817","ZARA COAT 3"}, {"sita12@gmail.com","S@0001am","ADIDAS ORIGINAL"}}; 
//	}
//	
	
// 2nd way 

//HashMap<String, String> map =  new HashMap<String, String>(); // this how we create hashmap object.
//map.put("email","srk17@gmail.com");
//map.put("password","Loni@1817");
//map.put("product","ZARA COAT 3");
//
//HashMap<String, String> map1=  new HashMap<String, String>(); 
//map1.put("email","sita12@gmail.com");
//map1.put("password","S@0001am");
//map1.put("product","ADIDAS ORIGINAL");

