package com.vo;

import java.sql.Timestamp;

public class RateReportData {
    String subService;
    String serviceName;
    String serviceWeightDependency;
    String serviceValueDependency;
    String serviceLocationDependency;
    Timestamp lastSync;
    float price;


    public String getSubService() {
        return subService;
    }

    public void setSubService(String subService) {
        this.subService = subService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceWeightDependency() {
        return serviceWeightDependency;
    }

    public void setServiceWeightDependency(String serviceWeightDependency) {
        this.serviceWeightDependency = serviceWeightDependency;
    }

    public String getServiceValueDependency() {
        return serviceValueDependency;
    }

    public void setServiceValueDependency(String serviceValueDependency) {
        this.serviceValueDependency = serviceValueDependency;
    }

    public String getServiceLocationDependency() {
        return serviceLocationDependency;
    }

    public void setServiceLocationDependency(String serviceLocationDependency) {
        this.serviceLocationDependency = serviceLocationDependency;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getLastSync() {
        return lastSync;
    }

    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync;
    }
}
