package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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
import com.constants.Status;
import com.domain.IdCounters;
import com.domain.MasterAddress;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.IdCounterRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.RmsRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;
import com.services.impl.ReportServiceImpl;
import com.services.impl.TrackingServiceImpl;
import com.vo.MasterAddressVo;
import com.vo.RmsTableVo;
import com.vo.TrackingVo;

@RunWith(MockitoJUnitRunner.class)
public class InwardOutwardBagTest {

	@Mock
	private TrackingCSRepository trackingCSRepository;

	@Mock
	private TrackingDetailsRepository trackingDetailsRepository;

	@InjectMocks
	private TrackingServiceImpl trackingServive;

	@Mock
	private MasterAddressService masterAddress;

	@InjectMocks
	private MasterAddressServiceImpl masterAddressImpl;

	@Mock
	private MasterAddressRepository masterAddressRepository;

	@Mock
	private IdCounterRepository idCounterRepo;

	@Mock
	private RmsRepository rmsRepository;

	@InjectMocks
	private ReportServiceImpl reportService;

	//fetching bag details by bagid for bag received by po user (inward)
	@Test
	public void fetchInwardBagDetailForPOTest() {
		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setBagId("123456789");
		bagdetail.setBagStatus(BagStatus.OUT);
		bagdetail.setLocationType(LocationType.POST_OFFICE);
		bagdetail.setLocationId(1000L);
		bagdetail.setTrackingDesc("test find bag details");

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
		bagdetaillist.add(bagdetail);

		Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
		List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

		assertEquals(1, response.size());
		assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
		assertEquals("123456789", response.get(0).getBagId());
		assertEquals("test find bag details", response.get(0).getTrackingDesc());
		assertEquals(Long.valueOf(loginuser.getPostalCode()), response.get(0).getLocationId());

	}

		//fetching bag details by bagid for bag forwarded to rms user  (outward)
		@Test
		public void fetchOutwardBagDetailForRMSTest() {
				TrackingCS bagdetail = new TrackingCS();
				bagdetail.setBagId("123456789");
				bagdetail.setBagStatus(BagStatus.CREATED);
				bagdetail.setLocationType(LocationType.POST_OFFICE);
				bagdetail.setLocationId(1000L);
				bagdetail.setTrackingDesc("test find bag details");

				Role role_po = new Role();
				role_po.setName("ROLE_PO_USER");
				role_po.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_po);
				loginuser.setPostalCode(1000);

				List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
				bagdetaillist.add(bagdetail);

				Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
				List<TrackingVo> response = trackingServive.findByBagId("123456789", "forward", loginuser);

