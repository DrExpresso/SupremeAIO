package ch.makery.address.selenium;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import ch.makery.address.view.SupremeBotOverviewController;

/**
 * @author DrExpresso
 */
public class InstoreRegistrationSelenium implements Runnable {
	
	private SupremeBotOverviewController controller;
	private String billingProfile;
	private int taskNumber;
	
	public ChromeDriver driver;
	private String signupURL = "https://london.supremenewyork.com/";
	
	// Import Billing Info Details from saved model(Person)
	private String profileLoader;
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
	
	/**
	 * @param controller - pass the main bot controller to access methods from that class
	 * @param taskNumber - task number for indentification
	 * @param billingProfile - billing profile used for the checkout process
	 */
	public InstoreRegistrationSelenium(SupremeBotOverviewController controller, int taskNumber, String billingProfile) {
		this.controller = controller;
		this.taskNumber = taskNumber;
		this.billingProfile = billingProfile;
	}

	/**
	 * Invokes the launchTask method
	 */
	@Override
	public void run() {
		try {
			this.main();
		} catch (InterruptedException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void main() throws InterruptedException, ParseException {
		//Update all columns to running
		System.out.println(taskNumber);
		controller.returnTasks().getItems().get(taskNumber - 1).setStatus("Running");
		controller.returnTasks().refresh();

		//Check if URL returns OK response if not stop the task
		try {
			if (this.getResponseCode(signupURL) == 200) {
				this.launchBrowser();
			} else {
				System.out.println("Thread stopped");
				Thread.currentThread().interrupt();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void launchBrowser() throws InterruptedException, FileNotFoundException, IOException, ParseException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "/resources/" +"/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.get(signupURL);
	
		while (driver.getPageSource().contains("Registration has closed for this release")) {
			Thread.sleep(3000);
			driver.navigate().refresh();
		}
		
		
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
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		if (driver.getCurrentUrl().contains("signup")) {

			js.executeScript("document.getElementById('customer_name').setAttribute('value', ' " + billingFirstName + "')");
			js.executeScript("document.getElementById('customer_email').setAttribute('value', '"+ billingEmail + "')");
			js.executeScript("document.getElementById('customer_tel').setAttribute('value', '"+ telephone +"')");

			WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"step_1_button\"]"));
			continueButton.click();
		
			Thread.currentThread().wait(1000);
			
			js.executeScript("document.getElementById('customer_street').setAttribute('value', '"+billingAddress +"')");
			js.executeScript("document.getElementById('customer_zip').setAttribute('value', '"+billingPostcode+"')");
			js.executeScript("document.getElementById('customer_city').setAttribute('value', '"+billingCity+"')");
			js.executeScript("document.getElementById('cn').setAttribute('value', '"+cardNumber+"')");
			js.executeScript("document.getElementById('credit_card_verification_value').setAttribute('value', '"+cardCvv+"')");
			
			//Select Card Expiry and Year
			WebElement expiryMonthDropDown = driver.findElement(By.name("credit_card[month]"));
			expiryMonthDropDown.click();
			new Select(expiryMonthDropDown).selectByVisibleText(cardExpiry);
			
			WebElement expiryYearDropDown = driver.findElement(By.name("credit_card[year]"));
			expiryYearDropDown.click();
			new Select(expiryYearDropDown).selectByVisibleText(cardYear);
			
			WebElement submitButton = driver.findElement(By.name("commit"));
			continueButton.click();
		}

		
	}

	
	/**
	 * @param urlString - main signup URL
	 * @return - returns the response code in int
	 * @throws IOException - exception
	 */
	public int getResponseCode(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection huc = (HttpURLConnection)url.openConnection();
		huc.setRequestMethod("GET");
		huc.connect();

		return huc.getResponseCode();
		
	}

}
