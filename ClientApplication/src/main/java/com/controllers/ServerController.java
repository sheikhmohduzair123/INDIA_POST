package com.controllers;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;

import com.constants.PStatus;
import com.domain.*;
import com.repositories.PostalServiceRepository;
import com.repositories.SUserRepository;
import com.vo.ParcelVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.BagStatus;
import com.constants.Status;
import com.services.ClientService;
import com.services.MasterAddressService;
import com.services.ReportService;
import com.services.TrackingService;
import com.services.UserRegistrationService;
import com.util.WebUtils;
import com.vo.CreateUserVo;

import static com.controllers.ParcelBagTrackingController.distinctByKey;


@Controller
@Profile("server")
@RequestMapping("/server")
public class ServerController {

	protected Logger log = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private SUserRepository userRepository;

    @Autowired
    private UserRegistrationService registrationService;

    @Autowired
	private PostalServiceRepository postalServiceRepository;



	@RequestMapping(value = "/saveClientDetails", method = RequestMethod.GET)
	@ResponseBody
	public Client saveClientDetails(@ModelAttribute Client client, Principal principal) throws Exception {
		log.debug("inside save client::"+client.getClientName()+"  "+client.getPostalCode()+" "+client.getDistrict());
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		try {
			client = clientService.saveClientDetails(client,loginedUser.getUsername());
		} catch (IOException e) {
			log.error("Error occured while saving client "+client.getClientId()+" ::",e);
		}

		log.debug("saving client::"+client.getClientName());
		return client;
	}

	@RequestMapping(value = "/checkExistingUserInPostalCode", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingUserInPostalCode(@ModelAttribute Client client) {
		log.debug("inside checking if counter name already exist for client::"+client.getClientName()+" and pincode:: "+client.getPostalCode()+" "+client.getDistrict());

		Boolean existingCntrInPostalCode = clientService.fetchExistingCounterInPostalCode(client.getClientName(), client.getPostalCode());
		if(existingCntrInPostalCode)
			return true;
		else
			return false;
	}

	@RequestMapping(value="/expireClient", method = RequestMethod.GET)
	@ResponseBody
	public void expireClient(String clientId, Principal principal){
			log.info("inside changing status (active/deactive) for clientid::"+clientId);
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			clientService.expireClient(clientId, loginedUser.getUsername());
	}

	@RequestMapping(value="/approveClientDetails", method = RequestMethod.GET)
	@ResponseBody
	public void approveClientDetails(String clientId, String remarks, Principal principal){
			log.info("inside approving clientid::"+clientId);
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			try {
				clientService.approveClientDetails(clientId, remarks, loginedUser.getUsername());
			} catch (Exception e) {
				log.debug("client id::"+clientId+" is not avtive");
				e.printStackTrace();
			}
	}

	@RequestMapping(value="/rejectClientDetails", method = RequestMethod.GET)
	@ResponseBody
	public void rejectClientDetails(String clientId, String remarks, Principal principal){
			log.info("inside rejecting clientid::"+clientId);
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			clientService.rejectClientDetails(clientId, remarks, loginedUser.getUsername());
	}

	@RequestMapping(value="/fetchClientDetails", method = RequestMethod.GET)
	@ResponseBody
	public Client fetchClientDetails(String clientId) {
			log.info("fetch clientid::"+clientId +"details");
			return clientService.findClientDetail(clientId);
	}

	@RequestMapping(value="/getClientList", method = RequestMethod.GET)
	@ResponseBody
	public List<Client> getClientList(@RequestParam String type){
			log.info("inside fetch client status list");
			List<Client> clients =  clientService.getClientList(type);
			return clients;

	}

	@RequestMapping(value="/registerClient", method = RequestMethod.GET)
	public String registerClient(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.debug("opened register client page");
		return "registerClient";
	}


	@RequestMapping(value="/clientListAll", method = RequestMethod.GET)
	public String clientListAll(Model model, Principal principal) {
		log.debug("inside fetching all client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type","all");
		return "clientList";
	}

	@RequestMapping(value="/clientListPending", method = RequestMethod.GET)
	public String clientListPending(Model model, Principal principal) {
		log.debug("inside fetching pending client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type","approval required");
		return "clientList";
	}

	@RequestMapping(value="/clientListApproved", method = RequestMethod.GET)
	public String clientListApproved(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type","approved");
		return "clientList";
	}

	@RequestMapping(value="/clientListNonCommissioned", method = RequestMethod.GET)
	public String clientListNonCommissioned(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type","unauthorized");
		return "clientList";
	}

	@RequestMapping(value="/clientListRejected", method = RequestMethod.GET)
	public String clientListRejected(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("type","rejected");
        model.addAttribute("role", loginedUser.getRole().getName());

		return "clientList";
	}

	@RequestMapping(value="/clientListExpired", method = RequestMethod.GET)
	public String clientListExpired(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("type","expired");
        model.addAttribute("role", loginedUser.getRole().getName());
		return "clientList";
	}

	// Show user Registration Page
		@RequestMapping("/registerUserPage")
		public String createUserPage(@ModelAttribute com.domain.User user, Model model, Principal principal) {
			com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
			model.addAttribute("user", loginedUser.getName());
	        model.addAttribute("role", loginedUser.getRole().getName());
			return "createUser";
		}

   	// Save User On Server
		@RequestMapping(value = "/RegisterUser", method = RequestMethod.GET)
		@ResponseBody
		@Transactional
		public String createUser(CreateUserVo userVo,Model model, Principal principal) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			String status = registrationService.registerUser(userVo,loginedUser);
			return status;
		}

