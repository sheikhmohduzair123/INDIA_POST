package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.constants.PStatus;
import com.constants.Status;
import com.constants.UpdatePayableAmnt;
import com.domain.Address;
import com.domain.Client;
import com.domain.Control;
import com.domain.Parcel;
import com.domain.Services;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.ControlRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.SUserRepository;
import com.services.impl.ClientServiceImpl;
import com.services.impl.DataSyncServiceImpl;
import com.services.impl.ParcelServiceImpl;
import com.vo.ServicesVo;
import com.vo.UpdatePayableAmountVo;

@RunWith(MockitoJUnitRunner.class)
public class ParcelBookTest {

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	@Mock
	private ClientServiceImpl clientService;

	@InjectMocks
	private ParcelServiceImpl parcelServiceImpl;

	@Mock
	private PostalServiceRepository postalServiceRepository;

	@InjectMocks
	private DataSyncServiceImpl dataSyncServiceImpl;

	@Mock
	private DataSyncServiceImpl dataSyncService;

	@Mock
	private ControlRepository controlRepository;

	@Mock
	private SUserRepository sUserRepository;

	@Mock
	private ParcelRepository parcelRepository;

	//new machine test
	@Test
	public void checkLocalClientStatusNewMachineTest() throws Exception {
		List<Client> clientList = new ArrayList<Client>();

		String token = "abcdefghijk1234567890";

		ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

		Mockito.when(clientRepository.findAll()).thenReturn(clientList);
		assertEquals("new machine",clientServiceImpl.checkLocalClientStatus(token));
	}

