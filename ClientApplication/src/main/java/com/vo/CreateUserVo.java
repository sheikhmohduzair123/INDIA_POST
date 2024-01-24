package com.vo;

import com.domain.Role;

public class CreateUserVo {
    private String name;
    private String email;
    private String username; 
    private String dateOfBirth;
    private String postalCode;
	private String zone;
	private String division;
	private String district;
	private String thana;
	private String subOffice; 
	private Role role;
	private String rmsId;
	private String addressLine1;
	private String addressLine2;
	private String mobileNumber;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getRmsId() {
		return rmsId;
	}
	public void setRmsId(String rmsId) {
		this.rmsId = rmsId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddreessLine1(String addreessLine1) {
		this.addressLine1 = addreessLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addreessLine2) {
		this.addressLine2 = addreessLine2;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
	
    
}
