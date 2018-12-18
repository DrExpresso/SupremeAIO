package ch.makery.address.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SupremeTask {
	private final SimpleStringProperty id;
	private final SimpleStringProperty item;
	private final SimpleStringProperty billingProfile;
	private final SimpleStringProperty mode;
	private final SimpleStringProperty colour;
	private final SimpleStringProperty category;
	private final SimpleStringProperty size;


	
	//Status Information
	private SimpleStringProperty status;
	private final SimpleStringProperty statusRunning;
	private final SimpleStringProperty foundItem;
	private final SimpleStringProperty fetchingVariants;
	private final SimpleStringProperty addingToCart;
	private final SimpleStringProperty checkout;
	private final SimpleStringProperty checkedOut;
	private final SimpleStringProperty reCaptchaToken;
	private final SimpleStringProperty startTimer;
	private final SimpleStringProperty autoStart;
	private final SimpleStringProperty itemNotFound;
	private final SimpleStringProperty errorOccured;
	
	public SupremeTask() {
        this(null, null, null, null, null, null, null, null);
    }
	
	public SupremeTask(String id, String item,String size, String colour, String category, String billingProfile, String status, String mode) {
			this.id =  new SimpleStringProperty(id);
			this.item =  new SimpleStringProperty(item);
			this.billingProfile = new SimpleStringProperty(billingProfile);
			this.status =  new SimpleStringProperty(status);
			this.mode =  new SimpleStringProperty(mode);
			this.colour =  new SimpleStringProperty(colour);
			this.category =  new SimpleStringProperty(category);
			this.size =  new SimpleStringProperty(size);
			this.status = new SimpleStringProperty("Ready");
			this.statusRunning = new SimpleStringProperty("Running");
			this.errorOccured = new SimpleStringProperty("Error occurred");
			this.foundItem = new SimpleStringProperty("Item found");
			this.itemNotFound = new SimpleStringProperty("Item not found");
			this.addingToCart = new SimpleStringProperty("Adding to cart...");
			this.fetchingVariants = new SimpleStringProperty("Fetching variants.");
			this.checkout = new SimpleStringProperty("Checking out");
			this.reCaptchaToken = new SimpleStringProperty("Captcha required");
			this.checkedOut = new SimpleStringProperty("Check your email");
			this.startTimer = new SimpleStringProperty("Launching at: " + keywordInfo.getKeywordInfo().getStartTimer());
			this.autoStart = new SimpleStringProperty("Launching at: 11:00:00");
		}

		public String getId( ) {
			return id.get();
		}	
		
		public SimpleStringProperty getIdProperty( ) {
			return id;
		}
		
		public StringProperty getIemProperty( ) {
			return item;
		}
		
		public StringProperty getColourProperty( ) {
			return colour;
		}
		
		public StringProperty getSizeProperty( ) {
			return size;
		}
		
		public StringProperty getCategoryProperty( ) {
			return category;
		}

		public StringProperty getBillingProperty( ) {
			return billingProfile;
		}
		
		public StringProperty getAutostartProperty( ) {
			return autoStart;
		}
		
		public StringProperty getModeProperty() {
			return mode;
		}
		
		public StringProperty getItemNotFoundProperty( ) {
			return itemNotFound;
		}
		
		public StringProperty getFetchingVariantsProperty( ) {
			return fetchingVariants;
		}
		
		public StringProperty getStatusProperty( ) {
			return status;
		}
		
		public StringProperty getStartTimerProperty( ) {
			return startTimer;
		}
		
		public StringProperty getErrorProperty( ) {
			return errorOccured;
		}
		
		public StringProperty getStatusRunningProperty( ) {
			return statusRunning;
		}
		
		public StringProperty getFoundItemProperty( ) {
			return foundItem;
		}
		
		public StringProperty getRecaptchaTokenProperty( ) {
			return reCaptchaToken;
		}
		
		public StringProperty getAddingToCartProperty( ) {
			return addingToCart;
		}
		
		public StringProperty getCheckoutProperty( ) {
			return checkout;
		}
		
		public StringProperty getCheckedOutProperty() {
			return checkedOut;
		}
		
		public void setId(String id) {
			this.id.set(id);
		}
		
		public String getIem( ) {
			return item.get();
		}
		
		public String getSize( ) {
			return size.get();
		}
		
		public String getColour( ) {
			return colour.get();
		}
		
		public String getCategory( ) {
			return category.get();
		}
		
		public String getMode( ) {
			return mode.get();
		}
		
		public void setMode(String mode) {
			this.mode.set(mode);
		}
		
		public void setItem(String item) {
			this.item.set(item);
		}
		

		public String getBillingProfile( ) {
			return billingProfile.get();
		}
		
		public void setBillingProfile(String billingProfile) {
			this.billingProfile.set(billingProfile);
		}
		
		@Override
		public String toString() {
			return "SupremeTask [id=" + id + ", item=" + item + ", billingProfile=" + billingProfile + ", proxy="
					 + ", mode=" + mode + ", status=" + status + ", statusRunning=" + statusRunning + "]";
		}

		public String getStatus( ) {
			return status.get();
		}
		
		public void setStatus(String status) {
			this.status.set(status);
		}
}
