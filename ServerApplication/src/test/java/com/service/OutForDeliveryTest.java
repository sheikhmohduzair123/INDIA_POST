package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.InvoiceBreakup;
import com.domain.Parcel;
import com.domain.Role;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.MasterAddressService;
import com.services.impl.TrackingServiceImpl;
import com.vo.MasterAddressVo;
import com.vo.ParcelVo;

@RunWith(MockitoJUnitRunner.class)
public class OutForDeliveryTest {

	@InjectMocks
	private TrackingServiceImpl trackingServiceImpl;

	@Mock
	private ParcelRepository parcelRepository;

	@Mock
	private TrackingDetailsRepository trackingDetailsRepository;

	@Mock
	private TrackingCSRepository trackingCSRepository;

	@Mock
	private PostalServiceRepository postalServiceRepository;

	@Mock
	private MasterAddressService masterAddress;

	//parcel is void, so cannot be marked outfordelivery
	@Test
	public void parcelVoidGetParcelDetailsTest() {

		Parcel p = new Parcel();
		p.setStatus("void");
		p.setTrackId("123456789");

		Mockito.when(parcelRepository.findParcelByTrackId("123456789")).thenReturn(p);

		assertNull(trackingServiceImpl.findByParcelId("123456789"));

	}

	//parcel is directly sent to outfordelivery, not bagged even once
    @Test
	public void parcelBookedOnlyGetParcelDetailsTest() {

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.UNBAGGED.toString());
		p.setRateCalculationJSON("{\r\n" +
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

		InvoiceBreakup invoiceObject = new InvoiceBreakup();
		invoiceObject.setName("Guaranteed Express Post");

		p.setInvoiceBreakup(invoiceObject);
		p.setService(1);
		
		List<Services> subServiceList = new ArrayList<>();
		Services service = new Services();
		service.setId(1L);
		service.setServiceCode("GEP");
		service.setStatus(Status.ACTIVE);
		subServiceList.add(service);

		Mockito.when(parcelRepository.findParcelByTrackId("123456789")).thenReturn(p);
		Mockito.when(trackingCSRepository.findByObjParcelAndStatus(p, Status.ACTIVE)).thenReturn(null);
		Mockito.when(postalServiceRepository.findByIdAndStatus(1L, Status.ACTIVE)).thenReturn(service);
		//Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
	//	.thenReturn(service);
		Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
		.thenReturn(subServiceList);

		
		ParcelVo response = trackingServiceImpl.findByParcelId("123456789");

		assertNotNull(response);
		assertEquals("123456789",response.getTrackId());
		assertEquals("booked",response.getStatus());
		assertEquals("UNBAGGED",response.getpStatus());
	}

	//parcel is bagged & sent to outfordelivery
	@Test
	public void parcelBaggedGetParcelDetailsTest() {

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.BAGGED.toString());
		p.setRateCalculationJSON("{\r\n" +
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

		InvoiceBreakup invoiceObject = new InvoiceBreakup();
		invoiceObject.setName("Guaranteed Express Post");

		p.setInvoiceBreakup(invoiceObject);
		p.setService(1);

		List<Services> subServiceList = new ArrayList<>();
		Services service = new Services();
		service.setId(1L);
		service.setServiceCode("GEP");
		service.setStatus(Status.ACTIVE);
		
		subServiceList.add(service);

		TrackingCS t = new TrackingCS();
		t.setObjParcel(p);
		t.setBagId("987654321");
		t.setpStatus(PStatus.BAGGED);
		t.setStatus(Status.ACTIVE);

		Mockito.when(parcelRepository.findParcelByTrackId("123456789")).thenReturn(p);
		Mockito.when(trackingCSRepository.findByObjParcelAndStatus(p, Status.ACTIVE)).thenReturn(t);
		Mockito.when(postalServiceRepository.findByIdAndStatus(1L, Status.ACTIVE)).thenReturn(service);
	//	Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
		//		.thenReturn(service);
		Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
		.thenReturn(subServiceList);

		ParcelVo response = trackingServiceImpl.findByParcelId("123456789");

		assertNotNull(response);
		assertEquals("123456789",response.getTrackId());
		assertEquals("BAGGED",response.getpStatus());
		assertEquals(Status.ACTIVE.toString(),response.getStatus());
	}

