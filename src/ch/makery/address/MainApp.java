package ch.makery.address;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.makery.address.model.SupremeTask;
import ch.makery.address.model.keywordInfo;
import ch.makery.address.view.ImageScrapperController;
import ch.makery.address.view.ProfileCreatorController;
import ch.makery.address.view.SupremeBotOverviewController;
import ch.makery.address.view.keywordController;
import ch.makery.address.view.reCaptchaController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	public Scene main;
	public Scene profileCreator;
	public Stage dialogue;
	private Stage primaryStage;
	private ObservableList<SupremeTask> taskData = FXCollections.observableArrayList();
	
	private SupremeBotOverviewController botController;
	private ProfileCreatorController profileController;
	private reCaptchaController recaptchaController;
	
    private Logger logger = Logger.getLogger(getClass().getName());


	// Gets current Table
	public ObservableList<SupremeTask> getTaskData() {
		return taskData;
	}

	@Override
	public void start(Stage primaryStage) {
		// Main preferences
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SupremeAIO");
		this.primaryStage.getIcons()
				.add(new Image("file:" + System.getProperty("user.dir") + "/resources/images/" + "icon.png"));

		initRootLayout();
	}

	// Main Application
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SupremeBotOverview.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();

			main = new Scene(rootLayout);
			main.getStylesheets().add(getClass().getResource("/css/ClearTheme.css").toExternalForm()); // Add Default
																										// Clear Theme
			primaryStage.setScene(main);
			primaryStage.show();
			primaryStage.setResizable(false);

			botController = loader.getController();
			botController.setMainApp(this, botController);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}


	public void showProfileCreator() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileCreator.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			dialogue = new Stage();
			dialogue.setTitle("Edit Profile");
			dialogue.initModality(Modality.NONE);
			dialogue.initOwner(primaryStage);

			String css = this.getClass().getResource("/css/ProfileTheme.css").toExternalForm();

			profileCreator = new Scene(page);
			dialogue.setScene(profileCreator);
			dialogue.getIcons()
					.add(new Image("file:" + System.getProperty("user.dir") + "/resources/images/" + "edit.png"));
			dialogue.setResizable(false);
			profileCreator.getStylesheets().add(css);
			
			profileController = loader.getController();
			profileController.setDialogStage(dialogue);
			
			profileController.setProfileCreatorController(botController);
		

			dialogue.show();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	public void showRecaptchaWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/reCaptcha.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("ReCaptcha Harvester");
			dialogStage.initModality(Modality.NONE);
			dialogStage.getIcons().add(
					new Image("file:" + System.getProperty("user.dir") + "/resources/images/" + "reCaptchaIcon.png"));
			String css = this.getClass().getResource("/css/ClearTheme.css").toExternalForm();
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(css);

			recaptchaController = loader.getController();
			recaptchaController.setDialogStage(dialogStage);

			dialogStage.setScene(scene);
			dialogStage.show();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);		}
	}

	// Start Timer Dialog box
	public void timerDialog() {
		TextInputDialog dialog = new TextInputDialog("11:00:00");

		// Get the Stage.
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(this.getClass().getResource("/resources/images/timer.png").toString()));

		dialog.setHeaderText(null);
		dialog.setTitle("Tasks start timer");
		dialog.setContentText("Time:");
		dialog.setGraphic(null);
				
		ButtonType resetTimer = new ButtonType("Reset");
		dialog.getDialogPane().getButtonTypes().add(resetTimer);
		
		//Lookup button and reset the hasRunStarted variable to set timer to false
		final Button reset = (Button) dialog.getDialogPane().lookupButton(resetTimer);
		reset.addEventFilter(ActionEvent.ACTION, event -> 
			keywordInfo.getKeywordInfo().setHasRunStarted(false)
		);

		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			keywordInfo.getKeywordInfo().setStartTimer(result.get());
			//Console log update
			botController.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Set tasks start timer: " + result.get() + "\n");
			keywordInfo.getKeywordInfo().setHasRunStarted(true);
		}

	}

	// Checkout delay
	public void checkoutDelayDialog() {
		TextInputDialog dialog = new TextInputDialog("0000");

		// Get the Stage.
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(this.getClass().getResource("/resources/images/delay.png").toString()));

		dialog.setHeaderText(null);
		dialog.setTitle("Checkout Delay");
		dialog.setContentText("Delay:");
		dialog.setGraphic(null);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			keywordInfo.getKeywordInfo().setCheckoutDelay(Integer.parseInt(result.get()));
			botController.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Set checkout delay: " + result.get() + "\n");
		}

	}
	
	public void showAboutWindow() {
		String information = "Version: 1.4.0.0";
		String updates = 
				  "\n+ Added start and top buttons for individual tasks "
				+ "\n+ Added action column "
				+ "\n+ New About window dialog showing updates and version info "
				+ "\n+ Delete a row by double clicking "
				+ "\n+ Fixed log output for each task "
				+ "\n+ Added slider for theme switcher";
				
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Information");
		alert.setContentText(information);
		
		ButtonType resetTimer = new ButtonType("Twitter");
		alert.getDialogPane().getButtonTypes().add(resetTimer);
		
		Button resetTimerBtn = (Button) alert.getDialogPane().lookupButton(resetTimer);
		resetTimerBtn.addEventFilter(ActionEvent.ACTION, event -> 
			getHostServices().showDocument("https://twitter.com/DrExpresso")
		);
		
		ButtonType github = new ButtonType("Github");
		alert.getDialogPane().getButtonTypes().add(github);
		
		Button githutBtn = (Button) alert.getDialogPane().lookupButton(github);
		githutBtn.addEventFilter(ActionEvent.ACTION, event -> 
			getHostServices().showDocument("https://github.com/DrExpresso/SupremeAIO")
		);
		
		Label label = new Label("Updates:");

		TextArea textArea = new TextArea(updates);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();

	}
	
	public void showImageScraperDialog() throws FileNotFoundException {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ImageScraperOverview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Image Scraper");
			dialogStage.initModality(Modality.NONE);
			String css = this.getClass().getResource("/css/ClearTheme.css").toExternalForm();
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(css);

			ImageScrapperController controller = loader.getController();
			controller.setMainApp(this);

			
			
			dialogStage.setScene(scene);
			dialogStage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();
			    
			this.errorStackTraceDialog("Stacktrace error see log", exceptionText);
			    
		}
	}
	
	//Keyword help
	public void keywordDialog() throws FileNotFoundException {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/keywordOverview.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Keywords");
			dialogStage.initModality(Modality.NONE);
			dialogStage.getIcons().add(
					new Image("file:" + System.getProperty("user.dir") + "/resources/images/" + "keyword.ico"));
			String css = this.getClass().getResource("/css/ClearTheme.css").toExternalForm();
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(css);

			keywordController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);

			
			
			dialogStage.setScene(scene);
			dialogStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();
			    
			this.errorStackTraceDialog("Stacktrace error see log", exceptionText);
			    
		}
	}

	public void errorStackTraceDialog(String Error, String stackTraceElements) throws FileNotFoundException {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Look, an Exception Dialog");
		alert.setContentText(Error);

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(stackTraceElements);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
