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

    private long bagIdCntr;

    private long rmsId;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public long getBagIdCntr() {
		return bagIdCntr;
	}

	public void setBagIdCntr(long bagIdCntr) {
		this.bagIdCntr = bagIdCntr;
	}

	public long getRmsId() {
		return rmsId;
	}

	public void setRmsId(long rmsId) {
		this.rmsId = rmsId;
	}

	public long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(long postalCode) {
		this.postalCode = postalCode;
	}

}
