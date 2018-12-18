package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProxyTask {
	private final SimpleStringProperty id;
	private final SimpleStringProperty ip;
	private final SimpleStringProperty port;
	private final SimpleStringProperty user;
	private final SimpleStringProperty pass;
	private final SimpleStringProperty status;
	private final SimpleStringProperty speed;
	private final SimpleStringProperty errorOccured;


	public ProxyTask() {
		this(null, null, null, null, null, null, null);
	}

	public ProxyTask(String id, String ip, String port, String user, String pass, String status, String speed) {
		this.id =  new SimpleStringProperty(id);
		this.ip = new SimpleStringProperty(ip);
		this.port = new SimpleStringProperty(port);
		this.user = new SimpleStringProperty(user);
		this.pass = new SimpleStringProperty(pass);
		this.status = new SimpleStringProperty(status);
		this.speed = new SimpleStringProperty(speed);
		this.errorOccured = new SimpleStringProperty("Error occurred");
	}
	
	public String getId( ) {
		return id.get();
	}	
	
	public SimpleStringProperty getIdProperty( ) {
		return id;
	}

	public String getIp() {
		return ip.get();
	}

	public SimpleStringProperty getIpProperty() {
		return ip;
	}

	public String getPort() {
		return port.get();
	}

	public SimpleStringProperty getPortProperty() {
		return port;
	}

	public String getUser() {
		return user.get();
	}

	public SimpleStringProperty getUserProperty() {
		return user;
	}

	public String getPass() {
		return pass.get();
	}

	public SimpleStringProperty getPassProperty() {
		return pass;
	}
	
	public String getStatus() {
		return status.get();
	}

	public SimpleStringProperty getStatusProperty() {
		return status;
	}
	
	public String getSpeed() {
		return speed.get();
	}
	
	public void setSpeed(String speed) {
		this.speed.set(speed);
	}

	public SimpleStringProperty getSpeedProperty() {
		return speed;
	}
	
	public void setStatus(String status) {
		this.status.set(status);
	}
	
	public StringProperty getErrorProperty( ) {
		return errorOccured;
	}

}
