package com.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.constants.BagFillStatus;
import com.constants.BagStatus;
import com.constants.BagTitle;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;

@Entity
@Table(name="Tracking_cs")
public class TrackingCS implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "pTrackingId", nullable = true, referencedColumnName="trackId")
	private Parcel objParcel;

	@Enumerated(EnumType.STRING)
	private PStatus pStatus;

	private Long locationId;

	@Enumerated(EnumType.STRING)
	private LocationType locationType;

	private String bagId;


	@Enumerated(EnumType.STRING)
	private BagStatus bagStatus;

	//@Enumerated(EnumType.STRING)
	private String bagTitle;

	private String bagDesc;

	@Enumerated(EnumType.STRING)
	private BagFillStatus bagFillStatus;

	private String trackingDesc;

	private String bagPaging;

	private Status status;
	private Date createdOn;
	private Date updatedOn;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "createdBy", nullable = true, referencedColumnName="id")
	private User createdBy;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "updatedBy", nullable = true, referencedColumnName="id")
	private User updatedBy;



	public String getBagPaging() {
		return bagPaging;
	}

	public void setBagPaging(String bagPaging) {
		this.bagPaging = bagPaging;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Parcel getObjParcel() {
		return objParcel;
	}

	public void setObjParcel(Parcel objParcel) {
		this.objParcel = objParcel;
	}

	public PStatus getpStatus() {
		return pStatus;
	}

	public void setpStatus(PStatus pStatus) {
		this.pStatus = pStatus;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public LocationType getLocationType() {
		return locationType;
	}

   public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public BagStatus getBagStatus() {
		return bagStatus;
	}

	public void setBagStatus(BagStatus bagStatus) {
		this.bagStatus = bagStatus;
	}

	public String getBagTitle() {
		return bagTitle;
	}

	public void setBagTitle(String bagTitle) {
		this.bagTitle = bagTitle;
	}

	public String getBagDesc() {
		return bagDesc;
	}

	public void setBagDesc(String bagDesc) {
		this.bagDesc = bagDesc;
	}

	public BagFillStatus getBagFillStatus() {
		return bagFillStatus;
	}

	public void setBagFillStatus(BagFillStatus bagFillStatus) {
		this.bagFillStatus = bagFillStatus;
	}

	public String getTrackingDesc() {
		return trackingDesc;
	}

	public void setTrackingDesc(String trackingDesc) {
		this.trackingDesc = trackingDesc;
	}



	@Override
	public String toString() {
		return "TrackingCS [id=" + id + ", trackingId=" + objParcel + ", pStatus=" + pStatus + ", locationId="
				+ locationId + ", locationType=" + locationType + ", bagId=" + bagId + ", bagStatus=" + bagStatus
				+ ", bagTitle=" + bagTitle + ", bagDesc=" + bagDesc + ", bagFillStatus=" + bagFillStatus
				+ ", trackingDesc=" + trackingDesc + ", bagPagging=" + bagPaging + "]";
	}



}
