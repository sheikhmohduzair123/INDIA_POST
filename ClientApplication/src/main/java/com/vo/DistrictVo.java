package com.vo;

import java.util.Date;

import com.constants.Status;
import com.domain.Division;
import com.domain.User;

public class DistrictVo {

	private Long id;
	private Status status;
	private Date createdOn;
	private Date updatedOn;
	private String district;
	private Long division;
	public Long getDivision() {
		return division;
	}
	public void setDivision(Long division) {
		this.division = division;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

}
