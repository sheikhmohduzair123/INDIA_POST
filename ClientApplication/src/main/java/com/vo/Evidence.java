package com.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.JsonUtils;

public class Evidence {
    private String data;
    private String dateTime;
    private int keyVersion;
    private String dataWithoutSignature;
    private String dataSignatureOnly;
    private double amount;

    public Evidence() {
    }

    public Evidence(String data, String dateTime, int keyVersion, String dataWithoutSignature, String dataSignatureOnly,
            double amount) {
        this.data = data;
        this.dateTime = dateTime;
        this.keyVersion = keyVersion;
        this.dataWithoutSignature = dataWithoutSignature;
        this.dataSignatureOnly = dataSignatureOnly;
        this.amount = amount;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(int keyVersion) {
        this.keyVersion = keyVersion;
    }

    public String getDataWithoutSignature() {
        return dataWithoutSignature;
    }

    public void setDataWithoutSignature(String dataWithoutSignature) {
        this.dataWithoutSignature = dataWithoutSignature;
    }

    public String getDataSignatureOnly() {
        return dataSignatureOnly;
    }

    public void setDataSignatureOnly(String dataSignatureOnly) {
        this.dataSignatureOnly = dataSignatureOnly;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Evidence [data=" + data + ", dateTime=" + dateTime + ", keyVersion=" + keyVersion
                + ", dataWithoutSignature=" + dataWithoutSignature + ", dataSignatureOnly=" + dataSignatureOnly
                + ", amount=" + amount + "]";
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(this);
    }

}
