package com.constants;

public enum DispatchStatus {
	DELIVERED("Delivered"),RETURNED("Returned"),UNDELIVERED("Undelivered"),WILL_TRY_ANOTHER_TIME("Will Try Another Time");
	
	
	private final String dispatchStatus;
	
	DispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}
	 
	public String getDispatchStatus() {
		return dispatchStatus;
	}

	

	
}
