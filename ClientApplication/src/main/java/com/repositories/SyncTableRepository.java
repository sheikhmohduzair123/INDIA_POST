package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import com.domain.SyncTable;

public interface SyncTableRepository extends JpaRepository<SyncTable, Long>{
    List<SyncTable> findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(String type, String clientId);

	List<SyncTable> findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(String string, String clientId);


}
