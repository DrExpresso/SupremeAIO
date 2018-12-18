package ch.makery.address.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.controlsfx.control.ToggleSwitch;

import ch.makery.address.MainApp;
import ch.makery.address.model.ENUMstatus;
import ch.makery.address.model.SupremeTask;
import ch.makery.address.model.keywordInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InstoreRegistrationController {
	
	private Stage dialogStage;
	private MainApp mainApp;
	private SupremeBotOverviewController botController;
	public List<Thread> taskThreads = new ArrayList<Thread>();
	
	@FXML
	private ComboBox<String> cboRegion;
	private ObservableList<String> ObsRegionList = FXCollections.observableArrayList("UK");
	@FXML
	private ComboBox<String> cboProfiles;
	@FXML
	private ToggleSwitch tglAutostart;
	@FXML
	private Button btnCreateTask;
	@FXML
	private TextField txtTasks;
	
	public void setDialogStage(Stage dialogStage, MainApp mainApp, SupremeBotOverviewController controller) {
		this.dialogStage = dialogStage;
		this.mainApp = mainApp;
		
		this.botController = controller;
		cboProfiles.setItems(botController.getProfileList());
		cboProfiles.getSelectionModel().select(0);
	}

	public static void main(String[] args) {}
	
	@FXML
	public void initialize() {
		cboRegion.setItems(ObsRegionList);
		cboRegion.getSelectionModel().select(0);
	}
	
	@FXML
	public void createTaskButton(ActionEvent event) {
		 //Task ID For each browser
		 Integer taskCounter = 1;
		
		 //Region for checkout
		 String RegionID = cboRegion.getSelectionModel().getSelectedItem();
		 //Profile for checkout
		 String ProfileID = cboProfiles.getSelectionModel().getSelectedItem();
		 // No of tasks
		 Integer taskNumberID = Integer.parseInt(txtTasks.getText());
		 //Autostart at 11AM GTM
		 Boolean autostartID = tglAutostart.isSelected();
		 
		 botController.addButtonToTable();
		 
	
		 
		 keywordInfo.getKeywordInfo().setStartTimer("11:00:00");
		 keywordInfo.getKeywordInfo().setHasRunStarted(true);
		 
		 if (autostartID == true) {		 
			 for (int i = 0; i < taskNumberID; i++) {
				taskCounter = botController.returnTasks().getItems().size()  + 1 ;
				mainApp.getTaskData().add(new SupremeTask(taskCounter.toString(), "Instore Registration UK" , " " , " " , " ", ProfileID , ENUMstatus.Ready.toString() , "Browser"));
				botController.returnTasks().getItems().get(taskCounter - 1).setStatus("Launching at: 11:00:00");
				botController.taskCounter = botController.supremeTask.getItems().size()  + 1;
				
				botController.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task created- [Instore Registration UK , "+ RegionID + ", " + ProfileID  + "]" + "\n");
			 }
		 }
	}
	
	private void alertDialogBuilder(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
