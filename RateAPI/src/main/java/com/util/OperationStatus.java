package com.util;


public enum OperationStatus{
	SUCCESS(0, "SUCCESS"), 
	INVALID_REQUEST(1, "INVALID_REQUEST"),
	INTERNAL_SERVER_ERROR(2, "INTERNAL_SERVER_ERROR"),
	ID_NOT_PRESENT_IN_DB(3,"ID_NOT_PRESENT_IN_DB"),
	HIGH_WEIGHT_NOT_ACCEPTABLE(4, "HIGH_WEIGHT_NOT_ACCEPTABLE"),
	HIGH_VALUE_NOT_ACCEPTABLE(5, "HIGH_VALUE_NOT_ACCEPTABLE"),
	DATA_NOT_PRESENT(6, "DATA_NOT_PRESENT"),
	LOCATION_DATA_NOT_PRESENT(7, "LOCATION_DATA_NOT_PRESENT"),
	WEIGHT_DATA_NOT_PRESENT(8, "WEIGHT_DATA_NOT_PRESENT"),
	PRICE_DATA_NOT_PRESENT(9, "PRICE_DATA_NOT_PRESENT"),
	COD_TP_SELECTED(10, "COD_TP_SELECTED");
	
	private static OperationStatus[] all = OperationStatus.values();
    private final int id;
    private final String type;
    
    private OperationStatus(int id, String type) {
        this.id = id;
        this.type = type;
    }
    public int getOperationStatusId() {
        return this.id;
    }
    
    public String getOperationStatusString() {
        return this.type;
    }
    
	//method to create OperationStatus by type string
	public static OperationStatus createOperationStatus(String type){
		for(OperationStatus rt : all){
            if(rt.toString().equals(type)){
                return rt;
            }
        }
		return null;
	}
	
	//method to create OperationStatus by type id
	public static OperationStatus createOperationStatus(int status){
		for(OperationStatus rt : all){
            if(rt.getOperationStatusId() == status){
                return rt;
            }
        }
		return null;
	}
}