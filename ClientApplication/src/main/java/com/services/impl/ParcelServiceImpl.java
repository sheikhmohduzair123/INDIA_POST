package com.services.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.constants.UpdatePayableAmnt;
import com.controllers.ParcelController;
import com.domain.Client;
import com.domain.ClientAccount;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RmsTable;
import com.domain.Services;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.repositories.ClientAccountRepository;
import com.repositories.ClientRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.services.ParcelService;
import com.vo.JasperVo;
import com.vo.MasterAddressVo;
import com.vo.ParcelVo;
import com.vo.RateCalculation;
import com.vo.ServicesVo;
import com.vo.TrackingVo;
import com.vo.UpdatePayableAmountVo;
import com.util.PbCube;
import com.vo.UserAddressVO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ParcelServiceImpl implements ParcelService {

	Logger log = LoggerFactory.getLogger(ParcelController.class);

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private ParcelRepository parcelRepository;

	/*
	 * @Autowired private PostOfficeRepository postOfficeRepository;
	 */
	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private TrackingCSRepository trackingCSRepo;

	@Autowired
	private RmsRepository rmsRepo;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private TrackingDetailsRepository trackingDetailsRepository;

	@Value("${postalCode}")
	private String postalCode;

	@Value("${reportexport.path}")
	private String exportfilepath;

	@Value("${status}")
	private String status;

	public List<Services> fetchPostalSerives() {
		List<Services> postalServices = postalServiceRepository.findByParentServiceIdNullAndStatus(Status.ACTIVE);
		return postalServices;
	}

	@Override
	public List<String> getParcelCountCollection(int postalCode) {
		List<Client> clientList = clientRepository.findByPostalCodeAndStatus(postalCode,
				Enum.valueOf(Status.class, "ACTIVE"));

		List<String> dailyUpdate = new ArrayList<>();
		LocalDate today = LocalDate.now();
		LocalDateTime startDateTime = today.atStartOfDay();
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date startDate = Date.from(zdt.toInstant());
		List<Parcel> parcels = parcelRepository.findByClientAndCreatedDateBetween(clientList.get(0), startDate,
				new Date());

		float totalCollection = 0.0f;

		if (parcels.size() > 0) {
			for (Parcel parcel : parcels) {
				totalCollection = totalCollection + parcel.getInvoiceBreakup().getPayableAmnt();
			}
		}

		Optional<ClientAccount> optionalAccount = clientAccountRepository
				.findById(clientList.get(0).getClientId());
		ClientAccount account = null;
		System.out.println("ParcelServiceImpl.getParcelCountCollection()");
		if (optionalAccount.isEmpty()) {
			account = clientAccountRepository.save(new ClientAccount(clientList.get(0).getClientId()));
		} else {
			account = optionalAccount.get();
		}
		dailyUpdate.add(parcels.size() + "");
		dailyUpdate.add(String.format("%.2f", totalCollection));
		dailyUpdate.add(String.format("%.2f", account.getAvalBalance()));
		dailyUpdate.add(String.format("%.2f", account.getTotalBalance()));
		return dailyUpdate;
	}

	@Override
	public List<MasterAddressVo> getAddressByPostalcode(int postalCode) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findByPostalCodeAndStatus(postalCode,
				Status.ACTIVE);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(postalCode);
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public UserAddressVO getUserAddress(User user, MasterAddressVo masterAddress) {

		UserAddressVO userAddress = new UserAddressVO();

		userAddress.setPostalCode(masterAddress.getPostalCode());
		userAddress.setZone(masterAddress.getZone());
		userAddress.setDivision(masterAddress.getDivision());
		userAddress.setDistrict(masterAddress.getDistrict());
		userAddress.setThana(masterAddress.getThana());
		userAddress.setSubOffice(masterAddress.getSubOffice());
		userAddress.setAddressLine1(user.getAddressLine1());
		userAddress.setAddressLine2(user.getAddressLine2());
		userAddress.setMobileNumber(user.getMobileNumber());
		userAddress.setName(user.getName());
		return userAddress;
	}

	@Override
	public ParcelVo fetchParcelDetail(String trackId) {
		log.debug("Fetching details on Track id:::" + trackId);
		Parcel parcel = parcelRepository.findParcelByTrackId(trackId);
		ParcelVo parcelVo = null;
		if (parcel != null) {
			String parcelImage = Base64.getEncoder().encodeToString(parcel.getParcelimage());
			parcelVo = new ParcelVo();
			parcelVo.setTrackId(parcel.getTrackId());
			parcelVo.setReceiverAddress(parcel.getReceiverAddress());
			parcelVo.setService(parcel.getService());
			parcelVo.setpStatus(parcel.getpStatus());
			parcelVo.setStatus(parcel.getStatus());
			parcelVo.setRecipientName(parcel.getRecipientName());
			parcelVo.setRecipientMobileNumber(String.format("%010d", parcel.getRecipientMobileNumber()));
			parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
			parcelVo.setSyncFlag(parcel.isSyncFlag());
			parcelVo.setSenderName(parcel.getSenderName());
			parcelVo.setSenderMobileNumber(String.format("%010d", parcel.getSenderMobileNumber()));
			parcelVo.setSenderAddress(parcel.getSenderAddress());
			String parcelDelValue = String.valueOf(parcel.getParcelDeclerationValue());
			parcelVo.setParcelDeclerationValue(parcelDelValue);
			parcelVo.setParcelContent(parcel.getParcelContent());
			parcelVo.setRateCalculationJSON(parcel.getRateCalculationJSON());
			parcelVo.setParcelimage(parcel.getParcelimage());

		}
		return parcelVo;
	}

	@Override
	public int getParcelWeight() {

		PbCube cube = PbCube.cube;
		int connection = cube.ConnectPrinterEx(2, new String(), 0, 0, 0, 0);
		if (connection != 0)
			throw new RuntimeException("unable to connect PB printer");

		byte[] weight = new byte[10];

		int w_result = cube.GetScaleWeight(weight);
		if (w_result == 0) {
			throw new RuntimeException("unable to get weight from printer");
		}
		
		String weightString = new String(weight, StandardCharsets.US_ASCII).trim();
		log.info("weight from the cube: {}", weightString.length());
		if (weightString.contains("oz")) {
			log.info("setting unit to weight");
			if (!cube.SetScaleUnit())
				throw new RuntimeException("unable to set weight unit to grams");
			weight = new byte[10];
			w_result = cube.GetScaleWeight(weight);
			if (w_result == 0)
				throw new RuntimeException("unable to get weight from printer");
			weightString = new String(weight, StandardCharsets.US_ASCII).trim();
			log.info("weight from the cube: {}", weightString.length());
		}
		if (weightString.equals("a") || weightString.equals("c") || weightString.equals("d"))
			return 0;
		return Integer.parseInt(weightString.substring(0,weightString.length()-1));

	}

	@Override
	public void setParcelStatusVoid(String trackId) {
		log.debug("Canceling parcel having Track id:::" + trackId);
		Parcel p = parcelRepository.findParcelByTrackId(trackId);
		if (p.getStatus().equals(status)) {
			p.setStatus("void");
			parcelRepository.save(p);
		}

		ClientAccount clientAccount = clientAccountRepository.findById(p.getClient().getClientId()).get();
		clientAccount.setAvalBalance(clientAccount.getAvalBalance() + p.getInvoiceBreakup().getCalculatedAmnt());
		clientAccountRepository.save(clientAccount);
	}

	@Override
	public List<MasterAddressVo> getAddressBySubOffice(String subOffice) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findBySubOfficeAndStatus(subOffice,
				Status.ACTIVE);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public List<String> getDistinctDivisionByZone(String zone) {
		return masterAddressRepository.findDistinctDivisionByZone(zone, Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctDistrictByDivision(String division) {
		return masterAddressRepository.findDistinctDistrictByDivision(division, Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctThanaByDistrict(String district) {
		return masterAddressRepository.findDistinctThanaByDistrict(district, Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctSubOfficeByThana(String thana) {
		return masterAddressRepository.findDistinctSubOfficeByThana(thana, Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctZone() {
		return masterAddressRepository.findDistinctZone(Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctDivision() {
		return masterAddressRepository.findDistinctDivision(Status.ACTIVE);

	}

	@Override
	public List<String> getDistinctDistrict() {
		return masterAddressRepository.findDistinctDistrict(Status.ACTIVE);
	}

	@Override
	public List<MasterAddressVo> getAddressByThana(String thana) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findByThanaStartsWithIgnoreCase(thana);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public List<MasterAddressVo> getAddressBysubOfficeStartsWith(String subOffice) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findBySubOfficeStartsWithIgnoreCase(subOffice);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public List<MasterAddressVo> getAddressByDistrict(String district) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findByDistrictIgnoreCase(district);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public List<MasterAddressVo> getAddressByDivision(String division) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findAddressByDivisionIgnoreCase(division);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	public Parcel saveParcelDetails(Parcel parcel, UpdatePayableAmountVo jsonObj, String userName) {

		log.info("inside saveParcelDetails");
		User user = sUserRepository.findUserByUsernameAndStatus(userName, Status.ACTIVE);

		parcel.setCreatedBy(user.getId());
		parcel.setCreatedDate(new Date());
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));

		// List<Client> client =
		// clientRepository.findByPostalCodeAndStatus(clientList.get(0).getPostalCode(),
		// Enum.valueOf(Status.class,"ACTIVE"));
		parcel.setClient(clientList.get(0));

		String uuid = labelCode(parcel);

		parcel.setLabelCode(uuid);
		parcel.setPrintCount(0);

		parcel.setStatus(status);
		parcel.setpStatus(PStatus.UNBAGGED.toString());
		parcel.setSyncFlag(false);

		if (jsonObj.getRadioButtonValue().equals(UpdatePayableAmnt.YES) && jsonObj.getUpdateAmount() != 0) {
			parcel.getInvoiceBreakup().setChangedAmnt(jsonObj.getRadioButtonValue());
			parcel.getInvoiceBreakup().setCalculatedAmnt(parcel.getInvoiceBreakup().getPayableAmnt());
			parcel.getInvoiceBreakup().setPayableAmnt(jsonObj.getUpdateAmount());

		} else if (jsonObj.getRadioButtonValue().equals(UpdatePayableAmnt.NO)) {
			parcel.getInvoiceBreakup().setChangedAmnt(jsonObj.getRadioButtonValue());
			parcel.getInvoiceBreakup().setCalculatedAmnt(parcel.getInvoiceBreakup().getPayableAmnt());
		}
		Optional<ClientAccount> clientAccount = clientAccountRepository.findById(parcel.getClient().getClientId());
		ClientAccount account = clientAccount.get();
		if (account.getTotalParcelCount() == null) {
			account.setTotalParcelCount(1L);
		} else {
			account.setTotalParcelCount(account.getTotalParcelCount() + 1L);
		}
		parcel.setParcelCount(account.getTotalParcelCount());
		clientAccountRepository.save(account);

		Parcel p = parcelRepository.save(parcel);
		log.info("Parcel Details Saved");
		return p;
	}

	public Parcel setReprintDetails(String trackId, String paperSize)
			throws JsonMappingException, JsonProcessingException {
		log.debug("Reprinting parcel having Track id:::" + trackId);
		Parcel p = parcelRepository.findParcelByTrackId(trackId);

		if (p.getStatus().equals(status)) {
			ObjectMapper mapper = new ObjectMapper();
			RateCalculation rateMap = mapper.readValue(p.getRateCalculationJSON(), RateCalculation.class);
			List<Long> serviceIdList = new ArrayList<>();
			if (rateMap.getSubServicesRateCalculation() != null) {
				rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
					serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				});
			} else {
				serviceIdList.addAll(rateMap.getSubServiceId());
			}
			p.setRateCalculation(rateMap);
			p.setSubServices(serviceIdList);
			p.setPrintOption(paperSize);
			p.setPrintCount(p.getPrintCount() + 1);
			p.setpStatus(PStatus.UNBAGGED.toString());
			log.debug("Setting print count to " + p.getPrintCount() + "for parcel having Track id:::" + trackId);

			Date date = new Date(); // this object contains the current date value
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			p.setLastPrintDate(date);

			log.debug("Setting Reprint Date to " + formatter.format(p.getLastPrintDate())
					+ "for parcel having Track id:::" + trackId);

			parcelRepository.save(p);
		}
		return p;
	}

	public String labelCode(Parcel parcel) {
		int count = 0;

		String str = Integer.toString(parcel.getSenderAddress().getPostalCode());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String dat = simpleDateFormat.format(new Date());

		List<String> labelCodes = parcelRepository.getLabelCode(PageRequest.of(0, 1));
		if (labelCodes.size() > 0 && labelCodes.get(0) != null) {
			String labelCode = labelCodes.get(0);
			String date1 = labelCode.substring(6, 14);
			if (dat.equals(date1)) {
				String counter = labelCode.substring(labelCode.length() - 4);
				count = Integer.parseInt(counter) + 1;
			} else
				count = 1;
		} else
			count = 1;
		String formatValue = String.format("%04d", count);
		log.debug("UUID generated UUID generatedUUID GENERATED " + str + dat + formatValue);
		String s = str + dat + formatValue;
		return s;

	}

	@Override
	public Page<Parcel> getAllParcels(Pageable pageable) {
		return parcelRepository.findAll(pageable);
	}

	@Override
	public List<Parcel> getRecentParcels(int count) {
		Iterable<Parcel> parcels = parcelRepository.findAll(Sort.by(Direction.DESC, "createdDate"));
		if (!Iterables.isEmpty(parcels)) {
			List<Parcel> parcelList = Lists.newArrayList(parcels);
			return (parcelList.size() > count) ? parcelList.subList(0, count) : parcelList;
		}
		return null;
	}

	@Override
	public List<ServicesVo> getSubServices(Long serviceId) {
		List<Services> postalServices = postalServiceRepository.findByParentServiceIdAndStatus(serviceId,
				Status.ACTIVE);
		List<ServicesVo> servicesVoList = new ArrayList<ServicesVo>();
		for (Services services : postalServices) {
			ServicesVo servicesVo = new ServicesVo();
			servicesVo.setId(services.getId());
			servicesVo.setParentServiceId(services.getParentServiceId());
			servicesVo.setServiceName(services.getServiceName());
			servicesVo.setServiceCode(services.getServiceCode());
			servicesVoList.add(servicesVo);
		}
		return servicesVoList;
	}

	@Override
	public List<ParcelVo> getAllParcelBetweenDates(Timestamp fromDate, Timestamp toDate, String status) {

		List<ParcelVo> parcelVoList = new ArrayList<ParcelVo>();
		if (status.equals("all")) {
			List<Parcel> parcelList = parcelRepository.findParcelByCreatedDateBetween(fromDate, toDate);
			for (Parcel parcel : parcelList) {

				Parcel p = parcel;

				String parentService = "";
				String parentServiceCode = "";
				List<Services> subServiceList = new ArrayList<>();
				List<String> subServiceCode = new ArrayList<>();
				String parcelImage = Base64.getEncoder().encodeToString(parcel.getParcelimage());
				ParcelVo parcelVo = new ParcelVo();
				parcelVo.setTrackId(parcel.getTrackId());
				parcelVo.setCreatedDate(parcel.getCreatedDate());
				parcelVo.setLabelCode(parcel.getLabelCode());
				parcelVo.setSenderAddress(parcel.getSenderAddress());
				parcelVo.setReceiverAddress(parcel.getReceiverAddress());
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setService(parcel.getService());
				parcelVo.setpStatus(parcel.getpStatus());
				parcelVo.setStatus(parcel.getStatus());
				parcelVo.setRecipientName(parcel.getRecipientName());
				parcelVo.setRecipientMobileNumber(String.format("%010d", parcel.getRecipientMobileNumber()));
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setSyncFlag(parcel.isSyncFlag());
				parcelVo.setSenderName(parcel.getSenderName());
				parcelVo.setSenderMobileNumber(String.format("%010d", parcel.getSenderMobileNumber()));
				parcelVo.setSenderAddress(parcel.getSenderAddress());
				parcelVo.setParcelContent(parcel.getParcelContent());
				parcelVo.setParcelimage(parcel.getParcelimage());

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					log.error("Error retrieving parcel report:: ", e);
				}
				// List<Long> serviceIdList = new ArrayList<>();
				// serviceIdList.add(4L);
				// (rateMap.getSubServiceId() != null) {
				// rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
				// serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				// });
				// }
				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(rateMap.getSubServiceId());

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				if (services == null) {
					services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
				}
				parentServiceCode = services.getServiceCode();

				if (!(parcel.getSubServices() == null)) {
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);

					// if some services are disabled, find from disabled service
					if (!(subServiceList.size() == parcel.getSubServices().size())) {
						List<Long> subserviceId = new ArrayList<Long>();
						for (Services s : subServiceList) {
							subserviceId.add(s.getId());
						}

						List<Long> parcelSubServices = parcel.getSubServices();
						parcelSubServices.removeAll(subserviceId);

						for (Long serviceid : parcelSubServices) {
							Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
							subServiceList.add(service);
						}
					}
				}

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});

				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
				parcelVo.setServiceCode(serviceCode);

				parcelVoList.add(parcelVo);
			}
			return parcelVoList;

		} else if (status.equals("booked") || status.equals("void")) {
			List<Parcel> parcelList = parcelRepository.findParcelByStatusAndCreatedDateBetween(status, fromDate,
					toDate);
			for (Parcel parcel : parcelList) {

				String parentService = "";
				String parentServiceCode = "";
				List<Services> subServiceList = new ArrayList<>();
				List<String> subServiceCode = new ArrayList<>();
				String parcelImage = Base64.getEncoder().encodeToString(parcel.getParcelimage());
				ParcelVo parcelVo = new ParcelVo();
				parcelVo.setTrackId(parcel.getTrackId());
				parcelVo.setCreatedDate(parcel.getCreatedDate());
				parcelVo.setLabelCode(parcel.getLabelCode());
				parcelVo.setSenderAddress(parcel.getSenderAddress());
				parcelVo.setReceiverAddress(parcel.getReceiverAddress());
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setService(parcel.getService());
				parcelVo.setpStatus(parcel.getpStatus());
				parcelVo.setStatus(parcel.getStatus());
				parcelVo.setRecipientName(parcel.getRecipientName());
				parcelVo.setRecipientMobileNumber(String.format("%010d", parcel.getRecipientMobileNumber()));
				parcelVo.setInvoiceBreakup(parcel.getInvoiceBreakup());
				parcelVo.setSyncFlag(parcel.isSyncFlag());
				parcelVo.setSenderName(parcel.getSenderName());
				parcelVo.setSenderMobileNumber(String.format("%010d", parcel.getSenderMobileNumber()));
				parcelVo.setSenderAddress(parcel.getSenderAddress());
				parcelVo.setParcelContent(parcel.getParcelContent());
				parcelVo.setParcelimage(parcel.getParcelimage());

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// List<Long> serviceIdList = new ArrayList<>();

				// if (rateMap.getSubServicesRateCalculation() != null) {
				// rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
				// serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				// });
				// }

				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(rateMap.getSubServiceId());

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				if (services == null) {
					services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
				}
				parentServiceCode = services.getServiceCode();

				if (!(parcel.getSubServices() == null)) {
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);

					// if some services are disabled, find from disabled service
					if (!(subServiceList.size() == parcel.getSubServices().size())) {
						List<Long> subserviceId = new ArrayList<Long>();
						for (Services s : subServiceList) {
							subserviceId.add(s.getId());
						}

						List<Long> parcelSubServices = parcel.getSubServices();
						parcelSubServices.removeAll(subserviceId);

						for (Long serviceid : parcelSubServices) {
							Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
							subServiceList.add(service);
						}
					}
				}

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});

				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));
				parcelVo.setServiceCode(serviceCode);

				parcelVoList.add(parcelVo);
			}
			return parcelVoList;
		}
		return null;
	}

	/* generate report */

	@Override
	public void generateParcelReport(String fromdate, String todate, User user, String parcelStatus)
			throws ParseException {

		java.util.Date fdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
		java.util.Date uptodate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);

		LocalDate today = uptodate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime startDateTime = today.atTime(23, 59, 59);
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date tdate = Date.from(zdt.toInstant());

		java.sql.Date sqlfromDate = new java.sql.Date(fdate.getTime());
		java.sql.Date sqltoDate = new java.sql.Date(tdate.getTime());

		Timestamp ts = new Timestamp(sqlfromDate.getTime());
		Timestamp ts1 = new Timestamp(sqltoDate.getTime());

		List<Parcel> preport = null;

		if (parcelStatus.equals("all")) {
			preport = parcelRepository.findParcelByCreatedDateBetween(ts, ts1);

		} else if (parcelStatus.equals("booked") || parcelStatus.equals("void")) {
			preport = parcelRepository.findParcelByStatusAndCreatedDateBetween(parcelStatus, ts, ts1);
		}

		try {

			List<JasperVo> jasperVoList = new ArrayList<>();

			for (Parcel parcel : preport) {
				String parentService = "";
				String parentServiceCode = "";
				List<Services> subServiceList = new ArrayList<>();
				List<String> subServiceCode = new ArrayList<>();

				JasperVo jasperVo = new JasperVo();
				jasperVo.setTrackid(parcel.getTrackId());
				jasperVo.setLabelCode(parcel.getLabelCode());
				jasperVo.setContent(parcel.getParcelContent());
				jasperVo.setStatus(parcel.getStatus());
				jasperVo.setSenderAddress(
						"Name:" + parcel.getSenderName() + "," + "," + parcel.getSenderAddress().getAddressLine1() + ","
								+ parcel.getSenderAddress().getSubOffice() + ","
								+ parcel.getSenderAddress().getThana() + ","
								+ parcel.getSenderAddress().getDistrict() + ","
								+ parcel.getSenderAddress().getDivision() + "-"
								+ parcel.getSenderAddress().getPostalCode() + "("
								+ String.format("%010d", parcel.getSenderMobileNumber()) + ")");
				jasperVo.setReceiverAddress(
						"Name:" + parcel.getRecipientName() + "," + parcel.getReceiverAddress().getAddressLine1() + ","
								+ parcel.getReceiverAddress().getSubOffice() + ","
								+ parcel.getReceiverAddress().getThana() + ","
								+ parcel.getReceiverAddress().getDistrict() + ","
								+ parcel.getReceiverAddress().getDivision() + ","
								+ parcel.getReceiverAddress().getPostalCode() + "("
								+ String.format("%010d", parcel.getRecipientMobileNumber()) + ")");

				ObjectMapper mapper = new ObjectMapper();
				RateCalculation rateMap = null;
				try {
					rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
				} catch (JsonProcessingException e) {
					log.error("Error retrieving parcel(void/booked) report:: ", e);
				}
				// List<Long> serviceIdList = new ArrayList<>();

				// if (rateMap.getSubServicesRateCalculation() != null) {
				// rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
				// serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				// });
				// }

				parcel.setRateCalculation(rateMap);
				parcel.setSubServices(rateMap.getSubServiceId());

				parentService = parcel.getInvoiceBreakup().getName();
				Long parentServiceId = parcel.getService();
				Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
				if (services == null) {
					services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
				}
				parentServiceCode = services.getServiceCode();

				if (!(parcel.getSubServices() == null)) {
					subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
							Status.ACTIVE);

					// if some services are disabled, find from disabled service
					if (!(subServiceList.size() == parcel.getSubServices().size())) {
						List<Long> subserviceId = new ArrayList<Long>();
						for (Services s : subServiceList) {
							subserviceId.add(s.getId());
						}

						List<Long> parcelSubServices = parcel.getSubServices();
						parcelSubServices.removeAll(subserviceId);

						for (Long serviceid : parcelSubServices) {
							Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
							subServiceList.add(service);
						}
					}
				}

				subServiceList.forEach(subService -> {
					subServiceCode.add(subService.getServiceCode());
				});

				String serviceCode = String.join(",", subServiceCode);
				serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));

				jasperVo.setServices(serviceCode);
				jasperVo.setDate(new SimpleDateFormat("dd-MM-yyyy hh:mm").format(parcel.getCreatedDate()));

				jasperVoList.add(jasperVo);

			}

			/* String reportPath = "D:\\Report"; */

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager
					.compileReport(exportfilepath + "parcel-report.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(jasperVoList);

			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			MasterAddress masterAddress = masterAddressRepository
					.findMasterAddressByPostalCodeAndStatus((int) user.getPostalCode(), Status.ACTIVE);
			parameters.put("location", masterAddress.getSubOffice() + "," + masterAddress.getThana() + ","
					+ masterAddress.getDistrict() + "-" + masterAddress.getPostalCode());
			parameters.put("fromDate", new SimpleDateFormat("dd-MM-yyyy").format(ts));
			parameters.put("toDate", new SimpleDateFormat("dd-MM-yyyy").format(ts1));
			parameters.put("parcelReport", jrBeanCollectionDataSource);

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					new JREmptyDataSource());

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, exportfilepath + "reportPdf.pdf");
		} catch (Exception e) {
			log.error("Error generating parcel report pdf:: ", e);
		}
	}

	/* Excel Service */
	@Override
	public void generateParcelReportExcel(Timestamp fromDate, Timestamp toDate, String parcelStatus)
			throws IOException {

		String[] columns = { "S.No.", "Track ID", "Label Code", "Sender Details ", "Recipient Details", "Status",
				"Created Date", "Parcel Content", "Service", "Amount(&#x20B9;)", "Actual Weight(gm)" };

		List<Parcel> preport = null;

		if (parcelStatus.equals("all")) {
			preport = parcelRepository.findParcelByCreatedDateBetween(fromDate, toDate);

		} else if (parcelStatus.equals("booked") || parcelStatus.equals("void")) {
			preport = parcelRepository.findParcelByStatusAndCreatedDateBetween(parcelStatus, fromDate, toDate);
		}

		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("parcelrepot");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for (Parcel parcel : preport) {

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Row row = sheet.createRow(rowNum++);

			row.createCell(0)
					.setCellValue(rowNum - 1);

			row.createCell(1)
					.setCellValue(parcel.getTrackId());

			row.createCell(2)
					.setCellValue(parcel.getLabelCode());

			row.createCell(3)
					.setCellValue("Name : " + parcel.getSenderName() + " , Mobile : "
							+ String.format("%010d", parcel.getSenderMobileNumber()) + ","
							+ parcel.getSenderAddress().getAddressLine1() + ","
							+ (parcel.getSenderAddress().getAddressLine2().equals("") ? ""
									: parcel.getSenderAddress().getAddressLine2().concat(","))
							+ (parcel.getSenderAddress().getLandmark().equals("") ? ""
									: parcel.getSenderAddress().getLandmark().concat(","))
							+ parcel.getSenderAddress().getSubOffice() + ","
							+ parcel.getSenderAddress().getThana() + ","
							+ parcel.getSenderAddress().getDistrict() + ","
							+ parcel.getSenderAddress().getDivision() + ","
							+ parcel.getSenderAddress().getPostalCode());

			row.createCell(4)
					.setCellValue("Name :" + parcel.getRecipientName() + ", Mobile :"
							+ String.format("%010d", parcel.getRecipientMobileNumber())
							+ parcel.getReceiverAddress().getAddressLine1() + ","
							+ (parcel.getReceiverAddress().getAddressLine2().equals("") ? ""
									: parcel.getReceiverAddress().getAddressLine2().concat(","))
							+ (parcel.getReceiverAddress().getLandmark().equals("") ? ""
									: parcel.getReceiverAddress().getLandmark().concat(","))
							+ parcel.getReceiverAddress().getSubOffice() + ","
							+ parcel.getReceiverAddress().getThana() + ","
							+ parcel.getReceiverAddress().getDistrict() + ","
							+ parcel.getReceiverAddress().getDivision() + ","
							+ parcel.getReceiverAddress().getPostalCode());

			row.createCell(5)
					.setCellValue(parcel.getStatus());
			row.createCell(6)
					.setCellValue(simpleDateFormat.format(parcel.getCreatedDate()));
			row.createCell(7)
					.setCellValue(parcel.getParcelContent());

			String parentService = "";
			String parentServiceCode = "";
			List<Services> subServiceList = new ArrayList<>();
			List<String> subServiceCode = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			RateCalculation rateMap = null;
			try {
				rateMap = mapper.readValue(parcel.getRateCalculationJSON(), RateCalculation.class);
			} catch (JsonProcessingException e) {
				log.error("Error retrieving ratemap for parcel excel:: ", e);
			}
			List<Long> serviceIdList = new ArrayList<>();

			if (rateMap.getSubServicesRateCalculation() != null) {
				rateMap.getSubServicesRateCalculation().forEach(rateCalculation -> {
					serviceIdList.add(rateCalculation.getRateServiceCategory().getServiceId());
				});
			}

			parcel.setRateCalculation(rateMap);
			parcel.setSubServices(serviceIdList);

			parentService = parcel.getInvoiceBreakup().getName();
			Long parentServiceId = parcel.getService();
			Services services = postalServiceRepository.findByIdAndStatus(parentServiceId, Status.ACTIVE);
			if (services == null) {
				services = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(parentServiceId);
			}
			parentServiceCode = services.getServiceCode();

			if (!(parcel.getSubServices() == null)) {
				subServiceList = postalServiceRepository.findByIdInAndStatus(parcel.getSubServices(),
						Status.ACTIVE);

				// if some services are disabled, find from disabled service
				if (!(subServiceList.size() == parcel.getSubServices().size())) {
					List<Long> subserviceId = new ArrayList<Long>();
					for (Services s : subServiceList) {
						subserviceId.add(s.getId());
					}

					List<Long> parcelSubServices = parcel.getSubServices();
					parcelSubServices.removeAll(subserviceId);

					for (Long serviceid : parcelSubServices) {
						Services service = postalServiceRepository.findTopByIdOrderByUpdatedOnDesc(serviceid);
						subServiceList.add(service);
					}
				}
			}

			subServiceList.forEach(subService -> {
				subServiceCode.add(subService.getServiceCode());
			});

			String serviceCode = String.join(",", subServiceCode);
			serviceCode = parentServiceCode + (!(serviceCode.isBlank()) ? ("/" + serviceCode) : (""));

			row.createCell(8)
					.setCellValue(serviceCode);
			row.createCell(9)
					.setCellValue(parcel.getInvoiceBreakup().getPayableAmnt());
			row.createCell(10)
					.setCellValue(parcel.getActWt());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(exportfilepath + "reportExcel.xlsx");
		workbook.write(fileOut);
		fileOut.close();
	}

	public Client getClientDetails() {
		List<Client> client = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));
		return client.get(0);
	}

	// Getting daily end data and total Amount Collected
	public String getParcelCountTodayCollection() {
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));
		LocalDate today = LocalDate.now();
		LocalDateTime startDateTime = today.atStartOfDay();
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date startDate = Date.from(zdt.toInstant());

		List<Parcel> parcels = parcelRepository.findByClientAndCreatedDateBetween(clientList.get(0), startDate,
				new Date());

		float totalCollection = 0.0f;
		float totalLoad = 0.0f;
		if (parcels.size() > 0) {
			for (Parcel parcel : parcels) {
				totalCollection = totalCollection + parcel.getInvoiceBreakup().getPayableAmnt();
				totalLoad = totalLoad + parcel.getActWt();
			}
		}
		String parcelSize = Integer.toString(parcels.size());
		String tCollection = String.valueOf(totalCollection);
		String tLoad = String.valueOf(totalLoad);
		String update = parcelSize + "," + tCollection + "," + tLoad;

		return update;

	}

	@Override
	public String getParcelCountYesterDayCollection() {
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		LocalDateTime startDateTime = yesterday.atStartOfDay();
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date startDate = Date.from(zdt.toInstant());

		LocalDateTime endDateTime = today.atStartOfDay();
		ZonedDateTime endzdt = endDateTime.atZone(ZoneId.systemDefault());
		Date endDate = Date.from(endzdt.toInstant());

		List<Parcel> parcels = parcelRepository.findByClientAndCreatedDateBetween(clientList.get(0), startDate,
				endDate);
		float totalCollection = 0.0f;
		float totalLoad = 0.0f;
		if (parcels.size() > 0) {
			for (Parcel parcel : parcels) {
				totalCollection = totalCollection + parcel.getInvoiceBreakup().getPayableAmnt();
				totalLoad = totalLoad + parcel.getActWt();
			}
		}

		String parcelSize = Integer.toString(parcels.size());
		String tCollection = String.valueOf(totalCollection);
		String tLoad = String.valueOf(totalLoad);

		String yesterdayUpdate = parcelSize + "," + tCollection + "," + tLoad;

		return yesterdayUpdate;

	}

	@Override
	public String getVoidParcelCount() {
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));
		LocalDate today = LocalDate.now();
		LocalDateTime startDateTime = today.atStartOfDay();
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date startDate = Date.from(zdt.toInstant());
		List<Parcel> parcels = parcelRepository.findByClientAndCreatedDateBetweenAndStatus(clientList.get(0), startDate,
				new Date(), "void");

		float totalCollection = 0.0f;
		float totalLoad = 0.0f;
		if (parcels.size() > 0) {
			for (Parcel parcel : parcels) {
				totalCollection = totalCollection + parcel.getInvoiceBreakup().getPayableAmnt();
				totalLoad = totalLoad + parcel.getActWt();
			}
		}
		String tLoad = String.valueOf(totalLoad);
		String parcelSize = Integer.toString(parcels.size());
		String tCollection = String.valueOf(totalCollection);
		String todayVoidUpdate = parcelSize + "," + tCollection + "," + tLoad;

		return todayVoidUpdate;

	}

	@Override
	public String getVoidYesterdayParcelCount() {
		List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
				Enum.valueOf(Status.class, "ACTIVE"));

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		LocalDateTime startDateTime = yesterday.atStartOfDay();
		ZonedDateTime zdt = startDateTime.atZone(ZoneId.systemDefault());
		Date startDate = Date.from(zdt.toInstant());

		LocalDateTime endDateTime = today.atStartOfDay();
		ZonedDateTime endzdt = endDateTime.atZone(ZoneId.systemDefault());
		Date endDate = Date.from(endzdt.toInstant());

		List<Parcel> parcels = parcelRepository.findByClientAndCreatedDateBetweenAndStatus(clientList.get(0), startDate,
				endDate, "void");
		float totalCollection = 0.0f;
		float totalLoad = 0.0f;
		if (parcels.size() > 0) {

			for (Parcel parcel : parcels) {
				totalCollection = totalCollection + parcel.getInvoiceBreakup().getPayableAmnt();
				totalLoad = totalLoad + parcel.getActWt();
			}
		}

		String parcelSize = Integer.toString(parcels.size());
		String tCollection = String.valueOf(totalCollection);
		String tLoad = String.valueOf(totalLoad);
		String yesterdayVoidUpdate = parcelSize + "," + tCollection + "," + tLoad;

		return yesterdayVoidUpdate;
	}

	@Override
	public List<String> getDistinctSubOffice() {
		return masterAddressRepository.findDistinctSubOffice(Status.ACTIVE);
	}

	@Override
	public List<String> getDistinctThana() {
		return masterAddressRepository.findDistinctThana(Status.ACTIVE);
	}

	@Override
	public List<MasterAddressVo> getAddressByThanaOnly(String thana) {
		List<MasterAddress> masterAddressList = masterAddressRepository.findByThanaAndStatus(thana, Status.ACTIVE);
		List<MasterAddressVo> masterAddressListVo = new ArrayList<MasterAddressVo>();
		for (MasterAddress masterAddress : masterAddressList) {
			MasterAddressVo masterAddressVo = new MasterAddressVo();
			masterAddressVo.setPostalCode(masterAddress.getPostalCode());
			masterAddressVo.setZone(masterAddress.getZone());
			masterAddressVo.setDivision(masterAddress.getDivision());
			masterAddressVo.setDistrict(masterAddress.getDistrict());
			masterAddressVo.setThana(masterAddress.getThana());
			masterAddressVo.setSubOffice(masterAddress.getSubOffice());
			masterAddressListVo.add(masterAddressVo);
		}
		return masterAddressListVo;
	}

	@Override
	public List<Parcel> getAllParcelsList() {
		// return (List<Parcel>)
		parcelRepository.findParcelBypStatus(PStatus.UNBAGGED.toString());
		return (List<Parcel>) parcelRepository.findAll();
	}

	@Override
	public List<Parcel> getBySyncFlag(Boolean syncFlag) {
		return parcelRepository.findBySyncFlag(false);
	}

	// search bag
	// method inputBox

	@Override
	public List<TrackingVo> findBagDetailsWithBagId(String bagId, User loginedUser) {
		List<TrackingCS> bagList = trackingCSRepo.findAllByBagId(bagId);
		List<TrackingVo> trackingListVo = new ArrayList<>();
		// List<String> bgId = new ArrayList<>();
		if (bagList.isEmpty()) {

			// List<TrackingDetails> bagDisableList =
			// trackingDetailsRepository.findAllByBagIdAndStatusAndBagStatusOrderByUpdatedOnDesc(bagId,
			// Status.DISABLED, BagStatus.IN);
			List<TrackingDetails> bagDisableList = trackingDetailsRepository
					.findAllByBagIdAndStatusOrderByUpdatedOnDesc(bagId, Status.DISABLED);
			if (bagDisableList.isEmpty()) {
				return null;
			} else {
				List<TrackingDetails> list1 = bagDisableList
						.stream()
						.filter(distinctByKey(TrackingDetails::getObjParcel))
						.collect(Collectors.toList());

				for (TrackingDetails bagDisableData : list1) {
					Parcel parcel = bagDisableData.getObjParcel();
					TrackingCS tc = trackingCSRepo.findTrackingCSByObjParcel(parcel);

					TrackingVo objVo = new TrackingVo();
					if (bagDisableData.getLocationType().equals(LocationType.RMS)) {
						RmsTable rmsobj = rmsRepo.findById(bagDisableData.getLocationId()).orElse(null);

						objVo.setRmsName(rmsobj.getRmsName());
						objVo.setRmsType(rmsobj.getRmsType());
						objVo.setBagId(bagDisableData.getBagId());
						objVo.setLocationType(bagDisableList.get(0).getLocationType());
						// objVo.setBagFillStatus(bagDisableList.get(0).getBagFillStatus());
						objVo.setBagDesc(bagDisableList.get(0).getBagDesc());
						objVo.setBagStatus(bagDisableList.get(0).getBagStatus());
						objVo.setBagTitle(bagDisableList.get(0).getBagTitle());
						objVo.setUpdatedOn(bagDisableList.get(0).getUpdatedOn());
						objVo.setObjParcelVo(bagDisableData.getObjParcel());
						objVo.setNewBagId(tc.getBagId());
						objVo.setParcelUpdatedOn(tc.getUpdatedOn());
						objVo.setStatus(bagDisableList.get(0).getStatus());
						trackingListVo.add(objVo);

					}

					else if (bagDisableData.getLocationType().equals(LocationType.POST_OFFICE)) {

						List<MasterAddress> masterAdd = masterAddressRepository
								.findByPostalCodeAndStatus(bagDisableData.getLocationId().intValue(), Status.ACTIVE);
						MasterAddress masterData = masterAdd.get(0);

						objVo.setSubOfficeName(masterData.getSubOffice());
						objVo.setPostalCode(masterData.getPostalCode());

						objVo.setBagId(bagDisableData.getBagId());
						objVo.setLocationType(bagDisableList.get(0).getLocationType());
						// objVo.setBagFillStatus(bagDisableList.get(0).getBagFillStatus());
						objVo.setBagDesc(bagDisableList.get(0).getBagDesc());
						objVo.setBagStatus(bagDisableList.get(0).getBagStatus());
						objVo.setBagTitle(bagDisableList.get(0).getBagTitle());
						objVo.setUpdatedOn(bagDisableList.get(0).getUpdatedOn());
						objVo.setObjParcelVo(bagDisableData.getObjParcel());
						objVo.setNewBagId(tc.getBagId());
						objVo.setStatus(bagDisableList.get(0).getStatus());
						trackingListVo.add(objVo);
						;
					}

				}
				return trackingListVo;
			}
		} else {
			for (TrackingCS bagData : bagList) {

				TrackingVo objVo = new TrackingVo();
				if (bagData.getLocationType().equals(LocationType.RMS)) {
					RmsTable rmsobj = rmsRepo.findById(bagData.getLocationId()).orElse(null);

					objVo.setRmsName(rmsobj.getRmsName());
					objVo.setRmsType(rmsobj.getRmsType());
					objVo.setBagId(bagData.getBagId());
					objVo.setLocationType(bagData.getLocationType());
					objVo.setBagFillStatus(bagData.getBagFillStatus());
					objVo.setBagDesc(bagData.getBagDesc());
					objVo.setBagStatus(bagData.getBagStatus());
					objVo.setBagTitle(bagData.getBagTitle());
					objVo.setTrackingDesc(bagData.getTrackingDesc());
					objVo.setUpdatedOn(bagData.getUpdatedOn());
					objVo.setObjParcelVo(bagData.getObjParcel());
					trackingListVo.add(objVo);

				}

				else if (bagData.getLocationType().equals(LocationType.POST_OFFICE)) {

					List<MasterAddress> masterAdd = masterAddressRepository
							.findByPostalCodeAndStatus(bagData.getLocationId().intValue(), Status.ACTIVE);
					MasterAddress masterData = masterAdd.get(0);

					objVo.setSubOfficeName(masterData.getSubOffice());
					objVo.setPostalCode(masterData.getPostalCode());

					objVo.setLocationType(bagData.getLocationType());
					objVo.setBagId(bagData.getBagId());
					objVo.setBagFillStatus(bagData.getBagFillStatus());
					objVo.setBagDesc(bagData.getBagDesc());
					objVo.setBagStatus(bagData.getBagStatus());
					objVo.setBagTitle(bagData.getBagTitle());
					objVo.setTrackingDesc(bagData.getTrackingDesc());
					objVo.setUpdatedOn(bagData.getUpdatedOn());
					objVo.setObjParcelVo(bagData.getObjParcel());
					trackingListVo.add(objVo);
				}

			}
			return trackingListVo;
		}

	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@Override
	public List<TrackingCS> fetchRecentTransactionOfBag(User loginedUser) {
		if (loginedUser.getRole().getName().equalsIgnoreCase("ROLE_ADMIN")) {

			List<TrackingCS> bagList = trackingCSRepo.findDistinctBaGData(PageRequest.of(0, 5));
			List<TrackingCS> sortedList = bagList.stream()
					.sorted(Comparator.comparing(TrackingCS::getUpdatedOn).reversed()).collect(Collectors.toList());

			return sortedList;
		} else if (loginedUser.getRole().getName().equalsIgnoreCase("ROLE_PO_USER")) {

			Long postCode = loginedUser.getPostalCode();

			List<BagStatus> bagStatus = new ArrayList<>();
			bagStatus.add(BagStatus.CREATED);
			bagStatus.add(BagStatus.IN);

			List<TrackingCS> bagList = trackingCSRepo.findDistinctPostBagData(BagStatus.IN.toString(),
					BagStatus.CREATED.toString(), postCode);

			List<TrackingCS> sortedList = bagList.stream()
					.sorted(Comparator.comparing(TrackingCS::getUpdatedOn).reversed()).collect(Collectors.toList());

			return sortedList;
		} else if (loginedUser.getRole().getName().equalsIgnoreCase("ROLE_RMS_USER")) {

			List<BagStatus> bagStatus = new ArrayList<>();
			bagStatus.add(BagStatus.CREATED);
			bagStatus.add(BagStatus.IN);
			Long rmsID = loginedUser.getRmsId();
			List<TrackingCS> bagList = trackingCSRepo.findDistinctRMSBagData(BagStatus.IN.toString(),
					BagStatus.CREATED.toString(), rmsID);

			List<TrackingCS> sortedList = bagList.stream()
					.sorted(Comparator.comparing(TrackingCS::getUpdatedOn).reversed()).collect(Collectors.toList());
			return sortedList;
		}
		return null;
	}

}
