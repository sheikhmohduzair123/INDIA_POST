package com.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.constants.PStatus;
import com.domain.Client;
import com.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import com.domain.Parcel;
import com.domain.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParcelRepository extends PagingAndSortingRepository<Parcel, Long> {
	List<Parcel> findByClientAndCreatedDateBetween(Client client, Date startDate, Date date);

	List<Parcel> findByClientAndCreatedDateBetweenAndStatus(Client client, Date startDate, Date date, String status);

	@Query("select p.labelCode from Parcel p order by p.id desc")
	List<String> getLabelCode(Pageable pageable);


	@Query(value = "SELECT * FROM PARCEL_DETAILS p WHERE p.createdDate BETWEEN ?1 AND ?2", nativeQuery = true)
	List<Parcel> findParcelListBetweenDate(Timestamp fromDate, Timestamp toDate);

	Parcel findParcelByTrackId(String trackId);

	List<Parcel> findParcelBypStatus(String status);
    List<Parcel> findBySyncFlag(boolean syncFlag);

	//List<Parcel> findAllParcelBypStatusAndCreatedBy(String pStatus, Integer id);

	List<Parcel> findAllParcelBypStatusAndStatusAndCreatedByIn(String string, String status,List<Integer> userIdList);

	//List<Parcel> findAllParcelBypStatusAndCreatedByInAndServiceInAndCreatedDateBetween(String pstatus,List<Integer> userIdList, List<Long> longList, Timestamp ts, Timestamp ts1);

//	List<Parcel> findAllParcelBypStatusAndCreatedByInAndServiceInAndCreatedDateBetweenAndStatus(String upperCase,List<Integer> userIdList, List<Long> services, Timestamp fromDate, Timestamp toDate, String parcelStatus);

	List<Parcel> findAllParcelByCreatedByInAndServiceInAndCreatedDateBeforeAndStatus(List<Integer> userIdList,
			List<Long> services, Timestamp toDate, String parcelStatus);



}
