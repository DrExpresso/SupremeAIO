package ch.makery.address;

import java.io.IOException;
import ch.makery.address.model.SupremeTask;
import ch.makery.address.view.ProfileCreatorController;
import ch.makery.address.view.SupremeBotOverviewController;
import ch.makery.address.view.reCaptchaController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	public Scene main;
	public Scene profileCreator;
	public Stage dialogue;
	private Stage primaryStage;
	private ObservableList<SupremeTask> taskData = FXCollections.observableArrayList();
	
	//Gets current Table 
	public ObservableList<SupremeTask> getTaskData() {
		return taskData;
	}

	@Override
	public void start(Stage primaryStage) {
		//Main preferences
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SupremeAIO");
		this.primaryStage.getIcons().add(new Image("file:" + System.getProperty("user.dir")+ "/resources/images/" + "icon.png"));

		initRootLayout();
	}

	//Main Application
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SupremeBotOverview.fxml"));
			AnchorPane rootLayout = (AnchorPane) loader.load();
			
			main = new Scene(rootLayout);
			main.getStylesheets().add(getClass().getResource("/css/ClearTheme.css").toExternalForm()); //Add Default Clear Theme
			primaryStage.setScene(main);
			primaryStage.show();
			primaryStage.setResizable(false);

			SupremeBotOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
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
			dialogue.getIcons().add(new Image("file:" + System.getProperty("user.dir")+ "/resources/images/" + "edit.png"));
			dialogue.setResizable(false);
			profileCreator.getStylesheets().add(css);

			ProfileCreatorController controller = loader.getController();
			controller.setDialogStage(dialogue);

			dialogue.show();
		} catch (IOException e) {
			e.printStackTrace();
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
			dialogStage.getIcons().add(new Image("file:" + System.getProperty("user.dir")+ "/resources/images/" + "reCaptchaIcon.png"));
			String css = this.getClass().getResource("/css/ClearTheme.css").toExternalForm();
	       	dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(css);
			
			reCaptchaController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			
			dialogStage.setScene(scene);
			dialogStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}