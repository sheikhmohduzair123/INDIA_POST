package com.services;

import com.domain.Client;

import java.io.IOException;
import java.util.List;


public interface ClientService {
	Client saveMachineId(Client client) throws Throwable;

	Client fetchClientStatus(String clientId);

}
