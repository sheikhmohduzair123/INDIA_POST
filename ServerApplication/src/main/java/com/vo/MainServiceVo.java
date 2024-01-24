package com.vo;

public class MainServiceVo {

	private Long id;
	private String serviceName;
	private Long parentServiceId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Long getParentServiceId() {
		return parentServiceId;
	}
	public void setParentServiceId(Long parentServiceId) {
		this.parentServiceId = parentServiceId;
	}
	
	
	
}
