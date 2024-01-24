package com.repositories;

import java.sql.Timestamp;
import java.util.List;

import com.constants.BagStatus;
import com.constants.DispatchStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.Parcel;
import com.domain.TrackingCS;
import com.domain.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TrackingCSRepository extends JpaRepository<TrackingCS, Long> {

		List<TrackingCS> findByBagStatusAndLocationIdAndLocationTypeAndObjParcelNotNull(BagStatus bagStatus, Long locationId, LocationType locationType);
		List<TrackingCS> findByBagId(String bagId);
		List<TrackingCS> findByBagIdAndObjParcel(String bagId, Parcel objParcel);
		List<TrackingCS> findByObjParcel(Parcel parcel);
		List<TrackingCS> findByBagStatus(BagStatus received);
		List<TrackingCS> findAllByBagId(String bagId);
		//TrackingCS findByStatusAndObjParcel(Status status,Parcel parcel);
		TrackingCS findByObjParcelAndUpdatedByIn(Parcel parcel, List<User> userList);
		
		List<TrackingCS> findAllByUpdatedByIn(Sort sort, List<User> userList);
		List<TrackingCS> findBypStatusAndObjParcel(PStatus outForDelivery, Parcel trackId);

		TrackingCS findByObjParcelAndStatus(Parcel parcel, Status active);
		List<TrackingCS> findByBagIdAndStatus(String bagId, Status active);


		List<TrackingCS> findByBagStatusInAndUpdatedByInOrderByUpdatedOnDesc(List<BagStatus> str, List<User> user);
		//List<TrackingCS> findBypStatusInAndUpdatedOnBetweenAndStatusAndLocationTypeAndLocationIdAndUpdatedBy(List<PStatus> pstatus, Timestamp startDate, Timestamp endDate, Status active, LocationType rms, Long locationid, User user);

		List<TrackingCS> findAllBypStatusAndUpdatedByInAndObjParcelServiceInAndUpdatedOnBetween(PStatus pStatus,List<User> users ,List<Long> longList, Timestamp ts, Timestamp ts1);
		
		@Query(value = "SELECT DISTINCT ON (bagId) bagId, id, bagTitle, bagDesc, bagFillStatus, pStatus, locationId, locationType, bagStatus, trackingDesc, bagPaging, status, createdOn, createdBy, updatedBy, pTrackingId, updatedOn FROM tracking_cs t where t.bagId is not null order by bagid, updatedOn DESC", nativeQuery=true)
		List<TrackingCS> findDistinctBaGData(Pageable pageable);
		
		@Query(value = "SELECT DISTINCT ON (t.bagId) bagId, id, bagTitle, bagDesc, bagFillStatus, pStatus, locationId, locationType, bagStatus, trackingDesc, bagPaging, status, createdOn, createdBy, updatedBy, pTrackingId, updatedOn FROM tracking_cs t where t.bagStatus in (?1,?2) and t.locationId = ?3 and t.bagId is not null order by bagid, updatedOn DESC", nativeQuery=true)
		List<TrackingCS> findDistinctPostBagData(String inStatus,String creStatus, Long postCode);
		
		@Query(value = "SELECT DISTINCT ON (t.bagId) bagId, id, bagTitle, bagDesc, bagFillStatus, pStatus, locationId, locationType, bagStatus, trackingDesc, bagPaging, status, createdOn, createdBy, updatedBy, pTrackingId, updatedOn FROM tracking_cs t where t.bagStatus in (?1,?2) and t.locationId = ?3 and t.bagId is not null order by bagid, updatedOn DESC", nativeQuery=true)
		List<TrackingCS> findDistinctRMSBagData(String inStatus,String creStatus, Long rmsId);
		
		List<TrackingCS> findByUpdatedByInAndBagStatusIn(List<User> users, List<BagStatus> status);
		List<TrackingCS> findBypStatusAndUpdatedByInAndBagStatus(PStatus unbagged, List<User> users,BagStatus unbagged2);
		List<TrackingCS> findBypStatusInAndUpdatedByInAndObjParcelNotNull(List<PStatus> pstatus, List<User> users);
		List<TrackingCS> findByBagStatusIn(List<BagStatus> bagStatus);
		TrackingCS findTrackingCSByObjParcel(Parcel parcel);
		
}
