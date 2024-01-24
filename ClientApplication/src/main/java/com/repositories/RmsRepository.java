package com.repositories;

import java.util.List;

import com.constants.Status;
import com.domain.Address;
import com.domain.RmsTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RmsRepository extends JpaRepository<RmsTable, Long> {

	List<RmsTable> findAllByStatus(Status s);
	Boolean existsByRmsNameContainingIgnoreCaseAndRmsTypeAndRmsAddressPostalCodeAndStatus(String RmsName, String RmsType, int postalCode, Status active);
	List<RmsTable> findRmsTableByRmsTypeAndStatus(String rmsType, Status s);
	RmsTable findRmsTableByIdAndStatus(Long rmsId, Status status);
	List<RmsTable> findRmsByStatus(Status active);
	List<RmsTable> findAllByStatusOrderByRmsNameAsc(Status active);
	List<RmsTable> findAllByIdAndStatus(Long locationId,Status s);


}
