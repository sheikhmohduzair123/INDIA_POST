package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.domain.IdCounters;

@Repository
public interface IdCounterRepository extends JpaRepository<IdCounters, Integer>{

	IdCounters findByRmsId(Long rmsId);

	IdCounters findByPostalCode(long postalCode);

	}