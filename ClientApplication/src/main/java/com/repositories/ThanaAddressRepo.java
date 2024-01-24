package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.constants.Status;
import com.domain.District;
import com.domain.Thana;

@Repository
@Transactional
public interface ThanaAddressRepo extends JpaRepository<Thana, Long> {

    List<Thana> findByDistrictIn(List<District> district);
    List<Thana> findThanaBydistrict_id(Long id);
    List<Thana> findThanaBydistrict_idAndStatusOrderByThanaAsc(Long id, Status active);
    List<Thana> findThanaBydistrict_idAndThanaAndStatus(Long districtId, String thana,Status ACTIVE);
    List<Thana> findAllByStatusOrderByThanaAsc(Status i);
    List<Thana> findThanaBydistrict_idAndThanaContainingIgnoreCaseAndStatus(Long Id, String thana, Status active);

}
