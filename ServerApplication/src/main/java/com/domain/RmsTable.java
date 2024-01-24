package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="rms_table")
public class RmsTable extends BaseClass{

	    private static final long serialVersionUID = 1L;
	    
	    @Pattern(regexp = "^[a-zA-Z0-9]+(\\s+[a-zA-Z0-9]+)*$", message="{spacial.character.notAllowed}")
	    @NotNull
	    @Size(min = 2 , max = 15)
	    private String RmsName;
	    
	    @Pattern(regexp = "^[A-Za-z0-9'\\ \\.\\/\\s\\,\\-]*$", message="{spacial.character.notAllowed}")
	    @NotNull
	    private String RmsType;
	    
		@Range(min = 10000000000L, max = 99999999999L)
	    private Long RmsMobileNumber;
	    
	    @Valid
	    private Address RmsAddress;
	    private Long id;

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}

		public String getRmsName() {
			return RmsName;
		}
		public void setRmsName(String rmsName) {
			RmsName = rmsName;
		}
		public String getRmsType() {
			return RmsType;
		}
		public void setRmsType(String rmsType) {
			RmsType = rmsType;
		}
		public Long getRmsMobileNumber() {
			return RmsMobileNumber;
		}
		public void setRmsMobileNumber(Long rmsMobileNumber) {
			RmsMobileNumber = rmsMobileNumber;
		}
		public Address getRmsAddress() {
			return RmsAddress;
		}
		public void setRmsAddress(Address rmsAddress) {
			RmsAddress = rmsAddress;
		}

}
