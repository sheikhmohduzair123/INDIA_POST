package com.service;

import static org.junit.Assert.assertEquals;

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
import com.domain.Address;
import com.domain.RmsTable;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.services.MasterAddressService;
import com.services.impl.MasterAddressServiceImpl;
import com.sun.jdi.LongValue;
import com.vo.RmsTableVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class RmsPageTest {

	@Mock
	MasterAddressService masterAddressService;

	@InjectMocks
	MasterAddressServiceImpl masterAddressServiceImpl;
	
	@Mock
	RmsRepository rmsRepository;

	@Mock
	RmsTable rt;
	
	@Mock
	User userDummy;
	
	@Mock
	Address adrs;
	
	@Mock
	SUserRepository repository;
	
	@Mock
	MasterAddressRepository masterAddressRepository;

	@Before
	public void init() {
		Mockito.when(repository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(userDummy);
		 rt = new RmsTable();
		 adrs = new Address();
		adrs.setAddressLine1("line1");
		adrs.setAddressLine2("line2");
		adrs.setCountry("dummyCountry");
		adrs.setZone("dummyZone");
		adrs.setDistrict("dummyDistrict");
		adrs.setDivision("dummydivison");
		adrs.setThana("dummyThana");
		adrs.setSubOffice("dummmySubOffice");
		adrs.setLandmark("landmarkDummy");
		adrs.setPostalCode(0000);
		
		rt.setId(10L);
		rt.setRmsName("ABC");
		rt.setRmsType("TypeDummy");
		rt.setCreatedBy(userDummy);
		rt.setUpdatedBy(userDummy);
		rt.setCreatedOn(new Date());
		rt.setUpdatedOn(new Date());
		rt.setStatus(Status.ACTIVE);
		rt.setRmsMobileNumber((long)00000000000);
		rt.setRmsAddress(adrs);
		
	}

	//For save details of RMS
	@Test
	public void saveRmsDetails() {
		
		Mockito.when(rmsRepository.save(rt)).thenReturn(rt);
		
		assertEquals(rt, masterAddressServiceImpl.saveRmsDetails(rt, userDummy));
	}
	
	//for delete exist RMS with active Status
	@Test
	public void DeleteRMSIfCindition() {
		
		Long delId = 1L;
		RmsTable rt2 = new RmsTable();
		rt2.setId(1L);
		rt2.setRmsName("delete testing rmsName");
		rt2.setStatus(Status.ACTIVE);

		Mockito.when(rmsRepository.findById(delId)).thenReturn(Optional.of(rt2));
		
		Mockito.when(rmsRepository.save(rt2)).thenReturn(rt2);
		
		RmsTable deleteData = masterAddressServiceImpl.DeleteRMS(1L, userDummy);
		Assert.assertEquals(rt2, deleteData);
		Assert.assertEquals(Status.DISABLED, deleteData.getStatus());
		
	}	
	
	//for no exist rms in rmsTable (it means rms disabled already) failed part of delete method
	@Test
	public void DeleteRMSElsePart() {
		
		Long delId = 2L;
		RmsTable rt2 = new RmsTable();
		rt2.setId(2L);
		rt2.setRmsName("delete testing rmsName");
		rt2.setStatus(Status.DISABLED);

		Mockito.when(rmsRepository.findById(delId)).thenReturn(Optional.of(rt2));
		
		Assert.assertEquals(null , masterAddressServiceImpl.DeleteRMS(delId, userDummy));
		
	}	
	
	//for updating exist rms in rmsTable with Active Status
	@Test
	public void UpdateRmsDetailsIfCondition() {
		
		Long rmsId = 2L;
		RmsTable oldData = new RmsTable();
		oldData.setId(1L);
		oldData.setRmsName("before update abc");
		oldData.setStatus(Status.ACTIVE);
		
		RmsTable formData = new RmsTable();
		formData.setRmsName("new after updated abc");
		
		RmsTable newRMSObj = new RmsTable();
		newRMSObj.setCreatedOn(oldData.getCreatedOn()); // old date
		oldData.setCreatedOn(oldData.getCreatedOn());// new date
		newRMSObj.setStatus(Status.DISABLED);
		newRMSObj.setUpdatedOn(new Date()); // for new updated date
		oldData.setUpdatedOn(new Date()); // for new update date

		newRMSObj.setCreatedBy(oldData.getCreatedBy());
		oldData.setUpdatedBy(userDummy);
		newRMSObj.setUpdatedBy(userDummy);
		
		newRMSObj.setRmsName(oldData.getRmsName());
		oldData.setRmsName(formData.getRmsName());

		newRMSObj.setRmsType(oldData.getRmsType());
		oldData.setRmsType(formData.getRmsType());

		newRMSObj.setRmsAddress(oldData.getRmsAddress());
		oldData.setRmsAddress(formData.getRmsAddress());

		newRMSObj.setRmsMobileNumber(oldData.getRmsMobileNumber());
		oldData.setRmsMobileNumber(formData.getRmsMobileNumber());
		
		
		Mockito.when(rmsRepository.findById(rmsId)).thenReturn(Optional.of(oldData));
		
		Mockito.when(rmsRepository.save(newRMSObj)).thenReturn(newRMSObj);
		
		Assert.assertEquals("new after updated abc", masterAddressServiceImpl.updateRMS(2L, formData, userDummy).getRmsName());
		Assert.assertNotEquals(newRMSObj.getStatus(), masterAddressServiceImpl.updateRMS(2L, formData, userDummy).getStatus());
		Assert.assertEquals(newRMSObj.getUpdatedBy(), masterAddressServiceImpl.updateRMS(2L, formData, userDummy).getUpdatedBy());
		Assert.assertNotEquals(newRMSObj.getUpdatedOn(), masterAddressServiceImpl.updateRMS(2L, formData, userDummy).getUpdatedOn());
	}
	
	//for no exist rms in rmsTable (it means rms disabled already) failed part of update method
	@Test
	public void UpdateRmsDetailsElsePart() {
		
		Long rmsId = 3L;
		RmsTable oldData = new RmsTable();
		oldData.setId(3L);
		oldData.setRmsName("update abc");
		oldData.setStatus(Status.DISABLED);
		
		RmsTable formData = new RmsTable();
		formData.setRmsName("new updated abc");
		
		Mockito.when(rmsRepository.findById(rmsId)).thenReturn(Optional.of(oldData));
		
		Assert.assertEquals(null , masterAddressServiceImpl.updateRMS(rmsId, formData, userDummy));
	}
	
	//for all get Zone List
	@Test
	public void fetchRMSZoneListTest() {

		List<String> str = new ArrayList<String>();
		str.add("zone1");
		str.add("zone2");
		
		Mockito.when(masterAddressRepository.findDistinctZone(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(2 , masterAddressServiceImpl.fetchRMSZoneList(Status.ACTIVE).size());
		Assert.assertEquals(str , masterAddressServiceImpl.fetchRMSZoneList(Status.ACTIVE));
		 
	}
	
	@Test
	public void fetchRMSZoneListEmptyListTest() {

		List<String> str = new ArrayList<String>();

		Mockito.when(masterAddressRepository.findDistinctZone(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(0, masterAddressServiceImpl.fetchRMSZoneList(Status.ACTIVE).size());
		Assert.assertEquals(true , masterAddressServiceImpl.fetchRMSZoneList(Status.ACTIVE).isEmpty());
		 
	}

	//for all get Division List
	@Test
	public void fetchRMSDivisionListTest() {

		List<String> str = new ArrayList<String>();
		str.add("division1");
		str.add("division2");
		Mockito.when(masterAddressRepository.findDistinctDivision(Status.ACTIVE)).thenReturn(str);
		Assert.assertEquals(2 , masterAddressServiceImpl.fetchRMSDivisionList(Status.ACTIVE).size());
		Assert.assertEquals(str , masterAddressServiceImpl.fetchRMSDivisionList(Status.ACTIVE));
	}
	
	@Test
	public void fetchRMSDivisionListEmptyListTest() {

		List<String> str = new ArrayList<String>();

		Mockito.when(masterAddressRepository.findDistinctDivision(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(0 , masterAddressServiceImpl.fetchRMSDivisionList(Status.ACTIVE).size());
		Assert.assertEquals(true , masterAddressServiceImpl.fetchRMSDivisionList(Status.ACTIVE).isEmpty());
	}

	//for all get District List
	@Test
	public void fetchRMSDistrictListTest() {

		List<String> str = new ArrayList<String>();
		str.add("district1");
		str.add("district2");
		
		Mockito.when(masterAddressRepository.findDistinctDistrict(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(2 , masterAddressServiceImpl.fetchRMSDistrictList(Status.ACTIVE).size());
		Assert.assertEquals(str , masterAddressServiceImpl.fetchRMSDistrictList(Status.ACTIVE));
	}
	
	@Test
	public void fetchRMSDistrictListEmptyListTest() {

		List<String> str = new ArrayList<String>();

		Mockito.when(masterAddressRepository.findDistinctDistrict(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(0 , masterAddressServiceImpl.fetchRMSDistrictList(Status.ACTIVE).size());
		Assert.assertEquals(true , masterAddressServiceImpl.fetchRMSDistrictList(Status.ACTIVE).isEmpty());
	}

	//for all get Thana List
	@Test
	public void fetchRMSThanaListTest() {

		List<String> str = new ArrayList<String>();
		str.add("thana1");
		str.add("thana2");
		Mockito.when(masterAddressRepository.findDistinctThana(Status.ACTIVE)).thenReturn(str);
		Assert.assertEquals(2 , masterAddressServiceImpl.fetchRMSThanaList(Status.ACTIVE).size());
		Assert.assertEquals(str , masterAddressServiceImpl.fetchRMSThanaList(Status.ACTIVE));
	}
	
	@Test
	public void fetchRMSThanaListEmptyListTest() {

		List<String> str = new ArrayList<String>();

		Mockito.when(masterAddressRepository.findDistinctThana(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(0 , masterAddressServiceImpl.fetchRMSThanaList(Status.ACTIVE).size());
		Assert.assertEquals(true , masterAddressServiceImpl.fetchRMSThanaList(Status.ACTIVE).isEmpty());
	}

	//for all get SubOffice List
	@Test
	public void fetchRMSSubOfficeListTest() {

		List<String> str = new ArrayList<String>();
		str.add("subOffice1");
		str.add("subOffice2");
		
		Mockito.when(masterAddressRepository.findDistinctSubOffice(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(2 , masterAddressServiceImpl.fetchRMSSubOfficeList(Status.ACTIVE).size());
		Assert.assertEquals(str , masterAddressServiceImpl.fetchRMSSubOfficeList(Status.ACTIVE));
	}
	
	@Test
	public void fetchRMSSubOfficeListEmptyListTest() {

		List<String> str = new ArrayList<String>();
		
		Mockito.when(masterAddressRepository.findDistinctSubOffice(Status.ACTIVE)).thenReturn(str);
		
		Assert.assertEquals(0 , masterAddressServiceImpl.fetchRMSSubOfficeList(Status.ACTIVE).size());
		Assert.assertEquals(true , masterAddressServiceImpl.fetchRMSSubOfficeList(Status.ACTIVE).isEmpty());
	}
	
	//for during updation get data by id
	@Test
	public void updateButtonFilledDataTest() {

		Long updateId = 1L;
		rt.setId(updateId);
		Mockito.when(rmsRepository.findById(updateId)).thenReturn(Optional.of(rt));
		Assert.assertEquals(rt , masterAddressServiceImpl.updateButtonFilledData(updateId));
		
	}
	
	//during saving data of rms then same rms is already exist
	@Test
	public void getExistRmsDataTest() {
		
		Mockito.when(rmsRepository.existsByRmsNameContainingIgnoreCaseAndRmsTypeAndRmsAddressPostalCodeAndStatus("abc", "GPO", 1111, Status.ACTIVE)).thenReturn(true);
		Assert.assertEquals(true , masterAddressServiceImpl.getExistRmsData("abc", "GPO", 1111, Status.ACTIVE));
	}
	
	//during saving data of rms then rms is not exist in rmsTable (failed method of getExistRmsData)
	@Test
	public void getExistRmsDataTestFalseCondition() {
		
		Mockito.when(rmsRepository.existsByRmsNameContainingIgnoreCaseAndRmsTypeAndRmsAddressPostalCodeAndStatus("abc", "GPO", 1111, Status.ACTIVE)).thenReturn(false);
		Assert.assertNotEquals(true , masterAddressServiceImpl.getExistRmsData("abc", "GPO", 1111, Status.ACTIVE));
		Assert.assertEquals(false , masterAddressServiceImpl.getExistRmsData("abc", "GPO", 1111, Status.ACTIVE));
	}
	
	//get all rms List
	@Test
	public void getAllRmsList() {
		
		List<RmsTable> rmsTableList = new ArrayList<RmsTable>();
		
		rmsTableList.add(rt);
		
		Mockito.when(rmsRepository.findAll()).thenReturn(rmsTableList);		
		Assert.assertEquals(1 , masterAddressServiceImpl.getAllRMSList().size());
		Assert.assertEquals(Long.valueOf(10), masterAddressServiceImpl.getAllRMSList().get(0).getId());
		Assert.assertEquals("ABC" , masterAddressServiceImpl.getAllRMSList().get(0).getRmsName());
	}
	
	@Test
	public void getAllRmsListEmptyList() {
		
		List<RmsTable> rmsTableList = new ArrayList<RmsTable>();
		
		Mockito.when(rmsRepository.findAll()).thenReturn(rmsTableList);		

		Assert.assertEquals(0 , masterAddressServiceImpl.getAllRMSList().size());
		Assert.assertEquals(true , masterAddressServiceImpl.getAllRMSList().isEmpty());
	}
	
	@Test
	public void findRMSByRMSIdTest() {
		
		Mockito.when(rmsRepository.findRmsTableByIdAndStatus(10L, Status.ACTIVE)).thenReturn(rt);
		Assert.assertEquals(rt, masterAddressServiceImpl.findRMSByRMSId(10L));
	}

	@Test
	public void getAllRMSListByStatusTest() {
		
		List<RmsTable> rmsTableList = new ArrayList<RmsTable>();
		
		rmsTableList.add(rt);
		
		Mockito.when(rmsRepository.findAllByStatus(Status.ACTIVE)).thenReturn(rmsTableList);		
		Assert.assertEquals(1 , masterAddressServiceImpl.getAllRMSListByStatus(Status.ACTIVE).size());
		Assert.assertEquals(Long.valueOf(10), masterAddressServiceImpl.getAllRMSListByStatus(Status.ACTIVE).get(0).getId());
		Assert.assertEquals("ABC" , masterAddressServiceImpl.getAllRMSListByStatus(Status.ACTIVE).get(0).getRmsName());
		
	}
	
	@Test
	public void getAllRMSListByStatusEmptyListTest() {
		
		List<RmsTable> rmsTableList = new ArrayList<RmsTable>();
		
		Mockito.when(rmsRepository.findAllByStatus(Status.ACTIVE)).thenReturn(rmsTableList);		

		Assert.assertEquals(0 , masterAddressServiceImpl.getAllRMSListByStatus(Status.ACTIVE).size());
		Assert.assertEquals(true, masterAddressServiceImpl.getAllRMSListByStatus(Status.ACTIVE).isEmpty());
		
	}
	
	
}
