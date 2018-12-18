package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;

public class SlackWebhookController {
	
	private Stage dialogStage;
	private MainApp mainApp;
	
	@FXML
	private Button testWebHook;
	@FXML
	private Label msgStatus;
	@FXML
	private Label msgError;
	@FXML
	private TextField txtWebhook;
	private String WebhookUrl;

	public static void main(String[] args) {

	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void testWebHook(ActionEvent event) {
		if (txtWebhook.getText().length() > 0 && txtWebhook.getText().contains("https://hooks.slack.com/services")) {
			WebhookUrl = txtWebhook.getText().toString();
			
			SlackApi api = new SlackApi(WebhookUrl);
			api.call(new SlackMessage("SupremeAIO", "Testing webhook"));
			
			msgStatus.setVisible(true);
			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), msgStatus);
	        fadeTransition.setFromValue(2.0);
	        fadeTransition.setToValue(0.0);
	        fadeTransition.setCycleCount(1);
	        fadeTransition.setAutoReverse(false);
	        fadeTransition.play();
		} else {
			msgError.setVisible(true);
			msgError.setStyle("-fx-text-fill: #E40000;\r\n" + 
					" 	-fx-font-weight: bold;\r\n" + 
					"  	-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );  ");

			FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), msgError);
	        fadeTransition.setFromValue(2.0);
	        fadeTransition.setToValue(0.0);
	        fadeTransition.setCycleCount(1);
	        fadeTransition.setAutoReverse(false);
	        fadeTransition.play();
		}
	}
	
	@FXML
	public void initialize() {
		 final ImageView imageView = new ImageView(
					new Image("file:" + System.getProperty("user.dir") + "/resources/images/slackLogo.png"));
		 imageView.setFitHeight(23);
		 imageView.setFitWidth(23);
		 
		 testWebHook.setGraphic(imageView);
		 
		 msgStatus.setAlignment(Pos.CENTER);

	}
}
