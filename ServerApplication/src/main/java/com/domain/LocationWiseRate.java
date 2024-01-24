package com.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Range;

@Embeddable
public class LocationWiseRate implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer fromId;
	private Integer toId;
	@Range(min = 0, max = 10000000)
	private Float basePrice;
	@Range(min = 0, max = 10000000)
	private Float price;
	
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
