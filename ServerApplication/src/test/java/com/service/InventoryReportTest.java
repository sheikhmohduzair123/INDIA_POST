package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.domain.User;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.services.impl.ReportServiceImpl;
import com.vo.TrackingVo;

@RunWith(MockitoJUnitRunner.class)
public class InventoryReportTest {

	@InjectMocks
	private ReportServiceImpl reportServiceImpl;

	@Mock
	private SUserRepository sUserRepository;

	@Mock
	private TrackingCSRepository trackingCSRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private ParcelRepository parcelRepository;

	@Mock
	private PostalServiceRepository postalServiceRepository;

	// Bags inventory for RMS location
	@Test
	public void GetBagListRMSTest() {
		Role role_rms = new Role();
		role_rms.setName("ROLE_RMS_USER");
		role_rms.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_rms);
		user.setRmsId(1L);

		User user2 = new User();
		user.setRole(role_rms);
		user.setRmsId(1L);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		TrackingCS td = new TrackingCS();
		td.setBagId("123999999");
		td.setBagStatus(BagStatus.IN);
		td.setUpdatedBy(user);

		TrackingCS td1 = new TrackingCS();
		td1.setBagId("123456789");
		td1.setBagStatus(BagStatus.CREATED);
		td1.setUpdatedBy(user);

		TrackingCS td2 = new TrackingCS();
		td2.setBagId("123456789");
		td2.setBagStatus(BagStatus.IN);
		td2.setUpdatedBy(user);

		trackingList.add(td);
		trackingList.add(td1);
		trackingList.add(td2);

		List<BagStatus> status = new ArrayList<BagStatus>();
		status.add(BagStatus.IN);
		status.add(BagStatus.CREATED);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(role_rms);
		Mockito.when(sUserRepository.findByRoleAndRmsId(role_rms, 1L)).thenReturn(userlist);
		Mockito.when(trackingCSRepository.findByUpdatedByInAndBagStatusIn(userlist, status)).thenReturn(trackingList);

		Map<String, List<TrackingVo>> response = reportServiceImpl.getBagList("1", "RMS");

