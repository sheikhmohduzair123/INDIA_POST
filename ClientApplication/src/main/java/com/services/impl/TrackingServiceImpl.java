package com.services.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ch.qos.logback.core.db.dialect.SybaseSqlAnywhereDialect;
import com.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.*;
import com.vo.*;
import net.sf.jasperreports.engine.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.constants.BagFillStatus;
import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.MasterAddressService;
import com.services.TrackingService;
import com.util.JsonUtils;
import com.vo.ParcelVo;
import com.vo.RateCalculation;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class TrackingServiceImpl implements TrackingService {

	protected Logger log = LoggerFactory.getLogger(TrackingServiceImpl.class);

	@Autowired
	private ParcelRepository parcelRepository;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Value("${reportexport.path}")
	private String exportfilepath;

	@Autowired
	TrackingCSRepository trackingCSRepository;

	@Autowired
	RmsRepository rmsRepository;

	@Autowired
	MasterAddressRepository masterAddressRepository;

	@Autowired
	TrackingDetailsRepository trackingDetailsRepository;

	@Autowired
	SUserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private MasterAddressService masterAddress;

	@Override
	public List<TrackingCS> getAllBags() {
		return trackingCSRepository.findAll();
	}

	@Override
	public List<TrackingCS> findByBagStatus() {
		log.info("inside implementation of getting tracking details for a bag");
		List<TrackingCS> receivedBagList = trackingCSRepository.findByBagStatus(BagStatus.IN);
		return receivedBagList;
	}

	@Override
	public List<MasterAddress> getPostOfficeName() {
		log.info("inside implementation of get postoffice data");
		List<MasterAddress> polist = masterAddressRepository.findAllByStatus(Status.ACTIVE);
		return polist;
	}

	@Override
	public List<RmsTable> getRMSdata() {
		log.info("inside implementation of get rms data");
		List<RmsTable> rmslist = rmsRepository.findAllByStatus(Status.ACTIVE);
		return rmslist;
	}

	@Override
	public List<TrackingCS> findByBagId(String bagId, String reportFor, User loginedUser) {
		log.debug("fetching bag id details");
		LocationType location = null;
		if (loginedUser.getRole().getName().equals("ROLE_PO_USER"))
			location = LocationType.POST_OFFICE;
		else if (loginedUser.getRole().getName().equals("ROLE_RMS_USER"))
			location = LocationType.RMS;

		List<TrackingCS> bag_details = trackingCSRepository.findByBagId(bagId);

		if (!bag_details.isEmpty()) {
			if (reportFor.equalsIgnoreCase("forward")) {
				if (bag_details.get(0).getBagStatus().equals(BagStatus.OUT))
					bag_details.get(0).setTrackingDesc("bag status is already out");
			} else if (reportFor.equalsIgnoreCase("inward")) {
				if (!bag_details.get(0).getBagStatus().equals(BagStatus.OUT))
					bag_details.get(0).setTrackingDesc("bag status is not out");
			} else {
				if (bag_details.get(0).getLocationType().equals(location)) {
					if ((location.equals(LocationType.POST_OFFICE))
							&& (!bag_details.get(0).getLocationId().equals(loginedUser.getPostalCode()))) {
						bag_details.get(0).setTrackingDesc("wrong destination");
					} else if ((location.equals(LocationType.RMS))
							&& (!bag_details.get(0).getLocationId().equals(loginedUser.getRmsId()))) {
						bag_details.get(0).setTrackingDesc("wrong destination");
					}
				} else {
					bag_details.get(0).setTrackingDesc("wrong destination");
				}
			}
		}
		return bag_details;
	}

	@Override
	public List<String> generateEmptyBags(int numberofEmptyBags, User user) {
		RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(user.getRmsId(), Status.ACTIVE);

		List<String> bagids = new ArrayList<String>();
		for (int i = 0; i < numberofEmptyBags; i++) {
			// generate bag id
			String numericString = "0123456789";
			StringBuilder sb = new StringBuilder(9);

			for (int j = 0; j < 9; j++) {
				int index = (int) (numericString.length() * Math.random());
				sb.append(numericString.charAt(index));
			}
			bagids.add(sb.toString());
			TrackingCS bag = new TrackingCS();
			bag.setBagFillStatus(BagFillStatus.EMPTY);
			bag.setBagDesc("Empty Bag");
			bag.setBagId(sb.toString());
			bag.setBagStatus(BagStatus.CREATED);
			bag.setBagTitle("Empty Bag");
			bag.setCreatedBy(user);
			bag.setCreatedOn(new Date());
			bag.setUpdatedBy(user);
			bag.setUpdatedOn(bag.getCreatedOn());
			if (user.getRole().getName().equals("ROLE_RMS_USER")) {
				bag.setLocationType(LocationType.RMS);
				bag.setLocationId(user.getRmsId());
				bag.setTrackingDesc("Empty Bag created at RMS " + rmsName.getRmsName());
			} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
				bag.setLocationType(LocationType.POST_OFFICE);
				bag.setLocationId(user.getPostalCode());
				MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
				bag.setTrackingDesc("Empty Bag created at Postal Code " + user.getPostalCode() + " ("
						+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
			}
			bag.setStatus(Status.ACTIVE);
			trackingCSRepository.save(bag);

			TrackingDetails bag1 = new TrackingDetails();
			bag1.setBagFillStatus(BagFillStatus.EMPTY);
			bag1.setBagDesc("Empty Bag");
			bag1.setBagId(sb.toString());
			bag1.setBagStatus(BagStatus.CREATED);
			bag1.setBagTitle("Empty Bag");
			bag1.setCreatedBy(user);
			bag1.setCreatedOn(bag.getUpdatedOn());
			bag1.setUpdatedBy(user);
			bag1.setUpdatedOn(bag.getUpdatedOn());
			if (user.getRole().getName().equals("ROLE_RMS_USER")) {
				bag1.setLocationType(LocationType.RMS);
				bag1.setLocationId(user.getRmsId());
				bag.setTrackingDesc("Empty Bag created at RMS " + rmsName.getRmsName());
			} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
				bag1.setLocationType(LocationType.POST_OFFICE);
				bag1.setLocationId(user.getPostalCode());
				MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
				bag1.setTrackingDesc("Empty Bag created at Postal Code " + user.getPostalCode() + " ("
						+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
			}
			bag1.setStatus(Status.ACTIVE);
			trackingDetailsRepository.save(bag1);
		}
		return bagids;
	}

	@Override
	public String deleteEmptyBags(String bagId) {
		List<TrackingCS> bag = trackingCSRepository.findByBagId(bagId);

		// bag deleted from db only if created at current user && is empty
		if (bag.get(0).getBagStatus().equals(BagStatus.CREATED) && (bag.get(0).getObjParcel() == null)) {
			trackingCSRepository.deleteAll(bag);
			List<TrackingCS> checkbag = trackingCSRepository.findByBagId(bagId);

			if (checkbag.isEmpty()) {
				List<TrackingDetails> bag1 = trackingDetailsRepository.findByBagIdAndStatus(bagId, Status.ACTIVE);
				trackingDetailsRepository.deleteAll(bag1);
				List<TrackingDetails> checkbag1 = trackingDetailsRepository.findByBagIdAndStatus(bagId, Status.ACTIVE);

				if (checkbag1.isEmpty())
					return "success";
			}
			return "fail";
		}
		return "success";
	}

	@Override
	public void forwardBags(List<String> bagIds, String destination, String destinationType, User user) {

		for (String bagid : bagIds) {
			Date date = new Date();
			List<TrackingCS> bag = trackingCSRepository.findByBagId(bagid);

			for (TrackingCS parcel : bag) {
				parcel.setpStatus(PStatus.OUT);
				parcel.setBagStatus(BagStatus.OUT);
				parcel.setUpdatedBy(user);
				parcel.setUpdatedOn(date);
				if (destinationType.equalsIgnoreCase("RMS")) {
					parcel.setLocationType(LocationType.RMS);
					parcel.setLocationId(Long.parseLong(destination));
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(Long.parseLong(destination),
							Status.ACTIVE);
					parcel.setTrackingDesc(PStatus.OUT + " to RMS  " + rmsName.getRmsName());
				} else if (destinationType.equalsIgnoreCase("postoffice")) {
					parcel.setLocationType(LocationType.POST_OFFICE);
					parcel.setLocationId(Long.parseLong(destination));
					MasterAddress postaldetails = masterAddress.getPostalData((int) Long.parseLong(destination));
					parcel.setTrackingDesc(PStatus.OUT + " to Postal Code " + Long.parseLong(destination) + " ("
							+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingCSRepository.save(parcel);
			}
			List<TrackingDetails> bag1 = trackingDetailsRepository.findByBagIdAndStatus(bagid, Status.ACTIVE);
			for (TrackingDetails parcel1 : bag1) {
				TrackingDetails parcelNew = new TrackingDetails();
				parcelNew.setStatus(Status.DISABLED);
				parcelNew.setBagDesc(parcel1.getBagDesc());
				parcelNew.setBagFillStatus(parcel1.getBagFillStatus());
				parcelNew.setBagId(parcel1.getBagId());
				parcelNew.setBagStatus(parcel1.getBagStatus());
				parcelNew.setBagTitle(parcel1.getBagTitle());
				parcelNew.setCreatedBy(parcel1.getCreatedBy());
				parcelNew.setCreatedOn(parcel1.getCreatedOn());
				parcelNew.setLocationId(parcel1.getLocationId());
				parcelNew.setLocationType(parcel1.getLocationType());
				parcelNew.setObjParcel(parcel1.getObjParcel());
				parcelNew.setpStatus(parcel1.getpStatus());
				parcelNew.setTrackingDesc(parcel1.getTrackingDesc());
				parcelNew.setUpdatedBy(parcel1.getUpdatedBy());
				parcelNew.setUpdatedOn(parcel1.getUpdatedOn());
				trackingDetailsRepository.save(parcelNew);
				TrackingDetails parcel2 = parcel1;
				parcel2.setpStatus(PStatus.OUT);
				parcel2.setBagStatus(BagStatus.OUT);
				parcel2.setUpdatedBy(user);
				parcel2.setUpdatedOn(date);
				if (destinationType.equalsIgnoreCase("RMS")) {
					parcel2.setLocationType(LocationType.RMS);
					parcel2.setLocationId(Long.parseLong(destination));
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(Long.parseLong(destination),
							Status.ACTIVE);
					parcel2.setTrackingDesc(PStatus.OUT + " to RMS  " + rmsName.getRmsName());
				} else if (destinationType.equalsIgnoreCase("postoffice")) {
					parcel2.setLocationType(LocationType.POST_OFFICE);
					parcel2.setLocationId(Long.parseLong(destination));
					MasterAddress postaldetails = masterAddress.getPostalData((int) Long.parseLong(destination));
					parcel2.setTrackingDesc(PStatus.OUT + " to Postal Code " + Long.parseLong(destination) + " ("
							+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingDetailsRepository.save(parcel2);
			}
		}
	}

	@Override
	public void receiveBags(List<String> bagIds, User user) {
		Date date = new Date();

		for (String bagid : bagIds) {
			List<TrackingCS> bag = trackingCSRepository.findByBagId(bagid);
			for (TrackingCS parcel : bag) {
				parcel.setpStatus(PStatus.IN);
				parcel.setBagStatus(BagStatus.IN);
				parcel.setUpdatedBy(user);
				parcel.setUpdatedOn(date);
				if (user.getRole().getName().equals("ROLE_RMS_USER")) {
					parcel.setLocationType(LocationType.RMS);
					parcel.setLocationId(user.getRmsId());
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(user.getRmsId(), Status.ACTIVE);
					parcel.setTrackingDesc(PStatus.IN + " to RMS " + rmsName.getRmsName());
				} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
					parcel.setLocationType(LocationType.POST_OFFICE);
					parcel.setLocationId(user.getPostalCode());
					MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
					parcel.setTrackingDesc(PStatus.IN + " at Postal Code " + user.getPostalCode() + " ("
							+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingCSRepository.save(parcel);
			}
			List<TrackingDetails> bag1 = trackingDetailsRepository.findByBagIdAndStatus(bagid, Status.ACTIVE);
			for (TrackingDetails parcel1 : bag1) {
				TrackingDetails parcelNew = new TrackingDetails();
				parcelNew.setStatus(Status.DISABLED);
				parcelNew.setBagDesc(parcel1.getBagDesc());
				parcelNew.setBagFillStatus(parcel1.getBagFillStatus());
				parcelNew.setBagId(parcel1.getBagId());
				parcelNew.setBagStatus(parcel1.getBagStatus());
				parcelNew.setBagTitle(parcel1.getBagTitle());
				parcelNew.setCreatedBy(parcel1.getCreatedBy());
				parcelNew.setCreatedOn(parcel1.getCreatedOn());
				parcelNew.setLocationId(parcel1.getLocationId());
				parcelNew.setLocationType(parcel1.getLocationType());
				parcelNew.setObjParcel(parcel1.getObjParcel());
				parcelNew.setpStatus(parcel1.getpStatus());
				parcelNew.setTrackingDesc(parcel1.getTrackingDesc());
				parcelNew.setUpdatedBy(parcel1.getUpdatedBy());
				parcelNew.setUpdatedOn(parcel1.getUpdatedOn());
				trackingDetailsRepository.save(parcelNew);
				parcel1.setpStatus(PStatus.IN);
				parcel1.setBagStatus(BagStatus.IN);
				parcel1.setUpdatedBy(user);
				parcel1.setUpdatedOn(date);
				if (user.getRole().getName().equals("ROLE_RMS_USER")) {
					parcel1.setLocationType(LocationType.RMS);
					parcel1.setLocationId(user.getRmsId());
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(user.getRmsId(), Status.ACTIVE);
					parcel1.setTrackingDesc(PStatus.IN + " to RMS " + rmsName.getRmsName());
				} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
					parcel1.setLocationType(LocationType.POST_OFFICE);
					parcel1.setLocationId(user.getPostalCode());
					MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
					parcel1.setTrackingDesc(PStatus.IN + " at Postal Code " + user.getPostalCode() + " ("
							+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingDetailsRepository.save(parcel1);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingCS> saveTrackingDetails(Map<String, String> bagMap, User user) throws ParseException {

		log.info("inside implementation of saveTrackingDetails");
		// string map
		String data = "";
		List<TrackingCS> resBag = new ArrayList<TrackingCS>();

		for (Map.Entry<String, String> entry : bagMap.entrySet()) {
			data = entry.getKey();
		}

		// convert stringmap to map
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(data);

		// convert json to map
		Map<String, Object> map = JsonUtils.jsonToMap(json);

		// iterate map
		Iterator<Entry<String, Object>> itr = map.entrySet().iterator();
		Map<String, List<String>> bagParcel = null;
		while (itr.hasNext()) {
			Entry<String, Object> itrMap = itr.next();

			if (itrMap.getKey().equals("bagDetails")) {

				Map<String, String> bagDetails = (Map<String, String>) itrMap.getValue();

				Iterator<?> itrParcel = bagParcel.entrySet().iterator();
				Iterator<?> itrDetails = bagDetails.entrySet().iterator();

				while (itrParcel.hasNext() || itrDetails.hasNext()) {
					Map.Entry<?, ?> itrMapParcel = (Map.Entry<?, ?>) itrParcel.next();

					Map.Entry<?, ?> itrMapDetails = (Map.Entry<?, ?>) itrDetails.next();
					String numericString = "0123456789";
					StringBuilder sb = new StringBuilder(9);

					for (int i = 0; i < 9; i++) {
						int index = (int) (numericString.length() * Math.random());
						sb.append(numericString.charAt(index));
					}

					int flag = 0;
					String[] bagDetailss = itrMapDetails.getValue().toString().split(",");

					for (String s : bagDetailss) {
						++flag;
					}

					for (String i : (List<String>) itrMapParcel.getValue()) {
						if (flag == 0) {

							TrackingCS trackingCS = saveData(i, user, sb.toString(), "", "");
							resBag.add(trackingCS);
						} else if (flag == 1) {

							TrackingCS trackingCS = saveData(i, user, sb.toString(), "", bagDetailss[0]);
							resBag.add(trackingCS);
						} else if (bagDetailss[1].length() > 0 && bagDetailss[0].length() == 0) {

							TrackingCS trackingCS = saveData(i, user, sb.toString(), bagDetailss[1], "");
							resBag.add(trackingCS);
						} else if (bagDetailss[0].length() > 0 && bagDetailss[1].length() > 0) {

							TrackingCS trackingCS = saveData(i, user, sb.toString(), bagDetailss[1], bagDetailss[0]);
							resBag.add(trackingCS);
						}
					}
					/*
					 * for (String i : (List<String>) itrMapParcel.getValue()) { TrackingCS
					 * trackingCS = saveData(i, user, sb.toString(), ArrayUtils.isEmpty(bagDetailss)
					 * == true ? "" : bagDetailss[1], ArrayUtils.isEmpty(bagDetailss) == true ? "" :
					 * bagDetailss[0]); resBag.add(trackingCS);
					 *
					 * }
					 */
				}

			}
			if (itrMap.getKey().equals("bagParcel")) {

				bagParcel = (Map<String, List<String>>) itrMap.getValue();
			}
		}

		log.info(" Inside Save Bag And returning Created Bag to UI");
		return resBag;
	}

	public TrackingCS saveData(String trackId, User user, String bagId, String bagDesc, String bagTitle) {
		log.info("inside save Tracking details" + bagId);
		Parcel parcel = parcelRepository.findParcelByTrackId(trackId);
		log.info("inside save Tracking details" + parcel);
		TrackingCS trackingCS = new TrackingCS();
		trackingCS.setObjParcel(parcel);
		trackingCS.setBagFillStatus(BagFillStatus.FILLED);
		trackingCS.setBagId(bagId);
		trackingCS.setBagTitle(bagTitle);
		trackingCS.setBagDesc(bagDesc);
		trackingCS.setBagStatus(BagStatus.CREATED);
		trackingCS.setLocationId(user.getPostalCode());
		trackingCS.setLocationType(LocationType.POST_OFFICE);
		trackingCS.setpStatus(PStatus.BAGGED);
		trackingCS.setUpdatedOn(new Date());
		trackingCS.setCreatedOn(trackingCS.getUpdatedOn());
		trackingCS.setCreatedBy(user);
		trackingCS.setUpdatedBy(user);
		if (user.getRole().getName().equals("ROLE_RMS_USER")) {
			trackingCS.setLocationType(LocationType.RMS);
			trackingCS.setLocationId(user.getRmsId());
		} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
			trackingCS.setLocationType(LocationType.POST_OFFICE);
			trackingCS.setLocationId(user.getPostalCode());
			MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
			trackingCS.setTrackingDesc(PStatus.BAGGED + " at " + user.getPostalCode() + " ("
					+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
		}
		// update parcel status while bagged
		parcel.setpStatus(PStatus.BAGGED.toString());
		parcelRepository.save(parcel);
		trackingCS.setCreatedOn(trackingCS.getUpdatedOn());
		trackingCS.setStatus(Status.ACTIVE);
		trackingCSRepository.save(trackingCS);
		TrackingDetails trackingDetails = new TrackingDetails();
		trackingDetails.setObjParcel(parcel);
		trackingDetails.setBagFillStatus(BagFillStatus.FILLED);
		trackingDetails.setBagId(bagId);
		trackingDetails.setBagTitle(bagTitle);
		trackingDetails.setBagDesc(bagDesc);
		trackingDetails.setBagStatus(BagStatus.CREATED);
		trackingDetails.setLocationId(user.getPostalCode());
		trackingDetails.setLocationType(LocationType.POST_OFFICE);
		trackingDetails.setpStatus(PStatus.BAGGED);
		trackingDetails.setUpdatedOn(trackingCS.getUpdatedOn());
		trackingDetails.setCreatedOn(trackingCS.getUpdatedOn());
		trackingDetails.setCreatedBy(user);
		trackingDetails.setCreatedBy(user);
		if (user.getRole().getName().equals("ROLE_RMS_USER")) {
			trackingDetails.setLocationType(LocationType.RMS);
			trackingDetails.setLocationId(user.getRmsId());
		} else if (user.getRole().getName().equals("ROLE_PO_USER")) {
			trackingDetails.setLocationType(LocationType.POST_OFFICE);
			trackingDetails.setLocationId(user.getPostalCode());
			MasterAddress postaldetails = masterAddress.getPostalData((int) user.getPostalCode());
			trackingDetails.setTrackingDesc(PStatus.BAGGED + " at Postal Code " + user.getPostalCode() + " ("
					+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
		}
		trackingDetails.setStatus(Status.ACTIVE);
		trackingDetailsRepository.save(trackingDetails);
		return trackingCS;
	}

	@Override
	public void getAllParcel(String bagId) throws JRException {

	//	try {

			List<TrackingCS> parCs = trackingCSRepository.findAllByBagId(bagId);
			List<JasperVo> bagParcelListJasperVoList = new ArrayList<>();
			String bagTitle= "";
			String bagDesc = "";

			for(TrackingCS cs:parCs){
				bagTitle=cs.getBagTitle();
				bagDesc=cs.getBagDesc();

				JasperVo jasperVo = new JasperVo();
				jasperVo.setBagDesc(cs.getBagDesc());
				jasperVo.setContent(cs.getObjParcel().getParcelContent());
				jasperVo.setTrackid(cs.getObjParcel().getTrackId());
				jasperVo.setReceiverAddress(
						cs.getObjParcel().getReceiverAddress().getAddressLine1()+","
								+(cs.getObjParcel().getReceiverAddress().getAddressLine2().equals("") ? "":cs.getObjParcel().getReceiverAddress().getAddressLine2().concat(","))
								+(cs.getObjParcel().getReceiverAddress().getLandmark().equals("") ? "":cs.getObjParcel().getReceiverAddress().getLandmark().concat(","))
								+cs.getObjParcel().getReceiverAddress().getSubOffice()+","
								+cs.getObjParcel().getReceiverAddress().getThana()+","
								+cs.getObjParcel().getReceiverAddress().getDistrict()+","
								+cs.getObjParcel().getReceiverAddress().getDivision()+","
								+cs.getObjParcel().getReceiverAddress().getZone()+"-"
								+cs.getObjParcel().getReceiverAddress().getPostalCode()
				);
				jasperVo.setWeight(cs.getObjParcel().getActWt()+"");
				jasperVo.setRecipientnameandmob(cs.getObjParcel().getRecipientName()+"\n"+cs.getObjParcel().getRecipientMobileNumber());
				bagParcelListJasperVoList.add(jasperVo);
			}

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(exportfilepath + "bag-print.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(bagParcelListJasperVoList);


			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("parcelList", jrBeanCollectionDataSource);
			parameters.put("bagTitle",bagTitle);
			parameters.put("bagDesc",bagDesc);
			parameters.put("bagId",bagId);


			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					new JREmptyDataSource());

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportfilepath + "bagParcelList.pdf");
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}

	}

	@Override
	public void getBagLabel(String bagId, User loginedUser) throws JRException {
		List<MasterAddress> masterAddressList = masterAddressRepository
				.findByPostalCodeAndStatus((int) loginedUser.getPostalCode(), Status.ACTIVE);
		String address = "";
		for (MasterAddress masterAddress : masterAddressList) {
			address = masterAddress.getSubOffice() + "," + masterAddress.getDistrict() + "-"
					+ masterAddress.getPostalCode();
		}
//		try {
			int flag = 0;
			float totalWeight = 0;
			List<TrackingCS> parCs = trackingCSRepository.findAllByBagId(bagId);
			if (parCs.get(0).getObjParcel() == null) {
				totalWeight = 0;
				flag = 0;
			} else {
				for (TrackingCS s : parCs) {

					totalWeight += s.getObjParcel().getActWt();
					++flag;
				}
			}
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(exportfilepath + "bag-label.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(parCs);

			// Add parameters
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("totalWeight", "" + totalWeight);
			parameters.put("totalParcel", "" + flag);
			parameters.put("poName", address);

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportfilepath + "bagLabel.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
	}

	@Override
	public Map<String, List<TrackingCS>> findRmsByBagStatus(Long locationId) {
		List<TrackingCS> receivedBagList = null;
		receivedBagList = trackingCSRepository.findByBagStatusAndLocationIdAndLocationTypeAndObjParcelNotNull(
				BagStatus.IN, locationId, LocationType.RMS);
		return receivedBagList.stream().collect(Collectors.groupingBy(trackingCS -> trackingCS.getBagId()));
	}

	@Override
	public TrackingCS saveRmsBagData(String trackDetail, List<String> unBaggedList, User user, String bagId,
			String bagTitle, String bagDesc) {
		log.info("inside save Bag Details");
		TrackingCS trackingcs = null;

		String[] trackingBagDetails;
		String[] unBaggedParcelList;

		trackingBagDetails = trackDetail.toString().split("/");

		List<TrackingCS> BagList = null;
		RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(user.getRmsId(), Status.ACTIVE);
		Date date = new Date();
		// Saving Old Track Id without unbaggingDetails
		if (unBaggedList.size() == 0) {
			BagList = trackingCSRepository.findByBagId(trackingBagDetails[1]);
			for (TrackingCS trackOld : BagList) {
				trackOld.setBagStatus(BagStatus.CREATED);
				trackOld.setStatus(Status.ACTIVE);
				trackOld.setpStatus(PStatus.REBAGGED);
				trackOld.setUpdatedBy(user);
				trackOld.setUpdatedOn(date);
				trackOld.setLocationId(user.getRmsId());
				trackOld.setLocationType(LocationType.RMS);
				trackOld.setTrackingDesc(PStatus.REBAGGED + " at RMS " + rmsName.getRmsName());
				trackingCSRepository.save(trackOld);
			}

			List<TrackingDetails> bag1 = trackingDetailsRepository.findByBagIdAndStatus(trackingBagDetails[1],
					Status.ACTIVE);
			for (TrackingDetails parcel1 : bag1) {
				parcel1.setStatus(Status.DISABLED);
				trackingDetailsRepository.save(parcel1);
			}
		}

		// Saving Old TrackId with unbagged Parcels
		if (unBaggedList.size() > 0) {
			String unBaggedParcel = "";

			for (String unBaggedParcel1 : unBaggedList) {
				unBaggedParcel = unBaggedParcel1;

				unBaggedParcelList = unBaggedParcel.toString().split("/");

				// checking the old bag id with the unbagged bag Id
				Parcel parcel = parcelRepository.findParcelByTrackId(unBaggedParcelList[0]);

				BagList = trackingCSRepository.findByBagIdAndObjParcel(unBaggedParcelList[1], parcel);
				for (TrackingCS trackOldId : BagList) {
					trackOldId.setpStatus(PStatus.UNBAGGED);
					trackOldId.setUpdatedBy(user);
					trackOldId.setUpdatedOn(date);
					trackOldId.setLocationId(user.getRmsId());
					trackOldId.setLocationType(LocationType.RMS);
					trackOldId.setTrackingDesc(PStatus.UNBAGGED + " at RMS " + rmsName.getRmsName());
					trackingCSRepository.save(trackOldId);
				}
			}
		}

		// New BagId Details with all the new details
		Parcel parcel = parcelRepository.findParcelByTrackId(trackingBagDetails[0]);

		List<TrackingDetails> trackingDetails = trackingDetailsRepository.findByObjParcel(parcel);
		for (TrackingDetails trackingObj : trackingDetails) {
			trackingObj.setStatus(Status.DISABLED);
			trackingDetailsRepository.save(trackingObj);
		}
		TrackingDetails trackingDetail = new TrackingDetails();

		trackingDetail.setLocationType(LocationType.RMS);
		trackingDetail.setLocationId(user.getRmsId());
		trackingDetail.setBagId(bagId);
		trackingDetail.setBagDesc(bagDesc);
		trackingDetail.setBagTitle(bagTitle);
		trackingDetail.setCreatedBy(user);
		trackingDetail.setObjParcel(parcel);
		trackingDetail.setBagStatus(BagStatus.CREATED);
		trackingDetail.setpStatus(PStatus.REBAGGED);
		trackingDetail.setCreatedOn(date);
		trackingDetail.setStatus(Status.ACTIVE);
		trackingDetail.setUpdatedBy(user);
		trackingDetail.setUpdatedOn(date);
		trackingDetail.setTrackingDesc(PStatus.REBAGGED + " at RMS " + rmsName.getRmsName());
		trackingDetailsRepository.save(trackingDetail);

		List<TrackingCS> trackingCS = trackingCSRepository.findByObjParcel(parcel);
		for (TrackingCS trackingcs1 : trackingCS) {
			trackingcs = trackingcs1;
			trackingcs1.setBagId(bagId);
			trackingcs1.setBagDesc(bagDesc);
			trackingcs1.setBagTitle(bagTitle);
			trackingcs1.setCreatedBy(user);
			trackingcs1.setCreatedOn(date);
			trackingcs1.setUpdatedBy(user);
			trackingcs1.setUpdatedOn(date);
			trackingcs1.setpStatus(PStatus.REBAGGED);
			trackingcs1.setStatus(Status.ACTIVE);
			trackingcs1.setBagStatus(BagStatus.CREATED);
			trackingcs1.setLocationType(LocationType.RMS);
			trackingcs1.setLocationId(user.getRmsId());
			trackingcs1.setTrackingDesc(PStatus.REBAGGED + " at RMS " + rmsName.getRmsName());
			trackingCSRepository.save(trackingcs1);
		}
		log.info("saved Bag Details");
		return trackingcs;
	}

	@Override
	public List<Parcel> getAllParcels(long postalCode) {
		List<User> users = userRepository.findByPostalCodeAndStatus(postalCode, Status.ACTIVE);
		List<Integer> userIdList = new ArrayList<Integer>();
		for (User i : users) {
			userIdList.add(i.getId());
		}
		return parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(PStatus.UNBAGGED.toString(), "booked",
				userIdList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TrackingCS> saveRmsBagDetails(Map<String, List<String>> bagMap, User user) throws ParseException {

		List<TrackingCS> NewBagList = new ArrayList<TrackingCS>();

		String data = "";
		for (Entry<String, List<String>> entry : bagMap.entrySet()) {
			data = entry.getKey();
		}
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(data);

		Map<String, Object> map = JsonUtils.jsonToMap(json);

		// Iterator itr = map.entrySet().iterator();
		// Map<String, List<String>> bagParcel = null;

		Iterator<Entry<String, Object>> itr = map.entrySet().iterator();
		Map<String, List<String>> bagParcel = null;
		List<String> unbaggedParcels = null;

		while (itr.hasNext()) {
			Entry<String, Object> itrMap = itr.next();

			// Map.Entry itrMap = (Map.Entry) itr.next();

			if (itrMap.getKey().equals("bagDetails")) {

				Map<String, String> bagDetails = (Map<String, String>) itrMap.getValue();

				Iterator<?> itrParcel = bagParcel.entrySet().iterator();
				Iterator<?> itrDetails = bagDetails.entrySet().iterator();
				List<String> itrUnbagged = unbaggedParcels;

				while (itrParcel.hasNext() || itrDetails.hasNext()) {
					Map.Entry<?, ?> itrMapParcel = (Map.Entry<?, ?>) itrParcel.next();

					Map.Entry<?, ?> itrMapDetails = (Map.Entry<?, ?>) itrDetails.next();
					String numericString = "0123456789";
					StringBuilder sb = new StringBuilder(9);

					for (int i = 0; i < 9; i++) {
						int index = (int) (numericString.length() * Math.random());
						sb.append(numericString.charAt(index));
					}

					String[] bagDetailSplit = itrMapDetails.getValue().toString().split(",");
					int flag = 0;
					for (String s : bagDetailSplit) {
						++flag;
					}

					for (String bagMapValue : (List<String>) itrMapParcel.getValue()) {
						if (flag == 0) {
							if (itrUnbagged.size() > 0) {
								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										"", "");
								NewBagList.add(trackingCS);
							}
							if (itrUnbagged.size() == 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										"", "");
								NewBagList.add(trackingCS);
							}
						} else if (flag == 1) {
							if (itrUnbagged.size() > 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										bagDetailSplit[0], "");
								NewBagList.add(trackingCS);
							}
							if (itrUnbagged.size() == 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										bagDetailSplit[0], "");
								NewBagList.add(trackingCS);
							}
						} else if (bagDetailSplit[1].length() > 0 && bagDetailSplit[0].length() == 0) {

							if (itrUnbagged.size() > 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										"", bagDetailSplit[1]);
								NewBagList.add(trackingCS);
							}
							if (itrUnbagged.size() == 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										"", bagDetailSplit[1]);
								NewBagList.add(trackingCS);
							}

						} else if (bagDetailSplit[0].length() > 0 && bagDetailSplit[1].length() > 0) {

							if (itrUnbagged.size() > 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										bagDetailSplit[0], bagDetailSplit[1]);
								NewBagList.add(trackingCS);
							}
							if (itrUnbagged.size() == 0) {

								TrackingCS trackingCS = saveRmsBagData(bagMapValue, itrUnbagged, user, sb.toString(),
										bagDetailSplit[0], bagDetailSplit[1]);
								NewBagList.add(trackingCS);
							}

						}

					}
				}
			}

			if (itrMap.getKey().equals("bagMap")) {

				bagParcel = (Map<String, List<String>>) itrMap.getValue();
			}
			if (itrMap.getKey().equals("UnbaggedParcels")) {
				unbaggedParcels = (List<String>) itrMap.getValue();
			}

		}
		return NewBagList;

	}

	@Override
	public List<TrackingDetails> fetchDetailsWithParcelId(Parcel parcelTrackingId) {
		log.debug("Fetching details on Track id:::" + parcelTrackingId);
		List<TrackingDetails> parcelList = trackingDetailsRepository
				.findByObjParcelOrderByUpdatedOnDesc(parcelTrackingId);
		if (parcelList.isEmpty()) {
			return null;
		}
		return parcelList;
	}

	@Override
	public Map<Object, List<TrackingDetails>> fetchDetailsWithBagId(String bagId) {
		log.debug("Fetching details on Bag Track id:::" + bagId);
		List<TrackingDetails> parcelList = trackingDetailsRepository.findByBagIdOrderByUpdatedOnDesc(bagId);
		if (parcelList.get(0).getObjParcel() == null) {
			return parcelList.stream().collect(Collectors.groupingBy(TrackingDetails::getBagId));
		} else {
			return parcelList.stream().collect(Collectors.groupingBy(TrackingDetails -> TrackingDetails.getBagId()));

			// return
			// parcelList.stream().collect(Collectors.groupingBy(TrackingDetails->TrackingDetails.getObjParcel()));
		}
	}

	@Override
	public String findRmsName(Long rmsId) {
		RmsTable rmsname = rmsRepository.findRmsTableByIdAndStatus(rmsId, Status.ACTIVE);
		return rmsname.getRmsName();
	}

	@Override
	public List<MasterAddress> findByPostalCode(long postalCode) {
		return masterAddressRepository.findByPostalCodeAndStatus((int) postalCode, Status.ACTIVE);
	}

	@Override
	public RmsTable findRmsByRmsID(Long rmsId) {

		return rmsRepository.findRmsTableByIdAndStatus(rmsId, Status.ACTIVE);
	}

	@Override
	public Map<String, Object> getAlldailyDispatchReportByCurrentDate(Timestamp ts1, Timestamp ts, User loginedUser,
			Long postalCode, Long rmsName, PStatus parcelStatus) {

		Map<String, Object> response = new HashMap<String, Object>();
		List<User> users = new ArrayList<User>();

		/// **** For ADMIN User****///
		if (loginedUser.getRole().getName().equals("ROLE_ADMIN")) {

			if (postalCode == 0) {
				users = userRepository.findByRmsIdAndEnabledAndStatus(rmsName, true, Status.ACTIVE);
			} else {
				log.info("inside admin else part for po user");
				Role role = roleRepository.findOneByName("ROLE_PO_USER");
				users = userRepository.findAllByPostalCodeAndRoleAndEnabledAndStatus(postalCode, role, true,
						Status.ACTIVE);
			}
		}

		else if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
			log.info("inside PO user");
			Role role = roleRepository.findOneByName("ROLE_PO_USER");
			users = userRepository.findAllByPostalCodeAndRoleAndEnabledAndStatus(loginedUser.getPostalCode(), role,
					true, Status.ACTIVE);
		}

		else if (loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
			log.info("inside RMS user");
			users = userRepository.findByRmsIdAndEnabledAndStatus(loginedUser.getRmsId(), true, Status.ACTIVE);
		}
		List<TrackingDetails> trackingList = trackingDetailsRepository
				.findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(parcelStatus, ts, ts1, users);

		for (TrackingDetails trackingDetails : trackingList) {

			if (trackingDetails.getLocationType() == LocationType.RMS) {

				List<RmsTable> rmsList = rmsRepository.findAllByIdAndStatus(trackingDetails.getLocationId(),
						Status.ACTIVE);
				response.put("rms", rmsList);
			}
			if (trackingDetails.getLocationType() == LocationType.POST_OFFICE) {

				List<MasterAddress> ms = masterAddressRepository
						.findByPostalCodeAndStatus(trackingDetails.getLocationId().intValue(), Status.ACTIVE);
				response.put("master", ms);
			}
			response.put("Tracking", trackingList);
		}

		return response;
	}

	@Override
	public List<MasterAddress> fetchPOList() {

		return masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE);
	}

	@Override
	public List<RmsTable> fetchRmsList() {

		return rmsRepository.findAllByStatusOrderByRmsNameAsc(Status.ACTIVE);
	}

	@Override
	public Map<Object, List<TrackingDetails>> datewiseBagsReportForPoUser(Integer postalCode, BagStatus bagStatus,
			Timestamp ts1, Timestamp ts2) {

		List<User> users = userRepository.findByPostalCodeAndStatus(postalCode, Status.ACTIVE);

		List<TrackingDetails> list = trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(users,
				bagStatus, ts1, ts2);

		return list.stream().collect(Collectors.groupingBy(trackingDetails -> trackingDetails.getBagId()));

	}

	@Override
	public String getDispatchStatus(String trackId, String dispatchStatus, User loginedUser, String date,
			String timeValue, String timeValue1) throws java.text.ParseException {
		Parcel parcel = parcelRepository.findParcelByTrackId(trackId);
		List<User> userList = userRepository.findAllByPostalCodeAndRoleAndStatus(loginedUser.getPostalCode(),
				loginedUser.getRole(), Status.ACTIVE);
		TrackingCS trackingCS = trackingCSRepository.findByObjParcelAndUpdatedByIn(parcel, userList);
		TrackingDetails trackingDetails = trackingDetailsRepository
				.findByStatusAndObjParcel(Enum.valueOf(Status.class, "ACTIVE"), parcel);
		TrackingDetails newTrackingDetails = new TrackingDetails();
		MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());

		String inputDate = timeValue;
		String[] split = inputDate.split(":");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date todayDate = sdf.parse(date);
		Calendar finalDate = Calendar.getInstance();
		finalDate.setTime(todayDate);

		int hours = Integer.valueOf(split[0]);
		int minutes = Integer.valueOf(split[1]);
		if (timeValue1.equalsIgnoreCase("PM")) {
			int hours1 = hours + 12;
			finalDate.set(Calendar.HOUR_OF_DAY, hours1);
		} else
			finalDate.set(Calendar.HOUR, hours);
		finalDate.set(Calendar.MINUTE, minutes);

		Date dateNew = finalDate.getTime();

		if (trackingCS == null) {
			return "failure";

		} else {
			if ((trackingCS.getpStatus().equals(PStatus.DELIVERED))
					|| (trackingCS.getpStatus().equals(PStatus.RETURNED))
					|| (trackingCS.getpStatus().equals(PStatus.UNDELIVERED))
					|| (trackingCS.getpStatus().equals(PStatus.OUT_FOR_DELIVERY))
					|| (trackingCS.getpStatus().equals(PStatus.WILL_TRY_ANOTHER_TIME))) { // tracking cs object
				trackingCS.setUpdatedBy(loginedUser);
				trackingCS.setUpdatedOn(dateNew);

				// tracking Details object
				newTrackingDetails.setObjParcel(parcel);
				newTrackingDetails.setBagStatus(BagStatus.UNBAGGED);
				newTrackingDetails.setpStatus(PStatus.RETURNED);
				newTrackingDetails.setStatus(Status.ACTIVE);

				if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
					newTrackingDetails.setLocationType(LocationType.POST_OFFICE);
					newTrackingDetails.setLocationId(loginedUser.getPostalCode());
				}
				newTrackingDetails.setUpdatedBy(loginedUser);
				newTrackingDetails.setUpdatedOn(dateNew);

				if (dispatchStatus.equalsIgnoreCase("Returned")) {

					trackingCS.setpStatus(PStatus.RETURNED);
					// new Bag entry in tracking Details
					newTrackingDetails.setpStatus(PStatus.RETURNED);
					newTrackingDetails
							.setTrackingDesc(PStatus.RETURNED + " at Postal Code " + loginedUser.getPostalCode() + " ("
									+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				} else if (dispatchStatus.equalsIgnoreCase("Delivered")) {
					trackingCS.setpStatus(PStatus.DELIVERED);
					newTrackingDetails
							.setTrackingDesc(PStatus.DELIVERED + " at Postal Code " + loginedUser.getPostalCode() + " ("
									+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
					newTrackingDetails.setpStatus(PStatus.DELIVERED);
				} else if (dispatchStatus.equalsIgnoreCase("Undelivered")) {
					trackingCS.setpStatus(PStatus.UNDELIVERED);
					newTrackingDetails
							.setTrackingDesc(PStatus.UNDELIVERED + " at Postal Code " + loginedUser.getPostalCode()
									+ " (" + postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
					newTrackingDetails.setpStatus(PStatus.UNDELIVERED);

				} else if (dispatchStatus.equalsIgnoreCase("Will Try Another Time")) {
					trackingCS.setpStatus(PStatus.WILL_TRY_ANOTHER_TIME);
					newTrackingDetails.setTrackingDesc(
							PStatus.WILL_TRY_ANOTHER_TIME + " at Postal Code " + loginedUser.getPostalCode() + " ("
									+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
					newTrackingDetails.setpStatus(PStatus.WILL_TRY_ANOTHER_TIME);

				}

				trackingDetails.setStatus(Enum.valueOf(Status.class, "DISABLED"));
				trackingCSRepository.save(trackingCS);
				trackingDetailsRepository.save(trackingDetails);
				trackingDetailsRepository.save(newTrackingDetails);

			} else if ((trackingCS.getpStatus().equals(PStatus.BAGGED)) || (trackingCS.getpStatus().equals(PStatus.IN))
					|| (trackingCS.getpStatus().equals(PStatus.OUT))
					|| (trackingCS.getpStatus().equals(PStatus.REBAGGED))
					|| (trackingCS.getpStatus().equals(PStatus.UNBAGGED))) {
				return "wrong destination";

			}
			return "success";

		}

	}

	@Override
	public List<ParcelVo> findParcelsReportData(String fromdate,String todate,List<Long> services,User user,PStatus status,List<Long> subServices) throws java.text.ParseException {

		log.info("inside find parcels from tracking cs  ");
		java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
		java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());


		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
		java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

		Timestamp fromDate = new Timestamp(sqlfromDate.getTime());
		Timestamp toDate = new Timestamp(sqltoDate.getTime());
		//service


		List<User> users = userRepository.findByPostalCodeAndStatus(user.getPostalCode(), Status.ACTIVE);


		List<Integer> userIdList = new ArrayList<Integer>();
		for (User i : users) {
			userIdList.add(i.getId());
		}


		if (status.equals(PStatus.UNBAGGED)) {

			List<ParcelVo> parcelListVo = new ArrayList<>();
			log.info("inside getting data from parcels table " + status.getName());
			List<Parcel> parcelList = parcelRepository.findAllParcelBypStatusAndCreatedByInAndServiceInAndCreatedDateBetween(status.getName().toUpperCase(), userIdList, services, fromDate, toDate);

			for (Parcel parcel : parcelList) {
				List<Long> servicesList=new ArrayList<>();
				ObjectMapper mapper1 = new ObjectMapper();
				RateCalculation rateMap1=null;
				try {
					rateMap1 = mapper1.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}

				if(rateMap1.getSubServicesRateCalculation() != null)
				{
					rateMap1.getSubServicesRateCalculation().forEach(rateCalculation ->{
						servicesList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}
				//
				if(!subServices.isEmpty()) {
					if (CollectionUtils.containsAny(subServices, servicesList)) {
						ParcelVo parcelVo = parcelReportMis(parcel);
						parcelListVo.add(parcelVo);

					} else {
						log.info("no subservice list available ");
					}
				}else if(servicesList.isEmpty()){
					ParcelVo parcelVo = parcelReportMis(parcel);
					parcelListVo.add(parcelVo);
				}

				}
					return parcelListVo;

			}else {

			List<ParcelVo> parcelListVo = new ArrayList<>();
			log.info("inside getting data from tracking cs table ");

			List<TrackingCS> trackingCS=  trackingCSRepository.findAllBypStatusAndUpdatedByInAndObjParcelServiceInAndUpdatedOnBetween(status, users, services, fromDate, toDate);

			for(TrackingCS parcel : trackingCS){
				List<Long> servicesList=new ArrayList<>();

				ObjectMapper mapper1 = new ObjectMapper();
				RateCalculation rateMap1=null;
				try {
					rateMap1 = mapper1.readValue(parcel.getObjParcel().getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}

				if(rateMap1.getSubServicesRateCalculation() != null)
				{
					rateMap1.getSubServicesRateCalculation().forEach(rateCalculation ->{
						servicesList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}

				if(!subServices.isEmpty()) {
					if (CollectionUtils.containsAny(subServices, servicesList)) {
						ParcelVo parcelVo = parcelReportMisTracking(parcel.getObjParcel());
						parcelListVo.add(parcelVo);

					} else {
						log.info("no subservice list available ");
					}
				}else if(servicesList.isEmpty()){

					ParcelVo parcelVo = parcelReportMisTracking(parcel.getObjParcel());
					parcelListVo.add(parcelVo);
				}

			}
			return parcelListVo;
		}
	}


	@Override
	public ParcelVo findByParcelId(String parcelid) {
		Parcel parcel = parcelRepository.findParcelByTrackId(parcelid);
		if (parcel.getStatus().equalsIgnoreCase("booked")) {
			TrackingCS p = trackingCSRepository.findByObjParcelAndStatus(parcel, Status.ACTIVE);
			String parentService = "";
			String parentServiceCode = "";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();

			ParcelVo parcelVo = new ParcelVo();

			if (p != null) {
				parcelVo.setActWt(String.valueOf(p.getObjParcel().getActWt()));
				parcelVo.setCod(p.getObjParcel().isCod());
				parcelVo.setToPay(p.getObjParcel().isToPay());
				parcelVo.setInvoiceBreakup(p.getObjParcel().getInvoiceBreakup());
				parcelVo.setParcelContent(p.getObjParcel().getParcelContent());
				parcelVo.setParcelDeadWeight(String.valueOf(p.getObjParcel().getParcelDeadWeight()));
				parcelVo.setParcelDeclerationValue(String.valueOf(p.getObjParcel().getParcelDeclerationValue()));
				parcelVo.setpStatus(p.getpStatus().toString());
				parcelVo.setService(p.getObjParcel().getService());
				parcelVo.setReceiverAddress(p.getObjParcel().getReceiverAddress());
				parcelVo.setRecipientMobileNumber(String.format("%011d",p.getObjParcel().getRecipientMobileNumber()));
				parcelVo.setRecipientName(p.getObjParcel().getRecipientName());
				parcelVo.setStatus(p.getStatus().toString());
				parcelVo.setSubServices(p.getObjParcel().getSubServices());
				parcelVo.setTrackId(p.getObjParcel().getTrackId());
				parcelVo.setUpdatedOn(p.getUpdatedOn());

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(p.getObjParcel().getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Long> serviceIdList = new ArrayList<>();

				if (rateMap.getSubServicesRateCalculation() != null) {
					rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
						serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}

				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(serviceIdList);

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				parentServiceCode = services.getServiceCode();

				/*
				 * parentService=p.getObjParcel().getInvoiceBreakup().getName();
				 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
				 * parentServiceCode="GE"; } else
				 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
				 * parentServiceCode="UP"; } else
				 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
				 * parentServiceCode="IC"; }
				 */
				if (!(p.getObjParcel().getSubServices() == null))
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});

				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
				parcelVo.setServiceCode(serviceCode);

			} else {
				parcelVo.setActWt(String.valueOf(parcel.getActWt()));
				parcelVo.setCod(parcel.isCod());
				parcelVo.setToPay(parcel.isToPay());
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setParcelContent(parcel.getParcelContent());
				parcelVo.setParcelDeadWeight(String.valueOf(parcel.getParcelDeadWeight()));
				parcelVo.setParcelDeclerationValue(String.valueOf(parcel.getParcelDeclerationValue()));
				parcelVo.setpStatus(parcel.getpStatus());
				parcelVo.setService(parcel.getService());
				parcelVo.setReceiverAddress(parcel.getReceiverAddress());
				parcelVo.setRecipientMobileNumber(String.format("%011d", parcel.getRecipientMobileNumber()));
				parcelVo.setRecipientName(parcel.getRecipientName());
				parcelVo.setStatus(parcel.getStatus());
				parcelVo.setSubServices(parcel.getSubServices());
				parcelVo.setTrackId(parcel.getTrackId());
				parcelVo.setUpdatedOn(parcel.getCreatedDate());

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Long> serviceIdList = new ArrayList<>();
				if (rateMap.getSubServicesRateCalculation() != null) {
					rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
						serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}
				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(serviceIdList);

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				parentServiceCode = services.getServiceCode();

				/*
				 * parentService=parcel.getInvoiceBreakup().getName();
				 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
				 * parentServiceCode="GE"; } else
				 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
				 * parentServiceCode="UP"; } else
				 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
				 * parentServiceCode="IC"; }
				 */

				if (!(parcel.getSubServices() == null))
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);
				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});
				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
				parcelVo.setServiceCode(serviceCode);
			}
			return parcelVo;

		}
		return null;
	}

	@Override
	public void updateOutForDelivery(List<String> parcelids, User loginedUser) {

		Date date = new Date();

		for (String parcelid : parcelids) {
			Parcel parcel = parcelRepository.findParcelByTrackId(parcelid);

			TrackingCS bag = trackingCSRepository.findByObjParcelAndStatus(parcel, Status.ACTIVE);

			if (bag != null) {
				bag.setpStatus(PStatus.OUT_FOR_DELIVERY);
				bag.setBagStatus(BagStatus.UNBAGGED);
				bag.setUpdatedBy(loginedUser);
				bag.setUpdatedOn(date);
				if (loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
					bag.setLocationType(LocationType.RMS);
					bag.setLocationId(loginedUser.getRmsId());
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(loginedUser.getRmsId(), Status.ACTIVE);
					bag.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " from RMS " + rmsName.getRmsName());
				} else if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
					bag.setLocationType(LocationType.POST_OFFICE);
					bag.setLocationId(loginedUser.getPostalCode());
					MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());
					bag.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " from Postal Code " + loginedUser.getPostalCode()
							+ " (" + postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingCSRepository.save(bag);

				TrackingDetails bag1 = trackingDetailsRepository.findByObjParcelAndStatus(parcel, Status.ACTIVE);

				TrackingDetails parcelNew = new TrackingDetails();
				parcelNew.setStatus(Status.DISABLED);
				parcelNew.setBagDesc(bag1.getBagDesc());
				parcelNew.setBagFillStatus(bag1.getBagFillStatus());
				parcelNew.setBagId(bag1.getBagId());
				parcelNew.setBagStatus(bag1.getBagStatus());
				parcelNew.setBagTitle(bag1.getBagTitle());
				parcelNew.setCreatedBy(bag1.getCreatedBy());
				parcelNew.setCreatedOn(bag1.getCreatedOn());
				parcelNew.setLocationId(bag1.getLocationId());
				parcelNew.setLocationType(bag1.getLocationType());
				parcelNew.setObjParcel(bag1.getObjParcel());
				parcelNew.setpStatus(bag1.getpStatus());
				parcelNew.setTrackingDesc(bag1.getTrackingDesc());
				parcelNew.setUpdatedBy(bag1.getUpdatedBy());
				parcelNew.setUpdatedOn(bag1.getUpdatedOn());
				trackingDetailsRepository.save(parcelNew);

				bag1.setpStatus(PStatus.OUT_FOR_DELIVERY);
				bag1.setBagStatus(BagStatus.UNBAGGED);
				bag1.setUpdatedBy(loginedUser);
				bag1.setUpdatedOn(bag.getUpdatedOn());
				if (loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
					bag1.setLocationType(LocationType.RMS);
					bag1.setLocationId(loginedUser.getRmsId());
					RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(loginedUser.getRmsId(), Status.ACTIVE);
					bag1.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " from RMS " + rmsName.getRmsName());
				} else if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
					bag1.setLocationType(LocationType.POST_OFFICE);
					bag1.setLocationId(loginedUser.getPostalCode());
					MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());
					bag1.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " at Postal Code " + loginedUser.getPostalCode()
							+ " (" + postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				}
				trackingDetailsRepository.save(bag1);

				// updating the bag status for all parcels in that bag to UNBAGGED
				List<TrackingDetails> bags = trackingDetailsRepository.findByBagIdAndStatus(bag.getBagId(),
						Status.ACTIVE);
				for (TrackingDetails bags1 : bags) {
					if(!bags1.getObjParcel().getTrackId().equals(parcelid))
					{
						TrackingDetails newbag = new TrackingDetails();
						newbag.setBagStatus(BagStatus.UNBAGGED);
						newbag.setUpdatedOn(date);
						newbag.setUpdatedBy(loginedUser);
						newbag.setBagDesc(bags1.getBagDesc());
						newbag.setBagFillStatus(bags1.getBagFillStatus());
						newbag.setBagId(bags1.getBagId());
						newbag.setBagTitle(bags1.getBagTitle());
						newbag.setCreatedBy(bags1.getCreatedBy());
						newbag.setCreatedOn(bags1.getCreatedOn());
						newbag.setLocationId(bags1.getLocationId());
						newbag.setLocationType(bags1.getLocationType());
						newbag.setObjParcel(bags1.getObjParcel());
						newbag.setpStatus(PStatus.UNBAGGED);
						newbag.setStatus(Status.ACTIVE);
						if (loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
							RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(loginedUser.getRmsId(),
									Status.ACTIVE);
							newbag.setTrackingDesc(BagStatus.UNBAGGED + " at RMS" + rmsName.getRmsName());
						} else if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
							MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());
							newbag.setTrackingDesc(BagStatus.UNBAGGED + " at Postal Code " + loginedUser.getPostalCode()
									+ " (" + postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
						}
						trackingDetailsRepository.save(newbag);

						bags1.setStatus(Status.DISABLED);
						trackingDetailsRepository.save(bags1);
					}

				}

				List<TrackingCS> bagCS = trackingCSRepository.findByBagIdAndStatus(bag.getBagId(), Status.ACTIVE);
				for (TrackingCS newbag : bagCS) {
					if(!newbag.getObjParcel().getTrackId().equals(parcelid))
					{
						newbag.setBagStatus(BagStatus.UNBAGGED);
						newbag.setpStatus(PStatus.UNBAGGED);
						newbag.setUpdatedOn(date);
						newbag.setUpdatedBy(loginedUser);
						newbag.setStatus(Status.ACTIVE);
						if (loginedUser.getRole().getName().equals("ROLE_RMS_USER")) {
							RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(loginedUser.getRmsId(),
									Status.ACTIVE);
							newbag.setTrackingDesc(BagStatus.UNBAGGED + " at RMS" + rmsName.getRmsName());
						} else if (loginedUser.getRole().getName().equals("ROLE_PO_USER")) {
							MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());
							newbag.setTrackingDesc(BagStatus.UNBAGGED + " at Postal Code " + loginedUser.getPostalCode()
									+ " (" + postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
						}
						trackingCSRepository.save(newbag);
					}
				}
			}
			// if parcel not present in tracking details, then add a new row in trackingcs &
			// trackingdetails (when parcell dispatched without bagging
			else {
				MasterAddress postaldetails = masterAddress.getPostalData((int) loginedUser.getPostalCode());

				TrackingDetails parcelNew = new TrackingDetails();
				parcelNew.setStatus(Status.ACTIVE);
				parcelNew.setCreatedBy(loginedUser);
				parcelNew.setCreatedOn(date);
				parcelNew.setObjParcel(parcel);
				parcelNew.setpStatus(PStatus.OUT_FOR_DELIVERY);
				parcelNew.setUpdatedBy(loginedUser);
				parcelNew.setUpdatedOn(date);
				parcelNew.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " from Postal Code " + loginedUser.getPostalCode() + " ("
						+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				trackingDetailsRepository.save(parcelNew);

				TrackingCS parcelNew1 = new TrackingCS();
				parcelNew1.setStatus(Status.ACTIVE);
				parcelNew1.setCreatedBy(loginedUser);
				parcelNew1.setCreatedOn(date);
				parcelNew1.setObjParcel(parcel);
				parcelNew1.setpStatus(PStatus.OUT_FOR_DELIVERY);
				parcelNew1.setUpdatedBy(loginedUser);
				parcelNew1.setUpdatedOn(date);
				parcelNew1.setTrackingDesc(PStatus.OUT_FOR_DELIVERY + " from Postal Code " + loginedUser.getPostalCode() + " ("
						+ postaldetails.getSubOffice() + ", " + postaldetails.getThana() + ")");
				trackingCSRepository.save(parcelNew1);
			}
			parcel.setpStatus(PStatus.OUT_FOR_DELIVERY.toString());
			parcelRepository.save(parcel);
		}
	}

	@Override

	public void generateParcelDispatchReport(User loginedUser, List<String> trackId) throws JRException {

		String paymentMode = "";

		List<JasperVo> jasperVoList = new ArrayList<>();

		for (String trackingId : trackId) {

			String parentService = "";
			String parentServiceCode = "";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();

			Parcel parcel = parcelRepository.findParcelByTrackId(trackingId);
			List<TrackingCS> trackingList = trackingCSRepository.findBypStatusAndObjParcel(PStatus.OUT_FOR_DELIVERY,
					parcel);

			for (TrackingCS trackingCS : trackingList) {
				JasperVo parcelDispatchVo = new JasperVo();
				parcelDispatchVo.setTrackid(trackingId);
				parcelDispatchVo
						.setRecipientnameandmob(trackingCS.getObjParcel().getRecipientName() +"\n"+String.valueOf(trackingCS.getObjParcel().getRecipientMobileNumber()));

				// service code with parent service codes

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Long> serviceIdList = new ArrayList<>();
				if (rateMap.getSubServicesRateCalculation() != null) {
					rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
						serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}
				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(serviceIdList);

				parentService = parcel.getInvoiceBreakup().getName();

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				parentServiceCode = services.getServiceCode();

				/*
				 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
				 * parentServiceCode="GE"; } else
				 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
				 * parentServiceCode="UP"; } else
				 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
				 * parentServiceCode="IC"; }
				 */

				if (!(parcel.getSubServices() == null))
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);
				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});
				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));

				parcelDispatchVo.setServices(serviceCode);
				parcelDispatchVo
						.setReceiverAddress(trackingCS.getObjParcel().getReceiverAddress().getAddressLine1() + ", "
								+ (trackingCS.getObjParcel().getReceiverAddress().getAddressLine2().equals("") ? ""
										: trackingCS.getObjParcel().getReceiverAddress().getAddressLine2().concat(","))
								+ (trackingCS.getObjParcel().getReceiverAddress().getLandmark().equals("") ? ""
										: trackingCS.getObjParcel().getReceiverAddress().getLandmark().concat(", "))
								+ trackingCS.getObjParcel().getReceiverAddress().getSubOffice() + ", "
								+ trackingCS.getObjParcel().getReceiverAddress().getThana() + ", "
								+ trackingCS.getObjParcel().getReceiverAddress().getDistrict() + ", "
								+ trackingCS.getObjParcel().getReceiverAddress().getDivision() + ", "
								+ trackingCS.getObjParcel().getReceiverAddress().getZone() + "-"
								+ trackingCS.getObjParcel().getReceiverAddress().getPostalCode());

				if (trackingCS.getObjParcel().isToPay()) {
					paymentMode = "To Pay";
					parcelDispatchVo.setToCollect(
							String.valueOf(trackingCS.getObjParcel().getInvoiceBreakup().getPayableAmnt() + " Rs"));
				} else if (trackingCS.getObjParcel().isCod()) {
					paymentMode = "COD";
					parcelDispatchVo
							.setToCollect(String.valueOf(trackingCS.getObjParcel().getParcelDeclerationValue() + " Rs"));

				} else {

					paymentMode = "Prepaid";
					parcelDispatchVo.setToCollect("-");
				}
				parcelDispatchVo.setPaymentMode(paymentMode);
				jasperVoList.add(parcelDispatchVo);
			}
		//	try {

				// Compile the Jasper report from .jrxml to .japser
				JasperReport jasperReport = JasperCompileManager
						.compileReport(exportfilepath + "parcel-dispatch-report.jrxml");

				// Get your data source
				JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(
						jasperVoList);

				// Add parameters
				Map<String, Object> parameters = new HashMap<>();
				MasterAddress masterAddress = masterAddressRepository
						.findMasterAddressByPostalCodeAndStatus((int) loginedUser.getPostalCode(), Status.ACTIVE);
				parameters.put("location", masterAddress.getSubOffice() + "," + masterAddress.getThana() + ","
						+ masterAddress.getDistrict() + "-" + masterAddress.getPostalCode());
				parameters.put("dispatchReport",jrBeanCollectionDataSource);

				// Fill the report
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
						new JREmptyDataSource());

				// Export the report to a PDF file
				JasperExportManager.exportReportToPdfFile(jasperPrint, exportfilepath + "parcel-dispatch-report.pdf");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

		}
	}

	@Override
	public List<ParcelVo> findParcelsReportDataAdmin(String fromdate, String todate, List<Long> services, User user,
			PStatus status, List<Long> subServices, Long postalCode) throws java.text.ParseException {

		log.info("inside find parcels from tracking cs  ");
		java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
		java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
		java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

		Timestamp fromDate = new Timestamp(sqlfromDate.getTime());
		Timestamp toDate = new Timestamp(sqltoDate.getTime());
		// service

		List<User> users = userRepository.findByPostalCodeAndStatus(postalCode, Status.ACTIVE);

		List<Integer> userIdList = new ArrayList<Integer>();
		for (User i : users) {
			userIdList.add(i.getId());
		}

		if (status.equals(PStatus.UNBAGGED)) {

			List<ParcelVo> parcelListVo = new ArrayList<>();
			log.info("inside getting data from parcels table " + status.getName());
			List<Parcel> parcelList = parcelRepository
					.findAllParcelBypStatusAndCreatedByInAndServiceInAndCreatedDateBetween(
							status.getName().toUpperCase(), userIdList, services, fromDate, toDate);

			for (Parcel parcel : parcelList) {
				List<Long> servicesList = new ArrayList<>();
				ObjectMapper mapper1 = new ObjectMapper();
				RateCalculation rateMap1 = null;
				try {
					rateMap1 = mapper1.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}

				if (rateMap1.getSubServicesRateCalculation() != null) {
					rateMap1.getSubServicesRateCalculation().forEach(rateCalculation -> {
						servicesList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}
				//servicesList.isEmpty()
				if(!subServices.isEmpty()) {
					if (CollectionUtils.containsAny(subServices, servicesList)) {
						ParcelVo parcelVo = parcelReportMis(parcel);
						parcelListVo.add(parcelVo);

					} else {
						log.info("no subservice list available ");
					}
				}else if(servicesList.isEmpty()){

						ParcelVo parcelVo = parcelReportMis(parcel);
						parcelListVo.add(parcelVo);
				}
			}
			return parcelListVo;

		} else {

			List<ParcelVo> parcelListVo = new ArrayList<>();
			log.info("inside getting data from tracking cs table ");

			List<TrackingCS> trackingCS = trackingCSRepository
					.findAllBypStatusAndUpdatedByInAndObjParcelServiceInAndUpdatedOnBetween(status, users, services,
							fromDate, toDate);

			for (TrackingCS parcel : trackingCS) {
				List<Long> servicesList = new ArrayList<>();

				ObjectMapper mapper1 = new ObjectMapper();
				RateCalculation rateMap1 = null;
				try {
					rateMap1 = mapper1.readValue(parcel.getObjParcel().getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}

				if (rateMap1.getSubServicesRateCalculation() != null) {
					rateMap1.getSubServicesRateCalculation().forEach(rateCalculation -> {
						servicesList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}

				if(!subServices.isEmpty()) {
					if (CollectionUtils.containsAny(subServices, servicesList)) {
						ParcelVo parcelVo = parcelReportMisTracking(parcel.getObjParcel());
						parcelListVo.add(parcelVo);

					} else {
						log.info("no subservice list available ");
					}
				}else if(servicesList.isEmpty()){

					ParcelVo parcelVo = parcelReportMisTracking(parcel.getObjParcel());
					parcelListVo.add(parcelVo);
				}
			}

			return parcelListVo;
		}
	}

	private ParcelVo parcelReportMis(Parcel parcel) {
		String parentService = "";
		String parentServiceCode = "";
		List<Services> subServiceList = new ArrayList<>();
		List<String> subServiceCode = new ArrayList<>();

		ParcelVo parcelVo = new ParcelVo();
		parcelVo.setTrackId(parcel.getTrackId());
		parcelVo.setToPay(parcel.isToPay());
		parcelVo.setCod(parcel.isCod());
		parcelVo.setParcelDeclerationValue(parcel.getParcelDeclerationValue() + "");
		parcelVo.setParcelContent(parcel.getParcelContent());
		parcelVo.setStatus(parcel.getStatus());
		parcelVo.setpStatus(parcel.getpStatus());
		parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
		parcelVo.setActWt(parcel.getActWt() + "");
		parentServiceCode = parcel.getInvoiceBreakup().getName();
		//
		ObjectMapper mapper = new ObjectMapper();
		RateCalculation rateMap = null;

		try {
			rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		List<Long> serviceIdList = new ArrayList<>();
		if (rateMap.getSubServicesRateCalculation() != null) {
			rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
				serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
			});
		}
		parcel.setRateCalculation(rateMap);
		parcel.setSubServices(serviceIdList);
		parentService = parcel.getInvoiceBreakup().getName();
		Long parentServiceId = parcel.getService();
		Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
		parentServiceCode = services.getServiceCode();
		//

		//
		if (!(parcel.getSubServices() == null))
			subServiceList = postalServiceRepository
					.findByIdInAndStatus(parcel.getSubServices(), Status.ACTIVE);
		subServiceList.forEach(subService -> {
			subServiceCode.add(subService.getServiceCode());
		});
		String serviceCode = String.join(",", subServiceCode);
		serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
		parcelVo.setServiceCode(serviceCode);

		return parcelVo;


	}

	public ParcelVo parcelReportMisTracking(Parcel parcel){

		String parentService = "";
		String parentServiceCode = "";
		List<Services> subServiceList = new ArrayList<>();
		List<String> subServiceCode = new ArrayList<>();

		ParcelVo parcelVo = new ParcelVo();
		parcelVo.setTrackId(parcel.getTrackId());
		parcelVo.setToPay(parcel.isToPay());
		parcelVo.setCod(parcel.isCod());
		parcelVo.setParcelDeclerationValue(parcel.getParcelDeclerationValue() + "");
		parcelVo.setParcelContent(parcel.getParcelContent());
		parcelVo.setStatus(parcel.getStatus());
		parcelVo.setpStatus(parcel.getpStatus().toString());
		parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
		parcelVo.setActWt(parcel.getActWt() + "");

		// service

		ObjectMapper mapper = new ObjectMapper();
		RateCalculation rateMap = null;
		try {
			rateMap = mapper.readValue(parcel.getRateCalculationJSON(),
					RateCalculation.class);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		List<Long> serviceIdList = new ArrayList<>();
		if (rateMap.getSubServicesRateCalculation() != null) {
			rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
				serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
			});
		}
		parcel.setRateCalculation(rateMap);
		parcel.setSubServices(serviceIdList);
		/*parentService = parcel.getInvoiceBreakup().getName();
		if (parentService.equalsIgnoreCase("Guaranteed Express Post")) {
			parentServiceCode = "GE";
		} else if (parentService.equalsIgnoreCase("Unregistered Packets")) {
			parentServiceCode = "UP";
		} else if (parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
			parentServiceCode = "IC";
		}*/
		parentService = parcel.getInvoiceBreakup().getName();
		Long parentServiceId = parcel.getService();
		Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
		parentServiceCode = services.getServiceCode();
		//

		//
		if (!(parcel.getSubServices() == null))
			subServiceList = postalServiceRepository
					.findByIdInAndStatus(parcel.getSubServices(), Status.ACTIVE);
		subServiceList.forEach(subService -> {
			subServiceCode.add(subService.getServiceCode());
		});
		String serviceCode = String.join(",", subServiceCode);
		serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
		parcelVo.setServiceCode(serviceCode);

		return parcelVo;
	}

}
