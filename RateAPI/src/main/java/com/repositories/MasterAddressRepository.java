package com.repositories;

import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Parcel;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterAddressRepository extends JpaRepository<MasterAddress, Integer>{

	MasterAddress findByPostalCodeAndStatus(int postalCode, Status active);

	List<MasterAddress> findDistinctByDistrictAndStatus(String district, Pageable pageable, Status active);
	List<MasterAddress> findDistinctByDivisionAndStatus(String division, Pageable pageable, Status active);
	List<MasterAddress> findDistinctByZoneAndStatus(String zone, Pageable pageable, Status active);
	List<MasterAddress> findDistinctByThanaAndStatus(String thana, Pageable pageable, Status active);
}
