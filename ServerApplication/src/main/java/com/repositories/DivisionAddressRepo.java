package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.constants.Status;
import com.domain.Division;

@Repository
@Transactional
public interface DivisionAddressRepo extends JpaRepository<Division, Long> {

    List<Division> findDivisionByzone_id(Long id);
    List<Division> findDivisionByzone_idAndStatusOrderByDivisionAsc(Long id, Status s);
    List<Division> findAllByStatusOrderByDivisionAsc(Status active);
	List<Division> findDivisionByzone_idAndDivisionAndStatus(Long id, String division, Status active);
	Division findDivisionById(Long division);
    List<Division> findDivisionByzone_idAndDivisionContainingIgnoreCaseAndStatus(Long Id, String division, Status active);

}
