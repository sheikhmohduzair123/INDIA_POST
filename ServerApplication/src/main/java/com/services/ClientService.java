package com.services;

import java.io.IOException;
import java.util.List;

import com.constants.Status;
import com.domain.Client;
import com.domain.User;
import com.vo.ClientVo;


public interface ClientService {

	ClientVo saveClientDetails(Client client, String userName) throws IOException;

	Client generateMachineId(String clientId, String clientToken) throws IOException, Throwable;

	List<ClientVo> getClientList(String string);

	Boolean fetchExistingCounterInPostalCode(String clientName, int pinCode);

	String saveClientDetailsLocal(Client client);

	boolean getClientDetailsLocal();

	ClientVo findClientDetail(String clientId);

	Client approveClientDetails(String clientId, String remarks, String userName) throws Exception;

	Client rejectClientDetails(String clientId, String remarks, String username);

	Client expireClient(String clientId,String remarks, String username);

	void updateLocalClientStatus(Client client);

	String checkLocalClientStatus(String cookieToken) throws Exception;

	String checkLocalUserStatus(String cookieToken) throws IOException, Exception;

	String saveUserDetailsLocal(User user);

	String checkLocalClientOnlyStatus();

	String generatePassword(String clientId) throws IOException;

}
