package ch.makery.address.selenium;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import ch.makery.address.model.Person;
import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;

public class Selenium {

	public final static Selenium selenium = new Selenium();
	private String mainURL = "https://www.supremenewyork.com/shop/all/";
	private String checkoutURL = "https://www.supremenewyork.com/checkout";
	private HashMap<String, String> attributes = new HashMap<String, String>();
	private String finalURL = null;
	private String keyword = keywordInfo.getKeywordInfo().getKeyword();
	private String size = keywordInfo.getKeywordInfo().getSize();
	private String category = keywordInfo.getKeywordInfo().getCatagory();
	private String color = keywordInfo.getKeywordInfo().getColor();
	private String PROXY = "http://" + keywordInfo.getKeywordInfo().getProxy();

	// Import Billing Info Details from saved model(Person)
	private String billingFirstName = Person.getPersonInfo().getFullName();
	private String billingEmail = Person.getPersonInfo().getEmail();
	private String telephone = Person.getPersonInfo().getTelephone();
	private String billingAddress = Person.getPersonInfo().getAddress();
	private String billingCity = Person.getPersonInfo().getCity();
	private String billingPostcode = Person.getPersonInfo().getPostcode();
	private String cardNumber = Person.getPersonInfo().getCardNumber();
	private String cardCvv = Person.getPersonInfo().getCvv();
	public ChromeDriver driver;

	
	private int retryCounter = 10;
	
	public static Selenium getTesting() {
		return selenium;
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
			Selenium testrun = new Selenium();
			testrun.fullRun();
	}

	public void fullRun() throws IOException, InterruptedException {
			/*****************************************************
			 * Headless property -- Uncomment to use headless browser ChromeOptions headless
			 * = new ChromeOptions(); headless.addArguments("--headless"); WebDriver driver
			 * = new ChromeDriver(headless);
			 *****************************************************/

			//PROXY -- IP Authentication only 
			ChromeOptions options = new ChromeOptions();		
//			
//			  if (PROXY.contains("localhost")) {
//				Proxy proxy = new Proxy();
//				proxy.setHttpProxy("http://127.2.0.1:8080")
//				.setFtpProxy("http://127.0.0.1:80280")
//				.setSslProxy("http://127.0.0.1:82080");
//				options.setProxy(proxy);
//			  }
//			} else {
//				Proxy proxy = new Proxy();
//				proxy.setHttpProxy(PROXY)
//				.setFtpProxy(PROXY)
//				.setSslProxy(PROXY);
//				options.setProxy(proxy);
//			//	System.out.println(PROXY.toString() + keywordInfo.getKeywordInfo().getProxy().toString());
//			//-----------------------------------------//
//			}
			//System.setProperty("webdriver.chrome.driver", "C://Users//Prati//Documents//eclipse-workspace//Projects//SupremeAIO//resources/chromedriver.exe");
			
	         System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "/resources/" +"/chromedriver.exe");


			// Create a new instance of the Chrome driver
			driver = new ChromeDriver(options);
		
			JavascriptExecutor js = (JavascriptExecutor) driver;

			//Launch method for finding keyword
			this.keywordFinder();

			//Error handler
			if (driver.getCurrentUrl() == null) {
				driver.quit();
			}

			//
			driver.get(finalURL);
			
		
			

			// Select Size and add to cart
			WebElement mySelectElement = driver.findElement(By.name("size"));
			Select dropdown = new Select(mySelectElement);
			
			if (dropdown.getOptions().contains(size)) {
				System.out.println("Size found");
				dropdown.selectByVisibleText(size);
				driver.findElement(By.name("commit")).click();
				System.out.println("Cart successfull!");
			} else {
				for (int i = 0; i < retryCounter; i++) {
					System.out.println("Size not Found");
					System.out.println("Retrying in  3 seconds");
					Thread.sleep(3000);
					driver.navigate().refresh();
				}
			}
		
			
			//Sleep incase the add to cart is not detected
			Thread.sleep(1000);

			driver.navigate().to(checkoutURL);


