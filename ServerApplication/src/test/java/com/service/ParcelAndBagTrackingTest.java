package com.service;

import com.Application;
import com.constants.BagStatus;
import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.impl.TrackingServiceImpl;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ParcelAndBagTrackingTest {

	@InjectMocks
	TrackingServiceImpl trackingServiceimpl;

	@Mock
	ParcelRepository parcelRepository;

	@Mock
	SUserRepository userRepository;

	@Mock
	MasterAddressRepository masterAddressRepository;

	@Mock
	TrackingDetailsRepository trackingDetailsRepository;

	@Test
	public void trackWithParcelIdUnbagged(){

		Parcel parcel = new Parcel();
		parcel.setId(1L);
		parcel.setTrackId("0180001000006");
		parcel.setpStatus("UNBAGGED");
		parcel.setStatus("booked");
		parcel.setCreatedDate(new java.util.Date());
		parcel.setCreatedBy(2);

		String parcelTrackId = "0180001000006";
		User user = new User();
		user.setCreatedBy(2);
		user.setPostalCode(1100);

		List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
		MasterAddress masterAddress = new MasterAddress();
		masterAddress.setPostalCode(1100);
		masterAddress.setSubOffice("ABC");
		masterAddress.setStatus(Status.ACTIVE);
		masterAddressesList.add(masterAddress);

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
		TrackingDetails trackingDetails = new TrackingDetails();
		trackingDetails.setUpdatedOn(new java.util.Date());
		trackingDetails.setBagId("0012100004");
		trackingDetails.setTrackingDesc("Parcel Booked at Postal Code "+user.getPostalCode() +" ("+masterAddressesList.get(0).getSubOffice()+")");
		trackingDetailsList.add(trackingDetails);

		Mockito.when(parcelRepository.findParcelByTrackId(parcelTrackId)).thenReturn(parcel);
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1100, Status.ACTIVE)).thenReturn(masterAddressesList);
		Mockito.when(userRepository.findById(parcel.getCreatedBy())).thenReturn(Optional.of(user));
		Assert.assertEquals("Parcel Booked at Postal Code "+user.getPostalCode() +" ("+masterAddressesList.get(0).getSubOffice()+")" , trackingServiceimpl.fetchDetailsWithParcelId(parcel).get(0).getTrackingDesc());
	}

	@Test
	public void trackWithParcelIdBagged(){

		Parcel parcel = new Parcel();
		parcel.setId(1L);
		parcel.setTrackId("0180001000006");
		parcel.setpStatus("BAGGED");
		parcel.setCreatedDate(new java.util.Date());
		parcel.setCreatedBy(2);

		String parcelTrackId = "0180001000006";
		User user = new User();
		user.setCreatedBy(2);
		user.setPostalCode(1100);

		List<MasterAddress> masterAddressesList = new ArrayList<MasterAddress>();
		MasterAddress masterAddress = new MasterAddress();
		masterAddress.setPostalCode(1100);
		masterAddress.setSubOffice("ABC");
		masterAddress.setStatus(Status.ACTIVE);
		masterAddressesList.add(masterAddress);

		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
		TrackingDetails trackingDetails = new TrackingDetails();
		trackingDetails.setUpdatedOn(new java.util.Date());
		trackingDetails.setBagId("0012100004");
		trackingDetails.setTrackingDesc("Parcel Bagged at Postal Code");
		trackingDetails.setObjParcel(parcel);
		trackingDetailsList.add(trackingDetails);

		Mockito.when(parcelRepository.findParcelByTrackId(parcelTrackId)).thenReturn(parcel);
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1100, Status.ACTIVE)).thenReturn(masterAddressesList);
		Mockito.when(userRepository.findById(parcel.getCreatedBy())).thenReturn(Optional.of(user));
		Mockito.when(trackingDetailsRepository.findByObjParcelOrderByUpdatedOnDesc(parcel)).thenReturn(trackingDetailsList);
		Assert.assertEquals("Parcel Bagged at Postal Code" , trackingServiceimpl.fetchDetailsWithParcelId(parcel).get(0).getTrackingDesc());
	}

	@Test
	public void parcelNotPresent(){

		String parcelTrackId = "0180001000007";
		Parcel parcel = new Parcel();
		Mockito.when(parcelRepository.findParcelByTrackId(parcelTrackId)).thenReturn(null);
		Assert.assertEquals(null , trackingServiceimpl.fetchDetailsWithParcelId(parcel));
	}

	@Test
	public void bagTrackingWithId(){

		String bagId = "0012100004";
		List<TrackingDetails> trackingDetailsList = new ArrayList<TrackingDetails>();
		TrackingDetails trackingDetails = new TrackingDetails();
		trackingDetails.setUpdatedOn(new java.util.Date());
		trackingDetails.setTrackingDesc("Bagged at postalCode 1100");
		trackingDetails.setBagId("0012100004");
		trackingDetails.setBagStatus(BagStatus.CREATED);
		trackingDetailsList.add(trackingDetails);

		Mockito.when(trackingDetailsRepository.findByBagIdOrderByUpdatedOnDesc(bagId)).thenReturn(trackingDetailsList);
		Assert.assertEquals(BagStatus.CREATED, trackingServiceimpl.fetchDetailsWithBagId(bagId).values().stream().findFirst().get().get(0).getBagStatus());
	}

	@Test
	public void bagIdNotPresent(){

		Mockito.when(trackingDetailsRepository.findByBagIdOrderByUpdatedOnDesc("0012100004")).thenReturn(null);
		Assert.assertEquals(null, trackingServiceimpl.fetchDetailsWithBagId("0012100004"));
	}
}