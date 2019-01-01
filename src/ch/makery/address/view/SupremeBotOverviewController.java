package ch.makery.address.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.IOUtils;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONObject;

import ch.makery.address.MainApp;
import ch.makery.address.model.ENUMstatus;
import ch.makery.address.model.Person;
import ch.makery.address.model.SupremeTask;
import ch.makery.address.model.keywordInfo;
import ch.makery.address.selenium.InstoreRegistrationSelenium;
import ch.makery.address.selenium.MobileCheckout;
import ch.makery.address.selenium.Request;
import ch.makery.address.selenium.Selenium;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

public class SupremeBotOverviewController {

	@FXML
	private TextField keywords = TextFields.createClearableTextField();
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
	public TableView<SupremeTask> supremeTask;
	@FXML
	private TableColumn<SupremeTask, String> idColumn;
	@FXML
	public TableColumn<SupremeTask, String> itemColumn;
	@FXML
	private TableColumn<SupremeTask, String> colourColumn;
	@FXML
	private TableColumn<SupremeTask, String> categoryColumn;
	@FXML
	private TableColumn<SupremeTask, String> sizeColumn;
	@FXML
	private TableColumn<SupremeTask, String> billingColumn;
	@FXML
	private TableColumn<SupremeTask, String> proxyColumn;
	@FXML
	private TableColumn<SupremeTask, String> modeColumn;
	@FXML
	public TableColumn<SupremeTask, String> statusColumn;
	@FXML
	private TableColumn<SupremeTask, String> actionColumn;
	@FXML
	private RadioButton autocheck;
	@FXML
	public ToggleSwitch tglSwitchGui;

	// Reference to the main application.
	private MainApp mainApp;
	private ENUMstatus enumstatus;
	private ObservableList<String> profileList = FXCollections.observableArrayList();
	
	public List<Thread> threads = new ArrayList<Thread>();
	 
	
	//Option Values
	private ObservableList<String> sizeList = FXCollections.observableArrayList("Small", "Medium", "Large", "XLarge", "---------", "onesize",
			"---------", "30", "32", "34", "36", "38", "40");

	private ObservableList<String> statusList = FXCollections.observableArrayList("all", "Jackets", "shirts", "tops_sweaters",
			"sweatshirts", "pants", "shorts", "t-shirts", "hats", "bags", "accessories", "skate");

	private ObservableList<String> modeList = FXCollections.observableArrayList("Browser", "Mobile", "Requests");

	private ObservableList<String> colourList = FXCollections.observableArrayList("Red", "Orange", "Yellow", "Green", "Cyan",
			"Blue", "Indigo", "Violet", "Purple", "Magenta", "Pink", "Brown", "White", "Grey", "Black", "Light Pink", "Royal", 
			"Burgundy", "Navy", "Camo", "Lime", "Indigo", "Denim", "Cardinal", "Violet", "Ash Grey", "Mustard", "Natural", "Light Pink",
			"Light Blue", "Heather Grey", "Dark Green", "Rust", "Bright Royal", "Khaki", "Slate", "Gold", "Silver", "Light Purple", 
			"Dark Green", "Neon Blue", "Tan", "Burnt Orange");

	private ObservableList<String> proxyList = FXCollections.observableArrayList("LocalHost", "Random");

	// Variables
	public Integer taskCounter = 1;
	public String st[] = new String[100];
	public int noOfTasksID;
	
	
	
	private SupremeBotOverviewController passableController;
	private Selenium browser;
	private MobileCheckout mobileBrowser;

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
	
	public void setBrowserMode(Selenium browser) {
		this.browser = browser;
	}
	
