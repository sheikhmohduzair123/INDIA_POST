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
    @Column(columnDefinition = "integer default = 0")
    private Long awbCounter;
    @Column(columnDefinition = "integer default = 0")
    private Float avalBalance;
    @Column(columnDefinition = "integer default =0")
    private Float totalBalance;

    private Long totalParcelCount;

    public Long getTotalParcelCount() {
        return totalParcelCount;
    }

    public void setTotalParcelCount(Long totalParcelCount) {
        this.totalParcelCount = totalParcelCount;
    }

    // ClientAccount() {
    // }

    // public ClientAccount(String clientId) {
    //     this.clientId = clientId;
    //     this.awbCounter = 0l;
    //     this.avalBalance = 0.0f;
    //     this.totalBalance = 0.0f;
    // }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getAwbCounter() {
        return awbCounter;
    }

    public void setAwbCounter(Long awbCounter) {
        this.awbCounter = awbCounter;
    }

    public Float getAvalBalance() {
        return avalBalance;
    }

    public void setAvalBalance(Float avalBalance) {
        this.avalBalance = avalBalance;
    }

    public Float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Float totalBalance) {
        this.totalBalance = totalBalance;
    }

    
}
