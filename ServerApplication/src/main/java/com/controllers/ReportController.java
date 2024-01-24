package com.controllers;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.constants.ReportType;
import com.services.ReportService;
import com.vo.ParcelVo;

@RestController
@Profile({"client", "server"})
@RequestMapping("/report")
public class ReportController {

	private Logger log = LoggerFactory.getLogger(ReportController.class);
	private static final String fileNameExtensionXLX=".xls";
	private static final String fileNameExtensionCSV=".csv";
	private static final String SEPARATOR = "~";
	private static List<String> reportTypes = new ArrayList<String>();

	@Autowired
	private ReportService reportService;

	static{
		Collections.addAll(reportTypes, Stream.of(ReportType.values()).map(Enum::name).toArray(String[]::new));
	}

	@RequestMapping(path= "/reportHome", method = RequestMethod.GET)
    public String reportHome(Model model, Principal principal)
    {
    	log.info("inside reportHome");
    	model.addAttribute("reportTypes", reportTypes);

    	return "reportHome";//create reportHome.html

    }

	@RequestMapping(path= "/getReport", method = RequestMethod.GET)
    public String getReport(@RequestParam String reportType)
    {
    	log.info("inside getReport, reportType:"+reportType);

    	if(StringUtils.isNotBlank(reportType) && reportType.equals(ReportType.PARCEL_REPORT)){
    		log.debug("inside parcel report");
    	}

    	return "success";//change it accordingly

    }

	//change it accordingly
	private void getParcelReport(){
		log.debug("inside parcel report");
	}

	/*@RequestMapping(path = "/dailysummary", method = RequestMethod.GET)
	public List<Parcel> dailysummary(@RequestParam String division) throws ParseException {

		if(StringUtils.isNotBlank(division) && division.equals("All")){
			log.debug("inside parcel report");
			return reportService.getAllsummary();
		}else if((StringUtils.isNotBlank(division) && division.equals(Division.DHAKA.getName())       ||
				(StringUtils.isNotBlank(division)  && division.equals(Division.RAJSHAHI.getName()))   ||
				(StringUtils.isNotBlank(division)  && division.equals(Division.MYMENSINGH.getName())) ||
				(StringUtils.isNotBlank(division)  && division.equals(Division.KHULNA.getName()))     ||
				(StringUtils.isNotBlank(division)  && division.equals(Division.SYLHET.getName())))) {
			return reportService.getAllsummaryByDivision(division);
		}
		return null;
	}*/

	@RequestMapping(path = "/dailysummarys", method = RequestMethod.POST)
	public Map<String, List<ParcelVo>> dailysummarys(@RequestParam String fdate , @RequestParam String division, @RequestParam String zone, @RequestParam String district, @RequestParam String thana, @RequestParam String viewList, @RequestParam String subOffice, @RequestParam String aggregateBy) throws ParseException {

			return reportService.getsummary(fdate,zone,district,division,thana,subOffice,viewList,aggregateBy);

		/*
		 * if(division.isBlank() && district.isBlank() && thana.isBlank() &&
		 * zone.isBlank() && subOffice.isBlank() &&
		 * (list.isBlank()||list.equalsIgnoreCase("no grouping")) && !fdate.isBlank()) {
		 * return reportService.getAllsummaryBetweenDate(fdate); }
		 *
		 * if ((StringUtils.isNotBlank(division) && division.equals("All")) &&
		 * StringUtils.isNotBlank(fdate)) { System.out.println("all"); return
		 * reportService.getAllsummaryBetweenDate(fdate); } else if
		 * (StringUtils.isBlank(fdate)) { System.out.println("all without date"); return
		 * reportService.getAllsummary(); } System.out.println("date"); return
		 * reportService.getAllsummaryByDivisionAndDate(fdate, division);
		 */
	}

}
