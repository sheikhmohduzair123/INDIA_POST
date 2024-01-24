package com.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.ReportService;
import com.services.impl.ReportServiceImpl;
import com.vo.TrackingVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class RmsReportLoginedRmsUserTest {

	@Mock
	ReportService reportService;

	@InjectMocks
	ReportServiceImpl reportServiceImpl;
	
	@Mock
	User userDummy;
	
	@Mock
	SUserRepository sUserRepository;
	
	@Mock
	TrackingDetailsRepository trackingDetailsRepository;

	@Before
	public void init() {
		Mockito.when(sUserRepository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(userDummy);
		 
	}
		
	//Test cases for bagStatus is IN
		@Test
		public void findTodayBagListByRmsWhenBagStatusIn() throws Exception {		
			
				java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
				java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String t1  = dateFormat.format(timestamp);
				String t2  = dateFormat.format(timestamp1);
				
				List<User> userList = new ArrayList<User>();
				
				Role role = new Role();
				role.setName("ROLE_RMS_USER");
				role.setId(4);
				
				User loginedUser = new User();
				loginedUser.setId(2);
				loginedUser.setRole(role);
				loginedUser.setRmsId(5L);
				loginedUser.setStatus(Status.ACTIVE);
				
				userList.add(loginedUser);
				
				Parcel objParcel = new Parcel();
				objParcel.setId(1L);
				objParcel.setActWt(1234);
				objParcel.setpStatus("IN");
				
				Parcel objParcel2 = new Parcel();
				objParcel2.setId(2L);
				objParcel2.setActWt(5678);
				objParcel2.setpStatus("IN");
				
				List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
				TrackingDetails td = new TrackingDetails();
				td.setId(1L);
				td.setBagStatus(BagStatus.IN);
				td.setpStatus(PStatus.IN);
				td.setUpdatedOn(timestamp);
				td.setBagId("123456789");
				td.setBagDesc("abc Bag Desc");
				td.setBagTitle("demo title");
				td.setObjParcel(objParcel);
				td.setUpdatedBy(loginedUser);
				
				TrackingDetails td1 = new TrackingDetails();
				td1.setId(2L);
				td1.setBagStatus(BagStatus.IN);
				td1.setpStatus(PStatus.IN);
				td1.setUpdatedOn(timestamp1);
				td1.setBagId("999999999");
				td1.setBagDesc("second Bag Desc");
				td1.setBagTitle("second demo title");
				td1.setObjParcel(objParcel2);
				td1.setUpdatedBy(loginedUser);
				
				trackingDetailsList.add(td);
				trackingDetailsList.add(td1);
				
				Mockito.when(sUserRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(userList);

				Mockito.when(trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus.IN, timestamp, timestamp1, userList )).thenReturn(trackingDetailsList);
				
				assertEquals("999999999",reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().stream().skip(1).findFirst().get().get(0).getBagId());
				//assertEquals("999999999",reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).entrySet().stream().skip(1).findFirst().get().getValue().get(0).getBagId());
				//assertEquals("123456789",reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).entrySet().stream().findFirst().get().getValue().get(0).getBagId());
				assertEquals(timestamp,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().stream().findFirst().get().get(0).getUpdatedOn());
				assertEquals(timestamp1,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().stream().skip(1).findFirst().get().get(0).getUpdatedOn());
		}
		
		//Test cases for bagStatus is OUT
		@Test
		public void findTodayBagListByRmsWhenBagStatusOUT() throws Exception {		
			
			//same date 
			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 23:59:59.0");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String t1  = dateFormat.format(timestamp);
			String t2  = dateFormat.format(timestamp1);
			
			List<User> userList = new ArrayList<User>();
			
			Role role = new Role();
			role.setName("ROLE_RMS_USER");
			role.setId(4);
			
			User loginedUser = new User();
			loginedUser.setId(2);
			loginedUser.setRole(role);
			loginedUser.setRmsId(5L);
			loginedUser.setStatus(Status.ACTIVE);
			
			userList.add(loginedUser);
			
			Parcel objParcel = new Parcel();
			objParcel.setId(1L);
			objParcel.setActWt(1234);
			
			Parcel objParcel2 = new Parcel();
			objParcel2.setId(2L);
			objParcel2.setActWt(5678);
			
			List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
			TrackingDetails td = new TrackingDetails();
			td.setId(1L);
			td.setBagStatus(BagStatus.OUT);
			td.setUpdatedOn(timestamp);
			td.setBagId("123456789");
			td.setBagDesc("abc Bag Desc");
			td.setBagTitle("demo title");
			td.setObjParcel(objParcel);
			td.setUpdatedBy(loginedUser);
			
			TrackingDetails td1 = new TrackingDetails();
			td1.setId(2L);
			td1.setBagStatus(BagStatus.OUT);
			td1.setUpdatedOn(timestamp1);
			td1.setBagId("123456780");
			td1.setBagDesc("second Bag Desc");
			td1.setBagTitle("second demo title");
			td1.setObjParcel(objParcel2);
			td1.setUpdatedBy(loginedUser);
			
			trackingDetailsList.add(td);
			trackingDetailsList.add(td1);
			
				Mockito.when(sUserRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(userList);

				Mockito.when(trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus.OUT, timestamp, timestamp1, userList )).thenReturn(trackingDetailsList);
				Assert.assertEquals("123456789", reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().stream().findFirst().get().get(0).getBagId());
				Assert.assertEquals("123456780", reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().stream().skip(1).findFirst().get().get(0).getBagId());
				Assert.assertEquals(timestamp, reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().stream().findFirst().get().get(0).getUpdatedOn());
				Assert.assertEquals(timestamp1, reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().stream().skip(1).findFirst().get().get(0).getUpdatedOn());
		
		}
		
		//Test cases for bagStatus is IN no OUT Status no Data found
		@Test
		public void findTodayBagListByRmsWhenBagStatusOutNoDataFound() throws Exception {		

			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String t1  = dateFormat.format(timestamp);
			String t2  = dateFormat.format(timestamp1);
			
			List<User> userList = new ArrayList<User>();
			
			Role role = new Role();
			role.setName("ROLE_RMS_USER");
			role.setId(4);
			
			User loginedUser = new User();
			loginedUser.setId(2);
			loginedUser.setRole(role);
			loginedUser.setRmsId(5L);
			loginedUser.setStatus(Status.ACTIVE);
			
			userList.add(loginedUser);
			
			Parcel objParcel = new Parcel();
			objParcel.setId(1L);
			objParcel.setActWt(1234);
			
			Parcel objParcel2 = new Parcel();
			objParcel2.setId(2L);
			objParcel2.setActWt(5678);
			
			List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
			TrackingDetails td = new TrackingDetails();
			td.setId(1L);
			td.setBagStatus(BagStatus.IN);
			td.setUpdatedOn(timestamp);
			td.setBagId("123456789");
			td.setBagDesc("abc Bag Desc");
			td.setBagTitle("demo title");
			td.setObjParcel(objParcel);
			td.setUpdatedBy(loginedUser);
			
			TrackingDetails td1 = new TrackingDetails();
			td1.setId(2L);
			td1.setBagStatus(BagStatus.IN);
			td1.setUpdatedOn(timestamp1);
			td1.setBagId("123456780");
			td1.setBagDesc("second Bag Desc");
			td1.setBagTitle("second demo title");
			td1.setObjParcel(objParcel2);
			td1.setUpdatedBy(loginedUser);
			
			trackingDetailsList.add(td);
			trackingDetailsList.add(td1);
			
			Mockito.when(sUserRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(userList);

	        Mockito.when(trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus.IN, timestamp, timestamp1, userList )).thenReturn(trackingDetailsList);
		
	        Assert.assertEquals(true, reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).isEmpty());
	        Assert.assertEquals(0, reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().size());
	        Assert.assertEquals(true, reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.OUT).values().isEmpty());
		}
		
		
		//Test cases for bagStatus is empty no data found (data is not exist in database)
		@Test
		public void findTodayBagListByRmsWhenBagListEmpty() throws Exception {		
			java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
			java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String t1  = dateFormat.format(timestamp);
			String t2  = dateFormat.format(timestamp1);
			
			List<User> userList = new ArrayList<User>();
			
			Role role = new Role();
			role.setName("ROLE_RMS_USER");
			role.setId(4);
			
			User loginedUser = new User();
			loginedUser.setId(2);
			loginedUser.setRole(role);
			loginedUser.setRmsId(5L);
			loginedUser.setStatus(Status.ACTIVE);
			
			userList.add(loginedUser);
			List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
			
			Mockito.when(sUserRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(userList);

	        Mockito.when(trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus.IN, timestamp, timestamp1, userList )).thenReturn(trackingDetailsList);
		
	        assertEquals(0,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().size());
	        assertEquals(0,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).size());
	        assertEquals(true,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).isEmpty());
	        assertEquals(true,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().isEmpty());
	        
		}
		
		
		//Test cases when on given date no data found select wrong date
				@Test
				public void findTodayBagListByRmsWrongDate() throws Exception {		

					java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
					java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");
					
					java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf("2012-06-02 00:00:00.0");
					java.sql.Timestamp timestamp3 = java.sql.Timestamp.valueOf("2012-06-03 23:59:59.0");
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String t1  = dateFormat.format(timestamp2);
					String t2  = dateFormat.format(timestamp3);
					
					List<User> userList = new ArrayList<User>();
					
					Role role = new Role();
					role.setName("ROLE_RMS_USER");
					role.setId(4);
					
					User loginedUser = new User();
					loginedUser.setId(2);
					loginedUser.setRole(role);
					loginedUser.setRmsId(5L);
					loginedUser.setStatus(Status.ACTIVE);
					
					userList.add(loginedUser);
					
					Parcel objParcel = new Parcel();
					objParcel.setId(1L);
					objParcel.setActWt(1234);
					
					Parcel objParcel2 = new Parcel();
					objParcel2.setId(2L);
					objParcel2.setActWt(5678);
					
					List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
					TrackingDetails td = new TrackingDetails();
					td.setId(1L);
					td.setBagStatus(BagStatus.IN);
					td.setUpdatedOn(timestamp);
					td.setBagId("123456789");
					td.setBagDesc("abc Bag Desc");
					td.setBagTitle("demo title");
					td.setObjParcel(objParcel);
					td.setUpdatedBy(loginedUser);
					
					TrackingDetails td1 = new TrackingDetails();
					td1.setId(2L);
					td1.setBagStatus(BagStatus.IN);
					td1.setUpdatedOn(timestamp1);
					td1.setBagId("123456780");
					td1.setBagDesc("second Bag Desc");
					td1.setBagTitle("second demo title");
					td1.setObjParcel(objParcel2);
					td1.setUpdatedBy(loginedUser);
					
					trackingDetailsList.add(td);
					trackingDetailsList.add(td1);
					
						
					Mockito.when(sUserRepository.findByRmsId(loginedUser.getRmsId())).thenReturn(userList);

			        Mockito.when(trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus.IN, timestamp, timestamp1, userList )).thenReturn(trackingDetailsList);
				
			        Assert.assertEquals(true,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).isEmpty());
			        Assert.assertEquals(true,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().isEmpty());
			        Assert.assertEquals(0,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).values().size());
			        Assert.assertEquals(0,reportServiceImpl.findTodayBagListByRMs(loginedUser, t1, t2, BagStatus.IN).size());
				}
				
				
	}