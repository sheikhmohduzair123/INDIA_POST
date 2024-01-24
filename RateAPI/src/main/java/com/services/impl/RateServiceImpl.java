package com.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.constants.LocationDependency;
import com.constants.PriceType;
import com.constants.Status;
import com.constants.ValueDependency;
import com.constants.WeightDependency;
import com.domain.Config;
import com.domain.InvoiceBreakup;
import com.domain.Parcel;
import com.domain.RateTable;
import com.domain.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ConfigRepository;
import com.repositories.MasterAddressRepository;
import com.repositories.PostalServiceRepository;
import com.repositories.RateTableRepository;
import com.services.RateService;
import com.util.OperationStatus;
import com.vo.RateCalculation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class RateServiceImpl implements RateService {

	protected Logger log = LoggerFactory.getLogger(RateServiceImpl.class);

	@Autowired
	private RateTableRepository rateTableRepository;

	@Autowired
	private PostalServiceRepository postalServiceRepository;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	ConfigRepository configRepository;

	/*
	 * @Value("${tax1}") private String tax1Value;
	 * 
	 * @Value("${tax2}") private String tax2Value;
	 */

	@Value("${id.not.present}")
	private String idNotPresent;

	@Value("${high.weight}")
	private String highWeight;

	@Value("${high.value}")
	private String highValue;

	@Value("${no.location}")
	private String noLocation;

	@Value("${no.weight}")
	private String noWeight;

	@Value("${no.value}")
	private String noValue;

	@Value("${data.not.present}")
	private String noDataPresent;

	@Value("${cod.tp.not.together}")
	private String codTpNotTogether;

	@Override
	public Parcel getRate(Parcel parcel) {
		log.info("inside getRate");
		if (parcel.getService() <= 0) {
			log.error("cannot continue, invalid serviceId:" + parcel.getService());
			return parcel;
		}

		log.debug("we have received rate request for service:" + parcel.getService() + ", and sub services:"
				+ parcel.getSubServices());
		long serviceId = parcel.getService();
		RateCalculation mainServiceRateCalculation = rateCalculationServiceId(serviceId, parcel);
		parcel.setRateCalculation(mainServiceRateCalculation);
		List<Long> subServicesIds = parcel.getSubServices();
		InvoiceBreakup finalInvoice = mainServiceRateCalculation.getInvoiceBreakup();

		if (finalInvoice != null && subServicesIds != null && subServicesIds.size() > 0) {
			log.debug("subServicesIds:" + subServicesIds);
			List<Services> subServiceList = postalServiceRepository.findByIdInAndStatus(subServicesIds, Status.ACTIVE);
			List<String> subServiceCode = new ArrayList<>();
			subServiceList.forEach(subService -> {
				subServiceCode.add(subService.getServiceCode());
			});
			if (subServiceCode.contains("COD") && subServiceCode.contains("TP")) {
				RateCalculation rateCalculation = new RateCalculation();
				rateCalculation.setOperationStatus(OperationStatus.COD_TP_SELECTED);
				rateCalculation.setErrorMsg(codTpNotTogether);
				parcel.setRateCalculation(rateCalculation);
				log.error("Cannot select COD and To Pay together");
				return parcel;
			}

			if (subServiceCode.contains("COD")) {
				parcel.setCod(true);
			} else if (subServiceCode.contains("TP")) {
				parcel.setToPay(true);
			}
			List<RateCalculation> subServicesRateCalculation = new ArrayList<RateCalculation>(subServicesIds.size());
			List<InvoiceBreakup> subInvoices = new ArrayList<>();
			subServicesIds.forEach(s -> {
				RateCalculation r = rateCalculationServiceId(s, parcel);
				subServicesRateCalculation.add(r);
				subInvoices.add(r.getInvoiceBreakup());
				if (r.getOperationStatus().equals(OperationStatus.SUCCESS)) {
					finalInvoice.setSubTotal(finalInvoice.getSubTotal() + r.getInvoiceBreakup().getSubTotal());
					finalInvoice.setTotalTax(finalInvoice.getTotalTax() + r.getInvoiceBreakup().getTotalTax());
					finalInvoice.setPayableAmnt(finalInvoice.getPayableAmnt() + r.getInvoiceBreakup().getPayableAmnt());
				}
			});
			mainServiceRateCalculation.setSubServicesRateCalculation(subServicesRateCalculation);
			mainServiceRateCalculation.getInvoiceBreakup().setSubInvoices(subInvoices);

		} else {
			log.debug("no subservices present xxxxxxxxxx");
		}
		parcel.setInvoiceBreakup(finalInvoice);
		parcel.setRateCalculation(mainServiceRateCalculation);
		parcel.setRateCalculationJSON(getJSON(mainServiceRateCalculation));
		log.info("rate calculation has been added successfully");
		return parcel;
	}

	private RateCalculation rateCalculationServiceId(long serviceId, Parcel parcel) {

		log.info("inside ServiceCategoryServiceId, serviceId:" + serviceId);
		float volumetricWeight = calculateVolumetricWeight(parcel.getParcelLength(), parcel.getParcelWidth(),
				parcel.getParcelHeight(), parcel.getParcelDeadWeight());
		float weightToBeUsed = volumetricWeight > parcel.getParcelDeadWeight() ? volumetricWeight
				: parcel.getParcelDeadWeight();
		parcel.setActWt(weightToBeUsed);
		log.debug("volumetricWeight:" + volumetricWeight + ", deadWeight:" + parcel.getParcelDeadWeight()
				+ ", weightToBeUsed:" + weightToBeUsed);

		RateCalculation rateCalculation = new RateCalculation();
		float finalAmount = 0f;
		List<RateTable> sericeCategories = rateTableRepository.findByRateServiceCategoryServiceIdAndStatus(serviceId,
				Enum.valueOf(Status.class, "ACTIVE"));
		if (sericeCategories == null || sericeCategories.size() == 0) {
			log.warn("something weird happened ! we couldn't find id in RateTable, id:" + serviceId);
			rateCalculation.setOperationStatus(OperationStatus.ID_NOT_PRESENT_IN_DB);
			rateCalculation.setErrorMsg(idNotPresent);
			return rateCalculation;
		}

		log.info("service categories fetched for serviceId:" + serviceId + " ,count:" + sericeCategories.size());
		Optional<RateTable> sericeCategory = Optional.ofNullable(sericeCategories.get(0));// start working with generic
																							// row

		if (sericeCategory.isPresent()) {

			RateTable rateTable = sericeCategory.get();
			rateCalculation.setRateServiceCategory(sericeCategory.get().getRateServiceCategory());
			// check if weight is not higher than the one provided by service
			if (rateTable.getRateServiceCategory().getWeightMaxLimit() != null
					&& weightToBeUsed > rateTable.getRateServiceCategory().getWeightMaxLimit()) {
				log.warn("parcel weight is higher than provided by service category");
				rateCalculation.setOperationStatus(OperationStatus.HIGH_WEIGHT_NOT_ACCEPTABLE);
				rateCalculation.setErrorMsg(highWeight);
				return rateCalculation;
			}

			// check if parcel value declaration is higher than provided by service category
			if (rateTable.getRateServiceCategory().getValueMaxLimit() != null
					&& parcel.getParcelDeclerationValue() > rateTable.getRateServiceCategory().getValueMaxLimit()) {
				log.warn("parcel value is higher than provided by service category");
				rateCalculation.setOperationStatus(OperationStatus.HIGH_VALUE_NOT_ACCEPTABLE);
				rateCalculation.setErrorMsg(highValue);
				return rateCalculation;
			}

			// now check whether price type is fixed or not
			if (rateTable.getRateServiceCategory().getPriceType().equals(PriceType.FLAT)) {
				log.info("price type is fixed for this service category, id:" + serviceId);
				float price = rateTable.getRateServiceCategory().getPrice();
				finalAmount = finalAmount + price;
				log.debug("price for the service is, price:" + price);

			} else if (rateTable.getRateServiceCategory().getPriceType().equals(PriceType.VARIABLE)) {

				log.info("price type is variable for this service category, id:" + serviceId);

				if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getWeightDependency()
								.equals(WeightDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getValueDependency()
								.equals(ValueDependency.NOT_APPLICABLE)) {

					Optional<RateTable> row = null;
					long fromPostalId = masterAddressRepository
							.findByPostalCodeAndStatus(parcel.getSenderAddress().getPostalCode(), Status.ACTIVE)
							.getId();
					long toPostalId = masterAddressRepository
							.findByPostalCodeAndStatus(parcel.getReceiverAddress().getPostalCode(), Status.ACTIVE)
							.getId();
					if(fromPostalId == toPostalId){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.LOCAL))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if(fromPostalId != toPostalId){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.INTERCITY))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.SUB_OFFICE)) {
						long fromId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getSenderAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						long toId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getReceiverAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
										.getValueStartRange()
										&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
												.getValueUpToRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DISTRICT)) {
						long fromId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getSenderAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						long toId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getReceiverAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
										.getValueStartRange()
										&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
												.getValueUpToRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DIVISIONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getSenderAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						long toId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getReceiverAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
										.getValueStartRange()
										&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
												.getValueUpToRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.ZONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getSenderAddress().getZone(), PageRequest.of(0, 1),
										Status.ACTIVE)
								.get(0).getZoneId();
						long toId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getReceiverAddress().getZone(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getZoneId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
										.getValueStartRange()
										&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
												.getValueUpToRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.THANA)) {
						long fromId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getSenderAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						long toId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getReceiverAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
										.getValueStartRange()
										&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
												.getValueUpToRange())
								.findFirst();
					}

					log.info("Location, Weight and Value all dependency exist");
					log.debug("fetching exact row for locations, from:" + parcel.getSenderAddress().getPostalCode()
							+ ", to:" + parcel.getReceiverAddress().getPostalCode());

					if (!row.isPresent()) {
						log.warn("something weird happened ! we couldn't location data in RateTable, id:" + serviceId
								+ " from:" + parcel.getSenderAddress().getPostalCode() + ", to:"
								+ parcel.getReceiverAddress().getPostalCode());
						rateCalculation.setOperationStatus(OperationStatus.DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noDataPresent);
						return rateCalculation;
					}

					rateCalculation.setLocationWiseRate(row.get().getLocationWiseRate());
					RateTable location = row.get();
					float locationPrice = location.getLocationWiseRate().getPrice();
					finalAmount = finalAmount + locationPrice;
					log.info("final calculated locationPrice:" + locationPrice + ", finalAmount:" + finalAmount);

					float weightPrice = 0;
					rateCalculation.setWeightWiseRate(row.get().getWeightWiseRate());
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						weightPrice = row.get().getWeightWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						log.debug("weight dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getWeightWiseRate().getBasePrice();
						float price = row.get().getWeightWiseRate().getPrice();
						float weightFractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getWeightWiseRate().getWeightFractionFactor() != null) {
							weightFractionFactor = row.get().getWeightWiseRate().getWeightFractionFactor();
							partition = (float) Math
									.ceil((weightToBeUsed - row.get().getWeightWiseRate().getWeightStartRange())
											/ weightFractionFactor);
						}
						if (basePrice == 0) {
							weightPrice = price;
						} else
							weightPrice = basePrice + (price * partition);
					}
					finalAmount = finalAmount + weightPrice;
					log.info("final calculated weight price:" + weightPrice + ", finalAmount:" + finalAmount);

					log.debug("value dependency is there");
					float valuePrice = 0;

					rateCalculation.setParcelValueWiseRate(row.get().getParcelValueWiseRate());
					if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						valuePrice = row.get().getParcelValueWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getValueDependency()
							.equals(ValueDependency.MULTIPLIER)) {
						log.debug("value dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getParcelValueWiseRate().getBasePrice();
						float price = row.get().getParcelValueWiseRate().getPrice();
						float fractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getParcelValueWiseRate().getValueFraction() != null) {
							fractionFactor = row.get().getParcelValueWiseRate().getValueFraction();
							partition = (float) Math.ceil((parcel.getParcelDeclerationValue()
									- row.get().getParcelValueWiseRate().getValueStartRange()) / fractionFactor);
						}
						if (basePrice == 0) {
							valuePrice = price;
						} else
							valuePrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + valuePrice;
					log.info("final calculated value price:" + valuePrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getWeightDependency()
								.equals(WeightDependency.NOT_APPLICABLE)) {

					log.info("Location, Weight dependency exist");
					log.debug("fetching exact row for locations, from:" + parcel.getSenderAddress().getPostalCode()
							+ ", to:" + parcel.getReceiverAddress().getPostalCode());
					Optional<RateTable> row = null;
					long fromPostalId = masterAddressRepository
							.findByPostalCodeAndStatus(parcel.getSenderAddress().getPostalCode(), Status.ACTIVE)
							.getId();
					long toPostalId = masterAddressRepository
							.findByPostalCodeAndStatus(parcel.getReceiverAddress().getPostalCode(), Status.ACTIVE)
							.getId();
					if(fromPostalId == toPostalId){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.LOCAL))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if(fromPostalId != toPostalId){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.INTERCITY))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.SUB_OFFICE)) {
						long fromId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getSenderAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						long toId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getReceiverAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DISTRICT)) {
						long fromId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getSenderAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						long toId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getReceiverAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DIVISIONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getSenderAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						long toId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getReceiverAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.ZONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getSenderAddress().getZone(), PageRequest.of(0, 1),
										Status.ACTIVE)
								.get(0).getZoneId();
						long toId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getReceiverAddress().getZone(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getZoneId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.THANA)) {
						long fromId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getSenderAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						long toId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getReceiverAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						row = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					}
					if (!row.isPresent()) {
						log.warn("something weird happened ! we couldn't location data in RateTable, id:" + serviceId
								+ " from:" + parcel.getSenderAddress().getPostalCode() + ", to:"
								+ parcel.getReceiverAddress().getPostalCode());
						rateCalculation.setOperationStatus(OperationStatus.LOCATION_DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noDataPresent);
						return rateCalculation;
					}
					rateCalculation.setLocationWiseRate(row.get().getLocationWiseRate());
					RateTable location = row.get();
					float locationPrice = location.getLocationWiseRate().getPrice();
					finalAmount = finalAmount + locationPrice;
					log.info("final calculated locationPrice price:" + locationPrice + ", finalAmount:" + finalAmount);

					log.debug("weight dependency exist");
					float weightPrice = 0;
					rateCalculation.setWeightWiseRate(row.get().getWeightWiseRate());
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						weightPrice = row.get().getWeightWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						log.debug("weight dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getWeightWiseRate().getBasePrice();
						float price = row.get().getWeightWiseRate().getPrice();
						float weightFractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getWeightWiseRate().getWeightFractionFactor() != null) {
							weightFractionFactor = row.get().getWeightWiseRate().getWeightFractionFactor();
							partition = (float) Math
									.ceil((weightToBeUsed - row.get().getWeightWiseRate().getWeightStartRange())
											/ weightFractionFactor);
						}
						if (basePrice == 0) {
							weightPrice = price;
						} else
							weightPrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + weightPrice;
					log.info("final calculated weight price:" + weightPrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getWeightDependency()
						.equals(WeightDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getValueDependency()
								.equals(ValueDependency.NOT_APPLICABLE)) {

					log.info("Weight and Value dependency exist");

					Optional<RateTable> row = sericeCategories.stream()
							.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
									&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
							.filter(f -> parcel.getParcelDeclerationValue() > f.getParcelValueWiseRate()
									.getValueStartRange()
									&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
											.getValueUpToRange())
							.findFirst();
					if (!row.isPresent()) {
						log.warn("something weird happened ! we couldn't location data in RateTable, id:" + serviceId
								+ " from:" + parcel.getSenderAddress().getPostalCode() + ", to:"
								+ parcel.getReceiverAddress().getPostalCode());
						rateCalculation.setOperationStatus(OperationStatus.DATA_NOT_PRESENT);
						return rateCalculation;
					}

					float weightPrice = 0;
					rateCalculation.setWeightWiseRate(row.get().getWeightWiseRate());
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						weightPrice = row.get().getWeightWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						log.debug("weight dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getWeightWiseRate().getBasePrice();
						float price = row.get().getWeightWiseRate().getPrice();
						float weightFractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getWeightWiseRate().getWeightFractionFactor() != null) {
							weightFractionFactor = row.get().getWeightWiseRate().getWeightFractionFactor();
							partition = (float) Math
									.ceil((weightToBeUsed - row.get().getWeightWiseRate().getWeightStartRange())
											/ weightFractionFactor);
						}
						if (basePrice == 0) {
							weightPrice = price;
						} else
							weightPrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + weightPrice;
					log.info("final calculated weight price:" + weightPrice + ", finalAmount:" + finalAmount);

					log.debug("value dependency is there");
					float valuePrice = 0;

					rateCalculation.setParcelValueWiseRate(row.get().getParcelValueWiseRate());
					if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						valuePrice = row.get().getParcelValueWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getValueDependency()
							.equals(ValueDependency.MULTIPLIER)) {
						log.debug("value dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getParcelValueWiseRate().getBasePrice();
						float price = row.get().getParcelValueWiseRate().getPrice();
						float fractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getParcelValueWiseRate().getValueFraction() != null) {
							fractionFactor = row.get().getParcelValueWiseRate().getValueFraction();
							partition = (float) Math.ceil((parcel.getParcelDeclerationValue()
									- row.get().getParcelValueWiseRate().getValueStartRange()) / fractionFactor);
						}
						if (basePrice == 0) {
							valuePrice = price;
						} else
							valuePrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + valuePrice;
					log.info("final calculated value price:" + valuePrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)
						&& !rateTable.getRateServiceCategory().getValueDependency()
								.equals(ValueDependency.NOT_APPLICABLE)) {

					log.info("Location, and Value dependency exist");
					Optional<RateTable> row = sericeCategories.stream().filter(
							f -> f.getLocationWiseRate().getFromId() == parcel.getSenderAddress().getPostalCode()
									&& f.getLocationWiseRate().getToId() == parcel.getReceiverAddress().getPostalCode())
							.filter(f -> parcel.getParcelDeclerationValue() >= f.getParcelValueWiseRate()
									.getValueStartRange()
									&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate()
											.getValueUpToRange())
							.findFirst();
					if (!row.isPresent()) {
						log.warn("something weird happened ! we couldn't location data in RateTable, id:" + serviceId
								+ " from:" + parcel.getSenderAddress().getPostalCode() + ", to:"
								+ parcel.getReceiverAddress().getPostalCode());
						rateCalculation.setOperationStatus(OperationStatus.DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noDataPresent);
						return rateCalculation;
					}

					rateCalculation.setLocationWiseRate(row.get().getLocationWiseRate());
					RateTable location = row.get();
					float locationPrice = location.getLocationWiseRate().getPrice();
					finalAmount = finalAmount + locationPrice;
					log.info("final calculated locationPrice price:" + locationPrice + ", finalAmount:" + finalAmount);

					log.debug("value dependency is there");
					float valuePrice = 0;

					rateCalculation.setParcelValueWiseRate(row.get().getParcelValueWiseRate());
					if(rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.LOCAL)){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.LOCAL))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if(rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.INTERCITY)){
						row = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.INTERCITY))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						valuePrice = row.get().getParcelValueWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getValueDependency()
							.equals(ValueDependency.MULTIPLIER)) {
						log.debug("value dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = row.get().getParcelValueWiseRate().getBasePrice();
						float price = row.get().getParcelValueWiseRate().getPrice();
						float fractionFactor = 0.0f;
						float partition = 0.0f;
						if (row.get().getParcelValueWiseRate().getValueFraction() != null) {
							fractionFactor = row.get().getParcelValueWiseRate().getValueFraction();
							partition = (float) Math.ceil((parcel.getParcelDeclerationValue()
									- row.get().getParcelValueWiseRate().getValueStartRange()) / fractionFactor);
						}
						if (basePrice == 0) {
							valuePrice = price;
						} else
							valuePrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + valuePrice;
					log.info("final calculated value price:" + valuePrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getLocationDependency()
						.equals(LocationDependency.NOT_APPLICABLE)) {

					log.info("Location dependency exist");
					log.debug("fetching exact row for locations, from:" + parcel.getSenderAddress().getPostalCode()
							+ ", to:" + parcel.getReceiverAddress().getPostalCode());
					Optional<RateTable> locationRow = null;
					if(rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.LOCAL)){
						locationRow = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.LOCAL))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if(rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.INTERCITY)){
						locationRow = sericeCategories.stream()
								.filter(f -> f.getRateServiceCategory().getLocationDependency().equals(LocationDependency.INTERCITY))
								.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
										&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.SUB_OFFICE)) {
						long fromId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getSenderAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						long toId = masterAddressRepository
								.findByPostalCodeAndStatus(parcel.getReceiverAddress().getPostalCode(), Status.ACTIVE)
								.getId();
						locationRow = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DISTRICT)) {
						long fromId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getSenderAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						long toId = masterAddressRepository
								.findDistinctByDistrictAndStatus(parcel.getReceiverAddress().getDistrict(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDistrictId();
						locationRow = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.DIVISIONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getSenderAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						long toId = masterAddressRepository
								.findDistinctByDivisionAndStatus(parcel.getReceiverAddress().getDivision(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getDivisionId();
						locationRow = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.ZONAL)) {
						long fromId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getSenderAddress().getZone(), PageRequest.of(0, 1),
										Status.ACTIVE)
								.get(0).getZoneId();
						long toId = masterAddressRepository
								.findDistinctByZoneAndStatus(parcel.getReceiverAddress().getZone(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getZoneId();
						locationRow = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.findFirst();
					} else if (rateTable.getRateServiceCategory().getLocationDependency()
							.equals(LocationDependency.THANA)) {
						long fromId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getSenderAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						long toId = masterAddressRepository
								.findDistinctByThanaAndStatus(parcel.getReceiverAddress().getThana(),
										PageRequest.of(0, 1), Status.ACTIVE)
								.get(0).getThanaId();
						locationRow = sericeCategories.stream()
								.filter(f -> f.getLocationWiseRate().getFromId() == fromId
										&& f.getLocationWiseRate().getToId() == toId)
								.findFirst();
					}
					if (!locationRow.isPresent()) {
						log.warn("something weird happened ! we couldn't location data in RateTable, id:" + serviceId
								+ " from:" + parcel.getSenderAddress().getPostalCode() + ", to:"
								+ parcel.getReceiverAddress().getPostalCode());
						rateCalculation.setOperationStatus(OperationStatus.LOCATION_DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noLocation);
						return rateCalculation;
					}
					rateCalculation.setLocationWiseRate(locationRow.get().getLocationWiseRate());
					RateTable location = locationRow.get();
					float locationPrice = location.getLocationWiseRate().getPrice();
					finalAmount = finalAmount + locationPrice;
					log.info("final calculated locationPrice price:" + locationPrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getWeightDependency()
						.equals(WeightDependency.NOT_APPLICABLE)) {

					log.info("Weight dependency exist");

					log.debug("weight dependency exist");
					float weightPrice = 0;
					Optional<RateTable> weightRow = sericeCategories.stream()
							.filter(f -> weightToBeUsed > f.getWeightWiseRate().getWeightStartRange()
									&& weightToBeUsed <= f.getWeightWiseRate().getWeightEndRange())
							.findFirst();
					if (!weightRow.isPresent()) {
						log.warn("something weird happened ! we couldn't weight data in WeightTable, weight:"
								+ weightToBeUsed);
						rateCalculation.setOperationStatus(OperationStatus.WEIGHT_DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noWeight);
						return rateCalculation;
					}
					rateCalculation.setWeightWiseRate(weightRow.get().getWeightWiseRate());
					if (rateTable.getRateServiceCategory().getWeightDependency().equals(WeightDependency.SLAB_WISE)) {
						log.debug("weight dependency, SLAB Wise");
						weightPrice = weightRow.get().getWeightWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getWeightDependency()
							.equals(WeightDependency.MULTIPLIER)) {
						log.debug("weight dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = weightRow.get().getWeightWiseRate().getBasePrice();
						float price = weightRow.get().getWeightWiseRate().getPrice();
						float weightFractionFactor = 0.0f;
						float partition = 0.0f;
						if (weightRow.get().getWeightWiseRate().getWeightFractionFactor() != null) {
							weightFractionFactor = weightRow.get().getWeightWiseRate().getWeightFractionFactor();
							partition = (float) Math
									.ceil((weightToBeUsed - weightRow.get().getWeightWiseRate().getWeightStartRange())
											/ weightFractionFactor);
						}
						if (basePrice == 0) {
							weightPrice = price;
						} else
							weightPrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + weightPrice;
					log.info("final calculated weight price:" + weightPrice + ", finalAmount:" + finalAmount);

				} else if (!rateTable.getRateServiceCategory().getValueDependency()
						.equals(ValueDependency.NOT_APPLICABLE)) {

					log.info("Value dependency exist");

					log.debug("value dependency is there");
					float valuePrice = 0;
					Optional<RateTable> valueRow = sericeCategories.stream().filter(f -> parcel
							.getParcelDeclerationValue() > f.getParcelValueWiseRate().getValueStartRange()
							&& parcel.getParcelDeclerationValue() <= f.getParcelValueWiseRate().getValueUpToRange())
							.findFirst();
					if (!valueRow.isPresent()) {
						log.warn("something weird happened ! we couldn't value price data in PriceTable, value:"
								+ parcel.getParcelDeclerationValue());
						rateCalculation.setOperationStatus(OperationStatus.PRICE_DATA_NOT_PRESENT);
						rateCalculation.setErrorMsg(noValue);
						return rateCalculation;
					}
					rateCalculation.setParcelValueWiseRate(valueRow.get().getParcelValueWiseRate());
					if (rateTable.getRateServiceCategory().getValueDependency().equals(ValueDependency.SLAB_WISE)) {
						log.debug("value dependency, SLAB Wise");
						valuePrice = valueRow.get().getParcelValueWiseRate().getPrice();

					} else if (rateTable.getRateServiceCategory().getValueDependency()
							.equals(ValueDependency.MULTIPLIER)) {
						log.debug("value dependency, MULTIPLIER Wise");
						log.debug("calculating weight using weight fraction factor");
						float basePrice = valueRow.get().getParcelValueWiseRate().getBasePrice();
						float price = valueRow.get().getParcelValueWiseRate().getPrice();
						float fractionFactor = 0.0f;
						float partition = 0.0f;
						if (valueRow.get().getParcelValueWiseRate().getValueFraction() != null) {
							fractionFactor = valueRow.get().getParcelValueWiseRate().getValueFraction();
							partition = (float) Math.ceil((parcel.getParcelDeclerationValue()
									- valueRow.get().getParcelValueWiseRate().getValueStartRange()) / fractionFactor);
						}
						if (basePrice == 0) {
							valuePrice = price;
						} else
							valuePrice = basePrice + (price * partition);

					}
					finalAmount = finalAmount + valuePrice;
					log.info("final calculated value price:" + valuePrice + ", finalAmount:" + finalAmount);
				}
			}

			log.info("creating final invoice");
			rateCalculation.setOperationStatus(OperationStatus.SUCCESS);
			rateCalculation.setFinalAmount(finalAmount);
			// Optional<Services> service =
			// postalServiceRepository.findById(rateTable.getRateServiceCategory().getServiceId());
			Optional<Services> service = postalServiceRepository
					.findByIdAndStatus(rateTable.getRateServiceCategory().getServiceId(), Status.ACTIVE);
			String serviceName = "Not Found";
			if (service.isPresent()) {
				serviceName = service.get().getServiceName();
			}
			rateCalculation
					.setInvoiceBreakup(finalInvoice(finalAmount, rateTable.getRateServiceCategory().getTaxRate1(),
							rateTable.getRateServiceCategory().getTaxRate2(), serviceName));

		} else {
			log.warn("something weird happened ! we couldn't find id in RateTable, id:" + serviceId);
			rateCalculation.setOperationStatus(OperationStatus.ID_NOT_PRESENT_IN_DB);
			rateCalculation.setErrorMsg(idNotPresent);
			return rateCalculation;
		}

		return rateCalculation;

	}

	private InvoiceBreakup finalInvoice(float amount, Float taxtRate1, Float taxtRate2, String name) {

		log.info("inside finalInvoice, amount:" + amount + ", taxtRate1:" + taxtRate1 + ", taxtRate2:" + taxtRate2);
		if (taxtRate1 == null) {
			taxtRate1 = 0f;
		}

		if (taxtRate2 == null) {
			taxtRate2 = 0f;
		}

		InvoiceBreakup invoiceBreakup = new InvoiceBreakup();
		
		Config configObjTax1 = configRepository.findTopByConfigTypeOrderByUpdatedOnDesc("tax1");
		String tax1Value= configObjTax1.getConfigValue();
		Config configObjTax2 = configRepository.findTopByConfigTypeOrderByUpdatedOnDesc("tax2");
		String tax2Value= configObjTax2.getConfigValue();

		float totalTax = 0f;
		
		String tax = null;
		if (taxtRate1 != null && taxtRate1 > 0) {
			float taxAmnt1 = (amount * taxtRate1) / 100;
			totalTax = totalTax + taxAmnt1;
			tax = tax1Value + " : " + taxtRate1.toString() + "%";
			log.debug("totalTax after taxt1, totalTax:" + totalTax);
		}

		if (taxtRate2 != null && taxtRate2 > 0) {
			float taxAmnt2 = (amount * taxtRate2) / 100;
			totalTax = totalTax + taxAmnt2;
			if (tax == null)
				tax = tax2Value + " : " + taxtRate2.toString() + "%";
			else
				tax = tax + " + " + tax2Value + " : " + taxtRate2.toString() + "%";
			log.debug("totalTax after taxt2, totalTax:" + totalTax);
		}
		invoiceBreakup.setName(name);
		invoiceBreakup.setPrice(amount);
		invoiceBreakup.setTotalTax(totalTax);
		invoiceBreakup.setSubTotal(amount + totalTax);
		invoiceBreakup.setPayableAmnt(amount + totalTax);
		invoiceBreakup.setTaxPercent(tax);
		log.debug("calculation done");
		return invoiceBreakup;

	}

	/*
	 * Volumetric Weight: Breadth x Length x Height in centimetres / 5000 =
	 * Volumetric Weight in kilograms.
	 */
	private float calculateVolumetricWeight(float length, float width, float height, float dead_weight) {
		log.info("inside calculating volumetric weight");
		Config config = configRepository.findTopByConfigTypeOrderByUpdatedOnDesc("VolumetricWeightFormula");
		String formula = config.getConfigValue();

		formula=formula.replace("length", String.valueOf(length));
		formula=formula.replace("width",String.valueOf(width));
		formula=formula.replace("height", String.valueOf(height));
		formula=formula.replace("dead_weight", String.valueOf(dead_weight));

		Expression calc = new ExpressionBuilder(formula)
							.build();
		float vol_wgt = (float)calc.evaluate();
		return vol_wgt;
	}

	private String getJSON(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
			log.debug("json:" + json);
		} catch (JsonProcessingException e) {
			log.error("could not generate json", e);
		}

		return json;
	}

}
