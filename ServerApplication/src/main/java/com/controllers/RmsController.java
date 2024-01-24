package com.controllers;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.BagStatus;
import com.domain.RmsTable;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.repositories.RmsRepository;
import com.services.ReportService;
import com.services.TrackingService;
import com.util.WebUtils;
import com.vo.TrackingVo;


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


	@RequestMapping(value = "/saveRmsBag", method = RequestMethod.POST)
	@ResponseBody
	public List<TrackingVo> saveRmsbag(@RequestParam Map<String, List<String>> bagMap, Principal principal, Model model)
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
	    //if user has logined first time & passowrd has not been changed, forcefully movw to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

		model.addAttribute("user", loginedUser.getName());
        model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning Rms Page ");
		return "rmsPage";

	}

	@RequestMapping(value="/getReceivedBags", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, List<TrackingVo>> getReceivedBags( Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		Map<String, List<TrackingVo>> receivedBags = trackingService.findRmsByBagStatus(loginedUser.getRmsId());
		log.info("Getting Received Bags");
		return receivedBags;
	}

//RMS Logines User Bag Reports with datewise
	@RequestMapping(value = "/rmsReports")
	public String rmsReports( Model model, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
	    //if user has logined first time & passowrd has not been changed, forcefully movw to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		RmsTable obj = rmsRepository.findById(loginedUser.getRmsId()).orElse(null);
		model.addAttribute("rmsType", obj.getRmsType());
		model.addAttribute("rmsName", obj.getRmsName());
		log.info("Returning RMS Details");
		return "rmsReports";
	}


    @RequestMapping(value = "/getRMSReports", method = RequestMethod.POST)
    @ResponseBody
   	public Map<Object, List<TrackingVo>> getRMSReports( @RequestParam(value="fromdate") String fromdate, @RequestParam(value="todate") String todate,@RequestParam(value = "bagStatus") BagStatus bagStatus, Model model, Principal principal)throws Exception {
   		User loginedUser = (User) ((Authentication) principal).getPrincipal();
   		model.addAttribute("user", loginedUser.getUsername());
   		Map<Object, List<TrackingVo>>  bagList = reportService.findTodayBagListByRMs(loginedUser, fromdate,todate, bagStatus );
   		log.info("Returning RMS Reports");
   		return bagList;
   	}


    @RequestMapping(value = "/getUnbaggedList", method = RequestMethod.POST)
	@ResponseBody
	public List<TrackingVo> getUnbaggedList( Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		 List<TrackingVo> list= trackingService.getUnbaggedList(loginedUser);
	        log.info("Getting unbagged Bags");
	    	return list;
	}
    
    @RequestMapping(value = "/disposeEmptyBagRms", method = RequestMethod.GET)
	public String getEmptyBagDisposePageRms(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	    //if user has logined first time & passowrd has not been changed, forcefully movw to change password
        if(loginedUser.isFirstLogin())
        {
        	return "redirect:/changePasswordHome";
        }

		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag Page ");
		
		
		return "disposeEmptyBagRms";
	}
    
    @RequestMapping(value = "/disposeEmptyBagListRms", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> getEmptyBagDisposeList(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		
		return trackingService.getAllEmptyBagList(loginedUser);
	}
    
    @RequestMapping(value = "/disposedEmptyBagRms", method = RequestMethod.POST)
	public ResponseEntity<String> disposedEmptyBagRms(@RequestParam(value = "emptyBagId[]") List<String> emptyBagId,Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		trackingService.disposedEmptyBag(emptyBagId,loginedUser);
		
		return ResponseEntity.ok("Disposed Empty Bag");
	}
    
    @RequestMapping(value = "/disposedEmptyBagListRms", method = RequestMethod.GET)
	@ResponseBody
	public List<TrackingVo> disposedEmptyBag(Model model, Principal principal) {
		
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
	   
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		log.info("Returning dispose empty bag list ");
		
		
		return trackingService.disposedEmptyBagList(loginedUser);
	}


}
