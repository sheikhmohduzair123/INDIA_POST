package com.services.impl;

import org.junit.Test;

import com.domain.Address;
import com.domain.InvoiceBreakup;
import com.domain.Parcel;

public class ParcelRateServiceImplTest {
	@Test
	public void testGetRate() {
		Parcel parcel = new Parcel();

		Address senderAddress = new Address();
		senderAddress.setPostalCode(201307);
		Address receiverAddress = new Address();
		receiverAddress.setPostalCode(201304);

		InvoiceBreakup invoiceBreakup = new InvoiceBreakup();
		invoiceBreakup.setName("BP");

		parcel.setParcelDeadWeight(200f);
		parcel.setParcelDeclerationValue(500);
		parcel.setParcelHeight(10f);
		parcel.setParcelLength(10f);
		parcel.setParcelWidth(10f);
		parcel.setSenderAddress(senderAddress);
		parcel.setReceiverAddress(receiverAddress);
		parcel.setInvoiceBreakup(invoiceBreakup);

		ParcelRateServiceImpl service = new ParcelRateServiceImpl();
		parcel = service.getRate(parcel);

		System.out.println(parcel.toString());
		System.out.println(parcel.getRateCalculation().getInvoiceBreakup().getPayableAmnt());
	}
}
