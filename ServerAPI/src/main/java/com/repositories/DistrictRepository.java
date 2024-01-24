package com.repositories;

import com.constants.Status;
import com.domain.Client;
import com.domain.District;
import com.domain.MasterAddress;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query("SELECT d FROM District d WHERE d.updatedOn >= ?1")
   List<District> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTime);
}
