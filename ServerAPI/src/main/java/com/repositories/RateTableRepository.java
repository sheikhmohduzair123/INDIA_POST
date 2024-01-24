package com.repositories;

import com.domain.RateTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface RateTableRepository extends JpaRepository<RateTable, Long>{
    @Query("SELECT r FROM RateTable r WHERE r.updatedOn >= ?1")
    List<RateTable> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTimestamp);

}
