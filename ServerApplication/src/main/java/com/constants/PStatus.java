package com.constants;

public enum PStatus {

	IN("In"),OUT("Out"),BAGGED("Bagged"),REBAGGED("Rebagged"),UNBAGGED("Unbagged"),LOST("Lost"),OUT_FOR_DELIVERY("Out For Delivery"),
	DELIVERED("Delivered"),RETURNED("Returned"),UNDELIVERED("Undelivered"),WILL_TRY_ANOTHER_TIME("Will Try Another Time");
	;

	private final String name;

	 PStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