	public void setMobileMode(MobileCheckout mobile) {
		this.mobileBrowser = mobile;
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
	private void handleProxyTester(ActionEvent action) {
		//Open proxy tester Window
		mainApp.showProxyWindow();
	}
	
	@FXML
	private void handleAboutWindow(ActionEvent action) {
		//Open captcha Window
		mainApp.showAboutWindow();
	}
	
	@FXML
	private void handleImageScraper(ActionEvent action) throws FileNotFoundException {
		//Open captcha Window
		mainApp.showImageScraperDialog();
	}
	
	@FXML
	private void handleSlackWebhookDialog(ActionEvent action) throws FileNotFoundException {
		mainApp.SlackWebhookDialog();
	}
	
	@FXML
	private void handleInstoreRegistrationDialog(ActionEvent action) throws FileNotFoundException {
		mainApp.instoreRegistrationDialog();
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
		
			//Check Region of user
			JSONObject json = new JSONObject(IOUtils.toString(new URL("http://ip-api.com/json/?fields=country"), Charset.forName("UTF-8")));
			String countryRegion = json.get("country").toString();
			System.out.println( json.get("country").toString());
			
			idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
			itemColumn.setCellValueFactory(cellData -> cellData.getValue().getIemProperty());
			sizeColumn.setCellValueFactory(cellData -> cellData.getValue().getSizeProperty());
			colourColumn.setCellValueFactory(cellData -> cellData.getValue().getColourProperty());
			categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoryProperty());
			billingColumn.setCellValueFactory(cellData -> cellData.getValue().getBillingProperty());
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
			 // Table cell coloring
	        statusColumn.setCellFactory(new Callback<TableColumn<SupremeTask, String>, TableCell<SupremeTask, String>>() {
	            @Override
	            public TableCell<SupremeTask, String> call(TableColumn<SupremeTask, String> param) {
	                return new TableCell<SupremeTask, String>() {

	                    @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (!isEmpty()) {
	                            // Get fancy and change color based on data
	                            if(item.contains("Ready")) 
	                                this.setTextFill(Color.BLUEVIOLET);
	                            	this.setStyle("-fx-font-weight: bold");
	                            if(item.contains("Running")) 
	                                this.setTextFill(Color.GREEN);
	                            	this.setStyle("-fx-font-weight: bold");
	                            if(item.contains("Launching at: 11:00:00")) 
	                                this.setTextFill(Color.RED);
	                           		this.setStyle("-fx-font-weight: bold");
	                           	if(item.contains("Item Found")) 
		                            this.setTextFill(Color.ORANGE);
	                           	if(item.contains("Item not found")) 
		                            this.setTextFill(Color.RED);
	                           		this.setStyle("-fx-font-weight: bold");
	                           	if(item.contains("Error occurred")) 
			                        this.setTextFill(Color.RED);
		                           	this.setStyle("-fx-font-weight: bold");       
		                        if(item.contains("Website not reachable")) 
				                    this.setTextFill(Color.RED);
			                        this.setStyle("-fx-font-weight: bold");      
			                    if(item.contains("Adding to cart...")) 
					                this.setTextFill(Color.ORANGE);
				                    this.setStyle("-fx-font-weight: bold");     
				                if(item.contains("Checking out")) 
						            this.setTextFill(Color.ORANGE);
					                this.setStyle("-fx-font-weight: bold");     
					            if(item.contains("Captcha required")) 
							        this.setTextFill(Color.rgb(0, 92, 242));
						            this.setStyle("-fx-font-weight: bold");  
						        if(item.contains("Stopped")) 
								    this.setTextFill(Color.RED);
							        this.setStyle("-fx-font-weight: bold");  
	                            setText(item);

	                        }
	                    }

	                };
	            }
	        });
			
			this.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Initialized Bot \n");
			
