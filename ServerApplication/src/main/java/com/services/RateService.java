package com.services;

import com.domain.InvoiceBreakup;
import com.domain.Parcel;
import com.vo.RateCalculation;

public interface RateService {
	
	InvoiceBreakup calculateRate(Parcel parcel);
	void getRate(Parcel parcel);

}
