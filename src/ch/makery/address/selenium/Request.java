package ch.makery.address.selenium;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.SupremeBotOverviewController;

public class Request {
	
	//Main bot overview controller
	private final SupremeBotOverviewController controller;
	
	//Printer Writer for log text file, declared globally so other methods can use them
	private PrintWriter printWriter;
	
	//Create global variables to connect to supremenewyork for all methods
	private HttpURLConnection mainConnection;
	
	//Variables
	private final String mainShop = "http://www.supremenewyork.com";
	private final String mobile_stock = "http://www.supremenewyork.com/mobile_stock.json";
	
	private String category = keywordInfo.getKeywordInfo().getCatagory();
	private String keyword = keywordInfo.getKeywordInfo().getKeyword();
	private String size = keywordInfo.getKeywordInfo().getSize();
	private String color = keywordInfo.getKeywordInfo().getColor();
	
	private String keywordProductID;
	private String keyword_style_colour;
	private String keyword_size;

	
	//Get the reference for the main controller and store it in a variable
	public Request(SupremeBotOverviewController controller) {
		this.controller = controller;
	}
	

	public void main(String[] args) throws IOException {
		
		//Ensure status column is update
		controller.statusColumnUpdateRunning();
		
		//Create log file
		this.log_creator();
		
		//Test printer writer logger
		printWriter.println("LOG [TASK: " + "1 - MODE[Requests] - " +  " Time: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "]");
		printWriter.println();
		
		//finds keyword and saves 
		this.mobile_stock_checker();
		
		//Finds the correct colour and variant for the keyword
		this.variant_finder();
		
		//Create POST Request to add the item to the cart
		this.add_to_cart();
	}
	
	public void mobile_stock_checker() throws JSONException, MalformedURLException, IOException {
		
		// Convert to supremnewyork stock url and store as json object
		JSONObject json = new JSONObject(IOUtils.toString(new URL(mobile_stock), Charset.forName("UTF-8")));
		
		JSONObject main = json.getJSONObject("products_and_categories");
		JSONArray categoryItem = main.getJSONArray(category);
		
		
		//Iterate throught the array objects until keyword is found
		for (Object productSearch : categoryItem) {
			if ( ((JSONObject) productSearch).optString("name").contains(keyword)) {
				//If keyword found outputs element object
				keywordProductID = ((JSONObject) productSearch).optString("id").toString();
				controller.statusColumnUpdateItemFound();
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Item Found \n");
			} 
		}
	}
	
	public void variant_finder() throws JSONException, MalformedURLException, IOException {
		
		// Convert to supremnewyork stock url and store as json object
		JSONObject json = new JSONObject(IOUtils.toString(new URL(mainShop + "/shop/" + keywordProductID + ".json"), Charset.forName("UTF-8")));
		
		JSONArray styleJson = json.getJSONArray("styles");
		
		
		//Iterate throught the array objects until style colour is found
		for (Object productSearch : styleJson) {
			if ( ((JSONObject) productSearch).optString("name").contains(color)) {
				//If keyword found outputs element object
				keyword_style_colour = ((JSONObject) productSearch).optString("id").toString();

			} 
		}
		
		for (Object colorSearch: styleJson) {
			  Object sizes = ((JSONObject) colorSearch).get("sizes");
			for (Object djf : ((JSONArray) sizes)) {
				if (((JSONObject) djf).optString("name").contains(size)) {
					keyword_size = ((JSONObject) djf).optString("id").toString();
				}			
			}
		}
	
		//Console and Status update
		controller.statusColumnUpdateFetchingVariants();
		controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Fetching variants \n");
	}

		
	public void add_to_cart() throws IOException {

		URL cartPost = new URL("http://www.supremenewyork.com/shop/" + keywordProductID + "/add");

		// Create POST Request
		mainConnection = (HttpURLConnection) cartPost.openConnection();
		mainConnection.setReadTimeout(5000);
		
		//Attach POST Paramters (sie and style)
		mainConnection.setRequestMethod("POST");
		mainConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		mainConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
		mainConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

		
		//Post Variables
		mainConnection.setRequestProperty("charset", "utf-8");
		mainConnection.setRequestProperty("style", keyword_style_colour);
		mainConnection.setRequestProperty("size", keyword_size);
		mainConnection.setRequestProperty("commit", "add+to+basket");
		
		mainConnection.connect();
		
		//Get Page Source and print it
		BufferedReader r = new BufferedReader(new InputStreamReader(mainConnection.getInputStream(), Charset.forName("UTF-8")));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
		    sb.append(line);
		}
		
		System.out.println(sb.toString());

		//Check if add to cart was successful, Status code 200 = OK
		if(mainConnection.getResponseCode()>=200) {
			System.out.println(mainConnection.getResponseCode());
			
			//Console and Status update
			controller.statusColumnUpdateAddingToCart();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Adding to cart \n");
		}
		
		try {
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public void log_creator() throws IOException {
		//Create Log File
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + "/Log_Task_" + "1" + ".txt")) {
			file.flush();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Successfully created log file \n");
		}
				
		//Start the print writer to Log to the file
		FileWriter rawLogOutput = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/Log_Task_1.txt");
		printWriter = new PrintWriter(rawLogOutput);
	}
}
