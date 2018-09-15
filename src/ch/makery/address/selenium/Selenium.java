package ch.makery.address.selenium;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
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
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;

public class Selenium implements Runnable {

	private int taskNumber;
	private String mainURL = "https://www.supremenewyork.com/shop/all/";
	private String checkoutURL = "https://www.supremenewyork.com/checkout";
	private HashMap<String, String> attributes = new HashMap<String, String>();
	private String finalURL = null;
	private String keyword;
	private String size;
	private String category;
	private String color;
	private String profileLoader;
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
	
	private SupremeBotOverviewController controller;
	
	//Console Objects
	private PrintWriter printWriter;

	
	public Selenium(SupremeBotOverviewController controller, int taskNumber, String keyword, String size, String category, String color, String profileLoader) {
		this.controller = controller;
		this.taskNumber = taskNumber;
		this.keyword = keyword;
		this.size = size;
		this.category = category;
		this.color = color;
		this.profileLoader = profileLoader;
	}
	
	public  void main(String[] args) throws InterruptedException, IOException, ParseException {
			this.fullRun();
	}
	
	
	
	public void fullRun() throws IOException, InterruptedException, ParseException {
			/*****************************************************
			 * Headless property -- Uncomment to use headless browser ChromeOptions headless
			 * = new ChromeOptions(); headless.addArguments("--headless"); WebDriver driver
			 * = new ChromeDriver(headless);
			 *****************************************************/
		
		controller.setBrowserMode(this);
		
		String fileName;
		
		//Create Log File
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + "[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "] - Task= " +taskNumber+".txt")) {
			fileName = file.toString();
			file.flush();
			//controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Successfully created log file \n");
		}
		
		File f = new File(System.getProperty("user.dir")+ "/resources/Logs/");
		File[] matchingFiles = f.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(fileName);
			}
		});
		
		//Start the print writer to Log to the file
		FileWriter rawLogOutput = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/"+matchingFiles.toString());
		printWriter = new PrintWriter(rawLogOutput);
		
		printWriter.println("LOG [TASK: " + taskNumber + " -- " +  " Time: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "]");
		printWriter.println();
		
		controller.statusColumnUpdateRunning();
				
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
			
	         System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "/resources/" +"/chromedriver.exe");


			// Create a new instance of the Chrome driver
			driver = new ChromeDriver(options);
		

			//Launch method for finding keyword
		
		
			try {
					this.keywordFinder();
					controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Not Found! Retrying!  \n");
			} catch (WebDriverException e) {
				controller.statusColumnUpdateError();
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Not Found! \n");
				driver.close();
				Thread.currentThread().interrupt();
			}
				

			//Error handler
			if (driver.getCurrentUrl() == null) {
				controller.statusColumnUpdateError();
				driver.quit();
			}

			//

			try {
				driver.get(finalURL);
			} catch (WebDriverException e) {
				controller.statusColumnUpdateError();
				driver.close();
				Thread.currentThread().interrupt();
			}
			
			controller.statusColumnUpdateItemFound();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Item Found \n");
			
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			JavascriptExecutor billing_name_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_email_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_tel_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_address_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_postcode_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_card_js = (JavascriptExecutor) driver;
			JavascriptExecutor order_cvv_js = (JavascriptExecutor) driver;
			

			// Select Size and add to cart
			WebElement mySelectElement = driver.findElement(By.xpath("//*[@id=\"size\"]"));
			Select dropdown = new Select(mySelectElement);
			
			
			
			if (dropdown.getOptions() != null) {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Size found");
				dropdown.selectByVisibleText(size);
				driver.findElement(By.name("commit")).click();
				controller.statusColumnUpdateAddingToCart();
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Cart successful!");
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Cart successful! \n");
			} else {
				for (int i = 0; i < retryCounter; i++) {
					printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Size not Found... Retrying in  3 seconds");
					controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Size not Found... Retrying in  3 seconds \n");
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
				controller.statusColumnUpdateCheckingOut();
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Checking out \n");
				
				printWriter.close();

				//Open JSON Parse file to get billing and shipping info				
				JSONParser parser = new JSONParser();
				
				JSONObject a = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir")+ "/resources/json/" + profileLoader  +".json"));
				
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
			//	WebElement element = driver.findElement(By.xpath("//*[@id=\"cart-cc\"]/fieldset/p/label/div/ins"));
			//	js.executeScript("arguments[0].click();", element);
			//	js.executeScript("document.getElementByClassName('order[terms]').setAttribute('selected', 'selected')");
				billing_name_js.executeScript("document.getElementById('order_billing_name').setAttribute('value', 'John Doe')");
				order_email_js.executeScript("document.getElementById('order_email').setAttribute('value', 'johndoe@gmail.com')");
				order_tel_js.executeScript("document.getElementById('order_tel').setAttribute('value', '07899608391')");
				order_address_js.executeScript("document.getElementById('bo').setAttribute('value', '1 Main Street')");
				order_postcode_js.executeScript("document.getElementById('order_billing_zip').setAttribute('value', 'LE2 1LZ')");
				order_card_js.executeScript("document.getElementById('cnb').setAttribute('value', '4658 5910 9812 8034')");
				order_cvv_js.executeScript("document.getElementById('vval').setAttribute('value', '659')");
				
				
				//Select Country
				WebElement countryDropDown = driver.findElement(By.xpath("//*[@id=\"order_billing_country\"]"));
				countryDropDown.click();
				new Select(countryDropDown).selectByVisibleText(country);
				
				//Select Card Type
				js.executeScript("$('select[name=\"credit_card[type]\"]').click();");
				Select cardTypeDropDown = new Select(driver.findElement(By.name("credit_card[type]")));
				cardTypeDropDown.selectByVisibleText(cardType);
				
				
				
				//Select Card Expiry and Year
				WebElement expiryMonthDropDown = driver.findElement(By.xpath("//*[@id=\"credit_card_month\"]"));
				expiryMonthDropDown.click();
				new Select(expiryMonthDropDown).selectByVisibleText(cardExpiry);
				
				WebElement expiryYearDropDown = driver.findElement(By.xpath("//*[@id=\"credit_card_year\"]"));
				expiryYearDropDown.click();
				new Select(expiryYearDropDown).selectByVisibleText(cardYear);
				
	
				
				
				
				if (checkoutDelay > 0) {
					Thread.sleep(checkoutDelay);
					WebElement checkoutButton = driver.findElement(By.name("commit"));
					js.executeScript("arguments[0].click();", checkoutButton);
					controller.statusColumnUpdateRecaptcha();
					controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Please solve captcha \n");
					Thread.currentThread().wait();
				} else {
					WebElement checkoutButton = driver.findElement(By.name("commit"));
					js.executeScript("arguments[0].click();", checkoutButton);
					controller.statusColumnUpdateRecaptcha();
					controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Please solve captcha \n");
					Thread.currentThread().wait();
				}
				
			} else {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Checkout Failed");
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Checkout Failed \n");
			}
			
			if(driver.getPageSource().contains("you will recieve a shipping confirmation with the tracking number")) {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Checked out");
				controller.statusColumnUpdateCheckedOut();
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Checked out! \n");
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
				controller.statusColumnUpdateError();
				driver.quit();
			}
			
			//Connect to requested webpage if errors are clear
			org.jsoup.nodes.Document doc = Jsoup.connect(mainURL + category).get();
			
	
			//Search webpage for keyword within articles and store in the attributes hashmap 
			List<Element> articles = doc.getElementsByClass("inner-article");
			try {
				for (Element info : articles) {
					if (info.getElementsByClass("name-link").toString().contains(keyword) && info.getElementsByClass("name-link").get(1).toString().contains(color)) {
						attributes.put(info.getElementsByClass("name-link").text(), info.getElementsByClass("name-link").attr("abs:href").toString());
						printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Found!");
					} 
					else  {
						printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Not Found! Retrying!");
						controller.statusColumnUpdateItemNotFound();
					}
				}
			} catch (WebDriverException e) {
				printWriter.println("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Item Not Found! Retrying!");
				controller.statusColumnUpdateError();
			}
	
			// Get the URL from the HashMap, clean it and save it in finalURL
			if (attributes != null) {
				String item = attributes.values().toString();
				String item1 = item.replace("[", "");
				finalURL = item1.replace("]", "");
			}
			//Catch any errors to quite the chrome driver
		} catch (NullPointerException e) {
			controller.statusColumnUpdateError();
			e.printStackTrace();
	        driver.quit();
	    } catch (HttpStatusException e) {
	    	controller.statusColumnUpdateError();
	        e.printStackTrace();
	        driver.quit();
	    } catch (IOException e) {
	        e.printStackTrace();
	        controller.statusColumnUpdateError();
	        driver.quit();
	    }
	}

	@Override
	public void run() {
		try {
			this.main(null);
		} catch (IOException | InterruptedException | ParseException e) {
		    controller.statusColumnUpdateError();
			e.printStackTrace();
		}
	}

}
