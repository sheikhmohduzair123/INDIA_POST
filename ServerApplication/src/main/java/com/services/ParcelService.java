package com.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vo.MasterAddressVo;
import com.vo.ServicesVo;
import com.vo.TrackingVo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParcelService
{
    List<Services> fetchPostalSerives();
    List<MasterAddressVo> getAddressByPostalcode(int postalCode);
    List<String> getParcelCountCollection(int senderPincode);
    Parcel saveParcelDetails(Parcel parcel, String userName);

    Parcel fetchParcelDetail(String trackId);
    void setParcelStatusVoid(String trackId);
    Parcel setReprintDetails(String trackId, String paperSize) throws JsonMappingException, JsonProcessingException;

	List<MasterAddressVo> getAddressBySubOffice(String subOffice);
	List<String> getDistinctDivisionByZone(String zone);
	List<String> getDistinctDistrictByDivision(String division);
	List<String> getDistinctThanaByDistrict(String district);
	List<String> getDistinctSubOfficeByThana(String thana);
	List<String> getDistinctZone();
	List<String> getDistinctDivision();
	List<String> getDistinctDistrict();
	List<MasterAddress> getAddressByThana(String thana);
	List<MasterAddress> getAddressBysubOfficeStartsWith(String subOffice);
	List<MasterAddress> getAddressByDistrict(String district);
	List<MasterAddressVo> getAddressByDivision(String division);
	Page<Parcel> getAllParcels(Pageable pageable);
	List<Parcel> getRecentParcels(int count);
	List<ServicesVo> getSubServices(Long serviceId);

	List<Parcel> getAllParcelBetweenDates(Timestamp fromDate, Timestamp toDate);
	void generateParcelReport(String fromDate, String toDate, User user) throws ParseException;
	void generateParcelReportExcel(Timestamp fromDate, Timestamp toDate) throws IOException;
	Client getClientDetails();
	public String getParcelCountTodayCollection();
	public String getParcelCountYesterDayCollection();
	public String getVoidParcelCount();
	public String getVoidYesterdayParcelCount();

	List<String> getDistinctSubOffice();
	List<String> getDistinctThana();
	List<MasterAddressVo> getAddressByThanaOnly(String thana);
	List<Parcel> getAllParcelsList();
	List<Parcel> getBySyncFlag(Boolean syncFlag);
	//HashMap<String,Object> findBagDetailsWithBagId(String bagId, User loginedUser);
	List<TrackingVo> findBagDetailsWithBagId(String bagId, User loginedUser);
	//Map<Object, List<TrackingCS>> fetchRecentTransactionOfBag(User loginedUser);
	List<TrackingVo> fetchRecentTransactionOfBag(User loginedUser);
	List<MasterAddressVo> getSubOffice();
	List<MasterAddressVo> getSubOfficeByThana(String thana);
	
}
