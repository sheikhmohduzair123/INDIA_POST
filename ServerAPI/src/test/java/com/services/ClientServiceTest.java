package com.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.Application;
import com.constants.Status;
import com.domain.Client;
import com.repositories.ClientRepository;
import com.repositories.SUserRepository;
import com.services.ClientService;
import com.services.impl.ClientServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ClientServiceTest {

	@Mock
	ClientService clientService;

	@InjectMocks
	ClientServiceImpl clientServiceImpl;
	
	@Mock
	SUserRepository repository;
	
	@Mock
	ClientRepository clientRepo;
	
	@Mock
	Client client;
	
	@Value("${clientAccountStatus}")
	private String status;

	@Value("${clientStatus}")
	private String clientstatus;
	
	@Before
	public void init() {
		
		
		client = new Client();
		client.setClientId("11001581656985");
		client.setStatus(Enum.valueOf(Status.class, status));
		client.setClientToken("738bcd06-1677-4835-80a3-a5c790565ce4");
		client.setClientStatus(clientstatus);
		client.setClientName(null);
		
	}

	
	//For save details of client
	@Test
	public void saveMachineIdTest() throws Throwable {
		
				Client formData = new Client();
				formData.setClientId("11001581656985");
				formData.setClientToken("738bcd06-1677-4835-80a3-a5c790565ce4");
				
				ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");
				
				ReflectionTestUtils.setField(clientServiceImpl,"clientstatus","unauthorized");
				
				Mockito.when(clientRepo.findClientByClientTokenAndClientStatusAndStatus(formData.getClientToken(), clientstatus, Enum.valueOf(Status.class, status))).thenReturn(client);
				Mockito.when(clientRepo.save(client)).thenReturn(client);
				
				Assert.assertEquals(client , clientServiceImpl.saveMachineId(formData));
				
	}
	
	@Test
	public void saveMachineIdElseConditionTest() throws Throwable {
		
		Client formData = new Client();
		formData.setClientId("11001581656985");
		formData.setClientToken("738bcd06-1677-4835-80a3-a5c790565ce4");
				
		Client client1 = new Client();
				
		client1.setClientId("11001581656985");
		client1.setStatus(Enum.valueOf(Status.class, status));
		client1.setClientToken("738bcd06-1677-4835-80a3-a5c790565ce4");
		client1.setClientStatus("approved");
		client1.setClientName(null);
				
		ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");
				
		ReflectionTestUtils.setField(clientServiceImpl,"clientstatus","approved");
				
		Mockito.when(clientRepo.findClientByClientTokenAndClientStatusAndStatus(formData.getClientToken(), "approved", Enum.valueOf(Status.class, status))).thenReturn(client1);
				
		Assert.assertEquals(formData , clientServiceImpl.saveMachineId(formData));
				
	}
	
	@Test
	public void saveMachineIdElseConditionNoClientDataFoundTest() throws Throwable {
		
		Client formData = new Client();
		formData.setClientId("11001581656985");
		formData.setClientToken("738bcd06-1677-4835-80a3-a5c790565ce4");
				
		ReflectionTestUtils.setField(clientServiceImpl,"status","ACTIVE");
				
		ReflectionTestUtils.setField(clientServiceImpl,"clientstatus","unauthorized");
				
		Mockito.when(clientRepo.findClientByClientTokenAndClientStatusAndStatus(formData.getClientToken(), clientstatus, Enum.valueOf(Status.class, status))).thenReturn(null);
				
		Assert.assertEquals(formData , clientServiceImpl.saveMachineId(formData));
	}
	
	@Test
	public void fetchClientStatusTest() {

		String clientId = "11001581656985";
		Mockito.when(clientRepo.findClientByClientIdAndStatus(clientId, Status.ACTIVE)).thenReturn(client);
		Assert.assertEquals(client , clientServiceImpl.fetchClientStatus(clientId));
	}

}
	
	

	
	
	

