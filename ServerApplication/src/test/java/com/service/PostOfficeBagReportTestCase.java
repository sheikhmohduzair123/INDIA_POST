package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Role;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.impl.TrackingServiceImpl;
import com.vo.MasterAddressVo;
import com.vo.RmsTableVo;
import com.vo.TrackingVo;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class PostOfficeBagReportTestCase {

	@InjectMocks
	private TrackingServiceImpl trackingServiceImp;
	
	@Mock
	SUserRepository userRepository;

	@Mock
	TrackingDetailsRepository trackingDetailsRepository;
	
	TrackingDetails trackingDetails;
	
	TrackingVo 	objVo;
	
	TrackingVo objVo2;
	
	List<TrackingVo> trackingListVO;
	
    java.sql.Timestamp timestamp1;
	java.sql.Timestamp timestamp2;
	 
	List<User>userList;
	
	  User user2; 
	  User user;
	  TrackingDetails trackingDetailsObj2;
	  
	  SimpleDateFormat simpleDateFormat;
	  String dat;

	  java.util.Date fdate;
	  java.util.Date uptodate;
	  LocalDate today;
	  LocalDateTime startDateTime;
	  ZonedDateTime zdt;
	  Date tdate;
	  
	  java.sql.Date sqlfromDate;
	  java.sql.Date sqltoDate;
	  Timestamp currentTs1;
	  Timestamp currentTs2;
	  
	 List<TrackingDetails> trackingDetailsList; 
	 
	 @Before
	 public void init() throws ParseException
	 {
		 trackingListVO = new ArrayList<TrackingVo>();
		  timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 11:15:13.0"); 
		  timestamp2 = java.sql.Timestamp.valueOf("2012-05-03 11:25:17.0"); 
		  userList = new ArrayList<User>();
		  
		  trackingDetailsList = new ArrayList<TrackingDetails>();
		  trackingDetails= new TrackingDetails();
		  trackingDetailsObj2= new TrackingDetails();
		  
		  user2=new User(); 
		  user=new User();
		  
		  objVo = new TrackingVo();
		  objVo2 = new TrackingVo();
		  
		  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  dat = simpleDateFormat.format(new Date());

		  fdate = new SimpleDateFormat("yyyy-MM-dd").parse(dat);
		  uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(dat);
		  today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		  startDateTime = today.atTime(23, 59, 59);
		  zdt = startDateTime.atZone(ZoneId.systemDefault());
		  tdate = Date.from(zdt.toInstant());

		  sqlfromDate = new java.sql.Date(fdate.getTime());
		  sqltoDate = new java.sql.Date(tdate.getTime());
		  currentTs1 = new Timestamp(sqlfromDate.getTime());
		  currentTs2 = new Timestamp(sqltoDate.getTime());
		 
	 }	
	  @Test
	  public void datewiseBagsReportForPoUserTestCaseWhenBagStatusIsIN() {
	  
	  Role role=new Role(); 
	  role.setName("ROLE_PO_USER");
	  
	  user.setId(1); user.setPostalCode(1100); user.setName("ABC1");
	  user.setStatus(Status.ACTIVE); user.setRole(role);
	  
	  user2.setId(2); user2.setPostalCode(1100); user2.setName("ABC2");
	  user2.setStatus(Status.ACTIVE); user.setRole(role);
	  
	  userList.add(user); userList.add(user2);
	  
	  trackingDetails.setId(1l);
	  trackingDetails.setBagStatus(BagStatus.IN);
	  trackingDetails.setUpdatedOn(timestamp1);
	  trackingDetails.setBagId("1000100002"); 
	  trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	 
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.IN);
	  trackingDetailsObj2.setUpdatedOn(timestamp2);
	  trackingDetailsObj2.setBagId("1000100004");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	  
	  Mockito.when(userRepository.findByPostalCode(1100)).thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.IN,
	  timestamp1, timestamp2)).thenReturn(trackingDetailsList);
	  
	  assertEquals(2,trackingServiceImp.datewiseBagsReportForPoUser(1100,BagStatus.IN, timestamp1, timestamp2).size()); 
		 
	  Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(1100,BagStatus.IN, timestamp1, timestamp2);
	  
	  List<TrackingVo> receiveObjKey04 = mapData2.get("1000100004");    
	  List<TrackingVo> receiveObjKey02 = mapData2.get("1000100002");
	  
	  Assert.assertNotNull("Received Object is not null for Tracking key", receiveObjKey04.get(0));
	  
	  Assert.assertNotNull("Received List is not null for Tracking key", receiveObjKey02.get(0));

	  Assert.assertEquals(trackingDetailsList.get(1).getBagId(), receiveObjKey04.get(0).getBagId());
	  Assert.assertEquals(trackingDetailsList.get(1).getBagStatus(), receiveObjKey04.get(0).getBagStatus());
	  
	  Assert.assertEquals(trackingDetailsList.get(0).getBagId(), receiveObjKey02.get(0).getBagId());
	  Assert.assertEquals(trackingDetailsList.get(0).getBagStatus(), receiveObjKey02.get(0).getBagStatus());
	  }
	 
	
	@Test
	public void datewiseBagsReportForPoUserTestCaseWhenBagStatusIsOUT() {		
		Role role=new Role();
		role.setName("ROLE_RMS_USER");
		
		user.setId(1);
		user.setPostalCode(1212);
		user.setName("XYZ1");
		user.setStatus(Status.ACTIVE);
		user.setRole(role);
		
		user2.setId(2);
		user2.setPostalCode(1212);
		user2.setName("XYZ2");
		user2.setStatus(Status.ACTIVE);	
		user.setRole(role);
		
		userList.add(user);
		userList.add(user2);
	
		trackingDetails.setId(1l);
		trackingDetails.setBagStatus(BagStatus.OUT);
		trackingDetails.setUpdatedOn(timestamp1);
		trackingDetails.setBagId("1000100002");
		trackingDetails.setUpdatedBy(user);
		trackingDetailsList.add(trackingDetails);
		
		trackingDetailsObj2.setId(2l);
		trackingDetailsObj2.setBagStatus(BagStatus.OUT);
		trackingDetailsObj2.setUpdatedOn(timestamp2);
		trackingDetailsObj2.setBagId("1000100004");	
		trackingDetailsObj2.setUpdatedBy(user2);
		trackingDetailsList.add(trackingDetailsObj2);	
		
		Mockito.when(userRepository.findByPostalCode(1212)).thenReturn(userList);

        Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.OUT, timestamp1, timestamp2)).thenReturn(trackingDetailsList);
        
        Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(1212,BagStatus.OUT, timestamp1, timestamp2);
  	  
        List<TrackingVo> receiveObjKey04 = mapData2.get("1000100004");    
  	  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100002");

  	  	Assert.assertNotNull("Received Object is not null for Tracking key", receiveObjKey04.get(0));
  	 
  	  	Assert.assertNotNull("Received List is not null for Tracking key", receiveObjKey02.get(0));

  	  	Assert.assertEquals(trackingDetailsList.get(1).getBagId(), receiveObjKey04.get(0).getBagId());
		Assert.assertEquals(trackingDetailsList.get(1).getBagStatus(), receiveObjKey04.get(0).getBagStatus());
		  
		Assert.assertEquals(trackingDetailsList.get(0).getBagId(), receiveObjKey02.get(0).getBagId());
		Assert.assertEquals(trackingDetailsList.get(0).getBagStatus(), receiveObjKey02.get(0).getBagStatus());
	}

	
	  @Test
	  public void BagsReportForPoUserTestCaseWhenOUT_DataNotExists() {
	  
	  user.setId(1); 
	  user.setPostalCode(3232);
	  user.setName("ABC3");
	  user.setStatus(Status.ACTIVE);
	  
	  user2.setId(2);
	  user2.setPostalCode(3232);
	  user2.setName("ABC4");
	  user2.setStatus(Status.ACTIVE);
	  
	  userList.add(user);
	  userList.add(user2);
	  
	  trackingDetails.setId(1l);
	  trackingDetails.setBagStatus(BagStatus.IN);
	  trackingDetails.setUpdatedOn(timestamp1);
	  trackingDetails.setBagId("1000100018"); trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.IN);
	  trackingDetailsObj2.setUpdatedOn(timestamp2);
	  trackingDetailsObj2.setBagId("1000100019");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	  
	  Mockito.when(userRepository.findByPostalCode(3232)).
	  thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.IN,
	  timestamp1, timestamp2)).thenReturn(trackingDetailsList);
	  
      Map<Object, List<TrackingVo>> mapData =trackingListVO.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
	  
	  Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(3232,BagStatus.OUT, timestamp1, timestamp2);
  	  
	  Assert.assertNotSame("Provided MapData and Received MapData Both are Not Same ",mapData,mapData2);
		
	  assertEquals(0,trackingServiceImp.datewiseBagsReportForPoUser(3232,BagStatus.OUT, timestamp1, timestamp2).size());
	  
        List<TrackingVo> receiveObjKey04 = mapData2.get("1000100019");    
	  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100018");
		
	  	Assert.assertNull("Received Object is null for 1000100019 key", receiveObjKey04);
	 
	  	Assert.assertNull("Received List is null for 1000100018 key", receiveObjKey02);
	  
	  assertEquals(0,trackingServiceImp.datewiseBagsReportForPoUser(3232,BagStatus.OUT, timestamp1, timestamp2).size());
	  assertEquals(true,trackingServiceImp.datewiseBagsReportForPoUser(3232,BagStatus.OUT, timestamp1, timestamp2).isEmpty());

	  
	  }
	  
	  @Test
	  public void BagsReportForPoUserTestCaseWhenIN_DataNotExists() {
	  
	  user.setId(1);
	  user.setPostalCode(0000);
	  user.setName("XYZ3");
	  user.setStatus(Status.ACTIVE);
	  
	  user2.setId(2);
	  user2.setPostalCode(0000); 
	  user2.setName("XYZ4");
	  user2.setStatus(Status.ACTIVE);
	  
	  userList.add(user); 
	  userList.add(user2);
	  
	  trackingDetails.setId(1l); trackingDetails.setBagStatus(BagStatus.OUT);
	  trackingDetails.setUpdatedOn(timestamp1);
	  trackingDetails.setBagId("1000100011"); trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
	  trackingDetailsObj2.setUpdatedOn(timestamp2);
	  trackingDetailsObj2.setBagId("1000100016");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	  
	  Mockito.when(userRepository.findByPostalCode(0000)).
	  thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.
	  findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.OUT,timestamp1, timestamp2)).thenReturn(trackingDetailsList);
	  
	  Map<Object, List<TrackingVo>> mapData =trackingListVO.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
	  
	  Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.IN, timestamp1, timestamp2);
  	  
	  Assert.assertNotSame("Provided MapData and Received MapData Both are Not Same ",mapData,mapData2);
		
	  assertEquals(0,trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.IN, timestamp1, timestamp2).size());
	  
        List<TrackingVo> receiveObjKey04 = mapData2.get("1000100016");    
	  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100011");
		
	  	Assert.assertNull("Received Object is null for 1000100016 key", receiveObjKey04);
	 
	  	Assert.assertNull("Received List is null for 1000100011 key", receiveObjKey02);
	  
	  }
	  
	  @Test
	  public void BagsReportForPoUserTestCaseWhenPostalCodeNotExists() {
	  
	  user.setId(1);
	  user.setPostalCode(0000); 
	  user.setName("XYZ3");
	  user.setStatus(Status.ACTIVE);
	  
	  user2.setId(2);
	  user2.setPostalCode(0000); 
	  user2.setName("XYZ4");
	  user2.setStatus(Status.ACTIVE);
	  
	  userList.add(user);
	  userList.add(user2);
	  
	  trackingDetails.setId(1l); trackingDetails.setBagStatus(BagStatus.OUT);
	  trackingDetails.setUpdatedOn(timestamp1);
	  trackingDetails.setBagId("1000100011"); trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
	  trackingDetailsObj2.setUpdatedOn(timestamp2);
	  trackingDetailsObj2.setBagId("1000100016");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	   
	  Mockito.when(userRepository.findByPostalCode(0000)).
	  thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.
	  findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.OUT,
	  timestamp1, timestamp2)).thenReturn(trackingDetailsList);
	  
	  Map<Object, List<TrackingVo>> mapData =trackingListVO.stream().collect(Collectors.groupingBy(TrackingVo::
	  getBagId));
	  
	  assertEquals(true,trackingServiceImp.datewiseBagsReportForPoUser(1212,BagStatus.OUT, timestamp1, timestamp2).isEmpty());
	  
	  Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(1212,BagStatus.OUT, timestamp1, timestamp2);
  	  
	  Assert.assertNotSame("Provided MapData and Received MapData Both are Not Same ",mapData,mapData2);
	
	    List<TrackingVo> receiveObjKey04 = mapData2.get("1000100016");    
	  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100011");
		
	  	Assert.assertNull("Received Object is null for 1000100016 key", receiveObjKey04);
	 
	  	Assert.assertNull("Received List is null for 1000100011 key", receiveObjKey02);
	
	  }
	  
	  
	  @Test
	  public void BagsReportForPoUserTestCaseWhenCurrentDate() {
	  
	  user.setId(1);
	  user.setPostalCode(0000); 
	  user.setName("XYZ3");
	  user.setStatus(Status.ACTIVE);
	  
	  user2.setId(2); 
	  user2.setPostalCode(0000);
	  user2.setName("XYZ4");
	  user2.setStatus(Status.ACTIVE);
	  
	  userList.add(user); 
	  userList.add(user2);
	  
	  trackingDetails.setId(1l);
	  trackingDetails.setBagStatus(BagStatus.OUT);
	  trackingDetails.setUpdatedOn(new Date());
	  trackingDetails.setBagId("1000100011"); 
	  trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
	  trackingDetailsObj2.setUpdatedOn(new Date());
	  trackingDetailsObj2.setBagId("1000100016");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	  
	  Mockito.when(userRepository.findByPostalCode(0000)).
	  thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.OUT,
			  currentTs1, currentTs2)).thenReturn(trackingDetailsList);
	
	  Map<Object, List<TrackingVo>> mapData = trackingListVO.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
		  
	  assertNotEquals(mapData,trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.OUT, currentTs1, currentTs2));
		  
	  Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.OUT, currentTs1, currentTs2);
	  	  
	  Assert.assertNotSame("Provided MapData and Received MapData Both are Not Same ",mapData,mapData2);
		
		    List<TrackingVo> receiveObjKey04 = mapData2.get("1000100016");    
		  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100011");
			
		  	Assert.assertNotNull("Received Object is not null for 1000100016 key", receiveObjKey04);
		  	Assert.assertEquals(trackingDetailsList.get(1).getBagId(), receiveObjKey04.get(0).getBagId());
		  	Assert.assertEquals(trackingDetailsList.get(1).getUpdatedOn(), receiveObjKey04.get(0).getUpdatedOn());
		  	
		  	Assert.assertNotNull("Received List is not null for 1000100011 key", receiveObjKey02);
		  	Assert.assertEquals(trackingDetailsList.get(0).getBagId(), receiveObjKey02.get(0).getBagId());
		  	Assert.assertEquals(trackingDetailsList.get(0).getUpdatedOn(), receiveObjKey02.get(0).getUpdatedOn());
		  	
	  assertEquals(2,trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.OUT, currentTs1, currentTs2).size());
	  
	  }
	  
	  @Test
	  public void BagsReportForPoUserTestCaseWhenSearchByCurrentDateNoDataAvailable() {
	  
	  user.setId(1);
	  user.setPostalCode(0000); 
	  user.setName("XYZ3");
	  user.setStatus(Status.ACTIVE);
	  
	  user2.setId(2); 
	  user2.setPostalCode(0000);
	  user2.setName("XYZ4");
	  user2.setStatus(Status.ACTIVE);
	  
	  userList.add(user); 
	  userList.add(user2);
	  
	  trackingDetails.setId(1l);
	  trackingDetails.setBagStatus(BagStatus.OUT);
	  trackingDetails.setUpdatedOn(timestamp1);
	  trackingDetails.setBagId("1000100011"); 
	  trackingDetails.setUpdatedBy(user);
	  trackingDetailsList.add(trackingDetails);
	  
	  trackingDetailsObj2.setId(2l);
	  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
	  trackingDetailsObj2.setUpdatedOn(timestamp2);
	  trackingDetailsObj2.setBagId("1000100016");
	  trackingDetailsObj2.setUpdatedBy(user2);
	  trackingDetailsList.add(trackingDetailsObj2);
	  
	  Mockito.when(userRepository.findByPostalCode(0000)).
	  thenReturn(userList);
	  
	  Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(userList,BagStatus.OUT,
			  timestamp1, timestamp2)).thenReturn(trackingDetailsList);
	  
	  Map<Object, List<TrackingVo>> mapData = trackingListVO.stream().collect(Collectors.groupingBy(TrackingVo::
	  getBagId));
	  
     Map<Object, List<TrackingVo>> mapData2 = trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.OUT, currentTs1, currentTs2);
  	  
	 Assert.assertNotSame("Provided MapData and Received MapData Both are Not Same ",mapData,mapData2);
	
	    List<TrackingVo> receiveObjKey04 = mapData2.get("1000100016");    
	  	List<TrackingVo> receiveObjKey02 = mapData2.get("1000100011");
		
	  	Assert.assertNull("Received Object is null for 1000100016 key", receiveObjKey04);
	  	
	  	Assert.assertNull("Received List is null for 1000100011 key", receiveObjKey02);
	  	
	 assertEquals(0,trackingServiceImp.datewiseBagsReportForPoUser(0000,BagStatus.OUT, currentTs1, currentTs2).size());
	  
	  }
	  
}
