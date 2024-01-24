package com.services.impl;

import com.constants.Status;
import com.domain.Client;
import com.repositories.ClientRepository;
import com.services.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

	protected Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Value("${clientAccountStatus}")
	private String status;

	@Value("${clientStatus}")
	private String clientstatus;

	@Override
	public Client saveMachineId(Client client) throws Throwable {
		Client c = clientRepository.findClientByClientTokenAndClientStatusAndStatus(client.getClientToken(),clientstatus, Enum.valueOf(Status.class, status));
		//if(!c.equals(null)) {
		if(c != null) {
			if (c.getClientId().equals(client.getClientId()) && c.getStatus().equals(Enum.valueOf(Status.class, status))
						&& (c.getClientStatus().equals("unauthorized"))) {
				log.debug("\nmachine id generated for cleint::" + client.getClientId());
				c.setMachineId(client.getMachineId());
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
				c.setPassword(bc.encode(c.getClientToken() + ";" + client.getMachineId()));
				c.setClientStatus("approval required");
				clientRepository.save(c);
				c.setCreatedBy(null);
				c.setUpdatedBy(null);
				return c;
			} else {
				log.error("Client id does not match with client token in db for Client Token::" + client.getClientToken());
				client.setClientStatus("No Such Active client found!!!");
				return client;
			}
		}
		else {
			log.error("Client id does not match with client token in db for Client Token::" + client.getClientToken());
			client.setClientStatus("No Such Active client found!!!");
			return client;
		}

	}

	@Override
	public Client fetchClientStatus(String clientId) {
		Client c = clientRepository.findClientByClientIdAndStatus(clientId, Status.ACTIVE);
		c.setUpdatedBy(null);
		c.setCreatedBy(null);
		return c;
	}
}
