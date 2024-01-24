package com.controllers;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.print.PrintServiceLookup;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.constants.Status;
import com.domain.Client;
import com.domain.Control;
import com.domain.Parcel;
import com.domain.Services;
import com.domain.SyncTable;
import com.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.repositories.SUserRepository;
import com.services.ClientService;
import com.services.DataSyncService;
import com.services.ParcelService;
import com.services.ParcelSyncService;
import com.services.impl.UserDetailsServiceImpl;
import com.vo.JwtTokenResponse;

@Controller
@Profile({"client","clientonly"})
@RequestMapping("/client")
public class ClientController {

    protected Logger log = LoggerFactory.getLogger(ClientController.class);


    @Autowired
    private ClientService clientService;

    @Autowired
    DataSyncService dataSyncService;

    @Autowired
    ParcelService parcelService;

    @Autowired
    private UserDetailsServiceImpl userUpdateService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    ParcelSyncService parcelSyncService;

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

    @RequestMapping(value="/generateMachineIdDetails", method = RequestMethod.GET)
    @ResponseBody
    public  String generateMachineIdDetails(String clientId, String clientToken ,HttpServletResponse cookieResponse) throws Throwable {
        log.info("inside generate machine id for clientid::"+clientId +"client token::"+clientToken);
        RestTemplate restTemplate = restTemplate(clientId, clientToken);
        Client client = clientService.generateMachineId(clientId, clientToken);
        String token=JwtAuthenticate(client,cookieResponse);
       	HttpEntity<Object> entity = new HttpEntity<Object>(client, createHeaders(token));
        ResponseEntity<Client> response = restTemplate.exchange(serverURL+"/server/saveMachineIdDetails", HttpMethod.POST, entity, Client.class);
        client=response.getBody();
        String res = clientService.saveClientDetailsLocal(client);
        return res;
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

    @RequestMapping(value="/checkClientStatus", method = RequestMethod.GET)
    @ResponseBody
    public String checkClientStatus(HttpServletRequest req) throws Exception{
        log.info("inside checking client status");
    	String cookieToken=readCookie(req);
        return clientService.checkLocalClientStatus(cookieToken);
    }


    @RequestMapping(value="/checkClientOnlyStatus", method = RequestMethod.GET)
    @ResponseBody
    public String checkClientOnlyStatus() throws Exception{
        log.info("inside checking client status");
        return clientService.checkLocalClientOnlyStatus();
    }

    @RequestMapping(value="/generatePassword", method = RequestMethod.GET)
    @ResponseBody
    public String generatePassword(String clientId) throws Throwable {
        log.info("inside generate password for clientid::"+clientId);
        return clientService.generatePassword(clientId);
    }

	@RequestMapping(value = "/checkClientRejectedOrExpired", method = RequestMethod.GET)
	@ResponseBody
	public String checkClientRejectedOrExpired(HttpServletRequest req, HttpServletResponse res)
			throws IOException, Exception {
		log.info("inside checking client status rejected or expired & user is active");
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",Enum.valueOf(Status.class, "ACTIVE"));
		Client client = clientList.get(0);
		log.debug("logined client :" + client.getClientId() + " " + client.getClientToken());

		// getting token from cookie
		String key = "";
		String keyToken = "";
		Cookie cookie[] = req.getCookies();
		for (Cookie c : cookie) {

			if (c.getName().equals("server")) {
				key = c.getName();
				keyToken = c.getValue();
			}
		}

		if(key.isBlank())
			return "cookie_expired";

    	String cookieToken=readCookie(req);
    	if(cookieToken.isBlank())
			return "cookie_expired";

		if (key.equals("server")) {
			String user_status = clientService.checkLocalUserStatus(keyToken);
			if(user_status=="active")
			{
				return clientService.checkLocalClientStatus(cookieToken);
			}else
			{
				return user_status;
			}
		}
		else
			return "cookie_expired";


	}

    /*
     * This will be default landing page for now where user will land post
     * successful login Here user will fill up sender details, then successive steps
     * will be followed. We will save the sender details in DB then move on to
     * receiver details page.
     */
    @RequestMapping(value = "/senderHome", method = RequestMethod.GET)
    public String senderHome(Model model, Principal principal) {
        log.info("sender detials retrieved, moving on to receiver details");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        List<Services> postalServiceList = parcelService.fetchPostalSerives();
        List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class,"ACTIVE"));
        model.addAttribute("postalServiceList", postalServiceList);
        List<String> dailyData = parcelService.getParcelCountCollection(clientList.get(0).getPostalCode());
        model.addAttribute("dailyData", dailyData);
        String name = userUpdateService.getUserByUsername(loginedUser.getUsername());
        model.addAttribute("user", name);
        model.addAttribute("postalCode", clientList.get(0).getPostalCode());
        model.addAttribute("role", loginedUser.getRole().getName());
        model.addAttribute("enablePdfDownload", enablePdfDownload);
        model.addAttribute("volumetricFactor", volumetricFactor);
        if(PrintServiceLookup.lookupDefaultPrintService() != null) {
            String printer = PrintServiceLookup.lookupDefaultPrintService().getName();
            model.addAttribute("printer", printer);
        }
        return "default";
    }

    /** For calculating the net price of parcel for Information purpose **/
    @RequestMapping(value = "/netPrice", method = RequestMethod.GET)
    public String calculatePrice(Model model, Principal principal) {
        log.info("Check net payable amount");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        List<Services> postalServiceList = parcelService.fetchPostalSerives();
        model.addAttribute("postalServiceList", postalServiceList);
        //List<String> dailyData = parcelService.getParcelCountCollection(Integer.parseInt(postalCode));
        //model.addAttribute("dailyData", dailyData);
        String name = userUpdateService.getUserByUsername(loginedUser.getUsername());
        model.addAttribute("user", name);
        model.addAttribute("volumetricFactor", volumetricFactor);
        return "calculatePrice";
    }

    /**
     * It will redirect to the page where user can Cancel and Reprint the Parcel
     *
     * @param model
     * @param principal
     * @return Cancel and Reprint Page
     */
    @RequestMapping(path= "/searchParcel", method = RequestMethod.GET)
    public ModelAndView searchParcel(Model model, Principal principal) {
        log.info("inside fetch top 5 recent transactions in search parcel");
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        List<Parcel> parcels =  parcelService.getRecentParcels(5);
        model.addAttribute("parcels", parcels);
        model.addAttribute("enablePdfDownload", enablePdfDownload);
        if(PrintServiceLookup.lookupDefaultPrintService() != null) {
            String printer = PrintServiceLookup.lookupDefaultPrintService().getName();
            model.addAttribute("printer", printer);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchParcel");
        log.info("Returning Search Parcel Page");

        return modelAndView;
    }


    @RequestMapping("/getReportPage")
    public String getReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        log.info("Returning Page report");

        return "reportPage";
    }

    @RequestMapping(value = "/commissionClient", method = RequestMethod.GET)
    public String commissionClient(Model model)
    {
        log.debug("opened commission client page");

		if (clientService.getClientDetailsLocal() && userUpdateService.getUserDetailsLocal()) {
			return "redirect:/login";
		}
		  return "commissionClient";
    }

    @RequestMapping(value = "/commissionClientOnly", method = RequestMethod.GET)
    public String commissionClientOnly(Model model)
    {
        log.debug("opened commission client page");
        return "commissionClientOnly";
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    public String syncData(Principal principal,HttpServletRequest req) throws Exception
    {
    	String cookieToken=readCookie(req);
        Control control = dataSyncService.getClientControl();
        byte[] bytesData = dataSyncService.getMasterDataToSync(control,cookieToken);
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
    	objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        Map<String, Object> deserialized = objectMapper.readValue(bytesData, new TypeReference<Map<String, Object>>() {});
        SyncTable syncTable = objectMapper.convertValue(deserialized.get("syncTable"), SyncTable.class);
        syncTable = dataSyncService.saveUpdateSyncTable(syncTable);
        log.debug("Sync table data on client::  "+syncTable.getNoOfRecords());
        User loginedUser=null;
        if(!sUserRepository.findAll().isEmpty())
        	loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        long updatedRowsClient = dataSyncService.syncMasterData(bytesData, control, loginedUser);
        log.debug("\nUPDATED NO OF RECORDS:: "+updatedRowsClient);
        syncTable.setNoOfRecordsUpdated(updatedRowsClient);
        String status = dataSyncService.getUpdatedSyncTable(syncTable,cookieToken);
        return status;
    }

    @RequestMapping(value = "/getDaily", method = RequestMethod.GET)
    @ResponseBody
    public String getDaily() {
        log.info("Inside get daily and fetching daily details");
        String dailyUpdateData = parcelService.getParcelCountTodayCollection();
        String dailyVoidData = parcelService.getVoidParcelCount();
        Timestamp updatedTimeStamp;
        if(activeProfile.equals("clientonly"))
        {
        	updatedTimeStamp=null;
        }
        else
        {
        	updatedTimeStamp=dataSyncService.getLastUpdatedSyncTime();
        }

        String daily=dailyUpdateData +","+dailyVoidData+","+updatedTimeStamp;

        return daily;

    }

    @RequestMapping(value = "/getYesterday", method = RequestMethod.GET)
    @ResponseBody
    public String getYesterDay(){
        log.info("Inside get Yesterday and fetching Yesterday details");
        String yesterdayUpdateData = parcelService.getParcelCountYesterDayCollection();
        String yesterdayVoidData = parcelService.getVoidYesterdayParcelCount();

        Timestamp updatedTimeStamp;
        if(activeProfile.equals("clientonly"))
        {
        	updatedTimeStamp=null;
        }
        else
        {
        	updatedTimeStamp=dataSyncService.getLastUpdatedSyncTime();
        }

        String yesterday=yesterdayUpdateData +","+yesterdayVoidData+","+updatedTimeStamp;
        return yesterday;

    }

    @RequestMapping(value = "/getLastUpdateTime", method = RequestMethod.GET)
    @ResponseBody
    public String getLastUpdatedTime()
    {    log.info("Inside last updated timestamp details");
         String response=dataSyncService.inLastDay();
         return response;
    }

    @RequestMapping(value = "/getVolumetricWeight", method = RequestMethod.GET)
    @ResponseBody
    public float getVolumetricWeight(@RequestParam float length, @RequestParam float width, @RequestParam float height)
    {    log.info("Inside get volumetric weight");
         float volumetric=(length*width*height)*volumetricFactor;
         return volumetric;
    }

    @RequestMapping(value="/parcelDataToSync",method= RequestMethod.GET)
    @ResponseBody
    public boolean syncData(HttpServletRequest req) throws Exception
	{
		log.info("Inside Sync Data to sync Client with Master");
    	String cookieToken=readCookie(req);
		Client sysClient = parcelService.getClientDetails();
		SyncTable synctable = parcelSyncService.sendDataToSync(cookieToken);
		boolean status = parcelSyncService.saveSyncTable(synctable);
     	log.debug("Client id::::" +sysClient.getClientId());
//		String status= parcelSyncService.getUpdatedSyncTable(syncTable,cookieToken);
//		String status= parcelSyncService.getUpdatedSyncTable(synctable,cookieToken);
     	return status;
	}


    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password) throws Exception
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient(clientId,password));

        return clientHttpRequestFactory;
    }

    private HttpClient httpClient(String clientId, String password) throws Exception
    {
        //CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

//        credentialsProvider.setCredentials(AuthScope.ANY,
//                new UsernamePasswordCredentials(clientId,password));

        HttpClient client = HttpClientBuilder
                .create()
                //.setDefaultCredentialsProvider(credentialsProvider)
                .setSSLSocketFactory(socketFactory)
                .build();
        return client;
    }

    private RestTemplate restTemplate(String clientId, String clientToken) throws Exception {

        HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, clientToken);
        return new RestTemplate(factory);
    }

	//create  header for rest call
	  private HttpHeaders createHeaders(String jwtToken)
	  { return new HttpHeaders() {
		  {
			  log.info("creating header for rest call auth ");
			  setContentType(MediaType.APPLICATION_JSON);
			  setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			  String authHeader = "Bearer " + jwtToken;
			  add("Authorization", authHeader );
	      }
	    };
	  }

	//rest call for getting token from serverApi
	  private String JwtAuthenticate(Client client,HttpServletResponse res) throws Exception {

		    log.info(" getting token on rest call "+client);
			RestTemplate restTemplate = restTemplate(client.getClientId(), client.getClientToken());

			String jwtToken = restTemplate.postForObject(serverURL + "/server/authenticate", client, String.class);
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	JwtTokenResponse jwtTokenResponse = objectMapper.readValue(jwtToken, JwtTokenResponse.class);
	    	jwtToken = jwtTokenResponse.getToken();
	    	Cookie cookie=writeCookie(jwtToken);
			res.addCookie(cookie);
			return jwtToken;
		}


	  //write cookie(token) to browser
	  private Cookie writeCookie(String token) {

		  log.info("writing cookieToken to browser ");
		  Cookie cookie = new Cookie("server",token);
		  //cookie.setMaxAge(365 * 24 * 60 * 60); // expires in for one year
		  cookie.setMaxAge(Integer.parseInt(cookieMaxAge)); // for one day
		  //cookie.setSecure(true);
		  cookie.setHttpOnly(true);
		  //cookie.setDomain(baseurl);
		  //cookie.setPath("/");
		  return cookie;
	  }
	  //read cookie from browser
	  private String readCookie(HttpServletRequest req) {

		  log.info("Reading cookie from browser ");
		  String token="";
	    	Cookie cookies[] = req.getCookies();
	        for(Cookie c : cookies)
	        {
	            if(c.getName().equals("server")) {

	                token=c.getValue();
	            }
	        }
	        return token;
	  }
}