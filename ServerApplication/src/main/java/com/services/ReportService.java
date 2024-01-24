package com.services;

import com.constants.BagStatus;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Thana;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.domain.Zone;
import com.vo.ParcelVo;
import com.vo.RmsTableVo;
import com.vo.SyncTableVo;
import com.vo.TrackingVo;

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

    Map<String, List<ParcelVo>> getsummary(String fdate, String zone, String district, String division, String thana, String subOffice, String viewList, String aggregateBy) throws ParseException;

  //RMS Reports
	public Map<Object, List<TrackingVo>>  findTodayBagListByRMs(User loginedUser,  String fromdate,String todate, BagStatus bagStatus) throws Exception;

    List<RmsTableVo> getRMSNameByRMSType(String rmsType);
	  List<RmsTable> fetchRmsList();
	  Map<Object, List<TrackingVo>> fetchBagInventoryWithRmsAndBagStatus(Long rmsId, BagStatus bagStatus, Timestamp ts1, Timestamp ts2);

	Map<String, List<TrackingVo>> getPoBagReport(String fdate, String tdate, BagStatus reportType, User loginedUser) throws ParseException;

	Map<String, List<TrackingVo>> getBagList(String location, String locationType);

	Map<Parcel, List<TrackingVo>> getParcelList(String location, String locationType);
	
	List<SyncTableVo> getclientLastSyncReport(String zone, String district, String division, String thana, String subOffice) throws ParseException;
}
