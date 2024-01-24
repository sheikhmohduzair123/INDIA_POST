package com.service;

import java.lang.ModuleLayer.Controller;

import com.controllers.ParcelController;

public class Sample {
    public static void main(String[] args) {
    ParcelController controller = new ParcelController();
    controller.calculateVolumetricWeght(1.0f, 2.0f, 2.0f, "123");
    }
}
