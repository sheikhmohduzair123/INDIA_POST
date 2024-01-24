package com.controllers;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.Status;
import com.domain.Config;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.RateTable;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.repositories.ConfigRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.ZoneAddressRepo;
import com.services.MasterAddressService;
import com.services.ParcelService;
import com.services.TrackingService;
import com.vo.ConfigVo;
import com.vo.DistrictVo;
import com.vo.DivisionVo;
import com.vo.MainServiceVo;
import com.vo.MasterAddressVo;
import com.vo.RateTableVo;
import com.vo.RmsTableVo;
import com.vo.ServicesVo;
import com.vo.SubOfficeVo;
import com.vo.SubServiceVo;
import com.vo.ThanaVo;
import com.vo.ZoneVo;

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

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	TrackingService trackingService;
	// FOR ZONE ADDRESS
	// for showing zone page
	@RequestMapping("/showZone")
	@Transactional
	public String showMasterAddress(Zone objZone, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "zone";
	}

	// for showing ZoneTable
	@RequestMapping(value = "/showAllActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<ZoneVo> showZoneAddress(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllZoneList();
		else
			return masterAddressService.getAllZoneListByStatus(Enum.valueOf(Status.class, status));
	}

	// for insert Zone information
	@RequestMapping(value = "/actionZone", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> addZone(@Valid @ModelAttribute("objZone") Zone objZone, Model model,
			Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password

		Boolean existingZone = masterAddressService.fetchExistingZone(objZone.getZone().trim());
		if (existingZone) {
			return new ResponseEntity<String>(objZone.getZone() + " Already Exist", HttpStatus.OK);
		} else {
			model.addAttribute("role", loginedUser.getRole().getName());
			log.info("Add one Zone");
			Zone result = masterAddressService.saveZoneDetails(objZone, loginedUser);
			if (result != null) {
				log.info("zone added successfuly");
			}
			return new ResponseEntity<String>(objZone.getZone() + " Added Successfully", HttpStatus.OK);

		}
	}

	// for update Zone Address
	@RequestMapping(value = "/actionUpdateZone", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> updateZoneAddress(@Valid @RequestParam(value = "ZoneId") Long Zid,
			@ModelAttribute("objZone") Zone objZone, Principal principal, Model model) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		Boolean existingZone = masterAddressService.fetchExistingZone(objZone.getZone().trim());
		if (existingZone) {
			return new ResponseEntity<String>(objZone.getZone() + " Already Exists", HttpStatus.OK);
		} else {
			model.addAttribute("role", loginedUser.getRole().getName());
			Zone result = masterAddressService.updateZoneService(Zid, loginedUser, objZone.getZone().trim());
			if (result != null) {
				log.info("Update Selected zone");
			}

			return new ResponseEntity<String>("Zone Updated Successfully", HttpStatus.OK);
		}
	}

	// for Delete zone Address
	@RequestMapping(value = "/actionDeleteZone", method = RequestMethod.POST)
	@Transactional
	public String deleteZoneAddress(@RequestParam(value = "deletedId") Long del_id, Principal principal, Model model) {

		log.info("ZONE selected for delete");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		Zone result = masterAddressService.deleteZoneService(del_id, loginedUser);
		if (result != null) {
			log.info("selected ZONE deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:showZone";
	}

	// for showing division page
	@RequestMapping("/showDivision")
	public String showDivision(Division objDivision, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<Zone> zoneList = masterAddressService.fetchZoneList();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		model.addAttribute("zoneList", zoneList);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "division";
	}

	// for showing division table
	@RequestMapping(value = "/showAllDivisionActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<DivisionVo> showDivisionAddress(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllDivisionList();
		else
			return masterAddressService.getAllDivisionListByStatus(Enum.valueOf(Status.class, status));
	}

	// for insert Division information
	@RequestMapping(value = "/actionDivision", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> addDivision(@Valid Division objDivision, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		Boolean existingDivision = masterAddressService.fetchExistingDivision(objDivision.getZone().getId(),
				objDivision.getDivision().trim());
		if (existingDivision)
			return new ResponseEntity<String>(objDivision.getDivision() + " Already Exists", HttpStatus.OK);
		else {

			log.info("Add one Division");
			model.addAttribute("role", loginedUser.getRole().getName());
			Division result = masterAddressService.saveDivisionDetails(objDivision, loginedUser);
			if (result != null) {
				log.info("Division added successfully");
				return new ResponseEntity<String>(objDivision.getDivision() + " Added Successfully", HttpStatus.OK);
			} else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}
	}

	// for update division
	@RequestMapping(value = "/actionUpdateDivision", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> updateDivisionAddress(@Valid @RequestParam("divisionId") Long id,
			Division objDivision, Model model, Principal principal) {
		log.info("Update selected Division");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		Boolean existingDivision = masterAddressService.fetchExistingDivision(objDivision.getZone().getId(),
				objDivision.getDivision().trim());
		if (existingDivision)
			return new ResponseEntity<String>(objDivision.getDivision() + " Already Exists", HttpStatus.OK);
		else {

			Optional<Division> divisionList = divisionAddressRepo.findById(id);
			Division division = divisionList.get();
			Long zoneid = objDivision.getZone().getId();
			Optional<Zone> zoneList2 = zoneAddressRepo.findById(zoneid);
			Zone zone = zoneList2.get();

			Division result = masterAddressService.updateDivisionService(division, loginedUser, zone,
					objDivision.getDivision().trim());
			if (result != null) {
				log.info("division updated deleted");
				return new ResponseEntity<String>("Division Updated Successfully ", HttpStatus.OK);
			} else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}
	}

	// for delete division
	@RequestMapping(value = "/actionDeleteDivision", method = RequestMethod.POST)
	@Transactional
	public String deleteDivisionAddress(@RequestParam("deleteDivisionId") Long id, Division objDivision,
			Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		log.info("Division selected for delete");
		Optional<Division> divisionList = divisionAddressRepo.findById(id);
		Division division = divisionList.get();
		Division result = masterAddressService.updateOrDeleteDivision(division, loginedUser);
		if (result != null) {
			log.info("selected division deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:showDivision";
	}
	// End Division

	// District Controller
	@RequestMapping("/showDistrict")
	public String showDistrict(@ModelAttribute("objDistrict") District objDistrict, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}

		List<Division> divisionList = masterAddressService.fetchDivisionList();
		model.addAttribute("divisionList", divisionList);
		model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("user", loginedUser.getName());
		log.info("Returning Master Address data report");
		return "district";
	}

	@RequestMapping(value = "/actionDistrict", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addDistrict(@Valid District objDistrict, Principal principal, Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingDistrict = masterAddressService.fetchExistingDistrict(objDistrict.getDivision().getId(),
				objDistrict.getDistrict().trim());
		if (existingDistrict)
			return new ResponseEntity<String>(objDistrict.getDistrict() + " Already Exists ", HttpStatus.OK);
		else {
			model.addAttribute("role", loginedUser.getRole().getName());
			log.info("Add one District");
			District result = masterAddressService.saveDistrict(objDistrict, loginedUser);
			if (result != null) {
				log.info("district added successfully");
				return new ResponseEntity<String>(objDistrict.getDistrict() + " Added Successfully ", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Some error occurred", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/actionUpdateDistrict", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> updateDistrictAddress(@Valid @RequestParam("districtId") Long id,
			District objDistrict, Principal principal, Model model) {

		log.info("Update selected District");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingDistrict = masterAddressService.fetchExistingDistrict(objDistrict.getDivision().getId(),
				objDistrict.getDistrict().trim());
		if (existingDistrict)
			return new ResponseEntity<String>(objDistrict.getDistrict() + " Already Exists", HttpStatus.OK);
		else {
			Optional<District> districtList = districtAddressRepo.findById(id);
			District currentDistrict = districtList.get();

			Long divisionid = objDistrict.getDivision().getId();
			Optional<Division> divisionList2 = divisionAddressRepo.findById(divisionid);
			Division division = divisionList2.get();
			District result = masterAddressService.updateDistrict(currentDistrict, loginedUser, division,
					objDistrict.getDistrict().trim());
			if (result != null) {
				log.info("district updated successfully");
				return new ResponseEntity<String>(" District Updated Successfully ", HttpStatus.OK);
			} else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}

	}

	@RequestMapping(value = "/DistrictDelete", method = RequestMethod.POST)
	@Transactional
	public String DistrictDeleteMethod(@RequestParam("deleteDistrictId") Long id, Principal principal, Model model) {
		log.info("Update selected District");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		log.info("Delete selected District");
		Optional<District> districtList = districtAddressRepo.findById(id);
		District DistrictData = districtList.get();
		District result = masterAddressService.DistrictDelete(DistrictData, loginedUser);
		if (result != null) {
			log.info("selected district deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:showDistrict";
	}

	@RequestMapping("/showThana")
	public String showThana(@ModelAttribute("objThana") Thana objThana, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		List<District> districtList = masterAddressService.fetchDistrictList();
		model.addAttribute("districtList", districtList);
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		return "thana";
	}

	@RequestMapping(value = "/actionThana", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> actionThana(@Valid Thana objThana, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingThana = masterAddressService.fetchExistingThana(objThana.getDistrict().getId(),
				objThana.getThana().trim());
		if (existingThana)
			return new ResponseEntity<String>(objThana.getThana() + " Already Exists", HttpStatus.OK);
		else {

			model.addAttribute("role", loginedUser.getRole().getName());
			Thana result = masterAddressService.saveThanaDetails(objThana, loginedUser);
			if (result != null) {
				log.info("thana added successfully");
				return new ResponseEntity<String>(objThana.getThana() + " Added Successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Sone error occurred", HttpStatus.OK);
			}
		}
	}

	@RequestMapping(value = "/actionUpdateThana", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> actionUpdateThana(@Valid @RequestParam("thanaId") Long id, Thana objThana,
			Principal principal, Model model) {
		log.info("Update selected Thana");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingThana = masterAddressService.fetchExistingThana(objThana.getDistrict().getId(),
				objThana.getThana().trim());
		if (existingThana)
			return new ResponseEntity<String>(objThana.getThana() + " Already Exists", HttpStatus.OK);
		else {

			Optional<Thana> thanaList = thanaAddressRepo.findById(id);
			Thana currentThana = thanaList.get();
			Long districtid = objThana.getDistrict().getId();
			Optional<District> districtList2 = districtAddressRepo.findById(districtid);
			District district = districtList2.get();
			Thana result = masterAddressService.updateThanaDetails(currentThana, loginedUser, district,
					objThana.getThana().trim());
			if (result != null) {
				log.info("thana updated successfully");
				return new ResponseEntity<String>("Thana Updated Successfully ", HttpStatus.OK);
			} else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}
	}

	@RequestMapping(value = "/deleteThana", method = RequestMethod.POST)
	public String deleteThana(@RequestParam("deleteThanaId") Long id, Thana objThana, Principal principal,
			Model model) {

		log.info("Deleted selected Thana");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		Optional<Thana> thanaList = thanaAddressRepo.findById(id);
		Thana currentThana = thanaList.get();
		Thana result = masterAddressService.deleteThanaDetails(currentThana, loginedUser);
		if (result != null) {
			log.info("selected thana deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:showThana";
	}

	// FOr MASTER ADDRESS
	// for showing Master Address / SubOffice page
	@RequestMapping("/showMasterAddress")
	public String showMasterAddress(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Master Address data report");
		List<Zone> zoneList = masterAddressService.fetchZoneList();
		model.addAttribute("zoneList", zoneList);
		List<Division> divisionList = masterAddressService.fetchDivisionList();
		model.addAttribute("divisionList", divisionList);
		List<District> districtList = masterAddressService.fetchDistrictList();
		model.addAttribute("districtList", districtList);
		List<Thana> thanaList = masterAddressService.fetchThanaList();
		model.addAttribute("thanaList", thanaList);
		return "masterAddress";
	}

	// for insert subOffice in masterAddress table
	@RequestMapping(value = "/actionSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> addSubOffice(@Valid MasterAddress objMasterAddress, Principal principal,
			Model model) {

		log.info("inside subOffice");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		// if(loginedUser.isFirstLogin())
		// {
		// return new ResponseEntity<String>("redirect:/changePasswordHome",
		// HttpStatus.OK);
		// }
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
		Boolean status = masterAddressRepository.existsByPostalCodeAndStatus(objMasterAddress.getPostalCode(),
				Status.ACTIVE);
		Boolean existingSubOffice = masterAddressService.fetchExistingSubOffice(zone.getZone().trim(),
				division.getDivision().trim(), district.getDistrict().trim(), thana.getThana().trim(),
				objMasterAddress.getSubOffice().trim());
		if (status) {

			return new ResponseEntity<String>("Postal code " + objMasterAddress.getPostalCode() + " already exist",
					HttpStatus.OK);
		} else if (existingSubOffice) {
			return new ResponseEntity<String>(objMasterAddress.getSubOffice() + " Already Exists", HttpStatus.OK);
		} else {
			MasterAddress result = masterAddressService.saveSubOfficeDetails(objMasterAddress, zone, division, district,
					thana, loginedUser);
			model.addAttribute("role", loginedUser.getRole().getName());
			log.info("Add one subOffice");
			if (result != null) {
				log.info("subOffice added successfully");
			}
			return new ResponseEntity<String>(objMasterAddress.getSubOffice() + " Added Successfully ", HttpStatus.OK);
		}
	}

	// for update SubOffice / MasterAddress
	@RequestMapping(value = "/updateSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> updateSubOfficeOrMasterAddress(@RequestParam("subOfficeId") Long id,
			MasterAddress objMasterAddress, Model model, Principal principal) {

		log.info("Update selected SubOffice / MasterAddress");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();

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

		Boolean existingSubOffice = masterAddressService.fetchExistingSubOffice(zone.getZone().trim(),
				division.getDivision().trim(), district.getDistrict().trim(), thana.getThana().trim(),
				objMasterAddress.getSubOffice().trim());
		if ((masterAddress.getPostalCode()) != objMasterAddress.getPostalCode()) {

			List<MasterAddress> list = masterAddressRepository
					.findByPostalCodeAndStatus(objMasterAddress.getPostalCode(), Status.ACTIVE);

			if (list != null && list.size() > 0) {

				return new ResponseEntity<String>("Postal code " + objMasterAddress.getPostalCode() + " already exist",
						HttpStatus.OK);
			}
		} else if (existingSubOffice) {
			return new ResponseEntity<String>(objMasterAddress.getSubOffice() + " Already Exists", HttpStatus.OK);
		} else
			model.addAttribute("user", loginedUser.getName());
		MasterAddress result = masterAddressService.updateSubOfficeOrMasterAddressService(masterAddress, zone, division,
				district, thana, loginedUser, objMasterAddress.getSubOffice(), objMasterAddress.getPostalCode());
		if (result != null) {
			log.info("subOffice updated successfully");
		} else {
			log.info("Some error occured");
		}

		return new ResponseEntity<String>("Suboffice Updated Successfully ", HttpStatus.OK);
	}

	// for delete SubOffice / MasterAddress
	@RequestMapping(value = "/actionDeleteSubOfficeOrMasterAddress", method = RequestMethod.POST)
	@Transactional
	public String actionDeleteSubOfficeOrMasterAddress(@RequestParam("deleteSubOfficeId") Long id, Division objDivision,
			Principal principal, Model model) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		log.info("SubOffice selected for delete");
		Optional<MasterAddress> masterAddressList = masterAddressRepository.findById(id);
		MasterAddress masterAddress = masterAddressList.get();
		MasterAddress result = masterAddressService.updateOrDeleteSubOfficeOrMasterAddress(masterAddress, loginedUser);
		if (result != null) {
			log.info("selected subOffice deleted");
		} else {
			log.info("Some error occured");
		}
		log.info("selected SubOffice / MasterAddress deleted");
		return "redirect:showMasterAddress";
	}
	// End SubOffice / MasterAddress

	// for showing district table
	@RequestMapping(value = "/showAllDistrictActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<DistrictVo> showDistrictAddress(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllDistrictList();
		else
			return masterAddressService.getAllDistrictListByStatus(Enum.valueOf(Status.class, status));
	}

	// for showing thana table
	@RequestMapping(value = "/showAllThanaActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<ThanaVo> showThanaAddress(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllThanaList();
		else
			return masterAddressService.getAllThanaListByStatus(Enum.valueOf(Status.class, status));
	}

	// for showing masterAddress table
	@RequestMapping(value = "/showAllSubOfcActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<MasterAddressVo> showMasterAddress(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllMasterAddressList();
		else
			return masterAddressService.getAllMasterAddressListByStatus(Enum.valueOf(Status.class, status));
	}

	@RequestMapping(value = "/checkPostalcode", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkPostalcode(@ModelAttribute MasterAddress masterAddress) {
		log.debug("inside checking if postal code is already exist::");
		return masterAddressService.fetchExistingPostalCode(masterAddress.getPostalCode());
	}

	@RequestMapping(value = "/getDivisionByZone", method = RequestMethod.POST)
	@ResponseBody
	public List<DivisionVo> getDivisionByZone(@RequestParam(value = "status") Long id, Model model,
			Principal principal) {

		List<DivisionVo> divisionList = masterAddressService.fetchDivisionListByZone(id);
		model.addAttribute("divisionList", divisionList);
		log.info("Returning Master Address data report");
		return divisionList;
	}

	@RequestMapping(value = "/getDistrictByDivision", method = RequestMethod.POST)
	@ResponseBody
	public List<DistrictVo> getDistrictByDivision(@RequestParam(value = "status") Long id, Model model,
			Principal principal) {

		List<DistrictVo> districtList = masterAddressService.fetchDistrictListByDivision(id);
		model.addAttribute("districtList", districtList);
		log.info("Returning Master Address data report");
		return districtList;
	}

	@RequestMapping(value = "/getThanaByDistrict", method = RequestMethod.POST)
	@ResponseBody
	public List<ThanaVo> getThanaByDistrict(@RequestParam(value = "status") Long id, Model model, Principal principal) {

		List<ThanaVo> thanaList = masterAddressService.fetchThanaListByDistrict(id);
		model.addAttribute("thanaList", thanaList);
		log.info("Returning Master Address data report");
		return thanaList;
	}

	// for details of RMS
	@RequestMapping(value = "/showRms")
	public String showRMSDetails(@ModelAttribute("objRms") RmsTable objRms, Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
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

	@RequestMapping(value = "/showRMSActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<RmsTableVo> showRMSActiveStatus(@RequestParam String status, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		if (status.equals("ALL"))
			return masterAddressService.getAllRMSList();
		else
			return masterAddressService.getAllRMSListByStatus(Enum.valueOf(Status.class, status));
	}

//Saving RMS
	@RequestMapping(value = "/actionRMS", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addRmsData(@Valid RmsTable objRms, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		log.info("Adding RMS data");
		List<MasterAddress> list = masterAddressRepository
				.findByPostalCodeAndStatus(objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);

		Boolean data = masterAddressService.getExistRmsData(objRms.getRmsName().trim(), objRms.getRmsType(),
				objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
		if (list.isEmpty() && list.size() <= 0) {

			return new ResponseEntity<String>(
					"Postal Code " + objRms.getRmsAddress().getPostalCode() + " Does Not Exists", HttpStatus.OK);
		} else if (data) {
			return new ResponseEntity<String>(" RMS " + objRms.getRmsName() + " Already Exists ", HttpStatus.OK);
		} else {
			RmsTable result = masterAddressService.saveRmsDetails(objRms, loginedUser);
			if (result != null) {
				log.info("RmsData added succesfully");
			}
			return new ResponseEntity<String>(objRms.getRmsName() + " Added Succesfully", HttpStatus.OK);
		}
	}

	// Check Exist Data
	@RequestMapping(value = "/UpdateButtonExist", method = RequestMethod.POST)
	@ResponseBody
	public Boolean UpdateButtonExist(RmsTable objRms, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		log.info("Adding RMS data");
		Boolean data = masterAddressService.getExistRmsData(objRms.getRmsName().trim(), objRms.getRmsType(),
				objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
		if (data) {
			return true;
		} else {
			return false;
		}
	}

	// for filled data during update button
	@RequestMapping(value = "/updateButtonFilledData", method = RequestMethod.POST)
	@ResponseBody
	public RmsTable updateButtonFilledData(@RequestParam String updateId, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		log.info("Adding RMS data");
		return masterAddressService.updateButtonFilledData(Long.parseLong(updateId));
	}

	// For Update RMS
	// For Update RMS
	@RequestMapping(value = "/actionUpdateRMS", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateRmsData(@Valid @RequestParam("RMSId") Long RMSId, RmsTable objRms, Model model,
			Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		List<MasterAddress> list = masterAddressRepository
				.findByPostalCodeAndStatus(objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
		Boolean data = masterAddressService.getExistRmsData(objRms.getRmsName().trim(), objRms.getRmsType(),
				objRms.getRmsAddress().getPostalCode(), Status.ACTIVE);
		if (list.isEmpty() && list.size() <= 0) {

			return new ResponseEntity<String>(
					"Postal Code " + objRms.getRmsAddress().getPostalCode() + " Does Not Exists", HttpStatus.OK);
		} else if (data) {
			return new ResponseEntity<String>(" RMS " + objRms.getRmsName() + " Already Exists ", HttpStatus.OK);
		} else {
			model.addAttribute("user", loginedUser.getName());
			log.info("Adding RMS data");

			RmsTable result = masterAddressService.updateRMS(RMSId, objRms, loginedUser);
			if (result != null) {
				log.info("RmsData updated successfully");
			} else {
				log.info("Some error occured");
			}

			return new ResponseEntity<String>(" RMS Updated Successfully ", HttpStatus.OK);
		}

	}

	// for Delete RMS
	@RequestMapping(value = "/actionDeleteRms", method = RequestMethod.POST)
	public String DeleteRMS(@RequestParam("deletedId") Long del_id, Principal principal, Model model) {
		log.info("Delete selected RMS");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		RmsTable result = masterAddressService.DeleteRMS(del_id, loginedUser);
		if (result != null) {
			log.info("selected RmsData deleted");
		} else {
			log.info("Some error occured");
		}
		log.info("selected RMS deleted");
		return "redirect:showRms";
	}

	@RequestMapping("/serviceMaster")
	public String serviceMaster(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has logined first time & passowrd has not been changed, forcefully
		// movw to change password
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		List<Services> servicesList = masterAddressService.fetchServicesList();
		model.addAttribute("servicesList", servicesList);
		return "serviceMaster";
	}

	@RequestMapping(value = "/checkAndSaveService", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkAndSaveService(
			@Valid @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String service,
			@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String serviceCode,
			Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Save services");
		Boolean existingServices = masterAddressService.fetchExistingServices(service.trim(), serviceCode.trim());
		if (existingServices) {
			return true;
		} else {
			masterAddressService.addServices(service, serviceCode, loginedUser);
			return false;
		}

	}

	@RequestMapping(value = "/checkAndUpdateService", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkAndUpdateService(@Valid @RequestParam Long serviceId,
			@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String service,
			@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String serviceCode,
			Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("update services");
		Boolean existingServices = masterAddressService.fetchExistingServicesForUpdate(service.trim(),
				serviceCode.trim());
		if (existingServices) {
			return true;
		} else {
			masterAddressService.updateServices(serviceId, service, serviceCode, loginedUser);
			return false;
		}
	}

	@RequestMapping(value = "/checkAndSaveSubService", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkAndSaveSubService(@Valid @RequestParam(value = "serviceNameList[]") String serviceNameList[],
			@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String subServiceName,
			@Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "{spacial.character.notAllowed}") @RequestParam String serviceCode,
			Principal principal, Model model) {
		int j = 0;
		log.info("Save sub services");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		for (int i = 0; i < serviceNameList.length; i++) {
			Long pSId = Long.parseLong(serviceNameList[i]);
			Boolean existingServices = masterAddressService.fetchExistingSubServices(pSId, subServiceName, serviceCode);
			if (!existingServices) {
				masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
				j++;
			}
		}
		if (j > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/checkAndUpdateSubService", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkAndUpdateSubService(@RequestParam Long serviceId[],
			@RequestParam(value = "serviceNameList[]") String serviceNameList[], @RequestParam String subServiceName,
			@RequestParam String serviceCode, Principal principal) {

		log.info("update existing sub services");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		int r = 0;
		int subServiceIdLength = serviceId.length;
		int serviceIdLength = serviceNameList.length;
		if (subServiceIdLength > serviceIdLength) {
			int f = 0;
			log.info("inside sub service id's length is greater");
			for (int j = 0; j < subServiceIdLength; j++) {
				int flag = 0;
				for (int k = 0; k < serviceIdLength; k++) {
					Long pSId = Long.parseLong(serviceNameList[k]);
					Services services = postalServiceRepository.findById(serviceId[j]).orElse(null);
					if (services.getParentServiceId() == pSId) {
						masterAddressService.updateSubServices(pSId, serviceId[j], subServiceName, serviceCode,
								loginedUser);
						f++;
						flag = 1;
						r++;
					}
				}
				if (flag == 0) {
					masterAddressService.onlyUpdateSubService(serviceId[j], loginedUser);
					r++;
				}
			}
			if (f == 0) {
				for (int l = 0; l < serviceIdLength; l++) {
					Long pSId = Long.parseLong(serviceNameList[l]);
					masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
				}
			}

		} else if (subServiceIdLength < serviceIdLength) {
			log.info("inside service id's length is greater");
			for (int j = 0; j < serviceIdLength; j++) {
				int flag = 0;
				Long pSId = Long.parseLong(serviceNameList[j]);
				for (int k = 0; k < subServiceIdLength; k++) {
					Services services = postalServiceRepository.findById(serviceId[k]).orElse(null);
					if (services.getParentServiceId() == pSId) {
						flag = 1;
						masterAddressService.updateSubServices(pSId, serviceId[k], subServiceName, serviceCode,
								loginedUser);
						r++;
					}
				}
				if (flag == 0) {
					masterAddressService.addSubServices(pSId, subServiceName, serviceCode, loginedUser);
					r++;
				}
			}
		} else {
			log.info("inside Both services id's length equal");
			int indexOfsubServiceId = 0;
			for (int l = 0; l < serviceNameList.length; l++) {
				Long pSId = Long.parseLong(serviceNameList[l]);
				Boolean existingServices = masterAddressService.fetchExistingSubServicesOnUpdate(pSId, subServiceName,
						serviceCode);
				if (!existingServices) {
					masterAddressService.updateSubServices(pSId, serviceId[indexOfsubServiceId], subServiceName,
							serviceCode, loginedUser);
					r++;
					indexOfsubServiceId++;
				}
			}
		}
		if (r > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/deleteServiceOrSubServiceDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteServiceOrSubServiceDetails(@RequestParam Long serviceId, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		masterAddressService.deleteService(serviceId, loginedUser);
		return "success";
	}

	@RequestMapping(value = "/showAllServicesActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<ServicesVo> showAllServicesActiveStatus(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllServicesList();
		else
			return masterAddressService.getAllServicesListByStatus(Enum.valueOf(Status.class, status));
	}

	@RequestMapping("/rateTableUI")
	public String showRateTableUI(RateTable rateTable, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		
		List<MainServiceVo> postalService =masterAddressService.getAllMainServicesForDroupDownList();
		model.addAttribute("pstalServiceList", postalService);
		   
		List<SubServiceVo> SubServiceVoList= masterAddressService.getAllSubServices();
		model.addAttribute("SubServiceVoList", SubServiceVoList);

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		
		List<MasterAddress> pOList = trackingService.fetchPOList();
		model.addAttribute("pOList", pOList);
		
		log.info("Returning Rate Table Page ");

		return "rateTableUI";
	}
	
	@RequestMapping(value = "/getServiceBySubServices", method = RequestMethod.POST)
	@ResponseBody
	public String getServiceBySubServices(@RequestParam(value = "id") Long id, Model model, Principal principal) {

		ServicesVo serviceList = masterAddressService.fetchServiceBySubServicesId(id);
		log.info("Returning subservices data"+serviceList);
		return serviceList.getId().toString(); 
	}
	
	@RequestMapping(value = "/getSubServicesByService", method = RequestMethod.POST)
	@ResponseBody
	public List<SubServiceVo> getSubServicesByService(@RequestParam(value = "id") Long id, Model model, Principal principal) {

		List<SubServiceVo> subServiceList = masterAddressService.fetchSubServicesByServiceId(id);
		log.info("Returning subservices data");
		return subServiceList;
	}
	
	@RequestMapping(value = "/getAllDivision", method = RequestMethod.POST)
	@ResponseBody
	public List<DivisionVo> getAllDivision() {
		List<DivisionVo> divisionList = masterAddressService.getAllDivisionForRateMaster();
		return divisionList;
	}
	
	@RequestMapping(value = "/getAllDistrict", method = RequestMethod.POST)
	@ResponseBody
	public List<DistrictVo> getAllDistrict() {
		List<DistrictVo> districtList = masterAddressService.getAllDistrictForRateMaster();
		return districtList;
	}
	
	@RequestMapping(value = "/getAllThana", method = RequestMethod.POST)
	@ResponseBody
	public List<ThanaVo> getAllThana() {
		List<ThanaVo> ThanaList = masterAddressService.getAllThanaForRateMaster();
		return ThanaList;
	}
	
	@RequestMapping(value = "/getAllSubOffice", method = RequestMethod.POST)
	@ResponseBody
	public List<SubOfficeVo> getAllSubOffice() {
		List<SubOfficeVo> divisionList = masterAddressService.getAllSubOfficeForRateMaster();
		return divisionList;
	}

	@RequestMapping(value = "/showAllRateTableDataActiveStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<RateTableVo> showAllRateTableDataActiveStatus(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllRateTableDataList();
		else
			return masterAddressService.getAllRateTableDataByStatus(Enum.valueOf(Status.class, status));
	}

	@RequestMapping(value = "/saveRateTableDetails", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> saveRateTableDetails(@Valid @RequestBody RateTableVo rateTableJson, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Add one Rate data");
		
		RateTable rateTable = masterAddressService.saveRateTableData(rateTableJson, loginedUser);
		if (rateTable != null) {
			log.info("rate added successfuly");
			return new ResponseEntity<String>(" Added Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>(" Some error occured", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateRateTable", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public ResponseEntity<String> updateFixPriceRateTable(@Valid @RequestBody RateTableVo rateTableJson, Model model, Principal principal) {
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		RateTable rateTable = masterAddressService.updateRateTable(rateTableJson, loginedUser);
		if (rateTable != null) {
			log.info("rate added successfuly");
			return new ResponseEntity<String>(" Updated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>(" Some error occured", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteRateData", method = RequestMethod.POST)
	@Transactional
	public String deleteRateData(@RequestParam("rateTableId") Long id, Principal principal) {
		
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		log.info("Rate data selected for delete");
		RateTable result = masterAddressService.deleteRateData(id, loginedUser);
		if (result != null) {
			log.info("selected rate data deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:rateTableUI";
	}
		
	@RequestMapping(value = "/getRateReportPdf",method = RequestMethod.GET)
	@ResponseBody
	public void generateRateReportPdf(HttpServletResponse response) {
		masterAddressService.generateRateReportInPdf();
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


	// for showing config page
	@RequestMapping("/showConfig")
	@Transactional
	public String showConfig(Zone objZone, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Config Master data");
		return "configPage";
	}

	// for showing Config Table
	@RequestMapping(value = "/showAllActiveStatusConfig", method = RequestMethod.POST)
	@ResponseBody
	public List<ConfigVo> showConfigDataByStatus(@RequestParam String status) {
		if (status.equals("ALL"))
			return masterAddressService.getAllConfigList();
		else
			return masterAddressService.getAllConfigListByStatus(Enum.valueOf(Status.class, status));
	}

	// for save config information
	@RequestMapping(value = "/actionConfig", method = RequestMethod.POST)
	//@Transactional
	@ResponseBody
	public ResponseEntity<String> addConfigData(@Valid Config objConfig, Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingDivision = masterAddressService.fetchExistingConfig(objConfig.getConfigType());
		if (existingDivision)
			return new ResponseEntity<String>(objConfig.getConfigType() + " Already Exists", HttpStatus.OK);
		else {
			log.info("Add one config type with config value");
			model.addAttribute("role", loginedUser.getRole().getName());
			Config result = masterAddressService.saveConfigDetails(objConfig, loginedUser);
			if (result != null) {
				log.info("config info. added successfully");
				return new ResponseEntity<String>(objConfig.getConfigType() + " Added Successfully", HttpStatus.OK);
			} else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}
	}

	// for update config info.
	@RequestMapping(value = "/actionUpdateConfig", method = RequestMethod.POST)
	//@Transactional
	@ResponseBody
	public ResponseEntity<String> updateConfigTable(@Valid @RequestParam("configTypeId") Long id,
			Config objConfig, Model model, Principal principal) {
		log.info("Update selected Config Data");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		Boolean existingDivision = masterAddressService.fetchExistingConfig(id,objConfig);
		if (existingDivision)
			return new ResponseEntity<String>(objConfig.getConfigType() + " Already Exists", HttpStatus.OK);
		else {
			 Config result = masterAddressService.updateConfigDetails(id, loginedUser,objConfig);
			 if (result != null) {
				log.info("config updated ");
				return new ResponseEntity<String>(objConfig.getConfigType()+" Updated Successfully ", HttpStatus.OK);
			 } else {
				log.info("Some error occured");
				return new ResponseEntity<String>("Some error occured", HttpStatus.OK);
			}
		}
	}

	// for Delete Config  info.
	@RequestMapping(value = "/actionDeleteConfig", method = RequestMethod.POST)
	@Transactional
	public String deleteConfigData(@RequestParam("deletedId") Long id, Config objConfig,
			Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		if (loginedUser.isFirstLogin()) {
			return "redirect:/changePasswordHome";
		}
		log.info("Config  selected for delete");
		Optional<Config> configList = configRepository.findById(id);
		 Config configOldObj = configList.get();
		 Config result = masterAddressService.deleteConfigDetails(configOldObj, loginedUser);
		if (result != null) {
			log.info("selected Config deleted");
		} else {
			log.info("Some error occured");
		}
		return "redirect:showConfig";
	}

}