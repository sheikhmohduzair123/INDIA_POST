package com.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SyncTable")
public class SyncTable {
	private static final long serialVersionUID = 1L;

	private Long syncId;
	private Timestamp syncStartTimestamp;
	private String clientId;
	private String syncType;
	private String synceStatus;
	private Timestamp syncUpdatedTimestamp;
	private long noOfRecordsUpdated;
	private long noOfRecords;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getSyncId() {
		return syncId;
	}
	public void setSyncId(Long syncId) {
		this.syncId = syncId;
	}
	public Timestamp getSyncStartTimestamp() {
		return syncStartTimestamp;
	}
	public void setSyncStartTimestamp(Timestamp syncStartTimestamp) {
		this.syncStartTimestamp = syncStartTimestamp;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String string) {
		this.clientId = string;
	}
	public String getSyncType() {
		return syncType;
	}
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	public String getSynceStatus() {
		return synceStatus;
	}
	public void setSynceStatus(String synceStatus) {
		this.synceStatus = synceStatus;
	}
	public Timestamp getSyncUpdatedTimestamp() {
		return syncUpdatedTimestamp;
	}
	public void setSyncUpdatedTimestamp(Timestamp syncUpdatedTimestamp) {
		this.syncUpdatedTimestamp = syncUpdatedTimestamp;
	}

	public long getNoOfRecordsUpdated() {
		return noOfRecordsUpdated;
	}

	public void setNoOfRecordsUpdated(long noOfRecordsUpdated) {
		this.noOfRecordsUpdated = noOfRecordsUpdated;
	}

	public long getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(long noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
}
