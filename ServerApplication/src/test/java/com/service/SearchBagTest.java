package com.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.ParcelService;
import com.services.impl.ParcelServiceImpl;
import com.vo.TrackingVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class SearchBagTest {

	@Mock
	ParcelService parcelService;

	@InjectMocks
	ParcelServiceImpl parcelServiceImpl;

	@Mock
	TrackingCSRepository trackingCSRepo;

	@Mock
	TrackingDetailsRepository trackingDetailsRepository;

	
	@Mock
	User userDummy;
	
	@Mock
	Role role;
	
	@Mock
	RmsTable rmsTable;
	
	@Mock
	Parcel objParcel;
	
	@Mock
	MasterAddress master;
	
	@Mock
	MasterAddressRepository masterAddressRepository;
	
	@Mock
	TrackingVo trackingVo;

	@Mock
	SUserRepository repository;

	@Mock
	RmsRepository rmsRepository;
	
	
	@Before
	public void init() {
		Mockito.when(repository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(userDummy);
		objParcel = new Parcel();
		objParcel.setId(9L);
		objParcel.setTrackId("1111111111111");

		rmsTable =new RmsTable();
		rmsTable.setId(8L);
		rmsTable.setRmsName("ABC");
		rmsTable.setRmsType("GPO");
		
		
		master = new MasterAddress();
		master.setId(1L);
		master.setPostalCode(1111);
		master.setSubOffice("dummy subOfc");
		
		role = new Role();
		role.setId(4);
		role.setName("ROLE_RMS_USER");
		
		userDummy = new User();
		userDummy.setId(7);
		userDummy.setStatus(Status.ACTIVE);
		userDummy.setRole(role);
		userDummy.setRmsId(8L);
		userDummy.setPostalCode(0);
		
		
	}

	//when bagId is not found in trackingCS table and trackingDetails table
	@Test
	public void findBagDetailsWithBagIdTest() {
		

		String bagId = "123456789";
		
		List<TrackingCS> bagData = new ArrayList<TrackingCS>();
		
		List<TrackingDetails> bagDisableData = new ArrayList<>();
		
		Mockito.when(trackingCSRepo.findAllByBagId(bagId)).thenReturn(bagData);
		Mockito.when(trackingDetailsRepository.findAllByBagIdAndStatusOrderByUpdatedOnDesc(bagId, Status.DISABLED)).thenReturn(bagDisableData);
		
		Assert.assertEquals(null , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy));
		
		
	}
	
	//when bagId is not found in trackingCS table and found in trackingDetails table where LocationType is RMS
	@Test
	public void findBagDetailsWithBagIdInDisableTestIfConditionWithLocationRMS() {

		String bagId = "123456789";
		
		List<TrackingCS> bagData = new ArrayList<TrackingCS>();
		
		List<TrackingDetails> bagDisableData = new ArrayList<>();
		
		Parcel objPrcl = new Parcel();
		objPrcl.setId(9L);
		objPrcl.setpStatus("OUT_FOR_DELIVERY");
		objPrcl.setTrackId("1111111111111");
		
		TrackingDetails td = new TrackingDetails();
		td.setId(1L);
		td.setStatus(Status.DISABLED);
		td.setLocationId(8L);
		td.setLocationType(LocationType.RMS);
		td.setObjParcel(objPrcl);
		td.setBagDesc("abcdef bag desc");
		td.setBagTitle("abcd bagTitle");
		td.setBagStatus(BagStatus.UNBAGGED);
		td.setUpdatedOn(new java.util.Date());
		td.setBagId(bagId);

		TrackingDetails td2 = new TrackingDetails();
		td2.setId(2L);
		td2.setStatus(Status.DISABLED);
		td2.setLocationId(9L);
		td2.setLocationType(LocationType.RMS);
		td2.setObjParcel(objPrcl);
		
		bagDisableData.add(td);
		bagDisableData.add(td2);
		
		List<TrackingDetails> list1 = bagDisableData
                .stream()
                .filter(distinctByKey(TrackingDetails::getObjParcel))
                .collect(Collectors.toList());
		
		TrackingCS tc =new TrackingCS();
		tc.setId(9L);
		tc.setBagId("222222222");
		tc.setObjParcel(objPrcl);

		tc.setUpdatedOn(new java.util.Date());
		tc.setpStatus(PStatus.OUT_FOR_DELIVERY);
		tc.setStatus(Status.ACTIVE);
		
		

		Mockito.when(trackingCSRepo.findAllByBagId(bagId)).thenReturn(bagData);
		Mockito.when(trackingDetailsRepository.findAllByBagIdAndStatusOrderByUpdatedOnDesc(bagId, Status.DISABLED)).thenReturn(bagDisableData);
		
		Mockito.when(trackingCSRepo.findTrackingCSByObjParcel(list1.get(0).getObjParcel())).thenReturn(tc);
		Mockito.when(rmsRepository.findById(list1.get(0).getLocationId())).thenReturn(Optional.of(rmsTable));
		
		Assert.assertEquals(1 , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).size());
		Assert.assertEquals(bagId , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagId());
		Assert.assertEquals(BagStatus.UNBAGGED, parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagStatus());
		Assert.assertEquals(PStatus.OUT_FOR_DELIVERY , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getpStatus());
		Assert.assertEquals(objParcel , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getObjParcelVo());
		
	}
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
      Map<Object, Boolean> seen = new ConcurrentHashMap<>();
      return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
	
	
	//when bagId is not found in trackingCS Table and found in trackingDetails Table where LocationType is POST_OFFICE
	@Test
	public void findBagDetailsWithBagIdInDisableTestIfConditionWithLocationPostOffice() {

		String bagId = "123456789";
		
		List<TrackingCS> bagData = new ArrayList<TrackingCS>();
		
		Parcel objPrcl = new Parcel();
		objPrcl.setId(9L);
		objPrcl.setpStatus("OUT_FOR_DELIVERY");
		objPrcl.setTrackId("1111111111111");
		
		List<TrackingDetails> bagDisableData = new ArrayList<>();
		TrackingDetails td = new TrackingDetails();
		td.setId(1L);
		td.setStatus(Status.DISABLED);
		td.setLocationId((long) 1111);
		td.setLocationType(LocationType.POST_OFFICE);
		td.setObjParcel(objPrcl);
		td.setBagDesc("abcdef bag desc");
		td.setBagTitle("abcd bagTitle");
		td.setBagStatus(BagStatus.UNBAGGED);
		td.setUpdatedOn(new java.util.Date());
		td.setBagId(bagId);

		TrackingDetails td2 = new TrackingDetails();
		td2.setId(2L);
		td2.setStatus(Status.DISABLED);
		td2.setLocationId(9L);
		td2.setLocationType(LocationType.RMS);
		td2.setObjParcel(objPrcl);
		
		bagDisableData.add(td);
		bagDisableData.add(td2);
		
		List<TrackingDetails> list1 = bagDisableData
                .stream()
                .filter(distinctByKey(TrackingDetails::getObjParcel))
                .collect(Collectors.toList());
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		masterList.add(master);
		
		
		TrackingCS tc =new TrackingCS();
		tc.setId(9L);
		tc.setBagId("222222222");
		tc.setObjParcel(objPrcl);

		tc.setUpdatedOn(new java.util.Date());
		tc.setpStatus(PStatus.OUT_FOR_DELIVERY);
		tc.setStatus(Status.ACTIVE);
		
		Mockito.when(trackingCSRepo.findAllByBagId(bagId)).thenReturn(bagData);
		
		Mockito.when(trackingDetailsRepository.findAllByBagIdAndStatusOrderByUpdatedOnDesc(bagId, Status.DISABLED)).thenReturn(bagDisableData);
		
		Mockito.when(trackingCSRepo.findTrackingCSByObjParcel(list1.get(0).getObjParcel())).thenReturn(tc);
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(list1.get(0).getLocationId().intValue(), Status.ACTIVE)).thenReturn(masterList);
		Assert.assertEquals(1 , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).size());
		Assert.assertEquals(bagId , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagId());
		Assert.assertEquals(BagStatus.UNBAGGED , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagStatus());
		Assert.assertEquals(PStatus.OUT_FOR_DELIVERY , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getpStatus());
		Assert.assertEquals(objParcel , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getObjParcelVo());
		Assert.assertEquals(LocationType.POST_OFFICE , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getLocationType());
		
		
	}

	//when bagId is found in trackingCS table and  where LocationType is RMS
	@Test
	public void findBagDetailsWithBagIdInActiceTestIfConditionWithLocationRMS() {

		String bagId = "123456789";
		
		List<TrackingCS> bagData = new ArrayList<TrackingCS>();
		
		TrackingCS tc = new TrackingCS();
		tc.setId(1L);
		tc.setLocationId(8L);
		tc.setLocationType(LocationType.RMS);
		tc.setObjParcel(objParcel);
		tc.setBagDesc("abcdef bag desc");
		tc.setBagTitle("abcd bagTitle");
		tc.setBagStatus(BagStatus.IN);
		tc.setpStatus(PStatus.IN);
		tc.setUpdatedOn(new java.util.Date());
		tc.setBagId(bagId);
		bagData.add(tc);
		
		
		Mockito.when(trackingCSRepo.findAllByBagId(bagId)).thenReturn(bagData);
		
		Mockito.when(rmsRepository.findById(bagData.get(0).getLocationId())).thenReturn(Optional.of(rmsTable));
		
		Assert.assertEquals(1 , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).size());
		Assert.assertEquals(bagId , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagId());
		Assert.assertEquals(objParcel , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getObjParcelVo());
		Assert.assertEquals(BagStatus.IN , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagStatus());
		Assert.assertEquals(LocationType.RMS , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getLocationType());
		
	}
	
	//when bagId is found in trackingCS table and  where LocationType is POST_OFFICE
	@Test
	public void findBagDetailsWithBagIdInActiceTestIfConditionWithLocationPostOffice() {

		String bagId = "123456789";
		
		List<TrackingCS> bagData = new ArrayList<TrackingCS>();
		
		TrackingCS tc = new TrackingCS();
		tc.setId(1L);
		tc.setLocationId((long) 1111);
		tc.setLocationType(LocationType.POST_OFFICE);
		tc.setObjParcel(objParcel);
		tc.setBagDesc("abcdef bag desc");
		tc.setBagTitle("abcd bagTitle");
		tc.setBagStatus(BagStatus.IN);
		tc.setpStatus(PStatus.IN);
		tc.setUpdatedOn(new java.util.Date());
		tc.setBagId(bagId);
		bagData.add(tc);
		
		List<MasterAddress> masterList = new ArrayList<MasterAddress>();
		
		masterList.add(master);
		
		Mockito.when(trackingCSRepo.findAllByBagId(bagId)).thenReturn(bagData);
		
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(bagData.get(0).getLocationId().intValue(), Status.ACTIVE)).thenReturn(masterList);
		
		Assert.assertEquals(1 , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).size());
		Assert.assertEquals(bagId , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagId());
		Assert.assertEquals(objParcel, parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getObjParcelVo());
		Assert.assertEquals(BagStatus.IN, parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getBagStatus());
		Assert.assertEquals(LocationType.POST_OFFICE , parcelServiceImpl.findBagDetailsWithBagId(bagId, userDummy).get(0).getLocationType());
		
		
	}
	
	//Last Five Transaction For Admin Login User
	@Test
	public void fetchRecentTransactionOfBagForRoleAdmin() {
		
			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2020-09-23 10:10:10.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-09-24 10:05:10.0");
			
			User user = new User();
			Role role = new Role();
			role.setName("ROLE_ADMIN");
			user.setRole(role);
			
			List<TrackingCS> tcList = new ArrayList<TrackingCS>();
			
			TrackingCS tc = new TrackingCS();
			
			tc.setId(1L);
			tc.setBagId("123456789");
			tc.setBagTitle("abcd bagTitle");
			tc.setBagDesc("abcdef bag desc");
			tc.setUpdatedOn(timestamp);
			tc.setLocationType(LocationType.RMS);
			
			TrackingCS tc1 = new TrackingCS();
			tc1.setId(2L);
			tc1.setBagId("555555555");
			tc1.setBagTitle("second bagTitle");
			tc1.setBagDesc("second bag desc");
			tc1.setUpdatedOn(timestamp1);
			tc1.setLocationType(LocationType.POST_OFFICE);
			
			tcList.add(tc);
			tcList.add(tc1);
			
			Mockito.when(trackingCSRepo.findDistinctBaGData(PageRequest.of(0, 5))).thenReturn(tcList);
			
			Assert.assertEquals(2 , parcelServiceImpl.fetchRecentTransactionOfBag(user).size());
			Assert.assertEquals("555555555" , parcelServiceImpl.fetchRecentTransactionOfBag(user).get(0).getBagId());//due to sorting tc1 is placed on 0 index
			Assert.assertEquals("123456789" , parcelServiceImpl.fetchRecentTransactionOfBag(user).get(1).getBagId());
			
			
	}
	
	
	//All Recent Transaction of Login PO_USER
	@Test
	public void fetchRecentTransactionOfBagForRolePO_User() {
		
			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2020-09-23 10:10:10.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-09-24 10:05:10.0");
		
			User user = new User();
			Role role = new Role();
			role.setName("ROLE_PO_USER");
			user.setRole(role);
			user.setPostalCode(1111);
			
			List<TrackingCS> tcList = new ArrayList<TrackingCS>();
			
			TrackingCS tc = new TrackingCS();
			
			tc.setId(1L);
			tc.setBagId("123456789");
			tc.setBagTitle("abcd bagTitle");
			tc.setBagDesc("abcdef bag desc");
			tc.setUpdatedOn(timestamp);
			tc.setBagStatus(BagStatus.IN);
			tc.setLocationId((long) 1111);
			tc.setLocationType(LocationType.POST_OFFICE);
			
			TrackingCS tc1 = new TrackingCS();
			tc1.setId(2L);
			tc1.setBagId("555555555");
			tc1.setBagTitle("second bagTitle");
			tc1.setBagDesc("second bag desc");
			tc1.setUpdatedOn(timestamp1);
			tc1.setBagStatus(BagStatus.IN);
			tc1.setLocationId((long) 1111);
			tc1.setLocationType(LocationType.POST_OFFICE);
			
			tcList.add(tc);
			tcList.add(tc1);
			
			Mockito.when(trackingCSRepo.findDistinctPostBagData(BagStatus.IN.toString(),BagStatus.CREATED.toString(), (long) 1111)).thenReturn(tcList);
			Assert.assertEquals(2 , parcelServiceImpl.fetchRecentTransactionOfBag(user).size());
			Assert.assertEquals("555555555" , parcelServiceImpl.fetchRecentTransactionOfBag(user).get(0).getBagId());//due to sorting tc1 is placed on 0 index
			Assert.assertEquals("123456789" , parcelServiceImpl.fetchRecentTransactionOfBag(user).get(1).getBagId());
			Assert.assertEquals(LocationType.POST_OFFICE , parcelServiceImpl.fetchRecentTransactionOfBag(user).get(0).getLocationType());
	}
	
	//All Recent Transaction of RMS_USER
	@Test
	public void fetchRecentTransactionOfBagForRoleRmsUser() {
		
			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2020-09-23 10:10:10.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-09-24 10:05:10.0");
			
			User user = new User();
			Role role = new Role();
			role.setName("ROLE_RMS_USER");
			user.setRole(role);
			user.setRmsId(1L);
			
			List<TrackingCS> tcList = new ArrayList<TrackingCS>();
			
			TrackingCS tc = new TrackingCS();
			
			tc.setId(1L);
			tc.setBagId("123456789");
			tc.setBagTitle("abcd bagTitle");
			tc.setBagDesc("abcdef bag desc");
			tc.setUpdatedOn(timestamp);
			tc.setBagStatus(BagStatus.IN);
			tc.setLocationId(1L);
			tc.setLocationType(LocationType.RMS);
			
			TrackingCS tc1 = new TrackingCS();
			tc1.setId(2L);
			tc1.setBagId("555555555");
			tc1.setBagTitle("second bagTitle");
			tc1.setBagDesc("second bag desc");
			tc1.setUpdatedOn(timestamp1);
			tc1.setBagStatus(BagStatus.IN);
			tc1.setLocationId(1L);
			tc1.setLocationType(LocationType.RMS);
			
			tcList.add(tc);
			tcList.add(tc1);

			Mockito.when(trackingCSRepo.findDistinctRMSBagData(BagStatus.IN.toString(),BagStatus.CREATED.toString(), 1L)).thenReturn(tcList);
			Assert.assertEquals(2 , parcelServiceImpl.fetchRecentTransactionOfBag(user).size());
			
			Assert.assertEquals("555555555", parcelServiceImpl.fetchRecentTransactionOfBag(user).get(0).getBagId());//due to sorting tc1 is placed on 0 index
			Assert.assertEquals("123456789", parcelServiceImpl.fetchRecentTransactionOfBag(user).get(1).getBagId());
			Assert.assertEquals(LocationType.RMS, parcelServiceImpl.fetchRecentTransactionOfBag(user).get(0).getLocationType());
			
	}
	
	//If no login user(neither RMS user nor po_user nor Admin user)
	@Test
	public void fetchRecentTransactionOfBagForNOAnyUser() {
		
			User user = new User();
			Role role = new Role();
			role.setName("");
			user.setRole(role);
			
			Assert.assertEquals(null , parcelServiceImpl.fetchRecentTransactionOfBag(user));
	}
	
}

