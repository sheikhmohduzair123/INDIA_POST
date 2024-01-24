package com.repositories;

import com.constants.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Config;

import java.util.List;

public interface ConfigRepository extends JpaRepository<Config, Long> {

	Config findTopByConfigTypeOrderByUpdatedOnDesc(String string);
	List<Config> findByConfigTypeInAndStatus(List<String> configTypes, Status status);
}
