package com.controllers;

import static com.controllers.ParcelBagTrackingController.distinctByKey;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.BagStatus;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Client;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.services.ClientService;
import com.services.MasterAddressService;
import com.services.ReportService;
import com.services.TrackingService;
import com.services.UserRegistrationService;
import com.util.WebUtils;
import com.vo.ClientVo;
import com.vo.CreateUserVo;
import com.vo.MasterAddressVo;
import com.vo.ParcelVo;
import com.vo.SyncTableVo;
import com.vo.TrackingVo;
import com.vo.UserVo;

@Controller
@Profile("server")
@RequestMapping("/server")
@Validated
public class ServerController {

	protected Logger log = LoggerFactory.getLogger(ServerController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private TrackingService trackingService;

	@Autowired
	private ReportService reportService;
 
    @Autowired
    private RmsRepository rmsRepository;
    
    @Autowired
    private MasterAddressService masterAddressService;

	@Autowired
	private UserRegistrationService registrationService;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private PostalServiceRepository postalServiceRepository;


	@RequestMapping(value = "/saveClientDetails", method = RequestMethod.POST)
	@ResponseBody
	public ClientVo saveClientDetails(@Valid @ModelAttribute Client client, Principal principal, Model model) throws Exception {
		log.debug("inside save client::" + client.getClientName() + "  " + client.getPostalCode() + " "
				+ client.getDistrict());
		
		Boolean existingCntrInPostalCode = clientService.fetchExistingCounterInPostalCode(client.getClientName(),
				client.getPostalCode());

		if(existingCntrInPostalCode) {
			ClientVo cl = new ClientVo();
			cl.setRemarks(client.getClientName()+"Already Exists");
    		return cl;
		}
		else {
			List<MasterAddress> list = masterAddressRepository.findByPostalCodeAndStatus(client.getPostalCode(), Status.ACTIVE);

	    	if(list != null && list.size() > 0){


			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			ClientVo cl = null;
			try {
				// client = clientService.saveClientDetails(client,loginedUser.getUsername());
				cl = clientService.saveClientDetails(client, loginedUser.getUsername());

			} catch (IOException e) {
				log.error("Error saving client details "+client.getClientToken(),e);
			}

			log.debug("saving client::" + client.getClientName());
			return cl;

	    	}else{
	    		ClientVo cl = new ClientVo();
	    		return cl;
	    	}
		}
		
	}

//	@RequestMapping(value = "/checkExistingUserInPostalCode", method = RequestMethod.POST)
//	@ResponseBody
//	public Boolean checkExistingUserInPostalCode(Client client) {
//		log.debug("inside checking if counter name already exist for client::" + client.getClientName()
//				+ " and pincode:: " + client.getPostalCode() + " " + client.getDistrict());
//
//		Boolean existingCntrInPostalCode = clientService.fetchExistingCounterInPostalCode(client.getClientName(),
//				client.getPostalCode());
//		if (existingCntrInPostalCode)
//			return true;
//		else
//			return false;
//	}

	@RequestMapping(value = "/expireClient", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> expireClient(String clientId,@NotBlank String remarks, Principal principal) {
		log.info("inside changing status (active/deactive) for clientid::" + clientId);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Client c = clientService.expireClient(clientId, remarks, loginedUser.getUsername());
		if (c.getClientStatus().equalsIgnoreCase("expired")) {
			log.info(c.getClientName() + "(" + c.getPostalCode() + ")" + "expired successfully");
		return new ResponseEntity<>(HttpStatus.OK);
		}else {
			log.info(c.getClientName() + "(" + c.getPostalCode() + ")" + "cannot be expired");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/approveClientDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> approveClientDetails(String clientId, String remarks, Principal principal) {
		log.info("inside approving clientid::" + clientId);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		try {
			Client c = clientService.approveClientDetails(clientId, remarks, loginedUser.getUsername());
			if (c.getClientStatus().equalsIgnoreCase("approved"))
			{
				log.info(c.getClientName() + "(" + c.getPostalCode() + ")" + "approved successfully");
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else
				return new ResponseEntity<String>("Another counter already regsitered on this machine", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.debug("client id::" + clientId + " is not avtive");
			log.error("Error:: ", e);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/rejectClientDetails", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> rejectClientDetails(String clientId,  @NotBlank String remarks, Principal principal) {
		log.info("inside rejecting clientid::" + clientId);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Client c = clientService.rejectClientDetails(clientId, remarks, loginedUser.getUsername());
		if (c.getClientStatus().equalsIgnoreCase("rejected")) {
			log.info(c.getClientName() + "(" + c.getPostalCode() + ")" + "rejected successfully");
		return new ResponseEntity<>(HttpStatus.OK);
		}else
			log.info(c.getClientName() + "(" + c.getPostalCode() + ")" + "cannot be rejected");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/fetchClientDetails", method = RequestMethod.POST)
	@ResponseBody
	public ClientVo fetchClientDetails(String clientId) {
		log.info("fetch clientid::" + clientId + "details");
		return clientService.findClientDetail(clientId);
	}

	@RequestMapping(value = "/getClientList", method = RequestMethod.POST)
	@ResponseBody
	public List<ClientVo> getClientList(@RequestParam String type) {
		log.info("inside fetch client status list");
		List<ClientVo> clients = clientService.getClientList(type);
		return clients;
	}

	@RequestMapping(value = "/registerClient", method = RequestMethod.GET)
	public String registerClient(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.debug("opened register client page");
		return "registerClient";
	}

	@RequestMapping(value = "/clientListAll", method = RequestMethod.GET)
	public String clientListAll(Model model, Principal principal) {
		log.debug("inside fetching all client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type", "all");
		return "clientList";
	}

	@RequestMapping(value = "/clientListPending", method = RequestMethod.GET)
	public String clientListPending(Model model, Principal principal) {
		log.debug("inside fetching pending client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type", "approval required");
		return "clientList";
	}

	@RequestMapping(value = "/clientListApproved", method = RequestMethod.GET)
	public String clientListApproved(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type", "approved");
		return "clientList";
	}

	@RequestMapping(value = "/clientListNonCommissioned", method = RequestMethod.GET)
	public String clientListNonCommissioned(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("type", "unauthorized");
		return "clientList";
	}

	@RequestMapping(value = "/clientListRejected", method = RequestMethod.GET)
	public String clientListRejected(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("type", "rejected");
		model.addAttribute("role", loginedUser.getRole().getName());

		return "clientList";
	}

	@RequestMapping(value = "/clientListExpired", method = RequestMethod.GET)
	public String clientListExpired(Model model, Principal principal) {
		log.debug("inside fetching approved client status list");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("type", "expired");
		model.addAttribute("role", loginedUser.getRole().getName());
		return "clientList";
	}

	// Show user Registration Page
	@RequestMapping(value = "/registerUserPage", method = RequestMethod.GET)
	public String createUserPage(@ModelAttribute com.domain.User user, Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		return "createUser";
	}

	// Save User On Server
	@RequestMapping(value = "/RegisterUser", method = RequestMethod.POST)
	@ResponseBody
	// @Transactional
	public ResponseEntity<String> createUser(@Valid CreateUserVo userVo, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();	
		String status= null;
		
		if((userVo.getRole().equalsIgnoreCase("Post Office User"))||(userVo.getRole().equalsIgnoreCase("Front Desk User"))) {
		
			List<MasterAddress> list = masterAddressRepository.findByPostalCodeAndStatus(Integer.parseInt(userVo.getPostalCode()), Status.ACTIVE);
			
			if(list.isEmpty() && list.size() <= 0){
    		
    		return new ResponseEntity<String>("Postal Code "+userVo.getPostalCode()+" Does Not Exists", HttpStatus.OK) ;
    		}
		}
    	status = registrationService.registerUser(userVo, loginedUser);
    	
    		return new ResponseEntity<String>(status, HttpStatus.OK);
		
	}

	// update User controller
	@RequestMapping(value = "/UpdateUser", method = RequestMethod.POST)
	@ResponseBody
	// @Transactional
	public ResponseEntity<String> updateUser(@Valid CreateUserVo userVo, Model model, Principal principal) throws ParseException {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		
		if((userVo.getRole().equalsIgnoreCase("Post Office User"))||(userVo.getRole().equalsIgnoreCase("Front Desk User"))) {
		List<MasterAddress> list = masterAddressRepository.findByPostalCodeAndStatus(Integer.parseInt(userVo.getPostalCode()), Status.ACTIVE);
		
    	if(list.isEmpty() && list.size() <= 0){
    
    		return new ResponseEntity<String>("Postal Code Does Not Exists", HttpStatus.OK) ;
     		}
    	}
			registrationService.updateUser(userVo, loginedUser);
    		return new ResponseEntity<String>("User update successfully", HttpStatus.OK);
	}

	// for disable or enable user
	@RequestMapping(value = "/enabaleAndDisableUser", method = RequestMethod.POST)
	// @Transactional
	public String disableUser(@RequestParam(value = "email") String email, Principal principal, Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		log.info(email + "::User to be enabled or disabled");
		UserVo status = registrationService.enableAndDisableUserService(email, loginedUser);
		if (!(status == null)) {
			log.info(email + ":: User status has been successfully changed");
			return "redirect:showUserDetails";
		}else
			log.info(email + ":: User status cannot be changed");

		return null;
	}

	// for Change user Status
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	// @Transactional
	public String deleteUser(@RequestParam(value = "email_id") String email, Principal principal, Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		log.info("User selected for change status");
		UserVo status = registrationService.updateUserStatus(email, loginedUser);
		if (!(status == null)) {
			log.info(email + ":: User has been deleted successfully");
		return "redirect:showUserDetails";
		}else
			log.info(email + ":: User cannot be deleted");
		return null;
	}

	// for re-mail user
	@RequestMapping(value = "/resendMail", method = RequestMethod.POST)
	@ResponseBody
	public String resendMail(@RequestParam(value = "email") String email, Principal principal, Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("User selected for re-sent mail");
		String status = registrationService.resendMailUserService(email, loginedUser);
		return status;
	}

	// load userUpdate page
	@RequestMapping("/showUserDetails")
	@Transactional
	public String showUserDetails(@ModelAttribute("objUser") User user, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("inside user update page loading...");
		return "createUser";
	}

	// for showing userTable filter wise
	@RequestMapping(value = "/showUserTable", method = RequestMethod.POST)
	@ResponseBody
	public List<UserVo> showUserTable(@RequestParam String status, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("inside user table controller");
		List<UserVo> userlist = registrationService.findUserListByStatus(loginedUser, status);
		return userlist;
	}

	@RequestMapping(value = "/getAllRms", method = RequestMethod.POST)
	@ResponseBody
	public List<RmsTable> getAllRoles() {
		List<RmsTable> allRoles = registrationService.findAllRms();
		return allRoles;
	}

	@RequestMapping("/dailysummarypage")
	public String dailysummaryPage(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Daily summaryPage ");

		return "dailySummary";
	}

	@RequestMapping("/collectionReport")
	public String collectionReport(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning collection report page");
		return "collectionReport";
	}

	@RequestMapping("/capacityReport")
	public String capacityReport(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
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
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
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
	@RequestMapping(value = "/getReportByDate", method = RequestMethod.POST)
	public Map<Object, List<TrackingVo>> getReportByDate(HttpSession session, HttpServletResponse response,
			@RequestParam(value = "fromdate") String fromdate, @RequestParam(value = "todate") String todate,
			Model model, @RequestParam(value = "rmsName") Long rmsId,
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

		Map<Object, List<TrackingVo>> trackDetails = reportService.fetchBagInventoryWithRmsAndBagStatus(rmsId,
				bagStatus, ts1, ts2);

		log.debug("Returning Bag details for rmsId:{}", rmsId);

		return trackDetails;
	}

	/// ****For Post-Office Bags Reports****///
	@RequestMapping("/dateWisePoBagsReport")
	public String dateWisePoBagsReport(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		List<MasterAddress> pOList = trackingService.fetchPOList();
		model.addAttribute("pOList", pOList);
		log.info("Returning post-office bags report view ");
		return "dateWiseBagsReportPO";
	}

	@ResponseBody
	@RequestMapping(value = "/POreportDatewise", method = RequestMethod.POST)
	public Map<Object, List<TrackingVo>> POreportDatewise(HttpSession session, HttpServletResponse response,
			@RequestParam(value = "fromdate") String fromdate, @RequestParam(value = "todate") String todate,
			Model model, @RequestParam(value = "poName") Integer postalCode,
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

		Map<Object, List<TrackingVo>> bagDetailsPO = trackingService.datewiseBagsReportForPoUser(postalCode, bagStatus,
				ts1, ts2);
		log.debug("Returning Bag details for seclecting sub-office");
		return bagDetailsPO;
	}

	@RequestMapping(value = "/parcelReportMisAdmin", method = RequestMethod.GET)
	public String getParcelReportMisforAdmin(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

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
		List<Services> postalSubServiceList = postalSubService.stream().filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());

		List<Services> postalServiceList = postalServicel.stream().filter(distinctByKey(Services::getServiceName))
				.collect(Collectors.toList());
		model.addAttribute("postalSubServiceList", postalSubServiceList);
		model.addAttribute("pStatusList", pStatusList);
		model.addAttribute("postalServiceList", postalServiceList);

		return "parcelsReportPageAdmin";
	}

	@RequestMapping(value = "/getParcelsReportOnMis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getParcelsReportOnMis(@RequestParam("poCode") Integer poCode,
			@RequestParam(value = "fromdate") String fromdate, @RequestParam(value = "todate") String todate,
			@RequestParam(value = "status") PStatus status, @RequestParam(value = "parcelStatus") String parcelStatus,
			Model model, Principal principal, @RequestParam List<Long> services, @RequestParam List<Long> subservices)
			throws java.text.ParseException {

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

		List<ParcelVo> reportMis = trackingService.findParcelsReportDataAdmin(fromdate, todate, services, loginedUser,
				status, subservices, (long) poCode, parcelStatus);
		misReport.put("reportmisdata", reportMis);
		return misReport;
	}
	
	@RequestMapping(value = "/emptyBagReport", method = RequestMethod.GET)
	public String emptyBagReport(Model model, Principal principal) {

		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	      //if user has logined first time & passowrd has not been changed, forcefully movw to change password
	        if(loginedUser.isFirstLogin())
	        {
	        	return "redirect:/changePasswordHome";
	        }
	        model.addAttribute("user", loginedUser.getName());
	        model.addAttribute("role", loginedUser.getRole().getName());
	        
	        List<MasterAddressVo> masterAddressVo = masterAddressService.getAllMasterAddressList();
	        
	        List<RmsTable> rmsList= rmsRepository.findAllByStatus(Status.ACTIVE);
	       
	        model.addAttribute("masterAddress",masterAddressVo);
	        model.addAttribute("rms", rmsList);
	        
		return "disposeEmptyBagAdmin";
	}
	
	@RequestMapping(value = "/disposedEmptyBagReportList", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> disposedEmptyBagReportAdmin(@RequestParam String location,@RequestParam String rmsAndPostalCode,Model model, Principal principal) {

		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	      //if user has logined first time & passowrd has not been changed, forcefully movw to change password
	        
	        model.addAttribute("user", loginedUser.getName());
	        model.addAttribute("role", loginedUser.getRole().getName());
	       return  trackingService.disposedEmptyBagAdmin(location,rmsAndPostalCode);
	       
	}

	
	//Client Last Sync Report
			@RequestMapping("/clientLastSyncReport")
			@Transactional
			public String showClientLastSyncReport( Model model, Principal principal) {
				User loginedUser = (User) ((Authentication) principal).getPrincipal();
				//if user has logined first time & passowrd has not been changed, forcefully movw to change password
		        if(loginedUser.isFirstLogin())
		        {
		        	return "redirect:/changePasswordHome";
		        }

				model.addAttribute("user", loginedUser.getName());
		        model.addAttribute("role", loginedUser.getRole().getName());
		        List<Zone> zoneList = masterAddressService.fetchZoneList();
				model.addAttribute("zoneList", zoneList);
				List<Division> divisionList =masterAddressService.fetchDivisionList();
				model.addAttribute("divisionList", divisionList);
				List<District> districtList=masterAddressService.fetchDistrictList();
				model.addAttribute("districtList", districtList);
				List<Thana> thanaList=masterAddressService.fetchThanaList();
				model.addAttribute("thanaList", thanaList);
				log.info("Returning client last sync report");
				return "clientLastSyncReport";
			}
			
			
			
			@RequestMapping(path = "/clientLastSyncReport", method = RequestMethod.POST)
			@ResponseBody
			public  List<SyncTableVo> clientLastSyncReport( @RequestParam String division, @RequestParam String zone, @RequestParam String district, @RequestParam String thana, @RequestParam String subOffice) throws ParseException {
					
				return reportService.getclientLastSyncReport (zone,district,division,thana,subOffice);
			}


}
