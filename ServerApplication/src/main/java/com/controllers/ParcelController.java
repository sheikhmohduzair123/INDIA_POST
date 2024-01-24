package com.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.eclipse.jetty.util.IO;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.repositories.ClientRepository;
import com.services.ParcelService;
import com.util.A5LableGenerator;
import com.util.LabelGenerator;
import com.vo.MasterAddressVo;
import com.vo.ServicesVo;

/*
 * This class will have all methods which will be related to Parcel creation
 * Tracking related methods will not come here
 */


@RestController
@Profile({"client","server","clientonly"})
@RequestMapping("/parcel")
public class ParcelController {

    protected Logger log = LoggerFactory.getLogger(ParcelController.class);

    @Autowired
    private ParcelService parcelService;

	/*
	 * @Autowired private PostalServiceRepository postalServiceRepository;
	 *
	 * @Autowired private RateService rateService;
	 */
    @Autowired
	private ClientRepository clientRepository;

	/*
	 * @Autowired private SUserRepository srepository;
	 */
    @Autowired
	private LabelGenerator labelGenerator;

    @Autowired
	private A5LableGenerator a5LableGenerator;

	//Timeout value in milliseconds
	//private int timeout = 10000;

	@Value("${postalCode}")
	private String postalCode;

    @Value("${reportexport.path}")
    private String exportfilepath;

	@Value("${rateAPI}")
	private String rateURL;

	@Value("${app.baseurl}")
    private String baseurl;

	@Value("${cookie.maxage}")
	private String cookieMaxAge;


