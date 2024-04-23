package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageObjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingpage;

	public WebDriver initializeDriver() throws IOException
	{
		
		//properties class
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties"); //fileinputstream is used when you need to convert file into stream .
		// we will make some changes in the path as its too long and not everybody has the same path.
		// here by using user.dir it will take the path of were this program will be running we are
		// not hard coding the path.
		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"): prop.getProperty("browser");
		//prop.getProperty("browser");
		
		if(browserName.contains("chrome"))
		{
		ChromeOptions options = new ChromeOptions();
		//WebDriverManager.chromedriver().setup();  //we have added web driver dependencies to pom file.
		 if(browserName.contains("headless"))
		 {
		options.addArguments("headless");
		 }
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900)); //this helps you to run in full screen.
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			//firefox
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\DELL\\Desktop\\Selenium with Java\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//edge
			System.setProperty("webdriver.edge.driver",
					"C:\\Users\\DELL\\Desktop\\Selenium with Java\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
				
		//String to HashMap Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>()
				{
				});
		return data;
		//{map, map}
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingpage = new LandingPage(driver);  //this we have created object for importing driver to LandingPage class. 
		landingpage.GoTo();
		return landingpage;
	}
	
	@AfterMethod(alwaysRun=true) // this alwaysrun line was written when you are working with errorValidationTests.xml file
								// because if we don't write this the test will fail
								// because we used grouped feature in  errorvalidationstest.java class.
	public void tearDown()
	{
		driver.close();
	}
}
