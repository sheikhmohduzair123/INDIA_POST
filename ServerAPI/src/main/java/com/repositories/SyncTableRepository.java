package com.repositories;

import com.domain.SyncTable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncTableRepository extends JpaRepository<SyncTable, Long>{
    List<SyncTable> findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(String type, String clientId, Pageable pageable);
    List<SyncTable> findBySyncTypeAndClientIdOrderBySyncStartTimestampDesc(String type, String clientId,Pageable pageable);


}
