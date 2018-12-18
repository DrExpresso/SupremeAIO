package ch.makery.address.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import ch.makery.address.MainApp;
import ch.makery.address.model.ProxyTask;
import ch.makery.address.selenium.ProxyTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class proxyTesterController {

	@FXML
	private Stage dialogStage;
	private MainApp main;
	@FXML
	private Button importBtn;
	@FXML
	private Button testBtn;
	@FXML
	public TableView<ProxyTask> proxyTask;
	@FXML
	private TableColumn<ProxyTask, String> idColumn;
	@FXML
	private TableColumn<ProxyTask, String> ipColumn;
	@FXML
	public TableColumn<ProxyTask, String> portColumn;
	@FXML
	private TableColumn<ProxyTask, String> userColumn;
	@FXML
	private TableColumn<ProxyTask, String> passColumn;
	@FXML
	public TableColumn<ProxyTask, String> statusColumn;
	@FXML
	private TableColumn<ProxyTask, String> speedColumn;
	@FXML
	private TextField urlTxt;
	
	public List<Thread> threads = new ArrayList<Thread>();
	
	public Integer taskCounter = 1;
	
	public static void main(String[] args) {}
	
	public void setDialogStage(Stage dialogStage, MainApp main) {
		this.dialogStage = dialogStage;
		this.main= main;
		proxyTask.setItems(main.getProxyData());
	}
	
	public TableView<ProxyTask> returnTasks() {
		return proxyTask;
	}
	
	
	public void initialize() throws MalformedURLException, IOException {
	
		idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
		ipColumn.setCellValueFactory(cellData -> cellData.getValue().getIpProperty());
		portColumn.setCellValueFactory(cellData -> cellData.getValue().getPortProperty());
		userColumn.setCellValueFactory(cellData -> cellData.getValue().getUserProperty());
		passColumn.setCellValueFactory(cellData -> cellData.getValue().getPassProperty());
		statusColumn.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
		speedColumn.setCellValueFactory(cellData -> cellData.getValue().getSpeedProperty());
				
	}
	
	public void testProxy(ActionEvent action) throws MalformedURLException, IOException {
		for (ProxyTask task :proxyTask.getItems()) {
			Thread taskInformation  = new Thread(new ProxyTest(this,  Integer.parseInt(task.getId()) , task.getIp(), task.getPort(), task.getUser(), task.getPass()));
			this.threads.add(taskInformation);
		}
		
		for (Iterator<Thread> itr = threads.listIterator(); itr.hasNext();) {
            Thread thread = itr.next();
            thread.start();
        }
	}
	
	public void importProxies(ActionEvent action) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Import Proxies");
		alert.setHeaderText("Import proxies to test");
	

		TextArea textArea = new TextArea();
		textArea.setPromptText("IP:PORT:USER:PASS");
		textArea.setEditable(true);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		
		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setContent(textArea);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			for (int i =0; i < textArea.getText().split("\n").length; i++) {
				
				String pass1[] = textArea.getText().split("\n");		
				
				for (String s : pass1) {
					String check[] =  s.split(":");
					main.getProxyData().add(new ProxyTask(taskCounter.toString(), check[0], check[1], check[2], check[3], null,null));
					taskCounter = proxyTask.getItems().size()  + 1 ;
				}
			
			}
		} 
	}

}