	/**
	 * To fill in the address based on the Postal Code entered by user.
	 * @param postalCode
	 * @return
	 */
	@RequestMapping(path= "/getAddressByPostalcode", produces = "application/json", method = RequestMethod.POST)
    public MasterAddressVo getAddressByPostalcode(@RequestParam int postalCode)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+postalCode);
    	List<MasterAddressVo> list = parcelService.getAddressByPostalcode(postalCode);
    	if(list != null && list.size() > 0){
    		return list.get(0);
    	}else{
    		return null;
    	}

    }

	/**
	 * To fill in the address based on the Suboffice entered by user.
	 * @param subOffice
	 * @return
	 */
	@RequestMapping(path= "/getAddressBySubOffice", produces = "application/json", method = RequestMethod.POST)
    public MasterAddressVo getAddressBySubOffice(@RequestParam String subOffice)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+subOffice);
    	List<MasterAddressVo> list = parcelService.getAddressBySubOffice(subOffice);
       	if(list != null && list.size() > 0){
    		return list.get(0);
    	}else{
    		return null;
    	}

    }

	/**
	 * To fill in the Distinct Division based on the Zone entered by user.
	 * @param zone
	 * @return
	 */
	@RequestMapping(path= "/getDistinctDivisionByZone", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctDivisionByZone(@RequestParam String zone)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+zone);
    	List<String> list = parcelService.getDistinctDivisionByZone(zone);
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}
    }

	/**
	 * To fill in the Distinct District based on the Division entered by user.
	 * @param division
	 * @return
	 */
	@RequestMapping(path= "/getDistinctDistrictByDivision", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctDistrictByDivision(@RequestParam String division)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+division);
    	List<String> list = parcelService.getDistinctDistrictByDivision(division);
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}

    }

	/**
	 * To fill in the Distinct Thana based on the District entered by user.
	 * @param district
	 * @return
	 */
	@RequestMapping(path= "/getDistinctThanaByDistrict", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctThanaByDistrict(@RequestParam String district)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+district);
    	List<String> list = parcelService.getDistinctThanaByDistrict(district);
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}

    }

	/**
	 * To fill in the address based on the thana entered by user.
	 *
	 * @param thana
	 * @return
	 */
	@RequestMapping(path= "/getDistinctSubOfficeByThana", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctSubOfficeByThana(@RequestParam String thana)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+thana);
    	List<String> list = parcelService.getDistinctSubOfficeByThana(thana);
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}

    }

	@RequestMapping(path= "/getSubOfficeByThana", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddressVo> getSubOfficeByThana(@RequestParam String thana)
    {
    	log.info("inside getAddressByPostalcode, postalCode:"+thana);
    	List<MasterAddressVo> list = parcelService.getSubOfficeByThana(thana);
    	//list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}

    }

	/**
	 * Get the list of all the Distinct Zones
	 * @return
	 */
	@RequestMapping(path= "/getDistinctZone", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctZone()
    {
    	log.info("inside getAddressByPostalcode, postalCode:");
    	List<String> list = parcelService.getDistinctZone();
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}
    }

	/**
	 * Get the list of all the Distinct Thanas
	 * @return
	 */
	@RequestMapping(path= "/getDistinctThana", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctThana()
    {
    	log.info("inside get distinct Thana");
    	List<String> list = parcelService.getDistinctThana();
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}
    }

	/**
	 * Get the list of all the Distinct Sub Office
	 * @return
	 */
	@RequestMapping(path= "/getDistinctSubOffice", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctSubOffice()
    {
    	log.info("inside get distinct Thana");
    	List<String> list = parcelService.getDistinctSubOffice();
    	list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}
    }

	@RequestMapping(path= "/getSubOffice", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddressVo> getSubOffice()
    {
    	log.info("inside get distinct Thana");
    	List<MasterAddressVo> list = parcelService.getSubOffice();
    	//list.sort(String.CASE_INSENSITIVE_ORDER);
    	if(list != null && list.size() > 0){
    		return list;
    	}else{
    		return null;
    	}
    }


	/**
	 * Get the list of all the Distinct Division
	 * @return
	 */
	@RequestMapping(path = "/getDistinctDivision", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctDivision() {
		log.info("inside getDistinctDivision, postalCode:");
		List<String> list = parcelService.getDistinctDivision();
		list.sort(String.CASE_INSENSITIVE_ORDER);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * Get the list of all the Distinct District
	 * @return
	 */
	@RequestMapping(path = "/getDistinctDistrict", produces = "application/json", method = RequestMethod.POST)
	public List<String> getDistinctDistrict() {
		log.info("inside getAddressByDistrict");
		List<String> list = parcelService.getDistinctDistrict();
		list.sort(String.CASE_INSENSITIVE_ORDER);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * To fill in the address based on the Suboffice entered by user.
	 * @param thana
	 * @return
	 */
	@RequestMapping(path = "/getAddressByThana", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddressVo> getAddressByThana(@RequestParam String thana) {
		log.info("inside getAddressByThana, Thana:"+thana);
		List<MasterAddressVo> list = parcelService.getAddressByThanaOnly(thana);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	/**
	 * Fetch the list of Sub Offices based on the first three characters entered by User
	 * @param subOffice
	 * @return
	 */
	@RequestMapping(path = "/getAddressBysubOfficeStartsWith", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddress> getAddressBysubOfficeStartsWith(@RequestParam String subOffice) {
		log.info("inside getAddressBysubOfficeStartsWith, subOffice:"+subOffice);
		List<MasterAddress> list = parcelService.getAddressBysubOfficeStartsWith(subOffice);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@RequestMapping(path = "/getAddressByDistrict", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddress> getAddressByDistrict(@RequestParam String district) {
		log.info("inside getAddressByDistrict, District"+district);

		List<MasterAddress> list = parcelService.getAddressByDistrict(district);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@RequestMapping(path = "/getAddressByDivision", produces = "application/json", method = RequestMethod.POST)
	public List<MasterAddressVo> getAddressByDivision(@RequestParam String division) {
		log.info("inside getAddressByDivision, Division:"+division);
		List<MasterAddressVo> list = parcelService.getAddressByDivision(division);
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

    /** Returning to Logout page after logout */
    @RequestMapping(value = "/calculateRateForParcel", method = RequestMethod.GET)
    public Parcel calculateRateForParcel(@ModelAttribute("parcel") Parcel parcel, Model model, HttpSession session,Principal principal,HttpServletRequest req,HttpServletResponse res) throws Exception{
    	log.info("inside calculateRateForParcel");
    	log.debug("parcel:"+parcel);
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	log.debug("parcel:"+loginedUser);

    	List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class,"ACTIVE"));
    	Client client = clientList.get(0);

    	log.debug("logined client :"+client.getClientId()+" "+client.getClientToken());
    	//getting token from cookie
    	String ratekey="";
    	String ratetoken="";
    	Cookie cookie[]=req.getCookies();
    	for(Cookie c:cookie) {
    		if(c.getName().equals("rate")) {
    			ratekey=c.getName();
    			ratetoken=c.getValue();
    		}
    	}
    	if(ratekey.equals("rate")) {
    			parcel = rateApiRestCall(parcel,ratetoken,session);

    		return parcel;
    	}
    	else
    		return null;
   		//return parcel;
    }

    /** Fetch the rate of the parcel before printing
	 * @throws Exception */
    @RequestMapping(value = "/saveDetails", method = RequestMethod.GET)
    public List<String> saveParcelDetails(Principal principal, HttpSession session,String paperSize) throws Exception {

    	log.info("inside saveParcelDetails");
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	Random random = new Random();
		List<String> dailyData = null;
		Parcel parcel = (Parcel) session.getAttribute("parcel");
		/*
		 * int trackNumber = random.nextInt() & Integer.MAX_VALUE;
		 * parcel.setTrackId(trackNumber+"");
		 */
        String numericString = "0123456789";
        StringBuilder sb = new StringBuilder(11);

        for (int i = 0; i < 11; i++) {
          int index = (int)(numericString.length()* Math.random());
          sb.append(numericString.charAt(index));
        }
        parcel.setTrackId(sb.toString());

		log.debug("parcel:"+parcel);
		try{
	    	//rateService.getRate(parcel);
			log.debug("going to save details");
			parcel = parcelService.saveParcelDetails(parcel, loginedUser.getUsername());
			log.debug("details saved");

			if(paperSize.equals("A6")) {
			labelGenerator.generateLabel(parcel);
			}
			else if(paperSize.equals("A5")) {
				a5LableGenerator.generateLabel(parcel);
			}
			List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved", Enum.valueOf(Status.class,"ACTIVE"));
			dailyData = parcelService.getParcelCountCollection(clientList.get(0).getPostalCode());
		}
		catch(IOException exception){
			log.error("Label Generation Exception:::::"+exception);
		}
		session.setAttribute("parcel", "");
        return dailyData;
    }

	/**
	 * To update the parcel print count on re-printing of Parcel
	 * @param trackId
	 */
	@RequestMapping(value = "/saveReprintParcelDetails", method = RequestMethod.GET)
    public void saveReprintParcelDetails(String trackId, String paperSize) {

    	log.info("inside saveReprintParcelDetails for parcel trackId:: "+trackId);
   		Parcel parcelDetail=null;
		try {
			parcelDetail = parcelService.setReprintDetails(trackId,paperSize);
		} catch (JsonProcessingException e) {
			log.error("FETCHING PARCEL EXCEPTION:::::",e);
		}
   		log.debug("Saving Reprint parcel details for trackId:{}", trackId);
    	try{
			if(paperSize.equals("A6")) {
				labelGenerator.generateLabel(parcelDetail);
			}
			else if(paperSize.equals("A5")) {
				a5LableGenerator.generateLabel(parcelDetail);
			}
		}
		catch(Exception exception){
			log.error("Label Regeneration Exception:::::"+exception.getMessage());
		}
    }

	@RequestMapping(value = "/getParcelDetails", method = RequestMethod.GET)
   	public Parcel getParcelDetails(String trackId) {
   		Parcel parcelDetail = parcelService.fetchParcelDetail(trackId);
   		log.debug("Returning parcel details for trackId:{}", trackId);
   		return parcelDetail;
   	}

	@RequestMapping(value = "/cancelParcel", method = RequestMethod.GET)
	public void cancelParcel(String trackId) {
		parcelService.setParcelStatusVoid(trackId);
		log.debug("Cancelled parcel with trackId:{}", trackId);
	}

	@RequestMapping(value = "/getSubService", produces = "application/json", method = RequestMethod.POST)
	public List<ServicesVo> getSubService(@RequestParam Long serviceId) {
		return parcelService.getSubServices(serviceId);
	}

    @RequestMapping(path = "/listParcelReport", method = RequestMethod.GET)
    public List<Parcel> getReport(@RequestParam(value = "fromdate") String fromdate, @RequestParam(value = "todate") String todate, Model model) throws ParseException {
        log.info("inside listParcelReport");
        //string to date
        java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
        java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

        java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
        java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

        Timestamp ts = new Timestamp(sqlfromDate.getTime());
        Timestamp ts1 = new Timestamp(sqltoDate.getTime());
        List<Parcel> page = parcelService.getAllParcelBetweenDates(ts, ts1);
        log.info("returning listParcelReport");
        return page;

    }

    @RequestMapping(path= "/downloadPdf",method=RequestMethod.GET)
    public void getreportPdf(HttpSession session, HttpServletResponse response,@RequestParam(value="fromdate") String fromdate, @RequestParam(value="todate") String todate,Principal principal) throws Exception {

        log.info("inside downloadPdf controller");
		//convert list into pdf
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		parcelService.generateParcelReport(fromdate, todate,loginedUser);
        log.info("pdf file generated");
		//
		try {

			String fileName = "reportPdf.pdf";
			File fileToDownload = new File(exportfilepath + fileName);

			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IO.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (Exception exception) {
			log.info(exception.getMessage());
		}
        log.info("Downloding Pdf File ");


	}
    @RequestMapping(path= "/downloadExcel",method=RequestMethod.GET)
    public void getreportExcel(HttpSession session, HttpServletResponse response,@RequestParam(value="fromdate") String fromdate, @RequestParam(value="todate") String todate) throws Exception {

        log.info("inside downloadExcel controller");
        //convert list into Excel


        java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
        java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
        java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

        Timestamp ts = new Timestamp(sqlfromDate.getTime());
        Timestamp ts1 = new Timestamp(sqltoDate.getTime());

        parcelService.generateParcelReportExcel(ts,ts1);
        log.info("excel file generated");
        //
        try {

            String fileName = "reportExcel.xlsx";
            File fileToDownload = new File(exportfilepath + fileName);

            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            IO.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
        log.info("Downloding Excel File ");
    }

    @RequestMapping(value="/getAllParcel",method=RequestMethod.GET)
    public List<Parcel> getAllParcel( Model model , Principal principal){
    	log.info("inside get all parcel");
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	List<Parcel> pList=parcelService.getAllParcelsList();
    	log.info("inside get all parcel"+pList);
    	model.addAttribute("loginedUser", loginedUser);
      //  model.addAttribute("role", loginedUser.getRole().getName());
    	return pList;
    }

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() throws Exception
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() throws Exception
	{
		//CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		//Client sysClient = parcelService.getClientDetails();
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		//credentialsProvider.setCredentials(AuthScope.ANY,
				//new UsernamePasswordCredentials(sysClient.getClientId(), sysClient.getClientToken() + ";"
						//+ machineId.substring(machineId.length() - 38, machineId.length() - 2)));

		HttpClient client = HttpClientBuilder
				.create()
				//.setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	private RestTemplate restTemplate() throws Exception {
		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory();
		return new RestTemplate(factory);
	}


	//create header for rest template
	private HttpHeaders createHeaders(String jwtToken)
	  { return new HttpHeaders() {
		  /**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			  setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			  String authHeader = "Bearer " + jwtToken;
			  add("Authorization", authHeader );
	      }
	    };
	  }

	 private Parcel rateApiRestCall (Parcel parcel,String token,HttpSession session) throws Exception {

		 HttpEntity<Object> httpEntity=new HttpEntity<Object>(parcel,createHeaders(token));
			RestTemplate restTemplate = restTemplate();
			ResponseEntity<Parcel> response=restTemplate.exchange(rateURL+"/parcel/getRate", HttpMethod.POST, httpEntity, Parcel.class);
			parcel=response.getBody();
			session.setAttribute("parcel", parcel);
			return parcel;
	 }

}

