package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.constants.BagStatus;
import com.constants.Status;
import com.domain.Role;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.impl.ReportServiceImpl;
import com.vo.TrackingVo;

@RunWith(MockitoJUnitRunner.class)
public class BagsReportForPostOfficeUserTest {

	@InjectMocks
	private ReportServiceImpl reportServiceImpl;

	@Mock
	private SUserRepository sUserRepository;

	@Mock
	private TrackingDetailsRepository trackingDetailsRepository;

	// Test cases for bagStatus is IN
	@Test
	public void INtest() throws ParseException {

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		User user2 = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(loginuser);
		userlist.add(user2);

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();

		Date date = new Date(05 - 05 - 2020);
		TrackingDetails td = new TrackingDetails();
		td.setBagId("123999999");
		td.setBagStatus(BagStatus.IN);
		td.setUpdatedOn(new Timestamp(date.getTime()));
		td.setUpdatedBy(loginuser);

		date = new Date(06 - 05 - 2020);
		TrackingDetails td1 = new TrackingDetails();
		td1.setBagId("123456789");
		td1.setBagStatus(BagStatus.IN);
		td1.setUpdatedOn(new Timestamp(date.getTime()));
		td1.setUpdatedBy(loginuser);

		date = new Date(06 - 05 - 2020);
		TrackingDetails td2 = new TrackingDetails();
		td2.setBagId("123456789");
		td2.setBagStatus(BagStatus.IN);
		td2.setUpdatedOn(new Timestamp(date.getTime()));
		td2.setUpdatedBy(loginuser);

		trackingDetailsList.add(td);
		trackingDetailsList.add(td1);
		trackingDetailsList.add(td2);


    //    Mockito.when(sUserRepository.findAllByPostalCodeAndRoleAndStatus(1000, role_po, Status.ACTIVE)).thenReturn(userlist);

		Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(trackingDetailsList);

		Map<String, List<TrackingVo>> response = reportServiceImpl.getPoBagReport("2020-05-03","2020-05-09", BagStatus.IN, loginuser);

		assertNotNull(response);
		assertEquals(2, response.keySet().size());
		assertEquals(BagStatus.IN, response.get("123456789").get(0).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123456789").get(1).getBagStatus());
		assertEquals(BagStatus.IN, response.get("123999999").get(0).getBagStatus());
		assertEquals(td1.getUpdatedOn(), response.get("123456789").get(0).getUpdatedOn());
		assertEquals(td2.getUpdatedOn(), response.get("123456789").get(1).getUpdatedOn());
		assertEquals(td.getUpdatedOn(), response.get("123999999").get(0).getUpdatedOn());
	}

	// Test cases for bagStatus is OUT
	@Test
	public void OUTtest() throws ParseException {

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		User user2 = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(loginuser);
		userlist.add(user2);

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();

		Date date = new Date(05 - 05 - 2020);
		TrackingDetails td = new TrackingDetails();
		td.setBagId("123999999");
		td.setBagStatus(BagStatus.OUT);
		td.setUpdatedOn(new Timestamp(date.getTime()));
		td.setUpdatedBy(loginuser);

		date = new Date(06 - 05 - 2020);
		TrackingDetails td1 = new TrackingDetails();
		td1.setBagId("123456789");
		td1.setBagStatus(BagStatus.OUT);
		td1.setUpdatedOn(new Timestamp(date.getTime()));
		td1.setUpdatedBy(loginuser);

		date = new Date(06 - 05 - 2020);
		TrackingDetails td2 = new TrackingDetails();
		td2.setBagId("123456789");
		td2.setBagStatus(BagStatus.OUT);
		td2.setUpdatedOn(new Timestamp(date.getTime()));
		td2.setUpdatedBy(loginuser);

		trackingDetailsList.add(td);
		trackingDetailsList.add(td1);
		trackingDetailsList.add(td2);

    //    Mockito.when(sUserRepository.findAllByPostalCodeAndRoleAndStatus(1000, role_po, Status.ACTIVE)).thenReturn(userlist);

		Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(trackingDetailsList);

		Map<String, List<TrackingVo>> response = reportServiceImpl.getPoBagReport("2020-05-03","2020-05-09", BagStatus.OUT, loginuser);

		assertNotNull(response);
		assertEquals(2, response.keySet().size());
		assertEquals(BagStatus.OUT, response.get("123456789").get(0).getBagStatus());
		assertEquals(BagStatus.OUT, response.get("123456789").get(1).getBagStatus());
		assertEquals(BagStatus.OUT, response.get("123999999").get(0).getBagStatus());
		assertEquals(td1.getUpdatedOn(), response.get("123456789").get(0).getUpdatedOn());
		assertEquals(td2.getUpdatedOn(), response.get("123456789").get(1).getUpdatedOn());
		assertEquals(td.getUpdatedOn(), response.get("123999999").get(0).getUpdatedOn());
	}

	//no data available for given parameters
	@Test
	public void Nulltest() throws ParseException {

		Role role_po = new Role();
		role_po.setName("ROLE_PO_USER");
		role_po.setStatus(Status.ACTIVE);

		User loginuser = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		User user2 = new User();
		loginuser.setRole(role_po);
		loginuser.setPostalCode(1000);

		List<User> userlist = new ArrayList<User>();
		userlist.add(loginuser);
		userlist.add(user2);

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();

  //    Mockito.when(sUserRepository.findAllByPostalCodeAndRoleAndStatus(1000, role_po, Status.ACTIVE)).thenReturn(userlist);
		Mockito.when(trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(Mockito.anyList(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(trackingDetailsList);

		Map<String, List<TrackingVo>> response = reportServiceImpl.getPoBagReport("2020-05-03","2020-05-09", BagStatus.OUT, loginuser);

		assertTrue(response.isEmpty());
		assertEquals(0, response.keySet().size());
	}


}