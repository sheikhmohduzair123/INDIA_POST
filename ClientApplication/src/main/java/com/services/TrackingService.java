package com.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.constants.BagStatus;
import com.constants.PStatus;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.vo.ParcelVo;

import net.sf.jasperreports.engine.JRException;

import org.json.simple.parser.ParseException;

public interface TrackingService {

	List<TrackingCS>  saveTrackingDetails(Map<String, String> bagMap, User user) throws ParseException;
	List<TrackingCS> getAllBags();
	void getAllParcel(String bagId) throws JRException;
	void getBagLabel(String bagId,User loginedUser) throws JRException;

	List<TrackingCS> findByBagStatus();

	List<MasterAddress> getPostOfficeName();

	List<TrackingCS> findByBagId(String bagId, String reportFor, User loginedUser);

	List<RmsTable> getRMSdata();

	List<String> generateEmptyBags(int numberofEmptyBags, User user);

	String deleteEmptyBags(String bagId);

	void forwardBags(List<String> bagIds, String destination, String destinationType, User user);

	void receiveBags(List<String> bagIds, User user);


	Map<String, List<TrackingCS>> findRmsByBagStatus(Long locationId);

	List<TrackingCS>  saveRmsBagDetails(Map<String, List<String>> bagMap,User user) throws ParseException;

    TrackingCS saveRmsBagData(String trackDetail,List<String> unBaggedList,User user,String bagId,String bagTitle, String bagDesc);

	List<TrackingDetails> fetchDetailsWithParcelId(Parcel parcelTrackingId);
	Map<Object, List<TrackingDetails>> fetchDetailsWithBagId(String bagId);
	List<Parcel> getAllParcels(long postalCode);
	//List<Parcel> getAllParcels(Long postalCode);
	String findRmsName(Long rmsId);

	List<MasterAddress> findByPostalCode(long postalCode);
	RmsTable findRmsByRmsID(Long rmsId);
	Map<String, Object> getAlldailyDispatchReportByCurrentDate(Timestamp ts1, Timestamp ts, User loginedUser,
			Long postalCode, Long rmsName, PStatus parcelStatus);
	List<MasterAddress> fetchPOList();
	List<RmsTable> fetchRmsList();

	Map<Object, List<TrackingDetails>> datewiseBagsReportForPoUser(Integer postalCode, BagStatus bagStatus,
			Timestamp ts1, Timestamp ts2);



	List<ParcelVo> findParcelsReportData(String fromdate ,String todate ,List<Long> services,User user,PStatus status,List<Long> subservices) throws java.text.ParseException;

	void generateParcelDispatchReport(User loginedUser, List<String> trackId) throws JRException;

	ParcelVo findByParcelId(String parcelid);
	void updateOutForDelivery(List<String> parcelids, User loginedUser);

	String getDispatchStatus(String trackId, String dispatchStatus, User loginedUser, String date, String timeValue,	String timeValue1) throws java.text.ParseException;

	List<ParcelVo> findParcelsReportDataAdmin(String fromdate ,String todate ,List<Long> services,User user,PStatus status,List<Long> subservices ,Long postalCode) throws java.text.ParseException;

}
