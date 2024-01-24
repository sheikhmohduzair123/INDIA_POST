package com.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Range;

@Embeddable
public class WeightWiseRate implements Serializable {

	private static final long serialVersionUID = 1L;
	@Range(min = 0, max = 10000000)
	private Float weightStartRange;
	@Range(min = 0, max = 10000000)
	private Float weightEndRange;
	@Range(min = 0, max = 10000000)
	private Float weightFractionFactor;
	@Range(min = 0, max = 10000000)
	private Float basePrice;
	@Range(min = 0, max = 10000000)
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
