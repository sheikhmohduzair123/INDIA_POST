package com.services;

import java.util.List;

import org.springframework.context.annotation.Profile;

import com.constants.Status;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;

public interface MasterAddressService {

	List<Zone> fetchZoneList();
	List<Division> fetchDivisionList();
	List<District> fetchDistrictList();
	List<Thana> fetchThanaList();

	List<District> getDistrictByDivision(Long id);
	List<Thana> getThanaIdByDistrict(List<District> district);
	List<MasterAddress> getMasterAddressByThana(List<Long> thana);

	List<Division> fetchDivisionListByZone(Long id);
	List<District> fetchDistrictListByDivision(Long id);
	List<Thana> fetchThanaListByDistrict(Long id);
	//List<MasterAddress> getAllMasterAddress();

	Boolean fetchExistingPostalCode(int pinCode);

	void saveZoneDetails(Zone zone, User user);
	void updateZoneService(Long Zid, User user, String updateZone);
	void deleteZoneService(Long del_id, User user);

	void saveDivisionDetails(Division division, User user);
	void updateDivisionService(Division division, User user, Zone zone, String Name);
	void updateOrDeleteDivision(Division division,User user);

	void DistrictDelete(District Olddistrict, User user);
	void saveDistrict(District district, User user);
	void updateDistrict(District Olddistrict, User user,Division division, String Name);

	void saveThanaDetails(Thana thana, User user);
	void updateThanaDetails(Thana oldThana, User user, District district, String thanaName );
	void deleteThanaDetails(Thana oThana, String thanaName, User user );

	void saveSubOfficeDetails(MasterAddress masterAddress, Zone zone, Division division, District district, Thana thana, User user);
	void updateSubOfficeOrMasterAddressService(MasterAddress masterAddress, Zone zone, Division division, District district, Thana thana, User user, String subOfficeame, Integer postalCode);
	void updateOrDeleteSubOfficeOrMasterAddress(MasterAddress masterAddress, User user);

	///For VAlidaton
	List<Thana> findThanaWithDistrictId(Long id, String thana, Status active);
	List<Zone> findZoneByZoneName(String zone, Status active);
	List<Division> findDivisionWithZoneId(Long id, String division, Status status);
	List<District> findDistricrWithDivisionId(Long id, String district, Status active);
	List<MasterAddress> findSubOfficeWithThana(String thana, String subOffice, Status active);


	//for RMS
	void saveRmsDetails(RmsTable objRms, User user);
	void updateRMS(Long RMSId, RmsTable formData, User user);
	void DeleteRMS(Long del_id, User user);
	Boolean getExistRmsData(String rmsName, String rmsType,int postalCode, Status active);
	MasterAddress getPostalData(int pincode);
	RmsTable updateButtonFilledData(Long updateId);

	List<String> fetchRMSZoneList(Status active);
	List<String> fetchRMSDivisionList(Status active);
	List<String> fetchRMSDistrictList(Status active);
	List<String> fetchRMSThanaList(Status active);
	List<String> fetchRMSSubOfficeList(Status active);


	// For Exiting Name
	Boolean fetchExistingZone(String zone);
	Boolean fetchExistingDivision(Long id, String division);
	Boolean fetchExistingDistrict(Long id, String district);
	Boolean fetchExistingThana(Long id, String thana);
	Boolean fetchExistingSubOffice(String zone, String division, String district, String thana, String subOffice);

	List<Services> fetchServicesList();
	Boolean fetchExistingServices(String service, String serviceCode);
	void addServices(String service, String serviceCode, User user);
	void updateServices(Long serviceId,String service, String serviceCode, User user);
	Boolean fetchExistingServicesForUpdate(String service, String serviceCode);
    Boolean fetchExistingSubServices(Long pSId, String subServiceName, String serviceCode);
	Boolean fetchExistingSubServicesOnUpdate(Long serviceId, String subServiceName, String serviceCode);
    void addSubServices(Long parantServiceId, String subServiceName, String serviceCode, User user);
	void updateSubServices(Long pSId, Long serviceId, String subServiceName, String serviceCode, User user);
	void deleteService(Long serviceId, User user);
	void onlyUpdateSubService(Long serviceId, User user);
	RmsTable findRMSByRMSId(Long rmsId);
}
