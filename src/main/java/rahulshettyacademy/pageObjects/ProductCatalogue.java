package rahulshettyacademy.pageObjects;

import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver; // here we have created a local variable.

	public ProductCatalogue(WebDriver driver) // so when we make constructor same name as class name then it will
												// executes first before executing anything in this class.
	{
		super(driver);
		// Initialization
		this.driver = driver; // to get the driver from standalonetest class we will create an object in
								// standalonetest class.
		PageFactory.initElements(driver, this); // this line is written when you are using pagefactory design and is
												// written to get driver for findby annotation.
	}

	// List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	// this line we need to convert according to page factory.

	// Page Factory
	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prod = getProductList().stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
}
