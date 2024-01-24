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
import com.vo.ParcelVo;
import com.vo.ServicesVo;
import com.vo.TrackingVo;
import com.vo.UpdatePayableAmountVo;
import com.vo.UserAddressVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParcelService
{
    List<Services> fetchPostalSerives();
    List<MasterAddressVo> getAddressByPostalcode(int postalCode);
    List<String> getParcelCountCollection(int senderPincode);
    Parcel saveParcelDetails(Parcel parcel, UpdatePayableAmountVo jsonObj, String userName);

    ParcelVo fetchParcelDetail(String trackId);
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
	List<MasterAddressVo> getAddressByThana(String thana);
	List<MasterAddressVo> getAddressBysubOfficeStartsWith(String subOffice);
	List<MasterAddressVo> getAddressByDistrict(String district);
	List<MasterAddressVo> getAddressByDivision(String division);
	Page<Parcel> getAllParcels(Pageable pageable);
	List<Parcel> getRecentParcels(int count);
	List<ServicesVo> getSubServices(Long serviceId);

	List<ParcelVo> getAllParcelBetweenDates(Timestamp fromDate, Timestamp toDate,String status);
	void generateParcelReport(String fromDate, String toDate, User user,String parcelStatus) throws ParseException;
	void generateParcelReportExcel(Timestamp fromDate, Timestamp toDate,String parcelStatus) throws IOException;
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
	List<TrackingCS> fetchRecentTransactionOfBag(User loginedUser);

	public UserAddressVO getUserAddress(User user, MasterAddressVo address);
    int getParcelWeight();

}
