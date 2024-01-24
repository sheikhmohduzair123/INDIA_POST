package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.IdCounters;

public interface IdCounterRepository extends JpaRepository<IdCounters, Integer>{

	IdCounters findByPostalCode(long postalCode);

	}