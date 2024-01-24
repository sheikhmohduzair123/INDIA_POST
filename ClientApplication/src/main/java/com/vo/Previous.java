package com.vo;

public class Previous {
    private double ascendingRegister;
    private double descendingRegister;

    public Previous() {
    }

    public Previous(double ascendingRegister, double descendingRegister) {
        this.ascendingRegister = ascendingRegister;
        this.descendingRegister = descendingRegister;
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

    @Override
    public String toString() {
        return "Previous [ascendingRegister=" + ascendingRegister + ", descendingRegister=" + descendingRegister + "]";
    }

}
