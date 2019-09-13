package com.soaint.crud.models.osc.leed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"Name",
	"ContactPartyNumber",
	"LeadId"
})
public class Leed {

	@JsonProperty("ContactPartyNumber")
	private long ContactPartyNumber;
	@JsonProperty("Name")
	private String Name;
	@JsonProperty("LeadId")
	private long LeadId;
	
	public Leed() {
	
	}
		
	public Leed(long contactPartyNumber, String name, long leadId) {
		ContactPartyNumber = contactPartyNumber;
		Name = name;
		LeadId = leadId;
	}

	@JsonProperty("ContactPartyNumber")
	public long getPartyId() {return ContactPartyNumber;}
	@JsonProperty("ContactPartyNumber")
	public void setPartyId(long partyNumber) {ContactPartyNumber = partyNumber;}
	@JsonProperty("Name")
	public String getName() {return Name;}
	@JsonProperty("Name")
	public void setName(String name) {Name = name;}
	@JsonProperty("LeadId")
	public long getLeadId() {return LeadId;}
	@JsonProperty("LeadId")
	public void setLeadId(long leadId) {LeadId = leadId;}

	@Override
	public String toString() {
		return "Leed [ContactPartyNumber=" + ContactPartyNumber + ", Name=" + Name + ", LeadId=" + LeadId + "]";
	}
	
}
