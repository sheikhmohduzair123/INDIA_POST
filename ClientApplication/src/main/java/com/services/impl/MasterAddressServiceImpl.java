package com.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.domain.Control;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.Thana;
import com.domain.User;
import com.domain.Zone;
import com.repositories.ControlRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.ZoneAddressRepo;
import com.services.MasterAddressService;

@Service
public class MasterAddressServiceImpl implements MasterAddressService {

	protected Logger log = LoggerFactory.getLogger(MasterAddressServiceImpl.class);

	@Autowired
	private ZoneAddressRepo zoneAddressRepo;

	@Autowired
	private DivisionAddressRepo divisionAddressRepo;

	@Autowired
	private DistrictAddressRepo districtAddressRepo;

	@Autowired
	private ThanaAddressRepo thanaAddressRepo;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private RmsRepository rmsRepository;

	@Autowired
	private ControlRepository controlRepository;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Override
	public void saveZoneDetails(Zone zone, User user) {

		log.info("inside saveZoneDetails");
		zone.setCreatedBy(user);
		zone.setUpdatedBy(user);
		zone.setCreatedOn(new Date());
		zone.setUpdatedOn(new Date());
		zone.setStatus(Status.ACTIVE);
		zoneAddressRepo.save(zone);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
        Timestamp timestamp = new Timestamp(zone.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("Zone Details Saved");
	}

	@Override
	public void updateZoneService(Long Zid, User user, String updateZone) {
		Optional<Zone> zoneList = zoneAddressRepo.findById(Zid);
		Zone selectedZone = zoneList.get();
		Zone newZoneEntry = new Zone();
		Date date = new Date();
		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByZoneId(Zid);
		if(selectedZone.getStatus() == Status.ACTIVE) {
			for(MasterAddress masterAddresses : masterAddressesList){
				masterAddresses.setZone(updateZone);
				masterAddresses.setUpdatedOn(date);
				masterAddresses.setUpdatedBy(user);
			}
			newZoneEntry.setCreatedOn(selectedZone.getCreatedOn());
			selectedZone.setCreatedOn(selectedZone.getCreatedOn());
			newZoneEntry.setZone(selectedZone.getZone());
			selectedZone.setZone(updateZone);
			newZoneEntry.setStatus(Status.DISABLED);
			newZoneEntry.setUpdatedOn(date);
			selectedZone.setUpdatedOn(date);  //new date
			newZoneEntry.setCreatedBy(selectedZone.getCreatedBy());
			newZoneEntry.setUpdatedBy(user);
			selectedZone.setUpdatedBy(user);
		}
		zoneAddressRepo.save(newZoneEntry);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
	    Timestamp timestamp = new Timestamp(newZoneEntry.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.debug("zone is updated successfully");
	}

	@Override
	public void deleteZoneService(Long del_id, User user) {

		log.debug("Deleting Data Continue");
		Optional<Zone> zoneList = zoneAddressRepo.findById(del_id);
		Zone zone = zoneList.get();
		Date date = new Date();

		if(zone.getStatus() == Status.ACTIVE) {
			List<Division> divisionList = divisionAddressRepo.findDivisionByzone_id(zone.getId());
			List<District> districtList = districtAddressRepo.findByDivisionIn(divisionList);
			List<Thana> thanaList = thanaAddressRepo.findByDistrictIn(districtList);

			List<Long> thanaId = new ArrayList<Long>();
			for (Thana thana : thanaList) {
				thanaId.add(thana.getId());
			}

			List<MasterAddress> masterAddressesList = masterAddressRepository.findByThanaIdIn(thanaId);
			zone.setStatus(Status.DISABLED);
			zone.setUpdatedBy(user);
			zone.setUpdatedOn(date);

			for(Division division : divisionList) {
				division.setStatus(Status.DISABLED);
				division.setUpdatedBy(user);
				division.setUpdatedOn(date);
				divisionAddressRepo.save(division);
			}

			for(District district : districtList) {
				district.setStatus(Status.DISABLED);
				district.setUpdatedBy(user);
				district.setUpdatedOn(date);
				districtAddressRepo.save(district);
			}

			for(Thana thana : thanaList) {
				thana.setStatus(Status.DISABLED);
				thana.setUpdatedBy(user);
				thana.setUpdatedOn(date);
				thanaAddressRepo.save(thana);
			}

			for(MasterAddress masterAddress : masterAddressesList) {
				masterAddress.setStatus(Status.DISABLED);
				masterAddress.setUpdatedBy(user);
				masterAddress.setUpdatedOn(date);
					masterAddressRepository.save(masterAddress);
			}
			zoneAddressRepo.save(zone);

			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(zone.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
		}

	}

	@Override
	public Boolean fetchExistingZone(String zone) {
		List<Zone> validZoneList = zoneAddressRepo.findByZoneContainingIgnoreCaseAndStatus(zone, Enum.valueOf(Status.class,"ACTIVE"));
		for(int i=0; i<validZoneList.size(); i++) {
			if(validZoneList.get(i).getZone().equalsIgnoreCase(zone)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveDivisionDetails(Division division, User user) {
		Date date = new Date();

		log.info("inside saveDivisionDetails");
		division.setCreatedBy(user);
		division.setUpdatedBy(user);
		division.setCreatedOn(date);
		division.setUpdatedOn(date);
		division.setStatus(Status.ACTIVE);
		divisionAddressRepo.save(division);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
	    Timestamp timestamp = new Timestamp(division.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("Division Details Saved");
	}

	@Override
	public void updateDivisionService(Division division, User user,Zone zone, String divisionName) {

		Date date = new Date();
		Division newDivision = new Division();
		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByDivisionId(division.getId());
		if (division.getStatus() == Status.ACTIVE) {
			for(MasterAddress masterAddresses : masterAddressesList){
				masterAddresses.setDivision(divisionName);
				masterAddresses.setUpdatedBy(user);
				masterAddresses.setUpdatedOn(date);
			}
			newDivision.setCreatedOn(division.getCreatedOn());//old date
			division.setCreatedOn(division.getCreatedOn());// new data
			newDivision.setZone(division.getZone());
			division.setZone(zone);
			newDivision.setStatus(Status.DISABLED);
			newDivision.setUpdatedOn(date);// for new Updated date
			division.setUpdatedOn(date);// for old update date
			newDivision.setDivision(division.getDivision());// for new division
			division.setDivision(divisionName);// for Old division
			newDivision.setCreatedBy(division.getCreatedBy());
			division.setUpdatedBy(user);// for old updateBy
			newDivision.setUpdatedBy(user);// for new updated By
			divisionAddressRepo.save(newDivision);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newDivision.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
		}
	}

	@Override
	public void updateOrDeleteDivision(Division division,User user) {
		Date date = new Date();

		division.setStatus(Status.DISABLED);
		division.setUpdatedBy(user);
		division.setUpdatedOn(date);
		List<District> districtList = getDistrictByDivision(division.getId());
		List<Thana> thanaList = getThanaIdByDistrict(districtList);
		List<Long> thanaid = new ArrayList<Long>();
		for (Thana thana : thanaList) {
			thanaid.add(thana.getId());
		}
		List<MasterAddress> masterAddressesList = getMasterAddressByThana(thanaid);

		for (District district : districtList) {
			district.setStatus(Status.DISABLED);
			district.setUpdatedBy(user);
			district.setUpdatedOn(date);
		}
		for (Thana thana : thanaList) {
			thana.setStatus(Status.DISABLED);
			thana.setUpdatedBy(user);
			thana.setUpdatedOn(date);
		}
		for (MasterAddress masterAddress : masterAddressesList) {
			masterAddress.setStatus(Status.DISABLED);
			masterAddress.setUpdatedBy(user);
			masterAddress.setUpdatedOn(date);
		}
		divisionAddressRepo.save(division);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(division.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public Boolean fetchExistingDivision(Long id, String division) {
		List<Division> validDivisiontList = divisionAddressRepo.findDivisionByzone_idAndDivisionContainingIgnoreCaseAndStatus(id, division, Enum.valueOf(Status.class,"ACTIVE"));
		for(int i=0; i<validDivisiontList.size(); i++) {
			if(validDivisiontList.get(i).getDivision().equalsIgnoreCase(division)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveDistrict(District district, User user) {

		log.info("inside Save District Implementation");
		Date date = new Date();

		district.setCreatedBy(user);
		district.setUpdatedBy(user);
		district.setCreatedOn(date);
		district.setUpdatedOn(date);
		district.setStatus(Status.ACTIVE);
		districtAddressRepo.save(district);
		log.info("Saved District Details Succesfully");
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(district.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public void updateDistrict(District lastDistrict, User user,Division division, String districtName) {

		log.info("inside Update District Implementation");
		Date date = new Date();

		lastDistrict.setUpdatedOn(lastDistrict.getUpdatedOn());
		lastDistrict.setUpdatedBy(user);
		District newDistrict=new District();
		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByDistrictId(lastDistrict.getId());
		if(lastDistrict.getStatus()==Status.ACTIVE) {
			for(MasterAddress masterAddresses : masterAddressesList){
				masterAddresses.setDistrict(districtName);
				masterAddresses.setUpdatedBy(user);
				masterAddresses.setUpdatedOn(date);
			}
			newDistrict.setCreatedOn(lastDistrict.getCreatedOn());
			newDistrict.setDivision(lastDistrict.getDivision());
			lastDistrict.setDivision(division);
			newDistrict.setStatus(Status.DISABLED);
			newDistrict.setUpdatedOn(date);
			lastDistrict.setUpdatedOn(date);
			newDistrict.setDistrict(lastDistrict.getDistrict());
			lastDistrict.setDistrict(districtName);
			newDistrict.setCreatedBy(lastDistrict.getCreatedBy());
			newDistrict.setUpdatedBy(user);
			districtAddressRepo.save(newDistrict);
			log.info("Update District Details Succesfully");
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newDistrict.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
		}
	}

	@Override
	public void DistrictDelete(District DistrictData, User user) {

		log.info("inside District Delete Implementation");
		Date date = new Date();

		if (DistrictData.getStatus() == Status.ACTIVE) {
			DistrictData.setStatus(Status.DISABLED);
			DistrictData.setUpdatedBy(user);
			DistrictData.setUpdatedOn(date);
			List<Thana> thanaList = (List<Thana>) thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(DistrictData.getId(),Status.ACTIVE);
			for (Thana objThana : thanaList) {
				objThana.setStatus(Status.DISABLED);
				objThana.setUpdatedBy(user);
				objThana.setUpdatedOn(date);
				List<MasterAddress> MasterAddressList =(List<MasterAddress>)masterAddressRepository.findMasterAddressByThanaAndStatus(objThana.getThana(),Status.ACTIVE);
				for (MasterAddress objMaster : MasterAddressList) {
					objMaster.setStatus(Status.DISABLED);
					objMaster.setUpdatedBy(user);
					objMaster.setUpdatedOn(date);
				}
			}
		}
		districtAddressRepo.save(DistrictData);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(DistrictData.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("Delete District Details Succesfully");
	}

	@Override
	public Boolean fetchExistingDistrict(Long id, String district) {

		List<District> validDistrictList = districtAddressRepo.findDistrictBydivision_idAndDistrictContainingIgnoreCaseAndStatus(id, district, Enum.valueOf(Status.class,"ACTIVE"));
		for(int i = 0; i<validDistrictList.size(); i++) {
			if(validDistrictList.get(i).getDistrict().equalsIgnoreCase(district)) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void saveThanaDetails(Thana thana, User user) {

		log.info("inside saveThanaDetails");
		Date date = new Date();

		thana.setCreatedBy(user);
		thana.setUpdatedBy(user);
		thana.setCreatedOn(date);
		thana.setUpdatedOn(date);
		thana.setStatus(Status.ACTIVE);
		thanaAddressRepo.save(thana);
		log.info("Thana Details Saved");
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(thana.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public void updateThanaDetails(Thana oldThana, User user, District district, String thanaName) {

		Thana newThana= new Thana();
		Date date = new Date();

		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByThanaId(oldThana.getId());
		if(oldThana.getStatus()==Status.ACTIVE) {
			for(MasterAddress masterAddresses : masterAddressesList){
				masterAddresses.setThana(thanaName);
				masterAddresses.setUpdatedBy(user);
				masterAddresses.setUpdatedOn(date);
			}
		    newThana.setCreatedOn(oldThana.getCreatedOn());//old data
			newThana.setDistrict(oldThana.getDistrict());//old data
			oldThana.setDistrict(district);
			newThana.setStatus(Status.DISABLED);
			newThana.setThana(oldThana.getThana());
			oldThana.setThana(thanaName);
			newThana.setCreatedBy(oldThana.getCreatedBy());
			newThana.setUpdatedBy(user);
			oldThana.setUpdatedOn(date);
			oldThana.setCreatedOn(oldThana.getCreatedOn());
			newThana.setUpdatedOn(date);
			oldThana.setUpdatedBy(user);
			thanaAddressRepo.save(newThana);
				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
				Timestamp timestamp = new Timestamp(newThana.getUpdatedOn().getTime());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);
		}
	}

	@Override
	public void deleteThanaDetails(Thana oThana, String thanaName,User user) {
		Date date = new Date();

		if(oThana.getStatus()==Status.ACTIVE) {
			oThana.setStatus(Status.DISABLED);
			oThana.setUpdatedBy(user);
			oThana.setUpdatedOn(new Date());
			List<MasterAddress> subOfficeList = masterAddressRepository.findMasterAddressByThanaAndStatus(oThana.getThana(),Status.ACTIVE);
					for(MasterAddress subOffice:subOfficeList) {
						subOffice.setStatus(Status.DISABLED);
						subOffice.setUpdatedBy(user);
						subOffice.setUpdatedOn(date);
			}
			thanaAddressRepo.save(oThana);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(oThana.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
		 }
	}

	@Override
	public Boolean fetchExistingThana(Long id, String thana) {
		List<Thana> validThanaList = thanaAddressRepo.findThanaBydistrict_idAndThanaContainingIgnoreCaseAndStatus(id, thana, Enum.valueOf(Status.class,"ACTIVE"));
		for(int i = 0; i<validThanaList.size(); i++) {
			if(validThanaList.get(i).getThana().equalsIgnoreCase(thana)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveSubOfficeDetails(MasterAddress masterAddress, Zone zone, Division division, District district,
			Thana thana, User user) {

		log.info("inside saveSubOfficeDetails");
		Date date = new Date();

		masterAddress.setCreatedBy(user);
		masterAddress.setUpdatedBy(user);
		masterAddress.setCreatedOn(date);
		masterAddress.setUpdatedOn(date);
		masterAddress.setZone(zone.getZone());
		masterAddress.setZoneId(zone.getId());
		masterAddress.setDivision(division.getDivision());
		masterAddress.setDivisionId(division.getId());
		masterAddress.setDistrict(district.getDistrict());
		masterAddress.setDistrictId(district.getId());
		masterAddress.setThana(thana.getThana());
		masterAddress.setThanaId(thana.getId());
		masterAddress.setStatus(Status.ACTIVE);
		masterAddressRepository.save(masterAddress);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(masterAddress.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("subOffice Details Saved");
	}

	@Override
	public void updateSubOfficeOrMasterAddressService(MasterAddress masterAddress, Zone zone, Division division,
													  District district, Thana thana, User user,
													  String subOfficeame, Integer postalCode) {
		MasterAddress newMasterAddress = new MasterAddress();
		Date date = new Date();

		if (masterAddress.getStatus() == Status.ACTIVE) {
			newMasterAddress.setCreatedOn(masterAddress.getCreatedOn());// old date
			masterAddress.setCreatedOn(masterAddress.getCreatedOn());// new data
			newMasterAddress.setStatus(Status.DISABLED);
			newMasterAddress.setUpdatedOn(date);// for new Updated date
			masterAddress.setUpdatedOn(date);// for old update date
			newMasterAddress.setSubOffice(masterAddress.getSubOffice());// for New SubOffice
			masterAddress.setSubOffice(subOfficeame);// for Old SubOffice
			newMasterAddress.setCreatedBy(masterAddress.getCreatedBy());
			masterAddress.setUpdatedBy(user);// for old updateBy
			newMasterAddress.setUpdatedBy(user);// for new updated By
			newMasterAddress.setPostalCode(masterAddress.getPostalCode());
			masterAddress.setPostalCode(postalCode);

			newMasterAddress.setZone(masterAddress.getZone());
			newMasterAddress.setZoneId(masterAddress.getZoneId());
			masterAddress.setZone(zone.getZone());
			masterAddress.setZoneId(zone.getId());

			newMasterAddress.setDivision(masterAddress.getDivision());
			newMasterAddress.setDivisionId(masterAddress.getDivisionId());
			masterAddress.setDivision(division.getDivision());
			masterAddress.setDivisionId(division.getId());
			newMasterAddress.setDistrict(masterAddress.getDistrict());
			newMasterAddress.setDistrictId(masterAddress.getDistrictId());
			masterAddress.setDistrict(district.getDistrict());
			masterAddress.setDistrictId(district.getId());
			newMasterAddress.setThana(masterAddress.getThana());
			newMasterAddress.setThanaId(masterAddress.getThanaId());
			masterAddress.setThana(thana.getThana());
			masterAddress.setThanaId(thana.getId());
			masterAddressRepository.save(newMasterAddress);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newMasterAddress.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
		}
	}

	@Override
	public void updateOrDeleteSubOfficeOrMasterAddress(MasterAddress masterAddress,User user) {
		Date date = new Date();

		masterAddress.setStatus(Status.DISABLED);
		masterAddress.setUpdatedBy(user);
		masterAddress.setUpdatedOn(date);
		masterAddressRepository.save(masterAddress);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(masterAddress.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public Boolean fetchExistingSubOffice(String zone, String division, String district, String thana, String subOffice) {
		List<MasterAddress> validSubOfficeList = masterAddressRepository.findByZoneContainingIgnoreCaseAndDivisionContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndThanaContainingIgnoreCaseAndSubOfficeContainingIgnoreCaseAndStatus(zone, division, district, thana, subOffice, Enum.valueOf(Status.class,"ACTIVE"));
		for(int i = 0; i<validSubOfficeList.size(); i++) {
			if(validSubOfficeList.get(i).getSubOffice().equalsIgnoreCase(subOffice)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingPostalCode(int pinCode) {
		Boolean masterAddress = masterAddressRepository.existsByPostalCodeAndStatus(pinCode, Enum.valueOf(Status.class,"ACTIVE"));
		return masterAddress;
	}


	@Override
	public List<Zone> findZoneByZoneName(String zone,Status i) {

		List<Zone> validDistrictList=zoneAddressRepo.findZoneByZoneAndStatus(zone,Status.ACTIVE);
		for(Zone validDistrict: validDistrictList) {
			if(zone == validDistrict.getZone()) {
				return null;
			}
		}
		return validDistrictList;

	}

	@Override
	public List<Division> findDivisionWithZoneId(Long id, String division,Status i) {

		List<Division> validDistrictList=divisionAddressRepo.findDivisionByzone_idAndDivisionAndStatus(id, division,Status.ACTIVE);
		for(Division validDistrict: validDistrictList) {
			if(division == validDistrict.getDivision()) {
				return null;
			}
		}
		return validDistrictList;

	}

	@Override
	public List<District> findDistricrWithDivisionId(Long divisionId, String district,Status ACTIVE) {

		List<District> validDistrictList=districtAddressRepo.findDistrictBydivisionId_idAndDistrictAndStatus(divisionId,district,Status.ACTIVE);
		for(District validDistrict: validDistrictList) {
			if(district == validDistrict.getDistrict()) {

				return null;
			}
		}
		return validDistrictList;
	}


	@Override
	public List<Thana> findThanaWithDistrictId(Long id, String thana, Status active) {

		List<Thana> validDistrictList=thanaAddressRepo.findThanaBydistrict_idAndThanaAndStatus(id, thana,Status.ACTIVE);
		for(Thana validDistrict: validDistrictList) {
			if(thana == validDistrict.getThana()) {
				return null;
			}
		}
		return validDistrictList;
	}


	@Override
	public List<MasterAddress> findSubOfficeWithThana(String thana, String subOffice, Status active) {

		List<MasterAddress> validDistrictList=masterAddressRepository.findMasterAddressByThanaAndSubOfficeAndStatus(thana, subOffice,Status.ACTIVE);

		for(MasterAddress validDistrict: validDistrictList) {
			if(subOffice == validDistrict.getSubOffice()) {
				return null;
			}
		}
		return validDistrictList;
	}



	@Override
	public List<Zone> fetchZoneList() {
		return zoneAddressRepo.findAllByStatusOrderByZoneAsc(Status.ACTIVE);
	}

	@Override
	public List<Division> fetchDivisionList() {
		return divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE);
	}

	@Override
	public List<District> fetchDistrictList() {
		return districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE);
	}

	@Override
	public List<Thana> fetchThanaList() {
		return thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE);
	}

	@Override
	public List<District> getDistrictByDivision(Long id) {
		return districtAddressRepo.findBydivision_id(id);
	}

	@Override
	public List<Thana> getThanaIdByDistrict(List<District> district) {
		return thanaAddressRepo.findByDistrictIn(district);
	}

	@Override
	public List<MasterAddress> getMasterAddressByThana(List<Long> thana) {
		return masterAddressRepository.findByThanaIdIn(thana);
	}

	// all three for subOffice according to select option
	@Override
	public List<Division> fetchDivisionListByZone(Long id) {
		 return divisionAddressRepo.findDivisionByzone_idAndStatusOrderByDivisionAsc(id,Status.ACTIVE);
	}

	@Override
	public List<District> fetchDistrictListByDivision(Long id) {
		return districtAddressRepo.findDistrictBydivision_idAndStatusOrderByDistrictAsc(id,Status.ACTIVE);
	}

	@Override
	public List<Thana> fetchThanaListByDistrict(Long id) {
		return thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(id,Status.ACTIVE);
	}


//FOR RMS

	@Override
	public List<String> fetchRMSZoneList(Status active) {

		return masterAddressRepository.findDistinctZone(active);
	}

	@Override
	public List<String> fetchRMSDivisionList(Status active) {

		return masterAddressRepository.findDistinctDivision(active);
	}

	@Override
	public List<String> fetchRMSDistrictList(Status active) {

		return masterAddressRepository.findDistinctDistrict(active);
	}

	@Override
	public List<String> fetchRMSThanaList(Status active) {

		return masterAddressRepository.findDistinctThana(active);
	}

	@Override
	public List<String> fetchRMSSubOfficeList(Status active) {

		return masterAddressRepository.findDistinctSubOffice(active);
	}

	@Override
	public void saveRmsDetails(RmsTable objRms, User user) {

		log.info("inside saveRMSDetails");
		objRms.setCreatedBy(user);
		objRms.setUpdatedBy(user);
		objRms.setCreatedOn(new Date());
		objRms.setUpdatedOn(new Date());
		objRms.setStatus(Status.ACTIVE);
		rmsRepository.save(objRms);
		log.info("RMS Details saved");
	}

	@Override
	public RmsTable updateButtonFilledData(Long updateId) {

		return rmsRepository.findById(updateId).orElse(null);
	}



	@Override
	public void updateRMS(Long RMSId, RmsTable formData, User user) {

		RmsTable oldData = rmsRepository.findById(RMSId).orElse(null);
		RmsTable newRMSObj = new RmsTable();
		Date date = new Date();
		if(oldData.getStatus() == Status.ACTIVE) {
			newRMSObj.setCreatedOn(oldData.getCreatedOn()); //old date
			oldData.setCreatedOn(oldData.getCreatedOn());//new date
			newRMSObj.setStatus(Status.DISABLED);
			newRMSObj.setUpdatedOn(date); //for new updated date
			oldData.setUpdatedOn(date);  //for new update date

			newRMSObj.setCreatedBy(oldData.getCreatedBy());
			oldData.setUpdatedBy(user);
			newRMSObj.setUpdatedBy(user);

			newRMSObj.setRmsName(oldData.getRmsName());
			oldData.setRmsName(formData.getRmsName());

			newRMSObj.setRmsType(oldData.getRmsType());
			oldData.setRmsType(formData.getRmsType());

			newRMSObj.setRmsAddress(oldData.getRmsAddress());
			oldData.setRmsAddress(formData.getRmsAddress());

			newRMSObj.setRmsMobileNumber(oldData.getRmsMobileNumber());
			oldData.setRmsMobileNumber(formData.getRmsMobileNumber());

			rmsRepository.save(newRMSObj);

		}


	}

	@Override
	public void DeleteRMS(Long del_id, User user) {
		log.info("Deleting data");
		RmsTable rmsList = rmsRepository.findById(del_id).orElse(null);
		if(rmsList.getStatus() == Status.ACTIVE) {
			rmsList.setStatus(Status.DISABLED);
			rmsList.setUpdatedBy(user);
			rmsList.setUpdatedOn(new Date());
			rmsRepository.save(rmsList);
		}
	}

	@Override
	public Boolean getExistRmsData(String rmsName, String rmsType, int postalCode, Status active) {
		return rmsRepository.existsByRmsNameContainingIgnoreCaseAndRmsTypeAndRmsAddressPostalCodeAndStatus(rmsName, rmsType, postalCode, active);
	}

	@Override
	public MasterAddress getPostalData(int pincode) {
		MasterAddress p = masterAddressRepository.findMasterAddressByPostalCodeAndStatus(pincode, Status.ACTIVE);
		return p;
	}

	@Override
	public List<Services> fetchServicesList() {
		return postalServiceRepository.findByParentServiceIdIsNullAndStatus(Status.ACTIVE);
	}

	@Override
	public Boolean fetchExistingServices(String service, String serviceCode) {
		List<Services> serviceList = postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(service, Status.ACTIVE, serviceCode, Status.ACTIVE);
		for(int i = 0; i<serviceList.size(); i++){
			if(serviceList.get(i).getServiceName().equalsIgnoreCase(service) || serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingServicesForUpdate(String service, String serviceCode) {
		List<Services> serviceList = postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(service, Status.ACTIVE, serviceCode, Status.ACTIVE);
		for(int i = 0; i<serviceList.size(); i++){
			if(serviceList.get(i).getServiceName().equalsIgnoreCase(service) && serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void addServices(String service, String serviceCode, User user) {
		Date date = new Date();
		Services services=new Services();
		services.setCreatedBy(user);
		services.setUpdatedBy(user);
		services.setCreatedOn(date);
		services.setUpdatedOn(date);
		services.setStatus(Status.ACTIVE);
		services.setServiceName(service);
		services.setServiceCode(serviceCode);
		postalServiceRepository.save(services);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(services.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public void updateServices(Long serviceId, String service, String serviceCode, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		Services newServices = new Services();
		Date date = new Date();
		if(serviceData.getStatus() == Status.ACTIVE){

			newServices.setServiceName(serviceData.getServiceName());
			serviceData.setServiceName(service);
			newServices.setServiceCode(serviceData.getServiceCode());
			serviceData.setServiceCode(serviceCode);

			newServices.setUpdatedBy(user);
			serviceData.setUpdatedBy(user);

			newServices.setCreatedBy(user);
			serviceData.setCreatedBy(serviceData.getCreatedBy());

			newServices.setCreatedOn(serviceData.getCreatedOn());
			serviceData.setCreatedOn(serviceData.getCreatedOn());

			newServices.setUpdatedOn(date);
			serviceData.setUpdatedOn(date);

			newServices.setStatus(Status.DISABLED);
		}
		postalServiceRepository.save(newServices);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(newServices.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override

	public Boolean fetchExistingSubServices(Long pSId, String subServiceName, String serviceCode) {

		List<Services> serviceList = postalServiceRepository.findByParentServiceIdAndStatusOrServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(pSId, Status.ACTIVE, subServiceName, Status.ACTIVE, serviceCode, Status.ACTIVE);
		for(int i = 0; i<serviceList.size(); i++){
			if(
				(serviceList.get(i).getParentServiceId().equals(pSId) && serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName) && serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode))
				||
				(serviceList.get(i).getParentServiceId().equals(pSId) && serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName))
				||
				(serviceList.get(i).getParentServiceId().equals(pSId) && serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode))
			){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingSubServicesOnUpdate(Long serviceId, String subServiceName, String serviceCode) {

		List<Services> serviceList = postalServiceRepository.findByParentServiceIdAndServiceNameContainingIgnoreCaseAndServiceCodeContainingIgnoreCaseAndStatus(serviceId, subServiceName, serviceCode, Status.ACTIVE);
		for(int i = 0; i<serviceList.size(); i++){
			if(serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName) && serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode) && serviceList.get(i).getParentServiceId().equals(serviceId)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void addSubServices(Long parantServiceId, String subServiceName, String serviceCode, User user) {
		Date date = new Date();
		Services services=new Services();
		services.setCreatedBy(user);
		services.setUpdatedBy(user);
		services.setCreatedOn(date);
		services.setUpdatedOn(date);
		services.setStatus(Status.ACTIVE);
		services.setParentServiceId(parantServiceId);
		services.setServiceName(subServiceName);
		services.setServiceCode(serviceCode);
		postalServiceRepository.save(services);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(services.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public void updateSubServices(Long pSId, Long serviceId, String subServiceName, String serviceCode, User user) {
		Date date = new Date();
		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		Services newServices = new Services();

		newServices.setServiceName(serviceData.getServiceName());
		serviceData.setServiceName(subServiceName);
		newServices.setServiceCode(serviceData.getServiceCode());
		serviceData.setServiceCode(serviceCode);
		newServices.setParentServiceId(serviceData.getParentServiceId());
		serviceData.setParentServiceId(pSId);
		newServices.setStatus(Status.DISABLED);
		serviceData.setStatus(Status.ACTIVE);
		newServices.setCreatedBy(serviceData.getCreatedBy());
		serviceData.setCreatedBy(serviceData.getCreatedBy());
		newServices.setUpdatedBy(user);
		serviceData.setUpdatedBy(user);
		newServices.setCreatedOn(serviceData.getCreatedOn());
		serviceData.setCreatedOn(serviceData.getCreatedOn());
		newServices.setUpdatedOn(date);
		serviceData.setUpdatedOn(date);

		postalServiceRepository.save(newServices);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(serviceData.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override
	public void deleteService(Long serviceId, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		List<Services> servicesList = postalServiceRepository.findByParentServiceIdAndStatus(serviceId, Status.ACTIVE);
		serviceData.setStatus(Status.DISABLED);
		serviceData.setUpdatedBy(user);
		serviceData.setUpdatedOn(new Date());
		for(Services servi : servicesList){
			servi.setStatus(Status.DISABLED);
			servi.setUpdatedBy(user);
			servi.setUpdatedOn(new Date());
		}
		postalServiceRepository.save(serviceData);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(serviceData.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
	}

	@Override

	public void onlyUpdateSubService(Long serviceId, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		if(serviceData.getStatus() == Status.ACTIVE){
			serviceData.setStatus(Status.DISABLED);
			serviceData.setUpdatedBy(user);
			serviceData.setUpdatedOn(new Date());
		}
		postalServiceRepository.save(serviceData);
	}

	@Override
	public RmsTable findRMSByRMSId(Long rmsId) {
		RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(rmsId, Status.ACTIVE);
		return rmsName;
	}

}

