package com.constants;

public enum RmsType {
	
	GPO("GPO"), SO("SO"), RMS("RMS");
	
	private final String rmsType;
	
	 RmsType(String rmsType) {
		this.rmsType = rmsType;
	}
	 
	public String getRmsType() {
		return rmsType;
	}

}
