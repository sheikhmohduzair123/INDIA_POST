package com.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
public class Client extends BaseClass {

    private static final long serialVersionUID = 1L;
    private String clientName;
    private int postalCode;
    private String clientStatus;
    private String remarks; // to add reason for deactivating
    private String clientId; // postalCode+currentTimestamp
    private String clientToken; // uuid
    private String licenseNumber;
    private String machineId;
    private String division;
    private String district;
    private String thana;
    private String subOffice;
    private String zone;
    private String password;
    private int postalCounter;
    private Long id;
    private String fromAWB;
    private int counterAWB;
    private String toAWB;

    private String customerCode;
    private String customerType;

    private String ngpId;
    
    public String getNgpId() {
        return ngpId;
    }

    public void setNgpId(String ngpId) {
        this.ngpId = ngpId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String data) {
        this.clientId = data;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getSubOffice() {
        return subOffice;
    }

    public void setSubOffice(String subOffice) {
        this.subOffice = subOffice;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPostalCounter() {
        return postalCounter;
    }

    public void setPostalCounter(int postalCounter) {
        this.postalCounter = postalCounter;
    }

    public String getFromAWB() {
        return fromAWB;
    }

    public void setFromAWB(String fromAWB) {
        this.fromAWB = fromAWB;
    }

    public int getCounterAWB() {
        return counterAWB;
    }

    public void setCounterAWB(int counterAWB) {
        this.counterAWB = counterAWB;
    }

    public String getToAWB() {
        return toAWB;
    }

    public void setToAWB(String toAWB) {
        this.toAWB = toAWB;
    }

    

}
