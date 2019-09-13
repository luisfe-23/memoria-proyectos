package com.soaint.crud.modelsRn;

public class Contactos {

	private int id;
	private String lookupName;
	
	public Contactos() {
		super();
		
	}
	public Contactos(int id, String lookupName) {
		super();
		this.id = id;
		this.lookupName = lookupName;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}


	@Override
	public String toString() {
		return "Contactos [id=" + id + ", lookupName=" + lookupName + "]";
	}
	
	
	
	
}


