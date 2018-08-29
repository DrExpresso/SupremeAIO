package ch.makery.address.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.makery.address.MainApp;
import ch.makery.address.model.ENUMstatus;
import ch.makery.address.model.Person;
import ch.makery.address.model.SupremeTask;
import ch.makery.address.model.keywordInfo;
import ch.makery.address.selenium.MyThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SupremeBotOverviewController {

	@FXML
	private TextField keywords;
	@FXML
	private TextField checkoutDelay;
	@FXML
	private TextField noOfTasks;
	@FXML
	private TextField txtProxy;
	@FXML
	private Button createProfile;
	@FXML
	private Button startTasks;
	@FXML
	private TextArea console;
	@FXML
	private ComboBox<String> sizes;
	@FXML
	private ComboBox<String> catogry;
	@FXML
	private ComboBox<String> profiles;
	@FXML
	private ComboBox<String> colour;
	@FXML
	private ComboBox<String> modes;
	@FXML
	private TableColumn<Person, String> ID;
	@FXML
	private TableColumn<Person, String> item;
	@FXML
	private TableView<SupremeTask> supremeTask;
	@FXML
	private TableColumn<SupremeTask, String> idColumn;
	@FXML
	public TableColumn<SupremeTask, String> itemColumn;
	@FXML
	private TableColumn<SupremeTask, String> billingColumn;
	@FXML
	private TableColumn<SupremeTask, String> proxyColumn;
	@FXML
	private TableColumn<SupremeTask, String> modeColumn;
	@FXML
	private TableColumn<SupremeTask, String> statusColumn;
	@FXML
	private RadioButton autocheck;

	// Reference to the main application.
	private MainApp mainApp;
	private MyThread thread;
	private ENUMstatus enumstatus;
	private ObservableList<String> profileList = FXCollections.observableArrayList();
	
	
	//Option Values
	private ObservableList<String> sizeList = FXCollections.observableArrayList("Small", "Medium", "Large", "XLarge", "---------", "onesize",
			"---------", "30", "32", "34", "36", "38", "40");

	private ObservableList<String> statusList = FXCollections.observableArrayList("new", "jackets", "shirts", "tops/ sweaters",
			"sweatshirts", "pants", "shorts", "t-shirts", "hats", "bags", "accessories", "skate");

	private ObservableList<String> modeList = FXCollections.observableArrayList("Browser", "Requests");

	private ObservableList<String> colourList = FXCollections.observableArrayList("Red", "Orange", "Yellow", "Green", "Cyan",
			"Blue", "Indigo", "Violet", "Purple", "Magenta", "Pink", "Brown", "White", "Grey", "Black");

	private ObservableList<String> proxyList = FXCollections.observableArrayList("LocalHost", "Random");

	// Variables
	private Integer taskCounter = 1;
	public String st[] = new String[100];
	public int noOfTasksID;
	
	
	private SupremeBotOverviewController passableController;
	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 * @param botController 
	 */
	public void setMainApp(MainApp mainApp, SupremeBotOverviewController botController) {
		this.passableController = botController;
		this.mainApp = mainApp;
		supremeTask.setItems(mainApp.getTaskData());
		supremeTask.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		supremeTask.setPlaceholder(new Label(""));
	}
	
	public ObservableList<String> getProfileList(){
		return profileList;
	}

	public TableView<SupremeTask> returnTasks() {
		return supremeTask;
	}

	public TextField getKeyword() {
		return keywords;
	}

	public TableColumn<SupremeTask, String> returnStatusColumn() {
		return statusColumn;
	}
	
	public TextArea getConsole() {
		return console;
	}
	
	public ComboBox<String> getCboProfiles(){
		return profiles;
	}

	@FXML
	private void handleEditPerson() {
		mainApp.showProfileCreator();
	}

	@FXML
	private void handleRecaptchaWindow(ActionEvent action) {
		//Open captcha Window
		mainApp.showRecaptchaWindow();
	}
	
	@FXML
	private void handleKeywordWindow(ActionEvent action) throws FileNotFoundException {
		//Open Keyword Window
		mainApp.keywordDialog();
	}
	
	@FXML
	private void handleStartTimerDialog(ActionEvent action) {
		//Open Start Timer dialog
		mainApp.timerDialog();
	}
	
	@FXML
	private void handleCheckoutDelayDialog(ActionEvent action) {
		//Open checkout delay dialog
		mainApp.checkoutDelayDialog();
	}
	
	public void consoleWriter(String temp) {
		console.appendText(temp);
	}
	
	

	@FXML
	private void handleExitButton(ActionEvent actionEvent) {
		Node source = (Node) actionEvent.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void handleCloseWindow() {
		Platform.exit();
	}
	
	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	public void initialize() {
		try {
			//Checking Folder resources/json for profiles and adding them to the Combo Boxes 
			try {
				File folder = new File(System.getProperty("user.dir")+ "/resources/json/");
				File[] listOfFiles = folder.listFiles();

				for (File file : listOfFiles) {
				    if (file.isFile()) {
				        profileList.add(file.getName().replace(".json", ""));
				    }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
			itemColumn.setCellValueFactory(cellData -> cellData.getValue().getIemProperty());
			billingColumn.setCellValueFactory(cellData -> cellData.getValue().getBillingProperty());
			proxyColumn.setCellValueFactory(cellData -> cellData.getValue().getProxyProperty());
			modeColumn.setCellValueFactory(cellData -> cellData.getValue().getModeProperty());
			statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
		
			profiles.setItems(profileList);
			profiles.getSelectionModel().select(0);
			
			sizes.setItems(sizeList);
			sizes.getSelectionModel().select(0);
			
			modes.setItems(modeList);
			modes.getSelectionModel().select(0);

			catogry.setItems(statusList);
			catogry.getSelectionModel().select(0);

			colour.setItems(colourList);
			colour.getSelectionModel().select(0);
			
			this.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Initialized Bot \n");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
			    
			try {
				mainApp.errorStackTraceDialog("Stack Trace Error: See Log", exceptionText);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	
	@FXML
	public void createTask(ActionEvent event) throws InterruptedException, IOException, ParseException {
		boolean startTimer = keywordInfo.getKeywordInfo().getHasRunStarted();
		
		
		//Start threads with selenium or requests
		thread = new MyThread(passableController);
		thread.main(null);
		
		//Change column status to 'running'
		if (startTimer == true) {
			statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimerProperty());
			statusColumn.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
			supremeTask.refresh();
		} else if (startTimer == false) {
			statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusRunningProperty());
			statusColumn.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
			supremeTask.refresh();
		}
	}

	
	/*
	 * Still needs fixing, Function needs any active browsers currently open and set the status label as idle.
	 * */
	@FXML
	public void stopTasks() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
		statusColumn.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		supremeTask.refresh();
		
		Thread.currentThread().interrupt();
	}
	
	
	@FXML
	public void clearAllTasks(ActionEvent event) {
		supremeTask.getItems().clear();
		supremeTask.setPlaceholder(new Label(""));
		taskCounter = 1;
	}
	
	//Status Column Updates
	public void statusColumnUpdateRunning() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusRunningProperty());
		statusColumn.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateItemFound() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getFoundItemProperty());
		statusColumn.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateItemNotFound() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getItemNotFoundProperty());
		statusColumn.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateFetchingVariants() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getFetchingVariantsProperty());
		statusColumn.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateAddingToCart() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getAddingToCartProperty());
		statusColumn.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateCheckingOut() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getCheckedOutProperty());
		statusColumn.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateRecaptcha() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getRecaptchaTokenProperty());
		statusColumn.setStyle("-fx-text-fill: #005cf2; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	public void statusColumnUpdateCheckedOut() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getCheckedOutProperty());
		statusColumn.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
	

	
	//Themes
	@FXML
	private void toggleDarkTheme() {
		Scene loader = mainApp.main; //Loads the object into the scene so it can be accessed from this class
		loader.getStylesheets().clear();
		loader.getStylesheets().add(getClass().getResource("/css/DarkTheme.css").toExternalForm());
		
		Scene profile = mainApp.profileCreator; //Loads the object into the scene so it can be accessed from this class
		
		profile.getStylesheets().add(getClass().getResource("/css/DarkTheme.css").toExternalForm());

	}
	

	@FXML
	private void toggleClearTheme() {
		Scene loader = mainApp.main; //Loads the object into the scene so it can be accessed from this class
		loader.getStylesheets().clear();
		loader.getStylesheets().add(getClass().getResource("/css/ClearTheme.css").toExternalForm());
	}

	
	//Fetches all the info typed into left panel and sets in the keywordInfo Model. 
	@FXML
	public void fetchCatogory(ActionEvent action) {
		if (keywords.getText().toString() == null) {
			this.alertDialogBuilder("Information Dialog", null, "Input Keyword");
		} else {
				
			// Fetch keywords for size input	
			String sizeID = sizes.getSelectionModel().getSelectedItem().toString();
			keywordInfo.getKeywordInfo().setSize(sizeID);
	
			// Fetch keywords for input field
			String keywordsID = keywords.getText().toString();
			keywordInfo.getKeywordInfo().setKeyword(keywordsID);
	
			// Fetch selected catogory on the bot
			String catagoryID = catogry.getValue();
			keywordInfo.getKeywordInfo().setCatagory(catagoryID);
	
			// Fetch the select color
			String colourID = colour.getValue();
			keywordInfo.getKeywordInfo().setColor(colourID);
	
			String proxyID = txtProxy.getText().toString();
			
			String profileID = profiles.getSelectionModel().getSelectedItem().toString();
			keywordInfo.getKeywordInfo().setProfileLoader(profileID);
	
			String modeID = modes.getSelectionModel().getSelectedItem().toString();
			keywordInfo.getKeywordInfo().setMode(modeID);
	
		
			noOfTasksID = 0;
			if (noOfTasks.getText().isEmpty()) {
				noOfTasksID = 0;
			} else {
				noOfTasksID = Integer.parseInt(noOfTasks.getText());
			}
	
			//Add task to table
			for (int i = 0; i < noOfTasksID; i++) {
				mainApp.getTaskData().add(new SupremeTask(taskCounter.toString(), keywordsID, profileID, proxyID,
						ENUMstatus.Ready.toString(), modeID));
				statusColumn.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
				taskCounter = supremeTask.getItems().size() + 1;
				
				this.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task created - [" + sizeID + ", " + keywordsID + ", "  + colourID + "]" + "\n");
		}
		
		//Sets amount of tasks in model	
		keywordInfo.getKeywordInfo().setTasks(supremeTask.getItems().size());
		}
	}
	

	//Custom alert dialog for errors
	private void alertDialogBuilder(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	
}
