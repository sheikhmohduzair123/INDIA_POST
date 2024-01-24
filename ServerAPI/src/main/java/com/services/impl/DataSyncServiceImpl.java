package com.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.domain.Client;
import com.domain.Config;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.repositories.ConfigRepository;
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
import com.services.DataSyncService;
import com.vo.DistrictVo;
import com.vo.DivisionVo;
import com.vo.MasterAddressVo;
import com.vo.RateTableVo;
import com.vo.ServicesVo;
import com.vo.ThanaVo;
import com.vo.UserVo;
import com.vo.ZoneVo;


@Service
public class DataSyncServiceImpl implements DataSyncService {

	protected Logger log = LoggerFactory.getLogger(DataSyncServiceImpl.class);

	@Autowired
	RateTableRepository rateTableRepository;

	@Autowired
	ConfigRepository configRepository;

	@Autowired
	MasterAddressRepository masterAddressRepository;

	@Autowired
	ZoneRepository zoneRepository;

	@Autowired
	DivisionRepository divisionRepository;

	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	ThanaRepository thanaRepository;

	@Autowired
	PostalServiceRepository postalServiceRepository;

	@Autowired
	SyncTableRepository syncTableRepository;

	@Autowired
    ControlRepository controlRepository;

	@Autowired
	SUserRepository userRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public byte[] syncMasterDataOnClient(String clientId, long serverTimestamp) {
		SyncTable synctable = new SyncTable();
		Control control = controlRepository.findByStatus(Enum.valueOf(Status.class, "ACTIVE"));
		Client client = clientRepository.findClientByClientIdAndClientStatusAndStatus(clientId, "approved", Enum.valueOf(Status.class, "ACTIVE"));
		log.debug("Control data"+control.getServerMasterDataUpdateTimestamp());
		Timestamp clientLastUpdated = new Timestamp(serverTimestamp);

		if(serverTimestamp == 0)
		{
			log.debug("Inside Sync Master for First Time"+client.getPostalCode());
			return createMasterMessagePack(clientId, control, client.getPostalCode());
		}
		else if (control.getServerMasterDataUpdateTimestamp().equals(clientLastUpdated)) {
			synctable.setSynceStatus("already synced");
			synctable.setSyncType("master");
			synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
			synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
			synctable.setClientId(clientId);
			synctable.setNoOfRecords(0);
			synctable = syncTableRepository.save(synctable);
			ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
			//objectMapper.enableDefaultTyping();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("admin", new ArrayList<UserVo>());
			data.put("rates", new ArrayList<RateTableVo>());
			data.put("services", new ArrayList<ServicesVo>());
			data.put("zone", new ArrayList<Zone>());
			data.put("division", new ArrayList<Division>());
			data.put("district", new ArrayList<District>());
			data.put("thana", new ArrayList<Thana>());
			data.put("config", new ArrayList<Config>());
			data.put("masterAddress", new ArrayList<MasterAddress>());
			data.put("users", new ArrayList<UserVo>());
			data.put("roles", new ArrayList<Role>());
			data.put("syncTable", synctable);
			data.put("controlServerTimeStamp", control.getServerMasterDataUpdateTimestamp());
			data.put("serverTimeStamp", new Timestamp(System.currentTimeMillis()));
			byte[] bytes = null;
			try {
				bytes = objectMapper.writeValueAsBytes(data);
			} catch (JsonProcessingException e) {
				log.error("Error in syning master data for clientid "+ clientId+"::",e);
			}
			return bytes;

		}
		else if(control.getServerMasterDataUpdateTimestamp().after(new Timestamp(serverTimestamp)))
		{
			//return createUpdatedMasterMessagePack(clientId, control.getServerMasterDataUpdateTimestamp(), control, client.getPostalCode());

			return createUpdatedMasterMessagePack(clientId, new Timestamp(serverTimestamp), control, client.getPostalCode());

		}

		return null;
	}