			// Fill in details at checkout
			String checkoutUrl = driver.getCurrentUrl();

			if (checkoutUrl.contains("checkout")) {
				System.out.println("Checking out");
				WebElement fullname = driver.findElement(By.id("order_billing_name"));
				js.executeScript("arguments[0].value='" + billingFirstName + "';", fullname);
				WebElement email = driver.findElement(By.id("order_email"));
				js.executeScript("arguments[0].value='" + billingEmail + "';", email);
				WebElement tel = driver.findElement(By.id("order_tel"));
				js.executeScript("arguments[0].value='" + telephone + "';", tel);
				WebElement address = driver.findElement(By.id("bo"));
				js.executeScript("arguments[0].value='" + billingAddress + "';", address);
				WebElement city = driver.findElement(By.id("order_billing_city"));
				js.executeScript("arguments[0].value='" + billingCity + "';", city);
				WebElement postcode = driver.findElement(By.id("order_billing_zip"));
				js.executeScript("arguments[0].value='" + billingPostcode + "';", postcode);
				WebElement number = driver.findElement(By.id("cnb"));
				js.executeScript("arguments[0].value='" + cardNumber + "';", number);
				WebElement cvv = driver.findElement(By.id("vval"));
				js.executeScript("arguments[0].value='" + cardCvv + "';", cvv);
				driver.findElement(By.className("iCheck-helper")).click();

			}
		}
	
	
	//Method to input authenticate in chrome Browser -- currently not working
	public void configureAuthentication() {
//		driver.get("chrome-extension://enhldmjbphoeibbpdhmjkchohnidgnah/options.html");
//		driver.findElement(By.id("username")).sendKeys("ZeVSiXf0!a67");
//		driver.findElement(By.id("password")).sendKeys("mPRz2DxD");
//		driver.findElement(By.className("credential-form-submit")).click();
//		options.addExtensions(new File("C://Users//Prati//Documents//eclipse-workspace//Projects//SupremeAIO//resources/extension/MultiPass-for-HTTP-basic-authentication_v0.7.4.crx"));
	}
	
	public void killBrowser() {
		driver.quit();
	}

	public void keywordFinder() throws IOException, InterruptedException {
		try {
			//Checks if website is working {Error Catcher} 
			Connection.Response response = Jsoup.connect(mainURL + category).execute();
			response = Jsoup.connect(mainURL + category).execute();

			int statusCode = response.statusCode();
			
			if (statusCode == 404) {
				driver.close();
			}
			
			//Connect to requested webpage if errors are clear
			org.jsoup.nodes.Document doc = Jsoup.connect(mainURL + category).get();
			System.out.println(doc.baseUri());
			
	
			//Search webpage for keyword within articles and store in the attributes hashmap 
			List<Element> articles = doc.getElementsByClass("inner-article");
	
			for (Element info : articles) {
				if (info.getElementsByClass("name-link").toString().contains(keyword) && info.getElementsByClass("name-link").get(1).toString().contains(color)) {
					attributes.put(info.getElementsByClass("name-link").text(), info.getElementsByClass("name-link").attr("abs:href").toString());
					System.out.println("Item Found!");
				} 
				else {
					 System.out.println("Item Not Found! Retrying");
				}
			}
	
			// Get the URL from the HashMap, clean it and save it in finalURL
			if (attributes != null) {
				String item = attributes.values().toString();
				String item1 = item.replace("[", "");
				finalURL = item1.replace("]", "");
			}
			//Catch any errors to quite the chrome driver
		} catch (NullPointerException e) {
	        e.printStackTrace();
	        driver.quit();
	        SupremeBotOverviewController.getSupremeBotOverviewController().stopTasks();
	    } catch (HttpStatusException e) {
	        e.printStackTrace();
	        driver.quit();
	        SupremeBotOverviewController.getSupremeBotOverviewController().stopTasks();
	    } catch (IOException e) {
	        e.printStackTrace();
	        driver.quit();
	        SupremeBotOverviewController.getSupremeBotOverviewController().stopTasks();
	    }
	}

}
