package com.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="id_counters")
public class IdCounters implements Serializable {


	private static final long serialVersionUID = 1L;

	private long postalCode;

    private long parceltrackIdCntr;

  	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(long postalCode) {
		this.postalCode = postalCode;
	}

	public long getParceltrackIdCntr() {
		return parceltrackIdCntr;
	}

	public void setParceltrackIdCntr(long parceltrackIdCntr) {
		this.parceltrackIdCntr = parceltrackIdCntr;
	}


}
