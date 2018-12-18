package ch.makery.address.selenium;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import ch.makery.address.view.proxyTesterController;

public class ProxyTest implements Runnable {

	//Proxy variables
	private String url;
	private String ip;
	private String port;
	private String username;
	private String password;
	private int taskNumber;
	
	private proxyTesterController controller;
	
	//Get the references from the main controller and store into variables
	public ProxyTest(proxyTesterController controller, int taskNumber ,String ip, String port, String username, String password)  {
		this.url = url;
		this.controller = controller;
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
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
		System.out.println(ip + port + username + password);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, Integer.parseInt(port)));
		
		HttpURLConnection connection = (HttpURLConnection) new URL("https://www.google.com").openConnection(proxy);
//
//		String uname_pwd = username + ":"+ password;
//		String authString = "Basic " + new sun.misc.BASE64Encoder().encode(uname_pwd.getBytes());
//		connection.setRequestProperty("Proxy-Authorization", authString);
		
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		
		final long startTime = System.currentTimeMillis();
		connection.connect();
		
		if (connection.getResponseCode() != 200) {
			controller.returnTasks().getItems().get(taskNumber - 1).setStatus("Error");
			controller.returnTasks().refresh();
		} else {
			final long endTime = System.currentTimeMillis();
			//System.out.println("Time (ms) : " + (endTime - startTime));
			controller.returnTasks().getItems().get(taskNumber).setSpeed((endTime - startTime) + "ms");
			controller.returnTasks().getItems().get(taskNumber).setStatus("Ok");
			controller.returnTasks().refresh();
		}
					
//		  try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")))
//	        {
//	            String inputLine;
//	            StringBuilder stringBuilder = new StringBuilder();
//	            while ((inputLine = bufferedReader.readLine()) != null)
//	            {
//	                stringBuilder.append(inputLine);
//	            }
//
//	            System.out.println( stringBuilder.toString());
//	        }
		  
		  connection.disconnect();
		
		
//		Authenticator authenticator = new Authenticator() {
//
//			public PasswordAuthentication getPasswordAuthentication() {
//				return (new PasswordAuthentication("user", "password".toCharArray()));
//			}
//		};
//		Authenticator.setDefault(authenticator);
	}
	
	
}
