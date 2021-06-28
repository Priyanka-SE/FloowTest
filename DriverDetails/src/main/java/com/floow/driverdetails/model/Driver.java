package com.floow.driverdetails.model;

import javax.validation.constraints.NotEmpty;


public class Driver {
	
	private String driverID;
	@NotEmpty(message="Please provide driver's first name")
	private String firstName;
	@NotEmpty(message="Please provide driver's last name")
	private String lastName;
	@NotEmpty(message="Please provide driver's date of birth")
	private String dateOfBirth;
	private String creationDate;
	

	public Driver() {
		
	}
	public Driver(String driverID,String firstName,String lastName,String dateOfBirth,
			String creationDate) {
		super();
		this.driverID = driverID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.creationDate = creationDate;
	}
	public String getDriverID() {
		return driverID;
	}
	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
