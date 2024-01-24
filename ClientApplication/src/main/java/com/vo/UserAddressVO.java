package com.vo;

public class UserAddressVO {
    private Long id;
    private String division;
    private String district;
    private String thana;
    private String subOffice;
    private int postalCode;
    private String zone;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String mobileNumber;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public int getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    
}
