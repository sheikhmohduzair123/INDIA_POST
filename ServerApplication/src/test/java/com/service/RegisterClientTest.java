package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.constants.Status;
import com.domain.Client;
import com.domain.MasterAddress;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.ReportRepository;
import com.repositories.SUserRepository;
import com.services.impl.ClientServiceImpl;
import com.services.impl.ParcelServiceImpl;
import com.services.impl.ReportServiceImpl;
import com.vo.ClientVo;
import com.vo.MasterAddressVo;

@RunWith(MockitoJUnitRunner.class)
public class RegisterClientTest {

	@InjectMocks
	private ParcelServiceImpl parcelServiceImpl;

	@Mock
	private MasterAddressRepository masterAddressRepository;

	@Mock
	private ReportRepository reportRepository;

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ReportServiceImpl reportServiceimpl;

	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	@Mock
	private SUserRepository sUserRepository;

	List<MasterAddress> masterAddressesList;
	List<MasterAddress> masterAddressesEmptyList;

	@Before
	public void init() {
		masterAddressesList = new ArrayList<MasterAddress>();

		MasterAddress m = new MasterAddress();
		m.setSubOffice("suboffice");
		m.setPostalCode(1000);
		m.setThana("thana");
		m.setDistrict("district");
		m.setDivision("division");
		m.setZone("zone");

		masterAddressesList.add(m);

		masterAddressesEmptyList = new ArrayList<MasterAddress>();
	}

	@Test
	public void getAddressByPostalcodeTest() {
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1000, Status.ACTIVE)).thenReturn(masterAddressesList);
    	List<MasterAddressVo> list = parcelServiceImpl.getAddressByPostalcode(1000);
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
	}

	@Test
	public void getAddressByPostalcodeNullTest() {
		Mockito.when(masterAddressRepository.findByPostalCodeAndStatus(1000, Status.ACTIVE)).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressByPostalcode(1000).size());
	}

	@Test
	public void getAddressBysubOfficeStartsWithTest() {
		Mockito.when(masterAddressRepository.findBySubOfficeStartsWithIgnoreCase("suboffice")).thenReturn(masterAddressesList);
    	List<MasterAddress> list = parcelServiceImpl.getAddressBysubOfficeStartsWith("suboffice");
		assertEquals(1, list.size());
		assertEquals("suboffice", list.get(0).getSubOffice());
		assertEquals("thana", list.get(0).getThana());
		assertEquals("zone", list.get(0).getZone());
		assertEquals("division", list.get(0).getDivision());
		assertEquals("district", list.get(0).getDistrict());
	}

	@Test
	public void getAddressBysubOfficeStartsWithNullTest() {
		Mockito.when(masterAddressRepository.findBySubOfficeStartsWithIgnoreCase("suboffice")).thenReturn(masterAddressesEmptyList);
    	assertEquals(0, parcelServiceImpl.getAddressBysubOfficeStartsWith("suboffice").size());
	}

	@Test
	public void getDistinctSubOfficeByThanaTest() {
		List<String> subofficelist = new ArrayList<String>();
		subofficelist.add("subofficelist1");
		subofficelist.add("subofficelist2");

		Mockito.when(masterAddressRepository.findDistinctSubOfficeByThana("thana", Status.ACTIVE)).thenReturn(subofficelist);
    	List<String> list = parcelServiceImpl.getDistinctSubOfficeByThana("thana");

		assertEquals(2, list.size());
		assertEquals("subofficelist1", list.get(0));
		assertEquals("subofficelist2", list.get(1));
	}

	@Test
	public void getDistinctSubOfficeByThanaNullTest() {
		Mockito.when(masterAddressRepository.findDistinctSubOfficeByThana("thana", Status.ACTIVE)).thenReturn(null);
    	assertNull(parcelServiceImpl.getDistinctSubOfficeByThana("thana"));
	}

	//counter exists with same name in pincode
	@Test
	public void checkExistingUserInPostalCodeTrueTest() {
		Client client = new Client();
		client.setClientId("1234567890");
		client.setStatus(Status.ACTIVE);
		client.setPostalCode(1000);
		client.setClientName("counter");

		List<Client> clientList = new ArrayList<Client>();
		clientList.add(client);

		Mockito.when(clientRepository.findByPostalCodeAndStatus(1000, Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(clientList);
		ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

		assertTrue(clientServiceImpl.fetchExistingCounterInPostalCode("counter", 1000));
	}

	//counter doesnot exists with same name in pincode
		@Test
		public void checkExistingUserInPostalCodeFalseTest() {
			Client client = new Client();
			client.setClientId("1234567890");
			client.setStatus(Status.ACTIVE);
			client.setPostalCode(1000);
			client.setClientName("counter");

			List<Client> clientList = new ArrayList<Client>();
			clientList.add(client);

			Mockito.when(clientRepository.findByPostalCodeAndStatus(1000, Enum.valueOf(Status.class,"ACTIVE"))).thenReturn(clientList);
			ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

			assertFalse(clientServiceImpl.fetchExistingCounterInPostalCode("counter-1", 1000));
		}

		//save client
				@Test
				public void saveClientDetailsTest() throws IOException {
					Client client = new Client();
					client.setPostalCode(1000);
					client.setClientName("counter");

					User user = new User();
					user.setUsername("user");
					ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");
					ReflectionTestUtils.setField(clientServiceImpl,"clientstatus","unauthorized");

					Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(user);
					Mockito.when(clientRepository.save(client)).thenReturn(client);
					Mockito.when(clientRepository.countByPostalCode(1000)).thenReturn(2L);

					ClientVo client1 = clientServiceImpl.saveClientDetails(client,"user");

					assertNotNull(client1.getClientId());
					assertNotNull(client1.getClientToken());
					assertTrue(client1.getClientId().startsWith("1000"));
				}
}