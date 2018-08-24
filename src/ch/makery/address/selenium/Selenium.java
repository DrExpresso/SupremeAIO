package ch.makery.address.selenium;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

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
	private String billingFirstName;
	private String billingEmail;
	private String telephone;
	private String billingAddress;
	private String billingCity;
	private String billingPostcode;
	private String country;
	private String cardType;
	private String cardNumber;
	private String cardExpiry;
	private String cardYear;
	private String cardCvv;
	
	
	public ChromeDriver driver;
	private int retryCounter = 10;
	private int checkoutDelay = keywordInfo.getKeywordInfo().getCheckoutDelay();
	
	//Console Objects
	private PrintWriter printWriter;
	
	public static Selenium getTesting() {
		return selenium;
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, ParseException {
			Selenium testrun = new Selenium();
			testrun.fullRun();
	}
	
	
	public void initialize() throws IOException  {
	}
	
	public void fullRun() throws IOException, InterruptedException, ParseException {
			/*****************************************************
			 * Headless property -- Uncomment to use headless browser ChromeOptions headless
			 * = new ChromeOptions(); headless.addArguments("--headless"); WebDriver driver
			 * = new ChromeDriver(headless);
			 *****************************************************/
		
		//Create Log File
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + "/Log_Task_" + "1" + ".txt")) {
			file.flush();
			System.out.println("Successfully created log file");
		}
		
		//Start the print writer to Log to the file
		FileWriter rawLogOutput = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/Log_Task_1.txt");
		printWriter = new PrintWriter(rawLogOutput);
		
		printWriter.println("LOG [TASK: " + "1 -- " +  " Time: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "]");
		printWriter.println();
		
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
			WebElement mySelectElement = driver.findElement(By.xpath("//*[@id=\"size\"]"));
			Select dropdown = new Select(mySelectElement);
			
			
			
			if (dropdown.getOptions() != null) {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Size found");
				dropdown.selectByVisibleText(size);
				driver.findElement(By.name("commit")).click();
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Cart successful!");
			} else {
				for (int i = 0; i < retryCounter; i++) {
					printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Size not Found... Retrying in  3 seconds");
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
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Checking out");
				
				printWriter.close();

				//Open JSON Parse file to get billing and shipping info
				JSONParser parser = new JSONParser();
				
				JSONObject a = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir")+ "/resources/json" + "/Default_Profile.json"));
				
				billingFirstName = (String) a.get("Fullname");
				billingEmail = (String) a.get("Email");
				telephone = (String) a.get("Telephone");
				billingAddress = (String) a.get("Address");
				billingCity  = (String) a.get("City");
				billingPostcode  = (String) a.get("Postcode");
				country = (String) a.get("Country");
				cardType = (String) a.get("Card Type");
				cardExpiry = (String) a.get("Card Expiry Month");
				cardYear = (String) a.get("Card Expiry Year");
				cardNumber = (String) a.get("Card Number");
				cardCvv = (String) a.get("Card Security Code");
				
				
				//Check T&C box
				WebElement element = driver.findElement(By.xpath("//*[@id=\"cart-cc\"]/fieldset/p/label/div/ins"));
				js.executeScript("arguments[0].click();", element);
				
				//Shipping and Billing Info text input				
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
				
				//Select Country
				WebElement countryDropDown = driver.findElement(By.xpath("//*[@id=\"order_billing_country\"]"));
				countryDropDown.click();
				new Select(countryDropDown).selectByVisibleText(country);
				
				//Select Card Type
				js.executeScript("$('select[name=\"credit_card[type]\"]').click();");
				Select cardTypeDropDown = new Select(driver.findElement(By.name("credit_card[type]")));
				cardTypeDropDown.selectByVisibleText(cardType);
				
				WebElement number = driver.findElement(By.name("credit_card[cnb]"));
				js.executeScript("arguments[0].value='" + cardNumber + "';", number);
				
				//Select Card Expiry and Year
				WebElement expiryMonthDropDown = driver.findElement(By.xpath("//*[@id=\"credit_card_month\"]"));
				expiryMonthDropDown.click();
				new Select(expiryMonthDropDown).selectByVisibleText(cardExpiry);
				
				WebElement expiryYearDropDown = driver.findElement(By.xpath("//*[@id=\"credit_card_year\"]"));
				expiryYearDropDown.click();
				new Select(expiryYearDropDown).selectByVisibleText(cardYear);
				
				WebElement cvv = driver.findElement(By.id("vval"));
				js.executeScript("arguments[0].value='" + cardCvv + "';", cvv);
				
				
				
				if (checkoutDelay > 0) {
					Thread.sleep(checkoutDelay);
					WebElement checkoutButton = driver.findElement(By.name("commit"));
					js.executeScript("arguments[0].click();", checkoutButton);
				} else {
					WebElement checkoutButton = driver.findElement(By.name("commit"));
					js.executeScript("arguments[0].click();", checkoutButton);
				}
				
			} else {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Checkout Failed");
			}
			
			if(driver.getPageSource().contains("you will recieve a shipping confirmation with the tracking number")) {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Checked out");
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
					printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Found!");
				} 
				else {
					printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Not Found! Retrying!");
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