	  // update User controller
		@RequestMapping(value = "/UpdateUser", method = RequestMethod.GET)
		@ResponseBody
		@Transactional
		public Boolean updateUser(CreateUserVo userVo,@RequestParam("uId") Integer uId, Model model, Principal principal) throws ParseException  {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			User UserObj=userRepository.getOne(uId);
			//String dob= userVo.getDateOfBirth();
		    if((UserObj.getName().equals(userVo.getName()))&&(UserObj.getDob().toString().equals(userVo.getDateOfBirth()))

			 &&((UserObj.getPostalCode()+"").equals(userVo.getPostalCode())) &&(((UserObj.getRole()).equals((userVo.getRole())))))
			 {
		    	return false;
		     }

		    else
				 registrationService.updateUser(userVo,uId, loginedUser);
				 return true;
         }



		// for disable or enable user
	  	@RequestMapping(value = "/enabaleAndDisableUser", method = RequestMethod.GET)
	  	@Transactional
	  	public String disableUser(@RequestParam(value = "id") Integer id, Principal principal, Model model) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			log.info("User selected for disable enable service");
	  		registrationService.enableAndDisableUserService(id,loginedUser);
	  		log.info("selected user disabled enable service");
	  		return "redirect:showUserDetails";
	  	}


	  	// for Change user Status
	  		@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	  		@Transactional
	  		public String changeStatus(@RequestParam(value = "id") Integer id, Principal principal, Model model) {
				User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("User selected for change status");
	  			registrationService.updateUserStatus(id,loginedUser);
	  			return "redirect:showUserDetails";
	  		}

	  		// for re-mail user
	  				@RequestMapping(value = "/resendMail", method = RequestMethod.GET)
	                 @ResponseBody
	                 public String resendMail(@RequestParam(value = "id") Integer id, Principal principal, Model model) {
						User loginedUser = (User) ((Authentication) principal).getPrincipal();
						log.info("User selected for re-sent mail");
	  					String status=registrationService.resendMailUserService(id,loginedUser);
	  					return status;
	  				}

	   // load userUpdate page
	   	@RequestMapping("/showUserDetails")
	   	@Transactional
	   	public String showUserDetails(@ModelAttribute("objUser") User user, Model model, Principal principal) {
	   		User loginedUser = (User) ((Authentication) principal).getPrincipal();
	   		model.addAttribute("user", loginedUser.getName());
	        model.addAttribute("role", loginedUser.getRole().getName());
	   		log.info("inside user update page loading...");
	   		return "createUser";
	   	}

	  	// for showing userTable filter wise
	  	@RequestMapping(value = "/showUserTable", method = RequestMethod.GET)
	  	@ResponseBody
	  	public List<User> showUserTable(@RequestParam String status,Model model, Principal principal) {
	  		User loginedUser = (User) ((Authentication) principal).getPrincipal();
	  		log.info("inside user table controller");
	  		if (status.equals("ALL")) {

	  		   return (List<User>) registrationService.findAll(loginedUser);
	  	    }

	  		if(status.equals("TRUE")) {
	  			return (List<User>) registrationService.findAllByEnableAccoutTrue(loginedUser);
	  		}

	  		if(status.equals("FALSE")) {
	  			return (List<User>) registrationService.findAllByEnableAccoutFalse(loginedUser);
	  		}

		/*
		 * if(status.equals("ACTIVE")){ return (List<User>)
		 * registrationService.findAllByStatusActive(loginedUser); }
		 *
		 * if(status.equals("DISABLED")){ return (List<User>)
		 * registrationService.findAllByStatusDeactive(loginedUser); }
		 */
			return null;

	  	}


    @RequestMapping("/getAllRms")
	@ResponseBody
	public List<RmsTable> getAllRoles() {
		List<RmsTable> allRoles = registrationService.findAllRms();
		return allRoles;
	}


    @RequestMapping("/dailysummarypage")
    public String dailysummaryPage(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning Daily summaryPage ");

        return "dailySummary";
    }

    @RequestMapping("/collectionReport")
    public String collectionReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning collection report page");
        return "collectionReport";
    }

    @RequestMapping("/capacityReport")
    public String capacityReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning capacity report page");
        return "capacityReport";
    }

	/*
	 * @RequestMapping(value = "/getBagDetails", method = RequestMethod.POST) public
	 * String getBagDetails(@ModelAttribute("objThana") @Valid Thana objThana,
	 * BindingResult bindingResult, Model model, Principal principal) {
	 *
	 * if (bindingResult.hasErrors()) { User loginedUser = (User) ((Authentication)
	 * principal).getPrincipal(); List<District> districtList =
	 * masterAddressService.fetchDistrictList(); model.addAttribute("districtList",
	 * districtList); model.addAttribute("user", loginedUser.getName());
	 * log.info("validate Thana"); return "thana"; } User loginedUser = (User)
	 * ((Authentication) principal).getPrincipal(); List<Thana> data =
	 * masterAddressService.findThanaWithDistrictId(objThana.getDistrict().getId(),
	 * objThana.getThana(), Status.ACTIVE); if (data.size() == 0) {
	 * masterAddressService.saveThanaDetails(objThana, loginedUser.getUsername());
	 * return "redirect:showThana"; } else { List<District> districtList =
	 * masterAddressService.fetchDistrictList(); model.addAttribute("user",
	 * loginedUser.getName()); model.addAttribute("districtList", districtList);
	 * model.addAttribute("thanaName", objThana.getThana());
	 * model.addAttribute("error", data.size()); return "thana"; }
	 *
	 * }
	 *
	 *
	 */
	@RequestMapping("/dateWiseBagsReport")
    public String dateWiseBagsReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		List<RmsTable> rmsList = reportService.fetchRmsList();
		model.addAttribute("rmsList", rmsList);
        log.info("Returning track with Bag Id ");
        return "dateWiseBagsReport";
	}

	/*
	 * @ResponseBody
	 *
	 * @RequestMapping(value = "/getRMSNameByRMSType", method = RequestMethod.GET)
	 * public List<RmsTable> getRMSNameByRMSType(@RequestParam(value = "rmsType")
	 * String rmsType, Model model, Principal principal) { List<RmsTable> rmsTable =
	 * reportService.getRMSNameByRMSType(rmsType); model.addAttribute("rmsTable",
	 * rmsTable); log.info("Get RMS Name By RMS type "); return rmsTable; }
	 */

	@ResponseBody
	@RequestMapping(value = "/getReportByDate", method = RequestMethod.GET)
	public Map<Object, List<TrackingDetails>> getReportByDate(HttpSession session, HttpServletResponse response,@RequestParam(value="fromdate") String fromdate,
	@RequestParam(value="todate") String todate, Model model, @RequestParam(value = "rmsName") Long rmsId,
	@RequestParam(value = "bagStatus") BagStatus bagStatus) throws Exception {

		java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
        java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
        java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

        Timestamp ts1 = new Timestamp(sqlfromDate.getTime());
        Timestamp ts2 = new Timestamp(sqltoDate.getTime());

		Map<Object, List<TrackingDetails>> trackDetails = reportService.fetchBagInventoryWithRmsAndBagStatus(rmsId, bagStatus, ts1, ts2);
		log.debug("Returning Bag details for rmsId:{}", rmsId);

		return trackDetails;
	}


	///****For Post-Office Bags Reports****///
	 @RequestMapping("/dateWisePoBagsReport")
	    public String dateWisePoBagsReport(Model model, Principal principal) {
	        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
			model.addAttribute("user", loginedUser.getName());
			model.addAttribute("role", loginedUser.getRole().getName());
			List<MasterAddress> pOList = trackingService.fetchPOList();
			model.addAttribute("pOList", pOList);
	        log.info("Returning post-office bags report view ");
	        return "dateWiseBagsReportPO";
		}


		@ResponseBody
		@RequestMapping(value = "/POreportDatewise", method = RequestMethod.GET)
		public Map<Object, List<TrackingDetails>> getReportByDate(HttpSession session, HttpServletResponse response,@RequestParam(value="fromdate") String fromdate,
		@RequestParam(value="todate") String todate, Model model, @RequestParam(value = "poName") Integer postalCode,
		@RequestParam(value = "bagStatus") BagStatus bagStatus) throws Exception {

			java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
	        java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
			LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDateTime startDateTime = today.atTime(23, 59, 59);
			ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
			Date tdate = Date.from(zdt.toInstant());

			java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
	        java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

	        Timestamp ts1 = new Timestamp(sqlfromDate.getTime());
	        Timestamp ts2 = new Timestamp(sqltoDate.getTime());

			Map<Object, List<TrackingDetails>> bagDetailsPO = trackingService.datewiseBagsReportForPoUser(postalCode, bagStatus, ts1, ts2);
			log.debug("Returning Bag details for seclecting sub-office");
			return bagDetailsPO;
		}

	@RequestMapping(value = "/parcelReportMisAdmin", method = RequestMethod.GET)
	public  String getParcelReportMisforAdmin(Model model,Principal principal){
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();

		List<MasterAddress> pOList = trackingService.fetchPOList();
		model.addAttribute("pOList", pOList);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());

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
		List<Services> postalSubServiceList = postalSubService
				.stream()
				.filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());

		List<Services> postalServiceList = postalServicel
				.stream()
				.filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());
		model.addAttribute("postalSubServiceList", postalSubServiceList);
		model.addAttribute("pStatusList", pStatusList);
		model.addAttribute("postalServiceList", postalServiceList);


		return "parcelsReportPageAdmin";
	}

	@RequestMapping(value = "/getParcelsReportOnMis",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getParcelsReportOnMis(@RequestParam("poCode") Integer poCode,@RequestParam(value = "fromdate") String fromdate, @RequestParam(value = "todate") String todate, @RequestParam(value = "status") PStatus status, Model model, Principal principal, @RequestParam List<Long> services, @RequestParam List<Long> subservices) throws java.text.ParseException {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser); model.addAttribute("userInfo",userInfo);
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());

		Map<String,Object> misReport=new HashMap<>();
		List<Services> serviceCode = postalServiceRepository.findByIdInAndStatus(services,Status.ACTIVE);
		List<Services> subServiceCode = postalServiceRepository.findByIdInAndStatus(subservices,Status.ACTIVE);

		String serviceCodeName = "";
		for(Services services1: serviceCode){
			serviceCodeName+=services1.getServiceCode()+"/";
		}
		for(Services servicesSub: subServiceCode){
			serviceCodeName+=","+servicesSub.getServiceCode();
		}
		misReport.put("servicecode",serviceCodeName);


		List<ParcelVo> reportMis = trackingService.findParcelsReportDataAdmin(fromdate,todate,services,loginedUser,status,subservices,(long)poCode);
		misReport.put("reportmisdata",reportMis);
		return misReport;
	}



}

