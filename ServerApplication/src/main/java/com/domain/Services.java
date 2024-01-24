package com.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="postal_services")
public class Services extends BaseClass {

    private static final long serialVersionUID = 1L;
    
    @Pattern(regexp = "^[a-zA-Z0-9]+(\\s+[a-zA-Z0-9]+)*$",message = "{spacial.character.notAllowed}")
    @NotNull
    private String serviceName;
    
    @Pattern(regexp = "^[a-zA-Z0-9]+(\\s+[a-zA-Z0-9]+)*$",message = "{spacial.character.notAllowed}")
    @NotNull
    private String serviceCode;
    private Long parentServiceId;
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
