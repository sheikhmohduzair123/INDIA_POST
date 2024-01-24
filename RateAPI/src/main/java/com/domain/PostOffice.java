package com.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="POST_OFFICE")
public class PostOffice implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private int postalCode;
	private String postOfficeName;
	private Address address;
	private String type;
	
	private Set<PostOfficeCounter> postOfficeCounters;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostOfficeName() {
		return postOfficeName;
	}

	public void setPostOfficeName(String postOfficeName) {
		this.postOfficeName = postOfficeName;
	}

	@Embedded
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonManagedReference
	@OneToMany(fetch=FetchType.LAZY, mappedBy="postOffice")
	public Set<PostOfficeCounter> getPostOfficeCounters() {
		return postOfficeCounters;
	}

	public void setPostOfficeCounters(Set<PostOfficeCounter> postOfficeCounters) {
		this.postOfficeCounters = postOfficeCounters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((postOfficeCounters == null) ? 0 : postOfficeCounters
						.hashCode());
		result = prime * result
				+ ((postOfficeName == null) ? 0 : postOfficeName.hashCode());
		result = prime * result + postalCode;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof PostOffice)) {
			return false;
		}
		PostOffice other = (PostOffice) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (postOfficeCounters == null) {
			if (other.postOfficeCounters != null) {
				return false;
			}
		} else if (!postOfficeCounters.equals(other.postOfficeCounters)) {
			return false;
		}
		if (postOfficeName == null) {
			if (other.postOfficeName != null) {
				return false;
			}
		} else if (!postOfficeName.equals(other.postOfficeName)) {
			return false;
		}
		if (postalCode != other.postalCode) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
	

}
