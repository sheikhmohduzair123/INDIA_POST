package com.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import com.constants.LocationDependency;
import com.constants.PriceType;
import com.constants.Status;
import com.constants.ValueDependency;
import com.constants.WeightDependency;
import com.domain.Address;
import com.domain.LocationWiseRate;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.ParcelValueWiseRate;
import com.domain.RateServiceCategory;
import com.domain.RateTable;
import com.domain.Services;
import com.domain.WeightWiseRate;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RateTableRepository;
import com.services.impl.RateServiceImpl;
import com.util.OperationStatus;

@RunWith(MockitoJUnitRunner.class)
public class RateServiceImplTest {

	@InjectMocks
	private RateServiceImpl rateServiceImpl;

	@Mock
	private RateTableRepository rateTableRepository;

	@Mock
	private PostalServiceRepository postalServiceRepository;

	@Mock
	private MasterAddressRepository masterAddressRepository;

	@Test
	public void getRateNoService() {

		Parcel p = new Parcel();
		Parcel response = rateServiceImpl.getRate(p);
		assertEquals(p.getService(),response.getService());
		assertEquals(0,response.getService());
	}

	//only main service present & corresponding rate table entry not found & actwt set to parceldeadweight because parceldeadweight greater than volumetricweight
	@Test
	public void getRateNoServicePresentinRateTableNoSubServiceAndVolumetricWeightLessThanParcelDeadWeight() {

		Parcel p = new Parcel();
		p.setParcelLength(2);
		p.setParcelHeight(3);
		p.setParcelWidth(4);
		p.setParcelDeadWeight(50);
		p.setService(1);

		Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(null);

		ReflectionTestUtils.setField(rateServiceImpl,"idNotPresent","No Entry found corresponding to selected service");

		Parcel response = rateServiceImpl.getRate(p);
		assertEquals(p.getService(),response.getService());
		assertNotNull(response.getActWt());
		assertEquals(50, response.getActWt());
		assertNotNull(response.getRateCalculation());
		assertNotNull(response.getRateCalculationJSON());
		assertNull(response.getSubServices());
		assertNull(response.getInvoiceBreakup());
		assertNull(response.getRateCalculation().getInvoiceBreakup());
		assertEquals(OperationStatus.ID_NOT_PRESENT_IN_DB, response.getRateCalculation().getOperationStatus());
		assertEquals("No Entry found corresponding to selected service", response.getRateCalculation().getErrorMsg());
	}

	//only main service present & corresponding rate table entry not found & actwt set to volumetricweight because parceldeadweight less than volumetricweight
	@Test
	public void getRateNoServicePresentinRateTableNoSubServiceAndVolumetricWeightGreaterThanParcelDeadWeight() {

		Parcel p = new Parcel();
		p.setParcelLength(20);
		p.setParcelHeight(30);
		p.setParcelWidth(40);
		p.setParcelDeadWeight(2);
		p.setService(1);

		Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(null);

		ReflectionTestUtils.setField(rateServiceImpl,"idNotPresent","No Entry found corresponding to selected service");

		Parcel response = rateServiceImpl.getRate(p);
		assertEquals(p.getService(),response.getService());
		assertNotNull(response.getActWt());
		assertEquals(4.8f, response.getActWt());
		assertNotNull(response.getRateCalculation());
		assertNotNull(response.getRateCalculationJSON());
		assertNull(response.getSubServices());
		assertNull(response.getInvoiceBreakup());
		assertNull(response.getRateCalculation().getInvoiceBreakup());
		assertEquals(OperationStatus.ID_NOT_PRESENT_IN_DB, response.getRateCalculation().getOperationStatus());
		assertEquals("No Entry found corresponding to selected service", response.getRateCalculation().getErrorMsg());
	}

	   //only main service present & weight greater than weight max limit
		@Test
		public void getRateMainServicePresentNoSubServiceWeightGreaterThanAllowed() {

			Parcel p = new Parcel();
			p.setParcelLength(2);
			p.setParcelHeight(3);
			p.setParcelWidth(4);
			p.setParcelDeadWeight(90);
			p.setService(1);

			RateServiceCategory rateServiceCategory = new RateServiceCategory();
			rateServiceCategory.setWeightMaxLimit(50L);

			RateTable rt = new RateTable();
			rt.setRateServiceCategory(rateServiceCategory);

			List<RateTable> rateList = new ArrayList<RateTable>();
			rateList.add(rt);

			Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

			ReflectionTestUtils.setField(rateServiceImpl,"highWeight","Parcel Weight is more than the maximum limit of selected service");

			Parcel response = rateServiceImpl.getRate(p);
			assertEquals(p.getService(),response.getService());
			assertNotNull(response.getActWt());
			assertEquals(90f, response.getActWt());
			assertNotNull(response.getRateCalculation());
			assertNotNull(response.getRateCalculationJSON());
			assertNull(response.getSubServices());
			assertNull(response.getInvoiceBreakup());
			assertNull(response.getRateCalculation().getInvoiceBreakup());
			assertEquals(OperationStatus.HIGH_WEIGHT_NOT_ACCEPTABLE, response.getRateCalculation().getOperationStatus());
			assertEquals("Parcel Weight is more than the maximum limit of selected service", response.getRateCalculation().getErrorMsg());
		}

