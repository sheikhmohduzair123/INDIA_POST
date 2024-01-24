package com.vo;

public class SubServiceVo {

	private Long subServiceId;
	private String subServiceName;
	private Long parentServiceId;
	
	public Long getSubServiceId() {
		return subServiceId;
	}
	public void setSubServiceId(Long subServiceId) {
		this.subServiceId = subServiceId;
	}
	public String getSubServiceName() {
		return subServiceName;
	}
	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}
	public Long getParentServiceId() {
		return parentServiceId;
	}
	public void setParentServiceId(Long parentServiceId) {
		this.parentServiceId = parentServiceId;
	}
	
}
