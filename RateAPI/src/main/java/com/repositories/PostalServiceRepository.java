package com.repositories;

import com.constants.Status;
import com.domain.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostalServiceRepository extends JpaRepository<Services, Long> {
    List<Services> findByIdInAndStatus(List<Long> serviceIds, Status s);

	Optional<Services> findByIdAndStatus(Long serviceId, Status active);
}
