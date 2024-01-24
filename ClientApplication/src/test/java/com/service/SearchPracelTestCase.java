package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.Application;
import com.constants.PStatus;
import com.controllers.UserController;
import com.domain.Parcel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.repositories.ParcelRepository;
import com.services.impl.ParcelServiceImpl;
import com.vo.ParcelVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class SearchPracelTestCase {
	protected Logger log = LoggerFactory.getLogger(SearchPracelTestCase.class);

	    @InjectMocks
	    ParcelServiceImpl parcelServiceImpl;

	    @Mock
	    ParcelRepository parcelRepository;

	    @Value("${status}")
		String status;

	    Parcel parcel;
	    Parcel parcelObj;
		Parcel parcelObj2;
		ParcelVo parcelVo;

	    @Before
		 public void init()
		 {
	    	parcelObj=new Parcel();
	    	parcelObj2=new Parcel();
	    	parcelVo=new ParcelVo();
		 }

	@Test
	public void getParcelDetailsTest() {

		parcelObj.setTrackId("0110001000003");

		parcelVo.setTrackId(parcelObj.getTrackId());

		Mockito.when(parcelRepository.findParcelByTrackId(parcelObj.getTrackId())).thenReturn(parcelObj);

		assertEquals(parcelVo.getTrackId(), parcelServiceImpl.fetchParcelDetail(parcelObj.getTrackId()).getTrackId());
	}

	@Test
	public void getParcelDetailsTestWhenTrackIdDoesNotExist() {

		parcelObj.setTrackId("0110001000003");

		parcelVo.setTrackId(parcelObj.getTrackId());

		Mockito.when(parcelRepository.findParcelByTrackId(parcelObj.getTrackId())).thenReturn(parcelObj);

		Mockito.when(parcelRepository.findParcelByTrackId("0110001000001")).thenReturn(parcelObj2);

		assertNotEquals(parcelVo, parcelServiceImpl.fetchParcelDetail("0110001000001"));
	}


	@Test
	public void ReprintDetailsTestCaseForBookedStatus() throws JsonMappingException, JsonProcessingException {
		 parcel=new Parcel();

		parcel.setTrackId("0110001000001");
		parcel.setPrintCount(0);
		parcel.setPrintOption("A6");
		parcel.setStatus("booked");
		ReflectionTestUtils.setField(parcelServiceImpl,"status","booked");//For Load the @value value at test execution
		parcel.setpStatus(PStatus.UNBAGGED.toString());
		parcel.setRateCalculationJSON("{\r\n" +
				"  \"finalAmount\" : 0.0,\r\n" +
				"  \"errorMsg\" : null,\r\n" +
				"  \"operationStatus\" : \"SUCCESS\",\r\n" +
				"  \"rateServiceCategory\" : {\r\n" +
				"    \"serviceId\" : 1,\r\n" +
				"    \"priceType\" : \"FLAT\",\r\n" +
				"    \"locationDependency\" : null,\r\n" +
				"    \"weightDependency\" : null,\r\n" +
				"    \"valueDependency\" : null,\r\n" +
				"    \"parentServiceId\" : null,\r\n" +
				"    \"weightMaxLimit\" : null,\r\n" +
				"    \"valueMaxLimit\" : null,\r\n" +
				"    \"price\" : 0.0,\r\n" +
				"    \"taxRate1\" : null,\r\n" +
				"    \"taxRate2\" : null,\r\n" +
				"    \"expectedDelivery\" : 5\r\n" +
				"  },\r\n" +
				"  \"locationWiseRate\" : null,\r\n" +
				"  \"weightWiseRate\" : null,\r\n" +
				"  \"parcelValueWiseRate\" : null,\r\n" +
				"  \"invoiceBreakup\" : {\r\n" +
				"    \"name\" : \"Guaranteed Express Post\",\r\n" +
				"    \"price\" : 0.0,\r\n" +
				"    \"totalTax\" : 0.0,\r\n" +
				"    \"subTotal\" : 5.0,\r\n" +
				"    \"payableAmnt\" : 5.0,\r\n" +
				"    \"subInvoices\" : [ {\r\n" +
				"      \"name\" : \"Cash On Delivery\",\r\n" +
				"      \"price\" : 5.0,\r\n" +
				"      \"totalTax\" : 0.0,\r\n" +
				"      \"subTotal\" : 5.0,\r\n" +
				"      \"payableAmnt\" : 5.0,\r\n" +
				"      \"subInvoices\" : null,\r\n" +
				"      \"taxPercent\" : null\r\n" +
				"    } ],\r\n" +
				"    \"taxPercent\" : null\r\n" +
				"  },\r\n" +
				"  \"subServicesRateCalculation\" : [ {\r\n" +
				"    \"finalAmount\" : 5.0,\r\n" +
				"    \"errorMsg\" : null,\r\n" +
				"    \"operationStatus\" : \"SUCCESS\",\r\n" +
				"    \"rateServiceCategory\" : {\r\n" +
				"      \"serviceId\" : 12,\r\n" +
				"      \"priceType\" : \"FLAT\",\r\n" +
				"      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"weightDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"parentServiceId\" : 1,\r\n" +
				"      \"weightMaxLimit\" : null,\r\n" +
				"      \"valueMaxLimit\" : null,\r\n" +
				"      \"price\" : 5.0,\r\n" +
				"      \"taxRate1\" : null,\r\n" +
				"      \"taxRate2\" : null,\r\n" +
				"      \"expectedDelivery\" : 5\r\n" +
				"    },\r\n" +
				"    \"locationWiseRate\" : null,\r\n" +
				"    \"weightWiseRate\" : null,\r\n" +
				"    \"parcelValueWiseRate\" : null,\r\n" +
				"    \"invoiceBreakup\" : {\r\n" +
				"      \"name\" : \"Cash On Delivery\",\r\n" +
				"      \"price\" : 5.0,\r\n" +
				"      \"totalTax\" : 0.0,\r\n" +
				"      \"subTotal\" : 5.0,\r\n" +
				"      \"payableAmnt\" : 5.0,\r\n" +
				"      \"subInvoices\" : null,\r\n" +
				"      \"taxPercent\" : null\r\n" +
				"    },\r\n" +
				"    \"subServicesRateCalculation\" : null\r\n" +
				"  } ]\r\n" +
				"}");


        Date LastPrintDate=null;
		try {
			LastPrintDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("22-10-2011 17:39:22");
		} catch (ParseException e) {
			log.error("Error in test cases",e);
		}
		parcel.setLastPrintDate(LastPrintDate);

		Mockito.when(parcelRepository.findParcelByTrackId((parcel.getTrackId()))).thenReturn(parcel);

		Parcel parcelObj2=parcelServiceImpl.setReprintDetails(parcel.getTrackId(),parcel.getPrintOption());

		assertEquals(1, parcelObj2.getPrintCount());

		assertNotEquals(LastPrintDate,parcelObj2.getLastPrintDate());

		assertEquals(PStatus.UNBAGGED.toString(), parcelObj2.getpStatus());

		assertEquals("A6", parcelObj2.getPrintOption());
	}


	@Test
	public void ReprintDetailsTestCaseForVoidStatus() throws JsonMappingException, JsonProcessingException {
		parcel=new Parcel();

		parcel.setTrackId("0110001000001");
		parcel.setPrintCount(0);
		parcel.setPrintOption("A5");
		parcel.setStatus("void");
		parcel.setpStatus(PStatus.UNBAGGED.toString());
		parcel.setRateCalculationJSON("{\r\n" +
				"  \"finalAmount\" : 0.0,\r\n" +
				"  \"errorMsg\" : null,\r\n" +
				"  \"operationStatus\" : \"SUCCESS\",\r\n" +
				"  \"rateServiceCategory\" : {\r\n" +
				"    \"serviceId\" : 1,\r\n" +
				"    \"priceType\" : \"FLAT\",\r\n" +
				"    \"locationDependency\" : null,\r\n" +
				"    \"weightDependency\" : null,\r\n" +
				"    \"valueDependency\" : null,\r\n" +
				"    \"parentServiceId\" : null,\r\n" +
				"    \"weightMaxLimit\" : null,\r\n" +
				"    \"valueMaxLimit\" : null,\r\n" +
				"    \"price\" : 0.0,\r\n" +
				"    \"taxRate1\" : null,\r\n" +
				"    \"taxRate2\" : null,\r\n" +
				"    \"expectedDelivery\" : 5\r\n" +
				"  },\r\n" +
				"  \"locationWiseRate\" : null,\r\n" +
				"  \"weightWiseRate\" : null,\r\n" +
				"  \"parcelValueWiseRate\" : null,\r\n" +
				"  \"invoiceBreakup\" : {\r\n" +
				"    \"name\" : \"Guaranteed Express Post\",\r\n" +
				"    \"price\" : 0.0,\r\n" +
				"    \"totalTax\" : 0.0,\r\n" +
				"    \"subTotal\" : 100.0,\r\n" +
				"    \"payableAmnt\" : 100.0,\r\n" +
				"    \"subInvoices\" : [ {\r\n" +
				"      \"name\" : \"To Pay\",\r\n" +
				"      \"price\" : 100.0,\r\n" +
				"      \"totalTax\" : 0.0,\r\n" +
				"      \"subTotal\" : 100.0,\r\n" +
				"      \"payableAmnt\" : 100.0,\r\n" +
				"      \"subInvoices\" : null,\r\n" +
				"      \"taxPercent\" : null\r\n" +
				"    } ],\r\n" +
				"    \"taxPercent\" : null\r\n" +
				"  },\r\n" +
				"  \"subServicesRateCalculation\" : [ {\r\n" +
				"    \"finalAmount\" : 100.0,\r\n" +
				"    \"errorMsg\" : null,\r\n" +
				"    \"operationStatus\" : \"SUCCESS\",\r\n" +
				"    \"rateServiceCategory\" : {\r\n" +
				"      \"serviceId\" : 14,\r\n" +
				"      \"priceType\" : \"FLAT\",\r\n" +
				"      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"weightDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" +
				"      \"parentServiceId\" : 1,\r\n" +
				"      \"weightMaxLimit\" : null,\r\n" +
				"      \"valueMaxLimit\" : null,\r\n" +
				"      \"price\" : 100.0,\r\n" +
				"      \"taxRate1\" : null,\r\n" +
				"      \"taxRate2\" : null,\r\n" +
				"      \"expectedDelivery\" : 5\r\n" +
				"    },\r\n" +
				"    \"locationWiseRate\" : null,\r\n" +
				"    \"weightWiseRate\" : null,\r\n" +
				"    \"parcelValueWiseRate\" : null,\r\n" +
				"    \"invoiceBreakup\" : {\r\n" +
				"      \"name\" : \"To Pay\",\r\n" +
				"      \"price\" : 100.0,\r\n" +
				"      \"totalTax\" : 0.0,\r\n" +
				"      \"subTotal\" : 100.0,\r\n" +
				"      \"payableAmnt\" : 100.0,\r\n" +
				"      \"subInvoices\" : null,\r\n" +
				"      \"taxPercent\" : null\r\n" +
				"    },\r\n" +
				"    \"subServicesRateCalculation\" : null\r\n" +
				"  } ]\r\n" +
				"}");


		Date LastPrintDate=null;
		try {
			LastPrintDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("22-10-2011 17:39:22");
		} catch (ParseException e) {

			log.error("Error in test cases",e);
		}
		parcel.setLastPrintDate(LastPrintDate);

		Mockito.when(parcelRepository.findParcelByTrackId((parcel.getTrackId()))).thenReturn(parcel);

		Parcel parcelObj2=parcelServiceImpl.setReprintDetails(parcel.getTrackId(),parcel.getPrintOption());

		assertEquals(0, parcelObj2.getPrintCount());

        assertEquals(LastPrintDate, parcelObj2.getLastPrintDate());

		assertEquals(PStatus.UNBAGGED.toString(), parcelObj2.getpStatus());

		assertEquals("A5", parcelObj2.getPrintOption());
	}

	@Test
	public void setParcelStatusVoidTestCase() {

		parcel=new Parcel();

		parcel.setTrackId("0110001000002");
		parcel.setPrintCount(0);
		parcel.setPrintOption("A5");
		parcel.setStatus("booked");
		ReflectionTestUtils.setField(parcelServiceImpl,"status","booked");
		Date LastPrintDate=null;
		try {
			LastPrintDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("22-10-2011 17:39:22");
		} catch (ParseException e) {
			log.error("Error in test cases",e);
		}
		parcel.setLastPrintDate(LastPrintDate);

		Mockito.when(parcelRepository.findParcelByTrackId((parcel.getTrackId()))).thenReturn(parcel);

		parcelServiceImpl.setParcelStatusVoid(parcel.getTrackId());

		Mockito.verify(parcelRepository,Mockito.times(1)).save(parcel);

		assertEquals("void",parcel.getStatus());
	}


	@Test
	public void getRecentParcelsTestCase() throws ParseException {

		List<Parcel> list=new ArrayList<Parcel>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		  Parcel parcelObj1=new Parcel();
		  parcelObj1.setId(1l);
		  parcelObj1.setCreatedDate(format.parse("2012-02-21"));

		  Parcel parcelObj2=new Parcel();
		  parcelObj2.setId(2l);
		  parcelObj2.setCreatedDate(format.parse("2012-02-23"));


		  Parcel parcelObj3=new Parcel();
		  parcelObj3.setId(3l);
		  parcelObj3.setCreatedDate(format.parse("2012-02-26") );

		  Parcel parcelObj4=new Parcel();
		  parcelObj4.setId(4l);
		  parcelObj4.setCreatedDate(format.parse("2012-02-27"));

		  Parcel parcelObj5=new Parcel();
		  parcelObj5.setId(5l);
		  parcelObj5.setCreatedDate(new Date());


		list.add(parcelObj1);
		list.add(parcelObj2);
		list.add(parcelObj3);
		list.add(parcelObj4);
		list.add(parcelObj5);

		Mockito.when(parcelRepository.findAll(Sort.by(Direction.DESC, "createdDate"))).thenReturn(list);

		assertEquals(list,parcelServiceImpl.getRecentParcels(5));
	}

	@Test
	public void getRecentParcelsTestCaseWhenMoreThan5ParcelRecords() throws ParseException {

		List<Parcel> list=new ArrayList<Parcel>();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		  Parcel parcelObj1=new Parcel();
		  parcelObj1.setId(1l);
		  parcelObj1.setCreatedDate(format.parse("2012-02-21"));

		  Parcel parcelObj2=new Parcel();
		  parcelObj2.setId(2l);
		  parcelObj2.setCreatedDate(format.parse("2012-02-23"));


		  Parcel parcelObj3=new Parcel();
		  parcelObj3.setId(3l);
		  parcelObj3.setCreatedDate(format.parse("2012-02-26") );

		  Parcel parcelObj4=new Parcel();
		  parcelObj4.setId(4l);
		  parcelObj4.setCreatedDate(format.parse("2012-02-27"));

		  Parcel parcelObj5=new Parcel();
		  parcelObj5.setId(5l);
		  parcelObj5.setCreatedDate(format.parse("2012-02-28"));

		  Parcel parcelObj6=new Parcel();
		  parcelObj6.setId(6l);
		  parcelObj6.setCreatedDate(format.parse("2012-02-29"));

		  Parcel parcelObj7=new Parcel();
		  parcelObj7.setId(7l);
		  parcelObj7.setCreatedDate(new Date());



		list.add(parcelObj1);
		list.add(parcelObj2);
		list.add(parcelObj3);
		list.add(parcelObj4);
		list.add(parcelObj5);
		list.add(parcelObj6);
		list.add(parcelObj7);

		Mockito.when(parcelRepository.findAll(Sort.by(Direction.DESC, "createdDate"))).thenReturn(list);

		assertEquals(5,parcelServiceImpl.getRecentParcels(5).size());
	}


	@Test
	public void getRecentParcelsTestCaseWhenNoParcelExits() throws ParseException {

		List<Parcel> list=new ArrayList<Parcel>();

		Mockito.when(parcelRepository.findAll(Sort.by(Direction.DESC, "createdDate"))).thenReturn(list);

		assertEquals(null,parcelServiceImpl.getRecentParcels(5));

	}

}
