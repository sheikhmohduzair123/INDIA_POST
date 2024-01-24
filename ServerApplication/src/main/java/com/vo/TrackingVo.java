package com.vo;

import java.util.Date;

import com.constants.BagFillStatus;
import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Address;
import com.domain.Parcel;

public class TrackingVo {

	private Long id;
	private Parcel objParcelVo;
	private PStatus pStatus;
	private Long locationId;
	private LocationType locationType;
	private String bagId;
	private BagStatus bagStatus;
	private String bagTitle;
	private String bagDesc;
	private BagFillStatus bagFillStatus;
	private String trackingDesc;
	private Status status;
	private Date updatedOn;
	private String rmsName;
	private String rmsType;
	private String subOfficeName;
	private int postalCode;
	private String newBagId;
	private Date parcelUpdatedOn;
	private float actWt;
	private String trackId;
	private Address receiverAddress;
	private float payableAmnt;
	private String parcelContent;
	 private byte[] parcelimage;
	
	public byte[] getParcelimage() {
		return parcelimage;
	}

	public void setParcelimage(byte[] parcelimage) {
		this.parcelimage = parcelimage;
	}

	public Date getParcelUpdatedOn() {
		return parcelUpdatedOn;
	}

	public void setParcelUpdatedOn(Date parcelUpdatedOn) {
		this.parcelUpdatedOn = parcelUpdatedOn;
	}

	public String getNewBagId() {
		return newBagId;
	}

	public void setNewBagId(String newBagId) {
		this.newBagId = newBagId;
	}

	public String getRmsName() {
		return rmsName;
	}

	public void setRmsName(String rmsName) {
		this.rmsName = rmsName;
	}

	public String getRmsType() {
		return rmsType;
	}

	public void setRmsType(String rmsType) {
		this.rmsType = rmsType;
	}

	public String getSubOfficeName() {
		return subOfficeName;
	}

	public void setSubOfficeName(String subOfficeName) {
		this.subOfficeName = subOfficeName;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Parcel getObjParcelVo() {
		return objParcelVo;
	}

	public void setObjParcelVo(Parcel objParcelVo) {
		this.objParcelVo = objParcelVo;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public float getActWt() {
		return actWt;
	}

	public void setActWt(float actWt) {
		this.actWt = actWt;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public Address getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public float getPayableAmnt() {
		return payableAmnt;
	}

	public void setPayableAmnt(float payableAmnt) {
		this.payableAmnt = payableAmnt;
	}

	public String getParcelContent() {
		return parcelContent;
	}

	public void setParcelContent(String parcelContent) {
		this.parcelContent = parcelContent;
	}

	
}
