package com.soaint.crud.modelsRn;

public class Emails {

	private String address;
	private AddressType addressType;
	
	public Emails() {

	}

	

	public Emails(String address, AddressType addressType) {
		super();
		this.address = address;
		this.addressType = addressType;
	}



	public String getAddress() {return address;}

	public void setAddress(String address) {this.address = address;}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}


	
}
