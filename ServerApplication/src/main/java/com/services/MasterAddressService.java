package com.services;

import java.util.List;

import javax.validation.Valid;

import com.constants.Status;
import com.domain.*;
import com.vo.*;

public interface MasterAddressService {

	List<Zone> fetchZoneList();
	List<Division> fetchDivisionList();
	List<District> fetchDistrictList();
	List<Thana> fetchThanaList();

	List<District> getDistrictByDivision(Long id);
	List<Thana> getThanaIdByDistrict(List<District> district);
	List<MasterAddress> getMasterAddressByThana(List<Long> thana);

	List<DivisionVo> fetchDivisionListByZone(Long id);
	List<DistrictVo> fetchDistrictListByDivision(Long id);
	List<ThanaVo> fetchThanaListByDistrict(Long id);
	//List<MasterAddress> getAllMasterAddress();

	Boolean fetchExistingPostalCode(int pinCode);

	Zone saveZoneDetails(Zone zone, User user);
	Zone updateZoneService(Long Zid, User user, String updateZone);
	Zone deleteZoneService(Long del_id, User user);

	Division saveDivisionDetails(Division division, User user);
	Division updateDivisionService(Division division, User user, Zone zone, String Name);
	Division updateOrDeleteDivision(Division division,User user);

	District DistrictDelete(District Olddistrict, User user);
	District saveDistrict(District district, User user);
	District updateDistrict(District Olddistrict, User user,Division division, String Name);

	Thana saveThanaDetails(Thana thana, User user);
	Thana updateThanaDetails(Thana oldThana, User user, District district, String thanaName );
	Thana deleteThanaDetails(Thana oThana, User user );

	MasterAddress saveSubOfficeDetails(MasterAddress masterAddress, Zone zone, Division division, District district, Thana thana, User user);
	MasterAddress updateSubOfficeOrMasterAddressService(MasterAddress masterAddress, Zone zone, Division division, District district, Thana thana, User user, String subOfficeame, Integer postalCode);
	MasterAddress updateOrDeleteSubOfficeOrMasterAddress(MasterAddress masterAddress, User user);

	///For VAlidaton
	//List<Thana> findThanaWithDistrictId(Long id, String thana, Status active);
	//List<Zone> findZoneByZoneName(String zone, Status active);
	//List<Division> findDivisionWithZoneId(Long id, String division, Status status);
	//List<District> findDistricrWithDivisionId(Long id, String district, Status active);
	//List<MasterAddress> findSubOfficeWithThana(String thana, String subOffice, Status active);


	//for RMS
	RmsTable saveRmsDetails(RmsTable objRms, User user);
	RmsTable updateRMS(Long RMSId, RmsTable formData, User user);
	RmsTable DeleteRMS(Long del_id, User user);
	Boolean getExistRmsData(String rmsName, String rmsType,int postalCode, Status active);
	MasterAddressVo getPostalData(int pincode);
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
	Services addServices(String service, String serviceCode, User user);//
	Services updateServices(Long serviceId,String service, String serviceCode, User user);//
	Boolean fetchExistingServicesForUpdate(String service, String serviceCode);
    Boolean fetchExistingSubServices(Long pSId, String subServiceName, String serviceCode);
	Boolean fetchExistingSubServicesOnUpdate(Long serviceId, String subServiceName, String serviceCode);
    Services addSubServices(Long parantServiceId, String subServiceName, String serviceCode, User user);//
	Services updateSubServices(Long pSId, Long serviceId, String subServiceName, String serviceCode, User user);//
	Services deleteService(Long serviceId, User user);//
	Services onlyUpdateSubService(Long serviceId, User user);//
	RmsTable findRMSByRMSId(Long rmsId);

	List<ZoneVo> getAllZoneList();
	List<ZoneVo> getAllZoneListByStatus(Status status);
	List<DivisionVo> getAllDivisionList();
	List<DivisionVo> getAllDivisionListByStatus(Status status);
	List<DistrictVo> getAllDistrictList();
	List<DistrictVo> getAllDistrictListByStatus(Status status);
	List<ThanaVo> getAllThanaList();
	List<ThanaVo> getAllThanaListByStatus(Status status);
	List<MasterAddressVo> getAllMasterAddressList();
	List<MasterAddressVo> getAllMasterAddressListByStatus(Status status);

	List<RmsTableVo> getAllRMSList();
	List<RmsTableVo> getAllRMSListByStatus(Status status);

	List<ServicesVo> getAllServicesList();
	List<ServicesVo> getAllServicesListByStatus(Status status);


	//for rate table
	List<RateTableVo> getAllRateTableDataList();
	List<RateTableVo> getAllRateTableDataByStatus(Status status);
	
	RateTable saveRateTableData(RateTableVo saveRateTableDataVo, User user);
	RateTable updateRateTable(RateTableVo rateTable, User user);
	RateTable deleteRateData(Long id, User user);
	
	//for dropdowns
	List<MainServiceVo> getAllMainServicesForDroupDownList();
	List<SubServiceVo> fetchSubServicesByServiceId(Long id);
	ServicesVo fetchServiceBySubServicesId(Long id);
	List<SubServiceVo> getAllSubServices();
	
	List<DivisionVo> getAllDivisionForRateMaster();
	List<DistrictVo> getAllDistrictForRateMaster();
	List<ThanaVo> getAllThanaForRateMaster();
	List<SubOfficeVo> getAllSubOfficeForRateMaster();

	void generateRateReportInPdf();

	List<ConfigVo> getAllConfigList();
	List<ConfigVo> getAllConfigListByStatus(Status valueOf);
	Boolean fetchExistingConfig(String configType);
	Boolean fetchExistingConfig(@Valid Long id, Config objConfig);
	
	//List<Config> fetchConfigTypeList();
	Config updateConfigDetails(@Valid Long id, User loginedUser, Config objConfig);
	Config deleteConfigDetails(Config configOldObj, User loginedUser);
	Config saveConfigDetails(Config objConfig, User loginedUser);
}
