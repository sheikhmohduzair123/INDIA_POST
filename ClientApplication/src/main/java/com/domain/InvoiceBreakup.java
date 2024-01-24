package com.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.constants.UpdatePayableAmnt;

@Embeddable
public class InvoiceBreakup {

	private String name;
	private float price;
	private float totalTax;
	private float subTotal;
	// new field added for other charges
//	private float codCharges;
//	private float InsCharges;
//	private float pickupCharges;

	@Digits(integer = 7, fraction = 7)
	@DecimalMin("0.00")
	private float payableAmnt;

	private String subInvoices;
	private String taxPercent;

	@Digits(integer = 7, fraction = 7)
	@DecimalMin("0.00")
	private float calculatedAmnt;

	private UpdatePayableAmnt changedAmnt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}

	public float getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(float subTotal) {
		this.subTotal = subTotal;
	}

//	public float getCodCharges() {
//		return codCharges;
//	}
//
//	public void setCodCharges(float codCharges) {
//		this.codCharges = codCharges;
//	}
//
//	public float getInsCharges() {
//		return InsCharges;
//	}
//
//	public void setInsCharges(float insCharges) {
//		InsCharges = insCharges;
//	}
//
//	public float getPickupCharges() {
//		return pickupCharges;
//	}
//
//	public void setPickupCharges(float pickupCharges) {
//		this.pickupCharges = pickupCharges;
//	}

	public float getPayableAmnt() {
		return payableAmnt;
	}

	public void setPayableAmnt(float payableAmnt) {
		this.payableAmnt = payableAmnt;
	}

	public float getCalculatedAmnt() {
		return calculatedAmnt;
	}

	public void setCalculatedAmnt(float calculatedAmnt) {
		this.calculatedAmnt = calculatedAmnt;
	}

	public UpdatePayableAmnt getChangedAmnt() {
		return changedAmnt;
	}

	public void setChangedAmnt(UpdatePayableAmnt changedAmnt) {
		this.changedAmnt = changedAmnt;
	}

	public String getSubInvoices() {
		return subInvoices;
	}

	public void setSubInvoices(String subInvoices) {
		this.subInvoices = subInvoices;
	}

	public String getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(String taxPercent) {
		this.taxPercent = taxPercent;
	}

}