		   //only main service present & parcel value declaration greater than volume max limit
			@Test
			public void getRateMainServicePresentNoSubServiceVolumeGreaterThanAllowed() {

				Parcel p = new Parcel();
				p.setParcelLength(2);
				p.setParcelHeight(3);
				p.setParcelWidth(4);
				p.setParcelDeadWeight(90);
				p.setService(1);
				p.setParcelDeclerationValue(120);

				RateServiceCategory rateServiceCategory = new RateServiceCategory();
				rateServiceCategory.setValueMaxLimit(100L);

				RateTable rt = new RateTable();
				rt.setRateServiceCategory(rateServiceCategory);

				List<RateTable> rateList = new ArrayList<RateTable>();
				rateList.add(rt);

				Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

				ReflectionTestUtils.setField(rateServiceImpl,"highValue","Parcel Value is more than the maximum limit of selected service");

				Parcel response = rateServiceImpl.getRate(p);
				assertEquals(p.getService(),response.getService());
				assertNotNull(response.getActWt());
				assertEquals(90f, response.getActWt());
				assertNotNull(response.getRateCalculation());
				assertNotNull(response.getRateCalculationJSON());
				assertNull(response.getSubServices());
				assertNull(response.getInvoiceBreakup());
				assertNull(response.getRateCalculation().getInvoiceBreakup());
				assertEquals(OperationStatus.HIGH_VALUE_NOT_ACCEPTABLE, response.getRateCalculation().getOperationStatus());
				assertEquals("Parcel Value is more than the maximum limit of selected service", response.getRateCalculation().getErrorMsg());
			}

			   //only main service present & price type flat & no tax available
				@Test
				public void getRateMainServicePriceTypeFlatWithNoTax() {

					Parcel p = new Parcel();
					p.setParcelLength(2);
					p.setParcelHeight(3);
					p.setParcelWidth(4);
					p.setParcelDeadWeight(90);
					p.setService(1);
					p.setParcelDeclerationValue(120);

					RateServiceCategory rateServiceCategory = new RateServiceCategory();
					rateServiceCategory.setPriceType(PriceType.FLAT);
					rateServiceCategory.setPrice(20f);
					rateServiceCategory.setServiceId(1L);

					RateTable rt = new RateTable();
					rt.setRateServiceCategory(rateServiceCategory);

					List<RateTable> rateList = new ArrayList<RateTable>();
					rateList.add(rt);
					Services s =new Services();

					Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
					Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

					Parcel response = rateServiceImpl.getRate(p);
					assertEquals(p.getService(),response.getService());
					assertNotNull(response.getActWt());
					assertEquals(90f, response.getActWt());
					assertNotNull(response.getRateCalculation());
					assertNotNull(response.getRateCalculationJSON());
					assertNull(response.getSubServices());
					assertNotNull(response.getInvoiceBreakup());
					assertNotNull(response.getRateCalculation().getInvoiceBreakup());
					assertEquals(20, response.getInvoiceBreakup().getPayableAmnt());
					assertEquals(20, response.getInvoiceBreakup().getPrice());
					assertEquals(20, response.getInvoiceBreakup().getSubTotal());
					assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
					assertEquals(20, response.getRateCalculation().getFinalAmount());
				}

