package com.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.constants.LocationDependency;
import com.constants.PriceType;
import com.constants.ValueDependency;
import com.constants.WeightDependency;

import java.io.Serializable;

@Embeddable
public class RateServiceCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private Long serviceId;
	@NotNull
	private PriceType priceType;
	@NotNull
	private LocationDependency locationDependency;
	@NotNull
	private WeightDependency weightDependency;
	@NotNull
	private ValueDependency valueDependency;
	
	//more dependency can be added if needed
	
	private Long parentServiceId;
	@Range(min = 0, max = 10000000)
	private Long weightMaxLimit;
	@Range(min = 0, max = 10000000)
	private Long valueMaxLimit;
	@Range(min = 0, max = 10000000)
	private Float price;
	@Range(min = 0, max = 10000000)
	private Float taxRate1;
	@Range(min = 0, max = 10000000)
	private Float taxRate2;
	@Range(min = 0, max = 100)
	private Long expectedDelivery;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(Long expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}

	@Enumerated(EnumType.ORDINAL)
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public LocationDependency getLocationDependency() {
		return locationDependency;
	}
	public void setLocationDependency(LocationDependency locationDependency) {
		this.locationDependency = locationDependency;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public WeightDependency getWeightDependency() {
		return weightDependency;
	}
	public void setWeightDependency(WeightDependency weightDependency) {
		this.weightDependency = weightDependency;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public ValueDependency getValueDependency() {
		return valueDependency;
	}
	public void setValueDependency(ValueDependency valueDependency) {
		this.valueDependency = valueDependency;
	}

	public Long getParentServiceId() {
		return parentServiceId;
	}

	public void setParentServiceId(Long parentServiceId) {
		this.parentServiceId = parentServiceId;
	}

	public Long getWeightMaxLimit() {
		return weightMaxLimit;
	}

	public void setWeightMaxLimit(Long weightMaxLimit) {
		this.weightMaxLimit = weightMaxLimit;
	}

	public Long getValueMaxLimit() {
		return valueMaxLimit;
	}

	public void setValueMaxLimit(Long valueMaxLimit) {
		this.valueMaxLimit = valueMaxLimit;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getTaxRate1() {
		return taxRate1;
	}

	public void setTaxRate1(Float taxRate1) {
		this.taxRate1 = taxRate1;
	}

	public Float getTaxRate2() {
		return taxRate2;
	}

	public void setTaxRate2(Float taxRate2) {
		this.taxRate2 = taxRate2;
	}

}
