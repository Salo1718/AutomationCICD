package rahulshettyacademy.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver; //here we have created a local variable.
	public LandingPage(WebDriver driver)  //so when we make constructor same name as class name then it will executes first before executing anything in this class.
	{
		super(driver); // this line was written after the creation of abstractcomponent class.
		//Initialization
		this.driver = driver; // to get the driver from standalonetest class we will create an object in standalonetest class.
		PageFactory.initElements(driver,this); //this line is written when you are using pagefactory design and is written to get driver for findby annotation.
	}
	
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	// now here driver has no life before using constructor after using constructor this driver has life.
	
	//Page Factory
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCatalogue loginApplication(String email, String password) //this is an action method where we will compile all the three actions of login.
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productcatalogue = new ProductCatalogue(driver);  // if have writen this line so that we need not to create many object in the submitordertest class for every new page.
		return productcatalogue;
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void GoTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
}
