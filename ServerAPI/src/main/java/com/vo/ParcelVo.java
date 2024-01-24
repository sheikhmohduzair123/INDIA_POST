package com.vo;

import java.util.Date;
import java.util.List;

import com.domain.Address;
import com.domain.Client;
import com.domain.InvoiceBreakup;

public class ParcelVo {
	
	private String labelCode;
	private String trackId;
	private String printCount;
	private String senderName;
	private Long senderMobileNumber;
	private Address senderAddress;
	private String recipientName;
	private Long recipientMobileNumber;
	private Address receiverAddress;
	private Client client;
	private Date createdDate;
	private Date lastPrintDate;
	private String parcelDeclerationValue;
	private String parcelContent;
    private String parcelLength;
    private String parcelWidth;
    private String parcelHeight;
    private String parcelVolumeWeight;
    private String parcelDeadWeight;
	private String actWt;
    private long service;
	private Integer createdBy;
    private List<Long> subServices;
    private InvoiceBreakup invoiceBreakup;
    private String status;
    private RateCalculation rateCalculation;
    private String rateCalculationJSON;
    private String printOption;
    private boolean toPay;
    private boolean cod;
	private String pStatus;
	private boolean paid;
	private String evidence;
	
	private boolean syncFlag;
	private Date updatedOn;
	private String serviceCode;
	//private String parcelImageString;
	private byte[] parcelimage;
	private String scanedBarcode;
	private Long parcelCount;

	public Long getParcelCount() {
		return parcelCount;
	}
	public void setParcelCount(Long parcelCount) {
		this.parcelCount = parcelCount;
	}


	/*
	 * public String getParcelImageString() { return parcelImageString; } public
	 * void setParcelImageString(String parcelImageString) { this.parcelImageString
	 * = parcelImageString; }
	 */
	public String getScanedBarcode() {
		return scanedBarcode;
	}

	public void setScanedBarcode(String scanedBarcode) {
		this.scanedBarcode = scanedBarcode;
	}

	
	public byte[] getParcelimage() {
		return parcelimage;
	}
	public void setParcelimage(byte[] parcelimage) {
		this.parcelimage = parcelimage;
	}
	
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public String getTrackId() {
		return trackId;
	}
	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	public String getPrintCount() {
		return printCount;
	}
	public void setPrintCount(String printCount) {
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
	public Address getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(Address receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastPrintDate() {
		return lastPrintDate;
	}
	public void setLastPrintDate(Date lastPrintDate) {
		this.lastPrintDate = lastPrintDate;
	}
	public String getParcelDeclerationValue() {
		return parcelDeclerationValue;
	}
	public void setParcelDeclerationValue(String parcelDeclerationValue) {
		this.parcelDeclerationValue = parcelDeclerationValue;
	}
	public String getParcelContent() {
		return parcelContent;
	}
	public void setParcelContent(String parcelContent) {
		this.parcelContent = parcelContent;
	}
	public String getParcelLength() {
		return parcelLength;
	}
	public void setParcelLength(String parcelLength) {
		this.parcelLength = parcelLength;
	}
	public String getParcelWidth() {
		return parcelWidth;
	}
	public void setParcelWidth(String parcelWidth) {
		this.parcelWidth = parcelWidth;
	}
	public String getParcelHeight() {
		return parcelHeight;
	}
	public void setParcelHeight(String parcelHeight) {
		this.parcelHeight = parcelHeight;
	}
	public String getParcelVolumeWeight() {
		return parcelVolumeWeight;
	}
	public void setParcelVolumeWeight(String parcelVolumeWeight) {
		this.parcelVolumeWeight = parcelVolumeWeight;
	}
	public String getParcelDeadWeight() {
		return parcelDeadWeight;
	}
	public void setParcelDeadWeight(String parcelDeadWeight) {
		this.parcelDeadWeight = parcelDeadWeight;
	}
	public String getActWt() {
		return actWt;
	}
	public void setActWt(String actWt) {
		this.actWt = actWt;
	}
	public long getService() {
		return service;
	}
	public void setService(long service) {
		this.service = service;
	}
	public List<Long> getSubServices() {
		return subServices;
	}
	public void setSubServices(List<Long> subServices) {
		this.subServices = subServices;
	}
	public InvoiceBreakup getInvoiceBreakup() {
		return invoiceBreakup;
	}
	public void setInvoiceBreakup(InvoiceBreakup invoiceBreakup) {
		this.invoiceBreakup = invoiceBreakup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public RateCalculation getRateCalculation() {
		return rateCalculation;
	}
	public void setRateCalculation(RateCalculation rateCalculation) {
		this.rateCalculation = rateCalculation;
	}
	public String getRateCalculationJSON() {
		return rateCalculationJSON;
	}
	public void setRateCalculationJSON(String rateCalculationJSON) {
		this.rateCalculationJSON = rateCalculationJSON;
	}
	public String getPrintOption() {
		return printOption;
	}
	public void setPrintOption(String printOption) {
		this.printOption = printOption;
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
	public String getpStatus() {
		return pStatus;
	}
	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	

}
