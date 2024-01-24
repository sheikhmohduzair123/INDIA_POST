package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.constants.Status;
import com.domain.Client;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.SUserRepository;
import com.services.impl.ClientServiceImpl;
import com.vo.ClientVo;

@RunWith(MockitoJUnitRunner.class)
public class ClientListTest {

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private SUserRepository sUserRepository;

	@InjectMocks
	private ClientServiceImpl clientServiceImpl;

	//get client details
	@Test
	public void fetchClientDetailsTest(){

		Client client = new Client();
		client.setClientId("1234567890");
		client.setClientName("counter");
		Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
		ClientVo response = clientServiceImpl.findClientDetail("1234567890");
		assertEquals("1234567890", response.getClientId());
		assertEquals("counter", response.getClientName());
	}

	//get client details null
		@Test
		public void fetchClientDetailsNullTest(){
			Client client = new Client();
			Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
			assertNull(clientServiceImpl.findClientDetail("1234567890").getClientId());

		}

		//get client list all
		@Test
		public void getClientListAllTest() {
			Client client = new Client();
			client.setClientId("1234567890");
			client.setClientName("counter");

			Client client1 = new Client();
			client1.setClientId("1111111111");
			client1.setClientName("counter-1");

			List<Client> clientList = new ArrayList<Client>();
			clientList.add(client);
			clientList.add(client1);
			Mockito.when(clientRepository.findAll()).thenReturn(clientList);
			List<ClientVo> response = clientServiceImpl.getClientList("all");
			assertEquals("1234567890",response.get(0).getClientId());
			assertEquals("1111111111",response.get(1).getClientId());
			assertEquals("counter",response.get(0).getClientName());
			assertEquals("counter-1",response.get(1).getClientName());
		}

		//get client list all empty
			@Test
			public void getClientListAllEmptyTest() {
				List<Client> clientList = new ArrayList<Client>();
				Mockito.when(clientRepository.findAll()).thenReturn(clientList);
				assertTrue( clientServiceImpl.getClientList("all").isEmpty());
			}

			//get approved status client list
			@Test
			public void getClientListTest() {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("approved");

				Client client1 = new Client();
				client1.setClientId("1111111111");
				client1.setClientName("counter-1");
				client1.setStatus(Status.ACTIVE);
				client1.setClientStatus("approved");

				List<Client> clientList = new ArrayList<Client>();
				clientList.add(client);
				clientList.add(client1);

				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findByClientStatusAndStatus("approved",Status.ACTIVE)).thenReturn(clientList);
				List<ClientVo> response = clientServiceImpl.getClientList("approved");

				assertEquals("1234567890",response.get(0).getClientId());
				assertEquals("1111111111",response.get(1).getClientId());
				assertEquals("counter",response.get(0).getClientName());
				assertEquals("counter-1",response.get(1).getClientName());
				assertEquals("approved",response.get(0).getClientStatus());
				assertEquals("approved",response.get(1).getClientStatus());

			}

			//get approved status client list empty
				@Test
				public void getClientListEmptyTest() {
					ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");
					List<Client> clientList = new ArrayList<Client>();
					Mockito.when(clientRepository.findByClientStatusAndStatus("approved",Status.ACTIVE)).thenReturn(clientList);
					assertTrue( clientServiceImpl.getClientList("approved").isEmpty());
				}

			//approve client
			@Test
			public void approveClientDetailsTest() throws Exception {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("approval required");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
				Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(user);
				Mockito.when(clientRepository.save(client)).thenReturn(client);

				Client response = clientServiceImpl.approveClientDetails("1234567890", "test", "user");

				assertEquals("1234567890",response.getClientId());
				assertEquals("approved",response.getClientStatus());
				assertEquals(Status.ACTIVE,response.getStatus());
				assertNotNull(response.getUpdatedOn());
				assertEquals("test", response.getRemarks());
				assertEquals(user.getUsername(), response.getCreatedBy().getUsername());
			}

			//reject client
			@Test
			public void rejectClientDetailsTest() throws Exception {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("approval required");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
				Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(user);
				Mockito.when(clientRepository.save(client)).thenReturn(client);

				Client response = clientServiceImpl.rejectClientDetails("1234567890", "test reject", "user");

				assertEquals("1234567890",response.getClientId());
				assertEquals("rejected",response.getClientStatus());
				assertEquals(Status.ACTIVE,response.getStatus());
				assertNotNull(response.getUpdatedOn());
				assertEquals("test reject", response.getRemarks());
				assertEquals(user.getUsername(), response.getCreatedBy().getUsername());
			}

			//reject client fail
			@Test
			public void rejectClientDetailsFailTest() throws Exception {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("rejected");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
				Mockito.when(clientRepository.save(client)).thenReturn(client);

				Client response = clientServiceImpl.rejectClientDetails("1234567890", "test reject fail", "user");

				assertEquals("1234567890",response.getClientId());
				assertEquals("rejected",response.getClientStatus());
				assertEquals(Status.ACTIVE,response.getStatus());
				assertNull(response.getUpdatedOn());
				assertNull(response.getRemarks());
			}

			//expire client
			@Test
			public void expireClientDetailsTest() throws Exception {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("approval required");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
				Mockito.when(sUserRepository.findUserByUsernameAndStatus("user", Status.ACTIVE)).thenReturn(user);
				Mockito.when(clientRepository.save(client)).thenReturn(client);

				Client response = clientServiceImpl.expireClient("1234567890", "test expire", "user");

				assertEquals("1234567890",response.getClientId());
				assertEquals("expired",response.getClientStatus());
				assertEquals(Status.ACTIVE,response.getStatus());
				assertNotNull(response.getUpdatedOn());
				assertEquals("test expire", response.getRemarks());
				assertEquals(user.getUsername(), response.getCreatedBy().getUsername());
			}

			//expire client fail
			@Test
			public void expireClientDetailsFailTest() throws Exception {
				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.DISABLED);
				client.setClientStatus("approved");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);
				Mockito.when(clientRepository.save(client)).thenReturn(client);

				Client response = clientServiceImpl.expireClient("1234567890", "test expire fail", "user");

				assertEquals("1234567890",response.getClientId());
				assertEquals("approved",response.getClientStatus());
				assertEquals(Status.DISABLED,response.getStatus());
				assertNull(response.getUpdatedOn());
				assertNull(response.getRemarks());
			}

			//fail test- if client already approved, throw exception
			 @Test
			 public void approvedClientExceptionFailTest(){

				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.ACTIVE);
				client.setClientStatus("approved");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);

			    Assertions.assertThrows(Exception.class, () -> {
			    	clientServiceImpl.approveClientDetails("1234567890", "test", "user");
			    });
			  }

				//fail test- if client DISABLED , throw exception
			 @Test
			 public void disabledClientExceptionFailTest() {

				Client client = new Client();
				client.setClientId("1234567890");
				client.setClientName("counter");
				client.setStatus(Status.DISABLED);
				client.setClientStatus("approval required");

				User user = new User();
				user.setUsername("user");
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");

				Mockito.when(clientRepository.findClientByClientId("1234567890")).thenReturn(client);

			    Assertions.assertThrows(Exception.class, () -> {
			    	clientServiceImpl.approveClientDetails("1234567890", "test", "user");
			    });
			  }

}