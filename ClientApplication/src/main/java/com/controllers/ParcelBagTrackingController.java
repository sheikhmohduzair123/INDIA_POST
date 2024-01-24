package com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.IO;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.BagStatus;
import com.constants.PStatus;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.MasterAddressRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.services.ParcelService;
import com.services.MasterAddressService;
import com.services.ReportService;
import com.services.TrackingService;
import com.util.WebUtils;
import com.vo.TrackingVo;

import net.sf.jasperreports.engine.JRException;


@Controller
@Profile("server")
@RequestMapping("/pbTracking")
public class ParcelBagTrackingController {

	protected Logger log = LoggerFactory.getLogger(ParcelBagTrackingController.class);

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	private TrackingCSRepository trackingCSRepo;

	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private RmsRepository rmsrepo;

	@Autowired
	private MasterAddressRepository masterAddressRepo;

	@Autowired
	private MasterAddressService masterAddress;

	@Value("${reportexport.path}")
    private String exportfilepath;

	//With authentication
	@RequestMapping("/trackWithParcelId")
    public String trackWithParcelId(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning track With Parcle Id");
        return "trackWithParcelId";
	}

	//Without authentication
	@RequestMapping("/trackParcel")
    public String trackParcel(Model model) {
        log.info("Returning track With Parcle Id");
        return "trackWithParcelId";
	}

	@RequestMapping("/trackWithBagId")
    public String trackWithBagId(Model model, Principal principal) {
		 com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());

