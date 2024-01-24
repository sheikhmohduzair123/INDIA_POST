package com.vo;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.domain.Role;

public class CreateUserVo {

	@Pattern(regexp = "^[a-zA-Z][a-zA-Z\\s?\\.?\\-?]*$", message="{spacial.character.notAllowed}")
	@NotNull
    private String name;

	//@Pattern(regexp = "^(.+)@(.+)$", message="{email.not.valid}")
	@Email
	@NotNull
    private String email;

    private String username;

    @NotNull
    @Pattern(regexp = "^\\d{4}[\\-\\/\\s]?((((0[13578])|(1[02]))[\\-\\/\\s]?(([0-2][0-9])|(3[01])))|(((0[469])|(11))[\\-\\/\\s]?(([0-2][0-9])|(30)))|(02[\\-\\/\\s]?[0-2][0-9]))$", message="{date.format.invalid}")
    private String dateOfBirth;

    @Pattern(regexp = "^$|[0-9 ]{6}$", message="{some.error.occoured}")
    @NotNull
    private String postalCode;

    @Pattern(regexp = "^$|[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
	private String zone;

    @Pattern(regexp = "^$|[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
	private String division;

    @Pattern(regexp = "^$|[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
	private String district;

    @Pattern(regexp = "^$|[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\(\\)\\*]*$", message="{spacial.character.notAllowed}")
    @NotNull
	private String thana;

    @Pattern(regexp = "^$|[A-Za-z0-9'\\ \\.\\/\\s\\,\\-]*$", message="{spacial.character.notAllowed}")
    @NotNull
    private String role;

	@Pattern(regexp = "^$|[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\*\\(\\)]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String subOffice;

    @Pattern(regexp = "^[a-zA-Z0-9, ]*$", message="{spacial.character.notAllowed}")
	private String rmsId;

	public String getName()
	{
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
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
	public String getRmsId() {
		return rmsId;
	}
	public void setRmsId(String rmsId) {
		this.rmsId = rmsId;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
