package com.vo;

import com.constants.*;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class RateTableVo {
	
	private Long id;
	private Long rateTableId;
	private Long serviceId;
	private PriceType priceType;
	private LocationDependency locationDependency;
	private WeightDependency weightDependency;
	private ValueDependency valueDependency;
	private Long parentServiceId;
	private Long weightMaxLimit;
	private Long valueMaxLimit;
	private Float price;
	private Float taxRate1;
	private Float taxRate2;
	private Long expectedDelivery;
	
	private Float weightStartRange;
	private Float weightEndRange;
	private Float weightFractionFactor;
	private Float weightBasePrice;
	private Float weightPrice;
	
	private Integer fromId;
	private Integer toId;
	//private Float locationBasePrice;
	private Float locationPrice;
	
	private Integer valueStartRange;
	private Integer valueUpToRange;
	private Float valueFraction;
	private Float valueBasePrice;
	private Float valuePrice;
	
	
	private Long mainServiceId;
	private Long subServiceId;
	private String serviceName;
	private String subService;
	private Float locationBasePrice;
	private Status status;
	private Date updatedOn;
	private String updatedBy;
	
	public RateTableVo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRateTableId() {
		return rateTableId;
	}

	public void setRateTableId(Long rateTableId) {
		this.rateTableId = rateTableId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public LocationDependency getLocationDependency() {
		return locationDependency;
	}

	public void setLocationDependency(LocationDependency locationDependency) {
		this.locationDependency = locationDependency;
	}

	public WeightDependency getWeightDependency() {
		return weightDependency;
	}

	public void setWeightDependency(WeightDependency weightDependency) {
		this.weightDependency = weightDependency;
	}

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

	public Long getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(Long expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}

	public Float getWeightStartRange() {
		return weightStartRange;
	}

	public void setWeightStartRange(Float weightStartRange) {
		this.weightStartRange = weightStartRange;
	}

	public Float getWeightEndRange() {
		return weightEndRange;
	}

	public void setWeightEndRange(Float weightEndRange) {
		this.weightEndRange = weightEndRange;
	}

	public Float getWeightFractionFactor() {
		return weightFractionFactor;
	}

	public void setWeightFractionFactor(Float weightFractionFactor) {
		this.weightFractionFactor = weightFractionFactor;
	}

	public Float getWeightBasePrice() {
		return weightBasePrice;
	}

	public void setWeightBasePrice(Float weightBasePrice) {
		this.weightBasePrice = weightBasePrice;
	}

	public Float getWeightPrice() {
		return weightPrice;
	}

	public void setWeightPrice(Float weightPrice) {
		this.weightPrice = weightPrice;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public Float getLocationPrice() {
		return locationPrice;
	}

	public void setLocationPrice(Float locationPrice) {
		this.locationPrice = locationPrice;
	}

	public Integer getValueStartRange() {
		return valueStartRange;
	}

	public void setValueStartRange(Integer valueStartRange) {
		this.valueStartRange = valueStartRange;
	}

	public Integer getValueUpToRange() {
		return valueUpToRange;
	}

	public void setValueUpToRange(Integer valueUpToRange) {
		this.valueUpToRange = valueUpToRange;
	}

	public Float getValueFraction() {
		return valueFraction;
	}

	public void setValueFraction(Float valueFraction) {
		this.valueFraction = valueFraction;
	}

	public Float getValueBasePrice() {
		return valueBasePrice;
	}

	public void setValueBasePrice(Float valueBasePrice) {
		this.valueBasePrice = valueBasePrice;
	}

	public Float getValuePrice() {
		return valuePrice;
	}

	public void setValuePrice(Float valuePrice) {
		this.valuePrice = valuePrice;
	}

	public Long getMainServiceId() {
		return mainServiceId;
	}

	public void setMainServiceId(Long mainServiceId) {
		this.mainServiceId = mainServiceId;
	}

	public Long getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(Long subServiceId) {
		this.subServiceId = subServiceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSubService() {
		return subService;
	}

	public void setSubService(String subService) {
		this.subService = subService;
	}

	public Float getLocationBasePrice() {
		return locationBasePrice;
	}

	public void setLocationBasePrice(Float locationBasePrice) {
		this.locationBasePrice = locationBasePrice;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
