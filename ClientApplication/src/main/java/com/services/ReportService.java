package com.services;

import com.constants.BagStatus;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public interface ReportService {

    List<Parcel> getAllsummary() throws ParseException;

    List<Parcel> getAllsummaryByDivision(String division) throws ParseException;

    List<Parcel> getAllsummaryByDivisionAndDate(String fdate, String division) throws ParseException;

    List<Parcel> getAllsummaryBetweenDate(String fdate) throws ParseException;

    Map<String, List<Parcel>> getsummary(String fdate, String zone, String district, String division, String thana, String subOffice, String viewList, String aggregateBy) throws ParseException;

  //RMS Reports
	public Map<Object, List<TrackingDetails>>  findTodayBagListByRMs(User loginedUser,  String fromdate,String todate, BagStatus bagStatus) throws Exception;

    List<RmsTable> getRMSNameByRMSType(String rmsType);
	  List<RmsTable> fetchRmsList();
	  Map<Object, List<TrackingDetails>> fetchBagInventoryWithRmsAndBagStatus(Long rmsId, BagStatus bagStatus, Timestamp ts1, Timestamp ts2);

	Map<String, List<TrackingDetails>> getPoBagReport(String fdate, String tdate, BagStatus reportType, User loginedUser) throws ParseException;

	Map<String, List<TrackingCS>> getBagList(String location, String locationType, User loginedUser) throws ParseException;

	Map<Parcel, List<TrackingCS>> getParcelList(String location, String locationType, User loginedUser);
}
