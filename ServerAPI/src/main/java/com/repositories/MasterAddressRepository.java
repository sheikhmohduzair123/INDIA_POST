package com.repositories;

import com.domain.MasterAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface MasterAddressRepository extends JpaRepository<MasterAddress, Integer>{
    @Query("SELECT m FROM MasterAddress m WHERE m.updatedOn >= ?1")
    List<MasterAddress> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTimestamp);
}