		//rejected clients present: no client commissioned
		@Test
		public void checkLocalClientStatusNoClientCommissionTest() throws Exception {
			List<Client> clientList = new ArrayList<Client>();

			String token = "abcdefghijk1234567890";

			List<String> clientStatuses = new ArrayList<>();
	        clientStatuses.add("approval required");
	        clientStatuses.add("approved");

	        Client c1 = new Client();
	        c1.setStatus(Status.ACTIVE);
	        c1.setClientStatus("unauthorized");

			clientList.add(c1);
			List<Client> clientList1 = new ArrayList<Client>();

			ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

			Mockito.when(clientRepository.findAll()).thenReturn(clientList);
			Mockito.when(clientRepository.findByClientStatusInAndStatus(clientStatuses, Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(clientList1);

			assertEquals("no client commissioned",clientServiceImpl.checkLocalClientStatus(token));
		}


		@Test
		public void saveClientDetailsLocalTest() {

				User user = new User();

				Client c1 = new Client();
		        c1.setStatus(Status.ACTIVE);
		        c1.setClientStatus("approved");
		        c1.setCreatedBy(user);
		        c1.setUpdatedBy(user);
		        c1.setMachineId("abcd1234");
		        Mockito.when(clientRepository.save(c1)).thenReturn(c1);
		        assertEquals("approved",clientServiceImpl.saveClientDetailsLocal(c1));
		}

		//No Such Active client found in save client details
		@Test
		public void saveClientDetailsLocalNoSuchActiveClientFoundTest() {

				User user = new User();

				Client c1 = new Client();
		        c1.setStatus(Status.ACTIVE);
		        c1.setClientStatus("No Such Active client found");
		        Mockito.when(clientRepository.save(c1)).thenReturn(c1);
		        assertEquals("No Such Active client found",clientServiceImpl.saveClientDetailsLocal(c1));
		}

		@Test
		public void getSubServicesTest() {
			Services s1 = new Services();
			s1.setServiceName("s1");
			s1.setServiceCode("S1");
			s1.setParentServiceId(1L);

			Services s2 = new Services();
			s2.setServiceName("s2");
			s2.setServiceCode("S2");
			s2.setParentServiceId(1L);

			List<Services> servicelist = new ArrayList<Services>();
			servicelist.add(s1);
			servicelist.add(s2);

			Mockito.when(postalServiceRepository.findByParentServiceIdAndStatus(1L, Status.ACTIVE)).thenReturn(servicelist);

			List<ServicesVo> response = parcelServiceImpl.getSubServices(1L);

			assertEquals("1",response.get(0).getParentServiceId().toString());
			assertEquals("1",response.get(1).getParentServiceId().toString());
			assertEquals("s1", response.get(0).getServiceName());
			assertEquals("s2", response.get(1).getServiceName());
		}

		@Test
		public void getSubServicesNullTest() {

			List<Services> servicelist = new ArrayList<Services>();
			Mockito.when(postalServiceRepository.findByParentServiceIdAndStatus(1L, Status.ACTIVE)).thenReturn(servicelist);
			assertTrue(parcelServiceImpl.getSubServices(1L).isEmpty());
		}

		//exception test if control table entry not found
		@Test
		public void getLastUpdatedTimeTest() {

			List<Control> controlList = new ArrayList<Control>();
			Mockito.when(controlRepository.findAll()).thenReturn(controlList);
  		    Assertions.assertThrows(Exception.class, () -> {
				  dataSyncServiceImpl.inLastDay();
			});
		}

		//sync required
		@Test
		public void getLastUpdatedTimeSyncRequiredTest() throws Exception {

			List<Control> controlList = new ArrayList<Control>();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
			String text = "2020-05-18 10:00:05.123";
			LocalDateTime dateTime = LocalDateTime.parse(text, formatter);

			Control cntrl = new Control();
			cntrl.setClientMasterDataSyncTimestamp(Timestamp.valueOf(dateTime));

			controlList.add(cntrl);

			ReflectionTestUtils.setField(dataSyncServiceImpl,"synctime",86400000L);

			Mockito.when(controlRepository.findAll()).thenReturn(controlList);
			assertEquals("sync required", dataSyncServiceImpl.inLastDay());
		}

		//sync not required within sync time period value
		@Test
		public void getLastUpdatedTimeSyncNotRequiredTest() throws Exception {

			List<Control> controlList = new ArrayList<Control>();

		//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
		//	String text = "2020-07-07 10:00:05.123";
			
			Date date = new Date();
			
			LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
		//	LocalDateTime dateTime = LocalDateTime.parse(text, formatter);

			Control cntrl = new Control();
			cntrl.setClientMasterDataSyncTimestamp(Timestamp.valueOf(dateTime));

			controlList.add(cntrl);

			ReflectionTestUtils.setField(dataSyncServiceImpl,"synctime",86400000L);

			Mockito.when(controlRepository.findAll()).thenReturn(controlList);
			assertEquals("sync not required", dataSyncServiceImpl.inLastDay());

		}

		//sync not required with future date passed
		@Test
		public void getLastUpdatedTimeSyncNotRequiredFutureDateTest() throws Exception {

				List<Control> controlList = new ArrayList<Control>();

				//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
				//String text = "2020-07-07 10:00:05.123";
				//LocalDateTime dateTime = LocalDateTime.parse(text, formatter);

				Date date = new Date();
				
				LocalDateTime dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				
				dateTime.plusDays(2);
				
				Control cntrl = new Control();
				cntrl.setClientMasterDataSyncTimestamp(Timestamp.valueOf(dateTime));

				controlList.add(cntrl);

				ReflectionTestUtils.setField(dataSyncServiceImpl,"synctime",86400000L);

				Mockito.when(controlRepository.findAll()).thenReturn(controlList);
				assertEquals("sync not required", dataSyncServiceImpl.inLastDay());
		}

		//save parcel details when 4th label  printing on that date
		@Test
		public void saveParcelDetailsTest()
		{
			Address a = new Address();
			a.setPostalCode(1000);

			User u = new User();
			u.setUsername("user");
			u.setId(1);

			UpdatePayableAmountVo objVo = new UpdatePayableAmountVo();
			objVo.setPaperSize("A6");
			objVo.setRadioButtonValue(UpdatePayableAmnt.YES);
			objVo.setUpdateAmount(200);
			
			List<Client> clientList = new ArrayList<Client>();
			Client c = new Client();
			clientList.add(c);

			Parcel parcel = new Parcel();
			parcel.setSenderAddress(a);

			List<String> labelcodes = new ArrayList<String>();
			//labelcodes.add("1000202005220003");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			String dat = simpleDateFormat.format(new Date());
			
			labelcodes.add("1000"+dat+"0003");
			
			ReflectionTestUtils.setField(parcelServiceImpl,"status","ACTIVE");

			Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(u);
			Mockito.when(clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(clientList);
			Mockito.when(parcelRepository.save(parcel)).thenReturn(parcel);
			Mockito.when(parcelRepository.getLabelCode(PageRequest.of(0,1))).thenReturn(labelcodes);

			Parcel response = parcelServiceImpl.saveParcelDetails(parcel,objVo, u.getUsername());

			assertEquals(parcel.getSenderAddress(), response.getSenderAddress());
			assertTrue(response.getLabelCode().endsWith("0004"));
			assertNotNull(response.getCreatedDate());
			assertEquals(u.getId(), response.getCreatedBy());
			assertEquals(c, response.getClient());
			assertEquals(0, response.getPrintCount());
			assertEquals(Status.ACTIVE.toString(), response.getStatus());
			assertEquals(PStatus.UNBAGGED.toString(), response.getpStatus());
			assertFalse(response.isSyncFlag());

		}

		//save parcel details when 4th label  printing on that date
		@Test
		public void saveParcelDetailsFirstLabelOfTheDayTest()
		{
			Address a = new Address();
			a.setPostalCode(1000);

			User u = new User();
			u.setUsername("user");
			u.setId(1);

			UpdatePayableAmountVo objVo = new UpdatePayableAmountVo();
			objVo.setPaperSize("A6");
			objVo.setRadioButtonValue(UpdatePayableAmnt.YES);
			objVo.setUpdateAmount(200);
			
			List<Client> clientList = new ArrayList<Client>();
			Client c = new Client();
			clientList.add(c);

			Parcel parcel = new Parcel();
			parcel.setSenderAddress(a);

			List<String> labelcodes = new ArrayList<String>();
			labelcodes.add("1000202005120003"); //last label printed on 12/5/2020

			ReflectionTestUtils.setField(parcelServiceImpl,"status","ACTIVE");

			Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(u);
			Mockito.when(clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(clientList);
			Mockito.when(parcelRepository.save(parcel)).thenReturn(parcel);
			Mockito.when(parcelRepository.getLabelCode(PageRequest.of(0,1))).thenReturn(labelcodes);

			Parcel response = parcelServiceImpl.saveParcelDetails(parcel,objVo,  u.getUsername());

			assertEquals(parcel.getSenderAddress(), response.getSenderAddress());
			assertTrue(response.getLabelCode().endsWith("0001"));
			assertNotNull(response.getCreatedDate());
			assertEquals(u.getId(), response.getCreatedBy());
			assertEquals(c, response.getClient());
			assertEquals(0, response.getPrintCount());
			assertEquals(Status.ACTIVE.toString(), response.getStatus());
			assertEquals(PStatus.UNBAGGED.toString(), response.getpStatus());
			assertFalse(response.isSyncFlag());

		}

}
