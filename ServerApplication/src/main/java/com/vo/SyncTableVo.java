package com.vo;

import java.sql.Timestamp;


public class SyncTableVo {

	private String clientId;
	private String division;
	private String district;
	private String thana;
	private String subOffice;
	private String zone;
	private int postalCode;
	private String clientName;
	private Timestamp syncUpdatedTimestamp;
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getThana() {
		return thana;
	}
	public void setThana(String thana) {
		this.thana = thana;
	}
	public String getSubOffice() {
		return subOffice;
	}
	public void setSubOffice(String subOffice) {
		this.subOffice = subOffice;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	    
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Timestamp getSyncUpdatedTimestamp() {
		return syncUpdatedTimestamp;
	}
	public void setSyncUpdatedTimestamp(Timestamp syncUpdatedTimestamp) {
		this.syncUpdatedTimestamp = syncUpdatedTimestamp;
	}
	
}
