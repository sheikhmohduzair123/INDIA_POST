package com.vo;

import java.util.Date;

public class CreateSignatureRequestVo {

    private String customerTransactionId;
    private String ngpId;
    private String productId;
    private int dataVersion;
    private String data;
    private double amount;
    private String transactionDate;
    private String countryId;
    private String productGroupCode;
    private String partnerId;
    private String currency;

    public CreateSignatureRequestVo() {
    }

    public CreateSignatureRequestVo(String customerTransactionId, String ngpId, String productId, int dataVersion,
            String data, double amount, String transactionDate, String countryId, String productGroupCode,
            String partnerId, String currency) {
        this.customerTransactionId = customerTransactionId;
        this.ngpId = ngpId;
        this.productId = productId;
        this.dataVersion = dataVersion;
        this.data = data;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.countryId = countryId;
        this.productGroupCode = productGroupCode;
        this.partnerId = partnerId;
        this.currency = currency;
    }

    public String getCustomerTransactionId() {
        return customerTransactionId;
    }

    public void setCustomerTransactionId(String customerTransactionId) {
        this.customerTransactionId = customerTransactionId;
    }

    public String getNgpId() {
        return ngpId;
    }

    public void setNgpId(String ngpId) {
        this.ngpId = ngpId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(int dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CreateSignatureRequestVo [customerTransactionId=" + customerTransactionId + ", ngpId=" + ngpId
                + ", productId=" + productId + ", dataVersion=" + dataVersion + ", data=" + data + ", amount=" + amount
                + ", transactionDate=" + transactionDate + ", countryId=" + countryId + ", productGroupCode="
                + productGroupCode + ", partnerId=" + partnerId + ", currency=" + currency + "]";
    }

}