			//GUI Theme changer via toggle switch
			tglSwitchGui.selectedProperty().addListener((observable, oldValue, newValue) -> {
		        if(newValue) {
		        	Scene loader = mainApp.main; //Loads the object into the scene so it can be accessed from this class
		    		loader.getStylesheets().clear();
		    		loader.getStylesheets().add(getClass().getResource("/css/DarkTheme.css").toExternalForm());		 
		    		if ( mainApp.instoreScene != null ) {
			    		 mainApp.instoreScene.getStylesheets().clear();
			    		 mainApp.instoreScene.getStylesheets().add(getClass().getResource("/css/DarkTheme.css").toExternalForm());		 
		    		} 
		    		if ( mainApp.slackScene != null ) {
			    		 mainApp.slackScene.getStylesheets().clear();
			    		 mainApp.slackScene.getStylesheets().add(getClass().getResource("/css/SlackDark.css").toExternalForm());
		    		}
		    		if ( mainApp.profileCreator != null ) {
		    			 mainApp.profileCreator.getStylesheets().clear();
			    		 mainApp.profileCreator.getStylesheets().add(getClass().getResource("/css/ProfileThemeDark.css").toExternalForm());
		    		}
		    		
		        } else {
		        	Scene loader = mainApp.main; //Loads the object into the scene so it can be accessed from this class
		    		loader.getStylesheets().clear();
		    		loader.getStylesheets().add(getClass().getResource("/css/ClearTheme.css").toExternalForm());
		    		if ( mainApp.instoreScene != null ) {
		    			mainApp.instoreScene.getStylesheets().clear();
		    			mainApp.instoreScene.getStylesheets().add(getClass().getResource("/css/ClearTheme.css").toExternalForm());	
		    		}
		    		if ( mainApp.slackScene != null ) {
		    			mainApp.slackScene.getStylesheets().clear();
		    			mainApp.slackScene.getStylesheets().add(getClass().getResource("/css/Slack.css").toExternalForm());		
		    		}
		    		if ( mainApp.profileCreator != null ) {
		    			 mainApp.profileCreator.getStylesheets().clear();
			    		 mainApp.profileCreator.getStylesheets().add(getClass().getResource("/css/ProfileTheme.css").toExternalForm());
		    		}
		        }
		    });
			
