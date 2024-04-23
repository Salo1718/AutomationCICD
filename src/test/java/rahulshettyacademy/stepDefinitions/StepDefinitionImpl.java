package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.LandingPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest{

	public LandingPage landingPage;
	public ProductCatalogue productcatalogue;
	public ConfirmationPage confirmationPage;
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		//code
		landingPage = launchApplication();
	}
	
	 @Given("^Logged in with username (.+) and password (.+)$")
	 public void Logged_in_with_username_and_password(String username, String password)
	 {
		productcatalogue  = landingpage.loginApplication(username,password);
	 }
	 
	  
	  @When("^I add product (.+) to cart$")
	  public void I_add_product_to_cart(String productName) throws InterruptedException
	  {
		  List<WebElement> products = productcatalogue.getProductList();
		   productcatalogue.addProductToCart(productName);
	  }
	  
	  @When("^Checkout (.+) and submit the order$")
	  public void Checkout_and_submit_the_order(String productName)
	  {
		    CartPage cartPage = productcatalogue.goToCartPage();  
			Boolean match = cartPage.VerifyProductDisplay(productName);
			Assert.assertTrue(match);
			CheckoutPage checkoutpage = cartPage.goToCheckOut();
			checkoutpage.selectCountry("india");
			confirmationPage = checkoutpage.submitOrder();
	  }
	  
	 
	  @Then("{string} message is displayed on ConfirmationPage")
	  public void message_displayed_ConfirmationPage(String string)
	  {
		  String confirmMessage = confirmationPage.getConfirmationMessage();
		  Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		  driver.close();
	  }
	  
	  
	  @Then("^\"([^\"]*)\" message is displayed$")
	  public void something_message_is_displayed(String strArg1) throws Throwable {
		  
			Assert.assertEquals(strArg1, landingpage.getErrorMessage());
			driver.close();
	  }
}
