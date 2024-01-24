package com.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.constants.PriceType;
import com.constants.Status;
import com.domain.RateTable;

public interface RateTableRepository extends JpaRepository<RateTable, Long> {

	List<RateTable> findByRateServiceCategoryServiceId(long serviceId);
	List<RateTable> findByRateServiceCategoryServiceIdInAndStatus(List<Long> services, Status status, Sort sort);
	RateTable findByIdAndRateServiceCategoryParentServiceIdNullAndStatus(Long id, Status active);
	List<RateTable> findByStatus(Status status);
	List<RateTable> findRateTableByStatus(Status status);

}