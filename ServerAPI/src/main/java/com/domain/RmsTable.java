package com.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rms_table")
public class RmsTable extends BaseClass{

	    private static final long serialVersionUID = 1L;
	    private String RmsName;
	    private String RmsType;
	    private Long RmsMobileNumber;
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
