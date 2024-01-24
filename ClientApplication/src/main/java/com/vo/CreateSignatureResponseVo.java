package com.vo;

public class CreateSignatureResponseVo {
    private String ngpId;
    private String customerTransactionId;
    private String meterLicenseNumber;
    private String meterSerialNumber;
    private String additionalInfo;
    private Evidence evidence;
    private registerValues registerValues;

    public CreateSignatureResponseVo() {
    }

    public String getNgpId() {
        return ngpId;
    }

    public void setNgpId(String ngpId) {
        this.ngpId = ngpId;
    }

    public String getCustomerTransactionId() {
        return customerTransactionId;
    }

    public void setCustomerTransactionId(String customerTransactionId) {
        this.customerTransactionId = customerTransactionId;
    }

    public String getMeterLicenseNumber() {
        return meterLicenseNumber;
    }

    public void setMeterLicenseNumber(String meterLicenseNumber) {
        this.meterLicenseNumber = meterLicenseNumber;
    }

    public String getMeterSerialNumber() {
        return meterSerialNumber;
    }

    public void setMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public registerValues getRegisterValues() {
        return registerValues;
    }

    public void setRegisterValues(registerValues registerValues) {
        this.registerValues = registerValues;
    }

    @Override
    public String toString() {
        return "CreateSignatureResponseVo [ngpId=" + ngpId + ", customerTransactionId=" + customerTransactionId
                + ", meterLicenseNumber=" + meterLicenseNumber + ", meterSerialNumber=" + meterSerialNumber
                + ", additionalInfo=" + additionalInfo + ", evidence=" + evidence + ", registerValues=" + registerValues
                + "]";
    }

}
