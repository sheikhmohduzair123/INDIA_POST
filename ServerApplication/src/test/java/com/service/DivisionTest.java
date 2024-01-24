package com.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.Status;
import com.domain.Control;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.repositories.ControlRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.SUserRepository;
import com.repositories.ThanaAddressRepo;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;
import com.vo.DivisionVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class DivisionTest {

	@Mock
	MasterAddressService masterAddressService;

	@InjectMocks
	MasterAddressServiceImpl masterAddressServiceImpl;
	
	@Mock
	MasterAddressService masterAddressServiceDummy;

	@InjectMocks
	MasterAddressServiceImpl masterAddressServiceImplDummyImpl;
	
	@Mock
	SUserRepository repository;
	
	@Mock
	User userDummy;
	
	@Mock
	Division division;
	
	@Mock
	Zone zone;
	
	@Mock
	District district;
	
	@Mock
	Thana thana;
	
	@Mock
	MasterAddress masterAddress;
	
	@Mock
	Control control;
	
	@Mock
	ControlRepository controlRepo;
	
	@Mock
	DivisionAddressRepo divisionAddressRepo;
	
	@Mock
	DistrictAddressRepo districtAddressRepo;
	
	@Mock
	ThanaAddressRepo thanaAddressRepo;
	
	@Mock
	MasterAddressRepository masterAddressRepository;
	
	@Before
	public void init() {
		Mockito.when(repository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(userDummy);
		
		zone = new Zone();
		zone.setId(1L);
		zone.setZone("Zone1");
		zone.setCreatedBy(userDummy);
		zone.setUpdatedBy(userDummy);
		zone.setCreatedOn(new Date());
		zone.setUpdatedOn(new Date());
		zone.setStatus(Status.ACTIVE);
		
		division = new Division();
		division.setId(2L);
		division.setZone(zone);
		division.setDivision("dummyDivision");
		division.setCreatedBy(userDummy);
		division.setUpdatedBy(userDummy);
		division.setCreatedOn(new Date());
		division.setUpdatedOn(new Date());
		division.setStatus(Status.ACTIVE);
		

		district = new District();
		district.setId(3L);
		district.setDivision(division);
		district.setDistrict("dummyDistrict");
		district.setCreatedBy(userDummy);
		district.setUpdatedBy(userDummy);
		district.setCreatedOn(new Date());
		district.setUpdatedOn(new Date());
		district.setStatus(Status.ACTIVE);
		
		thana = new Thana();
		thana.setId(4L);
		thana.setDistrict(district);
		thana.setThana("dummyThana");
		thana.setCreatedBy(userDummy);
		thana.setUpdatedBy(userDummy);
		thana.setCreatedOn(new Date());
		thana.setUpdatedOn(new Date());
		thana.setStatus(Status.ACTIVE);
		
		masterAddress = new MasterAddress();
		masterAddress.setId(5L);
		masterAddress.setZone(zone.getZone());
		masterAddress.setZoneId(zone.getId());
		masterAddress.setDivision(division.getDivision());
		masterAddress.setDivisionId(division.getId());
		masterAddress.setDistrict(district.getDistrict());
		masterAddress.setDistrictId(district.getId());
		masterAddress.setThanaId(thana.getId());
		masterAddress.setThana(thana.getThana());
		masterAddress.setSubOffice("dummySubOffice");
		masterAddress.setPostalCode(1100);
		masterAddress.setCreatedBy(userDummy);
		masterAddress.setUpdatedBy(userDummy);
		masterAddress.setCreatedOn(new Date());
		masterAddress.setUpdatedOn(new Date());
		masterAddress.setStatus(Status.ACTIVE);
	}

	@Test
	public void saveDivisionDetailsTest() {
		
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Mockito.when(divisionAddressRepo.save(division)).thenReturn(division);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(division , masterAddressServiceImpl.saveDivisionDetails(division, userDummy));
		
	}
	
	@Test
	public void updateDivisionServiceIfConditionTest() {

			String divisionName = "division name updated successfully";
		
			List<Control> controlList = new ArrayList<Control>();
			controlList.add(control);
			
			Division div =new Division();
			div.setId(8L);
			div.setZone(zone);
			div.setStatus(Status.ACTIVE);
			div.setDivision("testingDiv");
			div.setCreatedOn(new Date());
			div.setCreatedBy(userDummy);
		
			Division newDivision = new Division();
		
			List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
			MasterAddress m = new MasterAddress();
			m.setDivisionId(8L);
			m.setDivision("oldDivision");
			m.setSubOffice("dummySubOffice");
			masterAddressesList.add(m);
		
			masterAddressesList.get(0).setDivision(divisionName);
			masterAddressesList.get(0).setUpdatedBy(userDummy);
			masterAddressesList.get(0).setUpdatedOn(new Date());
			
			//newDivision.setCreatedOn(div.getCreatedOn());
			//div.setCreatedOn(div.getCreatedOn());
			newDivision.setZone(div.getZone());
			div.setZone(zone);
			newDivision.setStatus(Status.DISABLED);
			//newDivision.setUpdatedOn(new Date());
			//div.setUpdatedOn(new Date());
			newDivision.setDivision(div.getDivision());
			div.setDivision(divisionName);
			//newDivision.setCreatedBy(div.getCreatedBy());
			div.setUpdatedBy(userDummy);
			
			newDivision.setUpdatedBy(userDummy);
			
			Mockito.when(masterAddressRepository.findMasterAddressByDivisionId(div.getId())).thenReturn(masterAddressesList);

			Mockito.when(divisionAddressRepo.save(newDivision)).thenReturn(newDivision);
			
			Mockito.when(controlRepo.findAll()).thenReturn(controlList);
			
			Assert.assertEquals(divisionName, masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName).getDivision());
			Assert.assertNotEquals(newDivision.getStatus(), masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName).getStatus());
			Assert.assertNotEquals(newDivision.getDivision(), masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName).getDivision());
			Assert.assertEquals(newDivision.getUpdatedBy(), masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName).getUpdatedBy());
			Assert.assertNotEquals(newDivision.getUpdatedOn(), masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName).getUpdatedOn());
	}
	
	
	@Test
	public void updateDivisionServiceElseConditionTest() {

			String divisionName = "division name updated successfully";
		
			Division div =new Division();
			div.setId(8L);
			div.setZone(zone);
			div.setStatus(Status.DISABLED);
			div.setDivision("testingDiv");
			div.setCreatedOn(new Date());
			div.setCreatedBy(userDummy);
		
			List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
			MasterAddress m = new MasterAddress();
			m.setDivisionId(8L);
			m.setDivision("oldDivision");
			m.setSubOffice("dummySubOffice");
			masterAddressesList.add(m);
			
			Mockito.when(masterAddressRepository.findMasterAddressByDivisionId(div.getId())).thenReturn(masterAddressesList);
			
			Assert.assertEquals(null , masterAddressServiceImpl.updateDivisionService(div, userDummy, zone, divisionName));
	}
	
	
	@Test
	public void updateOrDeleteDivisionIfConditionTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Division div =new Division();
		div.setId(8L);
		div.setZone(zone);
		div.setStatus(Status.ACTIVE);
		div.setDivision("testingDiv");
		div.setCreatedOn(new Date());
		div.setUpdatedOn(new Date());
		div.setCreatedBy(userDummy);
		
		
		List<District> districtList = new ArrayList<District>();
		
		District dist1 =new District();
		dist1.setId(4L);
		dist1.setDistrict("test district");
		dist1.setStatus(Status.ACTIVE);
		dist1.setDivision(div);
		
		districtList.add(dist1);
		
		List<Thana> thanaList = new ArrayList<Thana>();
		
		Thana thana1 =new Thana();
		thana1.setId(6L);
		thana1.setThana("test Thana");
		thana1.setStatus(Status.ACTIVE);
		thana1.setDistrict(dist1);
		
		thanaList.add(thana1);
		
		List<Long> thanaId = new ArrayList<Long>();
		thanaId.add(6L);
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		MasterAddress m = new MasterAddress();
		m.setId(8L);
		m.setStatus(Status.ACTIVE);
		m.setSubOffice("test subOffice");
		m.setThanaId(6L);

		masterList.add(m);
		
		
		Mockito.when(masterAddressServiceImplDummyImpl.getDistrictByDivision(div.getId())).thenReturn(districtList);
		Mockito.when(masterAddressServiceImplDummyImpl.getThanaIdByDistrict(districtList)).thenReturn(thanaList);
		Mockito.when(masterAddressServiceImplDummyImpl.getMasterAddressByThana(thanaId)).thenReturn(masterList);
		
		Mockito.when(divisionAddressRepo.save(div)).thenReturn(div);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Division deleteData = masterAddressServiceImpl.updateOrDeleteDivision(div, userDummy);

		Assert.assertEquals(div, deleteData);
		Assert.assertEquals(Status.DISABLED, deleteData.getStatus());
	}

	
	@Test
	public void updateOrDeleteDivisionElseConditionTest() {

		Division div =new Division();
		div.setId(8L);
		div.setStatus(Status.DISABLED);
		div.setDivision("testingDiv");
		
		Assert.assertEquals(null, masterAddressServiceImpl.updateOrDeleteDivision(div, userDummy));
	}
	
	@Test
	public void getDistrictByDivisionTest() {
		
		List<District> districtList = new ArrayList<District>();
		districtList.add(district);
		
		Mockito.when(districtAddressRepo.findBydivision_id(2L)).thenReturn(districtList);
		
		Assert.assertEquals(districtList, masterAddressServiceImpl.getDistrictByDivision(2L));
	}
	
	@Test
	public void getDistrictByDivisionEmptyListTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findBydivision_id(2L)).thenReturn(districtList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getDistrictByDivision(2L).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getDistrictByDivision(2L).size());
	}
	
	@Test
	public void getThanaIdByDistrictTest() {
		
		List<District> districtList = new ArrayList<District>();
		districtList.add(district);
		
		List<Thana> thanaList = new ArrayList<Thana>();
		thanaList.add(thana);
		
		Mockito.when(thanaAddressRepo.findByDistrictIn(districtList)).thenReturn(thanaList);
		
		Assert.assertEquals(thanaList, masterAddressServiceImpl.getThanaIdByDistrict(districtList));
	}
	
	@Test
	public void getThanaIdByDistrictEmptyListTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		List<Thana> thanaList = new ArrayList<Thana>();
		
		Mockito.when(thanaAddressRepo.findByDistrictIn(districtList)).thenReturn(thanaList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getThanaIdByDistrict(districtList).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getThanaIdByDistrict(districtList).size());
	}
	
	@Test
	public void getMasterAddressByThanaTest() {
		List<Long> thanaId = new ArrayList<Long>();
		thanaId.add(4L);
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		masterList.add(masterAddress);
		
		Mockito.when(masterAddressRepository.findByThanaIdIn(thanaId)).thenReturn(masterList);
		
		Assert.assertEquals(masterList, masterAddressServiceImpl.getMasterAddressByThana(thanaId));
	}
	
	@Test
	public void getMasterAddressByThanaEmptyListTest() {
		List<Long> thanaId = new ArrayList<Long>();
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		Mockito.when(masterAddressRepository.findByThanaIdIn(thanaId)).thenReturn(masterList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getMasterAddressByThana(thanaId).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getMasterAddressByThana(thanaId).size());
	}
	
	@Test
	public void fetchExistingDivisionIfConditionTest() {
		
		List<Division> validDivisiontList = new ArrayList<Division>();
		validDivisiontList.add(division);
		
		Mockito.when(divisionAddressRepo.findDivisionByzone_idAndDivisionContainingIgnoreCaseAndStatus(zone.getId(),"dummyDivision", Status.ACTIVE)).thenReturn(validDivisiontList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingDivision(zone.getId(),division.getDivision()));
		
	}
	
	@Test
	public void fetchExistingDivisionElseConditionTest() {
		
		List<Division> validDivisiontList = new ArrayList<Division>();
		
		Mockito.when(divisionAddressRepo.findDivisionByzone_idAndDivisionContainingIgnoreCaseAndStatus(3L,"dummyData", Status.ACTIVE)).thenReturn(validDivisiontList);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingDivision(2L, "division1"));
		
	}
	
	
	@Test
	public void fetchDivisionListTest() {
		List<Division> divisionList = new ArrayList<Division>();
		Division division1 = new Division();
		division1.setId(2L);
		division1.setStatus(Status.ACTIVE);
		
		divisionList.add(division);
		divisionList.add(division1);
		
		Mockito.when(divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE)).thenReturn(divisionList);
		Assert.assertEquals(divisionList, masterAddressServiceImpl.fetchDivisionList());
		Assert.assertEquals(divisionList.size(), masterAddressServiceImpl.fetchDivisionList().size());
	}
	
	@Test
	public void fetchDivisionListEmptyListTest() {
		
		List<Division> divisionList = new ArrayList<Division>();

		Mockito.when(divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE)).thenReturn(divisionList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchDivisionList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchDivisionList().size());
	}
	
	@Test
	public void fetchDivisionListByZoneTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		
		Mockito.when(divisionAddressRepo.findDivisionByzone_idAndStatusOrderByDivisionAsc(1L, Status.ACTIVE)).thenReturn(divisionList);
		
		Assert.assertEquals("dummyDivision", masterAddressServiceImpl.fetchDivisionListByZone(1L).get(0).getDivision());
		Assert.assertEquals(Long.valueOf(2), masterAddressServiceImpl.fetchDivisionListByZone(1L).get(0).getId());
	}
	
	@Test
	public void fetchDivisionListByZoneEmptyListTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		
		Mockito.when(divisionAddressRepo.findDivisionByzone_idAndStatusOrderByDivisionAsc(1L, Status.ACTIVE)).thenReturn(divisionList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchDivisionListByZone(1L).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchDivisionListByZone(1L).size());
	}
	
	@Test
	public void getAllDivisionListTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		
		Zone zone1 = new Zone();
		zone1.setId(2L);
		zone1.setZone("testingZone");
		zone1.setStatus(Status.ACTIVE);
		
		Division division1 = new Division();
		division1.setId(3L);
		division1.setZone(zone1);
		division1.setDivision("testingDivision");
		division1.setCreatedOn(new Date());
		division1.setUpdatedOn(new Date());
		division1.setUpdatedBy(userDummy);
		division1.setStatus(Status.ACTIVE);
		
		divisionList.add(division);
		divisionList.add(division1);
		

		Mockito.when(divisionAddressRepo.findAll()).thenReturn(divisionList);
		Assert.assertEquals(Long.valueOf(2), masterAddressServiceImpl.getAllDivisionList().get(0).getId());
		Assert.assertEquals("Zone1", masterAddressServiceImpl.getAllDivisionList().get(0).getZoneName());
		Assert.assertEquals("dummyDivision", masterAddressServiceImpl.getAllDivisionList().get(0).getDivision());
		Assert.assertEquals(Long.valueOf(3), masterAddressServiceImpl.getAllDivisionList().get(1).getId());
		Assert.assertEquals("testingDivision", masterAddressServiceImpl.getAllDivisionList().get(1).getDivision());
	}
	
	@Test
	public void getAllDivisionListEmptyListTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		
		Mockito.when(divisionAddressRepo.findAll()).thenReturn(divisionList);

		Assert.assertEquals(true, masterAddressServiceImpl.getAllDivisionList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllDivisionList().size());
	}
	
	
	@Test
	public void getAllDivisionListByStatusTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		
		Zone zone1 = new Zone();
		zone1.setId(2L);
		zone1.setZone("testingZone");
		zone1.setStatus(Status.ACTIVE);
		
		Division division1 = new Division();
		division1.setId(3L);
		division1.setZone(zone1);
		division1.setDivision("testingDivision");
		division1.setCreatedOn(new Date());
		division1.setUpdatedOn(new Date());
		division1.setUpdatedBy(userDummy);
		division1.setStatus(Status.ACTIVE);
		
		divisionList.add(division);
		divisionList.add(division1);
		

		Mockito.when(divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE)).thenReturn(divisionList);
		
		Assert.assertEquals(Long.valueOf(2), masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("Zone1", masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).get(0).getZoneName());
		Assert.assertEquals("dummyDivision", masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).get(0).getDivision());
		Assert.assertEquals(Long.valueOf(3), masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).get(1).getId());
		Assert.assertEquals("testingDivision", masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).get(1).getDivision());
		
	}
	
	@Test
	public void getAllDivisionListByStatusEmptyListTest() {
		
		List<Division> divisionList = new ArrayList<Division>();
		
		Mockito.when(divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE)).thenReturn(divisionList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllDivisionListByStatus(Status.ACTIVE).size());
	}
	
}
