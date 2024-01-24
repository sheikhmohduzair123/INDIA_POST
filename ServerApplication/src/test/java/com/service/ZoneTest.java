package com.service;

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
import com.repositories.ZoneAddressRepo;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ZoneTest {

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
	Control control;
	
	@Mock
	ControlRepository controlRepo;
	
	@Mock
	ZoneAddressRepo zoneAddressRepo;
	
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
		
		control = new Control();
		control.setId(1L);
		control.setStatus(Status.ACTIVE);
		control.setUpdatedOn(new Date());
	}

	//For save details of Zone
	@Test
	public void saveZoneDetailsTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Mockito.when(zoneAddressRepo.save(zone)).thenReturn(zone);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(zone , masterAddressServiceImpl.saveZoneDetails(zone, userDummy));
	}
	
	
	@Test
	public void updateZoneServiceTestIfCondition() {

		String updateZone = "zoneUpdatedSuccessfully";
		
		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);
		
		Zone newZoneEntry = new Zone();
		List<MasterAddress> master = new ArrayList<MasterAddress>();
		MasterAddress m = new MasterAddress();
		m.setZoneId(1L);
		m.setZone(zone.getZone());
		m.setSubOffice("dummySubOffice");
		master.add(m);
		
		master.get(0).setZone(updateZone);
		master.get(0).setUpdatedOn(new Date());
		master.get(0).setUpdatedBy(userDummy);
		
		//newZoneEntry.setCreatedOn(zone.getCreatedOn());
		//newZoneEntry.setZone(zone.getZone());
		zone.setZone(updateZone);
		//newZoneEntry.setStatus(Status.DISABLED);
		//newZoneEntry.setUpdatedOn(new Date());
		//zone.setUpdatedOn(new Date()); // new date
		//newZoneEntry.setCreatedBy(zone.getCreatedBy());
		newZoneEntry.setUpdatedBy(userDummy);
		zone.setUpdatedBy(userDummy);
		
		
		Mockito.when(zoneAddressRepo.findById(1L)).thenReturn(java.util.Optional.of(zone));
		Mockito.when(masterAddressRepository.findMasterAddressByZoneId(zone.getId())).thenReturn(master);
		Mockito.when(zoneAddressRepo.save(newZoneEntry)).thenReturn(newZoneEntry);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		
		Assert.assertEquals(updateZone, masterAddressServiceImpl.updateZoneService(1L, userDummy, updateZone).getZone());
		Assert.assertNotEquals(newZoneEntry.getStatus(), masterAddressServiceImpl.updateZoneService(1L, userDummy, updateZone).getStatus());
		Assert.assertNotEquals(newZoneEntry.getZone(), masterAddressServiceImpl.updateZoneService(1L, userDummy, updateZone).getZone());
		Assert.assertEquals(newZoneEntry.getUpdatedBy(), masterAddressServiceImpl.updateZoneService(zone.getId(), userDummy, updateZone).getUpdatedBy());
		Assert.assertNotEquals(newZoneEntry.getUpdatedOn(), masterAddressServiceImpl.updateZoneService(zone.getId(), userDummy, updateZone).getUpdatedOn());
	}
	
	@Test
	public void updateZoneServiceTestElseCondition() {

		String updateZone = "zoneUpdatedSuccessfully";
		
		Zone zone1 = new Zone();
		zone1.setId(1L);
		zone1.setZone("Zone1");
		zone1.setStatus(Status.DISABLED);
		
		List<MasterAddress> master = new ArrayList<MasterAddress>();
		MasterAddress m = new MasterAddress();
		m.setZoneId(1L);
		m.setZone("oldZone");
		m.setSubOffice("dummySubOffice");
		master.add(m);
		
		Mockito.when(zoneAddressRepo.findById(1L)).thenReturn(java.util.Optional.of(zone1));
		Mockito.when(masterAddressRepository.findMasterAddressByZoneId(1L)).thenReturn(master);
		
		Assert.assertEquals(null, masterAddressServiceImpl.updateZoneService(1L, userDummy, updateZone));
	}
	
	@Test
	public void deleteZoneServiceIfConditionTest() {


			List<Control> controlList = new ArrayList<Control>();
			controlList.add(control);
			
			Zone zone1 =new Zone();
			zone1.setId(1L);
			zone1.setStatus(Status.ACTIVE);
			zone1.setZone("testZone");
		
			List<Division> divisionList = new ArrayList<Division>();
			
			Division division1 = new Division();
			division1.setId(2L);
			division1.setDivision("testDiv");
			division1.setStatus(Status.ACTIVE);
			division1.setZone(zone1);
			
			divisionList.add(division1);
			
			List<District> districtList = new ArrayList<District>();
			
			District dist1 =new District();
			dist1.setId(4L);
			dist1.setDistrict("test district");
			dist1.setStatus(Status.ACTIVE);
			dist1.setDivision(division1);
			
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
 			
			Mockito.when(zoneAddressRepo.findById(1L)).thenReturn(Optional.of(zone1));
			Mockito.when(divisionAddressRepo.findDivisionByzone_id(zone1.getId())).thenReturn(divisionList);
			Mockito.when(districtAddressRepo.findByDivisionIn(divisionList)).thenReturn(districtList);
			Mockito.when(thanaAddressRepo.findByDistrictIn(districtList)).thenReturn(thanaList);
			Mockito.when(masterAddressRepository.findByThanaIdIn(thanaId)).thenReturn(masterList);
			Mockito.when(zoneAddressRepo.save(zone1)).thenReturn(zone1);
			
			Mockito.when(controlRepo.findAll()).thenReturn(controlList);
			
			Zone zone2 = masterAddressServiceImpl.deleteZoneService(1L, userDummy);
			
			Assert.assertEquals(zone1, zone2);
			Assert.assertEquals(Status.DISABLED, zone2.getStatus());
			
	}
	
	@Test
	public void deleteZoneServiceElseConditionTest() {

			Zone zone1 =new Zone();
			zone1.setId(1L);
			zone1.setStatus(Status.DISABLED);
			zone1.setZone("testZone");
			
			Mockito.when(zoneAddressRepo.findById(1L)).thenReturn(Optional.of(zone1));
			
			Assert.assertEquals(null, masterAddressServiceImpl.deleteZoneService(1L, userDummy));
	}
	
	@Test
	public void fetchExistingZoneTest() {
		
		//String zoneName = "Zone1";
		
		List<Zone> validZoneList = new ArrayList<Zone>();
		validZoneList.add(zone);
		
		Mockito.when(zoneAddressRepo.findByZoneContainingIgnoreCaseAndStatus("Zone1", Status.ACTIVE)).thenReturn(validZoneList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchExistingZone(zone.getZone()));
		
	}
	
	@Test
	public void fetchExistingZoneElseConditionTest() {
		
		List<Zone> validZoneList = new ArrayList<Zone>();
		
		Mockito.when(zoneAddressRepo.findByZoneContainingIgnoreCaseAndStatus("Zone12", Status.ACTIVE)).thenReturn(validZoneList);
		Assert.assertEquals(false, masterAddressServiceImpl.fetchExistingZone("zone2"));
		
	}
	
	@Test
	public void fetchZoneListTest() {
		List<Zone> zoneList = new ArrayList<Zone>();
		Zone zone1 = new Zone();
		zone1.setId(2L);
		zone1.setStatus(Status.ACTIVE);
		
		zoneList.add(zone);
		zoneList.add(zone1);
		
		Mockito.when(zoneAddressRepo.findAllByStatusOrderByZoneAsc(Status.ACTIVE)).thenReturn(zoneList);
		Assert.assertEquals(zoneList, masterAddressServiceImpl.fetchZoneList());
		Assert.assertEquals(zoneList.size(), masterAddressServiceImpl.fetchZoneList().size());
	}
	
	@Test
	public void fetchZoneListEmptyZoneDataTest() {
		List<Zone> zoneList = new ArrayList<Zone>();
		
		Mockito.when(zoneAddressRepo.findAllByStatusOrderByZoneAsc(Status.ACTIVE)).thenReturn(zoneList);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchZoneList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.fetchZoneList().size());
	}
	
	
	@Test
	public void getAllZoneListTest() {
		
		List<Zone> zoneList = new ArrayList<Zone>();
		Zone zone1 = new Zone();
		zone1.setId(2L);
		zone1.setZone("testingZone");
		zone1.setCreatedOn(new Date());
		zone1.setUpdatedOn(new Date());
		zone1.setUpdatedBy(userDummy);
		zone1.setStatus(Status.ACTIVE);
		
		zoneList.add(zone);
		zoneList.add(zone1);
		
		Mockito.when(zoneAddressRepo.findAll()).thenReturn(zoneList);
		
		Assert.assertEquals(Long.valueOf(1), masterAddressServiceImpl.getAllZoneList().get(0).getId());
		Assert.assertEquals("Zone1", masterAddressServiceImpl.getAllZoneList().get(0).getZone());
		Assert.assertEquals(Long.valueOf(2), masterAddressServiceImpl.getAllZoneList().get(1).getId());
		Assert.assertEquals("testingZone", masterAddressServiceImpl.getAllZoneList().get(1).getZone());
	}
	
	@Test
	public void getAllZoneListEmptyListTest() {
		
		List<Zone> zoneList = new ArrayList<Zone>();
		
		Mockito.when(zoneAddressRepo.findAll()).thenReturn(zoneList);

		Assert.assertEquals(true, masterAddressServiceImpl.getAllZoneList().isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllZoneList().size());
	}
	
	@Test
	public void getAllZoneListByStatusTest() {
		
		List<Zone> zoneList = new ArrayList<Zone>();
		
		Zone zone1 = new Zone();
		zone1.setId(2L);
		zone1.setZone("testingZone");
		zone1.setCreatedOn(new Date());
		zone1.setUpdatedOn(new Date());
		zone1.setUpdatedBy(userDummy);
		zone1.setStatus(Status.ACTIVE);
		
		zoneList.add(zone);
		zoneList.add(zone1);
		
		Mockito.when(zoneAddressRepo.findAllByStatusOrderByZoneAsc(Status.ACTIVE)).thenReturn(zoneList);
		
		Assert.assertEquals(Long.valueOf(1), masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("Zone1", masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).get(0).getZone());
		Assert.assertEquals(Long.valueOf(2), masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).get(1).getId());
		Assert.assertEquals("testingZone", masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).get(1).getZone());
	}
	
	@Test
	public void getAllZoneListByStatusEmptyListTest() {
		
		List<Zone> zoneList = new ArrayList<Zone>();
		
		Mockito.when(zoneAddressRepo.findAllByStatusOrderByZoneAsc(Status.ACTIVE)).thenReturn(zoneList);
		
		Assert.assertEquals(true, masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).isEmpty());
		Assert.assertEquals(0, masterAddressServiceImpl.getAllZoneListByStatus(Status.ACTIVE).size());
	}
	
}
	
	
	

