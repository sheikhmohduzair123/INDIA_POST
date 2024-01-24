package com.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Control")
public class Control extends BaseClass{

private static final long serialVersionUID = 1L;

	private Long id;
	private Timestamp ClientMasterDataSyncTimestamp;
	private Timestamp ServerMasterDataUpdateTimestamp;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getClientMasterDataSyncTimestamp() {
		return ClientMasterDataSyncTimestamp;
	}
	public void setClientMasterDataSyncTimestamp(Timestamp clientMasterDataSyncTimestamp) {
		ClientMasterDataSyncTimestamp = clientMasterDataSyncTimestamp;
	}
	public Timestamp getServerMasterDataUpdateTimestamp() {
		return ServerMasterDataUpdateTimestamp;
	}
	public void setServerMasterDataUpdateTimestamp(Timestamp serverMasterDataUpdateTimestamp) {
		ServerMasterDataUpdateTimestamp = serverMasterDataUpdateTimestamp;
	}


}

