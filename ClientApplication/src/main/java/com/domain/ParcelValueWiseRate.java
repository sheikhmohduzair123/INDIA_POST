package com.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ParcelValueWiseRate implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer valueStartRange;
	private Integer valueUpToRange;
	private Float valueFraction;
	private Float basePrice;
	private Float price;
	
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
	public Float getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Float basePrice) {
		this.basePrice = basePrice;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
}
