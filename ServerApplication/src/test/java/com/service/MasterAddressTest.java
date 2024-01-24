package com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.repositories.MasterAddressRepository;
import com.repositories.SUserRepository;
import com.repositories.ThanaAddressRepo;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class MasterAddressTest {

	@Mock
	MasterAddressService masterAddressService;

	@InjectMocks
	MasterAddressServiceImpl masterAddressServiceImpl;
	
	@Mock
	SUserRepository repository;
	
	@Mock
	User userDummy;
	
	@Mock
	Zone zone;
	
	@Mock
	Division division;
	
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
	public void saveSubOfficeDetailsTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Mockito.when(masterAddressRepository.save(masterAddress)).thenReturn(masterAddress);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);

		Assert.assertEquals(masterAddress , masterAddressServiceImpl.saveSubOfficeDetails(masterAddress, zone, division, district,thana, userDummy));
		
	}
	
	
	@Test
	public void updateSubOfficeOrMasterAddressServiceIfConditionTest() {

		String subOfficeName = "subOffice updated successfully";
		Integer postalCode = 1100;
		
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
				
		MasterAddress master =new MasterAddress();
		master.setId(8L);
		master.setThana(thana.getThana());
		master.setThanaId(thana.getId());
		master.setStatus(Status.ACTIVE);
		master.setSubOffice("testing subOffice");
		master.setCreatedOn(new Date());
		master.setCreatedBy(userDummy);
			
				
		MasterAddress newMasterAddress = new MasterAddress();
				
		//newMasterAddress.setCreatedOn(master.getCreatedOn());
		//master.setCreatedOn(master.getCreatedOn());
		newMasterAddress.setStatus(Status.DISABLED);
		//newMasterAddress.setUpdatedOn(new Date());
		//master.setUpdatedOn(new Date());
		newMasterAddress.setSubOffice(master.getSubOffice());
		master.setSubOffice(subOfficeName);
		//newMasterAddress.setCreatedBy(master.getCreatedBy());
		master.setUpdatedBy(userDummy);
		newMasterAddress.setUpdatedBy(userDummy);
		newMasterAddress.setPostalCode(master.getPostalCode());
		master.setPostalCode(postalCode);
	
		newMasterAddress.setZone(master.getZone());
		newMasterAddress.setZoneId(master.getZoneId());
		master.setZone(zone.getZone());
		master.setZoneId(zone.getId());
	
		newMasterAddress.setDivision(master.getDivision());
		newMasterAddress.setDivisionId(master.getDivisionId());
		master.setDivision(division.getDivision());
		master.setDivisionId(division.getId());
		
		newMasterAddress.setDistrict(master.getDistrict());
		newMasterAddress.setDistrictId(masterAddress.getDistrictId());
		master.setDistrict(district.getDistrict());
		master.setDistrictId(district.getId());
		
		newMasterAddress.setThana(master.getThana());
		newMasterAddress.setThanaId(master.getThanaId());
		
		master.setThana(thana.getThana());
		master.setThanaId(thana.getId());
				

		Mockito.when(masterAddressRepository.save(newMasterAddress)).thenReturn(newMasterAddress);
				
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);

		Assert.assertEquals(subOfficeName, masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode).getSubOffice());
		Assert.assertNotEquals(newMasterAddress.getStatus(), masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode).getStatus());
		Assert.assertNotEquals(newMasterAddress.getSubOffice(), masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode).getSubOffice());
		Assert.assertEquals(newMasterAddress.getUpdatedBy(), masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode).getUpdatedBy());
		Assert.assertNotEquals(newMasterAddress.getUpdatedOn(), masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode).getUpdatedOn());
		
	}
	
	@Test
	public void updateSubOfficeOrMasterAddressServiceElseConditionTest() {

		String subOfficeName = "subOffice updated successfully";
		Integer postalCode = 1100;
		
		MasterAddress master =new MasterAddress();
		master.setId(8L);
		master.setStatus(Status.DISABLED);
		master.setSubOffice("testing subOffice");
			
		Assert.assertEquals(null , masterAddressServiceImpl.updateSubOfficeOrMasterAddressService(master, zone, division, district, thana, userDummy, subOfficeName, postalCode));
}
	
	@Test
	public void updateOrDeleteSubOfficeOrMasterAddressIfConditionTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		MasterAddress master =new MasterAddress();
		master.setId(8L);
		master.setThana(thana.getThana());
		master.setThanaId(thana.getId());
		master.setStatus(Status.ACTIVE);
		master.setSubOffice("testing subOffice");
		master.setCreatedOn(new Date());
		master.setUpdatedOn(new Date());
		master.setCreatedBy(userDummy);
		
		
		Mockito.when(masterAddressRepository.save(master)).thenReturn(master);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		
		MasterAddress deleteData = masterAddressServiceImpl.updateOrDeleteSubOfficeOrMasterAddress(master, userDummy);

		Assert.assertEquals(master, deleteData);
		Assert.assertEquals(Status.DISABLED, deleteData.getStatus());
	}
	
	@Test
	public void updateOrDeleteSubOfficeOrMasterAddressElseConditionTest() {

		MasterAddress master =new MasterAddress();
		master.setId(8L);
		master.setStatus(Status.DISABLED);
		master.setSubOffice("testing subOffice");
		
		Assert.assertEquals(null, masterAddressServiceImpl.updateOrDeleteSubOfficeOrMasterAddress(master, userDummy));
	}
	
	
	@Test
	public void fetchExistingSubOfficeIfConditionTest() {
		
		List<MasterAddress> validSubOfficeList = new ArrayList<MasterAddress>();
		validSubOfficeList.add(masterAddress);
		
		Mockito.when(masterAddressRepository.findByZoneContainingIgnoreCaseAndDivisionContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndThanaContainingIgnoreCaseAndSubOfficeContainingIgnoreCaseAndStatus(zone.getZone(), division.getDivision(), district.getDistrict(), thana.getThana(), "dummySubOffice", Status.ACTIVE)).thenReturn(validSubOfficeList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingSubOffice(zone.getZone(), division.getDivision(), district.getDistrict(), thana.getThana(), "dummySubOffice"));
		
	}
	
	@Test
	public void fetchExistingSubOfficeElseCondition() {
		
		List<MasterAddress> validSubOfficeList = new ArrayList<MasterAddress>();
		
		Mockito.when(masterAddressRepository.findByZoneContainingIgnoreCaseAndDivisionContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndThanaContainingIgnoreCaseAndSubOfficeContainingIgnoreCaseAndStatus("testZone", "testDivision", "testDistrict", "testThana", "testSubOffice", Status.ACTIVE)).thenReturn(validSubOfficeList);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingSubOffice("testZone", "testDivision", "testDistrict", "testThana", "testSubOffice"));
		
	}
	
	@Test
	public void fetchExistingPostalCodeTest() { //ask this test case how to write
		
		Mockito.when(masterAddressRepository.existsByPostalCodeAndStatus(1100, Status.ACTIVE)).thenReturn(true);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingPostalCode(1100));
	}
	
	@Test
	public void fetchExistingPostalCodeFalseCondtionTest() { //ask this test case how to write
		
		Mockito.when(masterAddressRepository.existsByPostalCodeAndStatus(1100, Status.ACTIVE)).thenReturn(false);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingPostalCode(1100));
	}
	
	@Test
	public void getPostalDataTest() {
		
		Mockito.when(masterAddressRepository.findMasterAddressByPostalCodeAndStatus(1100, Status.ACTIVE)).thenReturn(masterAddress);
		Assert.assertEquals("dummySubOffice", masterAddressServiceImpl.getPostalData(1100).getSubOffice());
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.getPostalData(1100).getThana());
		
	}
	
	@Test
	public void getAllMasterAddressListTest() {
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		MasterAddress masterAddress1 = new MasterAddress();
		masterAddress1.setId(8L);
		masterAddress1.setZone("ABC");
		masterAddress1.setZoneId(9L);
		masterAddress1.setDivision("ABC Division");
		masterAddress1.setDivisionId(10L);
		masterAddress1.setDistrict("ABC District");
		masterAddress1.setDistrictId(11L);
		masterAddress1.setThanaId(12L);
		masterAddress1.setThana("ABC Thana");
		masterAddress1.setSubOffice("dummySubOffice");
		masterAddress1.setPostalCode(1100);
		masterAddress1.setCreatedBy(userDummy);
		masterAddress1.setUpdatedBy(userDummy);
		masterAddress1.setUpdatedOn(new Date());
		masterAddress1.setStatus(Status.ACTIVE);
		
		masterList.add(masterAddress);
		masterList.add(masterAddress1);
		
		Mockito.when(masterAddressRepository.findAll()).thenReturn(masterList);
		
		Assert.assertEquals(Long.valueOf(5), masterAddressServiceImpl.getAllMasterAddressList().get(0).getId());
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.getAllMasterAddressList().get(0).getThana());
		Assert.assertEquals("dummySubOffice", masterAddressServiceImpl.getAllMasterAddressList().get(0).getSubOffice());
		Assert.assertEquals(Long.valueOf(8), masterAddressServiceImpl.getAllMasterAddressList().get(1).getId());
		Assert.assertEquals("dummySubOffice", masterAddressServiceImpl.getAllMasterAddressList().get(1).getSubOffice());
	}
	
	@Test
	public void getAllMasterAddressListEmptyListTest() {
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		
		Mockito.when(masterAddressRepository.findAll()).thenReturn(masterList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllMasterAddressList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllMasterAddressList().size());
	}
	
	@Test
	public void getAllMasterAddressListByStatusTest() {
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		MasterAddress masterAddress1 = new MasterAddress();
		masterAddress1.setId(8L);
		masterAddress1.setZone("ABC");
		masterAddress1.setZoneId(9L);
		masterAddress1.setDivision("ABC Division");
		masterAddress1.setDivisionId(10L);
		masterAddress1.setDistrict("ABC District");
		masterAddress1.setDistrictId(11L);
		masterAddress1.setThanaId(12L);
		masterAddress1.setThana("ABC Thana");
		masterAddress1.setSubOffice("dummySubOffice");
		masterAddress1.setPostalCode(1100);
		masterAddress1.setCreatedBy(userDummy);
		masterAddress1.setUpdatedBy(userDummy);
		masterAddress1.setUpdatedOn(new Date());
		masterAddress1.setStatus(Status.ACTIVE);
		
		masterList.add(masterAddress);
		masterList.add(masterAddress1);
		
		Mockito.when(masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE)).thenReturn(masterList);
		
		Assert.assertEquals(Long.valueOf(5), masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).get(0).getThana());
		Assert.assertEquals("dummySubOffice", masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).get(0).getSubOffice());
		Assert.assertEquals(Long.valueOf(8), masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).get(1).getId());
		Assert.assertEquals("dummySubOffice", masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).get(1).getSubOffice());
	}
	
	@Test
	public void getAllMasterAddressListByStatusEmptyListTest() {
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		Mockito.when(masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE)).thenReturn(masterList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllMasterAddressListByStatus(Status.ACTIVE).size());
	}
	
}
