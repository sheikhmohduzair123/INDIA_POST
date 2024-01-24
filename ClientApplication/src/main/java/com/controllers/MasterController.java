package com.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.Status;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.ZoneAddressRepo;
import com.services.MasterAddressService;
import com.services.ParcelService;

@Controller
@RequestMapping("/masterController")
public class MasterController {

	protected Logger log = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	private ZoneAddressRepo zoneAddressRepo;

	@Autowired
	private DivisionAddressRepo divisionAddressRepo;

	@Autowired
	private DistrictAddressRepo districtAddressRepo;

	@Autowired
	private ThanaAddressRepo thanaAddressRepo;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	ParcelService parcelService;

	@Autowired
	MasterAddressService masterAddressService;

	@Autowired
	RmsRepository rmsRepository;

	@Autowired
	PostalServiceRepository postalServiceRepository;

	// FOR ZONE ADDRESS
	// for showing zone page
	@RequestMapping("/showZone")
	@Transactional
	public String showMasterAddress(Zone objZone, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "zone";
	}

	// for showing ZoneTable
	@RequestMapping(value = "/showAllActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<Zone> showZoneAddress(@RequestParam String status, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return (List<Zone>) zoneAddressRepo.findAll();
		else
			return (List<Zone>) zoneAddressRepo.findAllByStatusOrderByZoneAsc(Enum.valueOf(Status.class, status));
	}

	@RequestMapping(value = "/checkExistingZoneNameOnAdd", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingZoneNameOnAdd(Zone objZone, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Zone");
		Boolean existingZone = masterAddressService.fetchExistingZone(objZone.getZone());
		if(existingZone)
			return true;
		else
			return false;
	}

	// for insert Zone information
	@RequestMapping(value = "/actionZone", method = RequestMethod.POST)
	@Transactional
	public String addZone(@ModelAttribute("objZone") Zone objZone, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Zone");
		masterAddressService.saveZoneDetails(objZone, loginedUser);
		return "redirect:showZone";
	}

	// for update Zone Address
	@RequestMapping(value = "/actionUpdateZone", method = RequestMethod.POST)
	@Transactional
	public String updateZoneAddress(@RequestParam(value = "ZoneId") Long Zid, @ModelAttribute("objZone") Zone objZone,
									Principal principal, Model model) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		masterAddressService.updateZoneService(Zid, loginedUser, objZone.getZone());
		log.info("Update Selected zone");
		return "redirect:showZone";
	}

	// for Delete zone Address
	@RequestMapping(value = "/actionDeleteZone", method = RequestMethod.POST)
	@Transactional
	public String deleteZoneAddress(@RequestParam(value = "deletedId") Long del_id, Principal principal, Model model) {

		log.info("ZONE selected for delete");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.deleteZoneService(del_id, loginedUser);
		log.info("selected ZONE deleted");
		return "redirect:showZone";
	}

	// for showing division page
	@RequestMapping("/showDivision")
	public String showDivision(Division objDivision, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<Zone> zoneList = masterAddressService.fetchZoneList();
		model.addAttribute("zoneList", zoneList);
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "division";
	}

