package com.soaint.crud.modelsRn;

public class ContactosRn {
	

	private Name name;
	private Emails emails;
	
	
	public ContactosRn(Name name, Emails emails) {
		this.name = name;
		this.emails = emails;
	}
	
	public ContactosRn() {

	}

	public Name getName() {return name;}

	public void setName(Name name) {this.name = name;}

	public Emails getEmails() {return emails;}

	public void setEmails(Emails emails) {this.emails = emails;}

	
}
