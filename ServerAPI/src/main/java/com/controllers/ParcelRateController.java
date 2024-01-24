package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.domain.Parcel;
import com.services.ParcelRateService;

@RestController
@RequestMapping("/parcel")
public class ParcelRateController {

    protected Logger log = LoggerFactory.getLogger(ParcelRateController.class);

    @Autowired
    private ParcelRateService parcelRateService;

    @RequestMapping(value = "/getRateForParcel", method = RequestMethod.POST)
    public Parcel calculateRateForParcel(@RequestBody Parcel parcel) {
    	log.info("inside calculateRateForParcel");
    	log.debug("parcel:"+parcel);

    	Parcel p = parcelRateService.getRate(parcel);

        log.debug("parcel return:"+p);
        return p;
    }
    
}
