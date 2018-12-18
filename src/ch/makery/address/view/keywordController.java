package ch.makery.address.view;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import ch.makery.address.MainApp;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class keywordController {
	
	@FXML
	private WebView browser;
	private Stage dialogStage;
	private WebEngine webEngine;
	
	//Callback to print stack trace information
	private MainApp mainApp;
	
	@FXML
	private TextField addressBar;

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void initialize() {
		webEngine = browser.getEngine();
		
		//Preferences
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		webEngine.setJavaScriptEnabled(true);
		webEngine.setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

		Worker<Void> worker = webEngine.getLoadWorker();
		
		addressBar.setText("https://www.supremecommunity.com/season/fall-winter2018/droplists/");
		
		//Final URL LOAD
		webEngine.load("https://www.supremecommunity.com/season/fall-winter2018/droplists/");
		
	}
	
	@FXML
	public void goBack() throws FileNotFoundException {
		try {
	        webEngine.getHistory().go(-1);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    
		    StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
		    
		    mainApp.errorStackTraceDialog("Cannot go back", exceptionText);
		    
		} 
	}

	@FXML
	public void goForward() throws FileNotFoundException {
		try {
	        webEngine.getHistory().go(1);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    
		    StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
		    
		    mainApp.errorStackTraceDialog("Cannot go foward", exceptionText);
		    
		} 
	}

}
