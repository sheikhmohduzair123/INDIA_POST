package com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
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
import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.repositories.PostalServiceRepository;
import com.services.ReportService;
import com.services.TrackingService;
import com.util.WebUtils;
import com.vo.ParcelVo;
import com.vo.TrackingVo;

import net.sf.jasperreports.engine.JRException;

@Controller
@Profile("server")
@RequestMapping("/po")
public class PoController {

	private static final Logger log = LoggerFactory.getLogger(PoController.class);

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Value("${reportexport.path}")
	private String exportfilepath;

	@RequestMapping(value = "/poBagging", method = RequestMethod.GET)
	public String getBaggingPage(Model model, Principal principal) {
		log.info("inside getBaggingPage");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		return "Bagging";
	}

	@RequestMapping("/parcelDispatchStatus")
	public String rmsPage(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispatch update Page ");
		return "dispatchUpdate";

	}

	@RequestMapping(value = "/getAllParcel", method = RequestMethod.POST)
	@ResponseBody
	public List<ParcelVo> getAllParcel(Model model, Principal principal) {
		log.info("inside get all parcel");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<ParcelVo> pList = trackingService.getAllParcels(loginedUser.getPostalCode());
		log.info("inside get all parcel" + pList);
		model.addAttribute("loginedUser", loginedUser);
		model.addAttribute("role", loginedUser.getRole().getName());
		return pList;
	}

	@RequestMapping(value = "/savePoBag", method = RequestMethod.POST)
	@ResponseBody
	public List<TrackingVo> savePobag(@RequestParam Map<String, String> bagMap, Principal principal, Model model)
			throws ParseException, JsonMappingException, JsonProcessingException {
		log.info("inside save po bag");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		List<TrackingVo> trackingCs = trackingService.saveTrackingDetails(bagMap, loginedUser);
		if(trackingCs.size() > 0 ) {
			return trackingCs;
		}

		return null;
	}

	@RequestMapping(value = "/getDisptachStatusDetails", method = RequestMethod.POST)
	@ResponseBody
	public String getDispatchUpdateStatus(String trackId, String dispatchStatus, String date, String timeValue,
			String timeValue1, Principal principal, Model model) throws java.text.ParseException {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		String status = trackingService.getDispatchStatus(trackId, dispatchStatus, loginedUser, date, timeValue,
				timeValue1);
		return status;
	}

	@RequestMapping(value = "/poBagReports", method = RequestMethod.GET)
	public String poBagReports(Model model, Principal principal) {
		log.info("inside get Post Office Bag Reports Page");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("postalcode", loginedUser.getPostalCode());
		model.addAttribute("role", loginedUser.getRole().getName());
		return "poBagReports";
	}

	@RequestMapping(path = "/getPoBagReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<TrackingVo>> getPoBagReport(Principal principal, @RequestParam String fdate,
			@RequestParam String tdate, @RequestParam BagStatus reportType) throws ParseException {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		try {
			return reportService.getPoBagReport(fdate, tdate, reportType, loginedUser);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			log.error("Error generating Postoffice Bags report:: ",e);
		}
		return null;
	}

