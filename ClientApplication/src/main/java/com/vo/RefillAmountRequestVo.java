package com.vo;

public class RefillAmountRequestVo {
    private String productId;
    private String customerTransactionId;
    private String ngpId;
    private double amount;

    public RefillAmountRequestVo() {
    }

    public RefillAmountRequestVo(String productId, String customerTransactionId, String ngpId, double amount) {
        this.productId = productId;
        this.customerTransactionId = customerTransactionId;
        this.ngpId = ngpId;
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RefillAmountRequestVo [productId=" + productId + ", customerTransactionId=" + customerTransactionId
                + ", ngpId=" + ngpId + ", amount=" + amount + "]";
    }

}
