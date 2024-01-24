package com.constants;


public enum ReportType {

	PARCEL_REPORT("Parcel Report");

	private final String name;
	public String getName() {
		return name;
	}

	ReportType(String name){
		this.name=name;
	}

	public static void main(String[] args) {
		for(ReportType r: ReportType.values()){
			System.out.println(r.ordinal());
		}
	}
}
