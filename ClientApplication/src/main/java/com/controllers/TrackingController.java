package com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.domain.*;
import org.eclipse.jetty.util.IO;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.repositories.RmsRepository;
import com.services.MasterAddressService;
import com.services.TrackingService;
import com.util.WebUtils;


@Controller
@Profile("server")
@RequestMapping("/tracking")
public class TrackingController {

	protected Logger log = LoggerFactory.getLogger(TrackingController.class);

	@Autowired
	private TrackingService trackingService;

	@RequestMapping("/bagInward")
    public String bagInward(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        log.info("Returning inward bag page");
        return "inward";
    }

    @RequestMapping("/bagForward")
    public String bagForward(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
        model.addAttribute("postofficeUser", loginedUser.getPostalCode());
        if(!(loginedUser.getRmsId() == null))
        {
        	String rmsName = trackingService.findRmsName(loginedUser.getRmsId());
    		model.addAttribute("rmsUserInfo",rmsName);
        }
        log.info("Returning forward bag page");
        return "forward";
    }


    @RequestMapping(value = "/getBagDetails", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingCS> getBagDetails(@RequestParam String bagId, @RequestParam String reportFor, Principal principal) {
    	log.info("inside getting bag details");

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        List<TrackingCS> bagDetails = trackingService.findByBagId(bagId, reportFor, loginedUser);
        return bagDetails;
	}

	@RequestMapping(value = "/generateEmptyBags", method = RequestMethod.GET)
	@ResponseBody
	public List<String> generateEmptyBags(@RequestParam int numberofEmptyBags, Principal principal) {
    	log.info("inside getting bag details");

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        List<String> bagids = trackingService.generateEmptyBags(numberofEmptyBags, loginedUser);
        return bagids;
	}

	@RequestMapping(value = "/deleteEmptyBags", method = RequestMethod.GET)
	@ResponseBody
	public String deleteEmptyBags(@RequestParam String bagId, Principal principal) {
    	log.info("inside getting bag details");
        String status = trackingService.deleteEmptyBags(bagId);
        return status;
	}

	@RequestMapping(value = "/forwardBags", method = RequestMethod.GET)
	@ResponseBody
	public void forwardBags(@RequestParam(value="bagIds[]") List<String> bagIds,String destination, String destinationType, Principal principal, Model model) {

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    	log.info("inside forwarding bags from"+loginedUser.getRole().getName());
    	trackingService.forwardBags(bagIds, destination, destinationType, loginedUser);
	}

	@RequestMapping(value = "/receiveBags", method = RequestMethod.GET)
	@ResponseBody
	public void receiveBags(@RequestParam(value="bagIds[]") List<String> bagIds, Principal principal) {

        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    	log.info("inside forwarding bags from"+loginedUser.getRole().getName());
    	trackingService.receiveBags(bagIds, loginedUser);
	}

	@RequestMapping(value = "/savePoBag", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingCS> savePobag(@RequestParam Map<String, String> bagMap, Principal principal, Model model)
			throws ParseException, JsonMappingException, JsonProcessingException {
		log.info("inside save po bag");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser); model.addAttribute("userInfo",userInfo);

		return trackingService.saveTrackingDetails(bagMap,loginedUser);
	}

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