	// for showing division table
	@RequestMapping(value = "/showAllDivisionActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<Division> showDivisionAddress(@RequestParam String status, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return (List<Division>) divisionAddressRepo.findAll();
		else
			return (List<Division>) divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Enum.valueOf(Status.class, status));
	}

	@RequestMapping(value = "/checkExistingDivisionName", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingDivisionName(Division objDivision, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Zone");
		Boolean existingDivision = masterAddressService.fetchExistingDivision(objDivision.getZone().getId(),objDivision.getDivision());
		if(existingDivision)
			return true;
		else
			return false;
	}

	// for insert Division information
	@RequestMapping(value = "/actionDivision", method = RequestMethod.POST)
	@Transactional
	public String addDivision(Division objDivision, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Add one Division");
		model.addAttribute("role", loginedUser.getRole().getName());
		masterAddressService.saveDivisionDetails(objDivision, loginedUser);
		return "redirect:showDivision";
	}

	// for update division
	@RequestMapping(value = "/actionUpdateDivision", method = RequestMethod.POST)
	@Transactional
	public String updateDivisionAddress(@RequestParam("divisionId") Long id, Division objDivision, Model model,
										Principal principal) {
		log.info("Update selected Division");
		Optional<Division> divisionList = divisionAddressRepo.findById(id);
		Division division = divisionList.get();
		Long zoneid = objDivision.getZone().getId();
		Optional<Zone> zoneList2 = zoneAddressRepo.findById(zoneid);
		Zone zone = zoneList2.get();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.updateDivisionService(division, loginedUser,zone, objDivision.getDivision());
		return "redirect:showDivision";
	}

	// for delete division
	@RequestMapping(value = "/actionDeleteDivision", method = RequestMethod.POST)
	@Transactional
	public String deleteDivisionAddress(@RequestParam("deleteDivisionId") Long id, Division objDivision,
										Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Division selected for delete");
		Optional<Division> divisionList = divisionAddressRepo.findById(id);
		Division division = divisionList.get();
		masterAddressService.updateOrDeleteDivision(division,loginedUser);
		log.info("selected devision deleted");
		return "redirect:showDivision";
	}
	// End Division

	// District Controller
	@RequestMapping("/showDistrict")
	public String showDistrict(@ModelAttribute("objDistrict") District objDistrict, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<Division> divisionList = masterAddressService.fetchDivisionList();
		model.addAttribute("divisionList", divisionList);
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("user", loginedUser.getName());
		log.info("Returning Master Address data report");
		return "district";
	}

	@RequestMapping(value = "/checkExistingDistrictName", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingDistrictName(District objDistrict, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Zone");
		Boolean existingDistrict = masterAddressService.fetchExistingDistrict(objDistrict.getDivision().getId(),
				objDistrict.getDistrict());
		if(existingDistrict)
			return true;
		else
			return false;
	}

	@RequestMapping(value = "/actionDistrict", method = RequestMethod.POST)
	public String addDistrict(District objDistrict, Principal principal, Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one District");
		masterAddressService.saveDistrict(objDistrict, loginedUser);
		return "redirect:showDistrict";
	}

	@RequestMapping(value = "/actionUpdateDistrict", method = RequestMethod.POST)
	@Transactional
	public String updateDistrictAddress(@RequestParam("districtId") Long id, District objDistrict, Principal principal,
										Model model) {

		log.info("Update selected District");
		Optional<District> districtList = districtAddressRepo.findById(id);
		District currentDistrict = districtList.get();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Long divisionid = objDistrict.getDivision().getId();
		Optional<Division> divisionList2 = divisionAddressRepo.findById(divisionid);
		Division division = divisionList2.get();
		masterAddressService.updateDistrict(currentDistrict, loginedUser,division, objDistrict.getDistrict());
		return "redirect:showDistrict";

	}

	@RequestMapping(value = "/DistrictDelete", method = RequestMethod.POST)
	@Transactional
	public String DistrictDeleteMethod(@RequestParam("deleteDistrictId") Long id, Principal principal, Model model) {
		log.info("Update selected District");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Delete selected District");
		Optional<District> districtList = districtAddressRepo.findById(id);
		District DistrictData = districtList.get();
		masterAddressService.DistrictDelete(DistrictData, loginedUser);
		return "redirect:showDistrict";
	}

	@RequestMapping("/showThana")
	public String showThana(@ModelAttribute("objThana") Thana objThana, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<District> districtList = masterAddressService.fetchDistrictList();
		model.addAttribute("districtList", districtList);
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "thana";
	}

	@RequestMapping(value = "/checkExistingThanaName", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingThanaName(Thana objThana, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Thana");
		Boolean existingThana = masterAddressService.fetchExistingThana(objThana.getDistrict().getId(),
				objThana.getThana());
		if(existingThana)
			return true;
		else
			return false;
	}

	@RequestMapping(value = "/actionThana", method = RequestMethod.POST)
	public String actionThana(Thana objThana, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		masterAddressService.saveThanaDetails(objThana, loginedUser);
		return "redirect:showThana";
	}

	@RequestMapping(value = "/actionUpdateThana", method = RequestMethod.POST)
	public String actionUpdateThana(@RequestParam("thanaId") Long id, Thana objThana, Principal principal,
									Model model) {

		log.info("Update selected Thana");
		Optional<Thana> thanaList = thanaAddressRepo.findById(id);
		Thana currentThana = thanaList.get();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Long districtid = objThana.getDistrict().getId();
		Optional<District> districtList2 = districtAddressRepo.findById(districtid);
		District district = districtList2.get();
		masterAddressService.updateThanaDetails(currentThana, loginedUser,district, objThana.getThana());
		return "redirect:showThana";
	}

	@RequestMapping(value = "/deleteThana", method = RequestMethod.POST)
	public String
	deleteThana(@RequestParam("deleteThanaId") Long id, Thana objThana, Principal principal, Model model) {

		log.info("Deleted selected Thana");
		Optional<Thana> thanaList = thanaAddressRepo.findById(id);
		Thana currentThana = thanaList.get();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.deleteThanaDetails(currentThana, objThana.getThana(),loginedUser);
		return "redirect:showThana";
	}

	// FOr MASTER ADDRESS
	// for showing Master Address / SubOffice page
	@RequestMapping("/showMasterAddress")
	public String showMasterAddress(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		List<Zone> zoneList = masterAddressService.fetchZoneList();
		model.addAttribute("zoneList", zoneList);
		List<Division> divisionList =masterAddressService.fetchDivisionList();
		model.addAttribute("divisionList", divisionList);
		List<District> districtList=masterAddressService.fetchDistrictList();
		model.addAttribute("districtList", districtList);
		List<Thana> thanaList=masterAddressService.fetchThanaList();
		model.addAttribute("thanaList", thanaList);
		return "masterAddress";
	}


	@RequestMapping(value = "/checkExistingSubOfficeNameOnAdd", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkExistingSubOfficeNameOnAdd(MasterAddress objMasterAddress, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("check exiting subOffice name on add");
		Long zoneid = Long.parseLong(objMasterAddress.getZone());
		Optional<Zone> zoneList2 = zoneAddressRepo.findById(zoneid);
		Zone zone = zoneList2.get();

		Long divisionid = Long.parseLong(objMasterAddress.getDivision());
		Optional<Division> divisionList2 = divisionAddressRepo.findById(divisionid);
		Division division = divisionList2.get();

		Long districtid = Long.parseLong(objMasterAddress.getDistrict());
		Optional<District> districtList2 = districtAddressRepo.findById(districtid);
		District district = districtList2.get();

		Long thanaid = Long.parseLong(objMasterAddress.getThana());
		Optional<Thana> thanaList2 = thanaAddressRepo.findById(thanaid);
		Thana thana = thanaList2.get();

		Boolean existingSubOffice = masterAddressService.fetchExistingSubOffice(zone.getZone(), division.getDivision(),
				district.getDistrict(), thana.getThana(), objMasterAddress.getSubOffice());
		if(existingSubOffice)
			return true;
		else
			return false;
	}

	// for insert subOffice in masterAddress table
	@RequestMapping(value = "/actionSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	public String addSubOffice(MasterAddress objMasterAddress, Principal principal, Model model) {

		log.info("inside subOffice");
		Long zoneid = Long.parseLong(objMasterAddress.getZone());
		Optional<Zone> zoneList2 = zoneAddressRepo.findById(zoneid);
		Zone zone = zoneList2.get();

		Long divisionid = Long.parseLong(objMasterAddress.getDivision());
		Optional<Division> divisionList2 = divisionAddressRepo.findById(divisionid);
		Division division = divisionList2.get();

		Long districtid = Long.parseLong(objMasterAddress.getDistrict());
		Optional<District> districtList2 = districtAddressRepo.findById(districtid);
		District district = districtList2.get();

		Long thanaid = Long.parseLong(objMasterAddress.getThana());
		Optional<Thana> thanaList2 = thanaAddressRepo.findById(thanaid);
		Thana thana = thanaList2.get();

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one subOffice");
		masterAddressService.saveSubOfficeDetails(objMasterAddress, zone, division, district, thana, loginedUser);
		return "redirect:showMasterAddress";

	}

	// for update SubOffice / MasterAddress
	@RequestMapping(value = "/updateSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	public String updateSubOfficeOrMasterAddress(@RequestParam("subOfficeId") Long id, MasterAddress objMasterAddress,
			Model model, Principal principal) {

		log.info("Update selected SubOffice / MasterAddress");
		Optional<MasterAddress> masterAddressList = masterAddressRepository.findById(id);
		MasterAddress masterAddress = masterAddressList.get();
		Long thanaid = Long.parseLong(objMasterAddress.getThana());
		Optional<Thana> thanaList2 = thanaAddressRepo.findById(thanaid);
		Thana thana = thanaList2.get();
		Long zoneid = Long.parseLong(objMasterAddress.getZone());
		Optional<Zone> zoneList1 = zoneAddressRepo.findById(zoneid);
		Zone zone = zoneList1.get();
		Long divisionid = Long.parseLong(objMasterAddress.getDivision());
		Optional<Division> divisionList1 = divisionAddressRepo.findById(divisionid);
		Division division = divisionList1.get();
		Long districtid = Long.parseLong(objMasterAddress.getDistrict());
		Optional<District> districtList1 = districtAddressRepo.findById(districtid);
		District district = districtList1.get();

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		masterAddressService.updateSubOfficeOrMasterAddressService(masterAddress, zone, division, district, thana,
				loginedUser, objMasterAddress.getSubOffice(), objMasterAddress.getPostalCode());
		return "redirect:showMasterAddress";

	}

	// for delete SubOffice / MasterAddress
	@RequestMapping(value = "/actionDeleteSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	public String actionDeleteSubOfficeOrMasterAddress(@RequestParam("deleteSubOfficeId") Long id, Division objDivision,
			Principal principal, Model model) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("SubOffice selected for delete");
		Optional<MasterAddress> masterAddressList = masterAddressRepository.findById(id);
		MasterAddress masterAddress = masterAddressList.get();
		masterAddressService.updateOrDeleteSubOfficeOrMasterAddress(masterAddress,loginedUser);
		log.info("selected SubOffice / MasterAddress deleted");
		return "redirect:showMasterAddress";
	}
	// End SubOffice / MasterAddress

	// for showing district table
	@RequestMapping(value = "/showAllDistrictActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<District> showDistrictAddress(@RequestParam String status, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return (List<District>) districtAddressRepo.findAll();
		else
			return (List<District>) districtAddressRepo.findAllByStatusOrderByDistrictAsc(Enum.valueOf(Status.class, status));
	}

	// for showing thana table
	@RequestMapping(value = "/showAllThanaActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<Thana> showThanaAddress(@RequestParam String status, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return (List<Thana>) thanaAddressRepo.findAll();
		else
			return (List<Thana>) thanaAddressRepo.findAllByStatusOrderByThanaAsc(Enum.valueOf(Status.class, status));
	}

	// for showing masterAddress table
	@RequestMapping(value = "/showAllSubOfcActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<MasterAddress> showMasterAddress(@RequestParam String status, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());

		if (status.equals("ALL"))
			return (List<MasterAddress>) masterAddressRepository.findAll();
		else
			return (List<MasterAddress>) masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Enum.valueOf(Status.class, status));
	}

	@RequestMapping(value = "/checkPostalcode", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkPostalcode(@ModelAttribute MasterAddress masterAddress) {
		log.debug("inside checking if postal code is already exist::");
		return masterAddressService.fetchExistingPostalCode(masterAddress.getPostalCode());
	}

	@RequestMapping("/getDivisionByZone")
	@ResponseBody
	public List<Division> getDivisionByZone(@RequestParam(value = "status") Long id, Model model, Principal principal) {

		List<Division> divisionList = masterAddressService.fetchDivisionListByZone(id);
		model.addAttribute("divisionList", divisionList);
		log.info("Returning Master Address data report");
		return divisionList;
	}

	@RequestMapping("/getDistrictByDivision")
	@ResponseBody
	public List<District> getDistrictByDivision(@RequestParam(value = "status") Long id, Model model,
			Principal principal) {

		List<District> districtList = masterAddressService.fetchDistrictListByDivision(id);
		model.addAttribute("districtList", districtList);
		log.info("Returning Master Address data report");
		return districtList;
	}

	@RequestMapping("/getThanaByDistrict")
	@ResponseBody
	public List<Thana> getThanaByDistrict(@RequestParam(value = "status") Long id, Model model, Principal principal) {

		List<Thana> thanaList = masterAddressService.fetchThanaListByDistrict(id);
		model.addAttribute("thanaList", thanaList);
		log.info("Returning Master Address data report");
		return thanaList;
	}

    //for details of RMS
	@RequestMapping(value = "/showRms")
	public String showRMSDetails(@ModelAttribute("objRms") RmsTable objRms, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<String> zoneList = masterAddressService.fetchRMSZoneList(Status.ACTIVE);
		model.addAttribute("zoneList", zoneList);

		List<String> divisoinList = masterAddressService.fetchRMSDivisionList(Status.ACTIVE);
		model.addAttribute("divisoinList", divisoinList);

		List<String> districtList = masterAddressService.fetchRMSDistrictList(Status.ACTIVE);
		model.addAttribute("districtList", districtList);

		List<String> thanaList = masterAddressService.fetchRMSThanaList(Status.ACTIVE);
		model.addAttribute("thanaList", thanaList);

		List<String> subOfficeList = masterAddressService.fetchRMSSubOfficeList(Status.ACTIVE);
		model.addAttribute("subOfficeList", subOfficeList);

		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning RMS Details");
		return "registerRms";
	}

// for showing RMS table

	@RequestMapping(value = "/showRMSActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<RmsTable> showRMSActiveStatus(@RequestParam String status, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return (List<RmsTable>) rmsRepository.findAll();
		else
			return (List<RmsTable>) rmsRepository.findAllByStatus(Enum.valueOf(Status.class, status));
	}

//Saving RMS
    @RequestMapping(value = "/actionRMS", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addRmsData(RmsTable objRms, Model model, Principal principal) {
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	model.addAttribute("user", loginedUser.getName());
    	log.info("Adding RMS data");
    	Boolean data = masterAddressService.getExistRmsData(objRms.getRmsName(), objRms.getRmsType(), objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
    	if(data == true) {
    		return true;
    	}
    	else {
    		masterAddressService.saveRmsDetails(objRms, loginedUser);
    		return false;
    	}
    }

    //Check Exist Data
    @RequestMapping(value = "/UpdateButtonExist", method = RequestMethod.POST)
    @ResponseBody
    public Boolean UpdateButtonExist(RmsTable objRms, Model model, Principal principal) {
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	model.addAttribute("user", loginedUser.getName());
    	log.info("Adding RMS data");
    	Boolean data = masterAddressService.getExistRmsData(objRms.getRmsName(), objRms.getRmsType(), objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
    	if(data == true) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    //for filled data during update button
    @RequestMapping(value = "/updateButtonFilledData", method = RequestMethod.GET)
    @ResponseBody
    public RmsTable updateButtonFilledData(@RequestParam String updateId,  Model model, Principal principal) {
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	model.addAttribute("user", loginedUser.getName());
    	log.info("Adding RMS data");
    	return masterAddressService.updateButtonFilledData(Long.parseLong(updateId));
    }


    //For Update RMS
  //For Update RMS
    @RequestMapping(value = "/actionUpdateRMS", method = RequestMethod.POST)
    public String updateRmsData(@RequestParam("RMSId") Long RMSId, RmsTable objRms, Model model, Principal principal) {
    	User loginedUser = (User) ((Authentication) principal).getPrincipal();
    	model.addAttribute("user", loginedUser.getName());
    	log.info("Adding RMS data");
    	masterAddressService.updateRMS(RMSId, objRms, loginedUser);
    	return "redirect:showRms";
    }


    //for Delete RMS
    @RequestMapping(value = "/actionDeleteRms", method = RequestMethod.POST)
	public String DeleteRMS(@RequestParam("deletedId") Long del_id, Principal principal, Model model) {
		log.info("Delete selected RMS");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.DeleteRMS(del_id, loginedUser);
		log.info("selected RMS deleted");
		return "redirect:showRms";
	}

	@RequestMapping("/serviceMaster")
	public String serviceMaster(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		List<Services> servicesList = masterAddressService.fetchServicesList();
		model.addAttribute("servicesList", servicesList);
		return "serviceMaster";
	}
	
	@RequestMapping(value = "/checkAndSaveService", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkAndSaveService(@RequestParam String service, @RequestParam String serviceCode, Principal principal){
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Save services");
		Boolean existingServices = masterAddressService.fetchExistingServices(service, serviceCode);
		if(existingServices){
			return true;
		}
		else{
			masterAddressService.addServices(service, serviceCode, loginedUser);
			return false;
		}
			
	}
	
	@RequestMapping(value = "/checkAndUpdateService", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkAndUpdateService(@RequestParam Long serviceId, @RequestParam String service, @RequestParam String serviceCode, Principal principal){
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("update services");
		Boolean existingServices = masterAddressService.fetchExistingServicesForUpdate(service, serviceCode);
		if(existingServices){
			return true;
		}			
		else{
			masterAddressService.updateServices(serviceId, service, serviceCode, loginedUser);
			return false;
		}
	}

	@RequestMapping(value = "/checkAndSaveSubService", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkAndSaveSubService(@RequestParam(value="serviceNameList[]") String serviceNameList[], @RequestParam String subServiceName, @RequestParam String serviceCode, Principal principal, Model model){
		int j=0;
		log.info("Save sub services");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		for(int i=0;i<serviceNameList.length;i++){
			Long pSId = Long.parseLong(serviceNameList[i]);
			Boolean existingServices = masterAddressService.fetchExistingSubServices(pSId, subServiceName, serviceCode);
			if(!existingServices){
				masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
				j++;
			}
		}
		if(j>0){
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/checkAndUpdateSubService", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkAndUpdateSubService(@RequestParam Long serviceId[], @RequestParam(value="serviceNameList[]") String serviceNameList[], @RequestParam String subServiceName, @RequestParam String serviceCode, Principal principal){
		
		log.info("update existing sub services");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		int r=0;
		int subServiceIdLength = serviceId.length;
		int serviceIdLength = serviceNameList.length;
		if(subServiceIdLength>serviceIdLength){
			int f=0;
			log.info("inside sub service id's length is greater");
			for(int j=0; j<subServiceIdLength; j++){
				int flag = 0;
				for(int k=0; k<serviceIdLength; k++){
					Long pSId = Long.parseLong(serviceNameList[k]);
					Services services = postalServiceRepository.findById(serviceId[j]).orElse(null);
					if(services.getParentServiceId()==pSId){
						masterAddressService.updateSubServices(pSId, serviceId[j], subServiceName, serviceCode, loginedUser);
						f++;
						flag = 1;
						r++;
					}
				}
				if(flag == 0){
					masterAddressService.onlyUpdateSubService(serviceId[j], loginedUser);
					r++;
				}
			}
			if(f==0){
				for(int l=0; l<serviceIdLength;l++){
					Long pSId = Long.parseLong(serviceNameList[l]);
					masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
				}
			}

		}
		else if(subServiceIdLength<serviceIdLength){
			log.info("inside service id's length is greater");
			for(int j=0; j<serviceIdLength; j++){
				int flag = 0;
				Long pSId = Long.parseLong(serviceNameList[j]);
				for(int k=0; k<subServiceIdLength; k++){
					Services services = postalServiceRepository.findById(serviceId[k]).orElse(null);
					if(services.getParentServiceId()==pSId){
						flag = 1;
						masterAddressService.updateSubServices(pSId, serviceId[k], subServiceName, serviceCode, loginedUser);
						r++;
					}
				}
				if(flag == 0){
					masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
					r++;
				}
			}
		}
		else{
			log.info("inside Both services id's length equal");
			int indexOfsubServiceId=0;
			for(int l=0;l<serviceNameList.length; l++){
				Long pSId = Long.parseLong(serviceNameList[l]);
				Boolean existingServices = masterAddressService.fetchExistingSubServicesOnUpdate(pSId, subServiceName, serviceCode);
				if(!existingServices){
					masterAddressService.updateSubServices(pSId, serviceId[indexOfsubServiceId], subServiceName, serviceCode, loginedUser);
					r++;
					indexOfsubServiceId++;
				}
			}
		}
		if(r>0){
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/deleteServiceOrSubServiceDetails", method = RequestMethod.GET)
	@ResponseBody
	public String deleteServiceOrSubServiceDetails(@RequestParam Long serviceId, Principal principal){

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.deleteService(serviceId, loginedUser);
		return "success";
	}

	@RequestMapping(value = "/showAllServicesActiveStatus", method = RequestMethod.GET)
	@ResponseBody
	public List<Services> showAllServicesActiveStatus() {
		
			return postalServiceRepository.findAll();
	}
}