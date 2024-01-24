package com.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.msgpack.jackson.dataformat.MessagePackFactory;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.domain.Client;
import com.domain.Control;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.RateTable;
import com.domain.Role;
import com.domain.Services;
import com.domain.SyncTable;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ControlRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RateTableRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.SyncTableRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.ZoneAddressRepo;
import com.services.DataSyncService;
import com.services.ParcelService;
import com.vo.DistrictVo;
import com.vo.DivisionVo;
import com.vo.MasterAddressVo;
import com.vo.ServicesVo;
import com.vo.ThanaVo;
import com.vo.UserVo;
import com.vo.ZoneVo;

@Service
public class DataSyncServiceImpl implements DataSyncService {

	protected Logger log = LoggerFactory.getLogger(DataSyncServiceImpl.class);

	@Autowired
	private ControlRepository controlRepository;

	@Autowired
	private SyncTableRepository syncTableRepository;

	@Autowired
	private ParcelService parcelService;

	/*
	 * @Autowired private ClientService clientService;
	 */

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private RateTableRepository rateTableRepository;

	@Autowired
	private ZoneAddressRepo zoneRepository;

	@Autowired
	private ThanaAddressRepo thanaRepository;

	@Autowired
	private DivisionAddressRepo divisionRepository;

	@Autowired
	private DistrictAddressRepo districtRepository;

	@Autowired
	private SUserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${server.url}")
	private String serverURL;

	@Value("${sync.time}")
	private Long synctime;

	public Control getClientControl() {
		List<Control> controlList = controlRepository.findAll();
		if (controlList.size() > 0)
			return controlList.get(0);
		else
			return new Control();
	}

	private void updateClientControl(Control control, Timestamp serverTimeStamp, Timestamp controlServerTimeStamp) {
		control.setClientMasterDataSyncTimestamp(serverTimeStamp);
		control.setServerMasterDataUpdateTimestamp(controlServerTimeStamp);
		controlRepository.save(control);
	}

	public SyncTable saveUpdateSyncTable(SyncTable syncTable) {
		return syncTableRepository.save(syncTable);
	}

	@Transactional(rollbackFor = Exception.class)
	public long syncMasterData(byte[] bytes, Control control, User loginedUser) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		Map<String, Object> deserialized = objectMapper.readValue(bytes, new TypeReference<Map<String, Object>>() {
		});
		long controlServerMilliseconds = (long) deserialized.get("controlServerTimeStamp");
		long milliseconds = (long) deserialized.get("serverTimeStamp");
		Timestamp serverTimestamp = new Timestamp(milliseconds);
		updateClientControl(control, serverTimestamp, new Timestamp(controlServerMilliseconds));

		List<RateTable> rateTableList = objectMapper.convertValue(deserialized.get("rates"),
				new TypeReference<List<RateTable>>() {
				});

		List<Role> roleList = objectMapper.convertValue(deserialized.get("roles"), new TypeReference<List<Role>>() {
		});

		List<UserVo> userList = objectMapper.convertValue(deserialized.get("users"), new TypeReference<List<UserVo>>() {
		});
		List<User> userDomainList = new ArrayList<User>();
		for (UserVo user : userList) {
			User usernew = new User();
			usernew.setEmail(user.getEmail());
			usernew.setAccountExpired(user.isAccountExpired());
			usernew.setAccountLocked(user.isAccountLocked());
			usernew.setCredentialsExpired(user.isCredentialsExpired());
			usernew.setDob(user.getDob());
			usernew.setEnabled(user.isEnabled());
			usernew.setFirstLogin(user.isFirstLogin());
			usernew.setId(user.getId());
			usernew.setIdentificationId(user.getIdentificationId());
			usernew.setLastLogin(user.getLastLogin());
			usernew.setName(user.getName());
			usernew.setPassword(user.getPassword());
			usernew.setPostalCode(user.getPostalCode());
			usernew.setRmsId(user.getRmsId());
			usernew.setRole(user.getRole());
			usernew.setStatus(user.getStatus());
			usernew.setUsername(user.getUsername());
			userDomainList.add(usernew);
		}

		List<Role> roleList1 = roleRepository.saveAll(roleList);
		List<User> userList1 = userRepository.saveAll(userDomainList);
		if (loginedUser == null) {
			log.debug("\n\nstoring front desk user as created by for master address");
			loginedUser = userList1.get(0);
		}

		List<ZoneVo> zoneList = objectMapper.convertValue(deserialized.get("zone"), new TypeReference<List<ZoneVo>>() {
		});

		List<Zone> zonelist = new ArrayList<Zone>();
		for (ZoneVo zoneVo : zoneList) {
			Zone zone = new Zone();
			zone.setCreatedBy(loginedUser);
			zone.setUpdatedBy(loginedUser);
			zone.setId(zoneVo.getId());
			zone.setStatus(zoneVo.getStatus());
			zone.setCreatedOn(zoneVo.getCreatedOn());
			zone.setUpdatedOn(zoneVo.getUpdatedOn());
			zone.setZone(zoneVo.getZone());
			zonelist.add(zone);
		}