	@RequestMapping(value = "/parcelsReportPage", method = RequestMethod.GET)
	public String parcelsReportMisPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());

		List<MasterAddress> poName = trackingService.findByPostalCode(loginedUser.getPostalCode());
		model.addAttribute("poName", poName.get(0).getSubOffice());
		model.addAttribute("userPostalCode", loginedUser.getPostalCode());

		List<Services> postalServicel = postalServiceRepository.findByParentServiceIdNullAndStatus(Status.ACTIVE);

		List<PStatus> pStatusList = new ArrayList<>();
		pStatusList.add(PStatus.IN);
		pStatusList.add(PStatus.OUT);
		pStatusList.add(PStatus.BAGGED);
		pStatusList.add(PStatus.UNBAGGED);
		pStatusList.add(PStatus.OUT_FOR_DELIVERY);
		pStatusList.add(PStatus.RETURNED);
		pStatusList.add(PStatus.DELIVERED);

		List<Services> postalSubService = postalServiceRepository.findByParentServiceIdNotNullAndStatus(Status.ACTIVE);
		List<Services> postalSubServiceList = postalSubService.stream().filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());

		List<Services> postalServiceList = postalServicel.stream().filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());
		model.addAttribute("subservicesList", postalSubServiceList);
		model.addAttribute("pStatusList", pStatusList);
		model.addAttribute("postalServiceList", postalServiceList);
		return "parcelsReportPage";
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@RequestMapping(value = "/getParcelsReportOnMis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getParcelsReportOnMis(@RequestParam(value = "fromdate") String fromdate,
			@RequestParam(value = "todate") String todate, @RequestParam(value = "status") PStatus status,
			@RequestParam("parcelStatus") String parcelStatus, Model model, Principal principal,
			@RequestParam List<Long> services, @RequestParam List<Long> subservices) throws java.text.ParseException {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());

		Map<String, Object> misReport = new HashMap<>();
		List<Services> serviceCode = postalServiceRepository.findByIdInAndStatus(services, Status.ACTIVE);
		// if some services are disabled, find from disabled service
		if (!(serviceCode.size() == services.size())) {
			List<Long> serviceId = new ArrayList<Long>();
			for (Services s : serviceCode) {
				serviceId.add(s.getId());
			}
			List<Long> parcelServices = subservices;
			parcelServices.removeAll(serviceId);
			for (Long serviceid : parcelServices) {
				Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
				serviceCode.add(service);
			}
		}

		List<Services> subServiceCode = postalServiceRepository.findByIdInAndStatus(subservices, Status.ACTIVE);

		// if some services are disabled, find from disabled service
		if (!(subServiceCode.size() == subservices.size())) {
			List<Long> subserviceId = new ArrayList<Long>();
			for (Services s : subServiceCode) {
				subserviceId.add(s.getId());
			}
			List<Long> parcelSubServices = subservices;
			parcelSubServices.removeAll(subserviceId);
			for (Long serviceid : parcelSubServices) {
				Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
				subServiceCode.add(service);
			}
		}

		String serviceCodeName = "";
		for (Services services1 : serviceCode) {
			serviceCodeName += services1.getServiceCode() + "/";
		}
		for (Services servicesSub : subServiceCode) {
			serviceCodeName += "," + servicesSub.getServiceCode();
		}
		misReport.put("servicecode", serviceCodeName);

		List<ParcelVo> reportdata = trackingService.findParcelsReportData(fromdate, todate, services, loginedUser,
				status, subservices, parcelStatus);
		misReport.put("reportmisdata", reportdata);
		return misReport;
	}

	@RequestMapping(value = "/outForDelivery", method = RequestMethod.GET)
	public String outForDelivery(Model model, Principal principal) {
		log.info("inside get Post Office Bag Reports Page");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("postalcode", loginedUser.getPostalCode());
		model.addAttribute("role", loginedUser.getRole().getName());
		return "outForDelivery";
	}

	@RequestMapping(value = "/getParcelDetails", method = RequestMethod.POST)
	@ResponseBody
	public ParcelVo getParcelDetails(@RequestParam String parcelid, Principal principal) {
		log.info("inside getting parcel details from tracking for outof delivery");
		ParcelVo pDetails = trackingService.findByParcelId(parcelid);
		return pDetails;
	}

	@RequestMapping(value = "/updateOutForDelivery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> receiveBags(@RequestParam(value = "parcelids[]") List<String> parcelids,
			Principal principal, HttpServletResponse response) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		log.info("inside update and print Out For Delivery list from " + loginedUser.getRole().getName());
		List<TrackingCS> list = trackingService.updateOutForDelivery(parcelids, loginedUser);

		if (!list.isEmpty()) {
			try {
				String fileName = "parcel-dispatch-report.pdf";
				File fileToDownload = new File(exportfilepath + fileName);

				trackingService.generateParcelDispatchReport(loginedUser, parcelids);

				InputStream inputStream = new FileInputStream(fileToDownload);
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				IO.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
				inputStream.close();
				log.info("Downloding Out for Delivery Pdf File ");
				return new ResponseEntity<String>("fileDownload", HttpStatus.OK);

			} catch (IOException ioException) {
				log.error(ioException.getLocalizedMessage());
				return new ResponseEntity<String>("IOException", HttpStatus.NOT_FOUND);
			} catch (JRException e) {
				// TODO: handle exception
				log.error("JRException:: ", e);
				return new ResponseEntity<String>("JRException", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			log.error("No parcel ids found for updating out for delivery");
			return new ResponseEntity<String>("IOException", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value = "/disposeEmptyBag", method = RequestMethod.GET)
	public String getEmptyBagDisposePage(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	    //if user has logined first time & passowrd has not been changed, forcefully movw to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag Page ");
		
		
		return "disposeEmptyBag";
	}
	
	@RequestMapping(value = "/disposeEmptyBagList", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> getEmptyBagDisposeList(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		
		return trackingService.getAllEmptyBagList(loginedUser);
	}
	
	@RequestMapping(value = "/disposedEmptyBag", method = RequestMethod.POST)
	@ResponseBody
	public String disposedEmptyBag(@RequestParam(value = "emptyBagId[]") List<String> emptyBagId,Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		trackingService.disposedEmptyBag(emptyBagId,loginedUser);
		
		return "Disposed Successfully";
	}
	
	@RequestMapping(value = "/disposedEmptyBagList", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> disposedEmptyBag(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		
		
		return trackingService.disposedEmptyBagList(loginedUser);
	}



}
