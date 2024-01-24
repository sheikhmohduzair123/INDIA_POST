package com.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constants.BagStatus;
import com.constants.ClientStatus;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Client;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.Services;
import com.domain.SyncTable;
import com.domain.Thana;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.domain.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.ReportRepository;
import com.repositories.RmsRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.SyncTableRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.ReportService;
import com.vo.ParcelVo;
import com.vo.RateCalculation;
import com.vo.RmsTableVo;
import com.vo.SyncTableVo;
import com.vo.TrackingVo;

@Service
@SuppressWarnings("unchecked")
public class ReportServiceImpl implements ReportService {

	protected Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private TrackingDetailsRepository trackingDetailsRepository;

	@Autowired
	private TrackingCSRepository trackingCSRepository;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private RmsRepository rmsRepository;

	@Autowired
	private SUserRepository userRepository;

	@Autowired
	private ParcelRepository parcelRepository;

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	MasterAddressRepository masterAddressRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	SyncTableRepository syncTableRepository;

	@Override
	public List<Parcel> getAllsummary() throws ParseException {

		DateTimeZone timeZone = DateTimeZone.forID("Asia/Calcutta");
		DateTime now = DateTime.now(timeZone);
		DateTime todayStart = now.withTimeAtStartOfDay();

		java.util.Date date = todayStart.toDate();

		Timestamp ts = new Timestamp(date.getTime());
		Timestamp ts1 = new Timestamp(new Date().getTime());

		return reportRepository.findsummaryDaily(ts, ts1);
	}

	@Override
	public List<Parcel> getAllsummaryByDivision(String division) throws ParseException {
		DateTimeZone timeZone = DateTimeZone.forID("Asia/Calcutta");
		DateTime now = DateTime.now(timeZone);
		DateTime todayStart = now.withTimeAtStartOfDay();

		java.util.Date date = todayStart.toDate();
		Timestamp ts = new Timestamp(date.getTime());
		Timestamp ts1 = new Timestamp(new Date().getTime());

		return reportRepository.findsummaryDailyByDivision(ts, ts1, division);

	}

	@Override
	public List<Parcel> getAllsummaryByDivisionAndDate(String fdate, String division) throws ParseException {

		DateTimeZone timeZone = DateTimeZone.forID("Asia/Calcutta");
		DateTime now = DateTime.parse(fdate);
		DateTime fromstartdate = now.withTimeAtStartOfDay();
		java.util.Date date = fromstartdate.toDate();

		Timestamp ts = new Timestamp(date.getTime());
		Timestamp ts1 = new Timestamp(new Date().getTime());

		return reportRepository.findsummaryDailyByDivisionAndDate(ts, ts1, division);
	}

	@Override
	public List<Parcel> getAllsummaryBetweenDate(String fdate) throws ParseException {

		DateTimeZone timeZone = DateTimeZone.forID("Asia/Calcutta");
		DateTime now = DateTime.parse(fdate);
		DateTime fromstartdate = now.withTimeAtStartOfDay();
		java.util.Date date = fromstartdate.toDate();

		Timestamp ts = new Timestamp(date.getTime());
		Timestamp ts1 = new Timestamp(new Date().getTime());

		return reportRepository.findsummaryDaily(ts, ts1);
	}

	/*
	 * private Collector<Parcel, ?, Map<String, Map<String, List<Parcel>>>>
	 * groupByStatusAndSenderAddressDivision() { return
	 * groupingBy(Parcel::getStatus, groupingBy(p ->
	 * p.getSenderAddress().getDivision())); }
	 */

