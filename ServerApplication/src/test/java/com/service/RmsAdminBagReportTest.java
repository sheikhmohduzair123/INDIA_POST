package com.service;

import com.Application;
import com.constants.BagStatus;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Parcel;
import com.domain.Role;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.impl.ReportServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class RmsAdminBagReportTest {

	@InjectMocks
	ReportServiceImpl reportServiceImpl;

	@Mock
	SUserRepository userRepository;

	@Mock
	TrackingDetailsRepository trackingDetailsRepository;

	@Test
	public void userAdminRmsReport() throws Exception {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
		java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");

		Role role = new Role();
		role.setId(4);
		role.setName("ROLE_RMS_USER");
		role.setStatus(Status.ACTIVE);

		List<User> userList = new ArrayList<User>();

		User user1 = new User();
		user1.setId(1);
		user1.setRmsId(12L);
		user1.setPostalCode(0);
		user1.setRole(role);
		user1.setStatus(Status.ACTIVE);
		userList.add(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setRmsId(12L);
		user2.setPostalCode(0);
		user2.setRole(role);
		user2.setStatus(Status.ACTIVE);
		userList.add(user2);

		Parcel parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setActWt(1234);
		parcel1.setTrackId("1010000080000");
		parcel1.setpStatus("BAGGED");

		Parcel parcel2 = new Parcel();
		parcel2.setId(2L);
		parcel2.setActWt(5678);
		parcel2.setTrackId("1010000080000");
		parcel2.setpStatus("BAGGED");

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();

		TrackingDetails trackingDetails = new TrackingDetails();
		trackingDetails.setId(1L);
		trackingDetails.setBagStatus(BagStatus.IN);
		trackingDetails.setpStatus(PStatus.BAGGED);
		trackingDetails.setUpdatedOn(timestamp1);
		trackingDetails.setBagId("123456780");
		trackingDetails.setBagDesc("Bagged at postal code 80000");
		trackingDetails.setBagTitle("INSURED");
		trackingDetails.setObjParcel(parcel1);
		trackingDetails.setUpdatedBy(user1);
		trackingDetailsList.add(trackingDetails);

		TrackingDetails trackingDetails1 = new TrackingDetails();
		trackingDetails1.setId(2L);
		trackingDetails1.setBagStatus(BagStatus.IN);
		trackingDetails1.setpStatus(PStatus.BAGGED);
		trackingDetails1.setUpdatedOn(timestamp2);
		trackingDetails1.setBagId("123456780");
		trackingDetails1.setBagDesc("Bagged at postal code 80000");
		trackingDetails1.setBagTitle("INSURED");
		trackingDetails1.setObjParcel(parcel2);
		trackingDetails1.setUpdatedBy(user2);
		trackingDetailsList.add(trackingDetails1);


		Mockito.when(userRepository.findByRmsId(12L)).thenReturn(userList);
		Mockito.when(trackingDetailsRepository.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(timestamp1, timestamp2, BagStatus.IN, userList)).thenReturn(trackingDetailsList);
		Assert.assertEquals(false, reportServiceImpl.fetchBagInventoryWithRmsAndBagStatus(12L, BagStatus.IN, timestamp1, timestamp2).isEmpty());
		Assert.assertEquals(timestamp1, reportServiceImpl.fetchBagInventoryWithRmsAndBagStatus(12L, BagStatus.IN, timestamp1, timestamp2).values().stream().findFirst().get().get(0).getUpdatedOn());
	}

	@Test
	public void userAdminRmsReportEmptyList() throws Exception {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
		java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");

		Role role = new Role();
		role.setId(4);
		role.setName("ROLE_RMS_USER");
		role.setStatus(Status.ACTIVE);

		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setId(1);
		user1.setRmsId(12L);
		user1.setPostalCode(0);
		user1.setRole(role);
		user1.setStatus(Status.ACTIVE);
		userList.add(user1);

		Mockito.when(userRepository.findByRmsId(12L)).thenReturn(userList);
		Mockito.when(trackingDetailsRepository.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(timestamp1, timestamp2, BagStatus.CREATED, userList)).thenReturn(null);
		Assert.assertEquals(true, reportServiceImpl.fetchBagInventoryWithRmsAndBagStatus(12L, BagStatus.IN, timestamp1, timestamp2).isEmpty());

	}

	@Test
	public void userAdminRmsReportDateNotPresent() throws Exception {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
		java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");

		java.sql.Timestamp timestamp3 = java.sql.Timestamp.valueOf("2012-06-02 00:00:00.0");
		java.sql.Timestamp timestamp4 = java.sql.Timestamp.valueOf("2012-06-03 23:59:59.0");

		Role role = new Role();
		role.setId(4);
		role.setName("ROLE_RMS_USER");
		role.setStatus(Status.ACTIVE);

		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setId(1);
		user1.setRmsId(12L);
		user1.setPostalCode(0);
		user1.setRole(role);
		user1.setStatus(Status.ACTIVE);
		userList.add(user1);

		Parcel parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setActWt(1234);
		parcel1.setTrackId("1010000080000");
		parcel1.setpStatus("BAGGED");

		Parcel parcel2 = new Parcel();
		parcel2.setId(2L);
		parcel2.setActWt(5678);
		parcel2.setTrackId("1010000080000");
		parcel2.setpStatus("BAGGED");

		Mockito.when(userRepository.findByRmsId(12L)).thenReturn(userList);
		Mockito.when(trackingDetailsRepository.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(timestamp1, timestamp2, BagStatus.CREATED, userList)).thenReturn(null);
		Assert.assertEquals(true, reportServiceImpl.fetchBagInventoryWithRmsAndBagStatus(12L, BagStatus.CREATED, timestamp3, timestamp4).isEmpty());
	}

	@Test
	public void userAdminRmsReportWrongStatus() throws Exception {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2012-05-02 00:00:00.0");
		java.sql.Timestamp timestamp2 = java.sql.Timestamp.valueOf("2012-05-03 23:59:59.0");

		Role role = new Role();
		role.setId(4);
		role.setName("ROLE_RMS_USER");
		role.setStatus(Status.ACTIVE);

		List<User> userList = new ArrayList<User>();

		User user1 = new User();
		user1.setId(1);
		user1.setRmsId(12L);
		user1.setPostalCode(0);
		user1.setRole(role);
		user1.setStatus(Status.ACTIVE);
		userList.add(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setRmsId(12L);
		user2.setPostalCode(0);
		user2.setRole(role);
		user2.setStatus(Status.ACTIVE);
		userList.add(user2);

		Parcel parcel1 = new Parcel();
		parcel1.setId(1L);
		parcel1.setActWt(1234);
		parcel1.setTrackId("1010000080000");
		parcel1.setpStatus("BAGGED");

		Parcel parcel2 = new Parcel();
		parcel2.setId(2L);
		parcel2.setActWt(5678);
		parcel2.setTrackId("1010000080000");
		parcel2.setpStatus("BAGGED");

		Mockito.when(userRepository.findByRmsId(12L)).thenReturn(userList);
		Mockito.when(trackingDetailsRepository.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(timestamp1, timestamp2, BagStatus.CREATED, userList)).thenReturn(null);
		Assert.assertEquals(true, reportServiceImpl.fetchBagInventoryWithRmsAndBagStatus(12L, BagStatus.OUT, timestamp1, timestamp2).isEmpty());
	}

}

