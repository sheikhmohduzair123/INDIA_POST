package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
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
import com.domain.Address;
import com.domain.InvoiceBreakup;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.Role;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.domain.Zone;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.ReportRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.MasterAddressService;
import com.services.ReportService;
import com.services.impl.ParcelServiceImpl;
import com.services.impl.ReportServiceImpl;
import com.services.impl.TrackingServiceImpl;
import com.vo.MasterAddressVo;
import com.vo.ParcelVo;

@RunWith(MockitoJUnitRunner.class)
public class DailSummaryCapacityManagementCollectionReportTest {

	@InjectMocks
	private ParcelServiceImpl parcelServiceImpl;

	@Mock
	private MasterAddressRepository masterAddressRepository;

	@Mock
	private ReportRepository reportRepository;
	
	@Mock
	private PostalServiceRepository postalServiceRepository;

	@InjectMocks
	private ReportServiceImpl reportServiceimpl;

	List<MasterAddress> masterAddressesList;
	List<MasterAddress> masterAddressesEmptyList;

	@Before
	public void init() {
		masterAddressesList = new ArrayList<MasterAddress>();

		MasterAddress m = new MasterAddress();
		m.setSubOffice("suboffice");
		m.setPostalCode(1000);
		m.setThana("thana");
		m.setDistrict("district");
		m.setDivision("division");
		m.setZone("zone");

		masterAddressesList.add(m);

		masterAddressesEmptyList = new ArrayList<MasterAddress>();
	}

	@Test
	public void getDistinctZoneTest() {
		List<String> zonelist = new ArrayList<String>();
		zonelist.add("zone1");
		zonelist.add("zone2");

		Mockito.when(masterAddressRepository.findDistinctZone(Status.ACTIVE)).thenReturn(zonelist);
    	List<String> list = parcelServiceImpl.getDistinctZone();

		assertEquals(2, list.size());
		assertEquals("zone1", list.get(0));
		assertEquals("zone2", list.get(1));
	}

	@Test
	public void getDistinctDivisionTest() {
		List<String> divisionlist = new ArrayList<String>();
		divisionlist.add("division1");
		divisionlist.add("division2");

		Mockito.when(masterAddressRepository.findDistinctDivision(Status.ACTIVE)).thenReturn(divisionlist);
    	List<String> list = parcelServiceImpl.getDistinctDivision();

		assertEquals(2, list.size());
		assertEquals("division1", list.get(0));
		assertEquals("division2", list.get(1));
	}

	@Test
	public void getDistinctDistrictTest() {
		List<String> districtlist = new ArrayList<String>();
		districtlist.add("district1");
		districtlist.add("district2");

		Mockito.when(masterAddressRepository.findDistinctDistrict(Status.ACTIVE)).thenReturn(districtlist);
    	List<String> list = parcelServiceImpl.getDistinctDistrict();

		assertEquals(2, list.size());
		assertEquals("district1", list.get(0));
		assertEquals("district2", list.get(1));
	}

	@Test
	public void getDistinctThanaTest() {
		List<String> thanalist = new ArrayList<String>();
		thanalist.add("thana1");
		thanalist.add("thana2");

		Mockito.when(masterAddressRepository.findDistinctThana(Status.ACTIVE)).thenReturn(thanalist);
    	List<String> list = parcelServiceImpl.getDistinctThana();

		assertEquals(2, list.size());
		assertEquals("thana1", list.get(0));
		assertEquals("thana2", list.get(1));
	}

	@Test
	public void getDistinctSubOfficeTest() {

		MasterAddress m1 = new MasterAddress();
		m1.setSubOffice("suboffice1");
		m1.setPostalCode(2000);

		masterAddressesList.add(m1);

		Mockito.when(masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE)).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getSubOffice();

		assertEquals(2, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("suboffice1", list.get(1).getSubOffice());
		assertEquals(1000, list.get(0).getPostalCode());
		assertEquals(2000, list.get(1).getPostalCode());
	}

