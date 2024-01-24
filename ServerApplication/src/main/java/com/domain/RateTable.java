package com.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

/*
 * in this table we will have only uni directional relationship only.
 */
@Entity
@Table(name="RATE_TABLE")
public class RateTable extends BaseClass{

	private static final long serialVersionUID = 1L;
	@Valid
	private RateServiceCategory rateServiceCategory;
	@Valid
	private LocationWiseRate locationWiseRate;
	@Valid
	private WeightWiseRate weightWiseRate;
	@Valid
	private ParcelValueWiseRate parcelValueWiseRate;
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
