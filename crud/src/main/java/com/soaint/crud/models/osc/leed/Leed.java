package com.soaint.crud.models.osc.leed;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Leed {

	@JsonProperty("ContactPartyId")
	private long PartyId;
	@JsonProperty("Name")
	private String Name;
	
	public Leed() {
			
	}
	
	public Leed(long partyNumber, String name) {
		super();
		PartyId = partyNumber;
		Name = name;
	}

	public long getPartyId() {return PartyId;}

	public void setPartyId(long partyNumber) {PartyId = partyNumber;}

	public String getName() {return Name;}

	public void setName(String name) {Name = name;}
	
	
	
}
