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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.makery.address.view.SupremeBotOverviewController;

public class Request implements Runnable {
	
	//Main bot overview controller
	private final SupremeBotOverviewController controller;
	
	//Task variables
	private int taskNumber;
	private String keyword;
	private String size;
	private String category;
	private String color;
	private String profileLoader;
	
	//Printer Writer for log text file, declared globally so other methods can use them
	private PrintWriter printWriter;
	
	//Create global variables to connect to supremenewyork for all methods
	private HttpURLConnection mainConnection;
	
	//Variables
	private final String mainShop = "https://www.supremenewyork.com";
	private final String mobile_stock = "https://www.supremenewyork.com/mobile_stock.json";

	//Variants from product data
	private String keywordProductID;
	private String keyword_style_colour;
	private String keyword_size;

	
	//Get the references from the main controller and store into variables
	public Request(SupremeBotOverviewController controller, int taskNumber, String keyword, String size, String category, String color, String profileLoader) {
		this.controller = controller;
		this.taskNumber = taskNumber;
		this.keyword = keyword;
		this.size = size;
		this.category = category;
		this.color = color;
		this.profileLoader = profileLoader;
	}
	
	//Run this thread using Runnable interface
	@Override
	public void run() {
		try {
			this.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}		
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
				System.out.println(keywordProductID);
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

		URL cartPost = new URL("https://www.supremenewyork.com/shop/" + keywordProductID + "/add");

		// Create POST Request
		mainConnection = (HttpURLConnection) cartPost.openConnection();
		mainConnection.setReadTimeout(5000);
		
		//Attach generic POST Paramters
		mainConnection.setRequestMethod("POST");
		mainConnection.setRequestProperty("authority", "www.supremenewyork.com");
		mainConnection.setRequestProperty("path", "/shop/"+ keywordProductID +"/add");
		mainConnection.setRequestProperty("scheme", "https");
		mainConnection.setRequestProperty("accept", "*/*;q=0.5, text/javascript, application/javascript, application/ecmascript, application/x-ecmascript");
		mainConnection.setRequestProperty("accept-encoding", "gzip, deflate, br");
		mainConnection.setRequestProperty("accept-language", "en-US,en;q=0.9");
		mainConnection.setRequestProperty("content-length","58");
		mainConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		mainConnection.setRequestProperty("cookie","lastid=1538303546651; pooky=76309c1e-a8c1-46a2-97e6-64a1fed1cf74; pooky_ok=eyJ0b2hydV9vayI6IGZhbHNlLCAiZW5hYmxlZCI6IHRydWUsICJtc19kcmFnIjoib24ifQ==; mp_c5c3c493b693d7f413d219e72ab974b2_mixpanel=%7B%22distinct_id%22%3A%20%221662a0958c941a-0863b9a9f86ad7-8383268-1fa400-1662a0958cabcf%22%2C%22Store%20Location%22%3A%20%22EU%20Web%22%2C%22Platform%22%3A%20%22Web%22%2C%22%24initial_referrer%22%3A%20%22%24direct%22%2C%22%24initial_referring_domain%22%3A%20%22%24direct%22%7D; __utma=74692624.153298685.1538303548.1538303548.1538303548.1; __utmc=74692624; __utmz=74692624.1538303548.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; request_method=DELETE; cart=0+items--; pure_cart=%7B%22cookie%22%3A%220+items--%22%2C%22total%22%3A%22%C2%A30%22%7D; _supreme_sess=UHRqVk8wT1lFZXQ4dmtkTFhDTW9ucXZFdjhESjdIRk9BR1pCQS9RZWw0dTNsbUt4aFlrU1Avemp5Y1RsNlhiK0lGelZzQlRSOW4rdHhDdFlaSnJzenZYVmVCTys3eFcyd29EZVE0Z0x0ZW9pazBYU2hENjlpRlE5Z0pDTEtrSTlmcVdHakFxT1VFd2NWWG45R3lFWWpsWmZrYWduZThNcTJFcDNrUlFaODBXQW82TC96NmdtK0w2SnVFRHJaeXpSQnNaZVpHem1hVVBJQTBOa1NrckxLUEZJa2Vhc1lHMGNHVm50MnNvTjhhcnpDWU9GRnJ4QlZuWCtCZXRHN3QrSkVsazVXWlhpOGlzQ01LelJXZi9rZWV2UCtSZExreVBrYTMvRENieDRvYWxoTEZ3Q2xmQXFCTVNIUWhPZHZDZ1VKTGl5Y2JOQ09IK0IrUFQ1RHdKLzdXRHJLWXpBWjlLNFVMM1hhWVZOcDZwM3IwSlppOTRlamVTeCtnb1dOUkdWZE1vSWZCY05pOGs5dU5aODZNUVlmMWNrNXYrd3NveGNQS1M0WTZMYU5sM0x0VTh2YzdwU3pUSHI4aW1HTVNSRy0tOVhHTUh4SFh3VU04QlphZlh1T1F5dz09--bdb7b7d1039f76e9fed49dc1da49f113b131068e; __utmb=74692624.7.10.1538303548; pooky_mouse_drag=ondrag=623e3ffdcdbbbd7e81c934d0a16d3656da0de48c072e6e5a123222ddcc2be39b6caec83e18b7e6c4c554513eac970272; pooky_pro=796387646720e564be149848da656a995156ca9324bff1af72996a8539437b75c4dff91f34149a7c787add55cc0b83a600154a194370a80b40107200272f837f0f33028deb3fabcb2ceb11dde56ada76ebc998be8fdf25883b742af1d6e817bd8d314066e21ac3a52da182a5b6b4edc4ca06f19312d7449a90de99245ec3a033");
		mainConnection.setRequestProperty("origin", "https://www.supremenewyork.com");
		mainConnection.setRequestProperty("referer", "https://www.supremenewyork.com/shop/jackets/qfpku5g0o/ulxwta3b9");
		mainConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		mainConnection.setRequestProperty("x-csrf-token", "AomF2imn6PLKM3D2iB0xKIu/zudzQDsaQA1x4/ODGrg/dz0qYm9JvWGieyzvOXZ1pMHqFrwyn/Q/q2JkHTzKFA==");
		mainConnection.setRequestProperty("x-requested-with", "XMLHttpRequest");
		
		//Post Variables
		mainConnection.setRequestProperty("charset", "utf-8");
		mainConnection.setRequestProperty("style", keyword_style_colour);
		mainConnection.setRequestProperty("size", keyword_size);
		mainConnection.setRequestProperty("commit", "add+to+basket");
		
		mainConnection.connect();
		
		getCookiesFromHTTPConnection();
		
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
	
	public void getCookiesFromHTTPConnection() {
		
		Map<String, List<String>> headerFields = mainConnection.getHeaderFields();
		
		Set<String> headerFieldSet = headerFields.keySet();
		Iterator<String> headerFieldsIter = headerFieldSet.iterator();
		
		while (headerFieldsIter.hasNext()) {
			
			String headerFieldKey = headerFieldsIter.next();
			
			if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
				
				List<String> headerFieldValue = headerFields.get(headerFieldKey);
				
				for (String headerValue: headerFieldValue) {
					System.out.println("Cookie Found...");
					
					String[] fields = headerValue.split("\\s");
					
					String cookieValue = fields[0];
					String expires = null;
					String path = null;
					String domain = null;
					boolean secure = false;
					
					// Parse each field
					for (int j = 1; j < fields.length; j++) {
						if("secure".equalsIgnoreCase(fields[j])) {
							secure = true;
						}
						else if (fields[j].indexOf('=') > 0) {
							String[] f = fields[j].split("=");
							if("expires".equalsIgnoreCase(f[0])) {
								expires = f[1];
							}
							else if("domain".equalsIgnoreCase(f[0])) {
								domain = f[1];
							} 
							else if ("path".equalsIgnoreCase(f[0])) {
								path = f[1];
							}
						}
					}
					
					System.out.println("cookieValue: " + cookieValue);
					System.out.println("expires: " + expires);
					System.out.println("path: " + path);
					System.out.println("domain: " + domain);
					System.out.println("secure: " + secure);
				}
				
			}
			
		}
	}
}
