package com.vo;

import com.domain.*;
import com.util.OperationStatus;

import java.util.List;

public class RateCalculation {
	
	float finalAmount;
	String errorMsg;
	OperationStatus operationStatus;
	RateServiceCategory rateServiceCategory;
	LocationWiseRate locationWiseRate;
	WeightWiseRate weightWiseRate;
	ParcelValueWiseRate parcelValueWiseRate;
	InvoiceBreakup invoiceBreakup;
	List<RateCalculation> subServicesRateCalculation;
	List<Long>subServiceId;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public float getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(float finalAmount) {
		this.finalAmount = finalAmount;
	}
	public OperationStatus getOperationStatus() {
		return operationStatus;
	}
	public void setOperationStatus(OperationStatus operationStatus) {
		this.operationStatus = operationStatus;
	}
	public RateServiceCategory getRateServiceCategory() {
		return rateServiceCategory;
	}
	public void setRateServiceCategory(RateServiceCategory rateServiceCategory) {
		this.rateServiceCategory = rateServiceCategory;
	}
	public LocationWiseRate getLocationWiseRate() {
		return locationWiseRate;
	}
	public void setLocationWiseRate(LocationWiseRate locationWiseRate) {
		this.locationWiseRate = locationWiseRate;
	}
	public WeightWiseRate getWeightWiseRate() {
		return weightWiseRate;
	}
	public void setWeightWiseRate(WeightWiseRate weightWiseRate) {
		this.weightWiseRate = weightWiseRate;
	}
	public ParcelValueWiseRate getParcelValueWiseRate() {
		return parcelValueWiseRate;
	}
	public void setParcelValueWiseRate(ParcelValueWiseRate parcelValueWiseRate) {
		this.parcelValueWiseRate = parcelValueWiseRate;
	}
	public List<RateCalculation> getSubServicesRateCalculation() {
		return subServicesRateCalculation;
	}
	public void setSubServicesRateCalculation(
			List<RateCalculation> subServicesRateCalculation) {
		this.subServicesRateCalculation = subServicesRateCalculation;
	}
	
	public InvoiceBreakup getInvoiceBreakup() {
		return invoiceBreakup;
	}
	public void setInvoiceBreakup(InvoiceBreakup invoiceBreakup) {
		this.invoiceBreakup = invoiceBreakup;
	}
	public List<Long> getSubServiceId() {
		return subServiceId;
	}

	public void setSubServiceId(List<Long> subServiceId) {
		this.subServiceId = subServiceId;
	}
	@Override
	public String toString() {
		return finalAmount
				+ ", "
				+ (operationStatus != null ? operationStatus + ", " : "")
				+ (rateServiceCategory != null ? rateServiceCategory + ", "
						: "")
				+ (locationWiseRate != null ? locationWiseRate + ", " : "")
				+ (weightWiseRate != null ? weightWiseRate + ", " : "")
				+ (parcelValueWiseRate != null ? parcelValueWiseRate + ", "
						: "")
				+ (subServicesRateCalculation != null ? subServicesRateCalculation
						: "");
	}

}
