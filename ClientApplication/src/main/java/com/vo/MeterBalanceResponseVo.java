package com.vo;

public class MeterBalanceResponseVo {
    private String ngpId;
    private double balance;
    private double ascendingRegister;
    private double descendingRegister;
    private double controlSum;
    private int pieceCount;

    public MeterBalanceResponseVo() {
    }

    public String getNgpId() {
        return ngpId;
    }

    public void setNgpId(String ngpId) {
        this.ngpId = ngpId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAscendingRegister() {
        return ascendingRegister;
    }

    public void setAscendingRegister(double ascendingRegister) {
        this.ascendingRegister = ascendingRegister;
    }

    public double getDescendingRegister() {
        return descendingRegister;
    }

    public void setDescendingRegister(double descendingRegister) {
        this.descendingRegister = descendingRegister;
    }

    public double getControlSum() {
        return controlSum;
    }

    public void setControlSum(double controlSum) {
        this.controlSum = controlSum;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    @Override
    public String toString() {
        return "MeterBalanceResponseVo [ngpId=" + ngpId + ", balance=" + balance + ", ascendingRegister="
                + ascendingRegister + ", descendingRegister=" + descendingRegister + ", controlSum=" + controlSum
                + ", pieceCount=" + pieceCount + "]";
    }

}
