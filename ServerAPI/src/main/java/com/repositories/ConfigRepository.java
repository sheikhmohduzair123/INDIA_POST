package com.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.domain.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    @Query("SELECT c FROM Config c WHERE c.updatedOn >= ?1")
	List<Config> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTime);
}
