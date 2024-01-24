package com.repositories;

import java.util.List;

import com.constants.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.RateTable;

public interface RateTableRepository extends JpaRepository<RateTable, Long>{
	
	List<RateTable> findByRateServiceCategoryServiceIdAndStatus(long serviceId, Status status);

}
