package com.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Embeddable
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Range(min = 100000, max = 999999)
	private int postalCode;
	
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String zone;
	
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String division;
	
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String district;
	
	@NotNull
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\*\\(\\)]*$", message="{spacial.character.notAllowed}")
	private String thana;
	
	@Pattern(regexp = "^[a-zA-Z][A-Za-z0-9'\\ \\.\\-\\(\\)]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String subOffice;

	@Pattern(regexp = "^[A-Za-z0-9'\\ \\.\\/\\s\\,\\-\\(\\)]*$", message="{spacial.character.notAllowed}")
	@NotNull
	private String addressLine1;

	@Size(min=0, max=30)
	@Pattern(regexp = "^(?:[A-Za-z0-9][A-Za-z0-9'\\\\ \\\\.\\\\/\\\\s\\\\,\\\\-]*)?$", message="{spacial.character.notAllowed}")
	private String addressLine2;

	@Size(min=0, max=30)
	@Pattern(regexp = "^(?:[A-Za-z0-9][A-Za-z0-9'\\ \\.\\/\\s\\,\\-]*)?$", message="{spacial.character.notAllowed}")
	private String landmark;

	@Pattern(regexp = "^$|[A-Za-z0-9'\\ \\.\\/\\s\\,\\-]*$", message="{spacial.character.notAllowed}")
	private String country;

	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
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
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String adddressLine1) {
		this.addressLine1 = adddressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String adddressLine2) {
		this.addressLine2 = adddressLine2;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = prime * result
				+ ((addressLine2 == null) ? 0 : addressLine2.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((division == null) ? 0 : division.hashCode());
		result = prime * result
				+ ((landmark == null) ? 0 : landmark.hashCode());
		result = prime * result + postalCode;
		result = prime * result
				+ ((subOffice == null) ? 0 : subOffice.hashCode());
		result = prime * result + ((thana == null) ? 0 : thana.hashCode());
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		if (addressLine1 == null) {
			if (other.addressLine1 != null) {
				return false;
			}
		} else if (!addressLine1.equals(other.addressLine1)) {
			return false;
		}
		if (addressLine2 == null) {
			if (other.addressLine2 != null) {
				return false;
			}
		} else if (!addressLine2.equals(other.addressLine2)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (district == null) {
			if (other.district != null) {
				return false;
			}
		} else if (!district.equals(other.district)) {
			return false;
		}
		if (division == null) {
			if (other.division != null) {
				return false;
			}
		} else if (!division.equals(other.division)) {
			return false;
		}
		if (landmark == null) {
			if (other.landmark != null) {
				return false;
			}
		} else if (!landmark.equals(other.landmark)) {
			return false;
		}
		if (postalCode != other.postalCode) {
			return false;
		}
		if (subOffice == null) {
			if (other.subOffice != null) {
				return false;
			}
		} else if (!subOffice.equals(other.subOffice)) {
			return false;
		}
		if (thana == null) {
			if (other.thana != null) {
				return false;
			}
		} else if (!thana.equals(other.thana)) {
			return false;
		}
		if (zone == null) {
			if (other.zone != null) {
				return false;
			}
		} else if (!zone.equals(other.zone)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return postalCode + ", " + (zone != null ? zone + ", " : "")
				+ (division != null ? division + ", " : "")
				+ (district != null ? district + ", " : "")
				+ (thana != null ? thana + ", " : "")
				+ (subOffice != null ? subOffice + ", " : "")
				+ (addressLine1 != null ? addressLine1 + ", " : "")
				+ (addressLine2 != null ? addressLine2 + ", " : "")
				+ (landmark != null ? landmark + ", " : "")
				+ (country != null ? country : "");
	}


}
