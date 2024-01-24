package com.vo;

import java.util.Date;

import com.constants.Status;
public class RmsTableVo {
	private Long id;
	private String RmsName;
	private String RmsType;
	private Status status;
	private Date updatedOn;
	private String country;
	private int postalCode;
	private String zone;
	private String division;
	private String district;
	private String thana;
	private String subOffice;
	private String addressLine1;
	private String addressLine2;
	private String landmark;
	private Long RmsMobileNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRmsName() {
		return RmsName;
	}

	public void setRmsName(String rmsName) {
		RmsName = rmsName;
	}

	public String getRmsType() {
		return RmsType;
	}

	public void setRmsType(String rmsType) {
		RmsType = rmsType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
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

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Long getRmsMobileNumber() {
		return RmsMobileNumber;
	}

	public void setRmsMobileNumber(Long rmsMobileNumber) {
		RmsMobileNumber = rmsMobileNumber;
	}

}
