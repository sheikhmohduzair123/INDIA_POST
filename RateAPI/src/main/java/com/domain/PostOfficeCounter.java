package com.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="POST_OFFICE_COUNTER")
public class PostOfficeCounter implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String counterName;//Counter + "-" + id
	private String systemIP;
	private String systemName;
	private String tokenKey;
	private PostOffice postOffice;//ManyToOne
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCounterName() {
		return counterName;
	}
	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}
	public String getSystemIP() {
		return systemIP;
	}
	public void setSystemIP(String systemIP) {
		this.systemIP = systemIP;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "postOffice", nullable = false, referencedColumnName="id")
	public PostOffice getPostOffice() {
		return postOffice;
	}
	public void setPostOffice(PostOffice postOffice) {
		this.postOffice = postOffice;
	}
	
	public String getTokenKey() {
		return tokenKey;
	}
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	@Override
	public String toString() {
		return (id != null ? id + ", " : "")
				+ (counterName != null ? counterName + ", " : "")
				+ (systemIP != null ? systemIP + ", " : "")
				+ (systemName != null ? systemName : "");
	}


}
