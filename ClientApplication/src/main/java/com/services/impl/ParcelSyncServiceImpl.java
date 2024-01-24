package com.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.transaction.Transactional;

import com.vo.CreateSignatureResponseVo;
import com.vo.ParcelSyncVo;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.ClientAccount;
import com.domain.Parcel;
import com.domain.SyncTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.pdf.codec.Base64;
import com.repositories.ClientAccountRepository;
import com.repositories.ClientRepository;
import com.repositories.ControlRepository;
import com.repositories.ParcelRepository;
import com.repositories.SyncTableRepository;
import com.services.ParcelService;
import com.services.ParcelSyncService;
import com.util.AuthTokenUtils;
import com.util.CreateSignatureApiUtils;
import com.util.SHA256SingnatureUtils;

@Service
public class ParcelSyncServiceImpl implements ParcelSyncService {

	@Autowired
	ControlRepository controlRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	SyncTableRepository syncTableRepository;

	@Autowired
	ParcelService parcelService;

	@Autowired
	ClientAccountRepository clientAccountRepository;

	@Autowired
	ParcelRepository parcelRepository;

	@Value("${server.url}")
	private String serverURL;

	protected Logger log = LoggerFactory.getLogger(ParcelSyncServiceImpl.class);

	// message packs returning bytes
	@Override
	public byte[] syncClientDataOnMaster(String clientId) {
		List<Parcel> parcels = parcelService.getBySyncFlag(false);
		SyncTable synctable = new SyncTable();
		Client client = clientRepository.findClientByClientIdAndClientStatusAndStatus(clientId, "approved",
				Enum.valueOf(Status.class, "ACTIVE"));
		if (parcels.isEmpty()) {
			synctable.setSynceStatus("already synced");
			synctable.setSyncType("parcel");
			synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
			synctable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
			synctable.setClientId(clientId);
			synctable.setNoOfRecords(0);
			synctable = syncTableRepository.save(synctable);
			ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
			// objectMapper.enableDefaultTyping();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("parcels", new ArrayList<Parcel>());
			data.put("syncTable", synctable);
			byte[] bytes = null;
			try {
				bytes = objectMapper.writeValueAsBytes(data);
			} catch (JsonProcessingException e) {
				log.error("Error occurred while parcel syncing::", e);
			}
			return bytes;
		} else if (parcels.size() > 0) {
			return createUpdatedClientMessagePack(clientId, client.getPostalCode());
		}
		return null;
	}

	// Creating message pack with some data...
	private byte[] createUpdatedClientMessagePack(String clientId, int postalCode) {
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		// objectMapper.enableDefaultTyping();
		Map<String, Object> data = new HashMap<String, Object>();
		log.debug("Client postal code::::" + postalCode);
		List<Parcel> parcelList = parcelService.getBySyncFlag(false);
		List<ParcelSyncVo> parcelVoList = new ArrayList<ParcelSyncVo>();
		for (Parcel parcel : parcelList) {
			if (parcel.isPaid()) {
				ParcelSyncVo parcelVo = new ParcelSyncVo();
				parcelVo.setSenderAddress(parcel.getSenderAddress());
				parcelVo.setSenderMobileNumber(parcel.getSenderMobileNumber());
				parcelVo.setSenderName(parcel.getSenderName());
				parcelVo.setClient(parcel.getClient());
				parcelVo.setActWt(String.valueOf(parcel.getActWt()));
				parcelVo.setCod(parcel.isCod());
				parcelVo.setToPay(parcel.isToPay());
				parcelVo.setCreatedDate(parcel.getCreatedDate());
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setLabelCode(parcel.getLabelCode());
				parcelVo.setLastPrintDate(parcel.getLastPrintDate());
				parcelVo.setParcelContent(parcel.getParcelContent());
				parcelVo.setParcelDeadWeight(String.valueOf(parcel.getParcelDeadWeight()));
				parcelVo.setParcelDeclerationValue(String.valueOf(parcel.getParcelDeclerationValue()));
				parcelVo.setParcelHeight(String.valueOf(parcel.getParcelHeight()));
				parcelVo.setParcelLength(String.valueOf(parcel.getParcelLength()));
				parcelVo.setParcelVolumeWeight(String.valueOf(parcel.getParcelVolumeWeight()));
				parcelVo.setParcelWidth(String.valueOf(parcel.getParcelWidth()));
				parcelVo.setPrintCount(String.valueOf(parcel.getPrintCount()));
				parcelVo.setPrintOption(parcel.getPrintOption());
				parcelVo.setpStatus(parcel.getpStatus());
				parcelVo.setRateCalculation(parcel.getRateCalculation());
				parcelVo.setRateCalculationJSON(parcel.getRateCalculationJSON());
				parcelVo.setService(parcel.getService());
				parcelVo.setReceiverAddress(parcel.getReceiverAddress());
				parcelVo.setRecipientMobileNumber(parcel.getRecipientMobileNumber());
				parcelVo.setRecipientName(parcel.getRecipientName());
				parcelVo.setStatus(parcel.getStatus());
				parcelVo.setSubServices(parcel.getSubServices());
				parcelVo.setTrackId(parcel.getTrackId());
				parcelVo.setCreatedBy(parcel.getCreatedBy());
				parcelVo.setParcelimage(parcel.getParcelimage());
				parcelVo.setScanedBarcode(parcel.getScanedBarcode());
				parcelVo.setParcelCount(parcel.getParcelCount());
				parcelVoList.add(parcelVo);
			}

		}
		SyncTable synctable = new SyncTable();
		synctable.setSynceStatus("pending");
		synctable.setSyncType("parcel");
		synctable.setSyncStartTimestamp(new Timestamp(System.currentTimeMillis()));
		synctable.setClientId(clientId);
		synctable.setNoOfRecords(parcelVoList.size());
		synctable = syncTableRepository.save(synctable);
		data.put("parcels", parcelVoList);
		data.put("syncTable", synctable);
		byte[] bytes = null;
		try {
			bytes = objectMapper.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			log.error("Error creating parcel message pack:: ", e);
		}

		float totalAmount = 0;
		for (Parcel parcel : parcelList) {
			if (!parcel.getStatus().equals("void")) {
				totalAmount += parcel.getInvoiceBreakup().getPayableAmnt();
			}
		}
		ClientAccount account = clientAccountRepository.findById(clientId).get();
		account.setTotalBalance(account.getTotalBalance() - totalAmount);
		clientAccountRepository.save(account);
		return bytes;
	}

