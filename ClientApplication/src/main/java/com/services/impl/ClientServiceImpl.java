package com.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.net.ssl.SSLContext;

import com.constants.*;
import com.domain.*;
import com.repositories.*;
import com.vo.RateReportData;
import com.vo.RefillAmountRequestVo;
import com.vo.RefillAmountResponseVo;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.services.ClientService;
import com.services.ParcelService;
import com.services.ParcelSyncService;
import com.util.RefillAmountUtils;
import com.vo.UserVo;

@Service
public class ClientServiceImpl implements ClientService {

	protected Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ControlRepository controlRepository;

	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Autowired
	private RateTableRepository rateTableRepository;

	@Autowired
	ParcelSyncService parcelSyncService;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private ZoneAddressRepo zoneRepository;

	@Autowired
	private ThanaAddressRepo thanaRepository;

	@Autowired
	private DivisionAddressRepo divisionRepository;

	@Autowired
	private DistrictAddressRepo districtRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ParcelRepository parcelRepository;

	@Autowired
	private PasswordHistoryRepo passwordHistory;

	@Autowired
	ConfigRepository configRepository;

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
	public Client saveClientDetails(Client client, String userName) throws IOException {

		log.info("inside saveClientDetails");

		User user = sUserRepository.findUserByUsernameAndStatus(userName, Status.ACTIVE);
		client.setCreatedBy(user);

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

		UUID token = UUID.randomUUID();
		String clientToken = token.toString();
		client.setClientToken(clientToken);
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		client.setPassword(bc.encode(clientToken));

		Client c = clientRepository.save(client);
		log.info("Client Details Saved for client::" + c.getClientName() + " for postal code::" + c.getPostalCode());

		return c;
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
	public String refillMeter(double amount) {
		Client client = clientRepository.findAll().get(0);

		ResponseEntity<RefillAmountResponseVo> response = RefillAmountUtils.refillMeter(amount, new String(Base64.getDecoder().decode(client.getNgpId())),
				client.getClientId(), "ITM_IN");
		
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			log.info(response.getBody().toString());
			return "Meter refilled successfully";
		}

		log.error(response.toString());
		return "Error refilling the ngp meter";

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
	public List<Client> getClientList(String clientStatus) {

		if (clientStatus.equals("all")) {
			return clientRepository.findAll();
		} else if (clientStatus.equals("unauthorized")) {
			return clientRepository.findByClientStatusAndStatus(clientStatus, Enum.valueOf(Status.class, status));
		} else if (clientStatus.equals("approval required")) {
			return clientRepository.findByClientStatusAndStatus(clientStatus, Enum.valueOf(Status.class, status));
		} else if (clientStatus.equals("approved")) {
			return clientRepository.findByClientStatusAndStatus(clientStatus, Enum.valueOf(Status.class, status));
		} else if (clientStatus.equals("rejected")) {
			return clientRepository.findByClientStatusAndStatus(clientStatus, Enum.valueOf(Status.class, status));
		} else if (clientStatus.equals("expired")) {
			return clientRepository.findByClientStatusAndStatus(clientStatus, Enum.valueOf(Status.class, status));
		}
		return null;
	}

	@Override
	public Boolean fetchExistingCounterInPostalCode(String clientName, int pinCode) {

		List<Client> counterName = clientRepository.findByPostalCodeAndStatus(pinCode,
				Enum.valueOf(Status.class, "ACTIVE"));
		log.debug("\nchecking if client:: " + clientName + "already exists in pincode::" + pinCode);
		for (int i = 0; i < counterName.size(); i++) {
			if ((counterName.get(i).getClientName().equals(clientName))
					&& (counterName.get(i).getStatus().equals(Enum.valueOf(Status.class, status)))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Client findClientDetail(String clientId) {
		log.debug("Fetching details for client with client id:::" + clientId);
		return clientRepository.findClientByClientId(clientId);
	}

	@Override
	public void approveClientDetails(String clientId, String remarks, String userName) throws Exception {
		Client c = clientRepository.findClientByClientId(clientId);

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
		clientRepository.save(c);
	}

	@Override
	public void rejectClientDetails(String clientId, String remarks, String username) {
		Client c = clientRepository.findClientByClientId(clientId);

		String clientstatus = c.getClientStatus();
		if (clientstatus.equals("approval required") || clientstatus.equals("approved")) {
			c.setClientStatus("rejected");
			c.setRemarks(remarks);
			c.setUpdatedOn(new Date());
			User user = sUserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
			c.setCreatedBy(user);
		}
		clientRepository.save(c);
	}

	@Override
	public void expireClient(String clientId, String username) {
		Client c = clientRepository.findClientByClientId(clientId);

		if (c.getStatus().equals(Enum.valueOf(Status.class, "ACTIVE"))) {
			c.setClientStatus("expired");
			c.setUpdatedOn(new Date());
			User user = sUserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
			c.setCreatedBy(user);
		}
		clientRepository.save(c);
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
	public String checkLocalUserStatus(String cookieToken, User loginedUser) throws Exception {
		log.info("inside implementation of checking user status ");
		User user = sUserRepository.findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(loginedUser.getEmail(),
				Status.ACTIVE, true);
		if (!(user == null)) {

			Client sysClient = parcelService.getClientDetails();

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
			userVo.setEmail(loginedUser.getEmail());
			userVo.setAccountExpired(loginedUser.isAccountExpired());
			userVo.setAccountLocked(loginedUser.isAccountLocked());
			userVo.setCredentialsExpired(loginedUser.isCredentialsExpired());
			userVo.setDob(loginedUser.getDob());
			userVo.setEnabled(loginedUser.isEnabled());
			userVo.setFirstLogin(loginedUser.isFirstLogin());
			userVo.setId(loginedUser.getId());
			userVo.setIdentificationId(loginedUser.getIdentificationId());
			userVo.setLastLogin(loginedUser.getLastLogin());
			userVo.setName(loginedUser.getName());
			userVo.setPassword(loginedUser.getPassword());
			userVo.setPostalCode(loginedUser.getPostalCode());
			userVo.setRmsId(loginedUser.getRmsId());
			userVo.setRole(loginedUser.getRole());
			userVo.setUsername(loginedUser.getUsername());
			userVo.setStatus(loginedUser.getStatus());
			userVo.setAddressLine1(loginedUser.getAddressLine1());
			userVo.setAddressLine2(loginedUser.getAddressLine2());
			userVo.setMobileNumber(loginedUser.getMobileNumber());
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
			usernew.setAddressLine1(uservo.getAddressLine1());
			usernew.setAddressLine2(uservo.getAddressLine2());
			usernew.setMobileNumber(uservo.getMobileNumber());

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

	@Override
	public Boolean checkIfOtherCounterAlreadyExists() {
		List<String> clientstatuses = new ArrayList<String>();
		clientstatuses.add("approval required");
		clientstatuses.add("approved");

		long clientlist = clientRepository.countByClientStatusInAndStatus(clientstatuses, Status.ACTIVE);
		if (clientlist == 0)
			return false;
		else
			return true;
	}

	@Override
	public void generateRateReportInPdf() {
		// TODO: 1. Location Dependency
		// 2. Location, value and weight Dependency
		// 3. Location and value Dependency
		// 4. Value and Weight Dependency
		// 5. All location dependency except local and intercity in location and weight
		// dependency
		List<RateReportData> reportVos = new ArrayList<>();
		List<Long> serviceIds = postalServiceRepository.findDistinctServiceName();
		List<RateTable> rateTables = rateTableRepository.findByRateServiceCategoryServiceIdInAndStatus(serviceIds,
				Status.ACTIVE, Sort.by("RateServiceCategoryServiceId").ascending().and(Sort.by("id")));
		List<Control> control = controlRepository.findAll();

		for (RateTable rateTable : rateTables) {
			RateReportData rateReportData = new RateReportData();

			// rateReportData.setLastSync(control.get(0).getClientMasterDataSyncTimestamp());

			if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

				Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
						.orElse(null);
				rateReportData.setServiceName(s1.getServiceName());

			} else {
				Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId())
						.orElse(null);
				rateReportData.setServiceName(s2.getServiceName());

				Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
						.orElse(null);
				rateReportData.setSubService(s3.getServiceName());
			}

			if (rateTable.getRateServiceCategory().getPriceType().equals(PriceType.FLAT)) {

				if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

					Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
							.orElse(null);
					rateReportData.setServiceName(s1.getServiceName());

				} else {
					Services s2 = postalServiceRepository
							.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
					rateReportData.setServiceName(s2.getServiceName());

					Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
							.orElse(null);
					rateReportData.setSubService(s3.getServiceName());
				}

				rateReportData.setPrice(rateTable.getRateServiceCategory().getPrice());
			} else if (rateTable.getRateServiceCategory().getPriceType().equals(PriceType.VARIABLE)) {

				if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

					Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
							.orElse(null);
					rateReportData.setServiceName(s1.getServiceName());

				} else {
					Services s2 = postalServiceRepository
							.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
					rateReportData.setServiceName(s2.getServiceName());

					Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId())
							.orElse(null);
					rateReportData.setSubService(s3.getServiceName());
				}

				if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getWeightDependency()
								.equals(WeightDependency.NOT_APPLICABLE)) {

					log.info("Location, Weight dependency exist");

					if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.LOCAL)) {
						rateReportData.setServiceLocationDependency("LOCAL");
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.INTERCITY)) {
						rateReportData.setServiceLocationDependency("INTERCITY");
					}

					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						rateReportData.setServiceWeightDependency(
								"Over " + rateTable.getWeightWiseRate().getWeightStartRange() + " gm not exceeding "
										+ rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						float basePrice = rateTable.getWeightWiseRate().getBasePrice();
						float price = rateTable.getWeightWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceWeightDependency(
									"Up to " + rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
							rateReportData.setPrice(price);
						} else {
							float weightFractionFactor = rateTable.getWeightWiseRate().getWeightFractionFactor();
							rateReportData.setServiceWeightDependency("Each " + weightFractionFactor
									+ " gm in excess of " + rateTable.getWeightWiseRate().getWeightStartRange()
									+ " gm up to " + rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
							rateReportData.setPrice(price);
						}
					}

				} else if (!rateTable.getRateServiceCategory().getWeightDependency()
						.equals(WeightDependency.NOT_APPLICABLE)) {
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						rateReportData.setServiceWeightDependency(
								"Over " + rateTable.getWeightWiseRate().getWeightStartRange() + " gm not exceeding "
										+ rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						float basePrice = rateTable.getWeightWiseRate().getBasePrice();
						float price = rateTable.getWeightWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceWeightDependency(
									"Up to " + rateTable.getWeightWiseRate().getWeightEndRange() + "gm");
							rateReportData.setPrice(price);
						} else {
							float weightFractionFactor = rateTable.getWeightWiseRate().getWeightFractionFactor();
							rateReportData.setServiceWeightDependency("Each " + weightFractionFactor
									+ " gm in excess of " + rateTable.getWeightWiseRate().getWeightStartRange()
									+ " gm up to " + rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
							rateReportData.setPrice(price);
						}
					}
				} else if (!rateTable.getRateServiceCategory().getValueDependency()
						.equals(ValueDependency.NOT_APPLICABLE)) {
					log.info("Value dependency exist");
					if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						rateReportData.setServiceValueDependency("Over Rs."
								+ rateTable.getParcelValueWiseRate().getValueStartRange() + " not exceeding Rs."
								+ rateTable.getParcelValueWiseRate().getValueUpToRange());
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getValueDependency()
							.equals(ValueDependency.MULTIPLIER)) {
						float basePrice = rateTable.getParcelValueWiseRate().getBasePrice();
						float price = rateTable.getParcelValueWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceValueDependency(
									"Up to Rs." + rateTable.getParcelValueWiseRate().getValueUpToRange());
							rateReportData.setPrice(price);
						} else {
							float valueFractionFactor = rateTable.getParcelValueWiseRate().getValueFraction();
							rateReportData.setServiceValueDependency("Each Rs." + valueFractionFactor
									+ " in excess of Rs." + rateTable.getParcelValueWiseRate().getValueStartRange()
									+ " up to Rs." + rateTable.getParcelValueWiseRate().getValueUpToRange());
							rateReportData.setPrice(price);
						}
					}
				} else if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)) {
					log.info("Location dependency exist");

				}
			}
			reportVos.add(rateReportData);
		}

		try {
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(".//Report//Rate_Table_Report.jrxml");
			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportVos);
			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("rateTableReportList", jrBeanCollectionDataSource);
			Date date = new Date();
			date.setTime(control.get(0).getClientMasterDataSyncTimestamp().getTime());
			String formattedDate = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa").format(date);
			parameters.put("lastSync", formattedDate);
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, ".//Report//Rate_Table_Report.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void emptyDatabase() {

		rateTableRepository.deleteAll();
		postalServiceRepository.deleteAll();
		masterAddressRepository.deleteAll();
		thanaRepository.deleteAll();
		parcelRepository.deleteAll();
		configRepository.deleteAll();
		passwordHistory.deleteAll();
		sUserRepository.deleteAll();
		roleRepository.deleteAll();
		controlRepository.deleteAll();

	}
}
