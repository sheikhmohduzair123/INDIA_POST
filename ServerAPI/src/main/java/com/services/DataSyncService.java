package com.services;

import com.domain.SyncTable;

public interface DataSyncService {

	byte[] syncMasterDataOnClient(String clientId, long serverTimestamp);
	SyncTable syncDataSuccess(SyncTable syncTable);
	byte[] getUserListForPostalCode(String postalcode, long serverTimestamp, long postalcode2);

}