	@Override
	public Map<String, List<ParcelVo>> getsummary(String fdate, String zone, String district, String division,
			String thana, String subOffice, String viewList, String aggregateBy) throws ParseException {

		// find by parcel status
		List<String> parcelStatus = new ArrayList<>();
		parcelStatus.add("booked");
		parcelStatus.add("void");

		if (viewList.equalsIgnoreCase("booked parcels")) {
			parcelStatus.remove("void");
		} else if (viewList.equalsIgnoreCase("void parcels")) {
			parcelStatus.remove("booked");
		}

		// finding fdate & nextdate to find data b/w 2 dates
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fdate);
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusDays(1);
		Date date_end = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		List<Parcel> p = null;
		if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All")
				&& zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All") && !fdate.isBlank()) {
			// no condition - fetch all
			p = reportRepository.findByCreatedDateBetweenAndStatusIn(date, date_end, parcelStatus);
		} else if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All")
				&& !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All") && !fdate.isBlank()) {
			// find for a zone
			List<MasterAddress> zonePostalCode = masterAddressRepository.findPostalCodeByZoneAndStatus(zone,
					Enum.valueOf(Status.class, "ACTIVE"));
			int zonePostCode = 0;
			List<Integer> zonePostalCodeList = new ArrayList<>();
			for (MasterAddress zoneCode : zonePostalCode) {
				zonePostCode = zoneCode.getPostalCode();
				zonePostalCodeList.add(zonePostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(zonePostalCodeList,
					date, date_end, parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All")
				&& thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			// find for a zone & division
			List<MasterAddress> divisionPostalCode = masterAddressRepository
					.findPostalCodeByZoneAndDivisionAndStatus(zone, division, Enum.valueOf(Status.class, "ACTIVE"));

			int divisionPostCode = 0;
			List<Integer> divisionPostalCodeList = new ArrayList<>();
			for (MasterAddress divisionCode : divisionPostalCode) {

				divisionPostCode = divisionCode.getPostalCode();
				divisionPostalCodeList.add(divisionPostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(
					divisionPostalCodeList, date, date_end, parcelStatus);

		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
				&& thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {
			// find for a zone & division & district
			List<MasterAddress> districtPostalCode = masterAddressRepository
					.findPostalCodeByZoneAndDivisionAndDistrictAndStatus(zone, division, district,
							Enum.valueOf(Status.class, "ACTIVE"));
			int districtPostCode = 0;
			List<Integer> districtPostalCodeList = new ArrayList<>();
			for (MasterAddress districtCode : districtPostalCode) {

				districtPostCode = districtCode.getPostalCode();
				districtPostalCodeList.add(districtPostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(
					districtPostalCodeList, date, date_end, parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
				&& !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			// find for a zone & division & district & thana
			List<MasterAddress> thanaPostalCode = masterAddressRepository
					.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndStatus(zone, division, district, thana,
							Enum.valueOf(Status.class, "ACTIVE"));
			int thanaPostCode = 0;
			List<Integer> thanaPostalCodeList = new ArrayList<>();
			for (MasterAddress thanaCode : thanaPostalCode) {

				thanaPostCode = thanaCode.getPostalCode();
				thanaPostalCodeList.add(thanaPostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(thanaPostalCodeList,
					date, date_end, parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
				&& !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && !subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			// find for a zone & division & district & thana & subOffice
			List<MasterAddress> subOfficePostalCode = masterAddressRepository
					.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndStatus(zone, division, district,
							thana, subOffice, Enum.valueOf(Status.class, "ACTIVE"));
			int subOfficePostCode = 0;
			List<Integer> subOfficePostalCodeList = new ArrayList<>();
			for (MasterAddress subOfficeCode : subOfficePostalCode) {

				subOfficePostCode = subOfficeCode.getPostalCode();
				subOfficePostalCodeList.add(subOfficePostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(
					subOfficePostalCodeList, date, date_end, parcelStatus);
		}

		List<ParcelVo> parcelVoList = new ArrayList<ParcelVo>();
		for (Parcel parcel1 : p) {

			String parentService = "";
			String parentServiceCode = "";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();

			ParcelVo parcelVo = new ParcelVo();
			parcelVo.setTrackId(parcel1.getTrackId());
			parcelVo.setLabelCode(parcel1.getLabelCode());
			parcelVo.setSenderName(parcel1.getSenderName());
			parcelVo.setSenderMobileNumber(String.format("%010d", parcel1.getSenderMobileNumber()));
			parcelVo.setSenderAddress(parcel1.getSenderAddress());
			parcelVo.setRecipientName(parcel1.getRecipientName());
			parcelVo.setRecipientMobileNumber(String.format("%010d", parcel1.getRecipientMobileNumber()));
			parcelVo.setReceiverAddress(parcel1.getReceiverAddress());
			parcelVo.setParcelValue(parcel1.getParcelDeclerationValue());
			parcelVo.setParcelContent(parcel1.getParcelContent());
			parcelVo.setCreatedDate(parcel1.getCreatedDate());
			parcelVo.setInvoiceBreakup(parcel1.getInvoiceBreakup());
			parcelVo.setStatus(parcel1.getStatus());
			parcelVo.setActWeight(parcel1.getActWt());
			parcelVo.setParcelVolumeWghtReprt(parcel1.getParcelVolumeWeight());
			parcelVo.setParcelDeadWghtReprt(parcel1.getParcelDeadWeight());
			parcelVo.setParcelimage(parcel1.getParcelimage());
			parcelVo.setScanedBarcode(parcel1.getScanedBarcode());
			
			ObjectMapper mapper = new ObjectMapper();
			RateCalculation rateMap = null;
			try {
				rateMap = mapper.readValue(parcel1.getRateCalculationJSON(), RateCalculation.class);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				log.error("Error occurred while generating daily summary report",e);
			}
			List<Long> serviceIdList = new ArrayList<>();

			if (rateMap.getSubServicesRateCalculation() != null) {
				rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
					serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				});
			}

			parcel1.setRateCalculation(rateMap);
			parcel1.setSubServices(serviceIdList);

			parentService = parcel1.getInvoiceBreakup().getName();
			Long parentServiceId = parcel1.getService();
			Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
			if (services == null) {
				services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
			}
			parentServiceCode = services.getServiceCode();

			if (!(parcel1.getSubServices() == null)) {
				subServiceList = postalServiceRepository.findByIdInAndStatus(parcel1.getSubServices(),
						Status.ACTIVE);

				// if some services are disabled, find from disabled service
				if (!(subServiceList.size() == parcel1.getSubServices().size())) {
					List<Long> subserviceId = new ArrayList<Long>();
					for (Services s : subServiceList) {
						subserviceId.add(s.getId());
					}

					List<Long> parcelSubServices = parcel1.getSubServices();
					parcelSubServices.removeAll(subserviceId);

					for (Long serviceid : parcelSubServices) {
						Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
						subServiceList.add(service);
					}
				}
			}

			subServiceList.forEach(subService -> {
				subServiceCode.add(subService.getServiceCode());
			});

			String serviceCode = String.join(",", subServiceCode);
			serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
			parcelVo.setServiceCode(serviceCode);

			parcelVoList.add(parcelVo);
		}

		if (aggregateBy.equals("Select Group By") || aggregateBy.equals("Select Aggregate By")) {
			return parcelVoList.stream().collect(Collectors.groupingBy(ParcelVo::getTrackId));
		} else if (aggregateBy.equals("Zone")) {
			return parcelVoList.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getZone()));
		} else if (aggregateBy.equals("Division")) {
			return parcelVoList.stream()
					.collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getDivision()));
		} else if (aggregateBy.equals("District")) {
			return parcelVoList.stream()
					.collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getDistrict()));
		} else if (aggregateBy.equals("Thana")) {
			return parcelVoList.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getThana()));
		} else if (aggregateBy.equals("Sub Office")) {
			return parcelVoList.stream()
					.collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getSubOffice()));
		}

		/*
		 * if ((StringUtils.isNotBlank(division) && division.equals("All")) &&
		 * StringUtils.isNotBlank(fdate)) { System.out.println("all"); return
		 * reportService.getAllsummaryBetweenDate(fdate); } else if
		 * (StringUtils.isBlank(fdate)) { System.out.println("all without date"); return
		 * reportService.getAllsummary(); } System.out.println("date"); return
		 * reportService.getAllsummaryByDivisionAndDate(fdate, division);
		 */
		return null;
	}

	/*
	 * private Collector<Parcel, ?, Map<String, Map<String, List<Parcel>>>>
	 * groupByTrackIdAndDivision() { return groupingBy(Parcel::getTrackId,
	 * groupingBy(p -> p.getSenderAddress().getDivision())); }
	 */

	// RMS LOGINED USer Bag Report
	@Override
	public Map<Object, List<TrackingVo>> findTodayBagListByRMs(User loginedUser, String fromdate, String todate,
			BagStatus bagStatus) throws Exception {

		// User userData =
		// sUserRepository.findUserByUsername(loginedUser.getUsername());
		// Long rmsId = userData.getRmsId();

		java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
		java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

		/*
		 * List<BagStatus> pstatus1 = new ArrayList<>(); pstatus1.add(BagStatus.IN);
		 * pstatus1.add(BagStatus.OUT);
		 */

		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
		java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

		Timestamp ts = new Timestamp(sqlfromDate.getTime());
		Timestamp ts1 = new Timestamp(sqltoDate.getTime());

		// List<User> users =
		// sUserRepository.findByRmsIdAndStatus(loginedUser.getRmsId(), Status.ACTIVE);
		List<User> users = sUserRepository.findByRmsId(loginedUser.getRmsId());

		List<TrackingDetails> bagList = trackingDetailsRepository
				.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(bagStatus, ts, ts1, users);
		log.info("bag DATA Table appear");
		List<TrackingVo> trackingVoList = new ArrayList<TrackingVo>();
		for (TrackingDetails trackingDetails : bagList) {
			TrackingVo trackingVo = new TrackingVo();
			trackingVo.setUpdatedOn(trackingDetails.getUpdatedOn());
			trackingVo.setBagId(trackingDetails.getBagId());
			trackingVo.setBagTitle(trackingDetails.getBagTitle());
			trackingVo.setBagDesc(trackingDetails.getBagDesc());
			if (trackingDetails.getObjParcel() != null) {
				trackingVo.setActWt(trackingDetails.getObjParcel().getActWt());
			}
			trackingVoList.add(trackingVo);
		}
		return trackingVoList.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
		// return
		// bagList.stream().collect(Collectors.groupingBy(TrackingDetails::getBagId));
	}

	@Override
	public List<RmsTable> fetchRmsList() {
		return rmsRepository.findAllByStatus(Status.ACTIVE);
	}

	@Override
	public List<RmsTableVo> getRMSNameByRMSType(String rmsType) {
		List<RmsTable> rmsNameList = rmsRepository.findRmsTableByRmsTypeAndStatus(rmsType, Status.ACTIVE);

		List<RmsTableVo> rmsTableVoList = new ArrayList<RmsTableVo>();
		for (RmsTable rmsTable : rmsNameList) {
			RmsTableVo rmsTableVo = new RmsTableVo();
			rmsTableVo.setId(rmsTable.getId());
			rmsTableVo.setRmsType(rmsTable.getRmsType());
			rmsTableVo.setRmsName(rmsTable.getRmsName());
			rmsTableVoList.add(rmsTableVo);
		}
		return rmsTableVoList;
	}

	@Override
	public Map<Object, List<TrackingVo>> fetchBagInventoryWithRmsAndBagStatus(Long rmsId, BagStatus bagStatus,
			Timestamp ts1, Timestamp ts2) {

		List<User> users = userRepository.findByRmsId(rmsId);
		// List<User> users = userRepository.findByRmsIdAndStatus(rmsId, Status.ACTIVE);

		List<TrackingDetails> trackingCSList = trackingDetailsRepository
				.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(ts1, ts2, bagStatus, users);

		List<TrackingVo> trackingVoList = new ArrayList<TrackingVo>();
		for (TrackingDetails trackingDetails : trackingCSList) {
			TrackingVo trackingVo = new TrackingVo();
			trackingVo.setUpdatedOn(trackingDetails.getUpdatedOn());
			trackingVo.setBagId(trackingDetails.getBagId());
			trackingVo.setBagTitle(trackingDetails.getBagTitle());
			trackingVo.setBagDesc(trackingDetails.getBagDesc());
			if (trackingDetails.getObjParcel() != null) {
				trackingVo.setTrackId(trackingDetails.getObjParcel().getTrackId());
				trackingVo.setActWt(trackingDetails.getObjParcel().getActWt());
			}
			trackingVoList.add(trackingVo);
		}
		return trackingVoList.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
	}

	@Override
	public Map<String, List<TrackingVo>> getPoBagReport(String fdate, String tdate, BagStatus reportType,
			User loginedUser) throws ParseException {

		Date fromdate = new SimpleDateFormat("yyyy-MM-dd").parse(fdate);
		Date todate = new SimpleDateFormat("yyyy-MM-dd").parse(tdate);
		LocalDate today = todate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date todate1 = Date.from(zdt.toInstant());
		// List<User> users =
		// sUserRepository.findAllByPostalCodeAndRoleAndStatus(loginedUser.getPostalCode(),
		// loginedUser.getRole(), Status.ACTIVE);
		List<User> users = sUserRepository.findAllByPostalCodeAndRole(loginedUser.getPostalCode(),
				loginedUser.getRole());
		List<TrackingDetails> baglist = trackingDetailsRepository
				.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(users, reportType, fromdate, todate1);
		List<TrackingVo> trackingVoList = new ArrayList<TrackingVo>();
		for (TrackingDetails trackingDetails : baglist) {
			TrackingVo trackingVo = new TrackingVo();
			trackingVo.setId(trackingDetails.getId());
			trackingVo.setBagId(trackingDetails.getBagId());
			trackingVo.setpStatus(trackingDetails.getpStatus());
			trackingVo.setBagTitle(trackingDetails.getBagTitle());
			trackingVo.setBagDesc(trackingDetails.getBagDesc());
			trackingVo.setBagStatus(trackingDetails.getBagStatus());
			trackingVo.setUpdatedOn(trackingDetails.getUpdatedOn());
			if (trackingDetails.getObjParcel() != null) {
				trackingVo.setTrackId(trackingDetails.getObjParcel().getTrackId());
				trackingVo.setActWt(trackingDetails.getObjParcel().getActWt());
				trackingVo.setPayableAmnt(trackingDetails.getObjParcel().getInvoiceBreakup().getPayableAmnt());
			}
			trackingVoList.add(trackingVo);
		}
		return trackingVoList.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
	}

	@Override
	public Map<String, List<TrackingVo>> getBagList(String location, String locationType) {
		List<User> users = null;
		if (locationType.equalsIgnoreCase("postoffice")) {
			long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_PO_USER");
			// users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role,
			// true, num, Status.ACTIVE);
			users = sUserRepository.findByRoleAndPostalCode(role, num);

		} else if (locationType.equalsIgnoreCase("RMS")) {
			Long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_RMS_USER");
			// users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true,
			// num, Status.ACTIVE);
			users = sUserRepository.findByRoleAndRmsId(role, num);
		}

		List<BagStatus> status = new ArrayList<BagStatus>();
		status.add(BagStatus.IN);
		status.add(BagStatus.CREATED);
		List<TrackingCS> baglist = trackingCSRepository.findByUpdatedByInAndBagStatusIn(users, status);

		List<TrackingVo> trackingVoList = new ArrayList<TrackingVo>();
		for (TrackingCS trackingCSS : baglist) {
			TrackingVo trackingVo = new TrackingVo();
			trackingVo.setId(trackingCSS.getId());
			trackingVo.setBagId(trackingCSS.getBagId());
			trackingVo.setpStatus(trackingCSS.getpStatus());
			trackingVo.setBagTitle(trackingCSS.getBagTitle());
			trackingVo.setBagDesc(trackingCSS.getBagDesc());
			trackingVo.setUpdatedOn(trackingCSS.getUpdatedOn());
			trackingVo.setBagStatus(trackingCSS.getBagStatus());
			if (trackingCSS.getObjParcel() != null) {
				trackingVo.setTrackId(trackingCSS.getObjParcel().getTrackId());
				trackingVo.setActWt(trackingCSS.getObjParcel().getActWt());
			}
			trackingVoList.add(trackingVo);
		}
		for (TrackingVo trackingVo : trackingVoList) {
			if (!(trackingVo.getpStatus() == null)) {
				if (trackingVo.getpStatus().equals(PStatus.UNBAGGED))
					trackingVoList.remove(trackingVo);
			}
		}

		return trackingVoList.stream().collect(Collectors.groupingBy(TrackingVo::getBagId));
	}

	@Override
	public Map<Parcel, List<TrackingVo>> getParcelList(String location, String locationType) {

		List<User> users = null;
		if (locationType.equalsIgnoreCase("postoffice")) {
			long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_PO_USER");
			// users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role,
			// true, num, Status.ACTIVE);
			users = sUserRepository.findByRoleAndPostalCode(role, num);
		} else if (locationType.equalsIgnoreCase("RMS")) {
			Long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_RMS_USER");
			// users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true,
			// num, Status.ACTIVE);
			users = sUserRepository.findByRoleAndRmsId(role, num);
		}

		List<PStatus> pstatus = new ArrayList<PStatus>();
		pstatus.add(PStatus.UNBAGGED);
		pstatus.add(PStatus.IN);
		pstatus.add(PStatus.BAGGED);
		pstatus.add(PStatus.REBAGGED);
		pstatus.add(PStatus.RETURNED);
		pstatus.add(PStatus.UNDELIVERED);
		pstatus.add(PStatus.WILL_TRY_ANOTHER_TIME);

		// fetching parcels which are bagged
		List<TrackingCS> baglist = trackingCSRepository.findBypStatusInAndUpdatedByInAndObjParcelNotNull(pstatus,
				users);

		List<TrackingVo> trackingVoList = new ArrayList<TrackingVo>();
		for (TrackingCS trackingCSS : baglist) {
			TrackingVo trackingVo = new TrackingVo();
			trackingVo.setId(trackingCSS.getId());
			trackingVo.setBagId(trackingCSS.getBagId());
			trackingVo.setpStatus(trackingCSS.getpStatus());
			trackingVo.setBagTitle(trackingCSS.getBagTitle());
			trackingVo.setBagDesc(trackingCSS.getBagDesc());
			trackingVo.setUpdatedOn(trackingCSS.getUpdatedOn());
			trackingVo.setObjParcelVo(trackingCSS.getObjParcel());
			trackingVoList.add(trackingVo);
		}

		// fetch service code for each parcel
		for (TrackingVo trackingVo : trackingVoList) {

			if (trackingVo.getObjParcelVo() != null) {
				String parentService = "";
				String parentServiceCode = "";
				List<Services> subServiceList = new ArrayList<>();
				List<String> subServiceCode = new ArrayList<>();

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(trackingVo.getObjParcelVo().getRateCalculationJSON(),
							RateCalculation.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					log.error("Error occurred in creating rate calculation mapper while generating parcel list:: ",e);
				}

				List<Long> serviceIdList = new ArrayList<>();

				if (rateMap.getSubServicesRateCalculation() != null) {
					rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
						serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
					});
				}

				// parcel.setRateCalculation(rateMap);
				// parcel.setSubServices(serviceIdList);

				/*
				 * parentService= trackingCS.getObjParcel().getInvoiceBreakup().getName();
				 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
				 * parentServiceCode="GE"; } else
				 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
				 * parentServiceCode="UP"; } else
				 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
				 * parentServiceCode="IC"; }
				 */

				parentService = trackingVo.getObjParcelVo().getInvoiceBreakup().getName();
				Long parentServiceId = trackingVo.getObjParcelVo().getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				if (services == null) {
					services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
				}

				parentServiceCode = services.getServiceCode();

				if (!(serviceIdList == null)) {
					subServiceList = postalServiceRepository.findByIdInAndStatus(serviceIdList, Status.ACTIVE);
					// if some services are disabled, find from disabled service
					if (!(subServiceList.size() == serviceIdList.size())) {
						List<Long> subserviceId = new ArrayList<Long>();
						for (Services s : subServiceList) {
							subserviceId.add(s.getId());
						}
						List<Long> parcelSubServices = serviceIdList;
						parcelSubServices.removeAll(subserviceId);
						for (Long serviceid : parcelSubServices) {
							Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
							subServiceList.add(service);
						}
					}
				}

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});
				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
				trackingVo.setBagDesc(serviceCode);

			}
		}

		// fetch parcels which are not bagged, either have been unbagged or not even
		// bagged once
		long num1 = 0L;
		Role role;
		if (locationType.equalsIgnoreCase("postoffice")) {
			num1 = Long.parseLong(location);
			role = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");
			// users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role,
			// true, num1, Status.ACTIVE);
			users = sUserRepository.findByRoleAndPostalCode(role, num1);
		} else if (locationType.equalsIgnoreCase("RMS")) {
			Long id = Long.parseLong(location);
			role = roleRepository.findOneByName("ROLE_RMS_USER");
			// users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true, id,
			// Status.ACTIVE);
			users = sUserRepository.findByRoleAndRmsId(role, id);
		}
		List<Integer> userIdList = new ArrayList<Integer>();
		for (User i : users) {
			userIdList.add(i.getId());
		}
		List<Parcel> p = parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(PStatus.UNBAGGED.toString(),
				"booked", userIdList);
		List<ParcelVo> parcelVoList = new ArrayList<ParcelVo>();
		for (Parcel parcel : p) {
			ParcelVo parcelVo = new ParcelVo();
			parcelVo.setTrackId(parcel.getTrackId());
			parcelVo.setCreatedDate(parcel.getCreatedDate());
			parcelVo.setActWeight(parcel.getActWt());
			parcelVoList.add(parcelVo);
		}
		for (Parcel parcel : p) {
			TrackingVo t = new TrackingVo();
			t.setObjParcelVo(parcel);

			String parentService = "";
			String parentServiceCode = "";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();

			ObjectMapper mapper = new ObjectMapper();
			RateCalculation rateMap = null;
			try {
				rateMap = mapper.readValue(t.getObjParcelVo().getRateCalculationJSON(), RateCalculation.class);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				log.error("Error occurred in creating rate calculation mapper while generating parcel list:: ",e);
			}

			List<Long> serviceIdList = new ArrayList<>();

			if (rateMap.getSubServicesRateCalculation() != null) {
				rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
					serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				});
			}

			// parcel.setRateCalculation(rateMap);
			// parcel.setSubServices(serviceIdList);

			/*
			 * parentService= t.getObjParcel().getInvoiceBreakup().getName();
			 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
			 * parentServiceCode="GE"; } else
			 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
			 * parentServiceCode="UP"; } else
			 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
			 * parentServiceCode="IC"; }
			 */

			parentService = t.getObjParcelVo().getInvoiceBreakup().getName();
			Long parentServiceId = t.getObjParcelVo().getService();
			Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
			if (services == null) {
				services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
			}
			parentServiceCode = services.getServiceCode();

			if (!(serviceIdList == null)) {
				subServiceList = postalServiceRepository.findByIdInAndStatus(serviceIdList, Status.ACTIVE);
				// if some services are disabled, find from disabled service
				if (!(subServiceList.size() == serviceIdList.size())) {
					List<Long> subserviceId = new ArrayList<Long>();
					for (Services s : subServiceList) {
						subserviceId.add(s.getId());
					}
					List<Long> parcelSubServices = serviceIdList;
					parcelSubServices.removeAll(subserviceId);
					for (Long serviceid : parcelSubServices) {
						Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
						subServiceList.add(service);
					}
				}
			}

			subServiceList.forEach(subService -> {
				subServiceCode.add(subService.getServiceCode());
			});
			String serviceCode = String.join(",", subServiceCode);
			serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
			t.setBagDesc(serviceCode);
			trackingVoList.add(t);
		}
		return trackingVoList.stream().collect(Collectors.groupingBy(TrackingVo::getObjParcelVo));
	}

	@Override
	public List<SyncTableVo> getclientLastSyncReport(String zone, String district, String division, String thana,
			String subOffice) throws ParseException {

			List<SyncTableVo> p = new ArrayList<SyncTableVo>();
			
			if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All")
					&& zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")) {
				
				
				List<Client> clientData = clientRepository.findAllByClientStatusAndStatus("approved", Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
					List<SyncTable> result = new ArrayList<SyncTable>();
					
					
					//to getClientId and find data of syncTable
				for(Client id : clientData) {
						SyncTable list = new SyncTable();
						list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
						if(list!=null) {
							result.add(list);
						}
					}
					
					
					for(SyncTable data : result) {
						
						for(Client ct : clientData) {
							if(ct.getClientId().equals(data.getClientId())){
								SyncTableVo syncVoObj = new SyncTableVo();	
						syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
						syncVoObj.setClientName(ct.getClientName());
						syncVoObj.setClientId(ct.getClientId());
						syncVoObj.setDistrict(ct.getDistrict());
						syncVoObj.setDivision(ct.getDivision());
						syncVoObj.setThana(ct.getThana());
						syncVoObj.setZone(ct.getZone());
						syncVoObj.setSubOffice(ct.getSubOffice());
						syncVoObj.setPostalCode(ct.getPostalCode());
						p.add(syncVoObj);
						}
					}
				}
					return p;	
			}
				
				
			} else if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All")
					&& !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")) {
				
				// find for a zone
				List<Client> clientData = clientRepository.findByZoneAndClientStatusAndStatus(zone, "approved", Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
				List<SyncTable> result = new ArrayList<SyncTable>();
				
				
				//to getClientId and find data of syncTable
				for(Client id : clientData) {
					SyncTable list = new SyncTable();
					list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
					if(list!=null) {
						result.add(list);
					}
				}
				
				
				for(SyncTable data : result) {
					
					for(Client ct : clientData) {
						if(ct.getClientId().equals(data.getClientId())){
							SyncTableVo syncVoObj = new SyncTableVo();	
					syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
					syncVoObj.setClientName(ct.getClientName());
					syncVoObj.setClientId(ct.getClientId());
					syncVoObj.setDistrict(ct.getDistrict());
					syncVoObj.setDivision(ct.getDivision());
					syncVoObj.setThana(ct.getThana());
					syncVoObj.setZone(ct.getZone());
					syncVoObj.setSubOffice(ct.getSubOffice());
					syncVoObj.setPostalCode(ct.getPostalCode());
					p.add(syncVoObj);
					}
				}
					
			}
				return p;
			}
				
				
			} else if (!division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All")
					&& thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
					) {

				List<Client> clientData = clientRepository.findByZoneAndDivisionAndClientStatusAndStatus(zone, division, "approved", Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
				List<SyncTable> result = new ArrayList<SyncTable>();
				
				
				//to getClientId and find data of syncTable
				for(Client id : clientData) {
					SyncTable list = new SyncTable();
					list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
					if(list!=null) {
						result.add(list);
					}
				}
				
				
				for(SyncTable data : result) {
					
					for(Client ct : clientData) {
						if(ct.getClientId().equals(data.getClientId())){
							SyncTableVo syncVoObj = new SyncTableVo();	
					syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
					syncVoObj.setClientName(ct.getClientName());
					syncVoObj.setClientId(ct.getClientId());
					syncVoObj.setDistrict(ct.getDistrict());
					syncVoObj.setDivision(ct.getDivision());
					syncVoObj.setThana(ct.getThana());
					syncVoObj.setZone(ct.getZone());
					syncVoObj.setSubOffice(ct.getSubOffice());
					syncVoObj.setPostalCode(ct.getPostalCode());
					p.add(syncVoObj);
					}
				}
					
				}
				return p;
				}
			} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
					&& thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
					) {
				// find for a zone & division & district
				List<Client> clientData = clientRepository.findByZoneAndDivisionAndDistrictAndClientStatusAndStatus(zone, division, district, "approved",  Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
				List<SyncTable> result = new ArrayList<SyncTable>();
				
				
				//to getClientId and find data of syncTable
				for(Client id : clientData) {
					SyncTable list = new SyncTable();
					list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
					if(list!=null) {
						result.add(list);
					}
				}
				
				
				for(SyncTable data : result) {
					
					for(Client ct : clientData) {
						if(ct.getClientId().equals(data.getClientId())){
							SyncTableVo syncVoObj = new SyncTableVo();	
					syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
					syncVoObj.setClientName(ct.getClientName());
					syncVoObj.setClientId(ct.getClientId());
					syncVoObj.setDistrict(ct.getDistrict());
					syncVoObj.setDivision(ct.getDivision());
					syncVoObj.setThana(ct.getThana());
					syncVoObj.setZone(ct.getZone());
					syncVoObj.setSubOffice(ct.getSubOffice());
					syncVoObj.setPostalCode(ct.getPostalCode());
					p.add(syncVoObj);
					}
				}
					
			}
				return p;
				}
			} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
					&& !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
					) {

				// find for a zone & division & district & thana
				List<Client> clientData = clientRepository.findByZoneAndDivisionAndDistrictAndThanaAndClientStatusAndStatus(zone, division, district, thana, "approved", Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
				List<SyncTable> result = new ArrayList<SyncTable>();
				
				
				//to getClientId and find data of syncTable
				for(Client id : clientData) {
					SyncTable list = new SyncTable();
					list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
					if(list!=null) {
						result.add(list);
					}
				}
				
				
				for(SyncTable data : result) {
					
					for(Client ct : clientData) {
						if(ct.getClientId().equals(data.getClientId())){
							SyncTableVo syncVoObj = new SyncTableVo();	
					syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
					syncVoObj.setClientName(ct.getClientName());
					syncVoObj.setClientId(ct.getClientId());
					syncVoObj.setDistrict(ct.getDistrict());
					syncVoObj.setDivision(ct.getDivision());
					syncVoObj.setThana(ct.getThana());
					syncVoObj.setZone(ct.getZone());
					syncVoObj.setSubOffice(ct.getSubOffice());
					syncVoObj.setPostalCode(ct.getPostalCode());
					p.add(syncVoObj);
					}
				}
					
			}
				return p;
				}
			} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All")
					&& !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && !subOffice.equalsIgnoreCase("All")
					) {

				List<Client> clientData = clientRepository.findByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndClientStatusAndStatus(zone, division, district, thana, subOffice, "approved", Status.ACTIVE);
				
				if(clientData.isEmpty()) {
					return p;
				}
				else {
				List<SyncTable> result = new ArrayList<SyncTable>();
				
				
				//to getClientId and find data of syncTable
				for(Client id : clientData) {
					SyncTable list = new SyncTable();
					list = syncTableRepository.findTopByClientIdOrderBySyncUpdatedTimestampDesc(id.getClientId());
					if(list!=null) {
						result.add(list);
					}
				}
				
				
				for(SyncTable data : result) {
					
					for(Client ct : clientData) {
						if(ct.getClientId().equals(data.getClientId())){
							SyncTableVo syncVoObj = new SyncTableVo();	
					syncVoObj.setSyncUpdatedTimestamp(data.getSyncUpdatedTimestamp());
					syncVoObj.setClientName(ct.getClientName());
					syncVoObj.setClientId(ct.getClientId());
					syncVoObj.setDistrict(ct.getDistrict());
					syncVoObj.setDivision(ct.getDivision());
					syncVoObj.setThana(ct.getThana());
					syncVoObj.setZone(ct.getZone());
					syncVoObj.setSubOffice(ct.getSubOffice());
					syncVoObj.setPostalCode(ct.getPostalCode());
					p.add(syncVoObj);
					}
				}
					
			}
				return p;
				}
			}
			return p;
	
	}

	
}
