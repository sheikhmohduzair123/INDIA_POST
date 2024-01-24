package com.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_account")
public class ClientAccount implements Serializable {

    @Id
    private String clientId;
    @Column(columnDefinition = "integer default 0",nullable = false)
    private long awbCounter;
    @Column(columnDefinition = "float default 0.0")
    private float avalBalance;
    @Column(columnDefinition = "float default 0.0")
    private float totalBalance;
    private Long totalParcelCount;

    public ClientAccount() {
    }

    public ClientAccount(String clientId) {
        this.clientId = clientId;
        this.awbCounter = 0l;
        this.avalBalance = 0.0f;
        this.totalBalance = 0.0f;
    }

    public Long getTotalParcelCount() {
        return totalParcelCount;
    }
    public void setTotalParcelCount(Long totalParcelCount) {
        this.totalParcelCount = totalParcelCount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getAwbCounter() {
        return awbCounter;
    }

    public void setAwbCounter(long awbCounter) {
        this.awbCounter = awbCounter;
    }

    public float getAvalBalance() {
        return avalBalance;
    }

    public void setAvalBalance(float avalBalance) {
        this.avalBalance = avalBalance;
    }

    public float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

}
