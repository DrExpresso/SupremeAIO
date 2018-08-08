package ch.makery.address.view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class reCaptchaController {

	@FXML
	private WebView browser;
	private Stage dialogStage;
	private WebEngine webEngine;

	
	public static void main(String[] args) {}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	
	public void initialize() {
		webEngine = browser.getEngine();
		File f = new File(System.getProperty("user.dir")+ "/resources/html/" + "reCaptcha.html");
		
		//Preferences
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		webEngine.setJavaScriptEnabled(true);
		webEngine.setUserAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		webEngine.load(f.toURI().toString());
	}
	
	@FXML
	public void googleSignIn(ActionEvent a) {
		webEngine.executeScript("window.location = \"https://accounts.google.com/ServiceLogin/signinchooser?passive=true&uilel=3&hl=en-GB&continue=https%3A%2F%2Fwww.youtube.com%2Fsignin%3Fnext%3D%252F%26action_handle_signin%3Dtrue%26hl%3Den-GB%26app%3Ddesktop&service=youtube&flowName=GlifWebSignIn&flowEntry=ServiceLogin\";");
		webEngine.setJavaScriptEnabled(true);
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
	}
	
	@FXML
	public void reCaptchaSolver(ActionEvent a) {
		File f = new File(System.getProperty("user.dir")+ "/resources/html/" + "reCaptcha.html");
		webEngine.load(f.toURI().toString());
		
		
	}
	
	@FXML
	private void handleCloseWindow(ActionEvent e) {
		((Node)(e.getSource())).getScene().getWindow().hide();
	}
	
	
	
	

}