		List<Zone> zoneList1 = zoneRepository.saveAll(zonelist);

		List<DivisionVo> divisionList = objectMapper.convertValue(deserialized.get("division"),
				new TypeReference<List<DivisionVo>>() {
				});
		List<Division> divisionlist = new ArrayList<Division>();
		for (DivisionVo divisionVo : divisionList) {
			Division division = new Division();
			division.setCreatedBy(loginedUser);
			division.setUpdatedBy(loginedUser);
			division.setCreatedOn(divisionVo.getCreatedOn());
			division.setUpdatedOn(divisionVo.getUpdatedOn());
			division.setId(divisionVo.getId());
			division.setStatus(divisionVo.getStatus());
			division.setDivision(divisionVo.getDivision());
			Zone zone = zoneRepository.findZoneById(divisionVo.getZone());
			division.setZone(zone);
			divisionlist.add(division);
		}

		List<Division> divisionList1 = divisionRepository.saveAll(divisionlist);

		List<DistrictVo> districtList = objectMapper.convertValue(deserialized.get("district"),
				new TypeReference<List<DistrictVo>>() {
				});
		List<District> districtlist = new ArrayList<District>();
		for (DistrictVo districtVo : districtList) {
			District district = new District();
			district.setCreatedBy(loginedUser);
			district.setUpdatedBy(loginedUser);
			district.setCreatedOn(districtVo.getCreatedOn());
			district.setUpdatedOn(districtVo.getUpdatedOn());
			district.setId(districtVo.getId());
			district.setStatus(districtVo.getStatus());
			district.setDistrict(districtVo.getDistrict());
			Division division = divisionRepository.findDivisionById(districtVo.getDivision());
			district.setDivision(division);
			districtlist.add(district);
		}

		List<District> districtList1 = districtRepository.saveAll(districtlist);

		List<ThanaVo> thanaList = objectMapper.convertValue(deserialized.get("thana"),
				new TypeReference<List<ThanaVo>>() {
				});

		List<Thana> thanalist = new ArrayList<Thana>();
		for (ThanaVo thanaVo : thanaList) {
			Thana thana = new Thana();
			thana.setCreatedBy(loginedUser);
			thana.setUpdatedBy(loginedUser);
			thana.setCreatedOn(thanaVo.getCreatedOn());
			thana.setUpdatedOn(thanaVo.getUpdatedOn());
			thana.setId(thanaVo.getId());
			thana.setStatus(thanaVo.getStatus());
			District district = districtRepository.findDistrictById(thanaVo.getDistrict());
			thana.setDistrict(district);
			thana.setThana(thanaVo.getThana());
			thanalist.add(thana);
		}

		List<Thana> thanaList1 = thanaRepository.saveAll(thanalist);

		List<MasterAddressVo> masterAddressesList = objectMapper.convertValue(deserialized.get("masterAddress"),
				new TypeReference<List<MasterAddressVo>>() {
				});

		List<MasterAddress> masterAddresslist = new ArrayList<MasterAddress>();
		for (MasterAddressVo masterAddressVo : masterAddressesList) {
			MasterAddress masterAddress = new MasterAddress();
			masterAddress.setCreatedBy(loginedUser);
			masterAddress.setUpdatedBy(loginedUser);
			masterAddress.setDistrict(masterAddressVo.getDistrict());
			masterAddress.setDistrictId(masterAddressVo.getDistrictId());
			masterAddress.setDivision(masterAddressVo.getDivision());
			masterAddress.setDivisionId(masterAddressVo.getDivisionId());
			masterAddress.setId(masterAddressVo.getId());
			masterAddress.setPostalCode(masterAddressVo.getPostalCode());
			masterAddress.setStatus(masterAddressVo.getStatus());
			masterAddress.setSubOffice(masterAddressVo.getSubOffice());
			masterAddress.setThana(masterAddressVo.getThana());
			masterAddress.setThanaId(masterAddressVo.getThanaId());
			masterAddress.setUpdatedOn(masterAddressVo.getUpdatedOn());
			masterAddress.setZone(masterAddressVo.getZone());
			masterAddress.setZoneId(masterAddressVo.getZoneId());
			masterAddresslist.add(masterAddress);
		}

		List<MasterAddress> masterAddressList1 = masterAddressRepository.saveAll(masterAddresslist);

		List<ServicesVo> servicesVoList = objectMapper.convertValue(deserialized.get("services"),
				new TypeReference<List<ServicesVo>>() {
				});

		List<Services> servicesList = new ArrayList<Services>();
		for (ServicesVo servicesVo : servicesVoList) {
			Services services = new Services();
			services.setId(servicesVo.getId());
			services.setServiceName(servicesVo.getServiceName());
			services.setServiceCode(servicesVo.getServiceCode());
			services.setParentServiceId(servicesVo.getParentServiceId());
			services.setCreatedOn(servicesVo.getCreatedOn());
			services.setCreatedBy(loginedUser);
			services.setUpdatedOn(servicesVo.getUpdatedOn());
			services.setUpdatedBy(loginedUser);
			services.setStatus(servicesVo.getStatus());
			servicesList.add(services);
		}

