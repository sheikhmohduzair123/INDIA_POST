package com.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.Control;
import com.domain.SyncTable;
import com.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.repositories.ConfigRepository;
import com.repositories.SUserRepository;
import com.services.ClientService;
import com.services.DataSyncService;
import com.services.ParcelService;
import com.services.ParcelSyncService;
import com.services.impl.LoginAttemptService;
import com.util.RefillAmountUtils;
import com.util.ServerTokenUtils;

@RestController
@Profile({ "client", "clientonly" })
@RequestMapping("/client")
public class ClientController {

    protected Logger log = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    DataSyncService dataSyncService;

    @Autowired
    ParcelService parcelService;

    // @Autowired
    // private UserDetailsServiceImpl userUpdateService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    ParcelSyncService parcelSyncService;

    @Autowired
    ConfigRepository configRepository;

    /*
     * @Autowired private SyncTableRepository syncTableRepository;
     */
    @Autowired
    private SUserRepository sUserRepository;

    @Value("${server.url}")
    private String serverURL;

    @Value("${postalCode}")
    private String postalCode;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${enable.pdf.download}")
    private String enablePdfDownload;

    @Value("${volumetric.factor}")
    private float volumetricFactor;

    @Autowired
    ServerTokenUtils tokenUtils;

    // public static String globalServerToken;

    /*
     * @Value("${trust.store}") private Resource trustStore;
     *
     *
     * @Value("${trust.store.password}") private String trustStorePassword;
     */

    @Value("${app.baseurl}")
    private String baseurl;

    @Value("${cookie.maxage}")
    private String cookieMaxAge;

    @RequestMapping(value = "/generateMachineIdDetails", method = RequestMethod.POST)
    public String generateMachineIdDetails(String clientId, String clientToken, HttpServletResponse cookieResponse)
            throws Throwable {
        log.info("inside generate machine id for clientid::" + clientId + "client token::" + clientToken);

        Boolean checkIfOtherCounterAlreadyExist = clientService.checkIfOtherCounterAlreadyExists();

        if (!checkIfOtherCounterAlreadyExist) {
            // empty db tables except client tables
            clientService.emptyDatabase();

            RestTemplate restTemplate = restTemplate(clientId, clientToken);
            Client client = clientService.generateMachineId(clientId, clientToken);
            String token = tokenUtils.JwtAuthenticate(client, cookieResponse);

            HttpEntity<Object> entity = new HttpEntity<Object>(client, createHeaders(token));
            ResponseEntity<Client> response = restTemplate.exchange(serverURL + "/server/saveMachineIdDetails",
                    HttpMethod.POST, entity, Client.class);
            client = response.getBody();
            String res = clientService.saveClientDetailsLocal(client);
            return res;
        } else {
            return "already registered counters available";
        }

    }

    /*
     * @RequestMapping(value="/generateMachineIdDetails", method =
     * RequestMethod.GET)
     *
     * @ResponseBody public String generateMachineIdDetails(String clientId, String
     * clientToken) throws Throwable {
     * log.info("inside generate machine id for clientid::"+clientId
     * +"client token::"+clientToken); RestTemplate restTemplate =
     * restTemplate(clientId, clientToken); Client client =
     * clientService.generateMachineId(clientId, clientToken); client =
     * restTemplate.postForObject(serverURL+"/server/saveMachineIdDetails" ,client,
     * Client.class); String response =
     * clientService.saveClientDetailsLocal(client); return response; }
     */

    @RequestMapping(value = "/checkClientStatus", method = RequestMethod.POST)
    public String checkClientStatus(HttpServletRequest req, HttpServletResponse res) throws Exception {

        log.info("inside checking client status");
        try {
            return clientService.checkLocalClientStatus(ServerTokenUtils.globalServerToken);
        }

        catch (Exception e) {

            log.error("Error occurred while checking local client status: ", e);

            if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                List<String> clientStauses = new ArrayList<>();
                clientStauses.add("approval required");
                clientStauses.add("approved");
                List<Client> client = clientRepository.findByClientStatusInAndStatus(clientStauses, Status.ACTIVE);
                ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client.get(0), res);

            }

