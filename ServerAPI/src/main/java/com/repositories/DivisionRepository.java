package com.repositories;

import com.constants.Status;
import com.domain.Client;
import com.domain.Division;
import com.domain.MasterAddress;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DivisionRepository extends JpaRepository<Division, Integer> {
    @Query("SELECT d FROM Division d WHERE d.updatedOn >= ?1")
 	List<Division> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTime);
}