	@Test
	public void getDistinctZoneNullTest() {
		Mockito.when(masterAddressRepository.findDistinctZone(Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctZone());
	}

	@Test
	public void getDistinctDivisionNullTest() {
		Mockito.when(masterAddressRepository.findDistinctDivision(Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctDivision());
	}

	@Test
	public void getDistinctDistrictNullTest() {
		Mockito.when(masterAddressRepository.findDistinctDistrict(Status.ACTIVE)).thenReturn(null);
		assertNull(parcelServiceImpl.getDistinctDistrict());
	}

	@Test
	public void getDistinctThanaNullTest() {
		Mockito.when(masterAddressRepository.findDistinctThana(Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctThana());
	}

	@Test
	public void getDistinctSubOfficeNullTest() {
		Mockito.when(masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE)).thenReturn(masterAddressesEmptyList);
		assertEquals(0, parcelServiceImpl.getSubOffice().size());
	}

	@Test
	public void getAddressBySubOfficeTest() {
		Mockito.when(masterAddressRepository.findBySubOfficeAndStatus("subOffice", Status.ACTIVE)).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getAddressBySubOffice("subOffice");
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
		assertEquals(1000, list.get(0).getPostalCode());
	}

	@Test
	public void getAddressBySubOfficeNullTest() {
		Mockito.when(masterAddressRepository.findBySubOfficeAndStatus("subOffice", Status.ACTIVE)).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressBySubOffice("subOffice").size());
	}

	@Test
	public void getAddressByThanaOnlyTest() {
		Mockito.when(masterAddressRepository.findByThanaAndStatus("thana", Status.ACTIVE)).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getAddressByThanaOnly("thana");
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
		assertEquals(0, list.get(0).getPostalCode());  //postal code is not set in VO, so default value is 0
	}

	@Test
	public void getAddressByThanaOnlyNullTest() {
		Mockito.when(masterAddressRepository.findByThanaAndStatus("thana", Status.ACTIVE)).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressByThanaOnly("thana").size());
	}

	@Test
	public void getAddressByDistrictTest() {
		Mockito.when(masterAddressRepository.findByDistrictIgnoreCase("district")).thenReturn(masterAddressesList);
    	List<MasterAddress> list = parcelServiceImpl.getAddressByDistrict("district");
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
		assertEquals(1000, list.get(0).getPostalCode());
	}

