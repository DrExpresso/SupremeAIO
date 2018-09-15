package ch.makery.address.view;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ch.makery.address.model.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class ProfileCreatorController {

	@FXML
	private TextField txtProfileName;
	@FXML
	private TextField txtFullName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField keywords;
	@FXML
	private TextField txtTelephone;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtAddress2;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField noOfTasks;
	@FXML
	private TextField txtPostcode;
	@FXML
	private TextField txtCardNumber;
	@FXML
	private TextField checkoutDelay;
	@FXML
	private TextField txtCvv;
	@FXML
	private ComboBox<String> cboCountry;
	@FXML
	private ComboBox<String> cboCardType;
	@FXML
	private ComboBox<String> cboExpiryMonth;
	@FXML
	private ComboBox<String> cboExpiryYear;
	@FXML
	private ComboBox<String> cboProfileName;
	@FXML
	private Button loadProfile;

	private ObservableList<String> profileList = FXCollections.observableArrayList();
	private Stage dialogStage;
	private SupremeBotOverviewController botController;
	
	//Combo box values
	ObservableList<String> countryList = FXCollections.observableArrayList("UK", "UK (N.IRELAND)", "AUSTRIA", "BELARUS",
			"BELGIUM", "BULGARIA", "CROATIA", "CZECH REPUBLIC", "DENMARK", "ESTONIA", "FINLAND", "FRANCE", "GERMANY",
			"GREECE", "HUNGARY", "ICELAND", "IRELAND", "ITALY", "LATVIA", "LITHUANIA", "LUXEMBOURG", "MONACO",
			"NETHERLANDS", "NORWAY", "POLAND", "PORTUGAL", "ROMANIA", "RUSSIA", "SLOVAKIA", "SLOVENIA", "SPAIN",
			"SWEDEN", "SWITZERLAND", "TURKEY");

	ObservableList<String> cardtypeList = FXCollections.observableArrayList("Visa", "American Express", "Mastercard",
			"Solo");

	ObservableList<String> cardExpiryMonthList = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12");

	ObservableList<String> cardExpiryYearList = FXCollections.observableArrayList("2018", "2019", "2020", "2021",
			"2022", "2023", "2024", "2025", "2026", "2027", "2028");

	@FXML
	private void initialize() {
		//Set combo box items at launch of window
		
		
		cboCountry.setItems(countryList);
		cboCountry.getSelectionModel().select(0);

		cboCardType.setItems(cardtypeList);
		cboCardType.getSelectionModel().select(0);

		cboExpiryYear.setItems(cardExpiryYearList);
		cboExpiryYear.getSelectionModel().select(0);

		cboExpiryMonth.setItems(cardExpiryMonthList);
		cboExpiryMonth.getSelectionModel().select(0);
		
		this.cardValidatorListener();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setProfileCreatorController(SupremeBotOverviewController controller2) {
		this.botController = controller2;
		
		cboProfileName.setItems(botController.getProfileList());
		cboProfileName.getSelectionModel().select(0);
	}
	

	@FXML
	public void setProfileDetails(ActionEvent action) throws IOException {
		Person.getPersonInfo().setProfileName(txtProfileName.getText());
		
		Person.getPersonInfo().setFullName(txtFullName.getText());

		Person.getPersonInfo().setEmail(txtEmail.getText());

		Person.getPersonInfo().setTelephone(txtTelephone.getText());

		Person.getPersonInfo().setAddress(txtAddress.getText());

		Person.getPersonInfo().setAddress2(txtAddress2.getText());

		Person.getPersonInfo().setCity(txtCity.getText());

		Person.getPersonInfo().setPostcode(txtPostcode.getText());

		Person.getPersonInfo().setCounty(cboCountry.getSelectionModel().getSelectedItem().toString());

		Person.getPersonInfo().setCardNumber(txtCardNumber.getText());

		Person.getPersonInfo().setCardtype(cboCardType.getSelectionModel().getSelectedItem().toString());

		Person.getPersonInfo().setExpiryMonth(cboExpiryMonth.getSelectionModel().getSelectedItem().toString());

		Person.getPersonInfo().setExpiryYear(cboExpiryYear.getSelectionModel().getSelectedItem().toString());

		Person.getPersonInfo().setCvv(txtCvv.getText());

		//Use Models JSONWritter method to save details to file when button is clicked
		Person.getPersonInfo().JSONWriter();
		
		alertDialogBuilder("Information Dialog", "Save success", "Created new profile!");
		
		botController.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Successfully updated profile \n");
		
		botController.getProfileList().add(txtProfileName.getText());
		

		System.out.println(Person.getPersonInfo().toString());
	}
	
	@FXML
	public void loadProfile(ActionEvent action) throws FileNotFoundException, IOException, ParseException {
		
		//Read in JSON from the Default_Profile file and set all values accordingly in text boxes.
		JSONParser parser = new JSONParser();
		
		JSONObject a = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir")+ "/resources/json" + "/" +  this.cboProfileName.getSelectionModel().getSelectedItem().toString()  + ".json"));

		String profileName = (String) a.get("Profile Name");
		txtProfileName.setText(profileName);
		
		String fullname = (String) a.get("Fullname");
		txtFullName.setText(fullname);
		
		String email = (String) a.get("Email");
		txtEmail.setText(email);
		
		String Telephone = (String) a.get("Telephone");
		txtTelephone.setText(Telephone);
		
		String Address = (String) a.get("Address");
		txtAddress.setText(Address);
		
		String Address2 = (String) a.get("Address 2");
		txtAddress2.setText(Address2);
		
		String City = (String) a.get("City");
		txtCity.setText(City);
		
		String Postcode = (String) a.get("Postcode");
		txtPostcode.setText(Postcode);
		
		String Country = (String) a.get("Country");
		cboCountry.getSelectionModel().select(Country);
		
		String CardType = (String) a.get("Card Type");
		cboCardType.getSelectionModel().select(CardType);
		
		String CardNumber = (String) a.get("Card Number");
		txtCardNumber.setText(CardNumber);
		
		String CardExpiryMonth = (String) a.get("Card Expiry Month");
		cboExpiryMonth.getSelectionModel().select(CardExpiryMonth);
		
		String CardExpiryYear = (String) a.get("Card Expiry Year");
		cboExpiryYear.getSelectionModel().select(CardExpiryYear);
		
		String CardSecurityCode = (String) a.get("Card Security Code");
		txtCvv.setText(CardSecurityCode);
		
		botController.consoleWriter("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Successfully loaded profile \n");
		
	}

	//Creates space inbetween the each 4 digits of the card input field
	public void cardValidatorListener() {
		txtCardNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.length() == 4 || newValue.length() == 9 || newValue.length() == 14) {
						txtCardNumber.setText(newValue + " ");
					} else if (txtCardNumber.getText().length() >= 19) {
						txtCardNumber.setText(txtCardNumber.getText().substring(0, 19));
					}
			}
		});
	}

	@FXML
	private void handleClearFields() {
		txtProfileName.clear();
		txtFullName.clear();
		txtEmail.clear();
		txtTelephone.clear();
		txtAddress.clear();
		txtAddress2.clear();
		txtCity.clear();
		txtPostcode.clear();
		txtCardNumber.clear();
		txtCvv.clear();
	}

	private void alertDialogBuilder(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
