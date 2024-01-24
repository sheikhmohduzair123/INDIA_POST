package com.domain;

import com.constants.Status;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * in this table we will have only uni directional relationship only.
 */
@Entity
@Table(name="RATE_TABLE")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RateTable implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Status status;
	private Date createdOn;
	private Date updatedOn;
	private User createdBy;
	private User updatedBy;
	private RateServiceCategory rateServiceCategory;
	private LocationWiseRate locationWiseRate;
	private WeightWiseRate weightWiseRate;
	private ParcelValueWiseRate parcelValueWiseRate;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@ManyToOne
	@JoinColumn(name="createdby_id", foreignKey=@ForeignKey(name="id"))
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne
	@JoinColumn(name="updatedby_id", foreignKey=@ForeignKey(name="id"))
	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Embedded
	public RateServiceCategory getRateServiceCategory() {
		return rateServiceCategory;
	}
	public void setRateServiceCategory(RateServiceCategory rateServiceCategory) {
		this.rateServiceCategory = rateServiceCategory;
	}

	@Embedded
	public LocationWiseRate getLocationWiseRate() {
		return locationWiseRate;
	}
	public void setLocationWiseRate(LocationWiseRate locationWiseRate) {
		this.locationWiseRate = locationWiseRate;
	}

	@Embedded
	public WeightWiseRate getWeightWiseRate() {
		return weightWiseRate;
	}
	public void setWeightWiseRate(WeightWiseRate weightWiseRate) {
		this.weightWiseRate = weightWiseRate;
	}

	@Embedded
	public ParcelValueWiseRate getParcelValueWiseRate() {
		return parcelValueWiseRate;
	}
	public void setParcelValueWiseRate(ParcelValueWiseRate parcelValueWiseRate) {
		this.parcelValueWiseRate = parcelValueWiseRate;
	}

}
