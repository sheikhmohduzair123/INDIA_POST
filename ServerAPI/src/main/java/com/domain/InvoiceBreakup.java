package com.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import com.constants.UpdatePayableAmnt;

@Embeddable
public class InvoiceBreakup {
	
	private String name;
	private float price;
	private float totalTax;
	private float subTotal;
	private float payableAmnt;
	//extra field added for other charges
//	private float codCharges;
//	private float InsCharges;
//	private float pickupCharges;

	private List<InvoiceBreakup> subInvoices;
	private String taxPercent;
	private float calculatedAmnt;
	private UpdatePayableAmnt changedAmnt;
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float general) {
		this.price = general;
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
	public float getPayableAmnt() {
		return payableAmnt;
	}
	public void setPayableAmnt(float payableAmnt) {
		this.payableAmnt = payableAmnt;
	}
	@Transient
	public List<InvoiceBreakup> getSubInvoices() {
		return subInvoices;
	}
	public void setSubInvoices(List<InvoiceBreakup> subInvoices) {
		this.subInvoices = subInvoices;
	}
	public String getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(String taxPercent) {
		this.taxPercent = taxPercent;
	}
	/*
	public float getCodCharges() {
		return codCharges;
	}
	public void setCodCharges(float codCharges) {
		this.codCharges = codCharges;
	}
	public float getInsCharges() {
		return InsCharges;
	}
	public void setInsCharges(float insCharges) {
		InsCharges = insCharges;
	}
	public float getPickupCharges() {
		return pickupCharges;
	}
	public void setPickupCharges(float pickupCharges) {
		this.pickupCharges = pickupCharges;
	}

	*/

}
