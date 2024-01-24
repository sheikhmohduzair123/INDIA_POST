package com.repositories;

import com.constants.Status;
import com.domain.Client;
import com.domain.MasterAddress;
import com.domain.Thana;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThanaRepository extends JpaRepository<Thana, Long> {

    @Query("SELECT m FROM Thana m WHERE m.updatedOn >= ?1")
    List<Thana> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTimestamp);
}
