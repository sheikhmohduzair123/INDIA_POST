package com.services;

import java.io.IOException;
import java.util.List;

import com.constants.Status;
import com.domain.Client;
import com.domain.User;

public interface ClientService {

	Client saveClientDetails(Client client, String userName) throws IOException;

	Client generateMachineId(String clientId, String clientToken) throws IOException, Throwable;

	List<Client> getClientList(String string);

	Boolean fetchExistingCounterInPostalCode(String clientName, int pinCode);

	String saveClientDetailsLocal(Client client);

	boolean getClientDetailsLocal();

	Client findClientDetail(String clientId);

	void approveClientDetails(String clientId, String remarks, String userName) throws Exception;

	void rejectClientDetails(String clientId, String remarks, String username);

	void expireClient(String clientId, String username);

	void updateLocalClientStatus(Client client);

	String checkLocalClientStatus(String cookieToken) throws Exception;

	String checkLocalUserStatus(String cookieToken, User loginedUser) throws IOException, Exception;

	String saveUserDetailsLocal(User user);

	String refillMeter(double amount);

	String checkLocalClientOnlyStatus();

	String generatePassword(String clientId) throws IOException;

	Boolean checkIfOtherCounterAlreadyExists();

	void generateRateReportInPdf();

	void emptyDatabase();
}