				assertEquals(1, response.size());
				assertNotEquals(BagStatus.OUT, response.get(0).getBagStatus());
				assertEquals("123456789", response.get(0).getBagId());
				assertEquals("test find bag details", response.get(0).getTrackingDesc());
				assertEquals(Long.valueOf(loginuser.getPostalCode()), response.get(0).getLocationId());
			}

	//fetching bag details should return empty list if not present in db
	@Test
	public void fetchParcelDetailNotFoundTest() {
		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		Role role_rms = new Role();
		role_rms.setName("ROLE_RMS_USER");
		role_rms.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);

		List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();

		Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
		List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

		assertEquals(0, response.size());
		assertTrue(response.isEmpty());
	}

	//fetching bag details by bagid for bag forwarded to rms user but received by post office(outward:reached wrong destination)
	@Test
	public void WrongDestinationTest() {
					TrackingCS bagdetail = new TrackingCS();
					bagdetail.setBagId("123456789");
					bagdetail.setBagStatus(BagStatus.OUT);
					bagdetail.setLocationType(LocationType.RMS);
					bagdetail.setLocationId(1L);
					bagdetail.setTrackingDesc("test find bag details");

					Role role_po = new Role();
					role_po.setName("ROLE_PO_USER");
					role_po.setStatus(Status.ACTIVE);

					User loginuser = new User();
					loginuser.setRole(role_po);
					loginuser.setPostalCode(1000);

					List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
					bagdetaillist.add(bagdetail);

					Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
					List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

					assertEquals(1, response.size());
					assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
					assertEquals("123456789", response.get(0).getBagId());
					assertEquals("wrong destination", response.get(0).getTrackingDesc());
					assertNotEquals(Long.valueOf(loginuser.getPostalCode()), response.get(0).getLocationId());
				}

	//fetching bag details by bagid for bag forwarded to postoffice 1000 user but received by postoffice 9999(outward:reached wrong destination)
	@Test
	public void WrongDestinationPostofficeTest() {
					TrackingCS bagdetail = new TrackingCS();
					bagdetail.setBagId("123456789");
					bagdetail.setBagStatus(BagStatus.OUT);
					bagdetail.setLocationType(LocationType.POST_OFFICE);
					bagdetail.setLocationId(1000L);
					bagdetail.setTrackingDesc("test find bag details");

					Role role_po = new Role();
					role_po.setName("ROLE_PO_USER");
					role_po.setStatus(Status.ACTIVE);

					User loginuser = new User();
					loginuser.setRole(role_po);
					loginuser.setPostalCode(9999);

					List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
					bagdetaillist.add(bagdetail);

					Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
					List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

					assertEquals(1, response.size());
					assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
					assertEquals("123456789", response.get(0).getBagId());
					assertEquals("wrong destination", response.get(0).getTrackingDesc());
					assertNotEquals(Long.valueOf(loginuser.getPostalCode()), response.get(0).getLocationId());
				}

	//fetching bag details by bagid for bag forwarded to rms 1 user but received by rms 2(outward:reached wrong destination)
		@Test
		public void WrongDestinationRMSTest() {
						TrackingCS bagdetail = new TrackingCS();
						bagdetail.setBagId("123456789");
						bagdetail.setBagStatus(BagStatus.OUT);
						bagdetail.setLocationType(LocationType.RMS);
						bagdetail.setLocationId(1L);
						bagdetail.setTrackingDesc("test find bag details");

						Role role_rms = new Role();
						role_rms.setName("ROLE_RMS_USER");
						role_rms.setStatus(Status.ACTIVE);

						User loginuser = new User();
						loginuser.setRole(role_rms);
						loginuser.setRmsId(2L);

						List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
						bagdetaillist.add(bagdetail);

						Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
						List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

						assertEquals(1, response.size());
						assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
						assertEquals("123456789", response.get(0).getBagId());
						assertEquals("wrong destination", response.get(0).getTrackingDesc());
						assertNotEquals(loginuser.getRmsId(), response.get(0).getLocationId());
					}

		//bag not forwarded still received by rms : bag status is not out
			@Test
			public void BagStatusNotOutTest() {
								TrackingCS bagdetail = new TrackingCS();
								bagdetail.setBagId("123456789");
								bagdetail.setBagStatus(BagStatus.CREATED);
								bagdetail.setLocationType(LocationType.POST_OFFICE);
								bagdetail.setLocationId(1000L);
								bagdetail.setTrackingDesc("test find bag details");

								Role role_rms = new Role();
								role_rms.setName("ROLE_RMS_USER");
								role_rms.setStatus(Status.ACTIVE);

								User loginuser = new User();
								loginuser.setRole(role_rms);
								loginuser.setRmsId(2L);

								List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
								bagdetaillist.add(bagdetail);

								Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
								List<TrackingVo> response = trackingServive.findByBagId("123456789", "inward", loginuser);

								assertEquals(1, response.size());
								assertNotEquals(BagStatus.OUT, response.get(0).getBagStatus());
								assertEquals("123456789", response.get(0).getBagId());
								assertEquals("bag status is not out", response.get(0).getTrackingDesc());
							}

			//bag which was already forwarded is forwarded again : bag status is already out
			@Test
			public void BagStatusIsAlreadyOutTest() {
								TrackingCS bagdetail = new TrackingCS();
								bagdetail.setBagId("123456789");
								bagdetail.setBagStatus(BagStatus.OUT);
								bagdetail.setLocationType(LocationType.POST_OFFICE);
								bagdetail.setLocationId(1000L);
								bagdetail.setTrackingDesc("test find bag details");

								Role role_po = new Role();
								role_po.setName("ROLE_PO_USER");
								role_po.setStatus(Status.ACTIVE);

								User loginuser = new User();
								loginuser.setRole(role_po);
								loginuser.setPostalCode(1000L);

								List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
								bagdetaillist.add(bagdetail);

								Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
								List<TrackingVo> response = trackingServive.findByBagId("123456789", "forward", loginuser);

								assertEquals(1, response.size());
								assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
								assertEquals("123456789", response.get(0).getBagId());
								assertEquals("bag status is already out", response.get(0).getTrackingDesc());
							}

			//receive bag at postoffice 1100
			@Test
			public void ReceiveBagTest() {

				TrackingCS bagdetail = new TrackingCS();
				bagdetail.setBagId("123456789");
				bagdetail.setBagStatus(BagStatus.OUT);
				bagdetail.setLocationType(LocationType.POST_OFFICE);
				bagdetail.setLocationId(1000L);

				TrackingCS bagdetail2 = new TrackingCS();
				bagdetail2.setBagId("123456789");
				bagdetail2.setBagStatus(BagStatus.IN);
				bagdetail2.setLocationType(LocationType.POST_OFFICE);
				bagdetail2.setLocationId(1000L);

				Role role_po = new Role();
				role_po.setName("ROLE_PO_USER");
				role_po.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_po);
				loginuser.setPostalCode(1000L);

				List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
				bagdetaillist.add(bagdetail);

				TrackingDetails bagdetail1 = new TrackingDetails();
				bagdetail1.setBagId("123456789");
				bagdetail1.setBagStatus(BagStatus.OUT);
				bagdetail1.setLocationType(LocationType.POST_OFFICE);
				bagdetail1.setLocationId(1000L);
				bagdetail1.setStatus(Status.ACTIVE);

				List<TrackingDetails> bagdetaillist1 = new ArrayList<TrackingDetails>();
				bagdetaillist1.add(bagdetail1);

				MasterAddressVo masterAddrssVo = new MasterAddressVo();
				masterAddrssVo.setPostalCode(1000);
				masterAddrssVo.setSubOffice("SubOffice");
				masterAddrssVo.setThana("Thana");

				Mockito.when(masterAddress.getPostalData(1000)).thenReturn(masterAddrssVo);
				Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
				Mockito.when(trackingDetailsRepository.findByBagIdAndStatus("123456789", Status.ACTIVE)).thenReturn(bagdetaillist1);
			//	Mockito.when(trackingDetailsRepository.save(bagdetail1)).thenReturn(bagdetail1);
				Mockito.when(trackingCSRepository.save(bagdetail)).thenReturn(bagdetail2);

				List<String> bagids = new ArrayList<String>();
				bagids.add("123456789");

			    List<TrackingCS> response =	trackingServive.receiveBags(bagids, loginuser);

				assertNotNull(response);
				assertEquals(1, response.size());
				assertEquals(BagStatus.IN, response.get(0).getBagStatus());
				assertEquals("1000", response.get(0).getLocationId().toString());
				assertEquals(LocationType.POST_OFFICE, response.get(0).getLocationType());
			}

			//receive bag fail condition, when empty bag list sent
			@Test
			public void ReceiveBagFailTest() {

				Role role_po = new Role();
				role_po.setName("ROLE_PO_USER");
				role_po.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_po);
				loginuser.setPostalCode(1000L);

				List<String> bagids = new ArrayList<String>();

				assertTrue(trackingServive.receiveBags(bagids, loginuser).isEmpty());
			}


			//forward bags to rms
			@Test
			public void ForwardBagTest() {

				TrackingCS bagdetail = new TrackingCS();
				bagdetail.setBagId("123456789");
				bagdetail.setBagStatus(BagStatus.CREATED);
				bagdetail.setLocationType(LocationType.POST_OFFICE);
				bagdetail.setLocationId(1000L);

				TrackingCS bagdetail2 = new TrackingCS();
				bagdetail2.setBagId("123456789");
				bagdetail2.setBagStatus(BagStatus.OUT);
				bagdetail2.setLocationType(LocationType.RMS);
				bagdetail2.setLocationId(1L);

				Role role_po = new Role();
				role_po.setName("ROLE_PO_USER");
				role_po.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_po);
				loginuser.setPostalCode(1000L);

				List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
				bagdetaillist.add(bagdetail);

				TrackingDetails bagdetail1 = new TrackingDetails();
				bagdetail1.setBagId("123456789");
				bagdetail1.setBagStatus(BagStatus.CREATED);
				bagdetail1.setLocationType(LocationType.POST_OFFICE);
				bagdetail1.setLocationId(1000L);
				bagdetail1.setStatus(Status.ACTIVE);

				List<TrackingDetails> bagdetaillist1 = new ArrayList<TrackingDetails>();
				bagdetaillist1.add(bagdetail1);

				RmsTable rms = new RmsTable();
				rms.setId(1L);
				rms.setRmsName("rms");

				Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);
				Mockito.when(trackingDetailsRepository.findByBagIdAndStatus("123456789", Status.ACTIVE)).thenReturn(bagdetaillist1);
				Mockito.when(rmsRepository.findRmsTableByIdAndStatus(1L, Status.ACTIVE)).thenReturn(rms);
				//Mockito.when(trackingDetailsRepository.save(bagdetail1)).thenReturn(bagdetail1);
				Mockito.when(trackingCSRepository.save(bagdetail)).thenReturn(bagdetail2);

				List<String> bagids = new ArrayList<String>();
				bagids.add("123456789");

			    List<TrackingCS> response =	trackingServive.forwardBags(bagids, "1", "RMS", loginuser);

				assertNotNull(response);
				assertEquals(1, response.size());
				assertEquals(BagStatus.OUT, response.get(0).getBagStatus());
				assertEquals("1", response.get(0).getLocationId().toString());
				assertEquals(LocationType.RMS, response.get(0).getLocationType());
			}

			//forward bags to rms fail, when empty bagid sent
			@Test
			public void ForwardBagFailTest() {

				Role role_po = new Role();
				role_po.setName("ROLE_PO_USER");
				role_po.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_po);
				loginuser.setPostalCode(1000L);

				List<String> bagids = new ArrayList<String>();

				assertTrue(trackingServive.forwardBags(bagids, "1", "RMS", loginuser).isEmpty());
			}


			//test postal code data
			@Test
			public void GetPostalData()
			{
				MasterAddress masterAddrss = new MasterAddress();
				masterAddrss.setPostalCode(1000);
				masterAddrss.setSubOffice("SubOffice");
				masterAddrss.setThana("Thana");
				masterAddrss.setStatus(Status.ACTIVE);
				Mockito.when(masterAddressRepository.findMasterAddressByPostalCodeAndStatus(1000, Status.ACTIVE)).thenReturn(masterAddrss);
				MasterAddressVo response = masterAddressImpl.getPostalData(1000);
				assertEquals(1000, response.getPostalCode());
				assertEquals("Thana", response.getThana());
				assertEquals("SubOffice", response.getSubOffice());
			}

			//test for generating empty bags,when counterid table is empty at Post office
			@Test
			public void generateEmptyBagsAtPOTest() {
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

				Mockito.when(masterAddress.getPostalData(1000)).thenReturn(masterAddrssVo);
				Mockito.when(idCounterRepo.findByPostalCode(loginuser.getPostalCode())).thenReturn(null);

				List<String> response = trackingServive.generateEmptyBags(1, loginuser);
				assertEquals(1, response.size());
				assertTrue(response.get(0).startsWith("1000"));
				assertTrue(response.get(0).endsWith("100001"));
			}

			//test for generating empty bags at RMS,when counterid table is not empty
			@Test
			public void generateEmptyBagsAtRMSTest() {
				Role role_rms = new Role();
				role_rms.setName("ROLE_RMS_USER");
				role_rms.setStatus(Status.ACTIVE);

				User loginuser = new User();
				loginuser.setRole(role_rms);
				loginuser.setRmsId(1L);

				RmsTable rms = new RmsTable();
				rms.setId(1L);
				rms.setRmsName("rms");

				IdCounters newcntr = new IdCounters();
				newcntr.setBagIdCntr(444444);
				newcntr.setRmsId(loginuser.getRmsId());

				Mockito.when(idCounterRepo.findByRmsId(loginuser.getRmsId())).thenReturn(newcntr);
		 		Mockito.when(rmsRepository.findRmsTableByIdAndStatus(1L, Status.ACTIVE)).thenReturn(rms);

				List<String> response = trackingServive.generateEmptyBags(1, loginuser);
				assertEquals(1, response.size());
				assertTrue(response.get(0).startsWith("0001"));
				assertTrue(response.get(0).endsWith("444445"));
			}

			//getRMSNameByRMSType
			@Test
			public void getRMSNameByRMSTypeTest() {
				RmsTable rms = new RmsTable();
				rms.setId(1L);
				rms.setRmsName("rms");
				rms.setRmsType("rmsType");

				RmsTable rms1 = new RmsTable();
				rms1.setId(2L);
				rms1.setRmsName("rms1");
				rms1.setRmsType("rmsType");

				List<RmsTable> rmsNameList = new ArrayList<RmsTable>();
				rmsNameList.add(rms);
				rmsNameList.add(rms1);

				Mockito.when(rmsRepository.findRmsTableByRmsTypeAndStatus("rmsType", Status.ACTIVE)).thenReturn(rmsNameList);
				List<RmsTableVo> response = reportService.getRMSNameByRMSType("rmsType");

				assertEquals(rms.getRmsType(), response.get(0).getRmsType());
				assertEquals(rms1.getRmsType(), response.get(1).getRmsType());
			}

			//getRMSNameByRMSType list is null
			@Test
			public void getRMSNameByRMSTypeListNullTest() {

				List<RmsTable> rmsNameList = new ArrayList<RmsTable>();
				Mockito.when(rmsRepository.findRmsTableByRmsTypeAndStatus("rmsType", Status.ACTIVE)).thenReturn(rmsNameList);
				assertEquals(0, reportService.getRMSNameByRMSType("rmsType").size());
			}

			//getRMSdata
			@Test
			public void getRMSdataTest() {
				RmsTable rms = new RmsTable();
				rms.setId(1L);
				rms.setRmsName("rms");
				rms.setRmsType("rmsType");

				RmsTable rms1 = new RmsTable();
				rms1.setId(2L);
				rms1.setRmsName("rms1");
				rms1.setRmsType("rmsType");

				List<RmsTable> rmsNameList = new ArrayList<RmsTable>();
				rmsNameList.add(rms);
				rmsNameList.add(rms1);

				Mockito.when(rmsRepository.findAllByStatus(Status.ACTIVE)).thenReturn(rmsNameList);
				List<RmsTableVo> response = trackingServive.getRMSdata();

				assertEquals(2, response.size());
				assertEquals(rms.getRmsType(), response.get(0).getRmsType());
				assertEquals(rms1.getRmsType(), response.get(1).getRmsType());
			}

			//getRMSdata list is null
			@Test
			public void getRMSdataListNullTest() {

				List<RmsTable> rmsNameList = new ArrayList<RmsTable>();
				Mockito.when(rmsRepository.findAllByStatus(Status.ACTIVE)).thenReturn(rmsNameList);
				assertEquals(0, trackingServive.getRMSdata().size());
			}

	//deleteEmptyBags
	@Test
	public void deleteEmptyBagsTest() {

		TrackingCS bagdetail = new TrackingCS();
		bagdetail.setBagId("123456789");
		bagdetail.setBagStatus(BagStatus.CREATED);
		bagdetail.setLocationType(LocationType.POST_OFFICE);
		bagdetail.setLocationId(1000L);
		bagdetail.setObjParcel(null);

		List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
		bagdetaillist.add(bagdetail);

		TrackingDetails bagdetail1 = new TrackingDetails();
		bagdetail1.setBagId("123456789");
		bagdetail1.setBagStatus(BagStatus.CREATED);
		bagdetail1.setLocationType(LocationType.POST_OFFICE);
		bagdetail1.setLocationId(1000L);
		bagdetail1.setStatus(Status.ACTIVE);
		bagdetail1.setObjParcel(null);

		List<TrackingDetails> bagdetaillist1 = new ArrayList<TrackingDetails>();
		bagdetaillist1.add(bagdetail1);

		List<TrackingCS> baglistEmpty = new ArrayList<TrackingCS>();
		List<TrackingDetails> bagdetaillistEmpty = new ArrayList<TrackingDetails>();

		//if same repo is called 2 times
		Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist).thenReturn(baglistEmpty);
		Mockito.when(trackingDetailsRepository.findByBagIdAndStatus("123456789", Status.ACTIVE)).thenReturn(bagdetaillist1).thenReturn(bagdetaillistEmpty);

		assertEquals("success", trackingServive.deleteEmptyBags("123456789"));
	}

	//deleteEmptyBags failed: when deleting bag is not at origin, then not deleted from db
		@Test
		public void deleteEmptyBagsNotDeletedFromDbTest() {

			TrackingCS bagdetail = new TrackingCS();
			bagdetail.setBagId("123456789");
			bagdetail.setBagStatus(BagStatus.IN);
			bagdetail.setLocationType(LocationType.POST_OFFICE);
			bagdetail.setLocationId(1000L);
			bagdetail.setObjParcel(null);

			List<TrackingCS> bagdetaillist = new ArrayList<TrackingCS>();
			bagdetaillist.add(bagdetail);

			Mockito.when(trackingCSRepository.findByBagId("123456789")).thenReturn(bagdetaillist);

			assertEquals("success", trackingServive.deleteEmptyBags("123456789"));
		}

}
