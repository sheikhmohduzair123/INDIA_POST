package com.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.Parcel;

public interface ParcelRepository extends PagingAndSortingRepository<Parcel, Long> {
	
}
