package ch.makery.address.selenium;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;

public class BrowserPJS {

	/*
	 *  
	 * TESTING MODULE ONLY
	 * 
	 */
	
	public static void main(String[] args) {
		
		BrowserPJS example = new BrowserPJS();
		
		example.runPhantomjsWebDriver();
		
	}
	
	public void runPhantomjsWebDriver() {
		
		try
		{
			// Set executable file path to system variable phantomjs.binary.path's value.
			System.setProperty("phantomjs.binary.path",  System.getProperty("user.dir")+ "/resources/" + "phantomjs.exe");		
	       
		
			
			// Initiate PhantomJSDriver.
			WebDriver driver = new PhantomJSDriver();	
			
			/* If you want to see the browser action, you can uncomment this block of code to use Chrome.
			// Specify Chrome Driver executable file path.
		    String chromeDriverPath = "C:\\Workspace\\dev2qa.com\\Lib\\chromedriver_win32\\chromedriver.exe";
			 
			//Assign chrome driver path to system property "webdriver.chrome.driver"
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			  
			//Initiate Chrome driver instance.
			WebDriver driver = new ChromeDriver();
			*/ 
			
			//Must make the web browser full size other wise it can not parse out result by xpath.
			driver.manage().window().maximize();
			
			driver.get("http://www.yahoo.com");         
	        
			Thread.sleep(3000);      			
	        
			// Print out yahoo home page title.
			System.out.println("Page title is: " + driver.getTitle());	
			
			// Get yahoo search text box element.
			By searchBoxById = By.id("uh-search-box");
			WebElement searchBox = driver.findElement(searchBoxById);
			// Set search keyword.
			if(searchBox!=null)
			{
				searchBox.sendKeys("selenium");
				System.out.println("Input search keyword success.");
			}
			
			// Get yahoo search box submit element.
			By submitBtnById = By.id("uh-search-button");
			WebElement submitBtn = driver.findElement(submitBtnById);
			// Click submit button.
			if(submitBtn!=null)
			{
				submitBtn.click();
				System.out.println("Submit search form success.");
			}
			
			Thread.sleep(3000);
			
			// Get search result element list by xpath in search result page. 
			By resultListByXPath = By.xpath("//ol[@class=\"mb-15 reg searchCenterMiddle\"]/li");
			List resultElementList = driver.findElements(resultListByXPath);
			
			if(resultElementList!=null)
			{
				int size = resultElementList.size();
				System.out.println("Search result list size = " + size);
				// Loop the result list.
				for(int i=0;i<size;i++)
				{
					WebElement resultElement = (WebElement) resultElementList.get(i);
					
					try
					{
						// Get result item title element by xpath.
						By titleByXPath = By.xpath(".//a");
						WebElement titleELement = resultElement.findElement(titleByXPath);
						String title = "";
						if(titleELement!=null)
						{
							title = titleELement.getText();
						}
						
						if(!"".equals(title))
						{
							System.out.println("title = " + title);
						}
					}catch(NoSuchElementException ex)
					{
						ex.printStackTrace();
					}
					
					try
					{
						// Get result item description element by xpath.
						By descByXPath = By.xpath(".//div[@class=\"compText aAbs\"]");
						WebElement descElement = resultElement.findElement(descByXPath);
						String description = "";
						if(descElement!=null)
						{
							description = descElement.getText();
						}
						
						if(!"".equals(description))
						{
							System.out.println("description = " + description);
							System.out.println();
						}
					}catch(NoSuchElementException ex)
					{
						ex.printStackTrace();
					}
				}
			}
			
	        driver.quit();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
		
	}


