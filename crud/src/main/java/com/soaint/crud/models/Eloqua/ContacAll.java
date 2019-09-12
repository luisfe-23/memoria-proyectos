package com.soaint.crud.models.Eloqua;

public class ContacAll {
	
	private long Id;
	private String FirstName;
	private String LastName;
	private String EmailAddess;
	
	public ContacAll() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContacAll(long id, String firstName, String lastName, String emailAddess) {
		super();
		Id = id;
		FirstName = firstName;
		LastName = lastName;
		EmailAddess = emailAddess;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmailAddess() {
		return EmailAddess;
	}
	
	public void setEmailAddess(String emailAddess) {
		EmailAddess = emailAddess;
	}
	@Override
	public String toString() {
		return "ContacAll [Id=" + Id + ", EmailAddess=" + EmailAddess + "]";
	}
	


	}
	

