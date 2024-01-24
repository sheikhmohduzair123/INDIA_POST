package com.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Base64;  

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.SUserRepository;
import com.services.ClientService;
import com.services.ParcelService;
import com.vo.ClientVo;
import com.vo.UserVo;

@Service
public class ClientServiceImpl implements ClientService {

	protected Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private ParcelService parcelService;

	@Value("${server.url}")
	private String serverURL;

	@Value("${clientAccountStatus}")
	private String status;

	@Value("${clientToken}")
	private String clientToken;

	@Value("${clientStatus}")
	private String clientstatus;

	@Override
	public boolean getClientDetailsLocal() {
		List<Client> c = clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class, status));
		if (c.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ClientVo saveClientDetails(Client client, String userName) throws IOException {

		log.info("inside saveClientDetails");

		client.setClientName(client.getClientName().trim());

		User user = sUserRepository.findUserByUsernameAndStatus(userName, Status.ACTIVE);
		client.setCreatedBy(user);

		long cnt = clientRepository.countByPostalCode(client.getPostalCode());
		client.setPostalCounter((int) (cnt + 1));

		client.setStatus(Enum.valueOf(Status.class, status));
		client.setClientStatus(clientstatus);
		Date date = new Date();
		long time = date.getTime();
		int ts = (int) (time / 1000);
		client.setCreatedOn(date);

		String d = String.valueOf(client.getPostalCode());
		String t = String.valueOf(ts);
		String data = d + t;
		client.setClientId(data);

		String ngpid = client.getNgpId();
		byte[] ngpIdBytes =ngpid.getBytes();
		String ngpId = Base64.getEncoder().encodeToString(ngpIdBytes);
		client.setNgpId(ngpId);
		System.out.println("-----Encoded NgpId :"+ngpId+"---------");

		UUID token = UUID.randomUUID();
		String clientToken = token.toString();
		client.setClientToken(clientToken);
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		client.setPassword(bc.encode(clientToken));

		Client c = clientRepository.save(client);
		log.info("Client Details Saved for client::" + c.getClientName() + " for postal code::" + c.getPostalCode());

		ClientVo clientVo = new ClientVo();
		clientVo.setClientToken(c.getClientToken());
		clientVo.setClientId(c.getClientId());
		return clientVo;
	}

	@Override
	public Client generateMachineId(String clientId, String clientToken) throws Throwable {

		Client client = new Client();
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();
		log.debug("\nmachine id generated for cleint::" + clientId);
		client.setClientId(clientId);
		client.setClientToken(clientToken);
		client.setMachineId(machineId.substring(machineId.length() - 38, machineId.length() - 2));
		return client;
	}

	@Override
	public String saveClientDetailsLocal(Client client) {
		if (!client.getClientStatus().equals("No Such Active client found!!!")) {
			client.setMachineId("");
			client.setCreatedBy(null);
			client.setUpdatedBy(null);
			clientRepository.save(client);
			return client.getClientStatus();
		} else {
			log.error("Client id and Client Secret not found on server!!");
			return client.getClientStatus();
		}
	}

	@Override
	public String saveUserDetailsLocal(User user) {
		sUserRepository.save(user);
		if (user.isEnabled()) {
			return "active";
		} else {
			return "disabled";
		}
	}

	@Override
	public List<ClientVo> getClientList(String clientStatus) {
		List<ClientVo> clientVoList = new ArrayList<ClientVo>();
		if (clientStatus.equals("all")) {
			List<Client> clientList = clientRepository.findAll();
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		} else if (clientStatus.equals("unauthorized")) {
			List<Client> clientList = clientRepository.findByClientStatusAndStatus(clientStatus,
					Enum.valueOf(Status.class, status));
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		} else if (clientStatus.equals("approval required")) {
			List<Client> clientList = clientRepository.findByClientStatusAndStatus(clientStatus,
					Enum.valueOf(Status.class, status));
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		} else if (clientStatus.equals("approved")) {
			List<Client> clientList = clientRepository.findByClientStatusAndStatus(clientStatus,
					Enum.valueOf(Status.class, status));
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		} else if (clientStatus.equals("rejected")) {
			List<Client> clientList = clientRepository.findByClientStatusAndStatus(clientStatus,
					Enum.valueOf(Status.class, status));
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		} else if (clientStatus.equals("expired")) {
			List<Client> clientList = clientRepository.findByClientStatusAndStatus(clientStatus,
					Enum.valueOf(Status.class, status));
			for (Client client : clientList) {
				ClientVo clientVo = new ClientVo();
				clientVo.setStatus(client.getStatus());
				clientVo.setClientStatus(client.getClientStatus());
				clientVo.setClientId(client.getClientId());
				clientVo.setClientName(client.getClientName());
				clientVo.setSubOffice(client.getSubOffice());
				clientVo.setThana(client.getThana());
				clientVo.setDistrict(client.getDistrict());
				clientVo.setRemarks(client.getRemarks());
				clientVoList.add(clientVo);
			}
			return clientVoList;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingCounterInPostalCode(String clientName, int pinCode) {

		List<Client> counterName = clientRepository.findByPostalCodeAndStatus(pinCode,
				Enum.valueOf(Status.class, "ACTIVE"));
		log.debug("\nchecking if client:: " + clientName + "already exists in pincode::" + pinCode);
		for (int i = 0; i < counterName.size(); i++) {
			if ((counterName.get(i).getClientName().equalsIgnoreCase(clientName))
					&& (counterName.get(i).getStatus().equals(Enum.valueOf(Status.class, status)))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ClientVo findClientDetail(String clientId) {

		log.debug("Fetching details for client with client id:::" + clientId);
		Client client = clientRepository.findClientByClientId(clientId);
		ClientVo clientVo = new ClientVo();
		clientVo.setClientToken(client.getClientToken());
		clientVo.setStatus(client.getStatus());
		clientVo.setClientStatus(client.getClientStatus());
		clientVo.setClientId(client.getClientId());
		clientVo.setClientName(client.getClientName());
		clientVo.setSubOffice(client.getSubOffice());
		clientVo.setThana(client.getThana());
		clientVo.setDistrict(client.getDistrict());
		clientVo.setDivision(client.getDivision());
		clientVo.setZone(client.getZone());
		clientVo.setPostalCode(client.getPostalCode());
		clientVo.setRemarks(client.getRemarks());
		return clientVo;
	}

	@Override
	public Client approveClientDetails(String clientId, String remarks, String userName) throws Exception {
		Client c = clientRepository.findClientByClientId(clientId);
		long clientlist = clientRepository.countByMachineIdAndClientStatusAndStatus(c.getMachineId(), "approved",
				Status.ACTIVE);

		if (clientlist == 0) {
			if ((c.getStatus().equals(Enum.valueOf(Status.class, status)))
					&& (c.getClientStatus().equals("approval required"))) {
				c.setClientStatus("approved");
				c.setRemarks(remarks);
				c.setUpdatedOn(new Date());
				User user = sUserRepository.findUserByUsernameAndStatus(userName, Status.ACTIVE);
				c.setCreatedBy(user);
				log.debug("Approved client::" + clientId);
			} else {
				throw new Exception();
			}
			Client updated_client = clientRepository.save(c);
			return updated_client;
		} else {
			log.info(
					"Counter already registered on this machine. Cannot register multiple counters on same machine (ClientId:  "
							+ clientId + ")");
		}
		return c;
	}

	@Override
	public Client rejectClientDetails(String clientId, String remarks, String username) {
		Client c = clientRepository.findClientByClientId(clientId);

		String clientstatus = c.getClientStatus();
		if (clientstatus.equals("approval required") || clientstatus.equals("approved")) {
			c.setClientStatus("rejected");
			c.setRemarks(remarks);
			c.setUpdatedOn(new Date());
			User user = sUserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
			c.setCreatedBy(user);
		}
		Client updateClient = clientRepository.save(c);
		return updateClient;
	}

	@Override
	public Client expireClient(String clientId, String remarks, String username) {
		Client c = clientRepository.findClientByClientId(clientId);
		if (c.getStatus().equals(Enum.valueOf(Status.class, "ACTIVE"))) {
			c.setClientStatus("expired");
			c.setUpdatedOn(new Date());
			c.setRemarks(remarks);
			User user = sUserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
			c.setCreatedBy(user);
		}
		Client client = clientRepository.save(c);
		return client;
	}

	@Override
	public void updateLocalClientStatus(Client client) {
		if (client.getClientStatus().equals("approved")) {
			log.debug("Local client status updated to approved!!");
			clientRepository.save(client);
		} else {
			log.error("Local client status not updated!!");
		}

	}

	// to check client status of local client
	@Override
	public String checkLocalClientStatus(String cookieToken) throws Exception {

		log.info("inside implementation of checking client status");
		List<Client> client_list = clientRepository.findAll();
		if (client_list.size() == 0)
			return "new machine";

		List<String> clientStauses = new ArrayList<>();
		clientStauses.add("approval required");
		clientStauses.add("approved");
		List<Client> c = clientRepository.findByClientStatusInAndStatus(clientStauses,
				Enum.valueOf(Status.class, status));
		if (c.size() > 0) {
			if (c.get(0).getClientStatus().equals("approval required")
					|| c.get(0).getClientStatus().equals("approved")) {
				String machineId = "";
				String command = "wmic csproduct get UUID";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					machineId += line;
				}
				input.close();
				RestTemplate restTemplate = restTemplate(c.get(0).getClientId(), c.get(0).getClientToken() + ";"
						+ machineId.substring(machineId.length() - 38, machineId.length() - 2));
				Client client = restTemplate.postForObject(serverURL + "/server/getServerClientDetails",
						new HttpEntity<Object>(c.get(0), createHeaders(cookieToken)), Client.class);

				return saveClientDetailsLocal(client);
			} else
				return c.get(0).getClientStatus();
		}

		return "no client commissioned";
	}

	// to check user status of local client
	@Override
	public String checkLocalUserStatus(String cookieToken) throws Exception {
		log.info("inside implementation of checking user status ");

		List<User> userlist = sUserRepository.findUserByEnabledAndStatus(true, Status.ACTIVE);
		Client sysClient = parcelService.getClientDetails();
		if (userlist.size() > 0) {
			String machineId = "";
			String command = "wmic csproduct get UUID";
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				machineId += line;
			}
			input.close();
			RestTemplate restTemplate = new RestTemplate(
					getClientHttpRequestFactory(sysClient.getClientId(), sysClient.getClientToken() + ";"
							+ machineId.substring(machineId.length() - 38, machineId.length() - 2)));
			// User u = restTemplate.postForObject(serverURL +
			// "/server/getServerUserDetails", userlist.get(0),
			// User.class);

			UserVo userVo = new UserVo();
			userVo.setEmail(userlist.get(0).getEmail());
			userVo.setAccountExpired(userlist.get(0).isAccountExpired());
			userVo.setAccountLocked(userlist.get(0).isAccountLocked());
			userVo.setCredentialsExpired(userlist.get(0).isCredentialsExpired());
			userVo.setDob(userlist.get(0).getDob());
			userVo.setEnabled(userlist.get(0).isEnabled());
			userVo.setFirstLogin(userlist.get(0).isFirstLogin());
			userVo.setId(userlist.get(0).getId());
			userVo.setIdentificationId(userlist.get(0).getIdentificationId());
			userVo.setLastLogin(userlist.get(0).getLastLogin());
			userVo.setName(userlist.get(0).getName());
			userVo.setPassword(userlist.get(0).getPassword());
			userVo.setPostalCode(userlist.get(0).getPostalCode());
			userVo.setRmsId(userlist.get(0).getRmsId());
			userVo.setRole(userlist.get(0).getRole());
			userVo.setUsername(userlist.get(0).getUsername());
			userVo.setStatus(userlist.get(0).getStatus());

			ResponseEntity<UserVo> u = restTemplate.exchange(serverURL + "/server/getServerUserDetails",
					HttpMethod.POST, new HttpEntity<Object>(userVo, createHeaders(cookieToken)), UserVo.class);
			UserVo uservo = u.getBody();

			User usernew = new User();
			usernew.setEmail(uservo.getEmail());
			usernew.setAccountExpired(uservo.isAccountExpired());
			usernew.setAccountLocked(uservo.isAccountLocked());
			usernew.setCredentialsExpired(uservo.isCredentialsExpired());
			usernew.setDob(uservo.getDob());
			usernew.setEnabled(uservo.isEnabled());
			usernew.setFirstLogin(uservo.isFirstLogin());
			usernew.setId(uservo.getId());
			usernew.setIdentificationId(uservo.getIdentificationId());
			usernew.setLastLogin(uservo.getLastLogin());
			usernew.setName(uservo.getName());
			usernew.setPassword(uservo.getPassword());
			usernew.setPostalCode(uservo.getPostalCode());
			usernew.setRmsId(uservo.getRmsId());
			usernew.setRole(uservo.getRole());
			usernew.setUsername(uservo.getUsername());
			usernew.setStatus(uservo.getStatus());

			return saveUserDetailsLocal(usernew);
		}
		return "disabled";
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password)
			throws Exception {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient(clientId, password));

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient(String clientId, String paswword) throws Exception {
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		// CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		/*
		 * credentialsProvider.setCredentials(AuthScope.ANY, new
		 * UsernamePasswordCredentials(clientId,paswword));
		 */

		HttpClient client = HttpClientBuilder
				.create()
				// .setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	private RestTemplate restTemplate(String clientId, String paswword) throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, paswword);
		return new RestTemplate(factory);
	}

	@Override
	public String checkLocalClientOnlyStatus() {
		log.info("inside implementation of checking client status");
		List<String> clientStatuses = new ArrayList<>();
		clientStatuses.add("unauthorized");
		clientStatuses.add("rejected");
		clientStatuses.add("expired");
		clientStatuses.add("approved");
		List<Client> c = clientRepository.findByClientStatusInAndStatus(clientStatuses,
				Enum.valueOf(Status.class, status));
		if (c.size() > 0) {
			return c.get(0).getClientStatus();
		}
		return "no client commissioned";
	}

	@Override
	public String generatePassword(String clientId) throws IOException {
		Client client = clientRepository.findClientByClientId(clientId);
		if (client != null) {
			String command = "wmic csproduct get UUID";
			String machineId = "";
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				machineId += line;
			}
			input.close();
			client.setClientId(clientId);
			client.setClientStatus("approved");
			client.setClientToken(clientToken);
			client.setUpdatedOn(new Date());
			BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
			client.setPassword(bc
					.encode(clientToken + ';' + machineId.substring(machineId.length() - 38, machineId.length() - 2)));
			clientRepository.save(client);
			log.debug("\npassword generated for client::" + clientId);
			return client.getClientStatus();
		} else
			return "No Client Found with Given Client Id";
	}

	// create header for rest call
	private HttpHeaders createHeaders(String jwtToken) {
		return new HttpHeaders() {
			{
				log.info("creating header for rest call auth ");
				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				String authHeader = "Bearer " + jwtToken;
				add("Authorization", authHeader);
			}
		};
	}

}
