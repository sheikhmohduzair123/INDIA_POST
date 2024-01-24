package com.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constants.BagStatus;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.ReportRepository;
import com.repositories.RmsRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.ReportService;
import com.vo.RateCalculation;

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


    @Override
    public List<Parcel> getAllsummary() throws ParseException {

        DateTimeZone timeZone = DateTimeZone.forID( "Asia/Calcutta" );
        DateTime now = DateTime.now( timeZone );
        DateTime todayStart = now.withTimeAtStartOfDay();

        java.util.Date date = todayStart.toDate();


        Timestamp ts = new Timestamp(date.getTime());
        Timestamp ts1 = new Timestamp(new Date().getTime());

        return reportRepository.findsummaryDaily(ts,ts1);
    }

    @Override
    public List<Parcel> getAllsummaryByDivision(String division) throws ParseException {
        DateTimeZone timeZone = DateTimeZone.forID( "Asia/Calcutta" );
        DateTime now = DateTime.now( timeZone );
        DateTime todayStart = now.withTimeAtStartOfDay();

        java.util.Date date = todayStart.toDate();
        Timestamp ts = new Timestamp(date.getTime());
        Timestamp ts1 = new Timestamp(new Date().getTime());

        return reportRepository.findsummaryDailyByDivision(ts,ts1,division);

    }

    @Override
    public List<Parcel> getAllsummaryByDivisionAndDate(String fdate, String division) throws ParseException {

        DateTimeZone timeZone = DateTimeZone.forID( "Asia/Calcutta" );
        DateTime now = DateTime.parse(fdate);
        DateTime fromstartdate = now.withTimeAtStartOfDay();
        java.util.Date date = fromstartdate.toDate();

        Timestamp ts = new Timestamp(date.getTime());
        Timestamp ts1 = new Timestamp(new Date().getTime());

        return reportRepository.findsummaryDailyByDivisionAndDate(ts,ts1,division);
    }

    @Override
    public List<Parcel> getAllsummaryBetweenDate(String fdate) throws ParseException {

        DateTimeZone timeZone = DateTimeZone.forID( "Asia/Calcutta" );
        DateTime now = DateTime.parse(fdate);
        DateTime fromstartdate = now.withTimeAtStartOfDay();
        java.util.Date date = fromstartdate.toDate();

        Timestamp ts = new Timestamp(date.getTime());
        Timestamp ts1 = new Timestamp(new Date().getTime());

        return reportRepository.findsummaryDaily(ts,ts1);
    }

	/*
	 * private Collector<Parcel, ?, Map<String, Map<String, List<Parcel>>>>
	 * groupByStatusAndSenderAddressDivision() { return
	 * groupingBy(Parcel::getStatus, groupingBy(p ->
	 * p.getSenderAddress().getDivision())); }
	 */

   	@Override
	public Map<String, List<Parcel>> getsummary(String fdate, String zone, String district, String division, String thana,
			String subOffice, String viewList, String aggregateBy) throws ParseException {


		//find by parcel status
		List<String> parcelStatus = new ArrayList<>();
		parcelStatus.add("booked");
		parcelStatus.add("void");

		if (viewList.equalsIgnoreCase("booked parcels")) {
			parcelStatus.remove("void");
		} else if (viewList.equalsIgnoreCase("void parcels")) {
			parcelStatus.remove("booked");
		}

		// finding fdate & nextdate to find data b/w 2 dates
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(fdate);
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(1);
        Date date_end = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        List<Parcel> p = null;
		if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All") && zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {
			//no condition - fetch all
			 p = reportRepository.findByCreatedDateBetweenAndStatusIn(date,date_end,parcelStatus);
		}else if (division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {
			//find for a zone
			List<MasterAddress> zonePostalCode = masterAddressRepository.findPostalCodeByZoneAndStatus(zone,Enum.valueOf(Status.class,"ACTIVE"));
			int zonePostCode=0;
			 List<Integer> zonePostalCodeList = new ArrayList<>();
			 for (MasterAddress zoneCode : zonePostalCode) {

				zonePostCode =zoneCode.getPostalCode();
				zonePostalCodeList.add(zonePostCode);
			}
            p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(zonePostalCodeList,date,date_end,parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			//find for a zone & division
	   		List<MasterAddress> divisionPostalCode = masterAddressRepository.findPostalCodeByZoneAndDivisionAndStatus(zone,division, Enum.valueOf(Status.class,"ACTIVE"));
	   		int divisionPostCode=0;
			 List<Integer> divisionPostalCodeList = new ArrayList<>();
			 for (MasterAddress divisionCode : divisionPostalCode) {

				 divisionPostCode =divisionCode.getPostalCode();
				 divisionPostalCodeList.add(divisionPostCode);
			}
			p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(divisionPostalCodeList,date,date_end,parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All") && thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			//find for a zone & division & district
	   		List<MasterAddress> districtPostalCode  = masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndStatus(zone,division,district,Enum.valueOf(Status.class,"ACTIVE"));
	   		int districtPostCode=0;
			 List<Integer> districtPostalCodeList = new ArrayList<>();
			 for (MasterAddress districtCode : districtPostalCode) {

				 districtPostCode =districtCode.getPostalCode();
				 districtPostalCodeList.add(districtPostCode);
			}
	   		p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(districtPostalCodeList,date,date_end,parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All") && !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			//find for a zone & division & district & thana
	   		List<MasterAddress> thanaPostalCode     = masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndStatus(zone,division,district,thana, Enum.valueOf(Status.class,"ACTIVE"));
	   		int thanaPostCode=0;
			 List<Integer> thanaPostalCodeList = new ArrayList<>();
			 for (MasterAddress thanaCode : thanaPostalCode) {

				 thanaPostCode =thanaCode.getPostalCode();
				 thanaPostalCodeList.add(thanaPostCode);
			}
	   		p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(thanaPostalCodeList,date,date_end,parcelStatus);
		} else if (!division.equalsIgnoreCase("All") && !district.equalsIgnoreCase("All") && !thana.equalsIgnoreCase("All") && !zone.equalsIgnoreCase("All") && !subOffice.equalsIgnoreCase("All")
				&& !fdate.isBlank()) {

			//find for a zone & division & district & thana & subOffice
	   		List<MasterAddress> subOfficePostalCode = masterAddressRepository.findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndStatus(zone,division,district,thana,subOffice, Enum.valueOf(Status.class,"ACTIVE"));
	   		int subOfficePostCode=0;
			 List<Integer> subOfficePostalCodeList = new ArrayList<>();
			 for (MasterAddress subOfficeCode : subOfficePostalCode) {

				 subOfficePostCode =subOfficeCode.getPostalCode();
				 subOfficePostalCodeList.add(subOfficePostCode);
			}
	   		p = reportRepository.findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(subOfficePostalCodeList,date,date_end,parcelStatus);
		}

		if(aggregateBy.equals("Select Group By") || aggregateBy.equals("Select Aggregate By")) {
			return p.stream().collect(Collectors.groupingBy(Parcel::getTrackId));
		}
		else if(aggregateBy.equals("Zone")) {
			return p.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getZone()));
		}
		else if(aggregateBy.equals("Division")) {
			return p.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getDivision()));
		}
		else if(aggregateBy.equals("District")) {
			return p.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getDistrict()));
		}
		else if(aggregateBy.equals("Thana")) {
			return p.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getThana()));
		}
		else if(aggregateBy.equals("Sub Office")) {
			return p.stream().collect(Collectors.groupingBy(parcel -> parcel.getSenderAddress().getSubOffice()));
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


   	//RMS LOGINED USer Bag Report
   	@Override
	public Map<Object, List<TrackingDetails>> findTodayBagListByRMs(User loginedUser, String fromdate,String todate, BagStatus bagStatus) throws Exception {

	//	User userData =  sUserRepository.findUserByUsername(loginedUser.getUsername());
	//	 Long rmsId = userData.getRmsId();


   			 java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			 java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

			 /*List<BagStatus> pstatus1 = new ArrayList<>();
			 pstatus1.add(BagStatus.IN);
			 pstatus1.add(BagStatus.OUT);*/

			 LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			 LocalDateTime startDateTime = today.atTime(23, 59, 59);
			 ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
			 Date tdate = Date.from(zdt.toInstant());

			 java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
			 java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

			 Timestamp ts = new Timestamp(sqlfromDate.getTime());
			 Timestamp ts1 = new Timestamp(sqltoDate.getTime());

			 List<User> users = sUserRepository.findByRmsIdAndStatus(loginedUser.getRmsId(), Status.ACTIVE);
			 List<TrackingDetails> bagList =   trackingDetailsRepository.findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(bagStatus, ts, ts1,users);
			 log.info("bag DATA Table appear");


			// for(TrackingDetails c: bagList)
			// {
				 //if(rmsId.equals(c.getLocationId())) {
					 return bagList.stream().collect(Collectors.groupingBy(TrackingDetails::getBagId));
				 //}
			// }


       // return null;
	}

   	@Override
	public List<RmsTable> fetchRmsList() {
		return rmsRepository.findAllByStatus(Status.ACTIVE);
	}

	@Override
	public List<RmsTable> getRMSNameByRMSType(String rmsType) {
		List<RmsTable> rmsNameList = rmsRepository.findRmsTableByRmsTypeAndStatus(rmsType, Status.ACTIVE);
		return rmsNameList;
	}

	@Override
	public Map<Object, List<TrackingDetails>> fetchBagInventoryWithRmsAndBagStatus(Long rmsId, BagStatus bagStatus, Timestamp ts1, Timestamp ts2) {
		List<User> users = userRepository.findByRmsIdAndStatus(rmsId, Status.ACTIVE);
		List<TrackingDetails> trackingCSList = trackingDetailsRepository.findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(ts1, ts2, bagStatus, users);

		for(TrackingDetails tdCS: trackingCSList){
			return trackingCSList.stream().collect(Collectors.groupingBy(TrackingDetails::getBagId));
		}
		return null;
	}

	@Override
	public Map<String, List<TrackingDetails>> getPoBagReport(String fdate, String tdate, BagStatus reportType, User loginedUser) throws ParseException {
		Date fromdate=new SimpleDateFormat("yyyy-MM-dd").parse(fdate);
		Date todate=new SimpleDateFormat("yyyy-MM-dd").parse(tdate);
		 LocalDate today = todate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 LocalDateTime startDateTime = today.atTime(23, 59, 59);
		 ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		 Date todate1 = Date.from(zdt.toInstant());

		List<User> users = sUserRepository.findAllByPostalCodeAndRoleAndStatus(loginedUser.getPostalCode(), loginedUser.getRole(), Status.ACTIVE);
		List<TrackingDetails> baglist = trackingDetailsRepository.findByUpdatedByInAndBagStatusAndUpdatedOnBetween(users,reportType, fromdate, todate1);
		return baglist.stream().collect(Collectors.groupingBy(TrackingDetails::getBagId));
	}

	@Override
	public Map<String, List<TrackingCS>> getBagList(String location, String locationType, User loginedUser) throws ParseException {
		List<User> users = null;
		if(locationType.equalsIgnoreCase("postoffice"))
		{
			long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_PO_USER");
			users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role, true, num, Status.ACTIVE);
		}
		else if(locationType.equalsIgnoreCase("RMS"))
		{
			Long num = Long.parseLong(location);
			Role role = roleRepository.findOneByName("ROLE_RMS_USER");
			users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true, num, Status.ACTIVE);
		}

			List<BagStatus> status = new ArrayList<BagStatus>();
			status.add(BagStatus.IN);
			status.add(BagStatus.CREATED);

			List<TrackingCS> baglist = trackingCSRepository.findByUpdatedByInAndBagStatusIn(users,status);

			for (TrackingCS trackingCS : baglist) {
				if(!(trackingCS.getpStatus() == null) )
				{
					if(trackingCS.getpStatus().equals(PStatus.UNBAGGED))
						baglist.remove(trackingCS);
				}

			}
			return baglist.stream().collect(Collectors.groupingBy(TrackingCS::getBagId));
	}

	@Override
	public Map<Parcel, List<TrackingCS>> getParcelList(String location, String locationType, User loginedUser) {

			List<User> users = null;
			if(locationType.equalsIgnoreCase("postoffice"))
			{
				long num = Long.parseLong(location);
				Role role = roleRepository.findOneByName("ROLE_PO_USER");
				users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role, true, num, Status.ACTIVE);
			}
			else if(locationType.equalsIgnoreCase("RMS"))
			{
				Long num = Long.parseLong(location);
				Role role = roleRepository.findOneByName("ROLE_RMS_USER");
				users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true, num, Status.ACTIVE);
			}

			List<PStatus> pstatus = new ArrayList<PStatus>();
			pstatus.add(PStatus.UNBAGGED);
			pstatus.add(PStatus.IN);
			pstatus.add(PStatus.BAGGED);
			pstatus.add(PStatus.REBAGGED);
			pstatus.add(PStatus.RETURNED);
			pstatus.add(PStatus.UNDELIVERED);
			pstatus.add(PStatus.WILL_TRY_ANOTHER_TIME);


			List<TrackingCS> baglist = trackingCSRepository.findBypStatusInAndUpdatedByInAndObjParcelNotNull(pstatus,users);

			for (TrackingCS trackingCS : baglist) {

				if(trackingCS.getObjParcel() != null)
				{
					String parentService="";
					String parentServiceCode="";
					List<Services> subServiceList = new ArrayList<>();
					List<String> subServiceCode = new ArrayList<>();

					ObjectMapper mapper = new ObjectMapper();
					RateCalculation rateMap=null;
					try {
						rateMap = mapper.readValue(trackingCS.getObjParcel().getRateCalculationJSON(), RateCalculation.class);
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

			//	parcel.setRateCalculation(rateMap);
			//	parcel.setSubServices(serviceIdList);


				/*
				 * parentService= trackingCS.getObjParcel().getInvoiceBreakup().getName();
				 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
				 * parentServiceCode="GE"; } else
				 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
				 * parentServiceCode="UP"; } else
				 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
				 * parentServiceCode="IC"; }
				 */

				parentService=trackingCS.getObjParcel().getInvoiceBreakup().getName();
				Long parentServiceId=trackingCS.getObjParcel().getService();
			    Services services=postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
			    parentServiceCode=services.getServiceCode();

				if(!(serviceIdList == null))
					subServiceList = postalServiceRepository.findByIdInAndStatus(serviceIdList, Status.ACTIVE);

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});
				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode+(!(serviceCode.isBlank())?("/"+serviceCode):(""));
				trackingCS.setBagDesc(serviceCode);

				}
			}

		long num1 = 0L;
		Role role;
		if(locationType.equalsIgnoreCase("postoffice"))
		{
			num1 = Long.parseLong(location);
			role = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");
			users = sUserRepository.findByRoleAndEnabledAndPostalCodeAndStatus(role, true, num1, Status.ACTIVE);
		}
		else if(locationType.equalsIgnoreCase("RMS"))
		{
			Long id =Long.parseLong(location);
			role = roleRepository.findOneByName("ROLE_RMS_USER");
			users = sUserRepository.findByRoleAndEnabledAndRmsIdAndStatus(role, true, id, Status.ACTIVE);
		}

		List<Integer> userIdList = new ArrayList<Integer>();
		for(User i:users) {
			userIdList.add(i.getId());
		}
		List<Parcel> p = parcelRepository.findAllParcelBypStatusAndStatusAndCreatedByIn(PStatus.UNBAGGED.toString(),"booked",userIdList);
		for (Parcel parcel : p) {
			TrackingCS t = new TrackingCS();
			t.setObjParcel(parcel);

			String parentService="";
			String parentServiceCode="";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();

			ObjectMapper mapper = new ObjectMapper();
			RateCalculation rateMap=null;
			try {
				rateMap = mapper.readValue(t.getObjParcel().getRateCalculationJSON(), RateCalculation.class);
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

	//	parcel.setRateCalculation(rateMap);
	//	parcel.setSubServices(serviceIdList);


			/*
			 * parentService= t.getObjParcel().getInvoiceBreakup().getName();
			 * if(parentService.equalsIgnoreCase("Guaranteed Express Post")) {
			 * parentServiceCode="GE"; } else
			 * if(parentService.equalsIgnoreCase("Unregistered Packets")) {
			 * parentServiceCode="UP"; } else
			 * if(parentService.equalsIgnoreCase("Invitation Cards/Greeting Cards")) {
			 * parentServiceCode="IC"; }
			 */

			parentService=t.getObjParcel().getInvoiceBreakup().getName();
			Long parentServiceId=t.getObjParcel().getService();
		    Services services=postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
		    parentServiceCode=services.getServiceCode();


		if(!(serviceIdList == null))
			subServiceList = postalServiceRepository.findByIdInAndStatus(serviceIdList, Status.ACTIVE);

		subServiceList.forEach(subService -> {
			subServiceCode.add(subService.getServiceCode());
		});
		String serviceCode = String.join(",", subServiceCode);
		serviceCode = parentServiceCode+(!(serviceCode.isBlank())?("/"+serviceCode):(""));
		t.setBagDesc(serviceCode);
		baglist.add(t);
		}
		return baglist.stream().collect(Collectors.groupingBy(TrackingCS::getObjParcel));
	}

}
