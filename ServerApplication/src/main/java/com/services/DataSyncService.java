package com.services;

import com.domain.Control;
import com.domain.SyncTable;
import com.domain.User;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public interface DataSyncService {

	@Transactional
	long syncMasterData(byte[] bytes, Control control, User loginedUser) throws IOException;
	byte[] getMasterDataToSync(Control control,String token) throws Exception;
	SyncTable saveUpdateSyncTable(SyncTable syncTable);
	Control getClientControl();
	String getUpdatedSyncTable(SyncTable syncTable,String token) throws Exception;
    Timestamp getLastUpdatedSyncTime();
	public String inLastDay();
}
