package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.constants.Status;
import com.domain.MasterAddress;
import com.domain.Thana;
@Repository
public interface MasterAddressRepository extends JpaRepository<MasterAddress, Long>{

	List<MasterAddress> findByPostalCodeAndStatus(int postalCode, Status status);
	List<MasterAddress> findBySubOfficeAndStatus(String subOffice, Status status);

	@Query("SELECT DISTINCT m.division FROM MasterAddress m WHERE m.zone = ?1 and m.status = ?2")
	List<String> findDistinctDivisionByZone(String zone, Status status);

	@Query("SELECT DISTINCT m.district FROM MasterAddress m WHERE m.division = ?1 and m.status = ?2")
	List<String> findDistinctDistrictByDivision(String division, Status status);

	@Query("SELECT DISTINCT m.thana FROM MasterAddress m WHERE m.district = ?1 and m.status = ?2")
	List<String> findDistinctThanaByDistrict(String district, Status status);

	@Query("SELECT DISTINCT m.subOffice FROM MasterAddress m WHERE m.thana = ?1 and m.status = ?2")
	List<String> findDistinctSubOfficeByThana(String thana, Status status);

	/*
	 * @Query("SELECT DISTINCT m.zone FROM MasterAddress m") List<String>
	 * findDistinctZone();
	 *
	 * @Query("SELECT DISTINCT m.division FROM MasterAddress m") List<String>
	 * findDistinctDivision();
	 *
	 * @Query("SELECT DISTINCT m.district FROM MasterAddress m") List<String>
	 * findDistinctDistrict();
	 */
	List<MasterAddress> findByThanaStartsWithIgnoreCase(String thana);

	List<MasterAddress> findBySubOfficeStartsWithIgnoreCase(String subOffice);

	List<MasterAddress> findByDistrictIgnoreCase(String district);

	List<MasterAddress> findAddressByDivisionIgnoreCase(String division);

	/*
	 * @Query("SELECT DISTINCT m.subOffice FROM MasterAddress m") List<String>
	 * findDistinctSubOffice();
	 *
	 * @Query("SELECT DISTINCT m.thana FROM MasterAddress m") List<String>
	 * findDistinctThana();
	 */
	List<MasterAddress> findByThanaAndStatus(String thana, Status status);

	//***Use For Master Address ***//
	List<MasterAddress> findByThanaIdIn(List<Long> thana);
	List<MasterAddress> findMasterAddressByThanaAndStatus(String thana, Status active);
	List<MasterAddress> findAllByStatus(Status i);
	List<MasterAddress> findAllByStatusOrderBySubOfficeAsc(Status i);
	List<MasterAddress> findMasterAddressByThanaAndSubOfficeAndStatus(String thana, String subOffice,Status ACTIVE);
	MasterAddress findMasterAddressByPostalCodeAndStatus(int pinCode, Status active);
	Boolean existsByPostalCodeAndStatus(int postalCode, Status status);
	List<Integer> findPostalCodeByStatus(Status active);

	@Query("SELECT DISTINCT m.zone FROM MasterAddress m WHERE m.status = ?1")
	List<String> findDistinctZone(Status active);

	@Query("SELECT DISTINCT m.division FROM MasterAddress m WHERE m.status = ?1")
	List<String> findDistinctDivision(Status active);

	@Query("SELECT DISTINCT m.district FROM MasterAddress m WHERE m.status = ?1")
	List<String> findDistinctDistrict(Status active);

	@Query("SELECT DISTINCT m.thana FROM MasterAddress m WHERE m.status = ?1")
	List<String> findDistinctThana(Status active);

	@Query("SELECT DISTINCT m.subOffice FROM MasterAddress m WHERE m.status = ?1")
	List<String> findDistinctSubOffice(Status active);

	List<MasterAddress>
	findByZoneContainingIgnoreCaseAndDivisionContainingIgnoreCaseAndDistrictContainingIgnoreCaseAndThanaContainingIgnoreCaseAndSubOfficeContainingIgnoreCaseAndStatus
			(String zone, String division, String district, String thana, String subOffice, Status active);

	List<MasterAddress> findMasterAddressByZoneId(Long zoneId);
	List<MasterAddress> findMasterAddressByDivisionId(Long divisionId);
	List<MasterAddress> findMasterAddressByDistrictId(Long districtId);
	List<MasterAddress> findMasterAddressByThanaId(Long thanaId);
	List<MasterAddress> findPostalCodeByZoneAndStatus(String zone, Status status);
	List<MasterAddress> findPostalCodeByZoneAndDivisionAndStatus(String zone,String division, Status status);
	List<MasterAddress> findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndStatus(String zone, String division,String district,String thana, Status status);
	List<MasterAddress> findPostalCodeByZoneAndDivisionAndDistrictAndStatus(String zone, String division, String district,	Status status);
	List<MasterAddress> findPostalCodeByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndStatus(String zone,String division, String district, String thana, String subOffice, Status status);

	List<MasterAddress> findAllByThanaAndStatusOrderBySubOfficeAsc(String thana, Status status);

}
