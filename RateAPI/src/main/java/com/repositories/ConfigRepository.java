package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {

	Config findTopByConfigTypeOrderByUpdatedOnDesc(String string);
}
