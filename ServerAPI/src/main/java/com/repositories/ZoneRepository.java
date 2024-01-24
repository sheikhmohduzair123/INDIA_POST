package com.repositories;

import com.constants.Status;
import com.domain.Client;
import com.domain.MasterAddress;
import com.domain.Zone;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {

    @Query("SELECT z FROM Zone z WHERE z.updatedOn >= ?1")
	List<Zone> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTime);
}