		assertNotNull(response);
		assertEquals(2, response.keySet().size());
		assertEquals(BagStatus.CREATED, response.get("123456789").get(0).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123456789").get(1).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123999999").get(0).getBagStatus());
	}
	
	// Bags inventory for RMS location where list is empty
	@Test
	public void GetBagListNullRMSTest() {
		Role role_rms = new Role();
		role_rms.setName("ROLE_RMS_USER");
		role_rms.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_rms);
		user.setRmsId(1L);

		User user2 = new User();
		user.setRole(role_rms);
		user.setRmsId(1L);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		List<BagStatus> status = new ArrayList<BagStatus>();
		status.add(BagStatus.IN);
		status.add(BagStatus.CREATED);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(role_rms);
		// Mockito.when(sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role_rms,
		// true, 1L, Status.ACTIVE)).thenReturn(userlist);
		// Mockito.when(trackingCSRepository.findByUpdatedByInAndBagStatusIn(userlist,status)).thenReturn(trackingList);

		assertTrue(reportServiceImpl.getBagList("1", "RMS").isEmpty());
	}
	
	// Bags inventory for Post office location
	@Test
	public void GetBagListPostOfficeTest() {
		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		User user2 = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		TrackingCS td = new TrackingCS();
		td.setBagId("123999999");
		td.setBagStatus(BagStatus.IN);
		td.setUpdatedBy(user);

		TrackingCS td1 = new TrackingCS();
		td1.setBagId("123456789");
		td1.setBagStatus(BagStatus.CREATED);
		td1.setUpdatedBy(user);

		TrackingCS td2 = new TrackingCS();
		td2.setBagId("123456789");
		td2.setBagStatus(BagStatus.IN);
		td2.setUpdatedBy(user);

		trackingList.add(td);
		trackingList.add(td1);
		trackingList.add(td2);

		List<BagStatus> status = new ArrayList<BagStatus>();
		status.add(BagStatus.IN);
		status.add(BagStatus.CREATED);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(role_po);
		Mockito.when(sUserRepository.findByRoleAndPostalCode(role_po, 1000)).thenReturn(userlist);
		Mockito.when(trackingCSRepository.findByUpdatedByInAndBagStatusIn(userlist, status)).thenReturn(trackingList);

		Map<String, List<TrackingVo>> response = reportServiceImpl.getBagList("1000", "postoffice");

		assertNotNull(response);
		assertEquals(2, response.keySet().size());
		assertEquals(BagStatus.CREATED, response.get("123456789").get(0).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123456789").get(1).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123999999").get(0).getBagStatus());
	}

	
	// Bags inventory for Post Office location where list is empty
	@Test
	public void GetBagListNullPostOfficeTest() {
		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		User user2 = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		List<BagStatus> status = new ArrayList<BagStatus>();
		status.add(BagStatus.IN);
		status.add(BagStatus.CREATED);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(role_po);
		// Mockito.when(sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role_po,
		// true, 1000, Status.ACTIVE)).thenReturn(userlist);
		// Mockito.when(trackingCSRepository.findByUpdatedByInAndBagStatusIn(userlist,status)).thenReturn(null);

		assertTrue(reportServiceImpl.getBagList("1000", "postoffice").isEmpty());
	}

	// Parcels inventory for Post Office location where list is empty
	@Test
	public void GetParcelListNullPostOfficeTest() {
		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		Role role_fd = new Role();
		role_fd.setName("ROLE_FRONT_DESK_USER");
		role_fd.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		User user2 = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		User user3 = new User();
		user3.setRole(role_fd);
		user3.setPostalCode(1000);
		user3.setId(1);

		User user4 = new User();
		user4.setRole(role_fd);
		user4.setPostalCode(1000);
		user4.setId(2);

		List<User> fduserlist = new ArrayList<User>();
		userlist.add(user3);
		userlist.add(user4);

		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(1);
		userIdList.add(2);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();
		List<Parcel> parcelList = new ArrayList<Parcel>();

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(role_po);
		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(role_fd);
		// Mockito.when(sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role_po,
		// true, 1000, Status.ACTIVE)).thenReturn(userlist).thenReturn(fduserlist);
		Mockito.when(trackingCSRepository.findBypStatusInAndUpdatedByInAndObjParcelNotNull(Mockito.anyList(),
				Mockito.anyList())).thenReturn(trackingList);
		Mockito.when(parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyList())).thenReturn(parcelList);

		assertTrue(reportServiceImpl.getParcelList("1000", "postoffice").isEmpty());
	}

	
	// Parcels inventory for Post Office location where some parcels are bagged & some are unbagged
	@Test
	public void GetBaggedUnbaggedParcelListPostOfficeTest() {
		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		Role role_fd = new Role();
		role_fd.setName("ROLE_FRONT_DESK_USER");
		role_fd.setStatus(Status.ACTIVE);

		User user = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		User user2 = new User();
		user.setRole(role_po);
		user.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		userlist.add(user2);

		User user3 = new User();
		user3.setRole(role_fd);
		user3.setPostalCode(1000);
		user3.setId(1);

		User user4 = new User();
		user4.setRole(role_fd);
		user4.setPostalCode(1000);
		user4.setId(2);

		List<User> fduserlist = new ArrayList<User>();
		userlist.add(user3);
		userlist.add(user4);

		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(1);
		userIdList.add(2);

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.BAGGED.toString());
		p.setRateCalculationJSON("{\r\n" + "  \"finalAmount\" : 0.0,\r\n" + "  \"errorMsg\" : null,\r\n"
				+ "  \"operationStatus\" : \"SUCCESS\",\r\n" + "  \"rateServiceCategory\" : {\r\n"
				+ "    \"serviceId\" : 1,\r\n" + "    \"priceType\" : \"FLAT\",\r\n"
				+ "    \"locationDependency\" : null,\r\n" + "    \"weightDependency\" : null,\r\n"
				+ "    \"valueDependency\" : null,\r\n" + "    \"parentServiceId\" : null,\r\n"
				+ "    \"weightMaxLimit\" : null,\r\n" + "    \"valueMaxLimit\" : null,\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"taxRate1\" : null,\r\n" + "    \"taxRate2\" : null,\r\n"
				+ "    \"expectedDelivery\" : 5\r\n" + "  },\r\n" + "  \"locationWiseRate\" : null,\r\n"
				+ "  \"weightWiseRate\" : null,\r\n" + "  \"parcelValueWiseRate\" : null,\r\n"
				+ "  \"invoiceBreakup\" : {\r\n" + "    \"name\" : \"Guaranteed Express Post\",\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"totalTax\" : 0.0,\r\n" + "    \"subTotal\" : 100.0,\r\n"
				+ "    \"payableAmnt\" : 100.0,\r\n" + "    \"subInvoices\" : [ {\r\n"
				+ "      \"name\" : \"To Pay\",\r\n" + "      \"price\" : 100.0,\r\n" + "      \"totalTax\" : 0.0,\r\n"
				+ "      \"subTotal\" : 100.0,\r\n" + "      \"payableAmnt\" : 100.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    } ],\r\n"
				+ "    \"taxPercent\" : null\r\n" + "  },\r\n" + "  \"subServicesRateCalculation\" : [ {\r\n"
				+ "    \"finalAmount\" : 100.0,\r\n" + "    \"errorMsg\" : null,\r\n"
				+ "    \"operationStatus\" : \"SUCCESS\",\r\n" + "    \"rateServiceCategory\" : {\r\n"
				+ "      \"serviceId\" : 14,\r\n" + "      \"priceType\" : \"FLAT\",\r\n"
				+ "      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"weightDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" + "      \"parentServiceId\" : 1,\r\n"
				+ "      \"weightMaxLimit\" : null,\r\n" + "      \"valueMaxLimit\" : null,\r\n"
				+ "      \"price\" : 100.0,\r\n" + "      \"taxRate1\" : null,\r\n" + "      \"taxRate2\" : null,\r\n"
				+ "      \"expectedDelivery\" : 5\r\n" + "    },\r\n" + "    \"locationWiseRate\" : null,\r\n"
				+ "    \"weightWiseRate\" : null,\r\n" + "    \"parcelValueWiseRate\" : null,\r\n"
				+ "    \"invoiceBreakup\" : {\r\n" + "      \"name\" : \"To Pay\",\r\n" + "      \"price\" : 100.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 100.0,\r\n"
				+ "      \"payableAmnt\" : 100.0,\r\n" + "      \"subInvoices\" : null,\r\n"
				+ "      \"taxPercent\" : null\r\n" + "    },\r\n" + "    \"subServicesRateCalculation\" : null\r\n"
				+ "  } ]\r\n" + "}");

		InvoiceBreakup invoiceObject = new InvoiceBreakup();
		invoiceObject.setName("Guaranteed Express Post");

		p.setInvoiceBreakup(invoiceObject);
		p.setService(1);
		List<Services> subServiceList = new ArrayList<>();

		Services service = new Services();
		service.setId(1L);
		service.setServiceCode("GEP");
		service.setStatus(Status.ACTIVE);

		Services service1 = new Services();
		service1.setId(2L);
		service1.setServiceCode("GL");
		service1.setStatus(Status.ACTIVE);

		subServiceList.add(service);
		subServiceList.add(service1);

		Parcel p1 = new Parcel();
		p1.setTrackId("333333333");
		p1.setStatus("booked");
		p1.setpStatus(PStatus.UNBAGGED.toString());
		p1.setRateCalculationJSON("{\r\n" + "  \"finalAmount\" : 0.0,\r\n" + "  \"errorMsg\" : null,\r\n"
				+ "  \"operationStatus\" : \"SUCCESS\",\r\n" + "  \"rateServiceCategory\" : {\r\n"
				+ "    \"serviceId\" : 1,\r\n" + "    \"priceType\" : \"FLAT\",\r\n"
				+ "    \"locationDependency\" : null,\r\n" + "    \"weightDependency\" : null,\r\n"
				+ "    \"valueDependency\" : null,\r\n" + "    \"parentServiceId\" : null,\r\n"
				+ "    \"weightMaxLimit\" : null,\r\n" + "    \"valueMaxLimit\" : null,\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"taxRate1\" : null,\r\n" + "    \"taxRate2\" : null,\r\n"
				+ "    \"expectedDelivery\" : 5\r\n" + "  },\r\n" + "  \"locationWiseRate\" : null,\r\n"
				+ "  \"weightWiseRate\" : null,\r\n" + "  \"parcelValueWiseRate\" : null,\r\n"
				+ "  \"invoiceBreakup\" : {\r\n" + "    \"name\" : \"Guaranteed Express Post\",\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"totalTax\" : 0.0,\r\n" + "    \"subTotal\" : 5.0,\r\n"
				+ "    \"payableAmnt\" : 5.0,\r\n" + "    \"subInvoices\" : [ {\r\n"
				+ "      \"name\" : \"General Letter Postage\",\r\n" + "      \"price\" : 5.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 5.0,\r\n" + "      \"payableAmnt\" : 5.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    } ],\r\n"
				+ "    \"taxPercent\" : null\r\n" + "  },\r\n" + "  \"subServicesRateCalculation\" : [ {\r\n"
				+ "    \"finalAmount\" : 5.0,\r\n" + "    \"errorMsg\" : null,\r\n"
				+ "    \"operationStatus\" : \"SUCCESS\",\r\n" + "    \"rateServiceCategory\" : {\r\n"
				+ "      \"serviceId\" : 2,\r\n" + "      \"priceType\" : \"VARIABLE\",\r\n"
				+ "      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"weightDependency\" : \"SLAB_WISE\",\r\n"
				+ "      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" + "      \"parentServiceId\" : 1,\r\n"
				+ "      \"weightMaxLimit\" : 100,\r\n" + "      \"valueMaxLimit\" : null,\r\n"
				+ "      \"price\" : null,\r\n" + "      \"taxRate1\" : null,\r\n" + "      \"taxRate2\" : null,\r\n"
				+ "      \"expectedDelivery\" : 5\r\n" + "    },\r\n" + "    \"locationWiseRate\" : null,\r\n"
				+ "    \"weightWiseRate\" : {\r\n" + "      \"weightStartRange\" : 0.0,\r\n"
				+ "      \"weightEndRange\" : 20.0,\r\n" + "      \"weightFractionFactor\" : null,\r\n"
				+ "      \"basePrice\" : 0.0,\r\n" + "      \"price\" : 5.0\r\n" + "    },\r\n"
				+ "    \"parcelValueWiseRate\" : null,\r\n" + "    \"invoiceBreakup\" : {\r\n"
				+ "      \"name\" : \"General Letter Postage\",\r\n" + "      \"price\" : 5.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 5.0,\r\n" + "      \"payableAmnt\" : 5.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    },\r\n"
				+ "    \"subServicesRateCalculation\" : null\r\n" + "  } ]\r\n" + "}");
		p1.setInvoiceBreakup(invoiceObject);
		p1.setService(1);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setBagId("222222222");
		bagdetail.setBagStatus(BagStatus.CREATED);
		bagdetail.setLocationType(LocationType.POST_OFFICE);
		bagdetail.setLocationId(1000L);
		bagdetail.setObjParcel(p);

		trackingList.add(bagdetail);

		List<Parcel> parcelList = new ArrayList<Parcel>();
		parcelList.add(p1);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(role_po);
		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(role_fd);
	//	Mockito.when(sUserRepository.findByRoleAndPostalCode(role_po, 1000)).thenReturn(userlist)
		//		.thenReturn(fduserlist);
		Mockito.when(trackingCSRepository.findBypStatusInAndUpdatedByInAndObjParcelNotNull(Mockito.anyList(),
				Mockito.anyList())).thenReturn(trackingList);

		Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
				.thenReturn(service).thenReturn(service1);

		Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
				.thenReturn(service).thenReturn(service1);

		Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
				.thenReturn(subServiceList);

		Mockito.when(parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyList())).thenReturn(parcelList);

		Map<Parcel, List<TrackingVo>> response = reportServiceImpl.getParcelList("1000", "postoffice");

		assertFalse(response.isEmpty());
		assertEquals(2, response.keySet().size());
		assertEquals("booked", response.get(p).get(0).getObjParcelVo().getStatus());
		assertEquals("UNBAGGED", response.get(p1).get(0).getObjParcelVo().getpStatus());
		assertEquals("BAGGED", response.get(p).get(0).getObjParcelVo().getpStatus());
	}

	// @Ignore
	// Parcels inventory for RMS location where some parcels are bagged
	@Test
	public void GetBaggedUnbaggedParcelListForRMSTest() {
		Role role_rms = new Role();
		role_rms.setName("ROLE_RMS_USER");
		role_rms.setStatus(Status.ACTIVE);

		Role role_fd = new Role();
		role_fd.setName("ROLE_FRONT_DESK_USER");
		role_fd.setStatus(Status.ACTIVE);

		/*
		 * User user = new User(); user.setRole(role_rms); user.setRmsId(1L);
		 *
		 * User user2 = new User(); user2.setRole(role_rms); user2.setRmsId(1L);
		 *
		 * List<User> userlist = new ArrayList<User>(); userlist.add(user);
		 * userlist.add(user2);
		 */

//				User user3 = new User();
//				user3.setRole(role_rms);
//				user3.setRmsId(1L);
//				user3.setId(1);
//
//				User user4 = new User();
//				user4.setRole(role_rms);
//				user4.setRmsId(1L);
//				user4.setId(2);

		/*
		 * List<User> fduserlist = new ArrayList<User>(); userlist.add(user3);
		 * userlist.add(user4);
		 */
		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(1);
		userIdList.add(2);

		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setpStatus(PStatus.BAGGED.toString());
		p.setRateCalculationJSON("{\r\n" + "  \"finalAmount\" : 0.0,\r\n" + "  \"errorMsg\" : null,\r\n"
				+ "  \"operationStatus\" : \"SUCCESS\",\r\n" + "  \"rateServiceCategory\" : {\r\n"
				+ "    \"serviceId\" : 1,\r\n" + "    \"priceType\" : \"FLAT\",\r\n"
				+ "    \"locationDependency\" : null,\r\n" + "    \"weightDependency\" : null,\r\n"
				+ "    \"valueDependency\" : null,\r\n" + "    \"parentServiceId\" : null,\r\n"
				+ "    \"weightMaxLimit\" : null,\r\n" + "    \"valueMaxLimit\" : null,\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"taxRate1\" : null,\r\n" + "    \"taxRate2\" : null,\r\n"
				+ "    \"expectedDelivery\" : 5\r\n" + "  },\r\n" + "  \"locationWiseRate\" : null,\r\n"
				+ "  \"weightWiseRate\" : null,\r\n" + "  \"parcelValueWiseRate\" : null,\r\n"
				+ "  \"invoiceBreakup\" : {\r\n" + "    \"name\" : \"Guaranteed Express Post\",\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"totalTax\" : 0.0,\r\n" + "    \"subTotal\" : 100.0,\r\n"
				+ "    \"payableAmnt\" : 100.0,\r\n" + "    \"subInvoices\" : [ {\r\n"
				+ "      \"name\" : \"To Pay\",\r\n" + "      \"price\" : 100.0,\r\n" + "      \"totalTax\" : 0.0,\r\n"
				+ "      \"subTotal\" : 100.0,\r\n" + "      \"payableAmnt\" : 100.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    } ],\r\n"
				+ "    \"taxPercent\" : null\r\n" + "  },\r\n" + "  \"subServicesRateCalculation\" : [ {\r\n"
				+ "    \"finalAmount\" : 100.0,\r\n" + "    \"errorMsg\" : null,\r\n"
				+ "    \"operationStatus\" : \"SUCCESS\",\r\n" + "    \"rateServiceCategory\" : {\r\n"
				+ "      \"serviceId\" : 14,\r\n" + "      \"priceType\" : \"FLAT\",\r\n"
				+ "      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"weightDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" + "      \"parentServiceId\" : 1,\r\n"
				+ "      \"weightMaxLimit\" : null,\r\n" + "      \"valueMaxLimit\" : null,\r\n"
				+ "      \"price\" : 100.0,\r\n" + "      \"taxRate1\" : null,\r\n" + "      \"taxRate2\" : null,\r\n"
				+ "      \"expectedDelivery\" : 5\r\n" + "    },\r\n" + "    \"locationWiseRate\" : null,\r\n"
				+ "    \"weightWiseRate\" : null,\r\n" + "    \"parcelValueWiseRate\" : null,\r\n"
				+ "    \"invoiceBreakup\" : {\r\n" + "      \"name\" : \"To Pay\",\r\n" + "      \"price\" : 100.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 100.0,\r\n"
				+ "      \"payableAmnt\" : 100.0,\r\n" + "      \"subInvoices\" : null,\r\n"
				+ "      \"taxPercent\" : null\r\n" + "    },\r\n" + "    \"subServicesRateCalculation\" : null\r\n"
				+ "  } ]\r\n" + "}");

		InvoiceBreakup invoiceObject = new InvoiceBreakup();
		invoiceObject.setName("Guaranteed Express Post");

		p.setInvoiceBreakup(invoiceObject);
		p.setService(1);

		List<Services> subServiceList = new ArrayList<>();

		Services service = new Services();
		service.setId(1L);
		service.setServiceCode("GEP");
		service.setStatus(Status.ACTIVE);

		Services service1 = new Services();
		service1.setId(2L);
		service1.setServiceCode("GL");
		service1.setStatus(Status.ACTIVE);

		subServiceList.add(service);
		subServiceList.add(service1);

		Parcel p1 = new Parcel();
		p1.setTrackId("333333333");
		p1.setStatus("booked");
		p1.setpStatus(PStatus.BAGGED.toString());
		p1.setRateCalculationJSON("{\r\n" + "  \"finalAmount\" : 0.0,\r\n" + "  \"errorMsg\" : null,\r\n"
				+ "  \"operationStatus\" : \"SUCCESS\",\r\n" + "  \"rateServiceCategory\" : {\r\n"
				+ "    \"serviceId\" : 1,\r\n" + "    \"priceType\" : \"FLAT\",\r\n"
				+ "    \"locationDependency\" : null,\r\n" + "    \"weightDependency\" : null,\r\n"
				+ "    \"valueDependency\" : null,\r\n" + "    \"parentServiceId\" : null,\r\n"
				+ "    \"weightMaxLimit\" : null,\r\n" + "    \"valueMaxLimit\" : null,\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"taxRate1\" : null,\r\n" + "    \"taxRate2\" : null,\r\n"
				+ "    \"expectedDelivery\" : 5\r\n" + "  },\r\n" + "  \"locationWiseRate\" : null,\r\n"
				+ "  \"weightWiseRate\" : null,\r\n" + "  \"parcelValueWiseRate\" : null,\r\n"
				+ "  \"invoiceBreakup\" : {\r\n" + "    \"name\" : \"Guaranteed Express Post\",\r\n"
				+ "    \"price\" : 0.0,\r\n" + "    \"totalTax\" : 0.0,\r\n" + "    \"subTotal\" : 5.0,\r\n"
				+ "    \"payableAmnt\" : 5.0,\r\n" + "    \"subInvoices\" : [ {\r\n"
				+ "      \"name\" : \"General Letter Postage\",\r\n" + "      \"price\" : 5.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 5.0,\r\n" + "      \"payableAmnt\" : 5.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    } ],\r\n"
				+ "    \"taxPercent\" : null\r\n" + "  },\r\n" + "  \"subServicesRateCalculation\" : [ {\r\n"
				+ "    \"finalAmount\" : 5.0,\r\n" + "    \"errorMsg\" : null,\r\n"
				+ "    \"operationStatus\" : \"SUCCESS\",\r\n" + "    \"rateServiceCategory\" : {\r\n"
				+ "      \"serviceId\" : 2,\r\n" + "      \"priceType\" : \"VARIABLE\",\r\n"
				+ "      \"locationDependency\" : \"NOT_APPLICABLE\",\r\n"
				+ "      \"weightDependency\" : \"SLAB_WISE\",\r\n"
				+ "      \"valueDependency\" : \"NOT_APPLICABLE\",\r\n" + "      \"parentServiceId\" : 1,\r\n"
				+ "      \"weightMaxLimit\" : 100,\r\n" + "      \"valueMaxLimit\" : null,\r\n"
				+ "      \"price\" : null,\r\n" + "      \"taxRate1\" : null,\r\n" + "      \"taxRate2\" : null,\r\n"
				+ "      \"expectedDelivery\" : 5\r\n" + "    },\r\n" + "    \"locationWiseRate\" : null,\r\n"
				+ "    \"weightWiseRate\" : {\r\n" + "      \"weightStartRange\" : 0.0,\r\n"
				+ "      \"weightEndRange\" : 20.0,\r\n" + "      \"weightFractionFactor\" : null,\r\n"
				+ "      \"basePrice\" : 0.0,\r\n" + "      \"price\" : 5.0\r\n" + "    },\r\n"
				+ "    \"parcelValueWiseRate\" : null,\r\n" + "    \"invoiceBreakup\" : {\r\n"
				+ "      \"name\" : \"General Letter Postage\",\r\n" + "      \"price\" : 5.0,\r\n"
				+ "      \"totalTax\" : 0.0,\r\n" + "      \"subTotal\" : 5.0,\r\n" + "      \"payableAmnt\" : 5.0,\r\n"
				+ "      \"subInvoices\" : null,\r\n" + "      \"taxPercent\" : null\r\n" + "    },\r\n"
				+ "    \"subServicesRateCalculation\" : null\r\n" + "  } ]\r\n" + "}");
		p1.setInvoiceBreakup(invoiceObject);
		p1.setService(1);

		List<TrackingCS> trackingList = new ArrayList<TrackingCS>();

		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setBagId("222222222");
		bagdetail.setBagStatus(BagStatus.IN);
		bagdetail.setLocationType(LocationType.RMS);
		bagdetail.setLocationId(1L);
		bagdetail.setObjParcel(p);

		TrackingCS bagdetail1 = new TrackingCS();
		bagdetail1.setBagId("222222222");
		bagdetail1.setBagStatus(BagStatus.IN);
		bagdetail1.setLocationType(LocationType.RMS);
		bagdetail1.setLocationId(1L);
		bagdetail1.setObjParcel(p1);

		trackingList.add(bagdetail);
		trackingList.add(bagdetail1);

		List<Parcel> parcelList = new ArrayList<Parcel>();

		// Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(role_rms);
		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(role_fd);
		// Mockito.when(sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role_rms,
		// true, 1000, Status.ACTIVE)).thenReturn(userlist).thenReturn(fduserlist);
		Mockito.when(trackingCSRepository.findBypStatusInAndUpdatedByInAndObjParcelNotNull(Mockito.anyList(),
				Mockito.anyList())).thenReturn(trackingList);
		Mockito.when(parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyList())).thenReturn(parcelList);
		Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
				.thenReturn(service).thenReturn(service1);
		Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
				.thenReturn(service).thenReturn(service1);
		Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
				.thenReturn(subServiceList);

		Map<Parcel, List<TrackingVo>> response = reportServiceImpl.getParcelList("1000", "postoffice");

		assertFalse(response.isEmpty());
		assertEquals(2, response.keySet().size());
		assertEquals("booked", response.get(p).get(0).getObjParcelVo().getStatus());
		assertEquals("BAGGED", response.get(p1).get(0).getObjParcelVo().getpStatus());
		assertEquals("BAGGED", response.get(p).get(0).getObjParcelVo().getpStatus());
	}
}