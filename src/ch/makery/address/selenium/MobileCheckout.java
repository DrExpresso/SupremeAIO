/**
 * 
 */
package ch.makery.address.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;

/**
 * @author DrExpresso
 *
 */
public class MobileCheckout implements Runnable {
	
	//Bot controller
	private SupremeBotOverviewController controller;
	
	//Task variables
	private int taskNumber;
	private String keyword;
	private String size;
	private String category;
	private String color;
	private String profileLoader;
	
	//Mobile user-agent (IPHONE IOS 12- SAFARI BROWSER 12)
	private String userAgent = "--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 12_0_1 like Mac OS X) AppleWebKit/605.1.15 "
								+ "(KHTML, like Gecko) Version/12.0 Mobile/15E148 Safari/604.1";
	//URLS
	private String mainURL = "https://www.supremenewyork.com/mobile/";
	private String checkoutURL = "https://www.supremenewyork.com/mobile/#checkout";
	
	//Browser variables
	private ChromeDriver driver;
	private int retryCounter = 10;
	private int checkoutDelay = keywordInfo.getKeywordInfo().getCheckoutDelay();
	
	//Console variables
	private PrintWriter printWriter;
	
	public MobileCheckout(SupremeBotOverviewController controller, int taskNumber, String profileLoader, String keyword, String size, String category, String color) {
		this.controller = controller;
		this.taskNumber = taskNumber;
		this.profileLoader = profileLoader;
		this.keyword = keyword;
		this.size = size;
		this.category = category;
		this.color = color;
	}
	
	@Override
	public void run() {
		this.main(null);
	}
	
	/**
	 * Mobile checkout class for supreme site
	 * @param args
	 */
	public void main(String[] args) {
		this.startTask();
	}
	
	public void startTask() {
		/*****************************************************
		 * Headless property -- Uncomment to use headless browser ChromeOptions headless
		 * = new ChromeOptions(); headless.addArguments("--headless"); WebDriver driver
		 * = new ChromeDriver(headless);
		 *****************************************************/
		
		//Setting Browser Mode
		controller.setMobileMode(this);
		
		//Ensure status column is update
		controller.statusColumnUpdateRunning();
		
		//Driver initialisation 
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+ "/resources/" +"/chromedriver.exe");
     	
     	
     	//Create a new instance of the Chrome driver and attaches user-agent properties
		ChromeOptions options = new ChromeOptions();
		options.addArguments(userAgent);

     	driver = new ChromeDriver(options);
     	driver.get(mainURL);
  
				
		//Create log file before executing any other task
		try {
			this.log_creator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void log_creator() throws IOException {
		String fileName = "[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) + "] - MOBILE - " + taskNumber +".txt";
		
		//Start the print writer to Log to the file with default message
		try (FileWriter file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + fileName)){
			printWriter = new PrintWriter(file);
			
			printWriter.println("LOG [TASK: " + taskNumber + " -- " +  " Time: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "]");
			printWriter.println("Checkout Mode: MOBILE");	
			printWriter.println("Configuration:   --checkout-delay: " + checkoutDelay + "   --retry-counter: " + retryCounter);	
			printWriter.println();	
			printWriter.close();
		}
	}


}
