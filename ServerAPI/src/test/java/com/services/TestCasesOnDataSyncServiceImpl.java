package com.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.Status;
import com.controllers.ParcelRateController;
import com.domain.Client;
import com.domain.Control;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RateTable;
import com.domain.Role;
import com.domain.Services;
import com.domain.SyncTable;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.repositories.ControlRepository;
import com.repositories.DistrictRepository;
import com.repositories.DivisionRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RateTableRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.SyncTableRepository;
import com.repositories.ThanaRepository;
import com.repositories.ZoneRepository;
import com.services.impl.DataSyncServiceImpl;
import com.vo.DistrictVo;
import com.vo.DivisionVo;
import com.vo.MasterAddressVo;
import com.vo.ServicesVo;
import com.vo.ThanaVo;
import com.vo.UserVo;
import com.vo.ZoneVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class TestCasesOnDataSyncServiceImpl {

	@InjectMocks
	DataSyncServiceImpl dataSyncServiceImpl;

	@Mock
	RateTableRepository rateTableRepository;

	@Mock
	MasterAddressRepository masterAddressRepository;

	@Mock
	ZoneRepository zoneRepository;

	@Mock
	DivisionRepository divisionRepository;

	@Mock
	DistrictRepository districtRepository;

	@Mock
	ThanaRepository thanaRepository;

	@Mock
	PostalServiceRepository postalServiceRepository;

	@Mock
	SyncTableRepository syncTableRepository;

	@Mock
	ControlRepository controlRepository;

	@Mock
	SUserRepository userRepository;

	@Mock
	ClientRepository clientRepository;

	@Mock
	RoleRepository roleRepository;

	Control control;
	Client client;
	List<Services> postalServices;
	Services services1;
	Services services2;
	Role roleid_User;
	List<User> userList;
	User user1;
	List<Role> roleList;
	Role role1;
	List<RateTable> rateTablesList;
	RateTable rateTable;
	List<Zone> zoneList;
	Zone zone1;
	List<Division> divisionList;
	Division division1;
	List<District> districtList;
	District district1;
	List<Thana> thanaList;
	Thana thana1;
	List<MasterAddress> masterAddressesList;
	MasterAddress masterAddress1;
	SyncTable synctable;
	List<SyncTable> syncTableList;

	java.sql.Timestamp timestamp;
	long serverTimestamp;
	DateFormat dateFormat;

	@Before
	public void init() {

		timestamp = java.sql.Timestamp.valueOf("2020-02-25 10:10:10.0");

		control = new Control();
		control.setId(2l);
		control.setServerMasterDataUpdateTimestamp(timestamp);
		control.setStatus(Status.ACTIVE);

		client = new Client();
		client.setId(4l);
		client.setClientId("12121591173558");
		client.setClientStatus("approved");
		client.setStatus(Status.ACTIVE);
		client.setPostalCode(1212);

		postalServices = new ArrayList<Services>();
		services1 = new Services();
		services1.setId(1l);
		services1.setParentServiceId(null);
		services1.setServiceCode("GE");
		services1.setServiceName("Guaranteed Express Post");
		services1.setStatus(Status.ACTIVE);
		services1.setUpdatedOn(timestamp);
		postalServices.add(services1);

		services2 = new Services();
		services2.setId(2l);
		services2.setParentServiceId(1l);
		services2.setServiceCode("GL");
		services2.setServiceName("General Letter Postage");
		services2.setStatus(Status.ACTIVE);
		services2.setUpdatedOn(timestamp);
		postalServices.add(services2);

		roleid_User = new Role();
		roleid_User.setId(3);
		roleid_User.setStatus(Status.ACTIVE);
		roleid_User.setDisplayName("Front Desk User");
		roleid_User.setName("ROLE_FRONT_DESK_USER");

		userList = new ArrayList<User>();
		user1 = new User();
		user1.setId(1);
		user1.setName("XYZ");
		user1.setPostalCode(1212l);
		user1.setEmail("abc@gmail.com");
		user1.setAccountExpired(false);
		user1.setAccountLocked(false);
		user1.setCredentialsExpired(false);
		user1.setDob(new Date());
		user1.setEnabled(true);
		user1.setFirstLogin(false);
		user1.setIdentificationId("121");
		user1.setLastLogin(null);
		user1.setPassword("111111");
		user1.setRmsId(null);
		user1.setRole(roleid_User);
		user1.setUsername(user1.getEmail());
		user1.setUpdatedOn(timestamp);
		userList.add(user1);

		roleList = new ArrayList<Role>();
		role1 = new Role();
		role1.setId(1);
		role1.setStatus(Status.ACTIVE);
		role1.setDisplayName("Front Desk User");
		role1.setName("ROLE_FRONT_DESK_USER");
		role1.setUpdatedOn(timestamp);
		roleList.add(role1);

		rateTablesList = new ArrayList<RateTable>();
		rateTable = new RateTable();
		rateTable.setId(1l);
		rateTable.setStatus(Status.ACTIVE);
		rateTable.setUpdatedOn(timestamp);
		rateTablesList.add(rateTable);

		zoneList = new ArrayList<Zone>();
		zone1 = new Zone();
		zone1.setId(1l);
		zone1.setZone("zoneA");
		zone1.setStatus(Status.ACTIVE);
		zone1.setUpdatedOn(timestamp);
		zoneList.add(zone1);

		divisionList = new ArrayList<Division>();
		division1 = new Division();
		division1.setId(1l);
		division1.setDivision("divisionA");
		division1.setStatus(Status.ACTIVE);
		division1.setZone(zone1);
		division1.setUpdatedOn(timestamp);
		divisionList.add(division1);

		districtList = new ArrayList<District>();
		district1 = new District();
		district1.setId(1l);
		district1.setDistrict("DistrictA");
		district1.setStatus(Status.ACTIVE);
		district1.setDivision(division1);
		district1.setUpdatedOn(timestamp);
		districtList.add(district1);

		thanaList = new ArrayList<Thana>();
		thana1 = new Thana();
		thana1.setId(1l);
		thana1.setThana("ThanaA");
		thana1.setStatus(Status.ACTIVE);
		thana1.setDistrict(district1);
		thana1.setUpdatedOn(timestamp);
		thanaList.add(thana1);

		masterAddressesList = new ArrayList<MasterAddress>();
		masterAddress1 = new MasterAddress();
		masterAddress1.setId(1l);
		masterAddress1.setSubOffice("masterAddressA");
		masterAddress1.setStatus(Status.ACTIVE);
		masterAddress1.setPostalCode(1212);
		masterAddress1.setZone(zone1.getZone());
		masterAddress1.setZoneId(zone1.getId());
		masterAddress1.setDivision(division1.getDivision());
		masterAddress1.setDivisionId(division1.getId());
		masterAddress1.setDistrict(district1.getDistrict());
		masterAddress1.setDistrictId(district1.getId());
		masterAddress1.setThana(thana1.getThana());
		masterAddress1.setThanaId(thana1.getId());
		masterAddress1.setUpdatedOn(timestamp);
		masterAddressesList.add(masterAddress1);

		synctable = new SyncTable();
		syncTableList = new ArrayList<SyncTable>();
		dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

	}

	@Test
	public void syncMasterDataOnClientPositiveTestCaseIfPartWhenTimestampIsZero()
			throws JsonParseException, JsonMappingException, IOException {

		synctable.setSynceStatus("pending");
		synctable.setSyncType("master");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(client.getClientId());
		synctable.setNoOfRecords(rateTablesList.size() + postalServices.size() + masterAddressesList.size()
				+ userList.size() + roleList.size() + thanaList.size() + zoneList.size() + divisionList.size()
				+ districtList.size());

		Mockito.when(controlRepository.findByStatus(Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(control);
		Mockito.when(clientRepository.findClientByClientIdAndClientStatusAndStatus(client.getClientId(), "approved",
				Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(client);
		Mockito.when(rateTableRepository.findAll()).thenReturn(rateTablesList);
		Mockito.when(postalServiceRepository.findAll()).thenReturn(postalServices);
		Mockito.when(zoneRepository.findAll()).thenReturn(zoneList);
		Mockito.when(divisionRepository.findAll()).thenReturn(divisionList);
		Mockito.when(districtRepository.findAll()).thenReturn(districtList);
		Mockito.when(thanaRepository.findAll()).thenReturn(thanaList);
		Mockito.when(masterAddressRepository.findAll()).thenReturn(masterAddressesList);
		Mockito.when(roleRepository.findAll()).thenReturn(roleList);
		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleid_User);
		Mockito.when(userRepository.findUserByPostalCodeAndRole(1212, roleid_User)).thenReturn(userList);
		Mockito.when(syncTableRepository.save(Mockito.any(SyncTable.class))).thenReturn(synctable);

		byte[] response = dataSyncServiceImpl.syncMasterDataOnClient("12121591173558", 0l);
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		Map<String, Object> deserialized = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
		});
		Assert.assertEquals(12, deserialized.keySet().size());

		Assert.assertEquals(true, deserialized.containsKey("rates"));
		List<RateTable> rateTableList = objectMapper.convertValue(deserialized.get("rates"),
				new TypeReference<List<RateTable>>() {
				});
		Assert.assertEquals(1, rateTableList.size());
		Assert.assertEquals(Status.ACTIVE, rateTableList.get(0).getStatus());

		Assert.assertEquals(true, deserialized.containsKey("services"));
		List<ServicesVo> servicesResponseList = objectMapper.convertValue(deserialized.get("services"),
				new TypeReference<List<ServicesVo>>() {
				});
		Assert.assertEquals(2, servicesResponseList.size());
		Assert.assertEquals("Guaranteed Express Post", servicesResponseList.get(0).getServiceName());
		Assert.assertEquals("General Letter Postage", servicesResponseList.get(1).getServiceName());

		Assert.assertEquals(true, deserialized.containsKey("zone"));
		List<ZoneVo> receiveZoneList = objectMapper.convertValue(deserialized.get("zone"),
				new TypeReference<List<ZoneVo>>() {
				});
		Assert.assertEquals(1, receiveZoneList.size());
		Assert.assertEquals("zoneA", receiveZoneList.get(0).getZone());

		Assert.assertEquals(true, deserialized.containsKey("division"));
		List<DivisionVo> receiveDivisionList = objectMapper.convertValue(deserialized.get("division"),
				new TypeReference<List<DivisionVo>>() {
				});
		Assert.assertEquals(1, receiveDivisionList.size());
		Assert.assertEquals("divisionA", receiveDivisionList.get(0).getDivision());

		Assert.assertEquals(true, deserialized.containsKey("district"));
		List<DistrictVo> receiveDistrictList = objectMapper.convertValue(deserialized.get("district"),
				new TypeReference<List<DistrictVo>>() {
				});
		Assert.assertEquals(1, receiveDistrictList.size());
		Assert.assertEquals("DistrictA", receiveDistrictList.get(0).getDistrict());

		Assert.assertEquals(true, deserialized.containsKey("thana"));
		List<ThanaVo> receiveThanaList = objectMapper.convertValue(deserialized.get("thana"),
				new TypeReference<List<ThanaVo>>() {
				});

		Assert.assertEquals(1, receiveThanaList.size());
		Assert.assertEquals("ThanaA", receiveThanaList.get(0).getThana());

		Assert.assertEquals(true, deserialized.containsKey("masterAddress"));
		List<MasterAddressVo> receiveMasterAddressList = objectMapper.convertValue(deserialized.get("masterAddress"),
				new TypeReference<List<MasterAddressVo>>() {
				});
		Assert.assertEquals(1, receiveMasterAddressList.size());
		Assert.assertEquals("masterAddressA", receiveMasterAddressList.get(0).getSubOffice());

		Assert.assertEquals(true, deserialized.containsKey("users"));
		List<UserVo> receiveUserList = objectMapper.convertValue(deserialized.get("users"),
				new TypeReference<List<UserVo>>() {
				});
		Assert.assertEquals(1, receiveUserList.size());
		Assert.assertEquals("XYZ", receiveUserList.get(0).getName());
		Assert.assertEquals(1212l, receiveUserList.get(0).getPostalCode());

		Assert.assertEquals(true, deserialized.containsKey("roles"));
		List<Role> receiveRoleList = objectMapper.convertValue(deserialized.get("roles"),
				new TypeReference<List<Role>>() {
				});
		Assert.assertEquals(1, receiveRoleList.size());
		Assert.assertEquals("Front Desk User", receiveRoleList.get(0).getDisplayName());

		Assert.assertEquals(true, deserialized.containsKey("syncTable"));
		SyncTable syncTableResponse = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
		Assert.assertEquals("pending", syncTableResponse.getSynceStatus());
		Assert.assertEquals(10, syncTableResponse.getNoOfRecords());

		Assert.assertEquals(true, deserialized.containsKey("controlServerTimeStamp"));
		Object controlServerTimeStampResponse = deserialized.get("controlServerTimeStamp");
		Timestamp clientLastUpdated = new Timestamp((long) controlServerTimeStampResponse);
		Assert.assertEquals(control.getServerMasterDataUpdateTimestamp(), clientLastUpdated);

		Assert.assertEquals(true, deserialized.containsKey("serverTimeStamp"));
		Object serverTimeStampResponse = deserialized.get("serverTimeStamp");
		Timestamp receiveServerTimeStamp = new Timestamp((long) serverTimeStampResponse);
		Assert.assertEquals(dateFormat.format(new Timestamp(System.currentTimeMillis())).toString(),
				dateFormat.format(receiveServerTimeStamp).toString());
	}

	@Test
	public void syncMasterDataOnClientPositiveTestCaseElseIfPartFirstWhenServerMasterDataUpdateTimestampAndServerTimestampEqual()
			throws JsonParseException, JsonMappingException, IOException {
		serverTimestamp = timestamp.getTime();

		synctable.setSynceStatus("already synced");
		synctable.setSyncType("master");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(client.getClientId());
		synctable.setNoOfRecords(0);

		Mockito.when(controlRepository.findByStatus(Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(control);
		Mockito.when(clientRepository.findClientByClientIdAndClientStatusAndStatus(client.getClientId(), "approved",
				Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(client);
		Mockito.when(syncTableRepository.save(Mockito.any(SyncTable.class))).thenReturn(synctable);

		byte[] response = dataSyncServiceImpl.syncMasterDataOnClient("12121591173558", serverTimestamp);
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		Map<String, Object> deserialized = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
		});

		Assert.assertEquals(13, deserialized.keySet().size());

		Assert.assertEquals(true, deserialized.containsKey("admin"));
		List<UserVo> adminResponseList = objectMapper.convertValue(deserialized.get("admin"),
				new TypeReference<List<UserVo>>() {
				});
		Assert.assertEquals(true, adminResponseList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("rates"));
		List<RateTable> ratesResponseList = objectMapper.convertValue(deserialized.get("rates"),
				new TypeReference<List<RateTable>>() {
				});
		Assert.assertEquals(true, ratesResponseList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("services"));
		List<ServicesVo> servicesResponseList = objectMapper.convertValue(deserialized.get("services"),
				new TypeReference<List<ServicesVo>>() {
				});
		Assert.assertEquals(true, servicesResponseList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("zone"));
		List<ZoneVo> receiveZoneList = objectMapper.convertValue(deserialized.get("zone"),
				new TypeReference<List<ZoneVo>>() {
				});
		Assert.assertEquals(true, receiveZoneList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("division"));
		List<DivisionVo> receiveDivisionList = objectMapper.convertValue(deserialized.get("division"),
				new TypeReference<List<DivisionVo>>() {
				});
		Assert.assertEquals(true, receiveDivisionList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("district"));
		List<DistrictVo> receiveDistrictList = objectMapper.convertValue(deserialized.get("district"),
				new TypeReference<List<DistrictVo>>() {
				});
		Assert.assertEquals(true, receiveDistrictList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("thana"));
		List<ThanaVo> receiveThanaList = objectMapper.convertValue(deserialized.get("thana"),
				new TypeReference<List<ThanaVo>>() {
				});
		Assert.assertEquals(true, receiveThanaList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("masterAddress"));
		List<MasterAddressVo> receiveMasterAddressList = objectMapper.convertValue(deserialized.get("masterAddress"),
				new TypeReference<List<MasterAddressVo>>() {
				});
		Assert.assertEquals(true, receiveMasterAddressList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("users"));
		List<UserVo> receiveUserList = objectMapper.convertValue(deserialized.get("users"),
				new TypeReference<List<UserVo>>() {
				});
		Assert.assertEquals(true, receiveUserList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("roles"));
		List<Role> receiveRoleList = objectMapper.convertValue(deserialized.get("roles"),
				new TypeReference<List<Role>>() {
				});
		Assert.assertEquals(true, receiveRoleList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("rates"));
		List<RateTable> rateTableList = objectMapper.convertValue(deserialized.get("rates"),
				new TypeReference<List<RateTable>>() {
				});
		Assert.assertEquals(true, rateTableList.isEmpty());

		Assert.assertEquals(true, deserialized.containsKey("syncTable"));
		SyncTable syncTableResponse = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
		Assert.assertEquals("already synced", syncTableResponse.getSynceStatus());
		Assert.assertEquals(0, syncTableResponse.getNoOfRecords());

		Assert.assertEquals(true, deserialized.containsKey("controlServerTimeStamp"));
		Object controlServerTimeStampResponse = deserialized.get("controlServerTimeStamp");
		Timestamp clientLastUpdated = new Timestamp((long) controlServerTimeStampResponse);
		Assert.assertEquals(control.getServerMasterDataUpdateTimestamp(), clientLastUpdated);

		Assert.assertEquals(true, deserialized.containsKey("serverTimeStamp"));
		Object serverTimeStampResponse = deserialized.get("serverTimeStamp");
		Timestamp receiveServerTimeStamp = new Timestamp((long) serverTimeStampResponse);
		Assert.assertEquals(dateFormat.format(new Timestamp(System.currentTimeMillis())).toString(),
				dateFormat.format(receiveServerTimeStamp).toString());

	}

	@Test
	public void syncMasterDataOnClientPositiveTestCaseElseIfPartSecondWhenServerMasterDataUpdateTimestampAfterServerTimestamp()
			throws JsonParseException, JsonMappingException, IOException {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-02-24 10:12:10.0");
		long serverTimestamp = timestamp1.getTime();

		synctable.setSynceStatus("pending");
		synctable.setSyncType("master");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(client.getClientId());
		synctable.setNoOfRecords(rateTablesList.size() + postalServices.size() + masterAddressesList.size()
				+ userList.size() + roleList.size() + thanaList.size() + districtList.size() + divisionList.size()
				+ zoneList.size());

		Mockito.when(controlRepository.findByStatus(Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(control);
		Mockito.when(clientRepository.findClientByClientIdAndClientStatusAndStatus(client.getClientId(), "approved",
				Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(client);
		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleid_User);
		Mockito.when(userRepository.findUserByPostalCodeAndRole(client.getPostalCode(), roleid_User))
				.thenReturn(userList);

		Mockito.when(rateTableRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(rateTablesList);
		Mockito.when(postalServiceRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(postalServices);
		Mockito.when(zoneRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(zoneList);
		Mockito.when(divisionRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(divisionList);
		Mockito.when(districtRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(districtList);
		Mockito.when(thanaRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(thanaList);
		Mockito.when(masterAddressRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(masterAddressesList);
		Mockito.when(roleRepository.findByUpdatedOnGreaterThanSyncTime(new Timestamp(serverTimestamp)))
				.thenReturn(roleList);
		Mockito.when(syncTableRepository.save(Mockito.any(SyncTable.class))).thenReturn(synctable);

		byte[] response = dataSyncServiceImpl.syncMasterDataOnClient("12121591173558", serverTimestamp);
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		Map<String, Object> deserialized = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
		});
		Assert.assertEquals(12, deserialized.keySet().size());

		Assert.assertEquals(true, deserialized.containsKey("rates"));
		List<RateTable> rateTableList = objectMapper.convertValue(deserialized.get("rates"),
				new TypeReference<List<RateTable>>() {
				});
		Assert.assertEquals(1, rateTableList.size());
		Assert.assertEquals(Status.ACTIVE, rateTableList.get(0).getStatus());

		Assert.assertEquals(true, deserialized.containsKey("services"));
		List<ServicesVo> servicesResponseList = objectMapper.convertValue(deserialized.get("services"),
				new TypeReference<List<ServicesVo>>() {
				});
		Assert.assertEquals(2, servicesResponseList.size());
		Assert.assertEquals("Guaranteed Express Post", servicesResponseList.get(0).getServiceName());
		Assert.assertEquals("General Letter Postage", servicesResponseList.get(1).getServiceName());

		Assert.assertEquals(true, deserialized.containsKey("zone"));
		List<ZoneVo> receiveZoneList = objectMapper.convertValue(deserialized.get("zone"),
				new TypeReference<List<ZoneVo>>() {
				});
		Assert.assertEquals(1, receiveZoneList.size());
		Assert.assertEquals("zoneA", receiveZoneList.get(0).getZone());

		Assert.assertEquals(true, deserialized.containsKey("division"));
		List<DivisionVo> receiveDivisionList = objectMapper.convertValue(deserialized.get("division"),
				new TypeReference<List<DivisionVo>>() {
				});
		Assert.assertEquals(1, receiveDivisionList.size());
		Assert.assertEquals("divisionA", receiveDivisionList.get(0).getDivision());

		Assert.assertEquals(true, deserialized.containsKey("district"));
		List<DistrictVo> receiveDistrictList = objectMapper.convertValue(deserialized.get("district"),
				new TypeReference<List<DistrictVo>>() {
				});
		Assert.assertEquals(1, receiveDistrictList.size());
		Assert.assertEquals("DistrictA", receiveDistrictList.get(0).getDistrict());

		Assert.assertEquals(true, deserialized.containsKey("thana"));
		List<ThanaVo> receiveThanaList = objectMapper.convertValue(deserialized.get("thana"),
				new TypeReference<List<ThanaVo>>() {
				});

		Assert.assertEquals(1, receiveThanaList.size());
		Assert.assertEquals("ThanaA", receiveThanaList.get(0).getThana());

		Assert.assertEquals(true, deserialized.containsKey("masterAddress"));
		List<MasterAddressVo> receiveMasterAddressList = objectMapper.convertValue(deserialized.get("masterAddress"),
				new TypeReference<List<MasterAddressVo>>() {
				});
		Assert.assertEquals(1, receiveMasterAddressList.size());
		Assert.assertEquals("masterAddressA", receiveMasterAddressList.get(0).getSubOffice());

		Assert.assertEquals(true, deserialized.containsKey("users"));
		List<UserVo> receiveUserList = objectMapper.convertValue(deserialized.get("users"),
				new TypeReference<List<UserVo>>() {
				});
		Assert.assertEquals(1, receiveUserList.size());
		Assert.assertEquals("XYZ", receiveUserList.get(0).getName());
		Assert.assertEquals(1212l, receiveUserList.get(0).getPostalCode());

		Assert.assertEquals(true, deserialized.containsKey("roles"));
		List<Role> receiveRoleList = objectMapper.convertValue(deserialized.get("roles"),
				new TypeReference<List<Role>>() {
				});
		Assert.assertEquals(1, receiveRoleList.size());
		Assert.assertEquals("Front Desk User", receiveRoleList.get(0).getDisplayName());

		Assert.assertEquals(true, deserialized.containsKey("syncTable"));
		SyncTable syncTableResponse = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
		Assert.assertEquals("pending", syncTableResponse.getSynceStatus());
		Assert.assertEquals(10, syncTableResponse.getNoOfRecords());

		Assert.assertEquals(true, deserialized.containsKey("controlServerTimeStamp"));
		Object controlServerTimeStampResponse = deserialized.get("controlServerTimeStamp");
		Timestamp clientLastUpdated = new Timestamp((long) controlServerTimeStampResponse);
		Assert.assertEquals(control.getServerMasterDataUpdateTimestamp(), clientLastUpdated);

		Assert.assertEquals(true, deserialized.containsKey("serverTimeStamp"));
		Object serverTimeStampResponse = deserialized.get("serverTimeStamp");
		Timestamp receiveServerTimeStamp = new Timestamp((long) serverTimeStampResponse);
		Assert.assertEquals(dateFormat.format(new Timestamp(System.currentTimeMillis())).toString(),
				dateFormat.format(receiveServerTimeStamp).toString());
	}

	@Test
	public void syncMasterDataOnClientTestCaseOnNullCondition() throws Exception {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-03-27 10:10:10.0");
		long serverTimestamp = timestamp1.getTime();

		Mockito.when(controlRepository.findByStatus(Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(control);
		Mockito.when(clientRepository.findClientByClientIdAndClientStatusAndStatus(client.getClientId(), "approved",
				Enum.valueOf(Status.class, "ACTIVE"))).thenReturn(client);

		Assert.assertNull(dataSyncServiceImpl.syncMasterDataOnClient("12121591173558", serverTimestamp));

	}

	@Test
	public void syncDataSuccessTestCasesForIfPartPendingCondition() {

		synctable.setSyncId(3L);
		synctable.setClientId("12121591173558");
		synctable.setSyncType("user");
		synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setNoOfRecordsUpdated(6);
		synctable.setNoOfRecords(6);
		synctable.setSynceStatus("pending");
	
		syncTableList.add(synctable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(synctable.getSyncType(),
				synctable.getClientId(), PageRequest.of(0, 1))).thenReturn(syncTableList);
		Mockito.when(syncTableRepository.save(syncTableList.get(0))).thenReturn(synctable);
		SyncTable responseSyncTable = dataSyncServiceImpl.syncDataSuccess(synctable);

		Assert.assertEquals("synced successfully", responseSyncTable.getSynceStatus());
		Assert.assertEquals(dateFormat.format(synctable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(responseSyncTable.getSyncUpdatedTimestamp()).toString());
		Assert.assertEquals(6, responseSyncTable.getNoOfRecordsUpdated());
	}

	@Test
	public void syncDataSuccessTestCasesForElseIfPartAlreadySyncedCondition() {

		synctable.setSyncId(1L);
		synctable.setClientId("11001581656985");
		synctable.setSyncType("user");
		synctable.setSynceStatus("already synced");
		synctable.setNoOfRecordsUpdated(3);
		synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setNoOfRecords(3);

		syncTableList.add(synctable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(synctable.getSyncType(),
				synctable.getClientId(), PageRequest.of(0, 1))).thenReturn(syncTableList);
		Mockito.when(syncTableRepository.save(syncTableList.get(0))).thenReturn(synctable);
		SyncTable responseSyncTable = dataSyncServiceImpl.syncDataSuccess(synctable);

		Assert.assertEquals("already synced", responseSyncTable.getSynceStatus());
		Assert.assertEquals(dateFormat.format(synctable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(responseSyncTable.getSyncUpdatedTimestamp()).toString());
		Assert.assertEquals(3, responseSyncTable.getNoOfRecordsUpdated());
	}

	@Test
	public void syncDataSuccessTestCasesForElsePartSyncingFailedCondition() {

		synctable.setSyncId(3L);
		synctable.setClientId("12121591173558");
		synctable.setSyncType("user");
		synctable.setNoOfRecordsUpdated(0);
		synctable.setSynceStatus("pending");
		synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setNoOfRecords(1);

		syncTableList.add(synctable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(synctable.getSyncType(),
				synctable.getClientId(), PageRequest.of(0, 1))).thenReturn(syncTableList);
		Mockito.when(syncTableRepository.save(syncTableList.get(0))).thenReturn(synctable);

		SyncTable responseSyncTable = dataSyncServiceImpl.syncDataSuccess(synctable);

		Assert.assertEquals("syncing failed", responseSyncTable.getSynceStatus());
		Assert.assertEquals(dateFormat.format(synctable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(responseSyncTable.getSyncUpdatedTimestamp()).toString());
		Assert.assertEquals(0, responseSyncTable.getNoOfRecordsUpdated());
	}

	@Test
	public void getUserListForPostalCodePositiveTestCaseElsePart()
			throws JsonParseException, JsonMappingException, IOException {

		java.sql.Timestamp timestamp1 = java.sql.Timestamp.valueOf("2020-09-24 10:12:10.0");
		long serverTimestamp = timestamp1.getTime();

		synctable.setSynceStatus("pending");
		synctable.setSyncType("user");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(client.getClientId());
		synctable.setNoOfRecords(userList.size());

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleid_User);
		Mockito.when(userRepository.findUserByPostalCodeAndRole(client.getPostalCode(), roleid_User))
				.thenReturn(userList);
		Mockito.when(syncTableRepository.save(Mockito.any(SyncTable.class))).thenReturn(synctable);

		byte[] response = dataSyncServiceImpl.getUserListForPostalCode("12121591173558", serverTimestamp, 1212l);
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		Map<String, Object> deserialized = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {
		});
		Assert.assertEquals(3, deserialized.keySet().size());

		Assert.assertEquals(true, deserialized.containsKey("users"));
		List<UserVo> receiveUserList = objectMapper.convertValue(deserialized.get("users"),
				new TypeReference<List<UserVo>>() {
				});
		Assert.assertEquals(1, receiveUserList.size());
		Assert.assertEquals("abc@gmail.com", receiveUserList.get(0).getEmail());

		Assert.assertEquals(true, deserialized.containsKey("syncTable"));
		SyncTable syncTableResponse = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
		Assert.assertEquals("pending", syncTableResponse.getSynceStatus());
		Assert.assertEquals(1, syncTableResponse.getNoOfRecords());
		Assert.assertEquals(DateFormat.getDateTimeInstance().format(synctable.getSyncStartTimestamp()),
				DateFormat.getDateTimeInstance().format(syncTableResponse.getSyncStartTimestamp()));

		Assert.assertEquals(true, deserialized.containsKey("serverTimeStamp"));
		long serverTimeStampResponse = (long) deserialized.get("serverTimeStamp");
		Timestamp receiveServerTimeStamp = new Timestamp(serverTimeStampResponse);

		Assert.assertEquals(dateFormat.format(new Timestamp(System.currentTimeMillis())).toString(),
				dateFormat.format(receiveServerTimeStamp).toString());

	}

}
