package com.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import com.vo.RateCalculation;

@Entity
@Table(name = "PARCEL_DETAILS")
@DynamicUpdate // for updating only particular fields of a row
public class Parcel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String labelCode;// pincode+postOffice-id+counter-id+labelCode+printCount
	private String trackId;
	private int printCount;

	// sender info
	private String senderName;
	private Long senderMobileNumber;
	private Address senderAddress;

	// recipient info
	private String recipientName;
	private Long recipientMobileNumber;
	private Address receiverAddress;

	private Client client;// ManyToOne

	// other info
	// private User createdBy;
	private Integer createdBy;
	private Date createdDate;
	private Date lastPrintDate;
	private String pathOnLocalFileSystem;// optional, we will think of it later on

	// parcel info
	private double parcelDeclerationValue;
	private String parcelContent;
	private String imagePath;
	private float parcelLength;
	private float parcelWidth;
	private float parcelHeight;
	private float parcelVolumeWeight;
	private float parcelDeadWeight;

	private float actWt;

	// flag to check whether parcel is synced or not with status true or false;
	private boolean syncFlag;

	// parcel services
	private long service;

	private List<Long> subServices;

	private InvoiceBreakup invoiceBreakup;

	private String status;
	private RateCalculation rateCalculation;
	private String rateCalculationJSON;
	private String printOption;

    @Column(columnDefinition = "boolean default false")
	private boolean paid;

	@Length(min = 1, max = 2000)
	private String evidence;


	// ngp Transaction Id
	@Column(unique = true)
	private String ngp_id;

	public String getNgpId() {
		return ngp_id;
	}

	public void setNgpId(String ngp_id) {
		this.ngp_id = ngp_id;
	}

	// flags for checking label Codes
	private boolean toPay;
	private boolean cod;

	private String pStatus;

	private byte[] parcelimage;
	private String parcelImageString;
	private String scanedBarcode;
	private Long parcelCount;
	
	private String ecommerceReferenceNo;

	public String getEcommerceReferenceNo() {
		return ecommerceReferenceNo;
	}

	public void setEcommerceReferenceNo(String ecommerceReferenceNo) {
		this.ecommerceReferenceNo = ecommerceReferenceNo;
	}

	public Long getParcelCount() {
		return parcelCount;
	}

	public void setParcelCount(Long parcelCount) {
		this.parcelCount = parcelCount;
	}

	public String getScanedBarcode() {
		return scanedBarcode;
	}

	public void setScanedBarcode(String scanedBarcode) {
		this.scanedBarcode = scanedBarcode;
	}

	@Transient
	public String getParcelImageString() {
		return parcelImageString;
	}

	public void setParcelImageString(String parcelImageString) {
		this.parcelImageString = parcelImageString;
	}

	public byte[] getParcelimage() {
		return parcelimage;
	}

	public void setParcelimage(byte[] parcelimage) {
		this.parcelimage = parcelimage;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public boolean isToPay() {
		return toPay;
	}

	public void setToPay(boolean toPay) {
		this.toPay = toPay;
	}

	public boolean isCod() {
		return cod;
	}

	public void setCod(boolean cod) {
		this.cod = cod;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getSenderMobileNumber() {
		return senderMobileNumber;
	}

	public void setSenderMobileNumber(Long senderMobileNumber) {
		this.senderMobileNumber = senderMobileNumber;
	}

	public Date getLastPrintDate() {
		return lastPrintDate;
	}

	public void setLastPrintDate(Date lastPrintDate) {
		this.lastPrintDate = lastPrintDate;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	@Embedded
	public Address getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(Address senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public Long getRecipientMobileNumber() {
		return recipientMobileNumber;
	}

	public void setRecipientMobileNumber(Long recipientMobileNumber) {
		this.recipientMobileNumber = recipientMobileNumber;
	}

	@Embedded
	public Address getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client", nullable = true, referencedColumnName = "id")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/*
	 * @ManyToOne(fetch=FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "createdby", nullable = false, referencedColumnName="id")
	 * public User getCreatedBy() { return createdBy; }
	 */
	/*
	 * public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
	 */

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPathOnLocalFileSystem() {
		return pathOnLocalFileSystem;
	}

	public void setPathOnLocalFileSystem(String pathOnLocalFileSystem) {
		this.pathOnLocalFileSystem = pathOnLocalFileSystem;
	}

	public double getParcelDeclerationValue() {
		return parcelDeclerationValue;
	}

	public void setParcelDeclerationValue(double parcelDeclerationValue) {
		this.parcelDeclerationValue = parcelDeclerationValue;
	}

	public String getParcelContent() {
		return parcelContent;
	}

	public void setParcelContent(String parcelContent) {
		this.parcelContent = parcelContent;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public float getParcelLength() {
		return parcelLength;
	}

	public void setParcelLength(float parcelLength) {
		this.parcelLength = parcelLength;
	}

	public float getParcelWidth() {
		return parcelWidth;
	}

	public void setParcelWidth(float parcelWidth) {
		this.parcelWidth = parcelWidth;
	}

	public float getParcelHeight() {
		return parcelHeight;
	}

	public void setParcelHeight(float parcelHeight) {
		this.parcelHeight = parcelHeight;
	}

	public float getParcelVolumeWeight() {
		return parcelVolumeWeight;
	}

	public void setParcelVolumeWeight(float parcelVolumeWeight) {
		this.parcelVolumeWeight = parcelVolumeWeight;
	}

	public float getActWt() {
		return actWt;
	}

	public void setActWt(float actWt) {
		this.actWt = actWt;
	}

	public float getParcelDeadWeight() {
		return parcelDeadWeight;
	}

	public void setParcelDeadWeight(float parcelDeadWeight) {
		this.parcelDeadWeight = parcelDeadWeight;
	}

	public long getService() {
		return service;
	}

	public void setService(long service) {
		this.service = service;
	}

	@Transient
	public List<Long> getSubServices() {
		return subServices;
	}

	public void setSubServices(List<Long> subServices) {
		this.subServices = subServices;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// @Transient
	@Embedded
	public InvoiceBreakup getInvoiceBreakup() {
		return invoiceBreakup;
	}

	public void setInvoiceBreakup(InvoiceBreakup invoiceBreakup) {
		this.invoiceBreakup = invoiceBreakup;
	}

	@Transient
	public RateCalculation getRateCalculation() {
		return rateCalculation;
	}

	public void setRateCalculation(RateCalculation rateCalculation) {
		this.rateCalculation = rateCalculation;
	}

	@Transient
	public String getPrintOption() {
		return printOption;
	}

	public void setPrintOption(String printOption) {
		this.printOption = printOption;
	}

	@Column(length = 3072000) // 3 MB size
	public String getRateCalculationJSON() {
		return rateCalculationJSON;
	}

	public void setRateCalculationJSON(String rateCalculationJSON) {
		this.rateCalculationJSON = rateCalculationJSON;
	}


	 public String getpStatus() {
		return pStatus;
	}

	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}

	

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	@Column(length = 2000)
	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((labelCode == null) ? 0 : labelCode.hashCode());
		result = prime
				* result
				+ ((recipientMobileNumber == null) ? 0 : recipientMobileNumber
						.hashCode());
		result = prime * result
				+ ((recipientName == null) ? 0 : recipientName.hashCode());
		result = prime
				* result
				+ ((senderMobileNumber == null) ? 0 : senderMobileNumber
						.hashCode());
		result = prime * result
				+ ((senderName == null) ? 0 : senderName.hashCode());
		result = prime * result + ((trackId == null) ? 0 : trackId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Parcel)) {
			return false;
		}
		Parcel other = (Parcel) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (labelCode == null) {
			if (other.labelCode != null) {
				return false;
			}
		} else if (!labelCode.equals(other.labelCode)) {
			return false;
		}
		if (recipientMobileNumber == null) {
			if (other.recipientMobileNumber != null) {
				return false;
			}
		} else if (!recipientMobileNumber.equals(other.recipientMobileNumber)) {
			return false;
		}
		if (recipientName == null) {
			if (other.recipientName != null) {
				return false;
			}
		} else if (!recipientName.equals(other.recipientName)) {
			return false;
		}
		if (senderMobileNumber == null) {
			if (other.senderMobileNumber != null) {
				return false;
			}
		} else if (!senderMobileNumber.equals(other.senderMobileNumber)) {
			return false;
		}
		if (senderName == null) {
			if (other.senderName != null) {
				return false;
			}
		} else if (!senderName.equals(other.senderName)) {
			return false;
		}
		if (trackId == null) {
			if (other.trackId != null) {
				return false;
			}
		} else if (!trackId.equals(other.trackId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return (id != null ? id + ", " : "")
				+ (labelCode != null ? labelCode + ", " : "")
				+ (trackId != null ? trackId + ", " : "")
				+ printCount
				+ ", "
				+ (senderName != null ? senderName + ", " : "")
				+ (senderMobileNumber != null ? senderMobileNumber + ", " : "")
				+ (senderAddress != null ? senderAddress + ", " : "")
				+ (recipientName != null ? recipientName + ", " : "")
				+ (recipientMobileNumber != null ? recipientMobileNumber + ", "
						: "")
				+ (receiverAddress != null ? receiverAddress + ", " : "")
				+ (client != null ? client + ", " : "")
				+ (createdBy != null ? createdBy + ", " : "")
				+ (createdDate != null ? createdDate + ", " : "")
				+ (pathOnLocalFileSystem != null ? pathOnLocalFileSystem + ", "
						: "") + parcelDeclerationValue + ", "
				+ (parcelContent != null ? parcelContent + ", " : "")
				+ (imagePath != null ? imagePath + ", " : "") + parcelLength
				+ ", " + parcelWidth + ", " + parcelHeight + ", "
				+ parcelVolumeWeight + ", " + parcelDeadWeight +", "
				+ service + ", "+ subServices;
	}



	public String printShippedAddress() {
		return "Shipped from:  " + senderName + "<br>" + senderAddress.getAddressLine1() + ", "
				+ (!senderAddress.getAddressLine2().equals("") ? senderAddress.getAddressLine2() + ", " : "")
				+ (!senderAddress.getLandmark().equals("") ? senderAddress.getLandmark() + ", " : "")
				+ senderAddress.getSubOffice() + ", " + senderAddress.getThana() + ", " + senderAddress.getDistrict()
				+ ", " + senderAddress.getDivision()+ ", " + senderAddress.getZone() + ", " +"India - "
				+ senderAddress.getPostalCode() + " (Mob:" + getSenderMobileNumber() + ")";
	}

	public String printToAddress() {
		return "<b>TO: " + recipientName + "<br>" + receiverAddress.getAddressLine1() + ", "
				+ (!receiverAddress.getAddressLine2().equals("") ? receiverAddress.getAddressLine2() + ", " : "")
				+ (!receiverAddress.getLandmark().equals("") ? receiverAddress.getLandmark() + ", " : "")
				+ receiverAddress.getSubOffice() + ", " +receiverAddress.getThana() + ", "
				+ receiverAddress.getDistrict()+ ", "
				+ receiverAddress.getDivision()+ ", "
				+receiverAddress.getZone()+", "
				+ "India - " + receiverAddress.getPostalCode() + "<br>(Mob:" + getRecipientMobileNumber()+ ")";
	}

}
