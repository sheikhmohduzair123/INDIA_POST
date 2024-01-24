package com.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TariffRequestVO {
    private String service;
    private String sourcepin;
    private String destinationpin;
    private String weight;
    private String length;
    private String breadth;
    private String height;
    private double INS_Value;
    private double COD_Value;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSourcepin() {
        return sourcepin;
    }

    public void setSourcepin(String sourcepin) {
        this.sourcepin = sourcepin;
    }

    public String getDestinationpin() {
        return destinationpin;
    }

    public void setDestinationpin(String destinationpin) {
        this.destinationpin = destinationpin;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBreadth() {
        return breadth;
    }

    public void setBreadth(String breadth) {
        this.breadth = breadth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
    public double getINS_Value() {
        return INS_Value;
    }

    public void setINS_Value(double iNS_Value) {
        this.INS_Value = iNS_Value;
    }

    public double getCOD_Value() {
        return COD_Value;
    }

    public void setCOD_Value(double cOD_Value) {
        this.COD_Value = cOD_Value;
    }

    @Override
    public String toString() {
        return "TariffRequestVO [service=" + service + ", sourcepin=" + sourcepin + ", destinationpin=" + destinationpin
                + ", weight=" + weight + ", length=" + length + ", breadth=" + breadth + ", height=" + height
                + ", INS_Value=" + INS_Value + ", COD_Value=" + COD_Value + "]";
    }

    
}