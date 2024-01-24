package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.MeterBalanceSyncService;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    MeterBalanceSyncService meterBalanceSyncService;

    @GetMapping("/account")
    public String getClientDetails() {

        boolean check = meterBalanceSyncService.syncBalance("eaaf0f04-7818-4eeb-8476-e670000dcac0");

        return (check == true ? "ok response" : "Internal server error");
    }

}
