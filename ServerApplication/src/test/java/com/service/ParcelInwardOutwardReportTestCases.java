package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.repositories.RmsRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.impl.TrackingServiceImpl;
import com.vo.MasterAddressVo;
import com.vo.RmsTableVo;
import com.vo.TrackingVo;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ParcelInwardOutwardReportTestCases {

 @InjectMocks
 TrackingServiceImpl trackingServiceImp;

 @Mock
 SUserRepository userRepository;

 @Mock
 TrackingDetailsRepository trackingDetailsRepository;

 @Mock
 RoleRepository roleRepository;

 @Mock
 RmsRepository rmsRepository;

 @Mock
 MasterAddressRepository masterAddressRepository;

 User userPO;

 User userFD;

 User userRMS;

 User userAdmin;

 Parcel parcelObj;

 Parcel parcelObj2;

 TrackingDetails trackingDetails;
 
 TrackingDetails trackingDetailsObj2;
 
 Role role;

 Role rolePO;
 
 Role roleFD;

 Role roleRMS;

 Map < String, Object > response;

 List < TrackingDetails > trackingDetailsList;
 List < User > users;
 List < MasterAddress > masterList;
 List < RmsTable > rmsList;
 java.sql.Timestamp timestamp1;
 java.sql.Timestamp timestamp2;
 java.util.Date utilDate;
 Date myDate;
 Date myDate1;

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
 
 List<MasterAddressVo> masterAddressVoList;
 List<RmsTableVo> rmsTableVoList;
 
 List<TrackingVo> trackingVoList;
	
 TrackingVo trackingVo; 
 TrackingVo trackingVo2; 
 
 RmsTable rmsTable;

 MasterAddress masterAddress;
 
 RmsTableVo rmsTableVo;
 MasterAddressVo masterAddressVo;
 
 
 
 @Before
 public void init() throws ParseException {
  response = new HashMap < String, Object > ();

  trackingDetailsList = new ArrayList < TrackingDetails > ();

  users = new ArrayList < User > ();

  masterList = new ArrayList < MasterAddress > ();

  rmsList = new ArrayList < RmsTable > ();
  
  masterAddressVoList = new ArrayList<MasterAddressVo>();
  rmsTableVoList = new ArrayList<RmsTableVo>();

  timestamp1 = java.sql.Timestamp.valueOf("2019-08-12 10:10:10.0");
  timestamp2 = java.sql.Timestamp.valueOf("2019-08-16 10:05:10.0");

  utilDate = new java.util.Date();

  myDate = new GregorianCalendar(2019, 8, 13).getTime();
  myDate1 = new GregorianCalendar(2019, 8, 14).getTime();

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

  parcelObj = new Parcel();
  parcelObj.setId(3l);
  
  parcelObj.setTrackId("0110001000001");
  parcelObj.setActWt(20.3f);
  parcelObj.setParcelContent("Laptops");


  parcelObj2 = new Parcel();
  parcelObj2.setId(5l);
 
  parcelObj2.setTrackId("0110001000002");
  parcelObj2.setActWt(15.5f);
  parcelObj2.setParcelContent("Mobiles");

  userAdmin = new User();
  role = new Role();
  role.setName("ROLE_ADMIN");
  userAdmin.setId(1);
  userAdmin.setPostalCode(0);
  userAdmin.setName("AdminUser");
  userAdmin.setStatus(Status.ACTIVE);
  userAdmin.setRmsId(null);
  userAdmin.setRole(role);
  users.add(userAdmin);

  userPO = new User();
  rolePO = new Role();
  rolePO.setName("ROLE_PO_USER");
  userPO.setId(2);
  userPO.setPostalCode(1100);
  userPO.setName("PoUser");
  userPO.setStatus(Status.ACTIVE);
  userPO.setRmsId(null);
  userPO.setRole(rolePO);
  users.add(userPO);

  userFD = new User();
  roleFD = new Role();
  roleFD.setName("ROLE_FRONT_DESK_USER");
  userFD.setId(3);
  userFD.setPostalCode(1100);
  userFD.setName("FdUser");
  userFD.setStatus(Status.ACTIVE);
  userFD.setRmsId(null);
  userFD.setRole(roleFD);
  users.add(userFD);

  userRMS = new User();
  roleRMS = new Role();
  roleRMS.setName("ROLE_RMS_USER");
  userRMS.setId(4);
  userRMS.setPostalCode(0);
  userRMS.setName("RMS_User");
  userRMS.setStatus(Status.ACTIVE);
  userRMS.setEnabled(true);
  userRMS.setRmsId(5l);
  userRMS.setRole(roleRMS);
  users.add(userRMS);


  trackingDetails = new TrackingDetails();
  trackingDetails.setId(1l);
  trackingDetails.setObjParcel(parcelObj);

  trackingDetailsObj2 = new TrackingDetails();
  trackingDetailsObj2.setId(2l);
  trackingDetailsObj2.setObjParcel(parcelObj2);
  
  rmsTable= new RmsTable();
  rmsTable.setId(5l);
  rmsTable.setRmsName("RMS_Name1");
  
    masterAddress = new MasterAddress();
	masterAddress.setPostalCode(1100);
	masterAddress.setSubOffice("SubOfficeA");
	
	trackingVoList = new ArrayList<TrackingVo>();
	trackingVo = new TrackingVo();
	trackingVo2 = new TrackingVo();
	
	rmsTableVo = new RmsTableVo();
	masterAddressVo=new MasterAddressVo();
  
    trackingVoList = new ArrayList<TrackingVo>();
    
      
 }

 ///******Positive Test Cases On Parcel Inward/Outward Report For Admin User******/// 

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenPO_SelectedParcelStatusOUT() {

  parcelObj.setpStatus(PStatus.OUT.toString());
  parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetails.setUpdatedOn(myDate);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userPO);
  trackingDetails.setpStatus(PStatus.OUT);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());
  trackingDetails.setObjParcel(parcelObj);

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(myDate1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.OUT);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);
  
  trackingDetailsList.add(trackingDetailsObj2);
  
		  rmsList.add(rmsTable);
	      masterList.add(masterAddress);
	
  response.put("rms", rmsList);
  response.put("master", masterList);
  response.put("Tracking", trackingDetailsList);
  
  Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

  Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);
 
    Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT);   
  
       Object receiveObjTracking = response2.get("Tracking");    
	   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

       Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
       Assert.assertEquals(trackingDetailsList.size(), receiveListTracking.size());
       Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
       Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
       
       Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
       Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
       
       Object receiveObjRMS = response2.get("rms");
       
       List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
       
       Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
       Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
       Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());
 
       Object receiveObjMaster = response2.get("master");
       
       List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
       
       Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
       Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
       Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());
 
 }
 
 
 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenPO_SelectedParcelStatusINWithCurrentDate() {

	 parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());
	 
  trackingDetails.setUpdatedOn(new Date());
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userPO);
  trackingDetails.setpStatus(PStatus.IN);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(new Date());
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.IN);
  trackingDetailsObj2.setObjParcel(parcelObj2);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);