		List<Services> servicesList1 = postalServiceRepository.saveAll(servicesList);

		long updatedRowsClient = 0;

		if (servicesList.size() > 0 || masterAddressesList.size() > 0 || rateTableList.size() > 0 || userList.size() > 0
				|| roleList.size() > 0 || zoneList.size() > 0 || thanaList.size() > 0 || divisionList.size() > 0
				|| districtList.size() > 0) {

			List<RateTable> rateTableList1 = rateTableRepository.saveAll(rateTableList);
			updatedRowsClient = servicesList1.size() + masterAddressList1.size() + rateTableList1.size()
					+ userList1.size() + roleList1.size() + zoneList1.size() + thanaList1.size() + divisionList1.size()
					+ districtList1.size();
		}

		return updatedRowsClient;
	}

	@Override
	public byte[] getMasterDataToSync(Control control, String token) throws Exception {

		Client sysClient = parcelService.getClientDetails();
		Map<String, String> params = new HashMap<>();
		params.put("clientId", sysClient.getClientId());
		if (control.getServerMasterDataUpdateTimestamp() == null) {
			log.debug("Inside NULL block *****");
			params.put("serverTimestamp", "0");
		} else {
			log.debug("Inside else NULL block *****" + control.getServerMasterDataUpdateTimestamp());
			params.put("serverTimestamp", control.getServerMasterDataUpdateTimestamp().getTime() + "");
		}
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();

		RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
				+ machineId.substring(machineId.length() - 38, machineId.length() - 2));

		HttpEntity<String> entity = new HttpEntity<String>(createHeaders(token));
		ResponseEntity<byte[]> bytes = restTemplate.exchange(
				serverURL + "/server/syncData/{clientId}/{serverTimestamp}", HttpMethod.GET, entity, byte[].class,
				params);
		byte[] bytesData = bytes.getBody();
		return bytesData;
	}

	@Override
	public String getUpdatedSyncTable(SyncTable syncTable, String token) throws Exception {
		Client sysClient = parcelService.getClientDetails();
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();
		RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
				+ machineId.substring(machineId.length() - 38, machineId.length() - 2));
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		 */

		HttpEntity<Object> entity = new HttpEntity<Object>(syncTable, createHeaders(token));
		ResponseEntity<SyncTable> response = restTemplate.exchange(serverURL + "/server/dataSyncedSuccess",
				HttpMethod.POST, entity, SyncTable.class);

		// syncTable = restTemplate.postForObject(serverURL+"/server/dataSyncedSuccess",
		// syncTable, SyncTable.class);
		syncTable = response.getBody();
		List<SyncTable> syncTables = syncTableRepository
				.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc("master", sysClient.getClientId());
		SyncTable syncData = syncTables.get(0);
		if (syncTable.getClientId().equals(syncData.getClientId())) {
			/*
			 * if(syncTable.getSyncStartTimestamp().equals(syncData.getSyncStartTimestamp())
			 * ) {
			 */
			syncData.setNoOfRecords(syncTable.getNoOfRecords());
			syncData.setNoOfRecordsUpdated(syncTable.getNoOfRecordsUpdated());
			syncData.setSynceStatus(syncTable.getSynceStatus());
			syncData.setSyncUpdatedTimestamp(syncTable.getSyncUpdatedTimestamp());
			// }

		}
		saveUpdateSyncTable(syncData);

		if (syncData.getSynceStatus().equals("synced successfully")
				|| syncData.getSynceStatus().equals("already synced")) {
			return "success";
		} else {
			return "failure";
		}
	}

	@Override
	public Timestamp getLastUpdatedSyncTime() {
		List<Control> controlList = controlRepository.findAll();
		return controlList.get(0).getClientMasterDataSyncTimestamp();
	}

	public String inLastDay() {
		List<Control> controlList = controlRepository.findAll();
		long lastUpdateDate = (long) 0.0;
		if (controlList.size() > 0)
			lastUpdateDate = controlList.get(0).getClientMasterDataSyncTimestamp().getTime();

		long currentTime = currentTime().getTime();

		if (currentTime - lastUpdateDate >= synctime) {
			return "sync required";
		} else {
		}
		return "sync not required";
	}

	private Timestamp currentTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password)
			throws Exception {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient(clientId, password));

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient(String clientId, String password) throws Exception {
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		// CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		// credentialsProvider.setCredentials(AuthScope.ANY,
		// new UsernamePasswordCredentials(clientId,password));

		HttpClient client = HttpClientBuilder.create()
				// .setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory).build();
		return client;
	}

	private RestTemplate restTemplate(String clientId, String clientToken) throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, clientToken);
		return new RestTemplate(factory);
	}

	// create header for rest call
	private HttpHeaders createHeaders(String jwtToken) {
		return new HttpHeaders() {
			/**
			*
			*/
			private static final long serialVersionUID = 1L;

			{
				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				String authHeader = "Bearer " + jwtToken;
				log.debug("\nauthheader" + authHeader);
				add("Authorization", authHeader);
				// setContentType(MediaType.APPLICATION_OCTET_STREAM);
			}
		};
	}

}
