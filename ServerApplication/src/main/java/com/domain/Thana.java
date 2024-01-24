package com.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="THANA")
public class Thana extends BaseClass {

	private static final long serialVersionUID = 1L;
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
	@NotNull
	@Size(min = 2,max = 50)
	private String thana;
	
	@Valid
	private District district;
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getThana() {
		return thana;
	}
	public void setThana(String thana) {
		this.thana = thana;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

}