	private byte[] createUpdatedMasterMessagePack(String clientId, Timestamp syncTime, Control control, int postalCode) {
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		//objectMapper.enableDefaultTyping();
		Map<String, Object> data = new HashMap<String, Object>();
		log.debug("Client postal code::::"+ postalCode);

		Role roleId = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");
		List<User> userList = userRepository.findUserByPostalCodeAndRole(postalCode,roleId);
		List<UserVo> userVoList = new ArrayList<UserVo>();
		for (User user : userList) {
			UserVo userVo = new UserVo();
			userVo.setEmail(user.getEmail());
			userVo.setAccountExpired(user.isAccountExpired());
			userVo.setAccountLocked(user.isAccountLocked());
			userVo.setCredentialsExpired(user.isCredentialsExpired());
			userVo.setDob(user.getDob());
			userVo.setEnabled(user.isEnabled());
			userVo.setFirstLogin(user.isFirstLogin());
			userVo.setId(user.getId());
			userVo.setIdentificationId(user.getIdentificationId());
			userVo.setLastLogin(user.getLastLogin());
			userVo.setName(user.getName());
			userVo.setPassword(user.getPassword());
			userVo.setPostalCode(user.getPostalCode());
			userVo.setRmsId(user.getRmsId());
			userVo.setRole(user.getRole());
			userVo.setUsername(user.getUsername());
			userVo.setStatus(user.getStatus());
			userVo.setAddressLine1(user.getAddressLine1());
			userVo.setAddressLine2(user.getAddressLine2());
			userVo.setMobileNumber(user.getMobileNumber());
			userVoList.add(userVo);
			
		}

		List<RateTable> rateTables = rateTableRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<RateTableVo> rateTableVoList = new ArrayList<RateTableVo>();
		for (RateTable rateTable : rateTables){

			RateTableVo rateTableVo = new RateTableVo();
			rateTableVo.setId(rateTable.getId());
			rateTableVo.setRateServiceCategory(rateTable.getRateServiceCategory());
			rateTableVo.setWeightWiseRate(rateTable.getWeightWiseRate());
			rateTableVo.setLocationWiseRate(rateTable.getLocationWiseRate());
			rateTableVo.setParcelValueWiseRate(rateTable.getParcelValueWiseRate());
			rateTableVo.setCreatedOn(rateTable.getCreatedOn());
			rateTableVo.setUpdatedOn(rateTable.getUpdatedOn());
			rateTableVo.setStatus(rateTable.getStatus());
			rateTableVoList.add(rateTableVo);
		}

		List<Services> postalServices= postalServiceRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<ServicesVo> servicesVoList3 = new ArrayList<ServicesVo>();
		for (Services services : postalServices){

			ServicesVo servicesVo = new ServicesVo();
			servicesVo.setId(services.getId());
			servicesVo.setServiceName(services.getServiceName());
			servicesVo.setServiceCode(services.getServiceCode());
			servicesVo.setParentServiceId(services.getParentServiceId());
			servicesVo.setCreatedOn(services.getCreatedOn());
			servicesVo.setUpdatedOn(services.getUpdatedOn());
			servicesVo.setStatus(services.getStatus());
			servicesVoList3.add(servicesVo);
		}

		List<Zone> zoneList = zoneRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);

		List<ZoneVo> zonelist = new ArrayList<>();
		for (Zone zone : zoneList) {

			ZoneVo zoneVo = new ZoneVo();
			zoneVo.setId(zone.getId());
			zoneVo.setStatus(zone.getStatus());
			zoneVo.setCreatedOn(zone.getCreatedOn());
			zoneVo.setUpdatedOn(zone.getUpdatedOn());
			zoneVo.setZone(zone.getZone());
			zonelist.add(zoneVo);
		}

		List<Division> divisionList = divisionRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
	    List<DivisionVo> divisionlist = new ArrayList<>();

	    for (Division division : divisionList) {
	    	DivisionVo divisionVo = new DivisionVo();

			divisionVo.setDivision(division.getDivision());
			divisionVo.setId(division.getId());
			divisionVo.setCreatedOn(division.getUpdatedOn());
			divisionVo.setUpdatedOn(division.getUpdatedOn());
			divisionVo.setStatus(division.getStatus());
			divisionVo.setZone(division.getZone().getId());
			divisionlist.add(divisionVo);
		}

