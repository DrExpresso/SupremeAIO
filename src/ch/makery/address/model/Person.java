package ch.makery.address.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.json.simple.JSONObject;

import com.cedarsoftware.util.io.JsonWriter;

public class Person {

	private static Person model = new Person();
	private String fullname;
	private String email;
	private String telephone;
	private String address;
	private String address2;
	private String city;
	private String postcode;
	private String country;

	private String profileName = "Default Profile";

	private String cardType;
	private String cardNumber;
	private String cvv;
	private String expiryMonth;
	private String expiryYear;

	public static Person getPersonInfo() {
		return model;
	}

	public String getFullName() {
		return fullname;
	}

	public void setFullName(String fullName) {
		this.fullname = fullName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCounty() {
		return country;
	}

	public void setCounty(String country) {
		this.country = country;
	}

	public String getCardtype() {
		return cardType;
	}

	public void setCardtype(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	@Override
	public String toString() {
		return "Person [fullname=" + fullname + ", email=" + email + ", telephone=" + telephone + ", address=" + address
				+ ", address2=" + address2 + " + city=" + city + ", postcode=" + postcode
				+ ", country=" + country + ", profileName=" + profileName + ", cardType=" + cardType + ", cardNumber="
				+ cardNumber + ", cvv=" + cvv + ", expiryMonth=" + expiryMonth + ", expiryYear=" + expiryYear + "]";
	}
	
	
	//Saves all Model data inputted to the Default_Profile.json 
	@SuppressWarnings("unchecked")
	public void JSONWriter() throws IOException {
		
		JSONObject obj = new JSONObject();
		
		//Shipping Info
		obj.put("Profile Name", this.profileName);
		obj.put("Fullname", this.fullname);
		obj.put("Email", this.email);
		obj.put("Telephone", this.telephone);
		obj.put("Address", this.address);
		obj.put("Address 2", this.address2);
		obj.put("City", this.city);
		obj.put("Postcode", this.postcode);
		obj.put("Country", this.country);
		
		//Billing Info
		obj.put("Card Type", this.cardType);
		obj.put("Card Number", this.cardNumber);
		obj.put("Card Security Code", this.cvv);
		obj.put("Card Expiry Month", this.expiryMonth);
		obj.put("Card Expiry Year", this.expiryYear);
		
	
		
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/json/" + "/" + profileName + ".json")) {
			file.write(JsonWriter.formatJson(obj.toJSONString()));
			file.flush();
			System.out.println("Successfully Copied JSON Object to File...");
		}
		
	}
}
