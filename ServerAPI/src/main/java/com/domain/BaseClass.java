package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.constants.Status;

/*
 * this class will have common fields which will be used in all tables(master tables specifically)
 * for every row we will use update-insert
 * previous row will be set on status "DISABLED" and new row will have status "ACTIVE"
 * "DISABLED" row will not be updated again - NEVER
 */
//for single table uncomment following comments
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Table(name="RATE_TABLE")

//for single table comment below line
@MappedSuperclass
public class BaseClass implements Serializable{

	private static final long serialVersionUID = 1L;
	private Status status;
	private Date createdOn;
	private Date updatedOn;
	private User createdBy;
	private User updatedBy;

	@Enumerated(EnumType.ORDINAL)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@ManyToOne
	@JoinColumn(name="createdby_id", foreignKey=@ForeignKey(name="id"))
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	@ManyToOne
	@JoinColumn(name="updatedby_id", foreignKey=@ForeignKey(name="id"))
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

}
