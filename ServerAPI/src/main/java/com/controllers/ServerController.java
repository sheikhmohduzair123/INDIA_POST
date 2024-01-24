package com.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.configuration.JwtTokenUtil;
import com.domain.Client;
import com.domain.JwtResponse;
import com.domain.SyncTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.SyncTableRepository;
import com.services.ClientService;
import com.services.DataSyncService;
import com.services.ParcelSyncService;
import com.services.SUserService;
import com.services.impl.JwtUserDetailsService;
import com.vo.UserVo;


@RestController
@RequestMapping("/server")
public class ServerController {

	protected Logger log = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private SUserService userService;
    @Autowired
    private DataSyncService dataSyncService;

   @Autowired
    private ParcelSyncService parcelSyncService;

   @Autowired
   private SyncTableRepository SyncTableRepository;


    @Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Client client) throws Exception {

		log.info("Authenticate Client Id :" + client.getClientId());

		authenticate(client.getClientId(), client.getClientToken());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(client.getClientId());

		final String token = jwtTokenUtil.generateToken(userDetails);

		log.info("Sending Token Back to RestTemplate  :");

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping("/health")
	@ResponseBody
	public String checkHealth() {
		return "ServerApi is running";
	}

	@RequestMapping(value = "/getTimestamp", method = RequestMethod.GET)
	public Timestamp getTimestamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}

	@RequestMapping(value="/saveMachineIdDetails", method = RequestMethod.POST)
	public Client generateMachineIdDetails(@RequestBody Client client) throws Throwable {
			log.info("inside generate machine id for clientid::"+client.getClientId() +"client token::"+client.getClientToken()+"client name::"+client.getClientName());
			Client c = clientService.saveMachineId(client);
			return c;
	}

	/*
	 * @RequestMapping("/activateSeverUser")
	 *
	 * @ResponseBody public String activateSeverUser(@RequestParam String
	 * registrationCode) { String status =
	 * registrationService.activateServerUser(registrationCode); return status; }
	 */

	@RequestMapping(value="/saveChangePassword", method = RequestMethod.POST)
	@ResponseBody
	public UserVo saveChangePassword(@RequestBody UserVo uservo){
		log.debug("saving changed password in server database");
	    UserVo u = userService.saveChangePassword(uservo);
	    return u;
	}

	@RequestMapping(value="/getUserDetails/{clientId}/{serverTimestamp}/{postalcode}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getUserDetails(@PathVariable("clientId") String clientId, @PathVariable("serverTimestamp") long serverTimestamp, @PathVariable("postalcode") long postalcode){
		log.debug("retrieving user details");
	    byte[] bytes = dataSyncService.getUserListForPostalCode(clientId, serverTimestamp,postalcode);
		return bytes;
	}

	@RequestMapping(value = "/syncData/{clientId}/{serverTimestamp}", method = RequestMethod.GET)
	public byte[] syncData(@PathVariable("clientId") String clientId, @PathVariable("serverTimestamp") long serverTimestamp)
	{
		log.debug("Client id::::"+clientId);
		byte[] bytes = dataSyncService.syncMasterDataOnClient(clientId, serverTimestamp);
		return bytes;

	}

	@RequestMapping(value="/dataSyncedSuccess", method = RequestMethod.POST)
	public SyncTable syncTableSuccess(@RequestBody SyncTable syncTable) throws Throwable {
		log.info("sync data success inside sever::::" + syncTable.getNoOfRecordsUpdated());
		syncTable = dataSyncService.syncDataSuccess(syncTable);
		return syncTable;
	}

	@RequestMapping(value="/getServerClientDetails", method = RequestMethod.POST)
	public Client getServerClientStatus(@RequestBody Client client) throws Throwable {
		log.info("checking client status inside sever::::" + client.getClientName() + "for postal code:: "+ client.getPostalCode());
		Client c = clientService.fetchClientStatus(client.getClientId());
		return c;
	}

	@RequestMapping(value="/getServerUserDetails", method = RequestMethod.POST)
	public UserVo getServerUserDetails(@RequestBody UserVo user) throws Throwable {
		log.info("checking user status inside sever::::" + user.getName() + "for postal code:: "+ user.getPostalCode());
		UserVo u = userService.findUserStatus(user.getEmail());
		return u;
	}


    @RequestMapping(value = "/parcelSync", method = RequestMethod.POST)
    public SyncTable syncData(@RequestBody byte[] bytes) throws Exception
    {
       // Control control = parcelSyncService.getClientControl();
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        Map<String, Object> deserialized = objectMapper.readValue(bytes, new TypeReference<Map<String, Object>>(){});
        SyncTable syncTable = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
        syncTable = parcelSyncService.saveUpdateSyncTable(syncTable);
        log.debug("Sync table data on client - "+syncTable.getNoOfRecords());
        long updatedRowsClient = parcelSyncService.syncClientData(bytes);
        syncTable.setNoOfRecordsUpdated(updatedRowsClient);
        parcelSyncService.saveUpdateSyncTable(syncTable);
		syncTable = parcelSyncService.syncDataSuccess(syncTable);
		return syncTable;
 }

	@RequestMapping(value = "/parcelDataSyncedSuccess",method = RequestMethod.POST)
	public SyncTable syncTableSuccessResponse(@RequestBody SyncTable syncTable) throws Throwable {
		List<SyncTable> syncTables = SyncTableRepository.findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(
		syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1));
		SyncTable syncTable1 = syncTables.get(0);
        return syncTable1;
	}


	//jwt authenticate
	private void authenticate(String username, String password) throws Exception {

		log.debug("inside jwt authentication ");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