	// for sending message pack to the Server
	@Transactional(rollbackOn = Exception.class)
	@Override
	public SyncTable sendDataToSync(String token) throws Exception {
		Client sysClient = parcelService.getClientDetails();
		Map<String, String> params = new HashMap<>();
		params.put("clientId", sysClient.getClientId());
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();

		List<Parcel> unpaidParcels = parcelRepository.findAllByStatusAndPaid("booked", false);
		List<Parcel> paidParcels = new LinkedList<>();
		System.out.println("ParcelSyncServiceImpl.sendDataToSync()"+unpaidParcels.size());
		for (Parcel parcel : unpaidParcels) {
			Parcel paid = createSignature(parcel, sysClient);
			if (paid != null)
				paidParcels.add(paid);
		}
		parcelRepository.saveAll(paidParcels);
		
		RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
				+ machineId.substring(machineId.length() - 38, machineId.length() - 2));

		byte[] bytes = syncClientDataOnMaster(sysClient.getClientId());

		HttpEntity<Object> entity = new HttpEntity<Object>(bytes, createHeaders(token));

		ResponseEntity<SyncTable> data = null;
		try {

			data = restTemplate.exchange(serverURL + "/server/parcelSync", HttpMethod.POST,
					entity, SyncTable.class);
		} catch (Exception e) {
			log.error("Error occurred in sending parcel data for syncing:: ", e);
		}
		SyncTable response = data.getBody();
		return response;
	}

	@Override
	public boolean saveSyncTable(SyncTable synctable) {
		List<SyncTable> syncTables = syncTableRepository
				.findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc("parcel", synctable.getClientId());
		SyncTable syncData = syncTables.get(0);
		syncData.setNoOfRecordsUpdated(synctable.getNoOfRecordsUpdated());
		syncData.setSynceStatus(synctable.getSynceStatus());
		syncData.setSyncUpdatedTimestamp(synctable.getSyncUpdatedTimestamp());
		syncTableRepository.save(syncData);

		if (syncData.getSynceStatus().equals("synced successfully")
				|| syncData.getSynceStatus().equals("already synced")) {
			List<Parcel> parcels = parcelRepository.findBySyncFlag(false);
			for (Parcel parcel : parcels) {
				parcel.setSyncFlag(true);
				parcelRepository.save(parcel);
			}
			return true;
		} else
			return false;
	}

	public SyncTable saveUpdateSyncTable(SyncTable syncTable) {
		return syncTableRepository.save(syncTable);
	}

	private Parcel createSignature(Parcel parcel, Client client) {
		try {
			String data = SHA256SingnatureUtils.createParcelHex(parcel);
			ResponseEntity<CreateSignatureResponseVo> entity = CreateSignatureApiUtils.createSignature(
					parcel.getLabelCode(), new String(Base64.decode(client.getNgpId())),
					data, parcel.getInvoiceBreakup().getPayableAmnt());
			if (entity.getStatusCode().equals(HttpStatus.CREATED)) {
				parcel.setPaid(true);
				try {
					parcel.setEvidence(new String(Base64.encodeBytes(entity.getBody().getEvidence().toJson().getBytes())));
				} catch (JsonProcessingException e) {
					log.error(e.getLocalizedMessage());
					e.printStackTrace();
				}
				return parcel;
			} else {
				if (entity.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
					AuthTokenUtils.oauthToken = null;
				log.error("Error creating signature for parcel:{} , response:{}", parcel, entity);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
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
