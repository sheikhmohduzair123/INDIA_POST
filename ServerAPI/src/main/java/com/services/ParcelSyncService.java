package com.services;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.domain.Control;
import com.domain.SyncTable;

public interface ParcelSyncService {

	@Transactional
	long syncClientData(byte[] bytes) throws IOException;
    Control getClientControl();
	SyncTable saveUpdateSyncTable(SyncTable syncTable);
	public SyncTable syncDataSuccess(SyncTable syncTable);
}
