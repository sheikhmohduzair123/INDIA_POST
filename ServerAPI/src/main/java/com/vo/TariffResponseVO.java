package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TariffResponseVO {
    private String validationStatus;
    private String chargeableWeight;
    private String baseTariff;
    private String podAckCharges;
    private String ddlCharges;
    private String vpCharges;
    private String inCharges;
    private String pickupCharges;
    private String serviceTax;
    private String totalTariff;
    private String codCharges;

    @JsonProperty("Validation Status")
    public String getValidationStatus() {
        return validationStatus;
    }

    @JsonProperty("Chargeable Weight")
    public String getChargeableWeight() {
        return chargeableWeight;
    }

    @JsonProperty("Base Tariff")
    public String getBaseTariff() {
        return baseTariff;
    }

    @JsonProperty("POD/ACK Charges")
    public String getPOD_ACKCharges() {
        return podAckCharges;
    }

    @JsonProperty("DDL Charges")
    public String getDDLCharges() {
        return ddlCharges;
    }

    @JsonProperty("VP Charges")
    public String getVPCharges() {
        return vpCharges;
    }

    @JsonProperty("INS Charges")
    public String getINCharges() {
        return inCharges;
    }

    @JsonProperty("Pickup Charges")
    public String getPickupCharges() {
        return pickupCharges;
    }

    @JsonProperty("Service Tax")
    public String getServiceTax() {
        return serviceTax;
    }

    @JsonProperty("Total Tariff")
    public String getTotalTariff() {
        return totalTariff;
    }

    @JsonProperty("COD Charges")
    public String getCODCharges() {
        return codCharges;
    }

    @JsonProperty("Validation Status")
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    @JsonProperty("Chargeable Weight")
    public void setChargeableWeight(String chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
    }

    @JsonProperty("Base Tariff")
    public void setBaseTariff(String baseTariff) {
        this.baseTariff = baseTariff;
    }

    @JsonProperty("POD/ACK Charges")
    public void setPodAckCharges(String podAckCharges) {
        this.podAckCharges = podAckCharges;
    }

    @JsonProperty("DDL Charges")
    public void setDdlCharges(String ddlCharges) {
        this.ddlCharges = ddlCharges;
    }

    @JsonProperty("VP Charges")
    public void setVpCharges(String vpCharges) {
        this.vpCharges = vpCharges;
    }

    @JsonProperty("INS Charges")
    public void setInCharges(String inCharges) {
        this.inCharges = inCharges;
    }

    @JsonProperty("Pickup Charges")
    public void setPickupCharges(String pickupCharges) {
        this.pickupCharges = pickupCharges;
    }

    @JsonProperty("Service Tax")
    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    @JsonProperty("Total Tariff")
    public void setTotalTariff(String totalTariff) {
        this.totalTariff = totalTariff;
    }

    @JsonProperty("COD Charges")
    public void setCodCharges(String codCharges) {
        this.codCharges = codCharges;
    }

    @Override
    public String toString() {
        return "TariffResponseVO [validationStatus=" + validationStatus + ", chargeableWeight=" + chargeableWeight
                + ", baseTariff=" + baseTariff + ", podAckCharges=" + podAckCharges + ", ddlCharges=" + ddlCharges
                + ", vpCharges=" + vpCharges + ", inCharges=" + inCharges + ", pickupCharges=" + pickupCharges
                + ", serviceTax=" + serviceTax + ", totalTariff=" + totalTariff + ", codCharges=" + codCharges + "]";
    }

    


}
