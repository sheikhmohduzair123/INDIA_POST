package com.domain;

import com.constants.Status;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="MASTER_ADDRESS")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class MasterAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Status status;
	private Date createdOn;
	private Date updatedOn;
	private User createdBy;
	private User updatedBy;
	private String division;
	private String district;
	private String thana;
	private String subOffice;
	private int postalCode;
	private String zone;
	private Long divisionId;
	private Long districtId;
	private Long thanaId;
	private Long zoneId;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getThana() {
		return thana;
	}

	public void setThana(String thana) {
		this.thana = thana;
	}

	public String getSubOffice() {
		return subOffice;
	}

	public void setSubOffice(String subOffice) {
		this.subOffice = subOffice;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Column(name="division_id")
	public Long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	@Column(name="district_id")
	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}


	@Column(name="zone_id")
	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getThanaId() {
		return thanaId;
	}

	public void setThanaId(Long thanaId) {
		this.thanaId = thanaId;
	}

	/*
	 * @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 *
	 * @JoinColumn(name = "thana_id", nullable = false) public Thana getThanaId() {
	 * return thanaId; }
	 *
	 * public void setThanaId(Thana thanaId) { this.thanaId = thanaId; }
	 */

}
