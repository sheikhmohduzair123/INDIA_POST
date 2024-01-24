package com.service;

import java.sql.Timestamp;
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
import com.repositories.DistrictAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.SUserRepository;
import com.repositories.ThanaAddressRepo;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;
import com.vo.DistrictVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class DistrictTest {

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
	Control control;
	
	@Mock
	ControlRepository controlRepo;
	
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
		
	}

	
	@Test
	public void saveDistrictTest() {


		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Mockito.when(districtAddressRepo.save(district)).thenReturn(district);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);

		Assert.assertEquals(district , masterAddressServiceImpl.saveDistrict(district, userDummy));
	}
	
	@Test
	public void updateDistrictIfConditionTest(){

			String districtName = "district updated successfully";
			
			List<Control> controlList = new ArrayList<Control>();
			controlList.add(control);
			
			District dist =new District();
			dist.setId(8L);
			dist.setDivision(division);
			dist.setStatus(Status.ACTIVE);
			dist.setDistrict("testing District");
			dist.setCreatedOn(new Date());
			dist.setCreatedBy(userDummy);
		
			District newDistrict = new District();
			
			List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
			MasterAddress m = new MasterAddress();
			m.setDistrictId(8L);
			m.setDistrict("oldDistrict");
			m.setSubOffice("dummySubOffice");
			masterAddressesList.add(m);
		
			masterAddressesList.get(0).setDistrict(districtName);
			masterAddressesList.get(0).setUpdatedBy(userDummy);
			masterAddressesList.get(0).setUpdatedOn(new Date());
			
			//newDistrict.setCreatedOn(dist.getCreatedOn());
			newDistrict.setDivision(dist.getDivision());
			dist.setDivision(division);
			newDistrict.setStatus(Status.DISABLED);
			//newDistrict.setUpdatedOn(new Date());
			//dist.setUpdatedOn(new Date());
			newDistrict.setDistrict(dist.getDistrict());
			dist.setDistrict(districtName);
			//newDistrict.setCreatedBy(dist.getCreatedBy());
			dist.setUpdatedBy(userDummy);
			newDistrict.setUpdatedBy(userDummy);
			
			Mockito.when(masterAddressRepository.findMasterAddressByDistrictId(dist.getId())).thenReturn(masterAddressesList);

			Mockito.when(districtAddressRepo.save(newDistrict)).thenReturn(newDistrict);
			
			Mockito.when(controlRepo.findAll()).thenReturn(controlList);

			Assert.assertEquals(districtName, masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName).getDistrict());
			Assert.assertNotEquals(newDistrict.getStatus(), masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName).getStatus());
			Assert.assertNotEquals(newDistrict.getDistrict(), masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName).getDistrict());
			Assert.assertEquals(newDistrict.getUpdatedBy(), masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName).getUpdatedBy());
			Assert.assertNotEquals(newDistrict.getUpdatedOn(), masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName).getUpdatedOn());
	
	}

	@Test
	public void updateDistrictElseCondtionTest(){

			String districtName = "district updated successfully";
			
			District dist =new District();
			dist.setId(8L);
			dist.setDivision(division);
			dist.setStatus(Status.DISABLED);
			dist.setDistrict("testing District");
			dist.setCreatedOn(new Date());
			dist.setCreatedBy(userDummy);
		
			List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
			MasterAddress m = new MasterAddress();
			m.setDivisionId(8L);
			m.setDivision("oldDivision");
			m.setSubOffice("dummySubOffice");
			masterAddressesList.add(m);
		
			Mockito.when(masterAddressRepository.findMasterAddressByDistrictId(dist.getId())).thenReturn(masterAddressesList);

			Assert.assertEquals(null , masterAddressServiceImpl.updateDistrict(dist, userDummy, division, districtName));
	}
	
	
	@Test
	public void DistrictDeleteIfConditionTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		District dist =new District();
		dist.setId(8L);
		dist.setDivision(division);
		dist.setStatus(Status.ACTIVE);
		dist.setDistrict("testing District");
		dist.setCreatedOn(new Date());
		dist.setUpdatedOn(new Date());
		dist.setCreatedBy(userDummy);
		
		List<Thana> thanaList = new ArrayList<Thana>();
		
		Thana thana1 =new Thana();
		thana1.setId(6L);
		thana1.setThana("test Thana");
		thana1.setStatus(Status.ACTIVE);
		thana1.setDistrict(dist);
		
		thanaList.add(thana1);
		
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		MasterAddress m = new MasterAddress();
		m.setId(8L);
		m.setStatus(Status.ACTIVE);
		m.setSubOffice("test subOffice");
		m.setThanaId(thana1.getId());
		masterList.add(m);
		
		Mockito.when(thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(dist.getId(), Status.ACTIVE)).thenReturn(thanaList);
		Mockito.when(masterAddressRepository.findMasterAddressByThanaAndStatus(thana1.getThana(), Status.ACTIVE)).thenReturn(masterList);
		Mockito.when(districtAddressRepo.save(dist)).thenReturn(dist);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		
		District deleteData = masterAddressServiceImpl.DistrictDelete(dist, userDummy);

		Assert.assertEquals(dist, deleteData);
		Assert.assertEquals(Status.DISABLED, deleteData.getStatus());
		
}
	
	@Test
	public void DistrictDeleteElseConditionTest() {

		District dist =new District();
		dist.setId(8L);
		dist.setStatus(Status.DISABLED);
		dist.setDistrict("testing District");
		
		Assert.assertEquals(null, masterAddressServiceImpl.DistrictDelete(dist, userDummy));
}
	
	@Test
	public void fetchExistingDistrictIfConditionTest() {
		
		List<District> validDistrictList = new ArrayList<District>();
		validDistrictList.add(district);
		
		Mockito.when(districtAddressRepo.findDistrictBydivision_idAndDistrictContainingIgnoreCaseAndStatus(division.getId(),"dummyDistrict", Status.ACTIVE)).thenReturn(validDistrictList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingDistrict(division.getId(), "dummyDistrict"));
		
	}
	
	@Test
	public void fetchExistingDistrictElseCondition() {
		
		List<District> validDistrictList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findDistrictBydivision_idAndDistrictContainingIgnoreCaseAndStatus(3L,"dummyData", Status.ACTIVE)).thenReturn(validDistrictList);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingDistrict(3L, "dummyData"));
		
	}
	
	
	@Test
	public void fetchDistrictListTest() {
		
		List<District> districtList = new ArrayList<District>();
		District district1 = new District();
		district1.setId(4L);
		district1.setStatus(Status.ACTIVE);
		
		districtList.add(district);
		districtList.add(district1);
		
		Mockito.when(districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE)).thenReturn(districtList);
		
		Assert.assertEquals(districtList, masterAddressServiceImpl.fetchDistrictList());
		Assert.assertEquals(districtList.size(), masterAddressServiceImpl.fetchDistrictList().size());
	}
	
	@Test
	public void fetchDistrictListEmptyListTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE)).thenReturn(districtList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchDistrictList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchDistrictList().size());
	}

	@Test
	public void fetchDistrictListByDivisionTest() {
		
		List<District> districtList = new ArrayList<District>();
		districtList.add(district);
		
		Mockito.when(districtAddressRepo.findDistrictBydivision_idAndStatusOrderByDistrictAsc(2L, Status.ACTIVE)).thenReturn(districtList);
		
		Assert.assertEquals("dummyDistrict", masterAddressServiceImpl.fetchDistrictListByDivision(2L).get(0).getDistrict());
		Assert.assertEquals(Long.valueOf(3), masterAddressServiceImpl.fetchDistrictListByDivision(2L).get(0).getId());
	}
	
	@Test
	public void fetchDistrictListByDivisionEmptyListTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findDistrictBydivision_idAndStatusOrderByDistrictAsc(2L, Status.ACTIVE)).thenReturn(districtList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchDistrictListByDivision(2L).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchDistrictListByDivision(2L).size());
	}
	
	@Test
	public void getAllDistrictListTest() {
		List<District> districtList = new ArrayList<District>();
		
		Division division1 = new Division();
		division1.setId(3L);
		division1.setDivision("testingDivision");
		division1.setStatus(Status.ACTIVE);
		
		District district1 = new District();
		district1.setId(4L);
		district1.setDistrict("testingDistrict");
		district1.setDivision(division1);
		district1.setCreatedOn(new Date());
		district1.setUpdatedOn(new Date());
		district1.setUpdatedBy(userDummy);
		district1.setStatus(Status.ACTIVE);
		
		districtList.add(district);
		districtList.add(district1);
		
		Mockito.when(districtAddressRepo.findAll()).thenReturn(districtList);
		Assert.assertEquals(Long.valueOf(3), masterAddressServiceImpl.getAllDistrictList().get(0).getId());
		Assert.assertEquals("dummyDivision", masterAddressServiceImpl.getAllDistrictList().get(0).getDivisionName());
		Assert.assertEquals("dummyDistrict", masterAddressServiceImpl.getAllDistrictList().get(0).getDistrict());
		Assert.assertEquals(Long.valueOf(4), masterAddressServiceImpl.getAllDistrictList().get(1).getId());
		Assert.assertEquals("testingDistrict", masterAddressServiceImpl.getAllDistrictList().get(1).getDistrict());
	}
	
	@Test
	public void getAllDistrictListEmptyListTest() {
		List<District> districtList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findAll()).thenReturn(districtList);

		Assert.assertEquals(true, masterAddressServiceImpl.getAllDistrictList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllDistrictList().size());
	}
	
	@Test
	public void getAllDistrictListByStatusTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		Division division1 = new Division();
		division1.setId(3L);
		division1.setDivision("testingDivision");
		division1.setStatus(Status.ACTIVE);
		
		District district1 = new District();
		district1.setId(4L);
		district1.setDistrict("testingDistrict");
		district1.setDivision(division1);
		district1.setCreatedOn(new Date());
		district1.setUpdatedOn(new Date());
		district1.setUpdatedBy(userDummy);
		district1.setStatus(Status.ACTIVE);
		
		districtList.add(district);
		districtList.add(district1);
		
		Mockito.when(districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE)).thenReturn(districtList);
		
		Assert.assertEquals(Long.valueOf(3), masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("dummyDivision", masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).get(0).getDivisionName());
		Assert.assertEquals("dummyDistrict", masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).get(0).getDistrict());
		Assert.assertEquals(Long.valueOf(4), masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).get(1).getId());
		Assert.assertEquals("testingDistrict", masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).get(1).getDistrict());
	}
	
	@Test
	public void getAllDistrictListByStatusEmptyListTest() {
		
		List<District> districtList = new ArrayList<District>();
		
		Mockito.when(districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE)).thenReturn(districtList);

		Assert.assertEquals(true, masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllDistrictListByStatus(Status.ACTIVE).size());
	}
	
}