		List<District> districtList = districtRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);

		List<DistrictVo> districtlist = new ArrayList<>();
		for (District district : districtList) {
			 DistrictVo districtVo = new DistrictVo();
			 districtVo.setCreatedOn(district.getCreatedOn());
			 districtVo.setUpdatedOn(district.getUpdatedOn());
			 districtVo.setId(district.getId());
			 districtVo.setStatus(district.getStatus());
			 districtVo.setDistrict(district.getDistrict());
			 districtVo.setDivision(district.getDivision().getId());
			 districtlist.add(districtVo);
		}

		List<Thana> thanaList = thanaRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<ThanaVo> thanalist = new ArrayList<>();

		for (Thana thana : thanaList) {
			 ThanaVo thanaVo = new ThanaVo();
				thanaVo.setCreatedOn(thana.getCreatedOn());
	            thanaVo.setUpdatedOn(thana.getUpdatedOn());
	            thanaVo.setId(thana.getId());
	            thanaVo.setStatus(thana.getStatus());
	            thanaVo.setDistrict(thana.getDistrict().getId());
	            thanaVo.setThana(thana.getThana());
	            thanalist.add(thanaVo);
		}


		List<MasterAddress> masterAddressesList = masterAddressRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<MasterAddressVo> masterAddresseslist = new ArrayList<>();

		for (MasterAddress masterAddress : masterAddressesList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setDistrictId(masterAddress.getDistrictId());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDivisionId(masterAddress.getDivisionId());
			masterAddressVo.setId(masterAddress.getId());
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setStatus(masterAddress.getStatus());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setThanaId(masterAddress.getThanaId());
			masterAddressVo.setUpdatedOn(masterAddress.getUpdatedOn());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setZoneId(masterAddress.getZoneId());
			masterAddresseslist.add(masterAddressVo);
		}

		List<Role> roleList = roleRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<Config> configList = configRepository.findByUpdatedOnGreaterThanSyncTime(syncTime);
		List<Config> configList2=new ArrayList<>();
		for(Config config:configList) {
			Config c = new Config();
			c.setConfigType(config.getConfigType());
			c.setConfigValue(config.getConfigValue());
			c.setCreatedBy(null);
			c.setUpdatedBy(null);
			c.setId(config.getId());
			c.setCreatedOn(config.getCreatedOn());
			c.setUpdatedOn(config.getUpdatedOn());
			c.setStatus(config.getStatus());
			configList2.add(c);
		}

		SyncTable synctable = new SyncTable();
		synctable.setSynceStatus("pending");
		synctable.setSyncType("master");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(clientId);
		synctable.setNoOfRecords(configList.size()+rateTables.size()+postalServices.size()+masterAddressesList.size()+userList.size()+roleList.size()+thanaList.size()+districtList.size()+divisionList.size()+zoneList.size());
		synctable = syncTableRepository.save(synctable);
		data.put("rates", rateTableVoList);
		data.put("services", servicesVoList3);
		data.put("zone", zonelist);
		data.put("division", divisionlist);
		data.put("district", districtlist);
		data.put("thana", thanalist);
		data.put("config", configList2);
		data.put("masterAddress", masterAddresseslist);
		data.put("users", userVoList);
		data.put("roles", roleList);
		//data.put("admin", adminVoList);
		data.put("syncTable", synctable);
		data.put("controlServerTimeStamp", control.getServerMasterDataUpdateTimestamp());
		data.put("serverTimeStamp", new Timestamp(System.currentTimeMillis()));
		byte[] bytes = null;
		try {
			bytes = objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			log.error("Error in retrieving master data for syncing with clientid "+ clientId+"::",e);
		}
		return bytes;
	}
	private byte[] createMasterMessagePack(String clientId, Control control, long postalCode) {
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		//objectMapper.enableDefaultTyping();
		Map<String, Object> data = new HashMap<String, Object>();
		log.debug("Client postal code::::"+ postalCode);
		List<RateTable> rateTables = rateTableRepository.findAll();
		List<RateTableVo> rateTableVoList1 = new ArrayList<RateTableVo>();
		for (RateTable rateTable : rateTables){

			RateTableVo rateTableVo = new RateTableVo();
			rateTableVo.setId(rateTable.getId());
			rateTableVo.setRateServiceCategory(rateTable.getRateServiceCategory());
			rateTableVo.setWeightWiseRate(rateTable.getWeightWiseRate());
			rateTableVo.setLocationWiseRate(rateTable.getLocationWiseRate());
			rateTableVo.setParcelValueWiseRate(rateTable.getParcelValueWiseRate());
			rateTableVo.setCreatedOn(rateTable.getCreatedOn());
			rateTableVo.setUpdatedOn(rateTable.getUpdatedOn());
			rateTableVo.setStatus(rateTable.getStatus());
			rateTableVoList1.add(rateTableVo);
		}

		List<Services> postalServices= postalServiceRepository.findAll();
		List<ServicesVo> servicesVoList2 = new ArrayList<ServicesVo>();
		for (Services services : postalServices){

			ServicesVo servicesVo = new ServicesVo();
			servicesVo.setId(services.getId());
			servicesVo.setServiceName(services.getServiceName());
			servicesVo.setServiceCode(services.getServiceCode());
			servicesVo.setParentServiceId(services.getParentServiceId());
			servicesVo.setCreatedOn(services.getCreatedOn());
			servicesVo.setUpdatedOn(services.getUpdatedOn());
			servicesVo.setStatus(services.getStatus());
			servicesVoList2.add(servicesVo);
		}
		List<Zone> zoneList = zoneRepository.findAll();
		List<ZoneVo> zoneVoList=new ArrayList<>();
		for (Zone zone : zoneList) {
			ZoneVo zoneVo= new ZoneVo();

            zoneVo.setId(zone.getId());
            zoneVo.setCreatedOn(zone.getCreatedOn());
            zoneVo.setUpdatedOn(zone.getUpdatedOn());
            zoneVo.setZone(zone.getZone());
            zoneVo.setStatus(zone.getStatus());
            zoneVoList.add(zoneVo);
		}


		List<Division> divisionList = divisionRepository.findAll();
		List<DivisionVo> divisionVoList=new ArrayList<>();

		for (Division division : divisionList) {
			DivisionVo divisionVo = new DivisionVo();

			divisionVo.setDivision(division.getDivision());
			divisionVo.setId(division.getId());
			divisionVo.setCreatedOn(division.getUpdatedOn());
			divisionVo.setUpdatedOn(division.getUpdatedOn());
			divisionVo.setStatus(division.getStatus());
			divisionVo.setZone(division.getZone().getId());
			divisionVoList.add(divisionVo);
		}

		 List<District> districtList = districtRepository.findAll();
		 List<DistrictVo> districtVoList = new ArrayList<>();
		 for (District district : districtList) {

			 DistrictVo districtVo = new DistrictVo();
			 districtVo.setCreatedOn(district.getCreatedOn());
			 districtVo.setUpdatedOn(district.getUpdatedOn());
			 districtVo.setId(district.getId());
			 districtVo.setStatus(district.getStatus());
			 districtVo.setDistrict(district.getDistrict());
			 districtVo.setDivision(district.getDivision().getId());

			 districtVoList.add(districtVo);
		}


		List<Thana> thanaList = thanaRepository.findAll();
		List<ThanaVo> thanaVoList = new ArrayList<>();
		for (Thana thana : thanaList) {
			ThanaVo thanaVo = new ThanaVo();
			thanaVo.setCreatedOn(thana.getCreatedOn());
            thanaVo.setUpdatedOn(thana.getUpdatedOn());
            thanaVo.setId(thana.getId());
            thanaVo.setStatus(thana.getStatus());
            thanaVo.setDistrict(thana.getDistrict().getId());
            thanaVo.setThana(thana.getThana());

            thanaVoList.add(thanaVo);

		}


		List<MasterAddress> masterAddressesList = masterAddressRepository.findAll();
		List<MasterAddressVo> masterAddressesVolist = new ArrayList<>();

		for (MasterAddress masterAddress : masterAddressesList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setDistrictId(masterAddress.getDistrictId());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDivisionId(masterAddress.getDivisionId());
			masterAddressVo.setId(masterAddress.getId());
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setStatus(masterAddress.getStatus());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setThanaId(masterAddress.getThanaId());
			masterAddressVo.setUpdatedOn(masterAddress.getUpdatedOn());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setZoneId(masterAddress.getZoneId());

			masterAddressesVolist.add(masterAddressVo);
		}


		List<Role> roleList = roleRepository.findAll();

		List<Config> configList = configRepository.findAll();

		Role roleid_User = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");
		List<User> userList = userRepository.findUserByPostalCodeAndRole(postalCode,roleid_User);
		
		List<UserVo> userVoList = new ArrayList<UserVo>();
		for (User user : userList) {
			UserVo userVo = new UserVo();
			userVo.setEmail(user.getEmail());
			userVo.setAccountExpired(user.isAccountExpired());
			userVo.setAccountLocked(user.isAccountLocked());
			userVo.setCredentialsExpired(user.isCredentialsExpired());
			userVo.setDob(user.getDob());
			userVo.setEnabled(user.isEnabled());
			userVo.setFirstLogin(user.isFirstLogin());
			userVo.setId(user.getId());
			userVo.setIdentificationId(user.getIdentificationId());
			userVo.setLastLogin(user.getLastLogin());
			userVo.setName(user.getName());
			userVo.setPassword(user.getPassword());
			userVo.setPostalCode(user.getPostalCode());
			userVo.setRmsId(user.getRmsId());
			userVo.setRole(user.getRole());
			userVo.setUsername(user.getUsername());
			userVo.setStatus(user.getStatus());
			userVo.setAddressLine1(user.getAddressLine1());
			userVo.setAddressLine2(user.getAddressLine2());
			userVo.setMobileNumber(user.getMobileNumber());
			userVoList.add(userVo);
		}

		SyncTable synctable = new SyncTable();
		synctable.setSynceStatus("pending");
		synctable.setSyncType("master");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(clientId);
		synctable.setNoOfRecords(configList.size()+rateTables.size()+postalServices.size()+masterAddressesList.size()+userList.size()+roleList.size()+thanaList.size()+zoneList.size()+divisionList.size()+districtList.size());
		synctable = syncTableRepository.save(synctable);

		data.put("rates", rateTableVoList1);
		data.put("services", servicesVoList2);
		data.put("zone", zoneVoList);
		data.put("division", divisionVoList);
		data.put("district", districtVoList);
		data.put("thana", thanaVoList);
		data.put("masterAddress",masterAddressesVolist);
		data.put("users", userVoList);
		data.put("roles", roleList);
		data.put("config", configList);
		data.put("syncTable", synctable);
		data.put("controlServerTimeStamp", control.getServerMasterDataUpdateTimestamp());
		data.put("serverTimeStamp", new Timestamp(System.currentTimeMillis()));
		byte[] bytes = null;
		try {
			bytes = objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			log.error("Error in creating object mapper for syning master data for clientid "+ clientId+"::",e);
		}
		return bytes;
	}
	public SyncTable syncDataSuccess(SyncTable syncTable)
	{
		List<SyncTable> syncTables = syncTableRepository.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1));
		SyncTable syncTable1 = syncTables.get(0);


		syncTable1.setNoOfRecordsUpdated(syncTable.getNoOfRecordsUpdated());
		if(syncTable1.getNoOfRecords() == syncTable.getNoOfRecordsUpdated() && syncTable1.getSynceStatus().equals("pending")) {
			syncTable1.setSynceStatus("synced successfully");
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		}
		else if(syncTable1.getNoOfRecords() == syncTable.getNoOfRecordsUpdated() && syncTable1.getSynceStatus().equals("already synced")) {
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		}
		else
		{
			syncTable1.setSynceStatus("syncing failed");
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		}
		syncTable = syncTableRepository.save(syncTable1);
		return syncTable;
	}

	@Override
	public byte[] getUserListForPostalCode(String clientId, long serverTimestamp, long postalcode) {

		log.debug("syncing updated user details from server to client for postal code"+postalcode);
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
			Map<String, Object> data = new HashMap<String, Object>();

			Role roleid_User = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");
			List<User> users = userRepository.findUserByPostalCodeAndRole(postalcode,roleid_User);

			List<UserVo> userVoList = new ArrayList<UserVo>();
			for (User user : users) {
				UserVo userVo = new UserVo();
				userVo.setEmail(user.getEmail());
				userVo.setAccountExpired(user.isAccountExpired());
				userVo.setAccountLocked(user.isAccountLocked());
				userVo.setCredentialsExpired(user.isCredentialsExpired());
				userVo.setDob(user.getDob());
				userVo.setEnabled(user.isEnabled());
				userVo.setFirstLogin(user.isFirstLogin());
				userVo.setId(user.getId());
				userVo.setIdentificationId(user.getIdentificationId());
				userVo.setLastLogin(user.getLastLogin());
				userVo.setName(user.getName());
				userVo.setPassword(user.getPassword());
				userVo.setPostalCode(user.getPostalCode());
				userVo.setRmsId(user.getRmsId());
				userVo.setRole(user.getRole());
				userVo.setUsername(user.getUsername());
				userVo.setStatus(user.getStatus());
				userVo.setAddressLine1(user.getAddressLine1());
				userVo.setAddressLine2(user.getAddressLine2());
				userVo.setMobileNumber(user.getMobileNumber());
				userVoList.add(userVo);
			}

			SyncTable synctable = new SyncTable();
			synctable.setSynceStatus("pending");
			synctable.setSyncType("user");
			synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
			synctable.setClientId(clientId);
			synctable.setNoOfRecords(userVoList.size());
			synctable = syncTableRepository.save(synctable);
			data.put("users", userVoList);
			data.put("syncTable", synctable);
			data.put("serverTimeStamp", new Timestamp(System.currentTimeMillis()));
			byte[] bytes = null;
			try {
				bytes = objectMapper.writeValueAsBytes(data);
			} catch (JsonProcessingException e) {
				log.error("Error in retrieving user data for clientid "+ clientId+"::",e);
			}
			return bytes;
	}


}
