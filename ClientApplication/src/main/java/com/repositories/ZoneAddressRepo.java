package com.repositories;

import java.util.List;

import com.constants.Status;
import com.domain.Division;
import com.domain.Zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ZoneAddressRepo extends JpaRepository<Zone, Long> {

	List<Zone> findAllByStatusOrderByZoneAsc(Status i);
	List<Zone> findZoneByZoneAndStatus(String zone, Status active);
	Zone findZoneById(Long zone);
	List<Zone> findByZoneContainingIgnoreCaseAndStatus(String zone, Status active);
}
