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
import com.vo.RmsTableVo;
import com.vo.TrackingVo;

import net.sf.jasperreports.engine.JRException;

import org.json.simple.parser.ParseException;

public interface TrackingService {

	List<TrackingVo>  saveTrackingDetails(Map<String, String> bagMap, User user) throws ParseException;
	List<TrackingCS> getAllBags();
	void getAllParcel(String bagId) throws JRException;
	void getBagLabel(String bagId,User loginedUser) throws JRException;

	List<TrackingCS> findByBagStatus();

	List<MasterAddress> getPostOfficeName();

	List<TrackingVo> findByBagId(String bagId, String reportFor, User loginedUser);

	List<RmsTableVo> getRMSdata();

	List<String> generateEmptyBags(int numberofEmptyBags, User user);

	String deleteEmptyBags(String bagId);

	List<TrackingCS> forwardBags(List<String> bagIds, String destination, String destinationType, User user);

	List<TrackingCS> receiveBags(List<String> bagIds, User user);


	Map<String, List<TrackingVo>> findRmsByBagStatus(Long locationId);

	List<TrackingVo>  saveRmsBagDetails(Map<String, List<String>> bagMap,User user) throws ParseException;

    TrackingCS saveRmsBagData(String trackDetail,List<String> unBaggedList,User user,String bagId,String bagTitle, String bagDesc);

	List<TrackingVo> fetchDetailsWithParcelId(Parcel parcelTrackingId);
	Map<Object, List<TrackingVo>> fetchDetailsWithBagId(String bagId);
	List<ParcelVo> getAllParcels(long postalCode);
	//List<Parcel> getAllParcels(Long postalCode);
	String findRmsName(Long rmsId);

	List<MasterAddress> findByPostalCode(long postalCode);
	RmsTable findRmsByRmsID(Long rmsId);
	Map<String, Object> getAlldailyDispatchReportByCurrentDate(Timestamp ts, Timestamp ts1, User loginedUser,
			Long postalCode, Long rmsName, PStatus parcelStatus);
	List<MasterAddress> fetchPOList();
	List<RmsTable> fetchRmsList();

	Map<Object, List<TrackingVo>> datewiseBagsReportForPoUser(Integer postalCode, BagStatus bagStatus,
			Timestamp ts1, Timestamp ts2);



	List<ParcelVo> findParcelsReportData(String fromdate ,String todate ,List<Long> services,User user,PStatus status,List<Long> subservices, String parcelStatus) throws java.text.ParseException;

	void generateParcelDispatchReport(User loginedUser, List<String> trackId) throws JRException;

	ParcelVo findByParcelId(String parcelid);
	List<TrackingCS> updateOutForDelivery(List<String> parcelids, User loginedUser);

	String getDispatchStatus(String trackId, String dispatchStatus, User loginedUser, String date, String timeValue,	String timeValue1) throws java.text.ParseException;

	List<ParcelVo> findParcelsReportDataAdmin(String fromdate ,String todate ,List<Long> services,User user,PStatus status,List<Long> subservices ,Long postalCode, String parcelStatus) throws java.text.ParseException;
        List<TrackingVo> getUnbaggedList(User loginedUser);
        
        List<TrackingVo> getAllEmptyBagList(User loginUser);
        
        void disposedEmptyBag(List<String> emptyBagId,User loginUser);

    	List<TrackingVo> disposedEmptyBagList(User loginedUser);

    	List<TrackingVo> disposedEmptyBagAdmin(String location, String rmsAndPostalCode);

}
