package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.constants.Status;
import com.domain.District;
import com.domain.Division;
import com.domain.User;

@Repository
@Transactional
public interface DistrictAddressRepo extends JpaRepository<District, Long> {

    List<District> findBydivision_id(Long id);
    List<District> findDistrictBydivision_idAndStatusOrderByDistrictAsc(Long id,Status active);
    List<District> findByDivisionIn(List<Division> div1);
    List<District> findAllByStatusOrderByDistrictAsc(Status i);
	List<District> findDistrictBydivisionId_idAndDistrictAndStatus(Long divisionId, String district, Status active);
	District findDistrictById(Long createdBy);
    List<District> findDistrictBydivision_idAndDistrictContainingIgnoreCaseAndStatus(Long Id, String district, Status active);

}