response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


  Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

  Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, currentTs1, currentTs2, users)).thenReturn(trackingDetailsList);
  
  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

 Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userAdmin, (long) 1100, null, PStatus.IN);   
 
 Object receiveObjTracking = response2.get("Tracking");    
 List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

 Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
 Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
 Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
 Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
 Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
 Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
 Assert.assertSame(trackingDetailsList.get(0).getUpdatedOn(),receiveListTracking.get(0).getUpdatedOn());
 Assert.assertSame(trackingDetailsList.get(1).getUpdatedOn(),receiveListTracking.get(1).getUpdatedOn());

 Object receiveObjRMS = response2.get("rms");
 
 List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
 
 Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
 Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
 Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

 Object receiveObjMaster = response2.get("master");
 
 List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
 
 Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
 Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
 Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());

 }


 ///*****Test Cases on Pracel_IN_OUT_Report For Admin User When RMS_Selected and ParcelStatus OUT*****///

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenRMS_SelectedParcelStatusOUT() {
	 parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetails.setUpdatedOn(myDate);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setpStatus(PStatus.OUT);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.RMS);
  trackingDetails.setLocationId(userRMS.getRmsId());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(myDate1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userRMS);
  trackingDetailsObj2.setpStatus(PStatus.OUT);
  trackingDetailsObj2.setLocationType(LocationType.POST_OFFICE);
  trackingDetailsObj2.setLocationId(userPO.getPostalCode());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


  Mockito.when(userRepository.findByRmsId(5l)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(0).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(1).getLocationId().intValue(),
   Status.ACTIVE)).thenReturn(masterList);

  Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long)0, 5l, PStatus.OUT);   
  
  Object receiveObjTracking = response2.get("Tracking");    
  List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

  Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
  Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
  Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
  Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
  Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
  Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
  
  Object receiveObjRMS = response2.get("rms");
  
  List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
  
  Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
  Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
  Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

  Object receiveObjMaster = response2.get("master");
  
  List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
  
  Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
  Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
  Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());

 }
 

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenRMS_SelectedParcelStatusINWithCurrentDate() {
	 parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());
  trackingDetails.setUpdatedOn(new Date());
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setpStatus(PStatus.IN);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.RMS);
  trackingDetails.setLocationId(userRMS.getRmsId());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(new Date());
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.IN);
  trackingDetailsObj2.setLocationType(LocationType.POST_OFFICE);
  trackingDetailsObj2.setLocationId(userPO.getPostalCode());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


  Mockito.when(userRepository.findByRmsId(5l)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, currentTs1, currentTs2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(0).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(1).getLocationId().intValue(),
   Status.ACTIVE)).thenReturn(masterList);
  Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userAdmin, (long)0, 5l, PStatus.IN);   
  
  Object receiveObjTracking = response2.get("Tracking");    
  List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

  Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
  Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
  Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
  Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
  Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
  Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
  Assert.assertSame(trackingDetailsList.get(0).getUpdatedOn(),receiveListTracking.get(0).getUpdatedOn());
  Assert.assertSame(trackingDetailsList.get(1).getUpdatedOn(),receiveListTracking.get(1).getUpdatedOn());
  
  
  Object receiveObjRMS = response2.get("rms");
  
  List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
  
  Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
  Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
  Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

  Object receiveObjMaster = response2.get("master");
  
  List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
  
  Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
  Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
  Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());

 }

  ///******Negative Test Cases On Parcel Inward/Outward Report For Admin User when PO selected ******/// 

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhen_No_IN_OR_OUT_Data() {
	
	 response.put("rms", rmsList);
	 response.put("master", masterList);
	 response.put("Tracking", trackingDetailsList);


  Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

  Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(5l, Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1100,
   Status.ACTIVE)).thenReturn(masterList);

  assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT));
  
  assertFalse(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("Tracking"));
  
  assertNotEquals(response.containsKey("rms"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("rms"));
  assertNotEquals(response.containsKey("master"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("master"));
 
 }

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenPO_SelectedNegativeNoOUT_Data() {

	 parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());
  trackingDetails.setUpdatedOn(timestamp1);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userPO);
  trackingDetails.setpStatus(PStatus.IN);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(timestamp1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.IN);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


  Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

  Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
   Status.ACTIVE)).thenReturn(masterList);

  assertFalse(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("Tracking"));
  
  assertNotEquals(response.containsKey("Tracking"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("Tracking"));
  assertNotEquals(response.containsKey("rms"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("rms"));
  assertNotEquals(response.containsKey("master"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT).containsKey("master"));
  assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.OUT));

 }
 
 @Test
 public void NegativePracel_IN_OUT_ReportForAdminUserWhenPO_SelectedIN_StatusWithSearchByCurrentDate_NoDataAvailable() {
	 parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetails.setBagStatus(BagStatus.OUT);
  trackingDetails.setUpdatedOn(timestamp1);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setpStatus(PStatus.OUT);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
  trackingDetailsObj2.setUpdatedOn(timestamp2);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userRMS);
  trackingDetailsObj2.setpStatus(PStatus.OUT);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
 Status.ACTIVE)).thenReturn(masterList);

assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userAdmin, (long) 1100, null, PStatus.IN));
assertEquals(true, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userAdmin, (long) 1100, null, PStatus.IN).isEmpty());

 
 }
 
///////////////*****Negative Test Cases on Pracel_IN_OUT_Report For Admin User When RMS_Selected****///////////
 
 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenRMS_SelectedNO_IN_OR_OUT_DATA() {
	 
  Mockito.when(userRepository.findByRmsId(5l)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(5l, Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1100,Status.ACTIVE)).thenReturn(masterList);

  assertEquals(response.isEmpty(), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin,(long) 0, 5l, PStatus.IN).isEmpty());
 
 }

 @Test
 public void getPracel_IN_OUT_ReportForAdminUserWhenRMS_SelectedNO_IN_DATA() {
	 parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetails.setUpdatedOn(timestamp1);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userPO);
  trackingDetails.setpStatus(PStatus.OUT);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(timestamp1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.OUT);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

response.put("rms", rmsList);
response.put("master", masterList);
response.put("Tracking", trackingDetailsList);


  Mockito.when(userRepository.findByRmsId(4l)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
   Status.ACTIVE)).thenReturn(masterList);

 assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin,(long) 0, 5l, PStatus.IN));
 
 assertNotEquals(response.containsKey("Tracking"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.IN).containsKey("Tracking"));
 assertNotEquals(response.containsKey("rms"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.IN).containsKey("rms"));
 assertNotEquals(response.containsKey("master"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin, (long) 1100, null, PStatus.IN).containsKey("master"));

 assertEquals(0, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin,(long) 0, 5l, PStatus.IN).size());
 assertTrue(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userAdmin,(long) 0, 5l, PStatus.IN).isEmpty());


 }

 @Test
 public void NegativePracel_IN_OUT_ReportForAdminUserWhenRMS_SelectedNO_OUT_DATAWithCurrentDate() {
	 parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());
  trackingDetails.setBagStatus(BagStatus.IN);
  trackingDetails.setUpdatedOn(new Date());
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userPO);
  trackingDetails.setpStatus(PStatus.IN);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setBagStatus(BagStatus.IN);
  trackingDetailsObj2.setUpdatedOn(new Date());
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userPO);
  trackingDetailsObj2.setpStatus(PStatus.IN);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

  response.put("rms", rmsList);
  response.put("master", masterList);
  response.put("Tracking", trackingDetailsList);

  Mockito.when(userRepository.findByRmsId(5l)).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),Status.ACTIVE)).thenReturn(masterList);

  assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS,(long) 0, 5l, PStatus.OUT));
 
  assertNotEquals(response.containsKey("Tracking"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long) 1100, null, PStatus.IN).containsKey("Tracking"));
  assertNotEquals(response.containsKey("rms"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long) 1100, null, PStatus.IN).containsKey("rms"));
  assertNotEquals(response.containsKey("master"), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long) 1100, null, PStatus.IN).containsKey("master"));

  assertEquals(0, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS,(long) 0, 5l, PStatus.OUT).size());
  assertEquals(true, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS,(long) 0, 5l, PStatus.OUT).isEmpty());

 }


  
 @Test 
 public void NegativePracel_IN_OUT_ReportForAdminUserWhenRMS_Selected_IN_Status_SearchByCurrentDateNoDataAvailable() {
	 parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetails.setBagStatus(BagStatus.OUT);
  trackingDetails.setUpdatedOn(myDate1);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setpStatus(PStatus.OUT);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setBagStatus(BagStatus.OUT);
  trackingDetailsObj2.setUpdatedOn(myDate);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userRMS);
  trackingDetailsObj2.setpStatus(PStatus.OUT);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

    rmsList.add(rmsTable);
    masterList.add(masterAddress);

    response.put("rms", rmsList);
    response.put("master", masterList);
    response.put("Tracking", trackingDetailsList);

