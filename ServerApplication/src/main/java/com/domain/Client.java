package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="CLIENT")
public class Client extends BaseClass {

    private static final long serialVersionUID = 1L;
    
    @Pattern(regexp = "^[a-zA-Z0-9]+(\\s+[a-zA-Z0-9]+)*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String clientName;
    
    @NotNull
    @Range(min = 100000, max = 999999)
    private int postalCode;
    private String clientStatus;
    
    @Pattern(regexp = "^$|[a-zA-Z0-9]+(\\s+[a-zA-Z0-9]+)*$", message="{spacial.character.notAllowed}")
    private String remarks;		//to add reason for deactivating
    private String clientId;	//postalCode+currentTimestamp
    private String clientToken; //uuid
    @Size(min = 10,max = 50)
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+'*$",message = "{spacial.character.notAllowed}")
    private String licenseNumber;
    private String machineId;
    
    @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String division;
    
    @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String district;
    
    @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\*]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String thana;
    
    @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\(\\)]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String subOffice;
    
    @Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String zone;

    @Pattern(regexp = "^[A-Z]{2}[0-9]{9}[A-Z]{2}$", message = "{spacial.character.notAllowed}")
    @NotNull
    private String fromAWB;
    
    @Column(columnDefinition = "integer default 0")
    private int counterAWB;

    @Pattern(regexp = "^[A-Z]{2}[0-9]{9}[A-Z]{2}$", message = "{spacial.character.notAllowed}")
    @NotNull
    private String toAWB;

    private String password;
    private int postalCounter;
	private Long id;

    private String customerCode;
    private String customerType;
    private String ngpId; //for active a meter
    
    public String getNgpId() {
        return ngpId;
    }

    public void setNgpId(String ngpId) {
        this.ngpId = ngpId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String data) {
        this.clientId = data;
    }
    public String getClientToken() {
        return clientToken;
    }
    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getSubOffice() {
        return subOffice;
    }

    public void setSubOffice(String subOffice) {
        this.subOffice = subOffice;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public int getPostalCounter() {
		return postalCounter;
	}

    public void setPostalCounter(int postalCounter) {
        this.postalCounter = postalCounter;
    }
    public String getFromAWB() {
        return fromAWB;
    }
    public void setFromAWB(String fromAWB) {
        this.fromAWB = fromAWB;
    }
    public int getCounterAWB() {
        return counterAWB;
    }
    public void setCounterAWB(int counterAWB) {
        this.counterAWB = counterAWB;
    }
    public String getToAWB() {
        return toAWB;
    }
    public void setToAWB(String toAWB) {
        this.toAWB = toAWB;
    }
    
    

}
