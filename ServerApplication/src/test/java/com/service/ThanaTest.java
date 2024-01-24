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
import com.vo.ThanaVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ThanaTest {

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
		
	}

	@Test
	public void saveThanaDetailsTest() {
		
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Mockito.when(thanaAddressRepo.save(thana)).thenReturn(thana);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);

		Assert.assertEquals(thana , masterAddressServiceImpl.saveThanaDetails(thana, userDummy));
	}
	
	
	@Test
	public void updateThanaDetailsIfConditionTest() {
		
		String thanaName = "thana updated successfully";
		
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Thana th =new Thana();
		th.setId(8L);
		th.setDistrict(district);
		th.setStatus(Status.ACTIVE);
		th.setThana("testing Thana");
		th.setCreatedOn(new Date());
		th.setCreatedBy(userDummy);
	
		Thana newThana = new Thana();
		
		List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
		MasterAddress m = new MasterAddress();
		m.setThanaId(8L);
		m.setThana("oldThana");
		m.setSubOffice("dummySubOffice");
		masterAddressesList.add(m);
	
		masterAddressesList.get(0).setDistrict(thanaName);
		masterAddressesList.get(0).setUpdatedBy(userDummy);
		masterAddressesList.get(0).setUpdatedOn(new Date());
		
		//newThana.setCreatedOn(th.getCreatedOn());// old data
		newThana.setDistrict(th.getDistrict());// old data
		th.setDistrict(district);
		newThana.setStatus(Status.DISABLED);
		newThana.setThana(th.getThana());
		th.setThana(thanaName);
		//newThana.setCreatedBy(th.getCreatedBy());
		newThana.setUpdatedBy(userDummy);
		//th.setUpdatedOn(new Date());
		//th.setCreatedOn(th.getCreatedOn());
		//newThana.setUpdatedOn(new Date());
		th.setUpdatedBy(userDummy);
		
		
		Mockito.when(masterAddressRepository.findMasterAddressByThanaId(th.getId())).thenReturn(masterAddressesList);

		Mockito.when(thanaAddressRepo.save(newThana)).thenReturn(newThana);
		
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);

		Assert.assertEquals(thanaName, masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName).getThana());
		Assert.assertNotEquals(newThana.getStatus(), masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName).getStatus());
		Assert.assertNotEquals(newThana.getThana(), masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName).getThana());
		Assert.assertEquals(newThana.getUpdatedBy(), masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName).getUpdatedBy());
		Assert.assertNotEquals(newThana.getUpdatedOn(), masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName).getUpdatedOn());

}

	
	@Test
	public void updateThanaDetailsElseConditionTest() {
		
		String thanaName = "thana updated successfully";
		
		Thana th =new Thana();
		th.setId(8L);
		th.setDistrict(district);
		th.setStatus(Status.DISABLED);
		th.setThana("testing Thana");
		th.setCreatedOn(new Date());
		th.setCreatedBy(userDummy);
	
		List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
		MasterAddress m = new MasterAddress();
		m.setThanaId(8L);
		m.setThana("oldThana");
		m.setSubOffice("dummySubOffice");
		masterAddressesList.add(m);
		
		Mockito.when(masterAddressRepository.findMasterAddressByThanaId(th.getId())).thenReturn(masterAddressesList);

		Assert.assertEquals(null , masterAddressServiceImpl.updateThanaDetails(th, userDummy, district, thanaName));
	}
	
	
	@Test
	public void deleteThanaDetailsIfConditionTest() {

			List<Control> controlList = new ArrayList<Control>();
			controlList.add(control);
			
			Thana th =new Thana();
			th.setId(8L);
			th.setDistrict(district);
			th.setStatus(Status.ACTIVE);
			th.setThana("testing Thana");
			th.setCreatedOn(new Date());
			th.setUpdatedOn(new Date());
			th.setCreatedBy(userDummy);
			
			List<MasterAddress> masterList = new ArrayList<MasterAddress>();
			
			MasterAddress m = new MasterAddress();
			m.setId(8L);
			m.setStatus(Status.ACTIVE);
			m.setSubOffice("test subOffice");
			m.setThanaId(th.getId());
			masterList.add(m);
			
			Mockito.when(masterAddressRepository.findMasterAddressByThanaAndStatus(th.getThana(), Status.ACTIVE)).thenReturn(masterList);
			Mockito.when(thanaAddressRepo.save(th)).thenReturn(th);
			
			Mockito.when(controlRepo.findAll()).thenReturn(controlList);
			
			Thana deleteData = masterAddressServiceImpl.deleteThanaDetails(th, userDummy);

			Assert.assertEquals(th, deleteData);
			Assert.assertEquals(Status.DISABLED, deleteData.getStatus());
	}
	
	@Test
	public void deleteThanaDetailsElseCondition() {

			Thana th =new Thana();
			th.setId(8L);
			th.setStatus(Status.DISABLED);
			th.setThana("testing Thana");
			
			Assert.assertEquals(null, masterAddressServiceImpl.deleteThanaDetails(th, userDummy));
	}
	
	@Test
	public void fetchExistingThanaIfConditionTest() {
		
		List<Thana> validThanaList = new ArrayList<Thana>();
		validThanaList.add(thana);
		
		Mockito.when(thanaAddressRepo.findThanaBydistrict_idAndThanaContainingIgnoreCaseAndStatus(district.getId(),"dummyThana", Status.ACTIVE)).thenReturn(validThanaList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingThana(district.getId(), "dummyThana"));
		
	}
	
	@Test
	public void fetchExistingThanaElseCondition() {
		
		List<Thana> validThanaList = new ArrayList<Thana>();
		
		Mockito.when(thanaAddressRepo.findThanaBydistrict_idAndThanaContainingIgnoreCaseAndStatus(5L,"dummyData", Status.ACTIVE)).thenReturn(validThanaList);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingThana(5L, "dummyData"));
		
	}
	

	@Test
	public void fetchThanaListTest() {
		List<Thana> ThanaList = new ArrayList<Thana>();
		Thana thana1 = new Thana();
		thana1.setId(5L);
		thana1.setStatus(Status.ACTIVE);
		
		ThanaList.add(thana);
		ThanaList.add(thana1);
		
		Mockito.when(thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE)).thenReturn(ThanaList);
		Assert.assertEquals(ThanaList, masterAddressServiceImpl.fetchThanaList());
		Assert.assertEquals(ThanaList.size(), masterAddressServiceImpl.fetchThanaList().size());
	
	}
	
	@Test
	public void fetchThanaListEmptyListTest() {
		List<Thana> ThanaList = new ArrayList<Thana>();
		
		Mockito.when(thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE)).thenReturn(ThanaList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchThanaList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchThanaList().size());
	
	}
	
	
	@Test
	public void fetchThanaListByDistrictTest() {
		
		List<Thana> thanaList = new ArrayList<Thana>();
		thanaList.add(thana);
		
		Mockito.when(thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(3L, Status.ACTIVE)).thenReturn(thanaList);
		
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.fetchThanaListByDistrict(3L).get(0).getThana());
		Assert.assertEquals(Long.valueOf(4), masterAddressServiceImpl.fetchThanaListByDistrict(3L).get(0).getId());
	}
	
	@Test
	public void fetchThanaListByDistrictEmptyListTest() {
		
		List<Thana> thanaList = new ArrayList<Thana>();

		Mockito.when(thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(3L, Status.ACTIVE)).thenReturn(thanaList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.fetchThanaListByDistrict(3L).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchThanaListByDistrict(3L).size());
	}
	
	@Test
	public void getAllThanaListTest() {
		List<Thana> ThanaList = new ArrayList<Thana>();
		
		District district1 = new District();
		district1.setId(4L);
		district1.setDistrict("testingDistrict");
		district1.setStatus(Status.ACTIVE);
		
		Thana thana1 = new Thana();
		thana1.setId(5L);
		thana1.setDistrict(district1);
		thana1.setThana("testingThana");
		thana1.setCreatedOn(new Date());
		thana1.setUpdatedOn(new Date());
		thana1.setUpdatedBy(userDummy);
		thana1.setStatus(Status.ACTIVE);
		
		ThanaList.add(thana);
		ThanaList.add(thana1);
		
		Mockito.when(thanaAddressRepo.findAll()).thenReturn(ThanaList);
		
		Assert.assertEquals(Long.valueOf(4), masterAddressServiceImpl.getAllThanaList().get(0).getId());
		Assert.assertEquals("dummyDistrict", masterAddressServiceImpl.getAllThanaList().get(0).getDistrictName());
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.getAllThanaList().get(0).getThana());
		Assert.assertEquals(Long.valueOf(5), masterAddressServiceImpl.getAllThanaList().get(1).getId());
		Assert.assertEquals("testingThana", masterAddressServiceImpl.getAllThanaList().get(1).getThana());
	}
	
	@Test
	public void getAllThanaListEmptyListTest() {
		List<Thana> ThanaList = new ArrayList<Thana>();
		
		
		Mockito.when(thanaAddressRepo.findAll()).thenReturn(ThanaList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllThanaList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllThanaList().size());
	}
	
	@Test
	public void getAllThanaListByStatusTest() {
		
		List<Thana> ThanaList = new ArrayList<Thana>();
		
		District district1 = new District();
		district1.setId(4L);
		district1.setDistrict("testingDistrict");
		district1.setStatus(Status.ACTIVE);
		
		Thana thana1 = new Thana();
		thana1.setId(5L);
		thana1.setDistrict(district1);
		thana1.setThana("testingThana");
		thana1.setCreatedOn(new Date());
		thana1.setUpdatedOn(new Date());
		thana1.setUpdatedBy(userDummy);
		thana1.setStatus(Status.ACTIVE);
		
		ThanaList.add(thana);
		ThanaList.add(thana1);
		
		Mockito.when(thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE)).thenReturn(ThanaList);
		
		Assert.assertEquals(Long.valueOf(4), masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("dummyDistrict", masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).get(0).getDistrictName());
		Assert.assertEquals("dummyThana", masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).get(0).getThana());
		Assert.assertEquals(Long.valueOf(5), masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).get(1).getId());
		Assert.assertEquals("testingThana", masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).get(1).getThana());
	}
	
	@Test
	public void getAllThanaListByStatusEmptyListTest() {
		
		List<Thana> ThanaList = new ArrayList<Thana>();
		
		Mockito.when(thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE)).thenReturn(ThanaList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllThanaListByStatus(Status.ACTIVE).size());
	}
	
}