Mockito.when(userRepository.findByRmsId(5L)).thenReturn(users);

Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
 Status.ACTIVE)).thenReturn(masterList);

assertEquals(true, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userAdmin,(long) 0, 5l, PStatus.IN).isEmpty());
  
 }

  ///******Positive Test Cases On Parcel Inward/Outward Report For PO User******///

  @Test
  public void getPracel_IN_OUT_ReportForPOUserWhenSelectParcelStatusOUT() {
   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(1100);
     parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
   trackingDetails.setUpdatedOn(myDate);
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userPO);
   trackingDetails.setpStatus(PStatus.OUT);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(myDate1);
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userPO);
   trackingDetailsObj2.setpStatus(PStatus.OUT);
   trackingDetailsObj2.setObjParcel(parcelObj2);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());

   trackingDetailsList.add(trackingDetailsObj2);

	   rmsList.add(rmsTable);
	   masterList.add(masterAddress);

	 response.put("rms", rmsList);
	 response.put("master", masterList);
	 response.put("Tracking", trackingVoList);

   Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

   Mockito.when(userRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(), rolePO)).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long)1100, null, PStatus.OUT);   
   
   Object receiveObjTracking = response2.get("Tracking");    
   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

   Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
   Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
   Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
   Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
   Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
   Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
     
   Object receiveObjRMS = response2.get("rms");
   
   List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
   
   Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
   Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

   Object receiveObjMaster = response2.get("master");
   
   List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
   
   Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
   Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());
  
  }


  @Test
  public void getPracel_IN_OUT_ReportForPOUserWhenSelectParcelStatusIN_WithCurrentDate() {
   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(1100);
     parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());
   
   trackingDetails.setUpdatedOn(new Date());
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userPO);
   trackingDetails.setpStatus(PStatus.IN);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(new Date());
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userPO);
   trackingDetailsObj2.setpStatus(PStatus.IN);
   trackingDetailsObj2.setObjParcel(parcelObj2);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());

   trackingDetailsList.add(trackingDetailsObj2);

   rmsList.add(rmsTable);
   masterList.add(masterAddress);

 response.put("rms", rmsList);
 response.put("master", masterList);
 response.put("Tracking", trackingDetailsList);

   Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

   Mockito.when(userRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(), rolePO)).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, currentTs1, currentTs2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userPO, (long)1100, null, PStatus.IN);   
   
   Object receiveObjTracking = response2.get("Tracking");    
   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

   Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
   Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
   Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
   Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
   Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
   Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
   Assert.assertSame(trackingDetailsList.get(0).getUpdatedOn(),receiveListTracking.get(0).getUpdatedOn());
   Assert.assertSame(trackingDetailsList.get(1).getUpdatedOn(),receiveListTracking.get(1).getUpdatedOn());
   
   Object receiveObjRMS = response2.get("rms");
   
   List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
   
   Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
   Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

   Object receiveObjMaster = response2.get("master");
   
   List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
   
   Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
   Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice()); 
  
  }

 
  ///******Negative Test Cases On Parcel Inward/Outward Report For PO User******///
 
  @Test
  public void getPracel_IN_OUT_ReportForPOUserWhenNO_Data_Negative() {
   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(1100);
 
	response.put("rms", rmsList);
	response.put("master", masterList);
	response.put("Tracking", trackingDetailsList);

   Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

   Mockito.when(userRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(), rolePO)).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(5l, Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1100,
    Status.ACTIVE)).thenReturn(masterList);

   assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.OUT));

   assertTrue(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.OUT).keySet().isEmpty());
  }

 
  @Test
  public void getPracel_IN_OUT_ReportForPOUserWhenSelectParcelStatusOUT_Negative() {
   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(1100);
        parcelObj.setpStatus(PStatus.IN.toString());
	    parcelObj2.setpStatus(PStatus.IN.toString());
   trackingDetails.setUpdatedOn(myDate);
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userPO);
   trackingDetails.setpStatus(PStatus.IN);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setBagStatus(BagStatus.IN);
   trackingDetailsObj2.setUpdatedOn(myDate1);
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userPO);
   trackingDetailsObj2.setpStatus(PStatus.IN);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());

   trackingDetailsList.add(trackingDetailsObj2);

	   rmsList.add(rmsTable);
	   masterList.add(masterAddress);

	 response.put("rms", rmsList);
	 response.put("master", masterList);
	 response.put("Tracking", trackingDetailsList);


   Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

   Mockito.when(userRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(), rolePO)).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.OUT));

   assertEquals(0, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.OUT).size());
   assertEquals(true, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.OUT).isEmpty());
  }

 ////*******Negative Test Cases For PO user*******////
  
  @Test
  public void NegativePracel_IN_OUT_ReportForPOUserWhenSelectParcelStatusIN() {
   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(1100);
   parcelObj.setpStatus(PStatus.OUT.toString());
   parcelObj2.setpStatus(PStatus.OUT.toString());
   trackingDetails.setUpdatedOn(timestamp1);
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userPO);
   trackingDetails.setpStatus(PStatus.OUT);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(timestamp2);
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userPO);
   trackingDetailsObj2.setpStatus(PStatus.OUT);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());

   trackingDetailsList.add(trackingDetailsObj2);
	
	   rmsList.add(rmsTable);
	   masterList.add(masterAddress);
	
	 response.put("rms", rmsTableVoList);
	 response.put("master", masterAddressVoList);
	 response.put("Tracking", trackingVoList);
	

   Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

   Mockito.when(userRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(), rolePO)).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.IN));

   assertEquals(0, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.IN).size());
  
   assertTrue(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userPO, (long) 1100, null, PStatus.IN).isEmpty());
  }

  
  @Test 
  public void NegativePracel_IN_OUT_ReportForPOUserSelectOUT_SattusWithSearchByCurrentDateNoDataAvailable() {

	  parcelObj.setpStatus(PStatus.IN.toString());
	  parcelObj2.setpStatus(PStatus.IN.toString());
   trackingDetails.setUpdatedOn(myDate1);
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userRMS);
   trackingDetails.setpStatus(PStatus.IN);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(myDate);
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userRMS);
   trackingDetailsObj2.setpStatus(PStatus.IN);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());
   trackingDetailsObj2.setObjParcel(parcelObj2);

   trackingDetailsList.add(trackingDetailsObj2);

	   rmsList.add(rmsTable);
	   masterList.add(masterAddress);
	
	 response.put("rms", rmsTableVoList);
	 response.put("master", masterAddressVoList);
	 response.put("Tracking", trackingVoList);


 Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

 Mockito.when(userRepository.findAllByPostalCodeAndRole(1100l, rolePO)).thenReturn(users);

 Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

 Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

 Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
  Status.ACTIVE)).thenReturn(masterList);

 assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userPO, (long) 1100, null, PStatus.OUT));
 
 assertEquals(!response.isEmpty(), trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userPO, (long) 1100, null, PStatus.OUT).isEmpty());

 }

  ////******Test Cases for RMS User****////

   @Test
  public void getPracel_IN_OUT_ReportForRMSUserWhenParcelSelected_OUT() {

   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(0);
   loginedUser.setRmsId(5L);;
   parcelObj.setpStatus(PStatus.OUT.toString());
	 parcelObj2.setpStatus(PStatus.OUT.toString());
   trackingDetails.setUpdatedOn(myDate);
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userRMS);
   trackingDetails.setpStatus(PStatus.OUT);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(myDate1);
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userRMS);
   trackingDetailsObj2.setpStatus(PStatus.OUT);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());
   trackingDetailsObj2.setObjParcel(parcelObj2);

   trackingDetailsList.add(trackingDetailsObj2);

	   rmsList.add(rmsTable);
	   masterList.add(masterAddress);

	 response.put("rms", rmsList);
	 response.put("master", masterList);
	 response.put("Tracking", trackingDetailsList);

   Mockito.when(userRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   assertEquals(3, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userRMS,
    (long) 0, 5L, PStatus.OUT).size());

   Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userRMS, (long)0, 5l, PStatus.OUT);   
   
   Object receiveObjTracking = response2.get("Tracking");    
   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

   Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
   Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
   Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
   Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
   Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
   Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());

   Object receiveObjRMS = response2.get("rms");
   
   List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
   
   Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
   Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

   Object receiveObjMaster = response2.get("master");
   
   List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
   
   Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
   Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());  

  }

 
  @Test
  public void getPracel_IN_OUT_ReportForRMSUserWhenParcelSelected_IN_CurrentDate() {

   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setPostalCode(0);
   loginedUser.setRmsId(5L);;
   parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());

   trackingDetails.setUpdatedOn(new Date());
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userRMS);
   trackingDetails.setpStatus(PStatus.IN);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(new Date());
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userRMS);
   trackingDetailsObj2.setpStatus(PStatus.IN);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());
   trackingDetailsObj2.setObjParcel(parcelObj2);

   trackingDetailsList.add(trackingDetailsObj2);

      rmsList.add(rmsTable);
      masterList.add(masterAddress);

	 response.put("rms", rmsList);
	 response.put("master", masterList);
	 response.put("Tracking", trackingDetailsList);


   Mockito.when(userRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, currentTs1, currentTs2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);

   assertEquals(3, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS,
    (long) 0, 5L, PStatus.IN).size());

   Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long)0, 5l, PStatus.IN);   
   
   Object receiveObjTracking = response2.get("Tracking");    
   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

   Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
   Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
   Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
   Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
   Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
   Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
   Assert.assertSame(trackingDetailsList.get(0).getUpdatedOn(),receiveListTracking.get(0).getUpdatedOn());
   Assert.assertSame(trackingDetailsList.get(1).getUpdatedOn(),receiveListTracking.get(1).getUpdatedOn());

   Object receiveObjRMS = response2.get("rms");
   
   List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
   
   Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
   Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

   Object receiveObjMaster = response2.get("master");
   
   List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
   
   Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
   Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());


  }

  @Test
  public void getPracel_IN_OUT_ReportForRMSUserWhenParcelSelected_OUT_CurrentDate() {

   User loginedUser = new User();
   loginedUser.setId(10);
   loginedUser.setRmsId(5L);
   parcelObj.setpStatus(PStatus.OUT.toString());
   parcelObj2.setpStatus(PStatus.OUT.toString());

   trackingDetails.setUpdatedOn(new Date());
   trackingDetails.setBagId("1000100006");
   trackingDetails.setUpdatedBy(userRMS);
   trackingDetails.setpStatus(PStatus.OUT);
   trackingDetails.setObjParcel(parcelObj);
   trackingDetails.setLocationType(LocationType.POST_OFFICE);
   trackingDetails.setLocationId(userPO.getPostalCode());

   trackingDetailsList.add(trackingDetails);

   trackingDetailsObj2.setUpdatedOn(new Date());
   trackingDetailsObj2.setBagId("1000100007");
   trackingDetailsObj2.setUpdatedBy(userRMS);
   trackingDetailsObj2.setpStatus(PStatus.OUT);
   trackingDetailsObj2.setLocationType(LocationType.RMS);
   trackingDetailsObj2.setLocationId(userRMS.getRmsId());
   trackingDetailsObj2.setObjParcel(parcelObj2);

   trackingDetailsList.add(trackingDetailsObj2);

   rmsList.add(rmsTable);
   masterList.add(masterAddress);

 response.put("rms", rmsList);
 response.put("master", masterList);
 response.put("Tracking", trackingDetailsList);


   Mockito.when(userRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(users);

   Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, currentTs1, currentTs2, users)).thenReturn(trackingDetailsList);

   Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

   Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
    Status.ACTIVE)).thenReturn(masterList);
  
   assertEquals(3, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS,
    (long) 0, 5L, PStatus.OUT).size());

   Map<String, Object> response2 = trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long)0, 5l, PStatus.OUT);   
   
   Object receiveObjTracking = response2.get("Tracking");    
   List<TrackingVo> receiveListTracking = (List<TrackingVo>) receiveObjTracking;

   Assert.assertNotNull("Received List is not null for Tracking key", receiveListTracking);
   Assert.assertEquals("Size mismatch for lists for key '" , trackingDetailsList.size(), receiveListTracking.size());
   Assert.assertSame(trackingDetailsList.get(0).getBagId(),receiveListTracking.get(0).getBagId());
   Assert.assertSame(trackingDetailsList.get(1).getBagId(),receiveListTracking.get(1).getBagId());
   Assert.assertSame(trackingDetailsList.get(0).getpStatus(),receiveListTracking.get(0).getpStatus());
   Assert.assertSame(trackingDetailsList.get(1).getpStatus(),receiveListTracking.get(1).getpStatus());
   Assert.assertSame(trackingDetailsList.get(0).getUpdatedOn(),receiveListTracking.get(0).getUpdatedOn());
   Assert.assertSame(trackingDetailsList.get(1).getUpdatedOn(),receiveListTracking.get(1).getUpdatedOn());

   Object receiveObjRMS = response2.get("rms");
   
   List<RmsTableVo> rmsTableVoListReceive=(List<RmsTableVo>) receiveObjRMS;
   
   Assert.assertNotNull("Received List is not null for RMS key", rmsTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for RMS key " , rmsList.size(), rmsTableVoListReceive.size());
   Assert.assertSame("Check Provided RMS name or Received RMS name for RMS key ",rmsList.get(0).getRmsName(),rmsTableVoListReceive.get(0).getRmsName());

   Object receiveObjMaster = response2.get("master");
   
   List<MasterAddressVo> masterTableVoListReceive=(List<MasterAddressVo>) receiveObjMaster;
   
   Assert.assertNotNull("Received List is not null for Master key", masterTableVoListReceive);
   Assert.assertEquals("Check Provided List size or Received List size for Master key " , masterList.size(), masterTableVoListReceive.size());
   Assert.assertSame("Check Provided Sub-Office name or Received Sub-Office name for Master key ",masterList.get(0).getSubOffice(),masterTableVoListReceive.get(0).getSubOffice());

  }
 
  ////******Negative Test Cases for RMS User****////										

  @Test
 public void getPracel_IN_OUT_ReportForRMSUserWhenParcelSelected_OUT_Negative() {

  User loginedUser = new User();
  loginedUser.setId(10);
  loginedUser.setPostalCode(0);
  loginedUser.setRmsId(5L);;
  parcelObj.setpStatus(PStatus.IN.toString());
	 parcelObj2.setpStatus(PStatus.IN.toString());

  trackingDetails.setUpdatedOn(myDate);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setpStatus(PStatus.IN);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);

  trackingDetailsObj2.setUpdatedOn(myDate1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userRMS);
  trackingDetailsObj2.setpStatus(PStatus.IN);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

 response.put("rms", rmsList);
 response.put("master", masterList);
 response.put("Tracking", trackingDetailsList);

  Mockito.when(userRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(users);

  Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.IN, timestamp1, timestamp2, users)).thenReturn(trackingDetailsList);

  Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

  Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
   Status.ACTIVE)).thenReturn(masterList);

  assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userRMS,(long) 0, 5L, PStatus.OUT));
 
  assertEquals(0, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userRMS,(long) 0, 5L, PStatus.OUT).size());
  
  assertTrue(trackingServiceImp.getAlldailyDispatchReportByCurrentDate(timestamp1, timestamp2, userRMS,(long) 0, 5L, PStatus.OUT).isEmpty());

 }
 
 @Test 
 public void NegativePracel_IN_OUT_ReportForRMSUserWhenStatusINAndSearchByCurrentDateNoDataAvailable() {
 
  parcelObj.setpStatus(PStatus.OUT.toString());
  trackingDetails.setUpdatedOn(myDate);
  trackingDetails.setBagId("1000100006");
  trackingDetails.setUpdatedBy(userRMS);
  trackingDetails.setObjParcel(parcelObj);
  trackingDetails.setLocationType(LocationType.POST_OFFICE);
  trackingDetails.setLocationId(userPO.getPostalCode());

  trackingDetailsList.add(trackingDetails);
  
  parcelObj2.setpStatus(PStatus.OUT.toString());
  trackingDetailsObj2.setUpdatedOn(myDate1);
  trackingDetailsObj2.setBagId("1000100007");
  trackingDetailsObj2.setUpdatedBy(userRMS);
  trackingDetailsObj2.setLocationType(LocationType.RMS);
  trackingDetailsObj2.setLocationId(userRMS.getRmsId());
  trackingDetailsObj2.setObjParcel(parcelObj2);

  trackingDetailsList.add(trackingDetailsObj2);

  rmsList.add(rmsTable);
  masterList.add(masterAddress);

	response.put("rms", rmsList);
	response.put("master", masterList);
	response.put("Tracking", trackingDetailsList);

Mockito.when(userRepository.findByRmsId(5L)).thenReturn(users);

Mockito.when(trackingDetailsRepository.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus.OUT, timestamp1, timestamp1, users)).thenReturn(trackingDetailsList);

Mockito.when(rmsRepository.findAllByIdAndStatus(trackingDetailsList.get(1).getLocationId(), Status.ACTIVE)).thenReturn(rmsList);

Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(trackingDetailsList.get(0).getLocationId().intValue(),
 Status.ACTIVE)).thenReturn(masterList);

assertNotEquals(response, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long) 0, 5l, PStatus.IN));

assertEquals(true, trackingServiceImp.getAlldailyDispatchReportByCurrentDate(currentTs1, currentTs2, userRMS, (long) 0, 5l, PStatus.IN).isEmpty());
  
}

 
}