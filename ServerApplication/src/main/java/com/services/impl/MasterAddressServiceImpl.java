package com.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.constants.LocationDependency;
import com.constants.PriceType;
import com.constants.Status;
import com.constants.ValueDependency;
import com.constants.WeightDependency;
import com.domain.*;
import com.repositories.ConfigRepository;
import com.repositories.ControlRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RateTableRepository;
import com.repositories.RmsRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.ZoneAddressRepo;
import com.services.MasterAddressService;
import com.vo.*;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	@Autowired
	private RateTableRepository rateTableRepository;

	@Autowired
	private ConfigRepository configRepository;


	@Value("${reportexport.path}")
	private String exportfilepath;

	@Override
	public Zone saveZoneDetails(Zone zone, User user) {

		log.info("inside saveZoneDetails");
		zone.setCreatedBy(user);
		zone.setUpdatedBy(user);
		zone.setCreatedOn(new Date());
		zone.setUpdatedOn(new Date());
		zone.setStatus(Status.ACTIVE);
		zone.setZone(zone.getZone().trim());
		zoneAddressRepo.save(zone);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(zone.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("Zone Details Saved");
		return zone;
	}

	@Override
	public Zone updateZoneService(Long Zid, User user, String updateZone) {
		Optional<Zone> zoneList = zoneAddressRepo.findById(Zid);
		Zone selectedZone = zoneList.get();
		Zone newZoneEntry = new Zone();
		Date date = new Date();
		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByZoneId(Zid);
		if (selectedZone.getStatus() == Status.ACTIVE) {
			for (MasterAddress masterAddresses : masterAddressesList) {
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
			selectedZone.setUpdatedOn(date); // new date
			newZoneEntry.setCreatedBy(selectedZone.getCreatedBy());
			newZoneEntry.setUpdatedBy(user);
			selectedZone.setUpdatedBy(user);
			zoneAddressRepo.save(newZoneEntry);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newZoneEntry.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			log.debug("zone is updated successfully");
			return selectedZone;
		}
		return null;
	}

	@Override
	public Zone deleteZoneService(Long del_id, User user) {

		log.debug("Deleting Data Continue");
		Optional<Zone> zoneList = zoneAddressRepo.findById(del_id);
		Zone zone = zoneList.get();
		Date date = new Date();

		if (zone.getStatus() == Status.ACTIVE) {
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

			for (Division division : divisionList) {
				division.setStatus(Status.DISABLED);
				division.setUpdatedBy(user);
				division.setUpdatedOn(date);
				divisionAddressRepo.save(division);
			}

			for (District district : districtList) {
				district.setStatus(Status.DISABLED);
				district.setUpdatedBy(user);
				district.setUpdatedOn(date);
				districtAddressRepo.save(district);
			}

			for (Thana thana : thanaList) {
				thana.setStatus(Status.DISABLED);
				thana.setUpdatedBy(user);
				thana.setUpdatedOn(date);
				thanaAddressRepo.save(thana);
			}

			for (MasterAddress masterAddress : masterAddressesList) {
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
			return zone;
		}
		return null;

	}

	@Override
	public Boolean fetchExistingZone(String zone) {
		List<Zone> validZoneList = zoneAddressRepo.findByZoneContainingIgnoreCaseAndStatus(zone,
				Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < validZoneList.size(); i++) {
			if (validZoneList.get(i).getZone().equalsIgnoreCase(zone)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Division saveDivisionDetails(Division division, User user) {
		Date date = new Date();

		log.info("inside saveDivisionDetails");
		division.setCreatedBy(user);
		division.setUpdatedBy(user);
		division.setCreatedOn(date);
		division.setUpdatedOn(date);
		division.setStatus(Status.ACTIVE);
		division.setDivision(division.getDivision().trim());
		divisionAddressRepo.save(division);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(division.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("Division Details Saved");
		return division;
	}

	@Override
	public Division updateDivisionService(Division division, User user, Zone zone, String divisionName) {

		Date date = new Date();
		Division newDivision = new Division();
		List<MasterAddress> masterAddressesList = masterAddressRepository
				.findMasterAddressByDivisionId(division.getId());
		if (division.getStatus() == Status.ACTIVE) {
			for (MasterAddress masterAddresses : masterAddressesList) {
				masterAddresses.setDivision(divisionName);
				masterAddresses.setUpdatedBy(user);
				masterAddresses.setUpdatedOn(date);
			}
			newDivision.setCreatedOn(division.getCreatedOn());// old date
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
			return division;
		}
		return null;
	}

	@Override
	public Division updateOrDeleteDivision(Division division, User user) {
		Date date = new Date();

		if (division.getStatus() == Status.ACTIVE) {
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
			return division;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingDivision(Long id, String division) {
		List<Division> validDivisiontList = divisionAddressRepo
				.findDivisionByzone_idAndDivisionContainingIgnoreCaseAndStatus(id, division,
						Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < validDivisiontList.size(); i++) {
			if (validDivisiontList.get(i).getDivision().equalsIgnoreCase(division)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public District saveDistrict(District district, User user) {

		log.info("inside Save District Implementation");
		Date date = new Date();

		district.setCreatedBy(user);
		district.setUpdatedBy(user);
		district.setCreatedOn(date);
		district.setUpdatedOn(date);
		district.setDistrict(district.getDistrict().trim());
		district.setStatus(Status.ACTIVE);
		districtAddressRepo.save(district);
		log.info("Saved District Details Succesfully");
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(district.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		return district;
	}

	@Override
	public District updateDistrict(District lastDistrict, User user, Division division, String districtName) {

		log.info("inside Update District Implementation");
		Date date = new Date();

		// lastDistrict.setUpdatedOn(lastDistrict.getUpdatedOn());

		District newDistrict = new District();
		List<MasterAddress> masterAddressesList = masterAddressRepository
				.findMasterAddressByDistrictId(lastDistrict.getId());
		if (lastDistrict.getStatus() == Status.ACTIVE) {
			for (MasterAddress masterAddresses : masterAddressesList) {
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
			lastDistrict.setUpdatedBy(user);
			newDistrict.setUpdatedBy(user);
			districtAddressRepo.save(newDistrict);
			log.info("Update District Details Succesfully");
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newDistrict.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return lastDistrict;
		}
		return null;
	}

	@Override
	public District DistrictDelete(District DistrictData, User user) {

		log.info("inside District Delete Implementation");
		Date date = new Date();

		if (DistrictData.getStatus() == Status.ACTIVE) {
			DistrictData.setStatus(Status.DISABLED);
			DistrictData.setUpdatedBy(user);
			DistrictData.setUpdatedOn(date);
			List<Thana> thanaList = (List<Thana>) thanaAddressRepo
					.findThanaBydistrict_idAndStatusOrderByThanaAsc(DistrictData.getId(), Status.ACTIVE);
			for (Thana objThana : thanaList) {
				objThana.setStatus(Status.DISABLED);
				objThana.setUpdatedBy(user);
				objThana.setUpdatedOn(date);
				List<MasterAddress> MasterAddressList = (List<MasterAddress>) masterAddressRepository
						.findMasterAddressByThanaAndStatus(objThana.getThana(), Status.ACTIVE);
				for (MasterAddress objMaster : MasterAddressList) {
					objMaster.setStatus(Status.DISABLED);
					objMaster.setUpdatedBy(user);
					objMaster.setUpdatedOn(date);
				}
			}
			districtAddressRepo.save(DistrictData);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(DistrictData.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			log.info("Delete District Details Succesfully");
			return DistrictData;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingDistrict(Long id, String district) {

		List<District> validDistrictList = districtAddressRepo
				.findDistrictBydivision_idAndDistrictContainingIgnoreCaseAndStatus(id, district,
						Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < validDistrictList.size(); i++) {
			if (validDistrictList.get(i).getDistrict().equalsIgnoreCase(district)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Thana saveThanaDetails(Thana thana, User user) {

		log.info("inside saveThanaDetails");
		Date date = new Date();

		thana.setCreatedBy(user);
		thana.setUpdatedBy(user);
		thana.setCreatedOn(date);
		thana.setUpdatedOn(date);
		thana.setThana(thana.getThana().trim());
		thana.setStatus(Status.ACTIVE);
		thanaAddressRepo.save(thana);
		log.info("Thana Details Saved");
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(thana.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		return thana;
	}

	@Override
	public Thana updateThanaDetails(Thana oldThana, User user, District district, String thanaName) {

		Thana newThana = new Thana();
		Date date = new Date();

		List<MasterAddress> masterAddressesList = masterAddressRepository.findMasterAddressByThanaId(oldThana.getId());
		if (oldThana.getStatus() == Status.ACTIVE) {
			for (MasterAddress masterAddresses : masterAddressesList) {
				masterAddresses.setThana(thanaName);
				masterAddresses.setUpdatedBy(user);
				masterAddresses.setUpdatedOn(date);
			}
			newThana.setCreatedOn(oldThana.getCreatedOn());// old data
			newThana.setDistrict(oldThana.getDistrict());// old data
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
			return oldThana;
		}
		return null;
	}

	@Override
	public Thana deleteThanaDetails(Thana oThana, User user) {
		Date date = new Date();

		if (oThana.getStatus() == Status.ACTIVE) {
			oThana.setStatus(Status.DISABLED);
			oThana.setUpdatedBy(user);
			oThana.setUpdatedOn(new Date());
			List<MasterAddress> subOfficeList = masterAddressRepository
					.findMasterAddressByThanaAndStatus(oThana.getThana(), Status.ACTIVE);
			for (MasterAddress subOffice : subOfficeList) {
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
			return oThana;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingThana(Long id, String thana) {
		List<Thana> validThanaList = thanaAddressRepo.findThanaBydistrict_idAndThanaContainingIgnoreCaseAndStatus(id,
				thana, Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < validThanaList.size(); i++) {
			if (validThanaList.get(i).getThana().equalsIgnoreCase(thana)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public MasterAddress saveSubOfficeDetails(MasterAddress masterAddress, Zone zone, Division division,
			District district, Thana thana, User user) {

		log.info("inside saveSubOfficeDetails");
		Date date = new Date();

		masterAddress.setCreatedBy(user);
		masterAddress.setUpdatedBy(user);
		masterAddress.setCreatedOn(date);
		masterAddress.setUpdatedOn(date);
		masterAddress.setZone(zone.getZone().trim());
		masterAddress.setZoneId(zone.getId());
		masterAddress.setDivision(division.getDivision().trim());
		masterAddress.setDivisionId(division.getId());
		masterAddress.setDistrict(district.getDistrict().trim());
		masterAddress.setDistrictId(district.getId());
		masterAddress.setThana(thana.getThana().trim());
		masterAddress.setThanaId(thana.getId());
		masterAddress.setStatus(Status.ACTIVE);
		masterAddressRepository.save(masterAddress);
		List<Control> controlList = controlRepository.findAll();
		Control control = controlList.get(0);
		Timestamp timestamp = new Timestamp(masterAddress.getUpdatedOn().getTime());
		control.setServerMasterDataUpdateTimestamp(timestamp);
		controlRepository.save(control);
		log.info("subOffice Details Saved");
		return masterAddress;
	}

	@Override
	public MasterAddress updateSubOfficeOrMasterAddressService(MasterAddress masterAddress, Zone zone,
			Division division, District district, Thana thana, User user, String subOfficeame, Integer postalCode) {
		MasterAddress newMasterAddress = new MasterAddress();
		Date date = new Date();

		if (masterAddress.getStatus() == Status.ACTIVE) {
			newMasterAddress.setCreatedOn(masterAddress.getCreatedOn());// old date
			masterAddress.setCreatedOn(masterAddress.getCreatedOn());// new data
			newMasterAddress.setStatus(Status.DISABLED);
			newMasterAddress.setUpdatedOn(date);// for new Updated date
			masterAddress.setUpdatedOn(date);// for old update date
			newMasterAddress.setSubOffice(masterAddress.getSubOffice().trim());// for New SubOffice
			masterAddress.setSubOffice(subOfficeame);// for Old SubOffice
			newMasterAddress.setCreatedBy(masterAddress.getCreatedBy());
			masterAddress.setUpdatedBy(user);// for old updateBy
			newMasterAddress.setUpdatedBy(user);// for new updated By
			newMasterAddress.setPostalCode(masterAddress.getPostalCode());
			masterAddress.setPostalCode(postalCode);

			newMasterAddress.setZone(masterAddress.getZone().trim());
			newMasterAddress.setZoneId(masterAddress.getZoneId());
			masterAddress.setZone(zone.getZone().trim());
			masterAddress.setZoneId(zone.getId());

			newMasterAddress.setDivision(masterAddress.getDivision().trim());
			newMasterAddress.setDivisionId(masterAddress.getDivisionId());
			masterAddress.setDivision(division.getDivision().trim());
			masterAddress.setDivisionId(division.getId());
			newMasterAddress.setDistrict(masterAddress.getDistrict().trim());
			newMasterAddress.setDistrictId(masterAddress.getDistrictId());
			masterAddress.setDistrict(district.getDistrict().trim());
			masterAddress.setDistrictId(district.getId());
			newMasterAddress.setThana(masterAddress.getThana().trim());
			newMasterAddress.setThanaId(masterAddress.getThanaId());
			masterAddress.setThana(thana.getThana().trim());
			masterAddress.setThanaId(thana.getId());
			masterAddressRepository.save(newMasterAddress);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newMasterAddress.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return masterAddress;
		}
		return null;
	}

	@Override
	public MasterAddress updateOrDeleteSubOfficeOrMasterAddress(MasterAddress masterAddress, User user) {
		Date date = new Date();

		if (masterAddress.getStatus() == Status.ACTIVE) {
			masterAddress.setStatus(Status.DISABLED);
			masterAddress.setUpdatedBy(user);
			masterAddress.setUpdatedOn(date);
			masterAddressRepository.save(masterAddress);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(masterAddress.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return masterAddress;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingSubOffice(String zone, String division, String district, String thana,
			String subOffice) {
		List<MasterAddress> validSubOfficeList = masterAddressRepository
				.findByZoneContainingIgnoreCaseAndDivisionContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndThanaContainingIgnoreCaseAndSubOfficeContainingIgnoreCaseAndStatus(
						zone, division, district, thana, subOffice, Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < validSubOfficeList.size(); i++) {
			if (validSubOfficeList.get(i).getSubOffice().equalsIgnoreCase(subOffice)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingPostalCode(int pinCode) {
		Boolean masterAddress = masterAddressRepository.existsByPostalCodeAndStatus(pinCode,
				Enum.valueOf(Status.class, "ACTIVE"));
		return masterAddress;
	}

	/*
	 * @Override public List<Zone> findZoneByZoneName(String zone, Status i) {
	 *
	 * List<Zone> validDistrictList = zoneAddressRepo.findZoneByZoneAndStatus(zone,
	 * Status.ACTIVE); for (Zone validDistrict : validDistrictList) { if (zone ==
	 * validDistrict.getZone()) { return null; } } return validDistrictList;
	 *
	 * }
	 *
	 * @Override public List<Division> findDivisionWithZoneId(Long id, String
	 * division, Status i) {
	 *
	 * List<Division> validDistrictList =
	 * divisionAddressRepo.findDivisionByzone_idAndDivisionAndStatus(id, division,
	 * Status.ACTIVE); for (Division validDistrict : validDistrictList) { if
	 * (division == validDistrict.getDivision()) { return null; } } return
	 * validDistrictList;
	 *
	 * }
	 *
	 * @Override public List<District> findDistricrWithDivisionId(Long divisionId,
	 * String district, Status ACTIVE) {
	 *
	 * List<District> validDistrictList = districtAddressRepo
	 * .findDistrictBydivisionId_idAndDistrictAndStatus(divisionId, district,
	 * Status.ACTIVE); for (District validDistrict : validDistrictList) { if
	 * (district == validDistrict.getDistrict()) {
	 *
	 * return null; } } return validDistrictList; }
	 *
	 * @Override public List<Thana> findThanaWithDistrictId(Long id, String thana,
	 * Status active) {
	 *
	 * List<Thana> validDistrictList =
	 * thanaAddressRepo.findThanaBydistrict_idAndThanaAndStatus(id, thana,
	 * Status.ACTIVE); for (Thana validDistrict : validDistrictList) { if (thana ==
	 * validDistrict.getThana()) { return null; } } return validDistrictList; }
	 *
	 * @Override public List<MasterAddress> findSubOfficeWithThana(String thana,
	 * String subOffice, Status active) {
	 *
	 * List<MasterAddress> validDistrictList = masterAddressRepository
	 * .findMasterAddressByThanaAndSubOfficeAndStatus(thana, subOffice,
	 * Status.ACTIVE);
	 *
	 * for (MasterAddress validDistrict : validDistrictList) { if (subOffice ==
	 * validDistrict.getSubOffice()) { return null; } } return validDistrictList; }
	 */
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
	public List<DivisionVo> fetchDivisionListByZone(Long id) {
		List<Division> divisionList = divisionAddressRepo.findDivisionByzone_idAndStatusOrderByDivisionAsc(id,
				Status.ACTIVE);
		List<DivisionVo> divisionVoList = new ArrayList<DivisionVo>();
		for (Division division : divisionList) {
			DivisionVo divisionVo = new DivisionVo();
			divisionVo.setId(division.getId());
			divisionVo.setDivision(division.getDivision());
			divisionVoList.add(divisionVo);
		}
		return divisionVoList;
	}

	@Override
	public List<DistrictVo> fetchDistrictListByDivision(Long id) {

		List<District> districtList = districtAddressRepo.findDistrictBydivision_idAndStatusOrderByDistrictAsc(id,
				Status.ACTIVE);
		List<DistrictVo> districtListVo = new ArrayList<DistrictVo>();
		for (District district : districtList) {
			DistrictVo districtVo = new DistrictVo();
			districtVo.setId(district.getId());
			districtVo.setDistrict(district.getDistrict());
			districtListVo.add(districtVo);
		}
		return districtListVo;
	}

	@Override
	public List<ThanaVo> fetchThanaListByDistrict(Long id) {

		List<Thana> thanaList = thanaAddressRepo.findThanaBydistrict_idAndStatusOrderByThanaAsc(id, Status.ACTIVE);
		List<ThanaVo> thanaListVo = new ArrayList<ThanaVo>();
		for (Thana thana : thanaList) {
			ThanaVo thanaVo = new ThanaVo();
			thanaVo.setId(thana.getId());
			thanaVo.setThana(thana.getThana());
			thanaListVo.add(thanaVo);
		}
		return thanaListVo;

	}

	// FOR RMS

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
	public RmsTable saveRmsDetails(RmsTable objRms, User user) {

		log.info("inside saveRMSDetails");
		objRms.setCreatedBy(user);
		objRms.setUpdatedBy(user);
		objRms.setCreatedOn(new Date());
		objRms.setUpdatedOn(new Date());
		objRms.setRmsName(objRms.getRmsName().trim());
		objRms.setStatus(Status.ACTIVE);
		rmsRepository.save(objRms);
		log.info("RMS Details saved");
		return objRms;
	}

	@Override
	public RmsTable updateButtonFilledData(Long updateId) {

		return rmsRepository.findById(updateId).orElse(null);
	}

	@Override
	public RmsTable updateRMS(Long RMSId, RmsTable formData, User user) {

		RmsTable oldData = rmsRepository.findById(RMSId).orElse(null);
		RmsTable newRMSObj = new RmsTable();
		Date date = new Date();
		if (oldData.getStatus() == Status.ACTIVE) {
			newRMSObj.setCreatedOn(oldData.getCreatedOn()); // old date
			oldData.setCreatedOn(oldData.getCreatedOn());// new date
			newRMSObj.setStatus(Status.DISABLED);
			newRMSObj.setUpdatedOn(date); // for new updated date
			oldData.setUpdatedOn(date); // for new update date

			newRMSObj.setCreatedBy(oldData.getCreatedBy());
			oldData.setUpdatedBy(user);
			newRMSObj.setUpdatedBy(user);

			newRMSObj.setRmsName(oldData.getRmsName());
			oldData.setRmsName(formData.getRmsName().trim());

			newRMSObj.setRmsType(oldData.getRmsType());
			oldData.setRmsType(formData.getRmsType());

			newRMSObj.setRmsAddress(oldData.getRmsAddress());
			oldData.setRmsAddress(formData.getRmsAddress());

			newRMSObj.setRmsMobileNumber(oldData.getRmsMobileNumber());
			oldData.setRmsMobileNumber(formData.getRmsMobileNumber());

			rmsRepository.save(newRMSObj);
			return oldData;

		}

		return null;
	}

	@Override
	public RmsTable DeleteRMS(Long del_id, User user) {
		log.info("Deleting data");
		RmsTable rmsList = rmsRepository.findById(del_id).orElse(null);
		if (rmsList.getStatus() == Status.ACTIVE) {
			rmsList.setStatus(Status.DISABLED);
			rmsList.setUpdatedBy(user);
			rmsList.setUpdatedOn(new Date());
			rmsRepository.save(rmsList);
			return rmsList;
		}
		return null;
	}

	@Override
	public Boolean getExistRmsData(String rmsName, String rmsType, int postalCode, Status active) {
		return rmsRepository.existsByRmsNameContainingIgnoreCaseAndRmsTypeAndRmsAddressPostalCodeAndStatus(rmsName,
				rmsType, postalCode, active);
	}

	@Override
	public MasterAddressVo getPostalData(int pincode) {
		MasterAddress p = masterAddressRepository.findMasterAddressByPostalCodeAndStatus(pincode, Status.ACTIVE);
		if (p != null) {
			MasterAddressVo masterAddrssVo = new MasterAddressVo();
			masterAddrssVo.setPostalCode(p.getPostalCode());
			masterAddrssVo.setSubOffice(p.getSubOffice());
			masterAddrssVo.setThana(p.getThana());
			return masterAddrssVo;
		} else
			return null;
	}

	@Override
	public List<Services> fetchServicesList() {
		return postalServiceRepository.findByParentServiceIdIsNullAndStatus(Status.ACTIVE);
	}

	@Override
	public Boolean fetchExistingServices(String service, String serviceCode) {
		List<Services> serviceList = postalServiceRepository
				.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(service,
						Status.ACTIVE, serviceCode, Status.ACTIVE);
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).getServiceName().equalsIgnoreCase(service)
					|| serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingServicesForUpdate(String service, String serviceCode) {
		List<Services> serviceList = postalServiceRepository
				.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(service,
						Status.ACTIVE, serviceCode, Status.ACTIVE);
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).getServiceName().equalsIgnoreCase(service)
					&& serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Services addServices(String service, String serviceCode, User user) {
		Date date = new Date();
		Services services = new Services();
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
		return services;
	}

	@Override
	public Services updateServices(Long serviceId, String service, String serviceCode, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		Services newServices = new Services();
		Date date = new Date();
		if (serviceData.getStatus() == Status.ACTIVE) {

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

			postalServiceRepository.save(newServices);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newServices.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return serviceData;
		}
		return null;
	}

	@Override
	public Boolean fetchExistingSubServices(Long pSId, String subServiceName, String serviceCode) {

		List<Services> serviceList = postalServiceRepository
				.findByParentServiceIdAndStatusOrServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(
						pSId, Status.ACTIVE, subServiceName, Status.ACTIVE, serviceCode, Status.ACTIVE);
		for (int i = 0; i < serviceList.size(); i++) {
			if ((serviceList.get(i).getParentServiceId().equals(pSId)
					&& serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName)
					&& serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode))
					|| (serviceList.get(i).getParentServiceId().equals(pSId)
							&& serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName))
					|| (serviceList.get(i).getParentServiceId().equals(pSId)
							&& serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean fetchExistingSubServicesOnUpdate(Long serviceId, String subServiceName, String serviceCode) {

		List<Services> serviceList = postalServiceRepository
				.findByParentServiceIdAndServiceNameContainingIgnoreCaseAndServiceCodeContainingIgnoreCaseAndStatus(
						serviceId, subServiceName, serviceCode, Status.ACTIVE);
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).getServiceName().equalsIgnoreCase(subServiceName)
					&& serviceList.get(i).getServiceCode().equalsIgnoreCase(serviceCode)
					&& serviceList.get(i).getParentServiceId().equals(serviceId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Services addSubServices(Long parantServiceId, String subServiceName, String serviceCode, User user) {
		Date date = new Date();
		Services services = new Services();
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
		return services;
	}

	@Override
	public Services updateSubServices(Long pSId, Long serviceId, String subServiceName, String serviceCode, User user) {
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
		return serviceData;
	}

	@Override
	public Services deleteService(Long serviceId, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		List<Services> servicesList = postalServiceRepository.findByParentServiceIdAndStatus(serviceId, Status.ACTIVE);
		serviceData.setStatus(Status.DISABLED);
		serviceData.setUpdatedBy(user);
		serviceData.setUpdatedOn(new Date());
		for (Services servi : servicesList) {
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
		return serviceData;
	}

	@Override
	public Services onlyUpdateSubService(Long serviceId, User user) {

		Services serviceData = postalServiceRepository.findById(serviceId).orElse(null);
		if (serviceData.getStatus() == Status.ACTIVE) {
			serviceData.setStatus(Status.DISABLED);
			serviceData.setUpdatedBy(user);
			serviceData.setUpdatedOn(new Date());
			postalServiceRepository.save(serviceData);
		}
		return serviceData;
	}

	@Override
	public RmsTable findRMSByRMSId(Long rmsId) {
		RmsTable rmsName = rmsRepository.findRmsTableByIdAndStatus(rmsId, Status.ACTIVE);
		return rmsName;
	}

	@Override
	public List<ZoneVo> getAllZoneList() {
		List<Zone> zoneList = zoneAddressRepo.findAll();
		List<ZoneVo> zoneVoList = new ArrayList<ZoneVo>();
		for (Zone zone : zoneList) {
			ZoneVo zoneVo = new ZoneVo();
			zoneVo.setUpdatedBy(zone.getUpdatedBy().getName());
			zoneVo.setId(zone.getId());
			zoneVo.setStatus(zone.getStatus());
			zoneVo.setCreatedOn(zone.getCreatedOn());
			zoneVo.setUpdatedOn(zone.getUpdatedOn());
			zoneVo.setZone(zone.getZone());
			zoneVoList.add(zoneVo);
		}
		return zoneVoList;
	}

	@Override
	public List<ZoneVo> getAllZoneListByStatus(Status status) {

		List<Zone> zoneList = zoneAddressRepo.findAllByStatusOrderByZoneAsc(status);
		List<ZoneVo> zoneVoList = new ArrayList<ZoneVo>();
		for (Zone zone : zoneList) {
			ZoneVo zoneVo = new ZoneVo();
			zoneVo.setUpdatedBy(zone.getUpdatedBy().getName());
			zoneVo.setId(zone.getId());
			zoneVo.setStatus(zone.getStatus());
			zoneVo.setCreatedOn(zone.getCreatedOn());
			zoneVo.setUpdatedOn(zone.getUpdatedOn());
			zoneVo.setZone(zone.getZone());
			zoneVoList.add(zoneVo);
		}
		return zoneVoList;
	}

	@Override
	public List<DivisionVo> getAllDivisionList() {

		List<Division> divisionList = divisionAddressRepo.findAll();
		List<DivisionVo> divisionVoList = new ArrayList<DivisionVo>();
		for (Division division : divisionList) {
			DivisionVo divisionVo = new DivisionVo();
			divisionVo.setUpdatedBy(division.getUpdatedBy().getName());
			divisionVo.setZone(division.getZone().getId());
			divisionVo.setZoneName(division.getZone().getZone());
			divisionVo.setId(division.getId());
			divisionVo.setStatus(division.getStatus());
			divisionVo.setCreatedOn(division.getCreatedOn());
			divisionVo.setUpdatedOn(division.getUpdatedOn());
			divisionVo.setDivision(division.getDivision());
			divisionVoList.add(divisionVo);
		}
		return divisionVoList;
	}

	@Override
	public List<DivisionVo> getAllDivisionListByStatus(Status status) {
		List<Division> divisionList = divisionAddressRepo.findAllByStatusOrderByDivisionAsc(status);
		List<DivisionVo> divisionVoList = new ArrayList<DivisionVo>();
		for (Division division : divisionList) {
			DivisionVo divisionVo = new DivisionVo();
			divisionVo.setUpdatedBy(division.getUpdatedBy().getName());
			divisionVo.setZone(division.getZone().getId());
			divisionVo.setZoneName(division.getZone().getZone());
			divisionVo.setId(division.getId());
			divisionVo.setStatus(division.getStatus());
			divisionVo.setCreatedOn(division.getCreatedOn());
			divisionVo.setUpdatedOn(division.getUpdatedOn());
			divisionVo.setDivision(division.getDivision());
			divisionVoList.add(divisionVo);
		}
		return divisionVoList;
	}

	@Override
	public List<DistrictVo> getAllDistrictList() {
		List<District> districtList = districtAddressRepo.findAll();
		List<DistrictVo> districtVoList = new ArrayList<DistrictVo>();
		for (District district : districtList) {
			DistrictVo districtVo = new DistrictVo();
			districtVo.setUpdatedBy(district.getUpdatedBy().getName());
			districtVo.setDivision(district.getDivision().getId());
			districtVo.setDivisionName(district.getDivision().getDivision());
			districtVo.setId(district.getId());
			districtVo.setStatus(district.getStatus());
			districtVo.setCreatedOn(district.getCreatedOn());
			districtVo.setUpdatedOn(district.getUpdatedOn());
			districtVo.setDistrict(district.getDistrict());
			districtVoList.add(districtVo);
		}
		return districtVoList;
	}

	@Override
	public List<DistrictVo> getAllDistrictListByStatus(Status status) {
		List<District> districtList = districtAddressRepo.findAllByStatusOrderByDistrictAsc(status);
		List<DistrictVo> districtVoList = new ArrayList<DistrictVo>();
		for (District district : districtList) {
			DistrictVo districtVo = new DistrictVo();
			districtVo.setUpdatedBy(district.getUpdatedBy().getName());
			districtVo.setDivision(district.getDivision().getId());
			districtVo.setDivisionName(district.getDivision().getDivision());
			districtVo.setId(district.getId());
			districtVo.setStatus(district.getStatus());
			districtVo.setCreatedOn(district.getCreatedOn());
			districtVo.setUpdatedOn(district.getUpdatedOn());
			districtVo.setDistrict(district.getDistrict());
			districtVoList.add(districtVo);
		}
		return districtVoList;
	}

	@Override
	public List<ThanaVo> getAllThanaList() {
		List<Thana> thanaList = thanaAddressRepo.findAll();
		List<ThanaVo> thanaVoList = new ArrayList<ThanaVo>();
		for (Thana thana : thanaList) {
			ThanaVo thanaVo = new ThanaVo();
			thanaVo.setUpdatedBy(thana.getUpdatedBy().getName());
			thanaVo.setDistrict(thana.getDistrict().getId());
			thanaVo.setDistrictName(thana.getDistrict().getDistrict());
			thanaVo.setId(thana.getId());
			thanaVo.setStatus(thana.getStatus());
			thanaVo.setCreatedOn(thana.getCreatedOn());
			thanaVo.setUpdatedOn(thana.getUpdatedOn());
			thanaVo.setThana(thana.getThana());
			thanaVoList.add(thanaVo);
		}
		return thanaVoList;
	}

	@Override
	public List<ThanaVo> getAllThanaListByStatus(Status status) {
		List<Thana> thanaList = thanaAddressRepo.findAllByStatusOrderByThanaAsc(status);
		List<ThanaVo> thanaVoList = new ArrayList<ThanaVo>();
		for (Thana thana : thanaList) {
			ThanaVo thanaVo = new ThanaVo();
			thanaVo.setUpdatedBy(thana.getUpdatedBy().getName());
			thanaVo.setDistrict(thana.getDistrict().getId());
			thanaVo.setDistrictName(thana.getDistrict().getDistrict());
			thanaVo.setId(thana.getId());
			thanaVo.setStatus(thana.getStatus());
			thanaVo.setCreatedOn(thana.getCreatedOn());
			thanaVo.setUpdatedOn(thana.getUpdatedOn());
			thanaVo.setThana(thana.getThana());
			thanaVoList.add(thanaVo);
		}
		return thanaVoList;
	}

	@Override
	public List<MasterAddressVo> getAllMasterAddressList() {
		List<MasterAddress> masterAddressesList = masterAddressRepository.findAll();
		List<MasterAddressVo> masterAddressesVoList = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressesList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setId(masterAddress.getId());
			masterAddressVo.setZoneId(masterAddress.getZoneId());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivisionId(masterAddress.getDivisionId());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrictId(masterAddress.getDistrictId());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThanaId(masterAddress.getThanaId());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressVo.setUpdatedBy(masterAddress.getUpdatedBy().getName());
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setStatus(masterAddress.getStatus());
			masterAddressVo.setUpdatedOn(masterAddress.getUpdatedOn());
			masterAddressesVoList.add(masterAddressVo);
		}
		return masterAddressesVoList;
	}

	@Override
	public List<MasterAddressVo> getAllMasterAddressListByStatus(Status status) {
		List<MasterAddress> masterAddressesList = masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(status);
		List<MasterAddressVo> masterAddressesVoList = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressesList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setId(masterAddress.getId());
			masterAddressVo.setZoneId(masterAddress.getZoneId());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivisionId(masterAddress.getDivisionId());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrictId(masterAddress.getDistrictId());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThanaId(masterAddress.getThanaId());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressVo.setUpdatedBy(masterAddress.getUpdatedBy().getName());
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setStatus(masterAddress.getStatus());
			masterAddressVo.setUpdatedOn(masterAddress.getUpdatedOn());
			masterAddressesVoList.add(masterAddressVo);
		}
		return masterAddressesVoList;
	}

	@Override
	public List<RmsTableVo> getAllRMSList() {
		List<RmsTable> rmsTableList = rmsRepository.findAll();
		List<RmsTableVo> rmsTableVoList = new ArrayList<RmsTableVo>();
		for (RmsTable rmsTable : rmsTableList) {
			RmsTableVo rmsTableVo = new RmsTableVo();
			rmsTableVo.setId(rmsTable.getId());
			rmsTableVo.setStatus(rmsTable.getStatus());
			rmsTableVo.setRmsName(rmsTable.getRmsName());
			rmsTableVo.setRmsType(rmsTable.getRmsType());
			rmsTableVo.setUpdatedOn(rmsTable.getUpdatedOn());
			rmsTableVo.setCountry(rmsTable.getRmsAddress().getCountry());
			rmsTableVo.setPostalCode(rmsTable.getRmsAddress().getPostalCode());
			rmsTableVo.setZone(rmsTable.getRmsAddress().getZone());
			rmsTableVo.setDivision(rmsTable.getRmsAddress().getDivision());
			rmsTableVo.setDistrict(rmsTable.getRmsAddress().getDistrict());
			rmsTableVo.setThana(rmsTable.getRmsAddress().getThana());
			rmsTableVo.setSubOffice(rmsTable.getRmsAddress().getSubOffice());
			rmsTableVo.setAddressLine1(rmsTable.getRmsAddress().getAddressLine1());
			rmsTableVo.setAddressLine2(rmsTable.getRmsAddress().getAddressLine2());
			rmsTableVo.setLandmark(rmsTable.getRmsAddress().getLandmark());
			rmsTableVo.setRmsMobileNumber(rmsTable.getRmsMobileNumber());
			rmsTableVoList.add(rmsTableVo);
		}
		return rmsTableVoList;
	}

	@Override
	public List<RmsTableVo> getAllRMSListByStatus(Status status) {
		List<RmsTable> rmsTableList = rmsRepository.findAllByStatus(status);
		List<RmsTableVo> rmsTableVoList = new ArrayList<RmsTableVo>();
		for (RmsTable rmsTable : rmsTableList) {
			RmsTableVo rmsTableVo = new RmsTableVo();
			rmsTableVo.setId(rmsTable.getId());
			rmsTableVo.setStatus(rmsTable.getStatus());
			rmsTableVo.setRmsName(rmsTable.getRmsName());
			rmsTableVo.setRmsType(rmsTable.getRmsType());
			rmsTableVo.setUpdatedOn(rmsTable.getUpdatedOn());
			rmsTableVo.setCountry(rmsTable.getRmsAddress().getCountry());
			rmsTableVo.setPostalCode(rmsTable.getRmsAddress().getPostalCode());
			rmsTableVo.setZone(rmsTable.getRmsAddress().getZone());
			rmsTableVo.setDivision(rmsTable.getRmsAddress().getDivision());
			rmsTableVo.setDistrict(rmsTable.getRmsAddress().getDistrict());
			rmsTableVo.setThana(rmsTable.getRmsAddress().getThana());
			rmsTableVo.setSubOffice(rmsTable.getRmsAddress().getSubOffice());
			rmsTableVo.setAddressLine1(rmsTable.getRmsAddress().getAddressLine1());
			rmsTableVo.setAddressLine2(rmsTable.getRmsAddress().getAddressLine2());
			rmsTableVo.setLandmark(rmsTable.getRmsAddress().getLandmark());
			rmsTableVo.setRmsMobileNumber(rmsTable.getRmsMobileNumber());
			rmsTableVoList.add(rmsTableVo);
		}
		return rmsTableVoList;
	}

	@Override
	public List<ServicesVo> getAllServicesList() {

		List<Services> servicesList = postalServiceRepository.findAll();
		if (servicesList != null) {
			List<ServicesVo> servicesListVo = new ArrayList<ServicesVo>();
			for (Services services : servicesList) {
				ServicesVo servicesVo = new ServicesVo();
				servicesVo.setId(services.getId());
				servicesVo.setParentServiceId(services.getParentServiceId());
				if (services.getParentServiceId() == null) {
					servicesVo.setServiceName(services.getServiceName());
					servicesVo.setSubService("-");
				} else {
					Long psId = services.getParentServiceId();
					Services s = postalServiceRepository.findById(psId).orElse(null);
					servicesVo.setServiceName(s.getServiceName());
					servicesVo.setSubService(services.getServiceName());
				}
				servicesVo.setServiceCode(services.getServiceCode());
				servicesVo.setStatus(services.getStatus());
				servicesVo.setUpdatedBy(services.getUpdatedBy().getName());
				servicesVo.setUpdatedOn(services.getUpdatedOn());
				servicesListVo.add(servicesVo);
			}
			return servicesListVo;
		}
		return null;
	}

	@Override
	public List<ServicesVo> getAllServicesListByStatus(Status status) {

		List<Services> servicesList = postalServiceRepository.findAllByStatusOrderByServiceNameAsc(status);
		if (servicesList != null) {
			List<ServicesVo> servicesListVo = new ArrayList<ServicesVo>();
			for (Services services : servicesList) {
				ServicesVo servicesVo = new ServicesVo();
				servicesVo.setId(services.getId());
				servicesVo.setParentServiceId(services.getParentServiceId());
				if (services.getParentServiceId() == null) {
					servicesVo.setServiceName(services.getServiceName());
					servicesVo.setSubService("-");
				} else {
					Long psId = services.getParentServiceId();
					Services s = postalServiceRepository.findById(psId).orElse(null);
					servicesVo.setServiceName(s.getServiceName());
					servicesVo.setSubService(services.getServiceName());
				}
				servicesVo.setServiceCode(services.getServiceCode());
				servicesVo.setStatus(services.getStatus());
				servicesVo.setUpdatedBy(services.getUpdatedBy().getName());
				servicesVo.setUpdatedOn(services.getUpdatedOn());
				servicesListVo.add(servicesVo);
			}
			return servicesListVo;
		}
		return null;
	}

	//For rate table
		@Override
		 public List<MainServiceVo> getAllMainServicesForDroupDownList() {
			 List<Services> servicesList = postalServiceRepository.findByParentServiceIdIsNullAndStatusOrderByServiceNameAsc(Status.ACTIVE);

			 if(servicesList != null) {
				 List<MainServiceVo> servicesListVo = new ArrayList<MainServiceVo>();
				 for (Services services : servicesList) {
					 MainServiceVo	 servicesVo = new MainServiceVo();
					 servicesVo.setId(services.getId());
					 servicesVo.setServiceName(services.getServiceName());
					 servicesVo.setParentServiceId(services.getParentServiceId());
					 servicesListVo.add(servicesVo);
				 }
				 return servicesListVo;
			}
		return null;
		}

		@Override
		public List<SubServiceVo> getAllSubServices() {

			List<Services> subServicesList = postalServiceRepository.findByParentServiceIdIsNotNullAndStatusOrderByServiceNameAsc(Status.ACTIVE);
			List<SubServiceVo> subServiceVoList = new ArrayList<SubServiceVo>();
			if(subServicesList != null) {
				for(Services subServices : subServicesList) {
					SubServiceVo subServiceVo = new SubServiceVo();
					subServiceVo.setSubServiceId(subServices.getId());
					subServiceVo.setSubServiceName(subServices.getServiceName());
					subServiceVo.setParentServiceId(subServices.getParentServiceId());
					subServiceVoList.add(subServiceVo);
				}
				return subServiceVoList;
			}
			return null;
		}

		@Override
		public List<SubServiceVo> fetchSubServicesByServiceId(Long id) {

			List<Services>subServicesList =postalServiceRepository.findByParentServiceIdAndStatusOrderByServiceNameAsc(id,Status.ACTIVE);
			List<SubServiceVo> subServiceVoList = new ArrayList<SubServiceVo>();
			for (Services subServices : subServicesList) {
				SubServiceVo subServiceVo = new SubServiceVo();
				subServiceVo.setSubServiceId(subServices.getId());
				subServiceVo.setSubServiceName(subServices.getServiceName());
				subServiceVo.setParentServiceId(subServices.getParentServiceId());
				subServiceVoList.add(subServiceVo);
			}
			return subServiceVoList;
		}

		@Override
		public ServicesVo fetchServiceBySubServicesId(Long id) {

			Services services = postalServiceRepository.findByIdAndStatusOrderByServiceNameAsc(id,Status.ACTIVE);
			Long mainServiceId = services.getParentServiceId();
			Services mainServices = postalServiceRepository.findByIdAndStatus(mainServiceId, Status.ACTIVE);
			ServicesVo serviceVo = new ServicesVo();
			  serviceVo.setId(mainServices.getId());
			  serviceVo.setServiceName(mainServices.getServiceName());
			  serviceVo.setParentServiceId(mainServices.getParentServiceId());
			return serviceVo;
		}

		@Override
		public List<DivisionVo> getAllDivisionForRateMaster() {

			List<Division> divisionList = divisionAddressRepo.findAllByStatusOrderByDivisionAsc(Status.ACTIVE);
			List<DivisionVo> divisionListVo = new ArrayList<DivisionVo>();
			for(Division division : divisionList) {
				DivisionVo divisionVo = new DivisionVo();
				divisionVo.setId(division.getId());
				divisionVo.setDivision(division.getDivision());
				divisionListVo.add(divisionVo);
			}
			return divisionListVo;
		}

		@Override
		public List<DistrictVo> getAllDistrictForRateMaster() {

			List<District> districtList = districtAddressRepo.findAllByStatusOrderByDistrictAsc(Status.ACTIVE);
			List<DistrictVo> districtListVo = new ArrayList<DistrictVo>();
			for(District district : districtList) {
				DistrictVo districtVo = new DistrictVo();
				districtVo.setId(district.getId());
				districtVo.setDistrict(district.getDistrict());
				districtListVo.add(districtVo);
			}
			return districtListVo;
		}

		@Override
		public List<ThanaVo> getAllThanaForRateMaster() {

			List<Thana> thanaList = thanaAddressRepo.findAllByStatusOrderByThanaAsc(Status.ACTIVE);
			List<ThanaVo> thanaListVo = new ArrayList<ThanaVo>();
			for(Thana thana : thanaList) {
				ThanaVo thanaVo = new ThanaVo();
				thanaVo.setId(thana.getId());
				thanaVo.setThana(thana.getThana());
				thanaListVo.add(thanaVo);
			}
			return thanaListVo;
		}

		@Override
		public List<SubOfficeVo> getAllSubOfficeForRateMaster() {

			List<MasterAddress> subOfficeList = masterAddressRepository.findAllByStatusOrderBySubOfficeAsc(Status.ACTIVE);
			List<SubOfficeVo> subOfficeListVo = new ArrayList<SubOfficeVo>();
			for(MasterAddress masterAddress : subOfficeList) {
				SubOfficeVo subOfficeVo = new SubOfficeVo();
				subOfficeVo.setId(masterAddress.getId());
				subOfficeVo.setSubOffice(masterAddress.getSubOffice());
				subOfficeVo.setPostalCode(masterAddress.getPostalCode());
				subOfficeListVo.add(subOfficeVo);
			}
			return subOfficeListVo;
		}

		@Override
		public List<RateTableVo> getAllRateTableDataList() {

			List<RateTable> rateTableList = rateTableRepository.findAll();
			if (rateTableList != null) {
				List<RateTableVo> rateTableListVo = new ArrayList<RateTableVo>();
				for (RateTable rateTable : rateTableList) {
					RateTableVo rateTableVo = new RateTableVo();
					rateTableVo.setId(rateTable.getId());

					if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

						Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
						rateTableVo.setMainServiceId(s1.getId());
						rateTableVo.setServiceName(s1.getServiceName());
						rateTableVo.setSubService("-");
					} else {
						Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
						rateTableVo.setMainServiceId(s2.getId());
						rateTableVo.setServiceName(s2.getServiceName());
						Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
						rateTableVo.setSubServiceId(s3.getId());
						rateTableVo.setSubService(s3.getServiceName());
					}
					rateTableVo.setPriceType(rateTable.getRateServiceCategory().getPriceType());
					rateTableVo.setWeightDependency(rateTable.getRateServiceCategory().getWeightDependency());
					rateTableVo.setLocationDependency(rateTable.getRateServiceCategory().getLocationDependency());
					rateTableVo.setValueDependency(rateTable.getRateServiceCategory().getValueDependency());
					rateTableVo.setWeightMaxLimit(rateTable.getRateServiceCategory().getWeightMaxLimit());
					rateTableVo.setValueMaxLimit(rateTable.getRateServiceCategory().getValueMaxLimit());

					if(rateTable.getRateServiceCategory().getPriceType().equals(PriceType.FLAT)) {
						rateTableVo.setPrice(rateTable.getRateServiceCategory().getPrice());
					}
					else if(!rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.NOT_APPLICABLE)) {
						rateTableVo.setWeightPrice(rateTable.getWeightWiseRate().getPrice());
						rateTableVo.setWeightBasePrice(rateTable.getWeightWiseRate().getBasePrice());
						rateTableVo.setWeightStartRange(rateTable.getWeightWiseRate().getWeightStartRange());
						rateTableVo.setWeightEndRange(rateTable.getWeightWiseRate().getWeightEndRange());
						rateTableVo.setWeightFractionFactor(rateTable.getWeightWiseRate().getWeightFractionFactor());
					}
					else if(!rateTable.getRateServiceCategory().getLocationDependency().equals(LocationDependency.NOT_APPLICABLE)) {
						rateTableVo.setLocationPrice(rateTable.getLocationWiseRate().getPrice());
						rateTableVo.setLocationBasePrice(rateTable.getLocationWiseRate().getBasePrice());
						rateTableVo.setFromId(rateTable.getLocationWiseRate().getFromId());
						rateTableVo.setToId(rateTable.getLocationWiseRate().getToId());
					}
					else if(!rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.NOT_APPLICABLE)) {
						rateTableVo.setValuePrice(rateTable.getParcelValueWiseRate().getPrice());
						rateTableVo.setValueBasePrice(rateTable.getParcelValueWiseRate().getBasePrice());
						rateTableVo.setValueStartRange(rateTable.getParcelValueWiseRate().getValueStartRange());
						rateTableVo.setValueUpToRange(rateTable.getParcelValueWiseRate().getValueUpToRange());
						rateTableVo.setValueFraction(rateTable.getParcelValueWiseRate().getValueFraction());
					}

					rateTableVo.setStatus(rateTable.getStatus());
					rateTableVo.setUpdatedBy(rateTable.getUpdatedBy().getName());
					rateTableVo.setUpdatedOn(rateTable.getUpdatedOn());
					rateTableVo.setTaxRate1(rateTable.getRateServiceCategory().getTaxRate1());
					rateTableVo.setTaxRate2(rateTable.getRateServiceCategory().getTaxRate2());
					rateTableVo.setExpectedDelivery(rateTable.getRateServiceCategory().getExpectedDelivery());
					rateTableListVo.add(rateTableVo);
				}
				return rateTableListVo;
			}
			return null;
		}

		@Override
		public List<RateTableVo> getAllRateTableDataByStatus(Status status) {
			List<RateTable> rateTableList = rateTableRepository.findRateTableByStatus(status);
			if (rateTableList != null) {
				List<RateTableVo> rateTableListVo = new ArrayList<RateTableVo>();
				for (RateTable rateTable : rateTableList) {
					RateTableVo rateTableVo = new RateTableVo();
					rateTableVo.setId(rateTable.getId());

					if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

						Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
						rateTableVo.setMainServiceId(s1.getId());
						rateTableVo.setServiceName(s1.getServiceName());
						rateTableVo.setSubService("-");
					} else {
						Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
						rateTableVo.setMainServiceId(s2.getId());
						rateTableVo.setServiceName(s2.getServiceName());
						Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
						rateTableVo.setSubServiceId(s3.getId());
						rateTableVo.setSubService(s3.getServiceName());
					}
					rateTableVo.setPriceType(rateTable.getRateServiceCategory().getPriceType());
					rateTableVo.setWeightDependency(rateTable.getRateServiceCategory().getWeightDependency());
					rateTableVo.setLocationDependency(rateTable.getRateServiceCategory().getLocationDependency());
					rateTableVo.setValueDependency(rateTable.getRateServiceCategory().getValueDependency());
					rateTableVo.setWeightMaxLimit(rateTable.getRateServiceCategory().getWeightMaxLimit());
					rateTableVo.setValueMaxLimit(rateTable.getRateServiceCategory().getValueMaxLimit());

					if(rateTable.getRateServiceCategory().getPriceType().equals(PriceType.FLAT)) {
						rateTableVo.setPrice(rateTable.getRateServiceCategory().getPrice());
					}
					else if(!rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.NOT_APPLICABLE)) {
						rateTableVo.setWeightPrice(rateTable.getWeightWiseRate().getPrice());
						rateTableVo.setWeightBasePrice(rateTable.getWeightWiseRate().getBasePrice());
						rateTableVo.setWeightStartRange(rateTable.getWeightWiseRate().getWeightStartRange());
						rateTableVo.setWeightEndRange(rateTable.getWeightWiseRate().getWeightEndRange());
						rateTableVo.setWeightFractionFactor(rateTable.getWeightWiseRate().getWeightFractionFactor());
					}
					else if(!rateTable.getRateServiceCategory().getLocationDependency().equals(LocationDependency.NOT_APPLICABLE)) {
						rateTableVo.setLocationPrice(rateTable.getLocationWiseRate().getPrice());
						rateTableVo.setLocationBasePrice(rateTable.getLocationWiseRate().getBasePrice());
						rateTableVo.setFromId(rateTable.getLocationWiseRate().getFromId());
						rateTableVo.setToId(rateTable.getLocationWiseRate().getToId());
					}
					else if(!rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.NOT_APPLICABLE)) {
						rateTableVo.setValuePrice(rateTable.getParcelValueWiseRate().getPrice());
						rateTableVo.setValueBasePrice(rateTable.getParcelValueWiseRate().getBasePrice());
						rateTableVo.setValueStartRange(rateTable.getParcelValueWiseRate().getValueStartRange());
						rateTableVo.setValueUpToRange(rateTable.getParcelValueWiseRate().getValueUpToRange());
						rateTableVo.setValueFraction(rateTable.getParcelValueWiseRate().getValueFraction());
					}

					rateTableVo.setStatus(rateTable.getStatus());
					rateTableVo.setUpdatedBy(rateTable.getUpdatedBy().getName());
					rateTableVo.setUpdatedOn(rateTable.getUpdatedOn());
					rateTableVo.setTaxRate1(rateTable.getRateServiceCategory().getTaxRate1());
					rateTableVo.setTaxRate2(rateTable.getRateServiceCategory().getTaxRate2());
					rateTableVo.setExpectedDelivery(rateTable.getRateServiceCategory().getExpectedDelivery());
					rateTableListVo.add(rateTableVo);
				}
				return rateTableListVo;
			}
			return null;
		}

		@Override
		public RateTable saveRateTableData(RateTableVo rateTableJson, User user) {

			log.info("inside save rate Details");
			RateTable rateTable = new RateTable();

			rateTable.setCreatedBy(user);
			rateTable.setUpdatedBy(user);
			rateTable.setCreatedOn(new Date());
			rateTable.setUpdatedOn(new Date());
			rateTable.setStatus(Status.ACTIVE);
			RateServiceCategory rateServiceCategory = new RateServiceCategory();

			//Rate service category
			if(rateTableJson.getParentServiceId() != null) {
				Services services = postalServiceRepository.findByIdAndStatus(rateTableJson.getParentServiceId(), Status.ACTIVE);
				rateServiceCategory.setParentServiceId(services.getParentServiceId());
				rateServiceCategory.setServiceId(services.getId());

			}
			else {
				rateServiceCategory.setServiceId(rateTableJson.getServiceId());
				rateServiceCategory.setParentServiceId(null);
			}
			rateServiceCategory.setPriceType(rateTableJson.getPriceType());
			rateServiceCategory.setLocationDependency(rateTableJson.getLocationDependency());
			rateServiceCategory.setWeightDependency(rateTableJson.getWeightDependency());
			rateServiceCategory.setValueDependency(rateTableJson.getValueDependency());
			rateServiceCategory.setWeightMaxLimit(rateTableJson.getWeightMaxLimit());
			rateServiceCategory.setValueMaxLimit(rateTableJson.getValueMaxLimit());
			rateServiceCategory.setPrice(rateTableJson.getPrice());
			rateServiceCategory.setTaxRate1(rateTableJson.getTaxRate1());
			rateServiceCategory.setTaxRate2(rateTableJson.getTaxRate2());
			rateServiceCategory.setExpectedDelivery(rateTableJson.getExpectedDelivery());
			rateTable.setRateServiceCategory(rateServiceCategory);

			WeightWiseRate weightWiseRate = new WeightWiseRate();
			LocationWiseRate locationWiseRate = new LocationWiseRate();
			ParcelValueWiseRate parcelValueWiseRate = new ParcelValueWiseRate();

			if(rateTableJson.getPriceType().equals(PriceType.FLAT)) {
				log.info("inside save FLAT rate Details");
				rateTable.setWeightWiseRate(null);
				rateTable.setLocationWiseRate(null);
				rateTable.setParcelValueWiseRate(null);
			}
			else {
				log.info("inside save Variable rate Details");
				//for weight dependency
				if(rateTableJson.getWeightDependency().equals(WeightDependency.NOT_APPLICABLE)) {
					rateTable.setWeightWiseRate(null);
				} else {
					weightWiseRate.setWeightStartRange(rateTableJson.getWeightStartRange());
					weightWiseRate.setWeightEndRange(rateTableJson.getWeightEndRange());
					weightWiseRate.setWeightFractionFactor(rateTableJson.getWeightFractionFactor());
					weightWiseRate.setBasePrice(rateTableJson.getWeightBasePrice());
					weightWiseRate.setPrice(rateTableJson.getWeightPrice());
					rateTable.setWeightWiseRate(weightWiseRate);
				}

				//for location dependency
				if(rateTableJson.getLocationDependency().equals(LocationDependency.NOT_APPLICABLE)) {
					rateTable.setLocationWiseRate(null);
				}
				else {
					locationWiseRate.setFromId(rateTableJson.getFromId());
					locationWiseRate.setToId(rateTableJson.getToId());
					locationWiseRate.setPrice(rateTableJson.getLocationPrice());
					rateTable.setLocationWiseRate(locationWiseRate);
				}

				//for value dependency
				if(rateTableJson.getValueDependency().equals(ValueDependency.NOT_APPLICABLE)){
					rateTable.setParcelValueWiseRate(null);
				}
				else {
					parcelValueWiseRate.setValueStartRange(rateTableJson.getValueStartRange());
					parcelValueWiseRate.setValueUpToRange(rateTableJson.getValueUpToRange());
					parcelValueWiseRate.setValueFraction(rateTableJson.getValueFraction());
					parcelValueWiseRate.setBasePrice(rateTableJson.getValueBasePrice());
					parcelValueWiseRate.setPrice(rateTableJson.getValuePrice());
					rateTable.setParcelValueWiseRate(parcelValueWiseRate);
				}
			}

			rateTableRepository.save(rateTable);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(rateTable.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			log.info("Rates Details Saved ");
			return rateTable;
		}

		@Override
		public RateTable updateRateTable(RateTableVo rateTableJson, User user) {

			RateTable oldRateTable = rateTableRepository.findById(rateTableJson.getRateTableId()).orElse(null);
			Date date = new Date();

			//For new row
			RateTable newRateTable = new RateTable();

			newRateTable.setCreatedOn(oldRateTable.getCreatedOn());
			newRateTable.setCreatedBy(oldRateTable.getCreatedBy());
			newRateTable.setUpdatedOn(date);
			newRateTable.setUpdatedBy(user);
			newRateTable.setStatus(Status.DISABLED);
			newRateTable.setRateServiceCategory(oldRateTable.getRateServiceCategory());
			newRateTable.setLocationWiseRate(oldRateTable.getLocationWiseRate());
			newRateTable.setWeightWiseRate(oldRateTable.getWeightWiseRate());
			newRateTable.setParcelValueWiseRate(oldRateTable.getParcelValueWiseRate());

			oldRateTable.setUpdatedOn(date);
			oldRateTable.setUpdatedBy(user);

			log.info("inside update rate Details");

			RateServiceCategory rateServiceCategory = new RateServiceCategory();

			//Rate service category
			if(rateTableJson.getParentServiceId() != null) {
				Services services = postalServiceRepository.findByIdAndStatus(rateTableJson.getParentServiceId(), Status.ACTIVE);
				rateServiceCategory.setParentServiceId(services.getParentServiceId());
				rateServiceCategory.setServiceId(services.getId());
			}
			else {
				rateServiceCategory.setServiceId(rateTableJson.getServiceId());
				rateServiceCategory.setParentServiceId(null);
			}
			rateServiceCategory.setPriceType(rateTableJson.getPriceType());
			rateServiceCategory.setLocationDependency(rateTableJson.getLocationDependency());
			rateServiceCategory.setWeightDependency(rateTableJson.getWeightDependency());
			rateServiceCategory.setValueDependency(rateTableJson.getValueDependency());
			rateServiceCategory.setWeightMaxLimit(rateTableJson.getWeightMaxLimit());
			rateServiceCategory.setValueMaxLimit(rateTableJson.getValueMaxLimit());
			rateServiceCategory.setPrice(rateTableJson.getPrice());
			rateServiceCategory.setTaxRate1(rateTableJson.getTaxRate1());
			rateServiceCategory.setTaxRate2(rateTableJson.getTaxRate2());
			rateServiceCategory.setExpectedDelivery(rateTableJson.getExpectedDelivery());
			oldRateTable.setRateServiceCategory(rateServiceCategory);

			WeightWiseRate weightWiseRate = new WeightWiseRate();
			LocationWiseRate locationWiseRate = new LocationWiseRate();
			ParcelValueWiseRate parcelValueWiseRate = new ParcelValueWiseRate();

			if(rateTableJson.getPriceType().equals(PriceType.FLAT)) {
				log.info("inside update FLAT rate Details");
				oldRateTable.setWeightWiseRate(null);
				oldRateTable.setLocationWiseRate(null);
				oldRateTable.setParcelValueWiseRate(null);
			}
			else {
				log.info("inside update Variable rate Details");
				//for weight dependency
				if(rateTableJson.getWeightDependency().equals(WeightDependency.NOT_APPLICABLE)) {
					oldRateTable.setWeightWiseRate(null);
				} else {
					weightWiseRate.setWeightStartRange(rateTableJson.getWeightStartRange());
					weightWiseRate.setWeightEndRange(rateTableJson.getWeightEndRange());
					weightWiseRate.setWeightFractionFactor(rateTableJson.getWeightFractionFactor());
					weightWiseRate.setBasePrice(rateTableJson.getWeightBasePrice());
					weightWiseRate.setPrice(rateTableJson.getWeightPrice());
					oldRateTable.setWeightWiseRate(weightWiseRate);
				}
				//for location dependency
				if(rateTableJson.getLocationDependency().equals(LocationDependency.NOT_APPLICABLE)) {
					oldRateTable.setLocationWiseRate(null);
				}
				else {
					locationWiseRate.setFromId(rateTableJson.getFromId());
					locationWiseRate.setToId(rateTableJson.getToId());
					locationWiseRate.setPrice(rateTableJson.getLocationPrice());
					oldRateTable.setLocationWiseRate(locationWiseRate);
				}
				//for value dependency
				if(rateTableJson.getValueDependency().equals(ValueDependency.NOT_APPLICABLE)){
					oldRateTable.setParcelValueWiseRate(null);
				}
				else {
					parcelValueWiseRate.setValueStartRange(rateTableJson.getValueStartRange());
					parcelValueWiseRate.setValueUpToRange(rateTableJson.getValueUpToRange());
					parcelValueWiseRate.setValueFraction(rateTableJson.getValueFraction());
					parcelValueWiseRate.setBasePrice(rateTableJson.getValueBasePrice());
					parcelValueWiseRate.setPrice(rateTableJson.getValuePrice());
					oldRateTable.setParcelValueWiseRate(parcelValueWiseRate);
				}
			}
			rateTableRepository.save(newRateTable);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(newRateTable.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return oldRateTable;

		}

		@Override
		public RateTable deleteRateData(Long id, User user) {

			RateTable rateTable = rateTableRepository.findById(id).orElse(null);
			rateTable.setUpdatedBy(user);
			rateTable.setUpdatedOn(new Date());
			rateTable.setStatus(Status.DISABLED);
			rateTableRepository.save(rateTable);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(rateTable.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			return rateTable;
		}

	@Override
	public void generateRateReportInPdf() {
		//TODO: 1. Location Dependency
		// 2. Location, value and weight Dependency
		// 3. Location and value Dependency
		// 4. Value and Weight Dependency
		// 5. All location dependency except local and intercity in location and weight dependency
		List<RateReportData> reportVos = new ArrayList<>();
		List<Long> serviceIds = postalServiceRepository.findDistinctServiceName();
		List<RateTable> rateTables = rateTableRepository.findByRateServiceCategoryServiceIdInAndStatus(serviceIds, Status.ACTIVE, Sort.by("RateServiceCategoryServiceId").ascending().and(Sort.by("id")));

		for (RateTable rateTable : rateTables) {
			RateReportData rateReportData = new RateReportData();

			//

			if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

				Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
				rateReportData.setServiceName(s1.getServiceName());

			} else {
				Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
				rateReportData.setServiceName(s2.getServiceName());

				Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
				rateReportData.setSubService(s3.getServiceName());
			}


			if(rateTable.getRateServiceCategory().getPriceType().equals(PriceType.FLAT)){

				if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

					Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
					rateReportData.setServiceName(s1.getServiceName());

				} else {
					Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
					rateReportData.setServiceName(s2.getServiceName());

					Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
					rateReportData.setSubService(s3.getServiceName());
				}

					rateReportData.setPrice(rateTable.getRateServiceCategory().getPrice());
			}
			else
			if(rateTable.getRateServiceCategory().getPriceType().equals(PriceType.VARIABLE)){

				if (rateTable.getRateServiceCategory().getParentServiceId() == null) {

					Services s1 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
					rateReportData.setServiceName(s1.getServiceName());

				} else {
					Services s2 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getParentServiceId()).orElse(null);
					rateReportData.setServiceName(s2.getServiceName());

					Services s3 = postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId()).orElse(null);
					rateReportData.setSubService(s3.getServiceName());
				}


				if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getWeightDependency()
						.equals(WeightDependency.NOT_APPLICABLE)) {

					log.info("Location, Weight dependency exist");

					if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.LOCAL)) {
						rateReportData.setServiceLocationDependency("LOCAL");
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.INTERCITY)) {
						rateReportData.setServiceLocationDependency("INTERCITY");
					}

					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						rateReportData.setServiceWeightDependency("Over "+rateTable.getWeightWiseRate().getWeightStartRange()+" gm not exceeding "+ rateTable.getWeightWiseRate().getWeightEndRange()+" gm");
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.MULTIPLIER)) {
						float basePrice = rateTable.getWeightWiseRate().getBasePrice();
						float price = rateTable.getWeightWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceWeightDependency("Up to "+rateTable.getWeightWiseRate().getWeightEndRange()+" gm");
							rateReportData.setPrice(price);
						} else {
							float weightFractionFactor = rateTable.getWeightWiseRate().getWeightFractionFactor();
							rateReportData.setServiceWeightDependency("Each " + weightFractionFactor + " gm in excess of " + rateTable.getWeightWiseRate().getWeightStartRange() + " gm up to " + rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
							rateReportData.setPrice(price);
						}
					}

				} else if (!rateTable.getRateServiceCategory().getWeightDependency()
						.equals(WeightDependency.NOT_APPLICABLE)){
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						rateReportData.setServiceWeightDependency("Over "+rateTable.getWeightWiseRate().getWeightStartRange()+" gm not exceeding "+ rateTable.getWeightWiseRate().getWeightEndRange()+" gm");
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.MULTIPLIER)) {
						float basePrice = rateTable.getWeightWiseRate().getBasePrice();
						float price = rateTable.getWeightWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceWeightDependency("Up to "+rateTable.getWeightWiseRate().getWeightEndRange()+"gm");
							rateReportData.setPrice(price);
						} else {
							float weightFractionFactor = rateTable.getWeightWiseRate().getWeightFractionFactor();
							rateReportData.setServiceWeightDependency("Each " + weightFractionFactor + " gm in excess of " + rateTable.getWeightWiseRate().getWeightStartRange() + " gm up to " + rateTable.getWeightWiseRate().getWeightEndRange() + " gm");
							rateReportData.setPrice(price);
						}
					}
				} else if (!rateTable.getRateServiceCategory().getValueDependency()
						.equals(ValueDependency.NOT_APPLICABLE)){
					log.info("Value dependency exist");
					if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						rateReportData.setServiceValueDependency("Over Rs."+rateTable.getParcelValueWiseRate().getValueStartRange()+" not exceeding Rs."+ rateTable.getParcelValueWiseRate().getValueUpToRange());
						rateReportData.setPrice(rateTable.getWeightWiseRate().getPrice());
					} else if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.MULTIPLIER)) {
						float basePrice = rateTable.getParcelValueWiseRate().getBasePrice();
						float price = rateTable.getParcelValueWiseRate().getPrice();

						if (basePrice == 0) {
							rateReportData.setServiceValueDependency("Up to Rs."+rateTable.getParcelValueWiseRate().getValueUpToRange());
							rateReportData.setPrice(price);
						} else {
							float valueFractionFactor = rateTable.getParcelValueWiseRate().getValueFraction();
							rateReportData.setServiceValueDependency("Each Rs." + valueFractionFactor + " in excess of Rs." + rateTable.getParcelValueWiseRate().getValueStartRange() + " up to Rs." + rateTable.getParcelValueWiseRate().getValueUpToRange());
							rateReportData.setPrice(price);
						}
					}
				} else if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)){
					log.info("Location dependency exist");

				}
			}
			reportVos.add(rateReportData);
		}

		try {
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(exportfilepath + "Rate_Table_Report.jrxml");
			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(reportVos);
			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("rateTableReportList", jrBeanCollectionDataSource);
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportfilepath + "Rate_Table_Report.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<ConfigVo> getAllConfigList() {

		List<ConfigVo> configVoDataList = new ArrayList<ConfigVo>();
		try {
			List<Config> configDataList = configRepository.findAll();

			for (Config config : configDataList) {
				ConfigVo configVo = new ConfigVo();
				if(config.getUpdatedBy()!=null) {
					configVo.setUpdatedBy(config.getUpdatedBy().getName());
				}
				configVo.setId(config.getId());
				configVo.setStatus(config.getStatus());
				configVo.setCreatedOn(config.getCreatedOn());
				configVo.setUpdatedOn(config.getUpdatedOn());
				configVo.setConfigType(config.getConfigType());
				configVo.setConfigValue(config.getConfigValue());
				configVoDataList.add(configVo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return configVoDataList;
	}

	@Override
	public List<ConfigVo> getAllConfigListByStatus(Status valueOf) {

		List<Config> configDataList = configRepository.findAllByStatusOrderByConfigTypeAsc(valueOf);
		List<ConfigVo> configVoDataList = new ArrayList<ConfigVo>();
		try {
			for (Config config : configDataList) {
				ConfigVo configVo = new ConfigVo();
				if(config.getUpdatedBy()!=null) {
					configVo.setUpdatedBy(config.getUpdatedBy().getName());
				}
				configVo.setId(config.getId());
				configVo.setStatus(config.getStatus());
				configVo.setCreatedOn(config.getCreatedOn());
				configVo.setUpdatedOn(config.getUpdatedOn());
				configVo.setConfigType(config.getConfigType());
				configVo.setConfigValue(config.getConfigValue());
				configVoDataList.add(configVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configVoDataList;

	}

	/*
	 * @Override public List<Config> fetchConfigTypeList() { return
	 * configRepository.findAllByStatusOrderByConfigTypeAsc(Status.ACTIVE);
	 *
	 * }
	 */

	@Override
	public Boolean fetchExistingConfig(String configType) {
		List<Config> configDataList = configRepository
				.findConfigByConfigTypeContainingIgnoreCaseAndStatus(configType,
						Enum.valueOf(Status.class, "ACTIVE"));
		for (int i = 0; i < configDataList.size(); i++) {
			if (configDataList.get(i).getConfigType().equalsIgnoreCase(configType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Config saveConfigDetails(Config objConfig, User loginedUser) {
		Date date = new Date();
		try {
			log.info("inside saveConfigDetails method");
			objConfig.setCreatedBy(loginedUser);
			objConfig.setUpdatedBy(loginedUser);
			objConfig.setCreatedOn(date);
			objConfig.setUpdatedOn(date);
			objConfig.setStatus(Status.ACTIVE);
			objConfig.setConfigType(objConfig.getConfigType());
			objConfig.setConfigValue(objConfig.getConfigValue());
			configRepository.save(objConfig);
			List<Control> controlList = controlRepository.findAll();
			Control control = controlList.get(0);
			Timestamp timestamp = new Timestamp(objConfig.getUpdatedOn().getTime());
			control.setServerMasterDataUpdateTimestamp(timestamp);
			controlRepository.save(control);
			log.info("Config Details Saved");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objConfig;

	}

	@Override
	public Config updateConfigDetails(Long id, User loginedUser, Config configObj) {
		Date date = new Date();

		 Optional<Config> configList = configRepository.findById(id);
		 Config configOldObj = configList.get();
		 Config newconfigObj = new Config();

		try {
					newconfigObj.setCreatedOn(configOldObj.getCreatedOn());// old date
					newconfigObj.setStatus(Status.DISABLED);
					newconfigObj.setUpdatedOn(date);// for new Updated date
					configOldObj.setUpdatedOn(date);// for old update date
					newconfigObj.setConfigType(configOldObj.getConfigType());//
					newconfigObj.setConfigValue(configOldObj.getConfigValue());
					configOldObj.setConfigValue(configObj.getConfigValue());// for Old configValue
					newconfigObj.setCreatedBy(configOldObj.getCreatedBy());
					configOldObj.setUpdatedBy(loginedUser);// for old updateBy
					newconfigObj.setUpdatedBy(loginedUser);// for new updated By
					configRepository.save(newconfigObj);
					List<Control> controlList = controlRepository.findAll();
					Control control = controlList.get(0);
					Timestamp timestamp = new Timestamp(newconfigObj.getUpdatedOn().getTime());
					control.setServerMasterDataUpdateTimestamp(timestamp);
					controlRepository.save(control);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return configOldObj;
	}

	@Override
	public Boolean fetchExistingConfig(Long id, Config objConfig) {
		 try {
			 Optional<Config> configList = configRepository.findById(id);
			 Config configOldObj = configList.get();
			if((configOldObj.getConfigType().equalsIgnoreCase(objConfig.getConfigType()))&&
					(configOldObj.getConfigValue().equalsIgnoreCase(objConfig.getConfigValue()))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			return false;
	}

	@Override
	public Config deleteConfigDetails(Config configOldObj, User loginedUser) {
		Date date = new Date();
		try {
			if (configOldObj.getStatus() == Status.ACTIVE) {
				configOldObj.setStatus(Status.DISABLED);
				configOldObj.setUpdatedBy(loginedUser);
				configOldObj.setUpdatedOn(date);
				configRepository.save(configOldObj);
				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
				Timestamp timestamp = new Timestamp(configOldObj.getUpdatedOn().getTime());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configOldObj;
	}

}
