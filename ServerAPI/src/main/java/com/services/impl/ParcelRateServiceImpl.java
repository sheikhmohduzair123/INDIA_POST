package com.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.constants.PriceType;
import com.constants.Status;
import com.domain.InvoiceBreakup;
import com.domain.Parcel;
import com.domain.RateServiceCategory;
import com.domain.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.PostalServiceRepository;
import com.services.ParcelRateService;
import com.util.OperationStatus;
import com.vo.RateCalculation;
import com.vo.TariffRequestVO;
import com.vo.TariffResponseVO;

@Service
public class ParcelRateServiceImpl implements ParcelRateService {

    protected Logger log = LoggerFactory.getLogger(ParcelRateServiceImpl.class);

    @Autowired
    private PostalServiceRepository postalServiceRepository;

    @Value("${cod.tp.not.together}")
    private String codTpNotTogether;

    @Value("${tariffAPI}")
    String tariffURL;

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

        } else if (finalInvoice != null && (subServicesIds == null || subServicesIds.size() == 0)) {
            log.debug("no subservices present xxxxxxxxxx");
            parcel.setCod(false);
            parcel.setToPay(false);

        }
        parcel.setInvoiceBreakup(finalInvoice);
        parcel.setRateCalculation(mainServiceRateCalculation);
        parcel.setRateCalculationJSON(getJSON(mainServiceRateCalculation));
        log.info("rate calculation has been added successfully");

        return parcel;
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

    private RateCalculation rateCalculationServiceId(long serviceId, Parcel parcel) {

        TariffRequestVO requestVO = new TariffRequestVO();
        RateCalculation rateCalculation = new RateCalculation();

        List<Long> subServicesId = new ArrayList<>();
        subServicesId = parcel.getSubServices();
        List<Services> subServices = postalServiceRepository.findByIdInAndStatus(subServicesId, Status.ACTIVE);
        List<String> subServiceCode = new ArrayList<>();
        subServices.forEach(s -> subServiceCode.add(s.getServiceCode()));

        Optional<Services> parentService = postalServiceRepository.findByIdAndStatus(serviceId, Status.ACTIVE);
        String serviceCode = parentService.get().getServiceCode();

        requestVO.setCOD_Value(subServiceCode.contains("COD")?parcel.getParcelDeclerationValue():0.0);
        requestVO.setINS_Value(subServiceCode.contains("IN")?parcel.getParcelDeclerationValue():0.0);
        requestVO.setBreadth(Float.toString(parcel.getParcelWidth()));
        requestVO.setDestinationpin(Integer.toString(parcel.getReceiverAddress().getPostalCode()));
        requestVO.setHeight(Float.toString(parcel.getParcelHeight()));
        requestVO.setLength(Float.toString(parcel.getParcelLength()));
        requestVO.setService(serviceCode);
        requestVO.setSourcepin(Integer.toString(parcel.getSenderAddress().getPostalCode()));
        requestVO.setWeight(Float.toString(parcel.getParcelDeadWeight()));

        log.info("TariffRequestVO [service=" + requestVO.getService() + ", sourcepin=" + requestVO.getSourcepin() + ", destinationpin=" + requestVO.getDestinationpin()
        + ", weight=" + requestVO.getWeight() + ", length=" + requestVO.getLength() + ", breadth=" + requestVO.getBreadth() + ", height=" + requestVO.getHeight()
        + ", INS_Value=" + requestVO.getINS_Value() + ", COD_Value=" + requestVO.getCOD_Value() + "]");

        List<TariffRequestVO> listOfRequest = new ArrayList<>();
        listOfRequest.add(requestVO);

        List<TariffResponseVO> listOfResponse = tariffApiRestCall(listOfRequest);
        TariffResponseVO response = listOfResponse.get(0);

        log.info("TariffResponseVO [Validation Status=" +response.getValidationStatus() + ", Chargeable Weight=" + response.getChargeableWeight()
        + ", Base Tariff=" + response.getBaseTariff() + ", POD/ACK Charges=" + response.getPOD_ACKCharges() + ", DDL Charges=" + response.getDDLCharges()
        + ", VP Charges=" + response.getVPCharges() + ", INS Charges=" + response.getINCharges() + ", Pickup Charges=" + response.getPickupCharges()
        + ", Service Tax=" + response.getServiceTax() + ", Total Tariff=" + response.getTotalTariff() + ", COD Charges=" + response.getCODCharges() + "]");

        String serviceName = parentService.get().getServiceName();
        InvoiceBreakup invoiceBreakup = new InvoiceBreakup();
        invoiceBreakup.setName(serviceName);
        invoiceBreakup.setPayableAmnt(Float.parseFloat(response.getTotalTariff()));
        invoiceBreakup.setPrice((float)(Math.round((Float.parseFloat(response.getBaseTariff()) + Float.parseFloat(response.getINCharges())
                + Float.parseFloat(response.getCODCharges()) + Float.parseFloat(response.getPickupCharges()))*100.0)/100.0));
        // invoiceBreakup.setPickupCharges(Float.parseFloat(response.getPickupCharges()));
        // invoiceBreakup.setCodCharges(Float.parseFloat(response.getCODCharges()));
        // invoiceBreakup.setInsCharges(Float.parseFloat(response.getINCharges()));
        invoiceBreakup
                .setSubTotal((float)(Math.round((Float.parseFloat(response.getBaseTariff()) + Float.parseFloat(response.getCODCharges())
                        + Float.parseFloat(response.getINCharges()) + Float.parseFloat(response.getPickupCharges())
                        + Float.parseFloat(response.getServiceTax()))*100.0)/100.0));
        invoiceBreakup.setTaxPercent(
                ((Float.parseFloat(response.getServiceTax())) * 100) / (Float.parseFloat(response.getBaseTariff())
                        + Float.parseFloat(response.getCODCharges()) + Float.parseFloat(response.getINCharges())
                        + Float.parseFloat(response.getPickupCharges()) + Float.parseFloat(response.getDDLCharges()))
                        + "%");
        invoiceBreakup.setTotalTax(Float.parseFloat(response.getServiceTax()));

        RateServiceCategory rateServiceCategory = new RateServiceCategory();
        rateServiceCategory.setExpectedDelivery(5L);
        rateServiceCategory.setPrice(Float.parseFloat(response.getBaseTariff()));
        rateServiceCategory.setParentServiceId(null);
        rateServiceCategory.setPriceType(PriceType.FLAT);
        rateServiceCategory.setServiceId(serviceId);

        rateCalculation.setErrorMsg(null);
        rateCalculation.setFinalAmount(Float.parseFloat(response.getBaseTariff()));
        rateCalculation.setInvoiceBreakup(invoiceBreakup);
        rateCalculation.setRateServiceCategory(rateServiceCategory);
        rateCalculation.setOperationStatus(OperationStatus.SUCCESS);
        rateCalculation.setSubServiceId(parcel.getSubServices());

        return rateCalculation;
    }

    public List<TariffResponseVO> tariffApiRestCall(List<TariffRequestVO> listOfRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(listOfRequest, headers);

        ParameterizedTypeReference<List<TariffResponseVO>> responseType = new ParameterizedTypeReference<List<TariffResponseVO>>() {
        };

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<TariffResponseVO>> tariffApiResponse = restTemplate.exchange(
                tariffURL + "/api/bulkGetTariff",
                HttpMethod.POST, httpEntity, responseType);
        List<TariffResponseVO> responseBodyList = tariffApiResponse.getBody();

        return responseBodyList;
    }

}
