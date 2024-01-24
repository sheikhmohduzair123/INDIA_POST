package com.services;

import com.domain.Control;
import com.domain.SyncTable;

public interface ParcelSyncService {
	byte[] syncClientDataOnMaster(String clientId);
	public SyncTable sendDataToSync(String token) throws Exception;
//	public String getUpdatedSyncTable(SyncTable syncTable, String token) throws Exception;
	boolean saveSyncTable(SyncTable synctable);
}
