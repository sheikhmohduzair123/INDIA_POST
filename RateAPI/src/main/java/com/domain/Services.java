package com.domain;

import com.constants.Status;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="postal_services")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Status status;
    private Date createdOn;
    private Date updatedOn;
    private User createdBy;
    private User updatedBy;
    private String serviceName;
    private String serviceCode;
    private Long parentServiceId;
    private String exclusionPolicies;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //@Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(java.util.Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public java.util.Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @ManyToOne
    @JoinColumn(name="createdby_id", foreignKey=@ForeignKey(name="id"))
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @ManyToOne
    @JoinColumn(name="updatedby_id", foreignKey=@ForeignKey(name="id"))
    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name = "postal_service_name", length = 50)
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(Long parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

	public String getExclusionPolicies() {
		return exclusionPolicies;
	}

	public void setExclusionPolicies(String exclusionPolicies) {
		this.exclusionPolicies = exclusionPolicies;
	}
}
