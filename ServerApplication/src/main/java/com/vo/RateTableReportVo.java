package com.vo;

import com.constants.LocationDependency;
import com.constants.ValueDependency;
import com.constants.WeightDependency;

public class RateTableReportVo {
	
	private String serviceName;
	private String subService;
	//rate service category
	private String locationDependency;
	private String weightDependency;
	private String valueDependency;
	private String weightMaxLimit;
	private String valueMaxLimit;
	private String rateServicePrice;
	private String taxRate1;
	private String taxRate2;
	private String expectedDelivery;

	
	//weight dependenacy
	private String weightStartRange;
	private String weightEndRange;
	private String weightFractionFactor;
	private String basePrice;
	private String price;
	//ParcelValueWiseRate
	private String valueStartRange;
	private String valueUpToRange;
	private String valueFraction;
	private String valuebasePrice;
	private String valueprice;
	//location wise
	private String fromId;
	private String toId;
	private String locationbasePrice;
	private String locationprice;
	
	
	
	
	
	public String getLocationDependency() {
		return locationDependency;
	}
	public void setLocationDependency(String locationDependency) {
		this.locationDependency = locationDependency;
	}
	public String getWeightDependency() {
		return weightDependency;
	}
	public void setWeightDependency(String weightDependency) {
		this.weightDependency = weightDependency;
	}
	public String getValueDependency() {
		return valueDependency;
	}
	public void setValueDependency(String valueDependency) {
		this.valueDependency = valueDependency;
	}
	public String getWeightMaxLimit() {
		return weightMaxLimit;
	}
	public void setWeightMaxLimit(String weightMaxLimit) {
		this.weightMaxLimit = weightMaxLimit;
	}
	public String getValueMaxLimit() {
		return valueMaxLimit;
	}
	public void setValueMaxLimit(String valueMaxLimit) {
		this.valueMaxLimit = valueMaxLimit;
	}
	public String getRateServicePrice() {
		return rateServicePrice;
	}
	public void setRateServicePrice(String rateServicePrice) {
		this.rateServicePrice = rateServicePrice;
	}
	public String getTaxRate1() {
		return taxRate1;
	}
	public void setTaxRate1(String taxRate1) {
		this.taxRate1 = taxRate1;
	}
	public String getTaxRate2() {
		return taxRate2;
	}
	public void setTaxRate2(String taxRate2) {
		this.taxRate2 = taxRate2;
	}
	public String getExpectedDelivery() {
		return expectedDelivery;
	}
	public void setExpectedDelivery(String expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}
	public String getValueStartRange() {
		return valueStartRange;
	}
	public void setValueStartRange(String valueStartRange) {
		this.valueStartRange = valueStartRange;
	}
	public String getValueUpToRange() {
		return valueUpToRange;
	}
	public void setValueUpToRange(String valueUpToRange) {
		this.valueUpToRange = valueUpToRange;
	}
	public String getValueFraction() {
		return valueFraction;
	}
	public void setValueFraction(String valueFraction) {
		this.valueFraction = valueFraction;
	}
	
	
	public String getValuebasePrice() {
		return valuebasePrice;
	}
	public void setValuebasePrice(String valuebasePrice) {
		this.valuebasePrice = valuebasePrice;
	}
	public String getValueprice() {
		return valueprice;
	}
	public void setValueprice(String valueprice) {
		this.valueprice = valueprice;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getLocationbasePrice() {
		return locationbasePrice;
	}
	public void setLocationbasePrice(String locationbasePrice) {
		this.locationbasePrice = locationbasePrice;
	}
	public String getLocationprice() {
		return locationprice;
	}
	public void setLocationprice(String locationprice) {
		this.locationprice = locationprice;
	}
	public String getWeightStartRange() {
		return weightStartRange;
	}
	public void setWeightStartRange(String weightStartRange) {
		this.weightStartRange = weightStartRange;
	}
	public String getWeightEndRange() {
		return weightEndRange;
	}
	public void setWeightEndRange(String weightEndRange) {
		this.weightEndRange = weightEndRange;
	}
	public String getWeightFractionFactor() {
		return weightFractionFactor;
	}
	public void setWeightFractionFactor(String weightFractionFactor) {
		this.weightFractionFactor = weightFractionFactor;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	
}
