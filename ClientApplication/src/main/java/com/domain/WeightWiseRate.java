package com.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
public class WeightWiseRate implements Serializable {

	private static final long serialVersionUID = 1L;
	private Float weightStartRange;
	private Float weightEndRange;
	private Float weightFractionFactor;
	private Float basePrice;
	private Float price;
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
