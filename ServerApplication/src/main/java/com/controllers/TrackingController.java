package com.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.TrackingCS;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.services.TrackingService;
import com.util.WebUtils;
import com.vo.TrackingVo;


@Controller
@Profile("server")
@RequestMapping("/tracking")
public class TrackingController {

	protected Logger log = LoggerFactory.getLogger(TrackingController.class);

	@Autowired
	private TrackingService trackingService;
	
	@Autowired
	private MasterAddressRepository masterAddressRepository;


	@RequestMapping("/bagInward")
    public String bagInward(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        //if user has logined first time & password has not been changed, forcefully move to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning inward bag page");
        return "inward";
    }

    @RequestMapping("/bagForward")
    public String bagForward(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        //if user has logined first time & passowrd has not been changed, forcefully movw to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		model.addAttribute("postofficeUser", loginedUser.getPostalCode());
        if(!(loginedUser.getRmsId() == null))
        {
			String rmsDetail = trackingService.findRmsName(loginedUser.getRmsId());
			String[] rmsDetails = rmsDetail.split(",");
    		model.addAttribute("rmsName",rmsDetails[0]);
    		model.addAttribute("rmsType",rmsDetails[1]);
        }
        log.info("Returning forward bag page");
        return "forward";
    }


    @RequestMapping(value = "/getBagDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<TrackingVo> getBagDetails(@RequestParam String bagId, @RequestParam String reportFor, Principal principal) {
    	log.info("inside getting bag details");

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        List<TrackingVo> bagDetails = trackingService.findByBagId(bagId, reportFor, loginedUser);
        return bagDetails;
	}

	@RequestMapping(value = "/generateEmptyBags", method = RequestMethod.POST)
	@ResponseBody
	public List<String> generateEmptyBags(@RequestParam int numberofEmptyBags, Principal principal) {
    	log.info("inside getting bag details");

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        List<String> bagids = new ArrayList<String>();
        if(numberofEmptyBags > 0)
        	bagids = trackingService.generateEmptyBags(numberofEmptyBags, loginedUser);
        return bagids;
	}

	@RequestMapping(value = "/deleteEmptyBags", method = RequestMethod.POST)
	@ResponseBody
	public String deleteEmptyBags(@RequestParam String bagId, Principal principal) {
    	log.info("inside getting bag details");
        String status = trackingService.deleteEmptyBags(bagId);
        return status;
	}

	/*
	 * @RequestMapping(value = "/forwardBags", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public void forwardBags(@RequestParam(value="bagIds[]")
	 * List<String> bagIds,String destination, String destinationType, Principal
	 * principal, Model model) {
	 * 
	 * com.domain.User loginedUser = (com.domain.User) ((Authentication)
	 * principal).getPrincipal();
	 * log.info("inside forwarding bags from"+loginedUser.getRole().getName());
	 * 
	 * List<TrackingCS> response = trackingService.forwardBags(bagIds, destination,
	 * destinationType, loginedUser);
	 * 
	 * if(!response.isEmpty())
	 * log.info("Bags forwared by "+loginedUser.getEmail()+" user successfully");
	 * else log.info("Bags id list not found"); }
	 */
	
	@RequestMapping(value = "/forwardBags", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> forwardBags(@RequestParam(value="bagIds[]") List<String> bagIds,String destination, String destinationType, Principal principal, Model model) {

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    	log.info("inside forwarding bags from"+loginedUser.getRole().getName());
    	
    	int postalcode = Integer.parseInt(destination);
    	String result=null;
    	
    	if(destinationType.equalsIgnoreCase("postoffice")) {
    	
    	Boolean status = masterAddressRepository.existsByPostalCodeAndStatus(Integer.parseInt(destination),Status.ACTIVE);	
    	if(status){ 
    		List<TrackingCS> response = trackingService.forwardBags(bagIds, destination, destinationType, loginedUser);

        	if(!response.isEmpty()) {
        		
        		log.info("Bags forwared by "+loginedUser.getEmail()+" user successfully");
        	result= "Bags forwared by "+loginedUser.getEmail()+" user successfully";
        	}
        	else {
        		log.info("Bags id list not found");
        		 }	
    	}else 
		
    		result=  "Postal code does not exist";
    	}
    	else if(destinationType.equalsIgnoreCase("RMS")) {
    		List<TrackingCS> response = trackingService.forwardBags(bagIds, destination, destinationType, loginedUser);

        	if(!response.isEmpty()) {
        	log.info("Bags forwared by "+loginedUser.getEmail()+" user successfully");
        	result= "Bags forwared by "+loginedUser.getEmail()+" user successfully";    		
        	}
        	else {
        		log.info("Bags id list not found");
        		 }
        }    	  
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/receiveBags", method = RequestMethod.POST)
	@ResponseBody
	public void receiveBags(@RequestParam(value="bagIds[]") List<String> bagIds, Principal principal) {

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    	log.info("inside forwarding bags from"+loginedUser.getRole().getName());

    	List<TrackingCS> response = trackingService.receiveBags(bagIds, loginedUser);

    	if(!response.isEmpty())
    		log.info("Bags received at "+loginedUser.getEmail()+" user");
    	else
    		log.info("Bags id list not found");
	}

	// @RequestMapping(value = "/savePoBag", method = RequestMethod.GET)
	// @ResponseBody
	// public List<TrackingCS> savePobag(@RequestParam Map<String, String> bagMap, Principal principal, Model model)
	// 		throws ParseException, JsonMappingException, JsonProcessingException {
	// 	log.info("inside save po bag");
	// 	User loginedUser = (User) ((Authentication) principal).getPrincipal();
	// 	String userInfo = WebUtils.toString(loginedUser); model.addAttribute("userInfo",userInfo);

	// 	return trackingService.saveTrackingDetails(bagMap,loginedUser);
	// }

	/*
	 * @RequestMapping(value = "/bagPrintPage", method = RequestMethod.GET) public
	 * String finalPage() { return "bagPrint"; }
	 */

	@RequestMapping(value = "/getAllBags", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingCS> finalPage(Principal principal,Model model) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo",userInfo);
		return trackingService.getAllBags();
	}

}