				   //only main service present & price type flat with tax1
					@Test
					public void getRateMainServicePriceTypeFlatWithTax1() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);
						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getSubServices());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(22, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(20, response.getInvoiceBreakup().getPrice());
						assertEquals(22, response.getInvoiceBreakup().getSubTotal());
						assertEquals(2, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(20, response.getRateCalculation().getFinalAmount());
					}

					  //only main service present & price type flat with tax2
					@Test
					public void getRateMainServicePriceTypeFlatWithTax2Only() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate2(10f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getSubServices());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(22, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(20, response.getInvoiceBreakup().getPrice());
						assertEquals(22, response.getInvoiceBreakup().getSubTotal());
						assertEquals(2, response.getInvoiceBreakup().getTotalTax());
						assertEquals("SGST : 10.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(20, response.getRateCalculation().getFinalAmount());
					}

					//only main service present & price type flat with tax1 & tax 2
					@Test
					public void getRateMainServicePriceTypeFlatWithTax1AndTax2() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);
						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getSubServices());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(23, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(20, response.getInvoiceBreakup().getPrice());
						assertEquals(23, response.getInvoiceBreakup().getSubTotal());
						assertEquals(3, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(20, response.getRateCalculation().getFinalAmount());
					}

					//only main service present & price type flat with tax1 & error: both cod & topay selected
					@Test
					public void getRateErrorToPayAndCodBothSelectedAsSubServices() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						List<Long> Idlist = new ArrayList<Long>(); //subservice list
						Idlist.add(2L);
						Idlist.add(3L);

						p.setSubServices(Idlist);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Services sub2 =new Services();
						sub2.setId(2L);
						sub2.setParentServiceId(1L);
						sub2.setServiceCode("COD");
						sub2.setServiceName("Cash On Delivery");

						Services sub3 =new Services();
						sub3.setId(3L);
						sub3.setParentServiceId(1L);
						sub3.setServiceCode("TP");
						sub3.setServiceName("To Pay");

						List<Services> subservicelist = new ArrayList<Services>();
						subservicelist.add(sub2);
						subservicelist.add(sub3);

						Mockito.when(postalServiceRepository.findByIdInAndStatus(Idlist, Status.ACTIVE)).thenReturn(subservicelist);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"codTpNotTogether","Please select either COD or To Pay Service. Both the services cannot be selected simultaneously.");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNull(response.getRateCalculationJSON());
						assertEquals(subservicelist.size(),response.getSubServices().size());
						assertEquals(Idlist.get(0),response.getSubServices().get(0));
						assertEquals(Idlist.get(1),response.getSubServices().get(1));
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.COD_TP_SELECTED, response.getRateCalculation().getOperationStatus());
						assertEquals("Please select either COD or To Pay Service. Both the services cannot be selected simultaneously.", response.getRateCalculation().getErrorMsg());
					}

					//both main service & sub-service (cod) present & price type flat with tax1 & tax2
					@Test
					public void getRateMainServicePriceTypeFlatWithTax1AndTax2WithSubServicesCod() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						List<Long> Idlist = new ArrayList<Long>(); //subservice list
						Idlist.add(2L);
						Idlist.add(3L);

						p.setSubServices(Idlist);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						RateServiceCategory rateSubServiceCategory1 = new RateServiceCategory();
						rateSubServiceCategory1.setPriceType(PriceType.FLAT);
						rateSubServiceCategory1.setPrice(200f);
						rateSubServiceCategory1.setServiceId(2L);
						rateSubServiceCategory1.setTaxRate1(10f);
						rateSubServiceCategory1.setTaxRate2(5f);

						RateTable rt1 = new RateTable();
						rt1.setRateServiceCategory(rateSubServiceCategory1);

						List<RateTable> rateList1 = new ArrayList<RateTable>();
						rateList1.add(rt1);

						RateServiceCategory rateSubServiceCategory2 = new RateServiceCategory();
						rateSubServiceCategory2.setPriceType(PriceType.FLAT);
						rateSubServiceCategory2.setPrice(100f);
						rateSubServiceCategory2.setServiceId(3L);
						rateSubServiceCategory2.setTaxRate1(10f);
						rateSubServiceCategory2.setTaxRate2(5f);

						RateTable rt2 = new RateTable();
						rt2.setRateServiceCategory(rateSubServiceCategory2);

						List<RateTable> rateList2 = new ArrayList<RateTable>();
						rateList2.add(rt2);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Services s1 =new Services();
						s1.setServiceName("Cash On Delivery");

						Services s2 =new Services();
						s2.setServiceName("Registered Packets");


						Services sub2 =new Services();
						sub2.setId(2L);
						sub2.setParentServiceId(1L);
						sub2.setServiceCode("COD");
						sub2.setServiceName("Cash On Delivery");

						Services sub3 =new Services();
						sub3.setId(3L);
						sub3.setParentServiceId(1L);
						sub3.setServiceCode("RP");
						sub3.setServiceName("Registered Packets");

						List<Services> subservicelist = new ArrayList<Services>();
						subservicelist.add(sub2);
						subservicelist.add(sub3);

						Mockito.when(postalServiceRepository.findByIdInAndStatus(Idlist, Status.ACTIVE)).thenReturn(subservicelist);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(2L,Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList1);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(3L,Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList2);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt1.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s1));
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt2.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s2));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertEquals(subservicelist.size(),response.getSubServices().size());
						assertEquals(Idlist.get(0),response.getSubServices().get(0));
						assertEquals(Idlist.get(1),response.getSubServices().get(1));
						assertNotNull(response.getInvoiceBreakup());
						assertTrue(response.isCod());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(368, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(20, response.getInvoiceBreakup().getPrice());
						assertEquals(368, response.getInvoiceBreakup().getSubTotal());
						assertEquals(48, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(20, response.getRateCalculation().getFinalAmount());

						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation());
						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup());
						assertEquals(230, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getPayableAmnt());
						assertEquals(200, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getPrice());
						assertEquals(230, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getSubTotal());
						assertEquals(30, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getTaxPercent());
						assertEquals("Cash On Delivery", response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getSubServicesRateCalculation().get(0).getOperationStatus());
						assertEquals(200, response.getRateCalculation().getSubServicesRateCalculation().get(0).getFinalAmount());

						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation());
						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup());
						assertEquals(115, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getPayableAmnt());
						assertEquals(100, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getPrice());
						assertEquals(115, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getSubTotal());
						assertEquals(15, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getTaxPercent());
						assertEquals("Registered Packets", response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getSubServicesRateCalculation().get(1).getOperationStatus());
						assertEquals(100, response.getRateCalculation().getSubServicesRateCalculation().get(1).getFinalAmount());
					}


					//both main service & sub-service (topay) present & price type flat with tax1 & tax2
					@Test
					public void getRateMainServicePriceTypeFlatWithTax1AndTax2WithSubServicesToPay() {

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(120);

						List<Long> Idlist = new ArrayList<Long>(); //subservice list
						Idlist.add(2L);
						Idlist.add(3L);

						p.setSubServices(Idlist);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.FLAT);
						rateServiceCategory.setPrice(20f);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						RateServiceCategory rateSubServiceCategory1 = new RateServiceCategory();
						rateSubServiceCategory1.setPriceType(PriceType.FLAT);
						rateSubServiceCategory1.setPrice(200f);
						rateSubServiceCategory1.setServiceId(2L);
						rateSubServiceCategory1.setTaxRate1(10f);
						rateSubServiceCategory1.setTaxRate2(5f);

						RateTable rt1 = new RateTable();
						rt1.setRateServiceCategory(rateSubServiceCategory1);

						List<RateTable> rateList1 = new ArrayList<RateTable>();
						rateList1.add(rt1);

						RateServiceCategory rateSubServiceCategory2 = new RateServiceCategory();
						rateSubServiceCategory2.setPriceType(PriceType.FLAT);
						rateSubServiceCategory2.setPrice(100f);
						rateSubServiceCategory2.setServiceId(3L);
						rateSubServiceCategory2.setTaxRate1(10f);
						rateSubServiceCategory2.setTaxRate2(5f);

						RateTable rt2 = new RateTable();
						rt2.setRateServiceCategory(rateSubServiceCategory2);

						List<RateTable> rateList2 = new ArrayList<RateTable>();
						rateList2.add(rt2);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Services s1 =new Services();
						s1.setServiceName("To Pay");

						Services s2 =new Services();
						s2.setServiceName("Registered Packets");


						Services sub2 =new Services();
						sub2.setId(2L);
						sub2.setParentServiceId(1L);
						sub2.setServiceCode("TP");
						sub2.setServiceName("To Pay");

						Services sub3 =new Services();
						sub3.setId(3L);
						sub3.setParentServiceId(1L);
						sub3.setServiceCode("RP");
						sub3.setServiceName("Registered Packets");

						List<Services> subservicelist = new ArrayList<Services>();
						subservicelist.add(sub2);
						subservicelist.add(sub3);

						Mockito.when(postalServiceRepository.findByIdInAndStatus(Idlist, Status.ACTIVE)).thenReturn(subservicelist);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(2L,Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList1);
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(3L,Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList2);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt1.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s1));
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt2.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s2));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertEquals(subservicelist.size(),response.getSubServices().size());
						assertEquals(Idlist.get(0),response.getSubServices().get(0));
						assertEquals(Idlist.get(1),response.getSubServices().get(1));
						assertNotNull(response.getInvoiceBreakup());
						assertTrue(response.isToPay());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(368, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(20, response.getInvoiceBreakup().getPrice());
						assertEquals(368, response.getInvoiceBreakup().getSubTotal());
						assertEquals(48, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(20, response.getRateCalculation().getFinalAmount());

						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation());
						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup());
						assertEquals(230, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getPayableAmnt());
						assertEquals(200, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getPrice());
						assertEquals(230, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getSubTotal());
						assertEquals(30, response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getTaxPercent());
						assertEquals("To Pay", response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getSubServicesRateCalculation().get(0).getOperationStatus());
						assertEquals(200, response.getRateCalculation().getSubServicesRateCalculation().get(0).getFinalAmount());

						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation());
						assertNotNull(response.getRateCalculation().getSubServicesRateCalculation().get(0).getInvoiceBreakup());
						assertEquals(115, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getPayableAmnt());
						assertEquals(100, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getPrice());
						assertEquals(115, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getSubTotal());
						assertEquals(15, response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getTaxPercent());
						assertEquals("Registered Packets", response.getRateCalculation().getSubServicesRateCalculation().get(1).getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getSubServicesRateCalculation().get(1).getOperationStatus());
						assertEquals(100, response.getRateCalculation().getSubServicesRateCalculation().get(1).getFinalAmount());
					}


					//main service present & price type variable, location dependency: district but error corresponding location not found, value & weight dependency: slabwise
					@Test
					public void getRateWithAllThreeDepenedenciesLocationErrorTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setDistrict("district");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setDistrict("district1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.DISTRICT);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(4);
						loc.setToId(7);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(30f);
						wgt.setWeightEndRange(100f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setSubOffice("suboffice");
						m.setPostalCode(1000);
						m.setDistrict("district");
						m.setDistrictId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setSubOffice("suboffice1");
						m1.setPostalCode(1100);
						m1.setDistrict("district1");
						m1.setDistrictId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(masterAddressRepository.findDistinctByDistrictAndStatus(p.getSenderAddress().getDistrict(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByDistrictAndStatus(p.getReceiverAddress().getDistrict(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"noDataPresent","Some data is missing either Weight, Value or Location corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("Some data is missing either Weight, Value or Location corresponding to the selected service", response.getRateCalculation().getErrorMsg());
					}

					//main service  & price type variable, location dependency: thana, value & weight dependency: multiplier, base price non zero
					@Test
					public void getRateWithAllThreeDepenedenciesTypeMultiplierAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setThana("thana");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						senderaddress.setThana("thana1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.THANA);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(30f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(20f);
						wgt.setWeightFractionFactor(10f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);
						prcl.setBasePrice(30f);
						prcl.setValueFraction(5f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setThana("thana");
						m.setPostalCode(1000);
						m.setThanaId(1L);
						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m.setThana("thana1");
						m1.setPostalCode(1100);
						m1.setThanaId(2L);
						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getSenderAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getReceiverAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(670, response.getRateCalculation().getFinalAmount());
						assertEquals(770.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(670, response.getInvoiceBreakup().getPrice());
						assertEquals(770.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(100.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
					}

					//main service  & price type variable, location dependency: thana, value & weight dependency: multiplier, base price zero
					@Test
					public void getRateWithAllThreeDepenedenciesTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setThana("thana");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						senderaddress.setThana("thana1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.THANA);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(30f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(0f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);
						prcl.setBasePrice(0f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setThana("thana");
						m.setPostalCode(1000);
						m.setThanaId(1L);
						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m.setThana("thana1");
						m1.setPostalCode(1100);
						m1.setThanaId(2L);
						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getSenderAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getReceiverAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(100, response.getRateCalculation().getFinalAmount());
						assertEquals(115, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(100, response.getInvoiceBreakup().getPrice());
						assertEquals(115, response.getInvoiceBreakup().getSubTotal());
						assertEquals(15, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
					}

					//main service  & price type variable, location dependency: thana, value & weight dependency: slab wise
					@Test
					public void getRateWithAllThreeDepenedenciesTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setThana("thana");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						senderaddress.setThana("thana1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.THANA);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(30f);
						wgt.setWeightEndRange(100f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setThana("thana");
						m.setPostalCode(1000);
						m.setThanaId(1L);
						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m.setThana("thana1");
						m1.setPostalCode(1100);
						m1.setThanaId(2L);
						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getSenderAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByThanaAndStatus(p.getReceiverAddress().getThana(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(100, response.getRateCalculation().getFinalAmount());
						assertEquals(115, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(100, response.getInvoiceBreakup().getPrice());
						assertEquals(115, response.getInvoiceBreakup().getSubTotal());
						assertEquals(15, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
					}

					//main service present & price type variable, location dependency: zone, weight dependency: slabwise
					@Test
					public void getRateWithLocationAndWeightDepenedenciesTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(80.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(70, response.getInvoiceBreakup().getPrice());
						assertEquals(80.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(10.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(70, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: zone, weight dependency: multiplier base price zero
					@Test
					public void getRateWithLocationAndWeightDepenedenciesTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(0f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(80.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(70, response.getInvoiceBreakup().getPrice());
						assertEquals(80.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(10.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(70, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: zone, weight dependency: multiplier base price non zero
					@Test
					public void getRateWithLocationAndWeightDepenedenciesTypeMultiplierBasePriceNonZeroAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(15f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(50f);
						wgt.setWeightFractionFactor(25f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(230, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(200, response.getInvoiceBreakup().getPrice());
						assertEquals(230, response.getInvoiceBreakup().getSubTotal());
						assertEquals(30, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(200, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: from to id not present ERROR, weight dependency: slabwise
					@Test
					public void getRateWithLocationDepenedencyError() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(11);
						loc.setToId(22);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"noDataPresent","Some data is missing either Weight, Value or Location corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.LOCATION_DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("Some data is missing either Weight, Value or Location corresponding to the selected service", response.getRateCalculation().getErrorMsg());
					}

					//main service present & price type variable, value & weight dependency: slabwise
					@Test
					public void getRateWithWeightAndValueDepenedenciesTypeSlabWiseAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(20f);
						wgt.setWeightEndRange(100f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(69, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(60, response.getInvoiceBreakup().getPrice());
						assertEquals(69, response.getInvoiceBreakup().getSubTotal());
						assertEquals(9, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(60, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, value & weight dependency: slabwise, ERROR: data not present
					@Test
					public void getRateWithWeightAndValueDepenedenciesErrorTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(20f);
						wgt.setWeightEndRange(50f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
					}

					//main service present & price type variable, value & weight dependency: multiplier, base price zero
					@Test
					public void getRateWithWeightAndValueDepenedenciesTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(20f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(0f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);
						prcl.setBasePrice(0f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(69, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(60, response.getInvoiceBreakup().getPrice());
						assertEquals(69, response.getInvoiceBreakup().getSubTotal());
						assertEquals(9, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(60, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, value & weight dependency: multiplier, base price non zero
					@Test
					public void getRateWithWeightAndValueDepenedenciesTypeMultiplierBasePriceNonZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(20f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(30f);
						wgt.setWeightFractionFactor(5f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(40f);
						prcl.setBasePrice(10f);
						prcl.setValueFraction(10f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(644, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(560, response.getInvoiceBreakup().getPrice());
						assertEquals(644, response.getInvoiceBreakup().getSubTotal());
						assertEquals(84, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(560, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: division, value dependency: slabwise
					@Test
					public void getRateWithLocationAndValueDepenedencyTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setDivision("division");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setDivision("division1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.DIVISIONAL);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1000);
						loc.setToId(1100);
						loc.setPrice(35f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(45f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(92, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(80, response.getInvoiceBreakup().getPrice());
						assertEquals(92, response.getInvoiceBreakup().getSubTotal());
						assertEquals(12, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(80, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: division, value dependency: multiplier, base price zero
					@Test
					public void getRateWithLocationAndValueDepenedencyTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setDivision("division");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setDivision("division1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.DIVISIONAL);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1000);
						loc.setToId(1100);
						loc.setPrice(35f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(45f);
						prcl.setBasePrice(0f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(92, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(80, response.getInvoiceBreakup().getPrice());
						assertEquals(92, response.getInvoiceBreakup().getSubTotal());
						assertEquals(12, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(80, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: division, value dependency: multiplier, base price non zero
					@Test
					public void getRateWithLocationAndValueDepenedencyTypeMultiplierBasePriceNonZeroAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setDivision("division");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setDivision("division1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.DIVISIONAL);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1000);
						loc.setToId(1100);
						loc.setPrice(35f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(45f);
						prcl.setBasePrice(20f);
						prcl.setValueFraction(20f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(166.75, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(145, response.getInvoiceBreakup().getPrice());
						assertEquals(166.75, response.getInvoiceBreakup().getSubTotal());
						assertEquals(21.75, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(145, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, location dependency: zone
					@Test
					public void getRateWithLocationDepenedencyTypeSlabWiseAndPriceTypeVariable() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1);
						loc.setToId(2);
						loc.setPrice(30f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(34.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(30, response.getInvoiceBreakup().getPrice());
						assertEquals(34.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(4.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(30, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, weight dependency: slabwise
					@Test
					public void getRateWithWeightDepenedenciesTypeSlabWiseAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(46.0, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(40, response.getInvoiceBreakup().getPrice());
						assertEquals(46.0, response.getInvoiceBreakup().getSubTotal());
						assertEquals(6.0, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(40, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, value dependency: slabwise
					@Test
					public void getRateWithValueDepenedencyTypeSlabWiseAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(30f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(34.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(30, response.getInvoiceBreakup().getPrice());
						assertEquals(34.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(4.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(30, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, value dependency: slabwise, ERROR
					@Test
					public void getRateWithValueDepenedencyError() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(50);
						prcl.setValueUpToRange(100);
						prcl.setPrice(30f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

						ReflectionTestUtils.setField(rateServiceImpl,"noValue","No Value data found corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.PRICE_DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("No Value data found corresponding to the selected service", response.getRateCalculation().getErrorMsg());
					}

					//main service present & price type variable, weight dependency: slabwise, ERROR
					@Test
					public void getRateWithWeightDepenedenciesError() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.SLAB_WISE);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(60f);
						wgt.setWeightEndRange(50f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

						ReflectionTestUtils.setField(rateServiceImpl,"noWeight","No Weight data found corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.WEIGHT_DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("No Weight data found corresponding to the selected service", response.getRateCalculation().getErrorMsg());

					}

					//main service present & price type variable, location dependency: zone, ERROR
					@Test
					public void getRateWithLocationDepenedencyOnlyError() {
						Address senderaddress = new Address();
						senderaddress.setZone("zone");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setZone("zone1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.ZONAL);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(3);
						loc.setToId(4);
						loc.setPrice(30f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						List<MasterAddress> senderaddresslist = new ArrayList<MasterAddress>();

						MasterAddress m = new MasterAddress();
						m.setPostalCode(1000);
						m.setZone("zone");
						m.setZoneId(1L);

						senderaddresslist.add(m);

						List<MasterAddress> rcvraddresslist = new ArrayList<MasterAddress>();

						MasterAddress m1 = new MasterAddress();
						m1.setPostalCode(1100);
						m1.setZone("zone1");
						m1.setZoneId(2L);

						rcvraddresslist.add(m1);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getSenderAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(senderaddresslist);
						Mockito.when(masterAddressRepository.findDistinctByZoneAndStatus(p.getReceiverAddress().getZone(), PageRequest.of(0,1), Status.ACTIVE)).thenReturn(rcvraddresslist);

						ReflectionTestUtils.setField(rateServiceImpl,"noLocation","No Location data found corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.LOCATION_DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("No Location data found corresponding to the selected service", response.getRateCalculation().getErrorMsg());
					}

					//main service present & price type variable, location dependency: division but ERROR, value dependency: slabwise
					@Test
					public void getRateWithLocationAndValueDepenedencyTypeSlabWiseAndPriceTypeVariableLocationError() {
						Address senderaddress = new Address();
						senderaddress.setDivision("division");
						senderaddress.setPostalCode(1000);

						Address rcvraddress = new Address();
						rcvraddress.setDivision("division1");
						rcvraddress.setPostalCode(1100);

						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						p.setSenderAddress(senderaddress);
						p.setReceiverAddress(rcvraddress);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setLocationDependency(LocationDependency.DIVISIONAL);
						rateServiceCategory.setValueDependency(ValueDependency.SLAB_WISE);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						LocationWiseRate loc = new LocationWiseRate();
						loc.setFromId(1000);
						loc.setToId(1209);
						loc.setPrice(35f);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setPrice(45f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setLocationWiseRate(loc);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);

						ReflectionTestUtils.setField(rateServiceImpl,"noDataPresent","Some data is missing either Weight, Value or Location corresponding to the selected service");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNull(response.getInvoiceBreakup());
						assertNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(OperationStatus.DATA_NOT_PRESENT, response.getRateCalculation().getOperationStatus());
						assertEquals("Some data is missing either Weight, Value or Location corresponding to the selected service", response.getRateCalculation().getErrorMsg());
				}
					//main service present & price type variable, weight dependency: multiplier, Base Price Zero
					@Test
					public void getRateWithWeightDepenedenciesTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);

						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(0f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(46.0, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(40, response.getInvoiceBreakup().getPrice());
						assertEquals(46.0, response.getInvoiceBreakup().getSubTotal());
						assertEquals(6.0, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(40, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, value dependency:multiplier, Base Price Zero
					@Test
					public void getRateWithValueDepenedencyTypeMultiplierBasePriceZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);

						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);

						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setBasePrice(0f);
						prcl.setPrice(30f);

						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setParcelValueWiseRate(prcl);

						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);

						Services s =new Services();
						s.setServiceName("Guaranteed Express");

						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));

						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");

						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(34.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(30, response.getInvoiceBreakup().getPrice());
						assertEquals(34.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(4.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(30, response.getRateCalculation().getFinalAmount());
					}

					//main service present & price type variable, weight dependency: multiplier, Base Price Non Zero
					@Test
					public void getRateWithWeightDepenedenciesTypeMultiplierBasePriceNonZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.NOT_APPLICABLE);
						rateServiceCategory.setWeightDependency(WeightDependency.MULTIPLIER);
						WeightWiseRate wgt = new WeightWiseRate();
						wgt.setWeightStartRange(1f);
						wgt.setPrice(40f);
						wgt.setWeightEndRange(100f);
						wgt.setBasePrice(15f);
						wgt.setWeightFractionFactor(10f);
						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setWeightWiseRate(wgt);
						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);
						Services s =new Services();
						s.setServiceName("Guaranteed Express");
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");
						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(431.25, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(375, response.getInvoiceBreakup().getPrice());
						assertEquals(431.25, response.getInvoiceBreakup().getSubTotal());
						assertEquals(56.25, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(375, response.getRateCalculation().getFinalAmount());
					}
					//main service present & price type variable, value dependency:multiplier, Base Price non Zero
					@Test
					public void getRateWithValueDepenedencyTypeMultiplierNonZeroAndPriceTypeVariable() {
						Parcel p = new Parcel();
						p.setParcelLength(2);
						p.setParcelHeight(3);
						p.setParcelWidth(4);
						p.setParcelDeadWeight(90);
						p.setService(1);
						p.setParcelDeclerationValue(40);
						RateServiceCategory rateServiceCategory = new RateServiceCategory();
						rateServiceCategory.setPriceType(PriceType.VARIABLE);
						rateServiceCategory.setServiceId(1L);
						rateServiceCategory.setTaxRate1(10f);
						rateServiceCategory.setTaxRate2(5f);
						rateServiceCategory.setLocationDependency(LocationDependency.NOT_APPLICABLE);
						rateServiceCategory.setValueDependency(ValueDependency.MULTIPLIER);
						rateServiceCategory.setWeightDependency(WeightDependency.NOT_APPLICABLE);
						ParcelValueWiseRate prcl = new ParcelValueWiseRate();
						prcl.setValueStartRange(0);
						prcl.setValueUpToRange(50);
						prcl.setBasePrice(10f);
						prcl.setValueFraction(10f);
						prcl.setPrice(30f);
						RateTable rt = new RateTable();
						rt.setRateServiceCategory(rateServiceCategory);
						rt.setParcelValueWiseRate(prcl);
						List<RateTable> rateList = new ArrayList<RateTable>();
						rateList.add(rt);
						Services s =new Services();
						s.setServiceName("Guaranteed Express");
						Mockito.when(rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(p.getService(),Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(rateList);
						Mockito.when(postalServiceRepository.findByIdAndStatus(rt.getRateServiceCategory().getServiceId(), Status.ACTIVE)).thenReturn(Optional.of(s));
						ReflectionTestUtils.setField(rateServiceImpl,"tax1Value","CGST");
						ReflectionTestUtils.setField(rateServiceImpl,"tax2Value","SGST");
						Parcel response = rateServiceImpl.getRate(p);
						assertEquals(p.getService(),response.getService());
						assertNotNull(response.getActWt());
						assertEquals(90f, response.getActWt());
						assertNotNull(response.getRateCalculation());
						assertNotNull(response.getRateCalculationJSON());
						assertNotNull(response.getInvoiceBreakup());
						assertNotNull(response.getRateCalculation().getInvoiceBreakup());
						assertEquals(149.5, response.getInvoiceBreakup().getPayableAmnt());
						assertEquals(130, response.getInvoiceBreakup().getPrice());
						assertEquals(149.5, response.getInvoiceBreakup().getSubTotal());
						assertEquals(19.5, response.getInvoiceBreakup().getTotalTax());
						assertEquals("CGST : 10.0% + SGST : 5.0%", response.getInvoiceBreakup().getTaxPercent());
						assertEquals("Guaranteed Express", response.getInvoiceBreakup().getName());
						assertEquals(OperationStatus.SUCCESS, response.getRateCalculation().getOperationStatus());
						assertEquals(130, response.getRateCalculation().getFinalAmount());
					}


}