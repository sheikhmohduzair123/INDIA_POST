package com.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.constants.Status;
import com.domain.Services;

public interface PostalServiceRepository extends JpaRepository<Services, Long> {
    @Query("SELECT s FROM Services s WHERE s.updatedOn >= ?1")
    List<Services> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTimestamp);

    List<Services> findByIdInAndStatus(List<Long> serviceIds, Status s);

	Optional<Services> findByIdAndStatus(Long serviceId, Status active);
}
