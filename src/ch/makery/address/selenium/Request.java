package ch.makery.address.selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ch.makery.address.model.keywordInfo;

public class Request {
	public static String mainShop = "http://www.supremenewyork.com";
	public static String keyword = "Supreme®/Hanes® Tagless Tees (3 Pack)";
	public static String colour = "Black";
	public static String cartBuild;
	public static String hrefs;
	private static String category = keywordInfo.getKeywordInfo().getCatagory();

	/*
	 * UNDER MAINTANCE
	 * 
	 * Class still not fully finished
	 */

	public static void main(String[] args) throws IOException {
		run();
		keywordFinder();
		atc();
		mobile_stock();
	}

	public static void run() throws IOException {

		URL url = new URL("http://www.supremenewyork.com/shop/all/accessories");

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");

		int status = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		System.out.println(content);

		// Terminate connection once checkout process finishes
		in.close();
		con.disconnect();

	}

	public static void keywordFinder() throws IOException {
		// Find keyword URL and store in hrefs
		org.jsoup.nodes.Document doc = Jsoup.connect("http://www.supremenewyork.com/shop/all/accessories").get();

		for (Element asd : doc.select("h1:contains(Supreme®/Hanes® Tagless Tees (3 Pack))")) {
			for (Element el : asd.getElementsByClass("name-link")) {
				hrefs = el.attr("abs:href");
			}
		}
	}

	public static void atc() throws IOException {
		// Find ATC Link
		org.jsoup.nodes.Document page = Jsoup.connect(hrefs).get();
		String addToCart = page.getElementById("cart-addf").attr("action");
		cartBuild = mainShop + addToCart;

		URL cart = new URL(cartBuild);

		// Grab POST Parameters for JSON URL
		URL jsonCart = new URL(cartBuild + ".json");

		// Create POST Request
		HttpURLConnection con1 = (HttpURLConnection) cart.openConnection();

		con1.setRequestMethod("POST");

		System.out.println(con1);

		System.out.println(con1.getResponseCode());
	}

	public static void mobile_stock() throws IOException {
		String sURL = "http://www.supremenewyork.com/mobile_stock.json";

		// Connect to mobile stock dispatcher
		URL url = new URL(sURL);
		URLConnection request = url.openConnection();
		request.connect();

		// Convert to JSON object and find variant
		JsonParser parse = new JsonParser();
		JsonElement root = parse.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonElement rootobj = root.getAsJsonObject().getAsJsonArray("products_and_categories").get(0).getAsJsonObject()
				.getAsJsonArray().get(0);
		System.out.println(rootobj);
	}

}