	@Test
	public void getAddressByDistrictNullTest() {
		Mockito.when(masterAddressRepository.findByDistrictIgnoreCase("district")).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressByDistrict("district").size());
	}

	@Test
	public void getAddressByDivisionTest() {
		Mockito.when(masterAddressRepository.findAddressByDivisionIgnoreCase("division")).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getAddressByDivision("division");
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
		assertEquals(0, list.get(0).getPostalCode());  //postal code is not set in VO, so default value is 0
	}

	@Test
	public void getAddressByDivisionNullTest() {
		Mockito.when(masterAddressRepository.findAddressByDivisionIgnoreCase("division")).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressByDivision("division").size());
	}

	@Test
	public void getDistinctDivisionByZoneTest() {
		List<String> divisionlist = new ArrayList<String>();
		divisionlist.add("division1");
		divisionlist.add("division2");

		Mockito.when(masterAddressRepository.findDistinctDivisionByZone("zone", Status.ACTIVE)).thenReturn(divisionlist);
    	List<String> list = parcelServiceImpl.getDistinctDivisionByZone("zone");

		assertEquals(2, list.size());
		assertEquals("division1", list.get(0));
		assertEquals("division2", list.get(1));
	}

	@Test
	public void getDistinctDivisionByZoneNullTest() {
		Mockito.when(masterAddressRepository.findDistinctDivisionByZone("zone", Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctDivisionByZone("zone"));
	}

	@Test
	public void getDistinctDistrictByDivisionTest() {
		List<String> districtlist = new ArrayList<String>();
		districtlist.add("district1");
		districtlist.add("district2");

		Mockito.when(masterAddressRepository.findDistinctDistrictByDivision("division", Status.ACTIVE)).thenReturn(districtlist);
    	List<String> list = parcelServiceImpl.getDistinctDistrictByDivision("division");

		assertEquals(2, list.size());
		assertEquals("district1", list.get(0));
		assertEquals("district2", list.get(1));
	}

	@Test
	public void getDistinctDistrictByDivisionNullTest() {
		Mockito.when(masterAddressRepository.findDistinctDistrictByDivision("division", Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctDistrictByDivision("division"));
	}

	@Test
	public void getDistinctThanaByDistrictTest() {
		List<String> thanalist = new ArrayList<String>();
		thanalist.add("thana1");
		thanalist.add("thana2");

		Mockito.when(masterAddressRepository.findDistinctThanaByDistrict("district", Status.ACTIVE)).thenReturn(thanalist);
    	List<String> list = parcelServiceImpl.getDistinctThanaByDistrict("district");

		assertEquals(2, list.size());
		assertEquals("thana1", list.get(0));
		assertEquals("thana2", list.get(1));
	}

	@Test
	public void getDistinctThanaByDistrictNullTest() {
		Mockito.when(masterAddressRepository.findDistinctThanaByDistrict("district", Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctThanaByDistrict("district"));
	}

	@Test
	public void getSubOfficeByThanaTest() {

		MasterAddress m1 = new MasterAddress();
		m1.setSubOffice("suboffice1");
		m1.setPostalCode(2000);

		masterAddressesList.add(m1);

		Mockito.when(masterAddressRepository.findAllByThanaAndStatusOrderBySubOfficeAsc("thana", Status.ACTIVE)).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getSubOfficeByThana("thana");

		assertEquals(2, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("suboffice1", list.get(1).getSubOffice());
		assertEquals(1000, list.get(0).getPostalCode());
		assertEquals(2000, list.get(1).getPostalCode());
	}

	@Test
	public void getSubOfficeByThanaNullTest() {

		Mockito.when(masterAddressRepository.findAllByThanaAndStatusOrderBySubOfficeAsc("thana", Status.ACTIVE)).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getSubOfficeByThana("thana").size());
	}

	//get all data without grouping
	@Test
	public void getAllSummaryWithouGroupingTest() throws ParseException {
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

		
		Parcel p = new Parcel();
		p.setStatus("booked");
		p.setTrackId("123456789");
		p.setCreatedDate(new Date(2020-05-10));
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
		p.setService(1l);

		Parcel p1 = new Parcel();
		p1.setStatus("void");
		p1.setTrackId("111111111");
		p1.setCreatedDate(new Date(2020-05-10));
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

		invoiceObject.setName("Guaranteed Express Post");

		p1.setInvoiceBreakup(invoiceObject);
		p1.setService(1l);

		List<Parcel> parcellist = new ArrayList<Parcel>();
		parcellist.add(p);
		parcellist.add(p1);

		Mockito.when(reportRepository.findByCreatedDateBetweenAndStatusIn(Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
		Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
		.thenReturn(service).thenReturn(service1);
		Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
		.thenReturn(subServiceList);
		Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
		.thenReturn(service).thenReturn(service1);


		Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","All","All","All","All","All","All Parcels","Select Aggregate By");
		assertNotNull(response);
		assertEquals(2, response.keySet().size());
		assertEquals(new Date(2020-05-10), response.get("123456789").get(0).getCreatedDate());
		assertEquals(new Date(2020-05-10), response.get("111111111").get(0).getCreatedDate());
	}

		//get all data empty list without grouping
		@Test
		public void getEmptyListAllDataDailySummaryTest() throws ParseException {
			List<Parcel> parcellist = new ArrayList<Parcel>();

			Mockito.when(reportRepository.findByCreatedDateBetweenAndStatusIn(Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
			assertTrue(reportServiceimpl.getsummary("2010-05-10","All","All","All","All","All","All Parcels","Select Aggregate By").isEmpty());
		}

		//get all data for a division without grouping
		@Test
		public void getAllSummaryForDivisionWithoutGroupingTest() throws ParseException {
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

			
			Address a = new Address();
			a.setZone("zone");
			a.setDivision("division");
			a.setSubOffice("subOffice");

			Parcel p = new Parcel();
			p.setStatus("booked");
			p.setTrackId("123456789");
			p.setSenderAddress(a);
			p.setCreatedDate(new Date(2020-05-10));
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
			p.setService(1l);

			Address a1 = new Address();
			a1.setZone("zone");
			a1.setDivision("division");
			a1.setSubOffice("subOffice1");

			Parcel p1 = new Parcel();
			p1.setStatus("void");
			p1.setTrackId("111111111");
			p1.setSenderAddress(a1);
			p1.setCreatedDate(new Date(2020-05-10));
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

			invoiceObject.setName("Guaranteed Express Post");

			p1.setInvoiceBreakup(invoiceObject);
			p1.setService(1l);

			List<Parcel> parcellist = new ArrayList<Parcel>();
			parcellist.add(p);
			parcellist.add(p1);

			Mockito.when(masterAddressRepository.findPostalCodeByZoneAndDivisionAndStatus("zone","division",Status.ACTIVE)).thenReturn(masterAddressesList);
			Mockito.when(reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
			Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
			.thenReturn(service).thenReturn(service1);
			Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
			.thenReturn(subServiceList);
			Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
			.thenReturn(service).thenReturn(service1);

			
			Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","zone","All","division","All","All","All Parcels","Select Aggregate By");
			assertNotNull(response);
			assertEquals(2, response.keySet().size());
			assertEquals(new Date(2020-05-10), response.get("123456789").get(0).getCreatedDate());
			assertEquals(new Date(2020-05-10), response.get("111111111").get(0).getCreatedDate());
			assertEquals(a, response.get("123456789").get(0).getSenderAddress());
			assertEquals("division", response.get("111111111").get(0).getSenderAddress().getDivision());
		}

		//get all data for a zone with group by suboffice
		@Test
		public void getAllSummaryForZoneWithGroupBySubOfficeTest() throws ParseException {
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

			
			Address a = new Address();
			a.setZone("zone");
			a.setSubOffice("subOffice");

			Parcel p = new Parcel();
			p.setStatus("booked");
			p.setTrackId("123456789");
			p.setSenderAddress(a);
			p.setCreatedDate(new Date(2020-05-10));
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
			p.setService(1l);

			Address a1 = new Address();
			a1.setZone("zone");
			a1.setSubOffice("subOffice1");

			Parcel p1 = new Parcel();
			p1.setStatus("void");
			p1.setTrackId("111111111");
			p1.setSenderAddress(a1);
			p1.setCreatedDate(new Date(2020-05-10));
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

			invoiceObject.setName("Guaranteed Express Post");

			p1.setInvoiceBreakup(invoiceObject);
			p1.setService(1l);

			List<Parcel> parcellist = new ArrayList<Parcel>();
			parcellist.add(p);
			parcellist.add(p1);

			Mockito.when(masterAddressRepository.findPostalCodeByZoneAndStatus("zone",Status.ACTIVE)).thenReturn(masterAddressesList);
			Mockito.when(reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
			Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
			.thenReturn(service).thenReturn(service1);
			Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
			.thenReturn(subServiceList);
			Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
			.thenReturn(service).thenReturn(service1);

			
			Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","zone","All","All","All","All","All Parcels","Sub Office");
			assertNotNull(response);
			assertEquals(2, response.keySet().size());
			assertEquals(new Date(2020-05-10), response.get("subOffice").get(0).getCreatedDate());
			assertEquals(new Date(2020-05-10), response.get("subOffice1").get(0).getCreatedDate());
			assertEquals(a, response.get("subOffice").get(0).getSenderAddress());
			assertEquals("zone", response.get("subOffice1").get(0).getSenderAddress().getZone());
		}

		//get all data for a district with group by thana
		@Test
		public void getAllSummaryForDistrictWithGroupByThanaTest() throws ParseException {
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
	
			Address a = new Address();
					a.setZone("zone");
					a.setThana("thana");
					a.setDistrict("district");
					a.setSubOffice("subOffice");

					Parcel p = new Parcel();
					p.setStatus("booked");
					p.setTrackId("123456789");
					p.setSenderAddress(a);
					p.setCreatedDate(new Date(2020-05-10));
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
					p.setService(1l);

					Address a1 = new Address();
					a1.setZone("zone");
					a1.setThana("thana1");
					a.setDistrict("district");
					a1.setSubOffice("subOffice1");

					Parcel p1 = new Parcel();
					p1.setStatus("void");
					p1.setTrackId("111111111");
					p1.setSenderAddress(a1);
					p1.setCreatedDate(new Date(2020-05-10));
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

					invoiceObject.setName("Guaranteed Express Post");

					p1.setInvoiceBreakup(invoiceObject);
					p1.setService(1l);

					List<Parcel> parcellist = new ArrayList<Parcel>();
					parcellist.add(p);
					parcellist.add(p1);

					Mockito.when(masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndStatus("zone","division","district",Status.ACTIVE)).thenReturn(masterAddressesList);
					Mockito.when(reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
					Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);
					Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
					.thenReturn(subServiceList);
					Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);

					
					Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","zone","district","division","All","All","All Parcels","Thana");
					assertNotNull(response);
					assertEquals(2, response.keySet().size());
					assertEquals(new Date(2020-05-10), response.get("thana").get(0).getCreatedDate());
					assertEquals(new Date(2020-05-10), response.get("thana1").get(0).getCreatedDate());
					assertEquals(a, response.get("thana").get(0).getSenderAddress());
					assertEquals("zone", response.get("thana1").get(0).getSenderAddress().getZone());
				}

		//get all data for a thana with group by suboffice
		@Test
		public void getAllSummaryForThanaWithGroupBySubOfficeTest() throws ParseException {
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
		
			Address a = new Address();
					a.setZone("zone");
					a.setThana("thana");
					a.setDistrict("district");
					a.setSubOffice("subOffice");

					Parcel p = new Parcel();
					p.setStatus("booked");
					p.setTrackId("123456789");
					p.setSenderAddress(a);
					p.setCreatedDate(new Date(2020-05-10));
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
					p.setService(1l);

					Address a1 = new Address();
					a1.setZone("zone");
					a1.setThana("thana1");
					a.setDistrict("district");
					a1.setSubOffice("subOffice1");

					Parcel p1 = new Parcel();
					p1.setStatus("void");
					p1.setTrackId("111111111");
					p1.setSenderAddress(a1);
					p1.setCreatedDate(new Date(2020-05-10));
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

					invoiceObject.setName("Guaranteed Express Post");

					p1.setInvoiceBreakup(invoiceObject);
					p1.setService(1l);

					List<Parcel> parcellist = new ArrayList<Parcel>();
					parcellist.add(p);
					parcellist.add(p1);

					Mockito.when(masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndStatus("zone","division","district","thana",Status.ACTIVE)).thenReturn(masterAddressesList);
					Mockito.when(reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
					Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);
					Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
					.thenReturn(subServiceList);
					Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);

					
					Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","zone","district","division","thana","All","All Parcels","Sub Office");
					assertNotNull(response);
					assertEquals(2, response.keySet().size());
					assertEquals(new Date(2020-05-10), response.get("subOffice").get(0).getCreatedDate());
					assertEquals(new Date(2020-05-10), response.get("subOffice1").get(0).getCreatedDate());
					assertEquals(a, response.get("subOffice").get(0).getSenderAddress());
					assertEquals("zone", response.get("subOffice1").get(0).getSenderAddress().getZone());
				}

		//get all data for a suboffice without group by
		@Test
		public void getAllSummaryForSubOfficeWithoutGroupByTest() throws ParseException {
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

			
					Address a = new Address();
					a.setZone("zone");
					a.setThana("thana");
					a.setDistrict("district");
					a.setSubOffice("subOffice");

					Parcel p = new Parcel();
					p.setStatus("booked");
					p.setTrackId("123456789");
					p.setSenderAddress(a);
					p.setCreatedDate(new Date(2020-05-10));
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
					p.setService(1l);

					Parcel p1 = new Parcel();
					p1.setStatus("void");
					p1.setTrackId("111111111");
					p1.setSenderAddress(a);
					p1.setCreatedDate(new Date(2020-05-10));
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

					invoiceObject.setName("Guaranteed Express Post");

					p1.setInvoiceBreakup(invoiceObject);
					p1.setService(1l);

					List<Parcel> parcellist = new ArrayList<Parcel>();
					parcellist.add(p);
					parcellist.add(p1);

					Mockito.when(masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndStatus("zone","division","district","thana","subOffice",Status.ACTIVE)).thenReturn(masterAddressesList);
					Mockito.when(reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.anyList())).thenReturn(parcellist);
					Mockito.when(postalServiceRepository.findByIdAndStatus(Mockito.anyLong(), Mockito.any())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);
					Mockito.when(postalServiceRepository.findByIdInAndStatus(Mockito.anyList(), Mockito.any()))
					.thenReturn(subServiceList);
					Mockito.when(postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(Mockito.anyLong())).thenReturn(service)
					.thenReturn(service).thenReturn(service1);

					
					Map<String, List<ParcelVo>> response  = reportServiceimpl.getsummary("2010-05-10","zone","district","division","thana","subOffice","All Parcels","Select Aggregate By");
					assertNotNull(response);
					assertEquals(2, response.keySet().size());
					assertEquals(new Date(2020-05-10), response.get("123456789").get(0).getCreatedDate());
					assertEquals(new Date(2020-05-10), response.get("111111111").get(0).getCreatedDate());
					assertEquals(a, response.get("123456789").get(0).getSenderAddress());
					assertEquals("zone", response.get("111111111").get(0).getSenderAddress().getZone());
				}
}