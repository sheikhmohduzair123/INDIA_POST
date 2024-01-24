package com.vo;

import com.constants.Status;
import com.domain.LocationWiseRate;
import com.domain.ParcelValueWiseRate;
import com.domain.RateServiceCategory;
import com.domain.WeightWiseRate;

import java.util.Date;

public class RateTableVo {

	private Long id;
	private RateServiceCategory rateServiceCategory;
	private LocationWiseRate locationWiseRate;
	private WeightWiseRate weightWiseRate;
	private ParcelValueWiseRate parcelValueWiseRate;
	private Status status;
	private Date createdOn;
	private Date updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RateServiceCategory getRateServiceCategory() {
		return rateServiceCategory;
	}

	public void setRateServiceCategory(RateServiceCategory rateServiceCategory) {
		this.rateServiceCategory = rateServiceCategory;
	}

	public LocationWiseRate getLocationWiseRate() {
		return locationWiseRate;
	}

	public void setLocationWiseRate(LocationWiseRate locationWiseRate) {
		this.locationWiseRate = locationWiseRate;
	}

	public WeightWiseRate getWeightWiseRate() {
		return weightWiseRate;
	}

	public void setWeightWiseRate(WeightWiseRate weightWiseRate) {
		this.weightWiseRate = weightWiseRate;
	}

	public ParcelValueWiseRate getParcelValueWiseRate() {
		return parcelValueWiseRate;
	}

	public void setParcelValueWiseRate(ParcelValueWiseRate parcelValueWiseRate) {
		this.parcelValueWiseRate = parcelValueWiseRate;
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
	
}