	//parcel only  booked, not bagged sent for out for delivery
	@Test
	public void updateOutForDeliveryForBookedParcelTest() {

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.UNBAGGED.toString());

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000L);

		MasterAddressVo masterAddrssVo = new MasterAddressVo();
		masterAddrssVo.setPostalCode(1000);
		masterAddrssVo.setSubOffice("SubOffice");
		masterAddrssVo.setThana("Thana");

		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setObjParcel(p);
		bagdetail.setpStatus(PStatus.OUT_FOR_DELIVERY);

		TrackingDetails bagdetail1 = new TrackingDetails();
		bagdetail1.setObjParcel(p);
		bagdetail1.setpStatus(PStatus.OUT_FOR_DELIVERY);

		Mockito.when(masterAddress.getPostalData(1000)).thenReturn(masterAddrssVo);
		Mockito.when(parcelRepository.findParcelByTrackId("123456789")).thenReturn(p);
		Mockito.when(trackingCSRepository.findByObjParcelAndStatus(p, Status.ACTIVE)).thenReturn(null);

		Mockito.when(parcelRepository.save(p)).thenReturn(p);
		Mockito.when(trackingDetailsRepository.save(Mockito.any(TrackingDetails.class))).thenReturn(bagdetail1);
		Mockito.when(trackingCSRepository.save(Mockito.any(TrackingCS.class))).thenReturn(bagdetail);

		List<String> parcelid = new ArrayList<>();
		parcelid.add("123456789");

		List<TrackingCS> response = trackingServiceImpl.updateOutForDelivery(parcelid, loginuser);

		assertEquals(1,response.size());
		assertEquals("123456789", response.get(0).getObjParcel().getTrackId());
		assertEquals(PStatus.OUT_FOR_DELIVERY, response.get(0).getpStatus());
		assertEquals("OUT_FOR_DELIVERY", response.get(0).getObjParcel().getpStatus());
	}

	//parcel bagged sent for out for delivery
	@Test
	public void updateOutForDeliveryForBaggedParcelTest() {

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.UNBAGGED.toString());

		Parcel p1 = new Parcel();
		p1.setTrackId("333333333");

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000L);

		MasterAddressVo masterAddrssVo = new MasterAddressVo();
		masterAddrssVo.setPostalCode(1000);
		masterAddrssVo.setSubOffice("SubOffice");
		masterAddrssVo.setThana("Thana");

		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setBagId("222222222");
		bagdetail.setBagStatus(BagStatus.CREATED);
		bagdetail.setLocationType(LocationType.POST_OFFICE);
		bagdetail.setLocationId(1000L);
		bagdetail.setObjParcel(p);

		TrackingCS bag2 = new TrackingCS();
		bag2.setBagId("222222222");
		bag2.setBagStatus(BagStatus.OUT);
		bag2.setLocationType(LocationType.POST_OFFICE);
		bag2.setLocationId(1000L);
		bag2.setStatus(Status.ACTIVE);
		bag2.setObjParcel(p1);

		List<TrackingCS> baglist = new ArrayList<TrackingCS>();
		baglist.add(bagdetail);
		baglist.add(bag2);

		TrackingDetails bagdetail1 = new TrackingDetails();
		bagdetail1.setBagId("222222222");
		bagdetail1.setBagStatus(BagStatus.OUT);
		bagdetail1.setLocationType(LocationType.POST_OFFICE);
		bagdetail1.setLocationId(1000L);
		bagdetail1.setStatus(Status.ACTIVE);
		bagdetail1.setObjParcel(p);

		TrackingDetails bagdetail2 = new TrackingDetails();
		bagdetail2.setBagId("222222222");
		bagdetail2.setBagStatus(BagStatus.OUT);
		bagdetail2.setLocationType(LocationType.POST_OFFICE);
		bagdetail2.setLocationId(1000L);
		bagdetail2.setStatus(Status.ACTIVE);
		bagdetail2.setObjParcel(p1);

		List<TrackingDetails> bagdetaillist1 = new ArrayList<TrackingDetails>();
		bagdetaillist1.add(bagdetail1);
		bagdetaillist1.add(bagdetail2);

		Mockito.when(masterAddress.getPostalData(1000)).thenReturn(masterAddrssVo);
		Mockito.when(parcelRepository.findParcelByTrackId("123456789")).thenReturn(p);
		Mockito.when(trackingCSRepository.findByObjParcelAndStatus(p, Status.ACTIVE)).thenReturn(bagdetail);
		Mockito.when(trackingDetailsRepository.findByObjParcelAndStatus(p, Status.ACTIVE)).thenReturn(bagdetail1);
		Mockito.when(trackingDetailsRepository.findByBagIdAndStatus("222222222",Status.ACTIVE)).thenReturn(bagdetaillist1);
		Mockito.when(trackingCSRepository.findByBagIdAndStatus("222222222",Status.ACTIVE)).thenReturn(baglist);

		Mockito.when(trackingDetailsRepository.save(bagdetail1)).thenReturn(bagdetail1);
		Mockito.when(trackingCSRepository.save(bagdetail)).thenReturn(bagdetail);
		Mockito.when(parcelRepository.save(p)).thenReturn(p);
		Mockito.when(trackingDetailsRepository.save(bagdetail2)).thenReturn(bagdetail2);
		Mockito.when(trackingCSRepository.save(bag2)).thenReturn(bag2);

		List<String> parcelid = new ArrayList<>();
		parcelid.add("123456789");

		List<TrackingCS> response = trackingServiceImpl.updateOutForDelivery(parcelid, loginuser);
		assertEquals(1,response.size());
		assertEquals("123456789", response.get(0).getObjParcel().getTrackId());
		assertEquals(PStatus.OUT_FOR_DELIVERY, response.get(0).getpStatus());
	}

	//empty bag list sent for out for delivery
		@Test
		public void updateOutForDeliveryEmptyListTest() {
			Role role_po = new Role();
			role_po.setName("ROLE_PO_USER");
			role_po.setStatus(Status.ACTIVE);

			User loginuser = new User();
			loginuser.setRole(role_po);
			loginuser.setPostalCode(1000L);

			List<String> parcelid = new ArrayList<>();

			assertTrue(trackingServiceImpl.updateOutForDelivery(parcelid, loginuser).isEmpty());
		}



}