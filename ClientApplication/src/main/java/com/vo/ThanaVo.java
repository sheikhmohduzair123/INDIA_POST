package com.vo;

import java.util.Date;

import com.constants.Status;
import com.domain.District;
import com.domain.User;

public class ThanaVo {
	private Long id;
	private Status status;
	private Date createdOn;
	private Date updatedOn;
	private Long district;
	private String thana;

	public Long getDistrict() {
		return district;
	}
	public void setDistrict(Long district) {
		this.district = district;
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

	public String getThana() {
		return thana;
	}
	public void setThana(String thana) {
		this.thana = thana;
	}


}
