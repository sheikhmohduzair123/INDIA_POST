package com.repositories;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.constants.Status;
import com.domain.RateTable;

public interface RateTableRepository extends JpaRepository<RateTable, Long>{
	
	List<RateTable> findByRateServiceCategoryServiceId(long serviceId);
	
	@Transactional
	List<RateTable> findAllByCreatedOnBeforeAndStatus(Timestamp tsFromDate, Status disabled);
	List<RateTable> findByRateServiceCategoryServiceIdInAndStatus(List<Long> services, Status status, Sort sort);
}
