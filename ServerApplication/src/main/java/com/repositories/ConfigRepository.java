package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.constants.Status;
import com.domain.Config;

public interface ConfigRepository extends JpaRepository<Config, Long>{

	List<Config> findAllByStatusOrderByConfigTypeAsc(Status active);

	//List<Config> findConfigTypeByDivisionContainingIgnoreCaseAndStatus(String configType, Status valueOf);

	List<Config> findConfigByConfigTypeContainingIgnoreCaseAndStatus(String configType, Status valueOf);

	List<Config> findAllByStatus(Status active);

}
