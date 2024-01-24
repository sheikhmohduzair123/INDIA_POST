package com.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.constants.LocationDependency;
import com.constants.PriceType;
import com.constants.Status;
import com.constants.ValueDependency;
import com.constants.WeightDependency;

public class RateTableDataVo {
	
	private Long serviceId;
	private PriceType priceType;
	private List<LocationDependency> locationDependency;
	private List<WeightDependency> weightDependency;
	private List<ValueDependency> valueDependency;
	private Long parentServiceId;
	private Long weightMaxLimit;
	private Long valueMaxLimit;
	private Float price;
	private Float taxRate1;
	private Float taxRate2;
	private Long expectedDelivery;
	
	private List<Float> weightStartRange;
	private List<Float> weightEndRange;
	private List<Float> weightFractionFactor;
	private List<Float> weightBasePrice;
	private List<Float> weightPrice;
	
	private List<Integer> fromId;
	private List<Integer> toId;
	//private Float locationBasePrice;
	private List<Float> locationPrice;
	
	private List<Integer> valueStartRange;
	private List<Integer> valueUpToRange;
	private List<Float> valueFraction;
	private List<Float> valueBasePrice;
	private List<Float> valuePrice;
	
	
	private Long mainServiceId;
	private Long subServiceId;
	private String serviceName;
	private String subService;
	private Float locationBasePrice;
	private Status status;
	private Date updatedOn;
	private String updatedBy;
	
	public RateTableDataVo() {
		
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

	public List<LocationDependency> getLocationDependency() {
		return locationDependency;
	}

	public void setLocationDependency(List<LocationDependency> locationDependency) {
		this.locationDependency = locationDependency;
	}

	public List<WeightDependency> getWeightDependency() {
		return weightDependency;
	}

	public void setWeightDependency(List<WeightDependency> weightDependency) {
		this.weightDependency = weightDependency;
	}

	public List<ValueDependency> getValueDependency() {
		return valueDependency;
	}

	public void setValueDependency(List<ValueDependency> valueDependency) {
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

	public List<Float> getWeightStartRange() {
		return weightStartRange;
	}

	public void setWeightStartRange(List<Float> weightStartRange) {
		this.weightStartRange = weightStartRange;
	}

	public List<Float> getWeightEndRange() {
		return weightEndRange;
	}

	public void setWeightEndRange(List<Float> weightEndRange) {
		this.weightEndRange = weightEndRange;
	}

	public List<Float> getWeightFractionFactor() {
		return weightFractionFactor;
	}

	public void setWeightFractionFactor(List<Float> weightFractionFactor) {
		this.weightFractionFactor = weightFractionFactor;
	}

	public List<Float> getWeightBasePrice() {
		return weightBasePrice;
	}

	public void setWeightBasePrice(List<Float> weightBasePrice) {
		this.weightBasePrice = weightBasePrice;
	}

	public List<Float> getWeightPrice() {
		return weightPrice;
	}

	public void setWeightPrice(List<Float> weightPrice) {
		this.weightPrice = weightPrice;
	}

	public List<Integer> getFromId() {
		return fromId;
	}

	public void setFromId(List<Integer> fromId) {
		this.fromId = fromId;
	}

	public List<Integer> getToId() {
		return toId;
	}

	public void setToId(List<Integer> toId) {
		this.toId = toId;
	}

	public List<Float> getLocationPrice() {
		return locationPrice;
	}

	public void setLocationPrice(List<Float> locationPrice) {
		this.locationPrice = locationPrice;
	}

	public List<Integer> getValueStartRange() {
		return valueStartRange;
	}

	public void setValueStartRange(List<Integer> valueStartRange) {
		this.valueStartRange = valueStartRange;
	}

	public List<Integer> getValueUpToRange() {
		return valueUpToRange;
	}

	public void setValueUpToRange(List<Integer> valueUpToRange) {
		this.valueUpToRange = valueUpToRange;
	}

	public List<Float> getValueFraction() {
		return valueFraction;
	}

	public void setValueFraction(List<Float> valueFraction) {
		this.valueFraction = valueFraction;
	}

	public List<Float> getValueBasePrice() {
		return valueBasePrice;
	}

	public void setValueBasePrice(List<Float> valueBasePrice) {
		this.valueBasePrice = valueBasePrice;
	}

	public List<Float> getValuePrice() {
		return valuePrice;
	}

	public void setValuePrice(List<Float> valuePrice) {
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
