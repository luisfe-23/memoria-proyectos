package com.soaint.crud.models.osc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactosOsc {
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("EmailAddress")
	private String emailAddress;
	public ContactosOsc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContactosOsc(String firstName, String lastName, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}
	@JsonProperty("FirstName")
	public String getFirstName() {
		return firstName;
	}
	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonProperty("LastName")
	public String getLastName() {
		return lastName;
	}
	@JsonProperty("LastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@JsonProperty("EmailAddress")
	public String getEmailAddress() {
		return emailAddress;
	}
	@JsonProperty("EmailAddress")
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@Override
	public String toString() {
		return "ContactosOsc [firstName=" + firstName + ", lastName=" + lastName + ", emailAddress=" + emailAddress
				+ "]";
	}


	
	
	
}