			//Delete selected row
			supremeTask.setRowFactory( tv -> {
			    TableRow<SupremeTask> row = new TableRow<>();
			    
			    //Change cursor over hover
			    row.hoverProperty().addListener((observable) -> {
			        final SupremeTask person = row.getItem();

			        if (row.isHover() && person != null) {
			           row.setCursor(Cursor.HAND);			            
			        }
			    });
			    
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	SupremeTask rowData = row.getItem();
			           
			        	 TextField itemTextField = new TextField(rowData.getIem());
			             TextField categoryTextField =  new TextField(rowData.getCategory());
			             TextField sizeTextField =  new TextField(rowData.getSize());
			             TextField colourTextField =  new TextField(rowData.getColour());
			             ComboBox<String> profiles = new ComboBox<String>();
			             profiles.getItems().addAll(profileList);
			             profiles.getSelectionModel().select(rowData.getBillingProfile());
			         	 ComboBox<String> modes = new ComboBox<String>();
			             modes.getItems().addAll(modeList);
			             modes.getSelectionModel().select(rowData.getMode());
			           

			             GridPane grid = new GridPane();
			             grid.setHgap(10);
			             grid.setVgap(10);
			             grid.setPadding(new Insets(16));
			             grid.setStyle(
			             		"  -fx-body-color: linear-gradient(to bottom, #FAFAFA, #EAEAEA);\r\n" + 
			             		"  -fx-background-color: \r\n" + 
			             		"       -fx-outer-border, \r\n" + 
			             		"       -fx-body-color;\r\n" + 
			             		"  -fx-background-insets: 0, 5;\r\n" + 
			             		"  -fx-background-radius: 5px, 0px;\r\n" + 
			             		"  -fx-background-repeat: no-repeat;\r\n" + 
			             		"  -fx-background-position: center;"
			             		+ " -fx-border-radius: 20;"
			             		+ " -fx-background-radius: 30;  -fx-padding: 40 60 40 60;" );
			             			             

			             final ImageView imageView = new ImageView(
			 					new Image("file:" + System.getProperty("user.dir") + "/resources/images/editTasks.png"));
			             imageView.setFitHeight(37);
			             imageView.setFitWidth(125);
			             imageView.setPreserveRatio(true);
			             
			             HBox hbox = new HBox(imageView);
			             hbox.setAlignment(Pos.CENTER_LEFT);
			             hbox.setStyle("-fx-padding: 0 0 0 60;");
			             GridPane.setColumnSpan(hbox, GridPane.REMAINING);
			             
			             ColumnConstraints col1Constraints = new ColumnConstraints();
			             col1Constraints.setPercentWidth(100);

			             grid.addRow(0,  hbox);
			             grid.addRow(1, new Label("Category:"), itemTextField);
			             grid.addRow(2, new Label("Category:"), categoryTextField);
			             grid.addRow(3, new Label("Size:"), sizeTextField);
			             grid.addRow(4, new Label("Colour:"), colourTextField);
			             grid.addRow(5, new Label("Billing profile:"), profiles);
			             grid.addRow(6, new Label("Mode:"), modes);		
			    

			             Button okButton = new Button("OK");
			             Button cancelButton = new Button("Cancel");
			             
			             HBox buttons = new HBox(okButton, cancelButton);
			             buttons.setAlignment(Pos.CENTER);
			             buttons.setSpacing(20);

			             grid.add(buttons, 0, 7, 2, 1);
			             
			             ColumnConstraints leftCol = new ColumnConstraints();
			             leftCol.setHgrow(Priority.NEVER);
			             leftCol.setHalignment(HPos.RIGHT);
			             ColumnConstraints rightCol = new ColumnConstraints();
			             rightCol.setHgrow(Priority.SOMETIMES);
			             grid.getColumnConstraints().addAll(leftCol, rightCol);
			             GridPane.setHalignment(okButton, HPos.CENTER);
			             
			             grid.getColumnConstraints().add(col1Constraints);

			             Scene scene = new Scene(grid);
			             Stage stage = new Stage();

			             okButton.setOnAction(e -> System.out.println("sd"));
			             
			            
			             cancelButton.setOnAction(e -> stage.hide() );
			             itemTextField.setOnAction(e -> stage.hide());
			             categoryTextField.setOnAction(e -> stage.hide());
			             sizeTextField.setOnAction(e -> stage.hide());
			             colourTextField.setOnAction(e -> stage.hide());

			             stage.initModality(Modality.APPLICATION_MODAL);
			             stage.initOwner(mainApp.getPrimaryStage());
			             stage.initStyle(StageStyle.UNDECORATED);
			             stage.setScene(scene);
			             stage.show();

			        }
			    });
			    return row ;
			});
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
			    
			try {
				mainApp.errorStackTraceDialog("Stack Trace Error: See Log", exceptionText);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		}
	}
	

	public void addButtonToTable() {

		Callback<TableColumn<SupremeTask, String>, TableCell<SupremeTask, String>> cellFactory = new Callback<TableColumn<SupremeTask, String>, TableCell<SupremeTask, String>>() {
			@Override
			public TableCell<SupremeTask, String> call(final TableColumn<SupremeTask, String> param) {
				final TableCell<SupremeTask, String> cell = new TableCell<SupremeTask, String>() {
					// Creates the start button
					final ImageView imageView = new ImageView(
							new Image("file:" + System.getProperty("user.dir") + "/resources/images/start.png"));
					

					Thread singleBrowserTask;
					

				
					private final Button startTasks = new Button("", imageView);
					{
						startTasks.setStyle("-fx-background-color: transparent;");
						startTasks.setOnAction((ActionEvent event) -> {
							
							consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task started \n");
							 if (this.getTableView().getItems().get(getIndex()).toString().contains("Instore Registration") && this.getTableView().getItems().get(getIndex()).toString().contains("Browser")) {
								singleBrowserTask = new Thread(new InstoreRegistrationSelenium(passableController,
										Integer.parseInt(this.getTableView().getItems().get(getIndex()).getId()),
										this.getTableView().getItems().get(getIndex()).getSize().toString()));
								singleBrowserTask.start();
							}
							 if (this.getTableView().getItems().get(getIndex()).toString().contains("Requests")) {
									singleBrowserTask = new Thread(new Request(passableController,
											Integer.parseInt(this.getTableView().getItems().get(getIndex()).getId()),
											this.getTableView().getItems().get(getIndex()).getIem().toString(),
											this.getTableView().getItems().get(getIndex()).getSize().toString(),
											this.getTableView().getItems().get(getIndex()).getCategory().toString(),
											this.getTableView().getItems().get(getIndex()).getColour().toString(),
											this.getTableView().getItems().get(getIndex()).getBillingProfile().toString()));
									singleBrowserTask.start();
								}
							 
							 if (this.getTableView().getItems().get(getIndex()).toString().contains("Mobile")) {
									singleBrowserTask = new Thread(new MobileCheckout(passableController,
											Integer.parseInt(this.getTableView().getItems().get(getIndex()).getId()),
											this.getTableView().getItems().get(getIndex()).getIem().toString(),
											this.getTableView().getItems().get(getIndex()).getSize().toString(),
											this.getTableView().getItems().get(getIndex()).getCategory().toString(),
											this.getTableView().getItems().get(getIndex()).getColour().toString(),
											this.getTableView().getItems().get(getIndex()).getBillingProfile().toString()));
									singleBrowserTask.start();
								}
							 
							 if (this.getTableView().getItems().get(getIndex()).toString().contains("Browser")) {
								singleBrowserTask = new Thread(new Selenium(passableController,
										Integer.parseInt(this.getTableView().getItems().get(getIndex()).getId()),
										this.getTableView().getItems().get(getIndex()).getIem().toString(),
										this.getTableView().getItems().get(getIndex()).getSize().toString(),
										this.getTableView().getItems().get(getIndex()).getCategory().toString(),
										this.getTableView().getItems().get(getIndex()).getColour().toString(),
										this.getTableView().getItems().get(getIndex()).getBillingProfile().toString()));
								singleBrowserTask.start();
							} 
							
						});
					
					}
					

					final ImageView stopImage = new ImageView(
							new Image("file:" + System.getProperty("user.dir") + "/resources/images/stop.png"));

					// Creates the stop button
					private final Button stopTasks = new Button("", stopImage);
					{

						stopTasks.setStyle("-fx-background-color: transparent;");
						stopTasks.setOnAction((ActionEvent event) -> {
							singleBrowserTask.currentThread().interrupt();
							browser.killBrowser();
							returnTasks().getItems().get(getIndex()).setStatus("Stopped");
							returnTasks().refresh();
						});
					}
					HBox pane = new HBox(startTasks, stopTasks);

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(pane);
							pane.setStyle(" -fx-alignment: CENTER;");
						}
					}
				};

				return cell;
			}
		};

		actionColumn.setCellFactory(cellFactory);

	}
	  
	/*
	 * Still needs fixing, Function needs any active browsers currently open and set the status label as idle.
	 * */
	@FXML
	public void stopTasks() {
		browser.killBrowser();
		
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
		statusColumn.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		supremeTask.refresh();
		
		for (Iterator<Thread> itr = threads.listIterator(); itr.hasNext();) {
            Thread thread = itr.next();
            thread.interrupt();
        }
	}
	
	
	@FXML
	public void clearAllTasks(ActionEvent event) {
		supremeTask.getItems().clear();
		supremeTask.setPlaceholder(new Label(""));
		taskCounter = 1;
		threads.clear();
	}
	
	//Status Column Updates
	public void statusColumnUpdateError() {
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getErrorProperty());
		statusColumn.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		supremeTask.refresh();
	}
	
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
		if (keywords.getText().length() <= 0) {
			this.alertDialogBuilder("Information Dialog", null, "Please Input Keyword");
		} else {
				
			// Fetch keywords for size input	
			String sizeID = sizes.getSelectionModel().getSelectedItem().toString();
	
			// Fetch keywords for input field
			String keywordsID = keywords.getText().toString();
	
			// Fetch selected catogory on the bot
			String catagoryID = catogry.getValue();
	
			// Fetch the select color
			String colourID = colour.getValue();
	
			String proxyID = txtProxy.getText().toString();
			
			String profileID = profiles.getSelectionModel().getSelectedItem().toString();
	
			String modeID = modes.getSelectionModel().getSelectedItem().toString();
			keywordInfo.getKeywordInfo().setMode(modeID);

			this.addButtonToTable();
			
			noOfTasksID = 0;
			if (noOfTasks.getText().isEmpty()) {
				noOfTasksID = 0;
			} else {
				noOfTasksID = Integer.parseInt(noOfTasks.getText());
			}
		 
				
			//Add task to table
			for (int i = 0; i < noOfTasksID; i++) {
				mainApp.getTaskData().add(new SupremeTask(taskCounter.toString(), keywordsID, sizeID ,colourID , catagoryID, profileID, ENUMstatus.Ready.toString(), modeID));
				taskCounter = supremeTask.getItems().size()  + 1 ;

	            
				
				this.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task created - [" + sizeID + ", " + keywordsID + ", "  + colourID + "]" + "\n");
		}

		//Sets amount of tasks in model	
		keywordInfo.getKeywordInfo().setTasks(supremeTask.getItems().size());
		}
	}
	
	
	@FXML
	public void createTask(ActionEvent event) throws InterruptedException, IOException, ParseException {
		boolean startTimer = keywordInfo.getKeywordInfo().getHasRunStarted();
		String startTime = keywordInfo.getKeywordInfo().getStartTimer();
		
	
		//If there is not start timer on the task a regular launch should happen other timer task should be started
		if (startTimer == false){
				//Check which mode should be running based on task information
				if (supremeTask.getItems().toString().contains("Browser")) {
					//Iterate through the table, create a thread and pass in task information from each row
					for (SupremeTask task :supremeTask.getItems()) {
						Thread taskInformation  = new Thread(new Selenium(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
						this.threads.add(taskInformation);
					}
				} else if (supremeTask.getItems().toString().contains("Request")) {
					for (SupremeTask task :supremeTask.getItems()) {
						Thread taskInformation  = new Thread(new Request(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
						this.threads.add(taskInformation);
					}
				} else if (supremeTask.getItems().toString().contains("Mobile")) {
					for (SupremeTask task :supremeTask.getItems()) {
						Thread taskInformation  = new Thread(new MobileCheckout(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
						this.threads.add(taskInformation);
					}
				}
		
				//Start the threads one by one
				for (Iterator<Thread> itr = threads.listIterator(); itr.hasNext();) {
		            Thread thread = itr.next();
		            thread.start();
		        }
				
				//Clear the threads for the next run
				threads.clear();
			
		} else if (startTimer == true ) {
				DateFormat year = new SimpleDateFormat("yyyy-MM-dd ");
				Date yearDate = new Date();
				System.out.println(year.format(yearDate)); //2016/11/16 12:08:43 -- TEST
				
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dateFormatter.parse(year.format(yearDate) + startTime);
				
				Timer timer = new Timer();
				timer.schedule(new schedulerDispatch(), date);
				this.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Tasks - Waiting for countdown: " + date + "\n");
			
		}
		
		//Change column status to 'running'
		if (startTimer == true) {
			statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimerProperty());
			statusColumn.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
			supremeTask.refresh();
		}
	}
	
	
	//Method for timed tasks
	public class schedulerDispatch extends TimerTask {
		public void run() {
			if (supremeTask.getItems().toString().contains("Browser") && supremeTask.getItems().toString().contains("Instore Registration")) {
				for (SupremeTask task :supremeTask.getItems()) {
					Thread taskInformation  = new Thread(new InstoreRegistrationSelenium(passableController, Integer.parseInt(task.getId()), task.getBillingProfile()));
					threads.add(taskInformation);
				}
			} else if (supremeTask.getItems().toString().contains("Requests")) {
				for (SupremeTask task :supremeTask.getItems()) {
					Thread taskInformation  = new Thread(new Request(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
					threads.add(taskInformation);
				}						
			} else if (supremeTask.getItems().toString().contains("Browser")) {
				for (SupremeTask task :supremeTask.getItems()) {
					Thread taskInformation  = new Thread(new Selenium(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
					threads.add(taskInformation);
				}	
			} else if (supremeTask.getItems().toString().contains("Mobile")) {
				for (SupremeTask task :supremeTask.getItems()) {
					Thread taskInformation  = new Thread(new MobileCheckout(passableController, Integer.parseInt(task.getId()) ,task.getIem().toString(), task.getSize(), task.getCategory(), task.getColour(), task.getBillingProfile()));
					threads.add(taskInformation);
				}	
			}
			
			for (Iterator<Thread> itr = threads.listIterator(); itr.hasNext();) {
	            Thread thread = itr.next();
	            thread.start();
	        }
			
			//Clear the threads for the next run
			threads.clear();
		}
	}
	
	public void checkForUpdates(ActionEvent action) {
				
		Notifications notificationBuilder = Notifications.create()
				.title("Check for updates")
				.text("No new updates found")
				.graphic(new ImageView(new Image("file:" + System.getProperty("user.dir") + "/resources/images/checked.png")))
				.hideAfter(Duration.seconds(5))
				.position(Pos.BOTTOM_RIGHT);
		
		notificationBuilder.darkStyle();
		notificationBuilder.show();
	}
	
	private void setupClearButtonField(CustomTextField customTextField)  throws Exception {
        Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
        m.setAccessible(true);
        m.invoke(null, customTextField, customTextField.rightProperty());
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