            return clientService.checkLocalClientStatus(ServerTokenUtils.globalServerToken);

        }

    }

    @RequestMapping(value = "/checkClientOnlyStatus", method = RequestMethod.POST)
    public String checkClientOnlyStatus() throws Exception {
        log.info("inside checking client status");
        return clientService.checkLocalClientOnlyStatus();
    }

    @RequestMapping(value = "/generatePassword", method = RequestMethod.POST)
    public String generatePassword(String clientId) throws Throwable {
        log.info("inside generate password for clientid::" + clientId);
        return clientService.generatePassword(clientId);
    }

    @RequestMapping(value = "/checkClientRejectedOrExpired", method = RequestMethod.POST)
    public String checkClientRejectedOrExpired(HttpServletRequest req, HttpServletResponse res, Principal principal)
            throws IOException, Exception {

        log.info("inside checking client status rejected or expired & user is active");
        List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
                Enum.valueOf(Status.class, "ACTIVE"));
        Client client = clientList.get(0);
        log.debug("logined client :" + client.getClientId() + " " + client.getClientToken());

        // getting token from cookie
        String key = "";
        String keyToken = "";
        /*
         * Cookie cookie[] = req.getCookies(); for (Cookie c : cookie) {
         *
         * if (c.getName().equals("server")) { key = c.getName(); keyToken =
         * c.getValue(); } }
         *
         * if(key.isBlank()) return "cookie_expired";
         */
        // String cookieToken=readCookie(req);
        // if(cookieToken.isBlank())
        // return "cookie_expired";

        /* if (key.equals("server")) { */
            // loginAttemptService.syncBalance();
        try {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String user_status = clientService.checkLocalUserStatus(ServerTokenUtils.globalServerToken, loginedUser);

            if (user_status == "active") {
                return clientService.checkLocalClientStatus(ServerTokenUtils.globalServerToken);
            } else {
                return user_status;
            }
        } catch (Exception e) {
            log.error("Error occurred while checking if counter was expired or rejected: ", e);

            if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client, res);
            }
            return clientService.checkLocalClientStatus(ServerTokenUtils.globalServerToken);
        }
        /*
         * } else return "cookie_expired";
         */
    }

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public String syncData(Principal principal, HttpServletRequest req, HttpServletResponse res) throws Exception {
        // String cookieToken=readCookie(req);
        Control control = dataSyncService.getClientControl();
        String status;
        byte[] bytesData = null;
        try {

            bytesData = dataSyncService.getMasterDataToSync(control, ServerTokenUtils.globalServerToken);
        } catch (Exception e) {

            log.error("Error occurred in syncing data: ", e);

            if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {

                List<Client> client = clientRepository.findByClientStatusAndStatus("approved", Status.ACTIVE);
                ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client.get(0), res);
            }
            bytesData = dataSyncService.getMasterDataToSync(control, ServerTokenUtils.globalServerToken);
        }
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        Map<String, Object> deserialized = objectMapper.readValue(bytesData, new TypeReference<Map<String, Object>>() {
        });
        SyncTable syncTable = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
        syncTable = dataSyncService.saveUpdateSyncTable(syncTable);
        log.debug("Sync table data on client::  " + syncTable.getNoOfRecords());
        User loginedUser = null;

        if (!sUserRepository.findUserByEnabledAndStatus(true, Status.ACTIVE).isEmpty())
            loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();

        long updatedRowsClient = dataSyncService.syncMasterData(bytesData, control, loginedUser);
        log.debug("\nUPDATED NO OF RECORDS:: " + updatedRowsClient);
        syncTable.setNoOfRecordsUpdated(updatedRowsClient);
        try {
            status = dataSyncService.getUpdatedSyncTable(syncTable, ServerTokenUtils.globalServerToken);
        } catch (Exception e) {

            log.error("Error occurred in syncing data: ", e);

            if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                List<Client> client = clientRepository.findByClientStatusAndStatus("approved", Status.ACTIVE);
                ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client.get(0), res);

            }
            status = dataSyncService.getUpdatedSyncTable(syncTable, ServerTokenUtils.globalServerToken);
        }

        return status;

    }

    @RequestMapping(value = "/getDaily", method = RequestMethod.POST)
    public String getDaily(Principal principal) {
        log.info("Inside get daily and fetching daily details");
        User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();

        String dailyUpdateData = parcelService.getParcelCountTodayCollection();
        String dailyVoidData = parcelService.getVoidParcelCount();
        Timestamp updatedTimeStamp;
        if (activeProfile.equals("clientonly")) {
            updatedTimeStamp = null;
        } else {
            updatedTimeStamp = dataSyncService.getLastUpdatedSyncTime();
        }

        String daily = dailyUpdateData + "," + dailyVoidData + "," + updatedTimeStamp;

        return daily;

    }

    @RequestMapping(value = "/getYesterday", method = RequestMethod.POST)
    public String getYesterDay(Principal principal) {

        log.info("Inside get Yesterday and fetching Yesterday details");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String yesterdayUpdateData = parcelService.getParcelCountYesterDayCollection();
        String yesterdayVoidData = parcelService.getVoidYesterdayParcelCount();

        Timestamp updatedTimeStamp;
        if (activeProfile.equals("clientonly")) {
            updatedTimeStamp = null;
        } else {
            updatedTimeStamp = dataSyncService.getLastUpdatedSyncTime();
        }

        String yesterday = yesterdayUpdateData + "," + yesterdayVoidData + "," + updatedTimeStamp;
        return yesterday;

    }

    @RequestMapping(value = "/getLastUpdateTime", method = RequestMethod.POST)
    public String getLastUpdatedTime(Principal principal) throws Exception {
        log.info("Inside last updated timestamp details");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String response = dataSyncService.inLastDay();
        return response;
    }

    @RequestMapping(value = "/getVolumetricWeight", method = RequestMethod.GET)
    public float getVolumetricWeight(@RequestParam float length, @RequestParam float width, @RequestParam float height,
            Principal principal) {
        log.info("Inside get volumetric weight");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        float volumetric = (length * width * height) * volumetricFactor;
        return volumetric;
    }

    @RequestMapping(value = "/parcelDataToSync", method = RequestMethod.POST)
    public boolean syncData(HttpServletRequest req, Principal principal, HttpServletResponse res) throws Exception {

        log.info("Inside Sync Data to sync Client with Master");

        // String cookieToken=readCookie(req);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        // String cookieToken=readCookie(req);
        Client sysClient = parcelService.getClientDetails();
        SyncTable synctable;
        try {
            synctable = parcelSyncService.sendDataToSync(ServerTokenUtils.globalServerToken);
        } catch (Exception e) {

            log.error("Error occurred in syncing data: ", e);

            if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                List<Client> client = clientRepository.findByClientStatusAndStatus("approved", Status.ACTIVE);
                ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client.get(0), res);
            }
            synctable = parcelSyncService.sendDataToSync(ServerTokenUtils.globalServerToken);
        }
        boolean status = parcelSyncService.saveSyncTable(synctable);
        log.debug("Client id::::" + sysClient.getClientId());
        loginAttemptService.syncBalance();
        return status;
    }

    @RequestMapping(value = "/getRateReportPdf", method = RequestMethod.GET)
    public void generateRateReportPdf(HttpServletResponse response) {
        clientService.generateRateReportInPdf();
        try {
            File file = new File(".//Report//Rate_Table_Report.pdf");

            if (file.exists()) {
                // here I use Commons IO API to copy this file to the response output stream, I
                // don't know which API you use.
                FileUtils.copyFile(file, response.getOutputStream());

                // here we define the content of this file to tell the browser how to handle it
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment;filename=RateCard.pdf");
                response.flushBuffer();
            } else {
                System.out.println("Contract Not Found");
            }
        } catch (IOException exception) {
            System.out.println("Contract Not Found");
            System.out.println(exception.getMessage());
        }
    }

    @RequestMapping(value = "/refill", method = RequestMethod.GET)
    public ResponseEntity<String> refillMeter(@RequestParam("amount") double amount) {
                
        String response = clientService.refillMeter(amount);

        loginAttemptService.syncBalance();
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password)
            throws Exception {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient(clientId, password));

        return clientHttpRequestFactory;
    }

    private HttpClient httpClient(String clientId, String password) throws Exception {
        // CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        // credentialsProvider.setCredentials(AuthScope.ANY,
        // new UsernamePasswordCredentials(clientId,password));

        HttpClient client = HttpClientBuilder
                .create()
                // .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLSocketFactory(socketFactory)
                .build();
        return client;
    }

    private RestTemplate restTemplate(String clientId, String clientToken) throws Exception {

        HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, clientToken);
        return new RestTemplate(factory);
    }

    // create header for rest call
    private HttpHeaders createHeaders(String jwtToken) {
        return new HttpHeaders() {
            {
                log.info("creating header for rest call auth ");
                setContentType(MediaType.APPLICATION_JSON);
                setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                String authHeader = "Bearer " + jwtToken;
                add("Authorization", authHeader);
            }
        };
    }
}