        log.info("Returning track with Bag Id ");
        return "trackWithBagId";
	}

	@ResponseBody
	@RequestMapping(value = "/getDetailsWithParcelId", method = RequestMethod.GET)
	public List<TrackingDetails> getDetailsWithParcelId(Parcel trackId, Model model) {
		List<TrackingDetails> trackDetails = trackingService.fetchDetailsWithParcelId(trackId);
		log.debug("Returning parcel details for trackId:{}", trackId);
   		return trackDetails;
	}

	@ResponseBody
	@RequestMapping(value = "/getDetailsWithBagId", method = RequestMethod.GET)
	public Map<Object, List<TrackingDetails>> getDetailsWithBagId(String trackId, Model model) {
		Map<Object, List<TrackingDetails>> trackDetails = trackingService.fetchDetailsWithBagId(trackId);
		log.debug("Returning Bag details for trackId:{}", trackId);
    	return trackDetails;
	 }

	@ResponseBody
	@RequestMapping(value = "/getRMSNameByRMSType", method = RequestMethod.GET)
	public List<RmsTable> getRMSNameByRMSType(@RequestParam(value = "rmsType") String rmsType, Model model, Principal principal) {
		List<RmsTable> rmsTable = reportService.getRMSNameByRMSType(rmsType);
		model.addAttribute("rmsTable", rmsTable);
		log.info("Get RMS Name By RMS type ");
		return rmsTable;
	}



	//Loading Dispatch Report Page
	@RequestMapping("/dispatchParcelReport")
    public String dispatchParcelReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();

		if(loginedUser.getRole().getName().equals("ROLE_PO_USER") ) {
				List<MasterAddress> poName = trackingService.findByPostalCode(loginedUser.getPostalCode());
				model.addAttribute("poName", poName.get(0).getSubOffice());
		}
		else if(loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
				RmsTable rmsData=trackingService.findRmsByRmsID(loginedUser.getRmsId());
				model.addAttribute("RMSname", rmsData.getRmsName());
				model.addAttribute("RMStype", rmsData.getRmsType());
		}

		        model.addAttribute("user", loginedUser.getName());
		        model.addAttribute("role", loginedUser.getRole().getName());
		        model.addAttribute("roleId", loginedUser.getRole().getId());
		        List<RmsTable> rmsList = trackingService.fetchRmsList();
		        List<MasterAddress> pOList = trackingService.fetchPOList();
				model.addAttribute("pOList", pOList);
				model.addAttribute("rmsList", rmsList);
		        log.info("Returning Dispatch Report Page..");
		        return "dispatchParcelReport";
    }

	@RequestMapping(value="/dailyDispatchReportAdmin", method = RequestMethod.GET)
	@ResponseBody
	public String dailyDispatchReport( @RequestParam("rmsName") Long rmsName,@RequestParam("poCode") Integer poCode, @RequestParam(value="fromdate") String fromdate,
	@RequestParam(value="todate") String todate,@RequestParam(value = "parcelStatus") PStatus parcelStatus, Principal principal, Model model) throws Exception {
		Long postalCode = 0L;
		if(poCode==null) {
		}else {
		 postalCode=Long.valueOf(poCode);
		}
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		log.info("inside dailyDispatchReport");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dat = simpleDateFormat.format(new Date());

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
	    Map<String, Object> report = trackingService.getAlldailyDispatchReportByCurrentDate(ts1,ts,loginedUser,postalCode,rmsName,parcelStatus);

	    log.info("returning dailyDispatchReport");
	    String json = "";
			try {

	    	json = mapper.writeValueAsString(report);

	    } catch (Exception e) {
	        System.out.println("error"+e);
	    }
        return json;
	}

	//Search Bag
	@RequestMapping(value = "/searchBag", method = RequestMethod.GET)

	public String searchBag(Model model, Principal principal) {
		log.debug("inside search Bag::");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		 log.info("Returning Search Bag Page");
		return "searchBag";
	}

	@RequestMapping(value = "/findBagDetails", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> findBagDetails(@RequestParam(value = "bagId") String bagId, Model model, Principal principal) {
		log.debug("inside search Bag::");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<TrackingVo> bagList = parcelService.findBagDetailsWithBagId(bagId, loginedUser);
		return bagList;
	}

	@RequestMapping(value = "/findRecentTransaction", method = RequestMethod.GET)
	@ResponseBody
	//public Map<Object, List<TrackingCS>> findRecentTransaction( Model model, Principal principal) {
	public List<TrackingCS> findRecentTransaction( Model model, Principal principal) {
		log.debug("inside search Bag::");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		//Map<Object, List<TrackingCS>> recentTransaction = parcelService.fetchRecentTransactionOfBag(loginedUser);
		List<TrackingCS> recentTransaction = parcelService.fetchRecentTransactionOfBag(loginedUser);
		return recentTransaction;
	}

	//End Search Bag

	@RequestMapping("/viewBagList")
    public String viewBagList(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		if(loginedUser.getRole().getName().equalsIgnoreCase("ROLE_PO_USER"))
		{
			model.addAttribute("usertype", loginedUser.getPostalCode());
			MasterAddress address = masterAddress.getPostalData((int)loginedUser.getPostalCode());
			model.addAttribute("userlocation", address.getSubOffice()+" ("+address.getPostalCode()+")");
		}
		else if(loginedUser.getRole().getName().equalsIgnoreCase("ROLE_RMS_USER"))
		{
			model.addAttribute("usertype", loginedUser.getRmsId());
			RmsTable rms = masterAddress.findRMSByRMSId(loginedUser.getRmsId());
			model.addAttribute("userlocation", rms.getRmsName()+" ("+rms.getRmsType()+")");
		}
        log.info("Returning Bag List View for "+loginedUser.getName()+" ("+loginedUser.getRole().getDisplayName()+")");
        return "viewBagList";
	}

	@RequestMapping(path = "/getBagList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<TrackingCS>> getBagList(Principal principal, @RequestParam String location, @RequestParam String locationType) throws ParseException {
			log.info("inside fetch bag list data");

			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			try {
				return reportService.getBagList(location,locationType,loginedUser);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

	@RequestMapping(path = "/getParcelList", method = RequestMethod.GET)
	@ResponseBody
	public Map<Parcel, List<TrackingCS>> getParcelList(Principal principal, @RequestParam String location, @RequestParam String locationType) throws ParseException {
			log.info("inside fetch parcel list data");

			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			return reportService.getParcelList(location,locationType,loginedUser);
	}

	@RequestMapping(value="/getPostalCodedata", method = RequestMethod.GET)
	@ResponseBody
	public MasterAddress getPostalCodedata(@RequestParam int pincode){
			log.info("inside fetch postal code data");
			MasterAddress p =  masterAddress.getPostalData(pincode);
			return p;
	}

	@RequestMapping(path= "/getRMSdata", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public List<RmsTable> getRMSdata(Principal principal)
    {
    	log.info("inside get rms");
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    	List<RmsTable> list = trackingService.getRMSdata();

    	RmsTable loginedRms=null;
    	for (RmsTable rms : list) {
			if(rms.getId().equals(loginedUser.getRmsId()))
			{
				loginedRms = rms;
			}
		}
    	list.remove(loginedRms);

    	/*List<String> rmsTypes=new ArrayList<String>();


		 * //without java 8 for (RmsTable rms : list) {
		 * if(!rmsTypes.contains(rms.getRmsType())) { rmsTypes.add(rms.getRmsType()); }
		 * }
		 */
    	List<RmsTable> list1 = list
                .stream()
                .filter(distinctByKey(RmsTable::getRmsType))
                .collect(Collectors.toList());
    	return list1;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
      Map<Object, Boolean> seen = new ConcurrentHashMap<>();
      return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @RequestMapping(value = "/bagParcelListPrint", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String>  bagParcelListPrint( HttpServletResponse response,@RequestParam(value = "bagId") String bagId,Principal principal,Model model) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);

		model.addAttribute("userInfo",userInfo);
		try {

			String fileName = "bagParcelList.pdf";
			File fileToDownload = new File(exportfilepath + fileName);

			trackingService.getAllParcel(bagId);

			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IO.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
			log.info("Downloding Pdf File ");
			return new ResponseEntity<String>("fileDownload",HttpStatus.OK);
		} catch (IOException ioException) {
			log.error(ioException.getLocalizedMessage());
			return new ResponseEntity<String>("IOException", HttpStatus.NOT_FOUND);
		}
		catch (JRException e) {
			// TODO: handle exception
			log.error("JRException:: " , e);
			return new ResponseEntity<String>("JRException", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}

    @RequestMapping(value = "/bagLabel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String>  bagLabel( HttpServletResponse response,@RequestParam(value = "bagId") String bagId,Principal principal,Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo",userInfo);
		try {

			String fileName = "bagLabel.pdf";
			File fileToDownload = new File(exportfilepath + fileName);
			trackingService.getBagLabel(bagId,loginedUser);
			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			IO.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
			log.info("Downloding Pdf File ");
			return new ResponseEntity<String>("fileDownload",HttpStatus.OK);
		} catch (IOException ioException) {
			log.error(ioException.getLocalizedMessage());
			return new ResponseEntity<String>("IOException", HttpStatus.NOT_FOUND);
		}
		catch (JRException e) {
			// TODO: handle exception
			log.error("JRException:: " , e);
			return new ResponseEntity<String>("JRException", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

