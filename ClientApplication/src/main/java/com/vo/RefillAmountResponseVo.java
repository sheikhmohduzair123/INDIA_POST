package com.vo;

public class RefillAmountResponseVo {
    private String ngpId;
    private String customerTransactionId;
    private String correlationId;
    private String status;
    private double amount;
    private double balance;

    public RefillAmountResponseVo() {
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

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "RefillAmountResponseVo [ngpId=" + ngpId + ", customerTransactionId=" + customerTransactionId
                + ", correlationId=" + correlationId + ", status=" + status + ", amount=" + amount + ", balance="
                + balance + "]";
    }

}
