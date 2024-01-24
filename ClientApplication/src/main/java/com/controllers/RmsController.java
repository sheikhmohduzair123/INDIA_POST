package com.controllers;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.constants.BagStatus;
import com.domain.RmsTable;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.repositories.RmsRepository;
import com.services.ReportService;
import com.services.TrackingService;
import com.util.WebUtils;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Profile("server")
@RequestMapping("/rms")
public class RmsController {
	protected Logger log = LoggerFactory.getLogger(RmsController.class);

    @Autowired
    private TrackingService trackingService;

    @Autowired
    private ReportService reportService;
    
    @Autowired
    private RmsRepository rmsRepository;


	@RequestMapping(value = "/saveRmsBag", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingCS> saveRmsbag(@RequestParam Map<String, List<String>> bagMap, Principal principal, Model model)
			throws ParseException, JsonMappingException, JsonProcessingException {
		log.info("Inside Rms Rebagging");
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		return trackingService.saveRmsBagDetails(bagMap, loginedUser);

	}

	@RequestMapping("/rmsPage")
	public String rmsPage(Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Rms Page ");
		return "rmsPage";

	}

	@RequestMapping("/getReceivedBags")
	@ResponseBody
	public Map<String, List<TrackingCS>> getReceivedBags( Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		Map<String, List<TrackingCS>> receivedBags = trackingService.findRmsByBagStatus(loginedUser.getRmsId());
		log.info("Getting Received Bags");
		return receivedBags;
	}

//RMS Logines User Bag Reports with datewise
	@RequestMapping(value = "/rmsReports")
	public String rmsReports( Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		RmsTable obj = rmsRepository.findById(loginedUser.getRmsId()).orElse(null);
		model.addAttribute("rmsType", obj.getRmsType());
		model.addAttribute("rmsName", obj.getRmsName());
		log.info("Returning RMS Details");
		return "rmsReports";
	}


    @RequestMapping(value = "/getRMSReports", method = RequestMethod.GET)
    @ResponseBody
   	public Map<Object, List<TrackingDetails>> getRMSReports( @RequestParam(value="fromdate") String fromdate, @RequestParam(value="todate") String todate,@RequestParam(value = "bagStatus") BagStatus bagStatus, Model model, Principal principal)throws Exception {
   		User loginedUser = (User) ((Authentication) principal).getPrincipal();
   		model.addAttribute("user", loginedUser.getUsername());
   		Map<Object, List<TrackingDetails>>  bagList = reportService.findTodayBagListByRMs(loginedUser, fromdate,todate, bagStatus );
   		log.info("Returning RMS Reports");
   		return bagList;
   	}

}
